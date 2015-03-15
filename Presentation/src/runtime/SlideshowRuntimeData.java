package runtime;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import renderer.SlideRenderer;
import Data.Slide;
import Data.SlideItem;
import Data.Slideshow;

public class SlideshowRuntimeData {

	private Slideshow slideshow;
	private Slide currentSlide;
	private SlideRenderer slideRenderer;

	private Scene scene;

	private long currentSlideStartTime;

	private double currentXAspectRatio;
	private double currentYAspectRatio;

	private int slideNumber;

	private ArrayList<Long> timingList;

	public SlideshowRuntimeData(Slideshow slideshow, Group group) {
		this.slideshow = slideshow;

		currentXAspectRatio = 16;// slideshow.getInfo().getXAspectRatio()
		currentYAspectRatio = 9;// slideshow.getInfo().getYAspectRatio()

		this.slideRenderer = new SlideRenderer(group);
		this.scene = group.getScene();
		updateScreenBoundaries();

		scene.setOnMouseClicked(new mouseClickHandler());

		currentSlide = slideshow.getSlide(0);
		slideRenderer.drawSlide(currentSlide);

		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
				updateScreenBoundaries();
			}
		});

		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				updateScreenBoundaries();
			}
		});

	}

	private void updateScreenBoundaries() {
		// screenBoundaries = Screen.get
		double sceneHeight = scene.getHeight();
		double sceneWidth = scene.getWidth();

		double xSlideStart;
		double ySlideStart;
		double slideWidth;
		double slideHeight;

		if (sceneWidth / currentXAspectRatio < sceneHeight / currentYAspectRatio) {
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
		updateSlide();
	}

	private void updateSlide() {
		slideRenderer.clear();

		currentSlide = slideshow.getSlide(slideNumber);
		/*
		 * .copySlide();
		 * 
		 * System.out.println( slideshow .getSlide(slideNumber
		 * ).get(0).toString()); currentSlide .remove(0); System.out
		 * .println(slideshow .getSlide (slideNumber) .get(0).toString());
		 */

		slideRenderer.drawSlide(currentSlide);
		currentSlideStartTime = getCurrentTime();
		buildTimingList();
		System.out.println("StartingTime: " + TimeUnit.MILLISECONDS.toSeconds(currentSlideStartTime));
		for (int i = 0; i < timingList.size(); i++) {
			System.out.println("Next update: " + TimeUnit.MILLISECONDS.toSeconds(timingList.get(i).longValue()));
			System.out.println("   Difference: "
					+ ((TimeUnit.MILLISECONDS.toSeconds(timingList.get(i).longValue())) - (TimeUnit.MILLISECONDS
							.toSeconds(currentSlideStartTime))));
		}
	}

	private void buildTimingList() {
		timingList = new ArrayList<Long>();
		for (SlideItem currentItem : currentSlide.getAll()) {
			long currentItemStartTime = (long) (currentSlideStartTime + TimeUnit.SECONDS.toMillis((long) currentItem
					.getStartTime()));
			long currentItemDuration = TimeUnit.SECONDS.toMillis((long) currentItem.getDuration());

			if (currentItemStartTime > currentSlideStartTime) {
				timingList.add(currentItemStartTime);
			}

			if (currentItemDuration != Long.MAX_VALUE) {
				timingList.add(currentItemDuration + currentSlideStartTime);
			}
		}
	}

	private class mouseClickHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent e) {
			/* ID which side of the screen is clicked on */
			if (e.getX() > (scene.getWidth()) * 0.5) {
				/* Change the value of slideNo accordingly */
				slideNumber++;
			} else {
				if (slideNumber > 0) {
					slideNumber--;
				}
			}
			updateSlide();
			System.out.println("Current slide: " + slideNumber);
		}
	}

	private long getCurrentTime() {
		return System.currentTimeMillis();
	}

}
