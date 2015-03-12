package runtime;

import Data.Slideshow;
import XML.ImprovedXMLReader;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RuntimeDataTest extends Application {

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
		primaryStage.setTitle(this.getClass().getSimpleName());
		Group group = new Group();
		Scene scene = new Scene(group, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Slideshow slideshow = new ImprovedXMLReader("test.xml").getSlideshow(); 
		
		SlideshowRuntimeData slideshowRuntimeData = new SlideshowRuntimeData(slideshow, group);
	}

}
