package runtime;

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
	
	private int slideNumber;

	public SlideshowRuntimeData(Slideshow slideshow, Group group) {
		this.slideshow = slideshow;

		currentSceneHeight = 16;//slideshow.getInfo().getXAspectRatio()
		currentSceneHeight = 9;//slideshow.getInfo().getYAspectRatio()

		this.slideRenderer = new SlideRenderer(group);
		this.scene = group.getScene();
		scene.setOnMouseClicked(new mouseClickHandler());	
		
		currentSlide = slideshow.getSlide(0);
		slideRenderer.drawSlide(currentSlide);
	}

	private void updateScreenBoundaries() {
		// screenBoundaries = Screen.get

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
