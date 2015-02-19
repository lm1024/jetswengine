/**
 * 
 */
package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author
 * @version 1.0 19/02/2015
 * 
 */
public class GUI extends Application {

	/**
	 * 
	 */
	public GUI() {
	}

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
		/* TODO Jake, please make a nice GUI. You are our only hope. */
		
		/* Set the title of the window */
		primaryStage.setTitle("JavaFX Welcome");

		Group group = new Group();
		//group.setStyle("-fx-background-color: white;");

		/* Create new button */
		Button btn = makeButton(100, 100, "Button Text");
		btn.setId("button1"); // id set so that source of event can be found
		btn.getText();
		group.getChildren().add(btn);

		Scene scene = new Scene(group, 500, 500);

		primaryStage.setScene(scene);
		
		/* Line sets the screen to fullscreen */
		//primaryStage.setFullScreen(true);

		//thisGraphicsHandler.drawRectangle();
		
		primaryStage.show();
	}

	/** Utility function for adding button */
	private Button makeButton(double xPos, double yPos, String buttonText) {
		/* Button section */
		Button btn = new Button();
		btn.setText(buttonText);
		btn.relocate(400, 400);
		btn.setOnAction(new buttonEventHandler());
		return btn;
	}

	/**
	 * Private class written so that multiple buttonEvents can be handled
	 * easily.
	 */
	private class buttonEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Button buttonPressed = (Button) e.getSource();
			System.out.println(buttonPressed.getId());
		}
	}

}
