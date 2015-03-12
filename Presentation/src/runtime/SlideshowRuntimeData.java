package runtime;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import renderer.SlideRenderer;
import Data.Slide;
import Data.Slideshow;

public class SlideshowRuntimeData {

	private Slideshow slideshow;
	private Slide currentSlide;
	private SlideRenderer slideRenderer;

	private Rectangle2D screenBoundarys;

	private Scene scene;

	private double currentSceneHeight;
	private double currentSceneWidth;
	
	private double currentXAspectRatio;
	private double currentYAspectRatio;
	
	private int slideNumber;

	public SlideshowRuntimeData(Slideshow slideshow, Group group) {
		this.slideshow = slideshow;

		currentXAspectRatio = 16;//slideshow.getInfo().getXAspectRatio()
		currentYAspectRatio = 9;//slideshow.getInfo().getYAspectRatio()

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
		slideRenderer.drawSlide(currentSlide);
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

}
