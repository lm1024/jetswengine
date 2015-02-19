/**
 * 
 */
package GUI;

import java.awt.Insets;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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

		//Group group = new Group();
		//group.setStyle("-fx-background-color: red;");
		
		//create a grid pane
		GridPane grid = new GridPane();

		/* Create new button */
		Button btn = makeButton(100, 100, "Button Text");
		btn.setId("button1"); // id set so that source of event can be found
		btn.getText();
		grid.add(btn, 0, 0);
		
		/* Create new button */
		Button btn1 = makeButton(200, 200, "Another Button");
		btn1.setId("button2"); // id set so that source of event can be found
		btn1.getText();
		grid.add(btn1, 0, 1);
		

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		//grid.setPadding(new Insets(25, 25, 25, 25));


		//creates a scene within the stage of pixel size x by y
		Scene mainScene = new Scene(grid, 400, 500);

		primaryStage.setScene(mainScene);
		
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
		btn.relocate(xPos, yPos);
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
			
			switch(buttonPressed.getId()) {
				case "button1" : System.out.println("button1 pressed");
					break;
				case "button2" : System.out.println("button2 pressed");
					break;
					default: System.out.println("error");
			}
			
		}
	}

}
