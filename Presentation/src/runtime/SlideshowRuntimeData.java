package runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
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

		Set<Long> deDuplicatedTimingList = new LinkedHashSet<Long>(timingList);

		System.out.println("Timing list: " + timingList.toString()); // TODO
																		// remove
		System.out.println("De Timing list: " + (deDuplicatedTimingList).toString()); // TODO
																						// remove
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
					/* If we are currently on a graph slide */
					if (currentSlide == null) {
						moveForwards();
						currentSlide = slideshow.getSlide(currentSlideNumber);
						buildCurrentSlide();
					}
					/*
					 * If we need to draw a answer slide (if the current slide
					 * has a question and if the answer data is valid.
					 */
					else if (currentSlide.containsQuestion() && currentSlide.getQuestion().hasAnswerData()) {
						slideRenderer.clear();
						slideRenderer.buildAnswerSlide(currentSlide.getQuestion());
						currentSlide = null;
					}
					/* If we just need to move forwards a slide */
					else {
						moveForwards();
						currentSlide = slideshow.getSlide(currentSlideNumber);
						buildCurrentSlide();
					}
				} else {
					/* Left side of screen */
					moveBackwards();
					currentSlide = slideshow.getSlide(currentSlideNumber);
					buildCurrentSlide();
				}
			}
		}
	}

	/**
	 * Class for handling window resize events.
	 * 
	 * @author tjd511
	 * 
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
			/* Switch through the different keyCodes that are handled. */
			switch (keyEvent.getCode()) {
			/* Close the screen if fullscreen is closed using the escape button */
			case ESCAPE:
				secondaryStage.close();
				closeSlideshow();
				break;
			case RIGHT:
				moveForwards();
				currentSlide = slideshow.getSlide(currentSlideNumber);
				buildCurrentSlide();
				break;
			case LEFT:
				moveBackwards();
				currentSlide = slideshow.getSlide(currentSlideNumber);
				buildCurrentSlide();
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Custom keyboard handler to handle keypresses. Handled events: Pressing
	 * escape closes the window, arrow keys change the current slide.
	 * 
	 * @author tjd511
	 */
	private class ClosingWindowHandler implements EventHandler<WindowEvent> {
		@Override
		public void handle(WindowEvent arg0) {
			// TODO Auto-generated method stub
			closeSlideshow();
			System.exit(0);
		}
	}

	/**
	 * Method to increase the slide number if the current slide number is under
	 * the number of slides in the slideshow.
	 */
	private void moveForwards() {
		/* Change the value of slideNo accordingly */
		if (currentSlideNumber < slideshow.getSlides().size() - 1) {
			currentSlideNumber++;
		}
	}

	/**
	 * Method to decrease the slide number if the current slide number is larger
	 * than 0.
	 */
	private void moveBackwards() {
		/* Change the value of slideNo accordingly */
		if (currentSlideNumber > 0) {
			currentSlideNumber--;
		}
	}

	/**
	 * Shuts down the scheduler so that events do not happen after the window
	 * has been closed.
	 */
	public void closeSlideshow() {
		scheduler.shutdownNow();
	}

}
