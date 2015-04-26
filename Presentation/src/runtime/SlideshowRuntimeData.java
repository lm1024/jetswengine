package runtime;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import renderer.SlideRenderer;
import Data.Slide;
import Data.SlideItem;
import Data.Slideshow;

public class SlideshowRuntimeData {

	/* Variables for keeping track of the current slideshow and current slide. */
	private Slideshow slideshow;
	private Slide currentSlide;

	/* The slide renderer that will draw and update the slides. */
	private SlideRenderer slideRenderer;

	/* Stage that the slides will be displayed in. */
	private Stage secondaryStage;

	/* The current aspect ratio of the slideshow. */
	private double currentXAspectRatio;
	private double currentYAspectRatio;

	/* The current slide number that the user is viewing. */
	private int currentSlideNumber;

	/* timingList will include the times of all of the updates in the slideshow. */
	private ArrayList<Long> timingList;
	private Long lastEventTime;

	/* Required for the scheduler. */
	protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> nextUpdate;

	/*
	 * Boolean for tracking if the screen has been hidden in black or white, and
	 * the rectangle that is doing the hiding.
	 */
	private boolean screenHidden;
	private Rectangle rect;

	/* int for tracking the input from the number keys for jumping to a slide. */
	private String numberInput;

	/**
	 * Constructor for the runtime class.
	 * 
	 * @param slideshow
	 *            the slideshow to be displayed in the new fullscreen window.
	 */
	public SlideshowRuntimeData(Slideshow slideshow) {
		/*
		 * Sets the current slideshow and the aspect ratio of both of the
		 * dimentions of the slideshow
		 */
		this.slideshow = slideshow;
		this.currentXAspectRatio = slideshow.getDefaults().getxAspectRatio();
		this.currentYAspectRatio = slideshow.getDefaults().getyAspectRatio();

		/*
		 * Instantiates the new group, stage and scene that the slideshow will
		 * be displayed in.
		 */
		Group group = new Group();
		Scene scene = new Scene(group, 10, 10);
		secondaryStage = new Stage();
		secondaryStage.setScene(scene);

		/* Moves the window off screen, so that it is "hidden". */
		secondaryStage.setX(-200);

		/* Set the stage to go fullscreen on the primary stage. */
		secondaryStage.setFullScreen(true);

		/* Instantiates the slideRenderer for this slideshow. */
		this.slideRenderer = new SlideRenderer(secondaryStage);

		/* Shows the screen so that the correct screen boundaries can be set. */
		secondaryStage.show();

		/* Updates the screen boundaries for the first time. */
		updateScreenBoundaries();

		/*
		 * Set the mouse click handler. Handles the clicking to move screen, and
		 * the right click menu. TODO this commment
		 */
		scene.setOnMouseClicked(new MouseClickHandler());

		/*
		 * Set the keypress handler. Handles all keyboard events including the
		 * arrow keys moving slide.
		 */
		scene.setOnKeyPressed(new KeyboardHandler());

		secondaryStage.setOnCloseRequest(new ClosingWindowHandler());

		/*
		 * Set the listeners for changing widths and heights of the window.
		 * Ensures that the screen boundaries are kept updated.
		 */
		scene.widthProperty().addListener(new WindowResizeHandler());
		scene.heightProperty().addListener(new WindowResizeHandler());

		/* Instantiate the number input string. */
		numberInput = "";

		/*
		 * Sets the current slide to be the initial slide in the slideshow and
		 * displays it.
		 */
		currentSlide = slideshow.getSlide(0);
		buildCurrentSlide();
	}

	/**
	 * Method updates the boundaries of the current slide. Ensures that each
	 * slide is displayed correctly within the required aspect ratio.
	 */
	private void updateScreenBoundaries() {
		/* Gets the current scene height and width. */
		double sceneHeight = secondaryStage.getScene().getHeight();
		double sceneWidth = secondaryStage.getScene().getWidth();

		/* Declares the slide dimention variables. */
		double xSlideStart;
		double ySlideStart;
		double slideWidth;
		double slideHeight;

		/*
		 * Calculates which direction is the limiting factor in keeping the
		 * aspect ratio constant but having the largest space available for the
		 * slide.
		 */
		if ((sceneWidth / currentXAspectRatio) < (sceneHeight / currentYAspectRatio)) {
			/* Horizontal restriction */
			slideWidth = sceneWidth;
			slideHeight = (sceneWidth / currentXAspectRatio) * currentYAspectRatio;
			xSlideStart = 0;
			ySlideStart = (sceneHeight - slideHeight) / 2;
		} else {
			/* Vertical restriction */
			slideWidth = (sceneHeight / currentYAspectRatio) * currentXAspectRatio;
			slideHeight = sceneHeight;
			xSlideStart = (sceneWidth - slideWidth) / 2;
			ySlideStart = 0;
		}

		/* Update the slide dimension in the renderer. */
		slideRenderer.updateSlideDimensions(xSlideStart, ySlideStart, slideWidth, slideHeight);
	}

	/** Method renders the current slide. */
	private void buildCurrentSlide() {
		/* Cancel all pending timing tasks */
		if (nextUpdate != null && !nextUpdate.isDone()) {
			nextUpdate.cancel(true);
		}

		/* Pass the current slide to the renderer to be drawn. */
		slideRenderer.drawSlide(currentSlide);

		/* Build the schedular list of timing events for this slide. */
		buildTimingList();

		/* Start the schedular if the timing list isn't empty. */
		if (!timingList.isEmpty()) {
			scheduleNextUpdate();
		}
	}

	/**
	 * Method builds the arraylist of times for items to update on the current
	 * slide.
	 */
	private void buildTimingList() {
		/* Clear the last event time variable */
		lastEventTime = 0l;

		/* Instantiate a new arraylist for the timinglist */
		timingList = new ArrayList<Long>();

		/*
		 * Loops through all the slide items, adds the start times and durations
		 * to the timing list.
		 */
		for (SlideItem slideItem : currentSlide.getAll()) {
			/* Adds the non zero start times to the timing list. */
			if (slideItem.getStartTime() > 0) {
				/* Converting the start time to milliseconds */
				timingList.add((long) ((double) slideItem.getStartTime() * 1000));
			}

			/* Adds the non-max durations to the timing list. */
			if (slideItem.getDuration() != Float.MAX_VALUE) {
				/*
				 * Adding the non-max durations + start time (which is the time
				 * of the event) to the list.
				 */
				timingList.add((long) (((double) slideItem.getStartTime() + slideItem.getDuration()) * 1000));
			}
		}
		/* Sort the list into ascending order. */
		Collections.sort(timingList);

		/* Remove any duplicates from the list. */
		timingList = new ArrayList<Long>(new LinkedHashSet<Long>(timingList));
	}

	/**
	 * Method schedules the next update slide event to hide/show slide elements.
	 */
	private void scheduleNextUpdate() {
		/*
		 * Add a new update to the schedular at the time of the next update -
		 * the time of the last event (which is the time when this method is
		 * called.
		 */
		nextUpdate = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				/* Hide/show all the relevent elements that need to be changed. */
				slideRenderer.updateSlide(timingList.get(0));

				/* Store the last event time before removing it from the list. */
				lastEventTime = timingList.get(0);
				timingList.remove(0);

				/* Add a new event if the list isn't finished. */
				if (!timingList.isEmpty()) {
					scheduleNextUpdate();
				}
			}
		}, timingList.get(0) - lastEventTime, TimeUnit.MILLISECONDS);
	}

	/**
	 * Class for handling mouse click events. Handles left clicks moving the
	 * slide back and forwards.
	 * 
	 * @author tjd511
	 */
	private class MouseClickHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent e) {
			/* Left mouse button (normally) */
			if (e.getButton() == MouseButton.PRIMARY) {
				/* ID which side of the screen is clicked on */
				if (e.getX() > (secondaryStage.getScene().getWidth()) * 0.5) {
					/* Right side of screen */
					moveForwards();
				} else {
					/* Left side of screen */
					moveBackwards();
				}
			}
		}
	}

	/**
	 * Class for handling window resize events.
	 * 
	 * @author tjd511
	 */
	private class WindowResizeHandler implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
				Number newSceneHeight) {
			/*
			 * If the screen has changed, update the boundary values and
			 * re-force it to go fullscreen.
			 */
			updateScreenBoundaries();
			secondaryStage.setFullScreen(true);
		}
	}

	/**
	 * Custom keyboard handler to handle keypresses. Handled events: Pressing
	 * escape closes the window, arrow keys change the current slide.
	 * 
	 * @author tjd511
	 */
	private class KeyboardHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent keyEvent) {
			if (keyEvent.getCode().isDigitKey()) {
				/* Add the digit to the end of the number input string. */
				numberInput = numberInput.concat(keyEvent.getText());
			} else {
				/* Switch through the different keyCodes that are handled. */
				switch (keyEvent.getCode()) {
				/*
				 * Close the screen if fullscreen is closed using the escape
				 * button
				 */
				case ESCAPE:
					secondaryStage.close();
					closeSlideshow();
					break;
				/* Arrow keys move the slides back and forwards. */
				case RIGHT:
					moveForwards();
					break;
				case LEFT:
					moveBackwards();
					break;
				case W:
					/* Intentionally falls through */
				case B:
					if (!screenHidden) {
						hideScreen(keyEvent);
					} else {
						showScreen();
					}
					break;
				case ENTER:
					int requestedSlide;
					/* Catches if the numberInput has no numbers in the string. */
					try {
						requestedSlide = Integer.parseInt(numberInput);
					} catch (NumberFormatException e) {
						/* Reset the string and quit the case. */
						numberInput = "";
						break;
					}
					/* If the requested slide is in the slideshow, go to it. */
					if (requestedSlide < slideshow.getSlides().size()) {
						currentSlideNumber = requestedSlide;
						currentSlide = slideshow.getSlide(currentSlideNumber);
						buildCurrentSlide();
					}
					/* Else move to the last slide. */
					else {
						currentSlideNumber = slideshow.getSlides().size() - 1;
						currentSlide = slideshow.getSlide(currentSlideNumber);
						buildCurrentSlide();
					}
					/* Reset the string */
					numberInput = "";
				default:
					break;
				}
			}
		}
	}

	/**
	 * Window closing handler for if the window is closed by means other than
	 * pressing the escape key.
	 * 
	 * @author tjd511
	 */
	private class ClosingWindowHandler implements EventHandler<WindowEvent> {
		@Override
		public void handle(WindowEvent arg0) {
			/*
			 * If the window is closed, close the slideshow and exit the
			 * program.
			 */
			closeSlideshow();
			System.exit(0); // not required
		}
	}

	/**
	 * Method to increase the slide number if the current slide number is under
	 * the number of slides in the slideshow.
	 */
	private void moveForwards() {
		/* If the screen has been hidden (by the black or white rectangle). */
		if (screenHidden) {
			showScreen();
		}
		/* If we are currently on a graph slide */
		else if (currentSlide == null) {
			/* Change the value of slideNo accordingly */
			if (currentSlideNumber < slideshow.getSlides().size() - 1) {
				currentSlideNumber++;
				currentSlide = slideshow.getSlide(currentSlideNumber);
				buildCurrentSlide();
			}
		}
		/*
		 * If we need to draw a answer slide (if the current slide has a
		 * question and if the answer data is valid.
		 */
		else if (currentSlide.containsQuestion() && currentSlide.getQuestion().hasAnswerData()) {
			slideRenderer.clear();
			slideRenderer.buildAnswerSlide(currentSlide.getQuestion());
			currentSlide = null;
		}
		/* If we just need to move forwards a slide */
		else {
			/* Change the value of slideNo accordingly */
			if (currentSlideNumber < slideshow.getSlides().size() - 1) {
				currentSlideNumber++;
				currentSlide = slideshow.getSlide(currentSlideNumber);
				buildCurrentSlide();
			}
		}
	}

	/**
	 * Method to decrease the slide number if the current slide number is larger
	 * than 0.
	 */
	private void moveBackwards() {
		/* If the screen has been hidden (by the black or white rectangle). */
		if (screenHidden) {
			showScreen();
		}
		/* If we are currently on a graph slide */
		else if (currentSlide == null) {
			/* Clear the answer slide and rebuild the previous slide. */
			slideRenderer.clear();
			currentSlide = slideshow.getSlide(currentSlideNumber);
			buildCurrentSlide();
		}
		/* Change the value of slideNo accordingly */
		else if (currentSlideNumber > 0) {
			currentSlideNumber--;
			currentSlide = slideshow.getSlide(currentSlideNumber);
			buildCurrentSlide();
		}
	}

	/**
	 * Shuts down the scheduler so that events do not happen after the window
	 * has been closed.
	 */
	public void closeSlideshow() {
		scheduler.shutdownNow();
	}

	/**
	 * @param keyEvent
	 */
	private void hideScreen(KeyEvent keyEvent) {
		/* Get the screensize */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();

		/* Get the current group that is being drawn on. */
		Group group = (Group) secondaryStage.getScene().getRoot();

		/* Instantiate a new rectangle that is the size of the screen. */
		rect = new Rectangle(0, 0, screenWidth, screenHeight);

		/*
		 * Fill the rectangle with white if w is pressed or black if b is
		 * pressed.
		 */
		switch (keyEvent.getCode()) {
		case W:
			rect.setFill(Color.WHITE);
			break;
		case B:
			/* Falls through */
		default:
			rect.setFill(Color.BLACK);
			break;
		}

		/* Add the rectangle to the group. */
		group.getChildren().add(rect);

		/*
		 * This is used to see if the rectangle needs to be removed upon the
		 * next event.
		 */
		screenHidden = true;
	}

	private void showScreen() {
		/* Get the current group */
		Group group = (Group) secondaryStage.getScene().getRoot();

		/* Remove the rectangle from the screen. */
		group.getChildren().remove(rect);

		/* Reset the hidden tracker */
		screenHidden = false;
	}

}
