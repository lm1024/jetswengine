package runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import renderer.SlideRenderer;
import Data.Question;
import Data.Slide;
import Data.SlideItem;
import Data.Slideshow;

public class SlideshowRuntimeData {

	private Slideshow slideshow;
	private Slide currentSlide;
	private SlideRenderer slideRenderer;

	private Scene scene;
	private Stage secondaryStage;

	private long currentSlideStartTime;

	private double currentXAspectRatio;
	private double currentYAspectRatio;

	private int slideNumber;

	private ArrayList<Long> timingList;
	private Long lastEventTime;

	protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private ScheduledFuture<?> currentTask;

	public SlideshowRuntimeData(Slideshow slideshow) {
		this.slideshow = slideshow;
		this.currentXAspectRatio = 16;// slideshow.getInfo().getXAspectRatio()
		this.currentYAspectRatio = 9;// slideshow.getInfo().getYAspectRatio()

		Group group = new Group();
		secondaryStage = new Stage();

		this.scene = new Scene(group, 10, 10);
		secondaryStage.setScene(scene);

		secondaryStage.setFullScreen(true);
		secondaryStage.show();

		this.slideRenderer = new SlideRenderer(group);

		scene.setOnMouseClicked(new MouseClickHandler());

		scene.widthProperty().addListener(new WindowResizeHandler());
		scene.heightProperty().addListener(new WindowResizeHandler());
		scene.setOnKeyPressed(new KeyboardHandler());

		currentSlide = slideshow.getSlide(0);

		updateScreenBoundaries();

		buildCurrentSlide();
	}

	private void updateScreenBoundaries() {
		double sceneHeight = scene.getHeight();
		double sceneWidth = scene.getWidth();

		double xSlideStart;
		double ySlideStart;
		double slideWidth;
		double slideHeight;

		if ((sceneWidth / currentXAspectRatio) < (sceneHeight / currentYAspectRatio)) {
			slideWidth = sceneWidth;
			slideHeight = ((sceneWidth / currentXAspectRatio) * currentYAspectRatio);
			xSlideStart = 0;
			ySlideStart = (sceneHeight - slideHeight) / 2;
		} else {
			slideWidth = (((sceneHeight) / currentYAspectRatio) * currentXAspectRatio);
			slideHeight = sceneHeight;
			xSlideStart = (sceneWidth - slideWidth) / 2;
			ySlideStart = 0;
		}

		slideRenderer.updateSlideDimentions(xSlideStart, ySlideStart, slideWidth, slideHeight);
	}

	private void buildCurrentSlide() {
		/* Cancel all pending timing tasks */
		if (currentTask != null && !currentTask.isDone()) {
			currentTask.cancel(true);
		}

		slideRenderer.drawSlide(currentSlide);
		buildTimingList();
		if (!timingList.isEmpty()) {
			scheduleNextUpdate();
		}
	}

	private void updateSlide() {
		System.out.println("Update at " + timingList.get(0));
		slideRenderer.updateSlide(timingList.get(0));
	}

	private void buildTimingList() {
		lastEventTime = 0l;
		timingList = new ArrayList<Long>();
		for (SlideItem slideItem : currentSlide.getAll()) {

			if (Math.abs(slideItem.getStartTime()) > 0.001) {
				System.out.println("Adding start update at : " + (long) ((double) slideItem.getStartTime() * 1000));
				timingList.add((long) ((double) slideItem.getStartTime() * 1000));
				System.out.println("For item " + slideItem.getType());
			}

			if ((Math.abs((slideItem.getStartTime() + slideItem.getDuration())) > 0.001)
					&& (slideItem.getDuration() != Float.MAX_VALUE)) {
				System.out.println("Adding end update at : "
						+ (long) (((double) slideItem.getStartTime() + slideItem.getDuration()) * 1000));
				timingList.add((long) (((double) slideItem.getStartTime() + slideItem.getDuration()) * 1000));
			}

		}
		Collections.sort(timingList);
		System.out.println(timingList.toString());
	}

	private void scheduleNextUpdate() {
		System.out.println("Next update: " + timingList.get(0));
		currentTask = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				updateSlide();
				lastEventTime = timingList.get(0);
				timingList.remove(0);
				if (!timingList.isEmpty()) {
					scheduleNextUpdate();
				} else {
					System.out.println("End of schedule");
					// scheduler.shutdown();
				}
			}
		}, timingList.get(0) - lastEventTime, TimeUnit.MILLISECONDS);
	}

	private class MouseClickHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent e) {
			/* ID which side of the screen is clicked on */
			if (e.getX() > (scene.getWidth()) * 0.5) {
				/* If we are currently on a graph slide */
				if (currentSlide == null) {
					moveForwards();
					currentSlide = slideshow.getSlide(slideNumber);
					buildCurrentSlide();
				}
				/* If we need to draw a graph */
				else if (currentSlide.containsQuestion() == true) {
					slideRenderer.clear();
					slideRenderer.buildAnswerSlide(currentSlide.getQuestion());
					currentSlide = null;
				}
				/* If we just need to move forwards a slide */
				else {
					moveForwards();
					currentSlide = slideshow.getSlide(slideNumber);
					buildCurrentSlide();
				}
			} else {
				moveBackwards();
				currentSlide = slideshow.getSlide(slideNumber);
				buildCurrentSlide();
			}

		}
	}

	private class WindowResizeHandler implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
				Number newSceneHeight) {
			updateScreenBoundaries();
		}
	}

	/*
	 * Custom keyboard handler to handle keypresses. Handled events: Pressing
	 * escape closes the window.
	 */
	private class KeyboardHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent keyEvent) {
			
			switch (keyEvent.getCode()) {
			/* Close the screen if fullscreen is closed using the escape button */
			case ESCAPE:
				secondaryStage.close();
				closeSlideshow();
				break;
			default:
				break;
			}

			/*if (keyEvent.getCode() == KeyCode.ESCAPE) {
				secondaryStage.close();
				closeSlideshow();
			}*/

		}
	}

	/*
	 * Method to increase the slide number if the current slide number is under
	 * the number of slides in the slideshow.
	 */
	private void moveForwards() {
		/* Change the value of slideNo accordingly */
		if (slideNumber < slideshow.getSlides().size() - 1) {
			slideNumber++;
		}
	}

	/*
	 * Method to decrease the slide number if the current slide number is larger
	 * than 0.
	 */
	private void moveBackwards() {
		/* Change the value of slideNo accordingly */
		if (slideNumber > 0) {
			slideNumber--;
		}
	}

	private long getCurrentTime() {
		return System.currentTimeMillis();
	}

	/*
	 * Shuts down the scheduler so that events do not happen after the window
	 * has been closed.
	 */
	public void closeSlideshow() {
		scheduler.shutdownNow();
	}

}
