package renderer;

import graphicsHandler.Shadow;
import XML.ImprovedXMLReader;
import Data.Slideshow;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SlideRendererDemo extends Application {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Must must implement the inherited abstract method
	 * Application.start(Stage).
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		/* Set the title of the window */
		Slideshow slideshow = new ImprovedXMLReader("test.xml").getSlideshow();
		primaryStage.setTitle("Slide Renderer Demo");
		Group group = new Group();
		Scene scene = new Scene(group, 1000, 800);
		primaryStage.setScene(scene);
		
		SlideRenderer slideRenderer = new SlideRenderer(primaryStage);
		slideRenderer.updateSlideDimensions(0, 0, 1000, 800);
		slideRenderer.drawSlide(slideshow.getSlide(0));
		
		primaryStage.show();
	}
}
