package runtime;

import java.io.IOException;

import comms.CommsHandler;

import Data.Slideshow;
import XML.ImprovedXMLReader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
		Slideshow slideshow = new ImprovedXMLReader("test.xml").getSlideshow();
		
		CommsHandler comms = null;
		try {
			comms = new CommsHandler();

		} catch (IOException e) {
			System.out.println("Another instance of the program is running, quitting.");
			System.exit(0);
		}

		final SlideshowRuntimeData slideshowRuntimeData = new SlideshowRuntimeData(slideshow, 0, 0, comms, false);
		
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				slideshowRuntimeData.closeSlideshow();
			}
		});

		//primaryStage.show();

		
	}

}
