package tests;

import sofia.video.VideoHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VideoTesting extends Application {

	
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
		
		addVideo(group, primaryStage);
	}
	
	private void addVideo(Group group, Stage stage) {
		/* Instantiate a new handler. */
		VideoHandler videoHandler = new VideoHandler(group, stage, new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/* Start testing here. */
		
		videoHandler.createVideo(100, 50, 600, "M:/SWEng/jetswengine2/Presentation/avengers-featurehp.mp4",false, true);

		
	}
	
	

}
