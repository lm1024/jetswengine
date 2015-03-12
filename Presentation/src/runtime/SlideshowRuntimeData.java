package runtime;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import renderer.SlideRenderer;
import Data.Slide;
import Data.Slideshow;

public class SlideshowRuntimeData {

	private Slideshow slideshow;
	private Slide currentSlide;
	private SlideRenderer slideRenderer;

	private Rectangle2D screenBoundarys;

	private double currentSceneHeight;
	private double currentSceneWidth;

	public SlideshowRuntimeData(Slideshow slideshow, Group group) {
		this.slideshow = slideshow;

		//currentSceneHeight = slideshow.getInfo().getXAspectRatio()
		//currentSceneHeight = slideshow.getInfo().getYAspectRatio()

		this.slideRenderer = new SlideRenderer(group);
	}

	private void updateScreenBoundaries() {
		//screenBoundaries = Screen.get
				
	}

	private void updateSlide() {
		slideRenderer.clear();
		slideRenderer.drawSlide(currentSlide);
	}

}
