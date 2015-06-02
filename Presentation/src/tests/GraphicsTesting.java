package tests;

import graphicsHandler.GraphicsHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicsTesting extends Application {

	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/* Create the SmartSlide Test Environment. */
		primaryStage.setTitle("SmartSlides Test Environment");
		Group group = new Group();
		Scene scene = new Scene(group, 900, 900);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		addAudio(group);
	}
	
	private void addAudio(Group group) {
		/* Instantiate a new handler. */
		GraphicsHandler graphicsHandler = new GraphicsHandler(group);
		
		/* Start testing here. */
	}
	
	

}
