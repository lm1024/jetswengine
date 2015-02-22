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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author
 * @version 1.0 19/02/2015
 * 
 */
public class DummyGUI extends Application {

	/**
	 * 
	 */
	public DummyGUI() {
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
		
		/* Calls to add items to screen */
		//ImageHandler thisImageHandler = new ImageHandler(group);
		//thisImageHandler.drawImage(100, 100, "file:me.png", 1, 1, 0, false, false);
		
		//GraphicsHandler thisGraphicsHandler = new GraphicsHandler(group);
		//thisGraphicsHandler.drawOval(200, 200, 300, 350, new Color(0, 0, 1, 1), 0);
		//thisGraphicsHandler.drawRectangle();
		
		TextHandler thisTextHandler = new TextHandler(group);
		thisTextHandler.drawString("Number 1", 200f, 100f, "Arial", 20, new Color(0,0,1,1), true, true, true, true, false, false, TextCase.CAPITALISED, Alignment.RIGHT);
		thisTextHandler.drawString("number 2 ", 200f, 140f, "Calibri", 20, new Color(0,1,1,1), true, true, true, true, false, false, TextCase.CAPITALISED, Alignment.RIGHT);
		thisTextHandler.drawString("NUMBER 3 ", 200f, 180f, "calibri", 20, new Color(1,0,1,1), true, true, true, true, false, false, TextCase.CAPITALISED, Alignment.RIGHT);
		thisTextHandler.drawString("Number 3 ", 200f, 220f, "Trebuchet MS", 20, new Color(0,0,1,1), true, true, true, true, false, false, TextCase.CAPITALISED, Alignment.RIGHT);
		thisTextHandler.drawString("Number 3 ", 200f, 260f, "Times New Roman", 20, new Color(1,1,0,1), true, true, true, true, false, false, TextCase.CAPITALISED, Alignment.RIGHT);

		thisTextHandler.addStringToBuffer("Number 1 ", "Calibri", 20, "ff0000ff", true, false, false, true, false, false, TextCase.LOWER, "ffaa00ff");
		thisTextHandler.addStringToBuffer("Number 2 ", "Webdings", 1, "ff00ff00", false, true, false, true, true, false, TextCase.LOWER, "ffaa00ff");
		thisTextHandler.addStringToBuffer("Number 3 ", "arial", 20, "ffff0000", false, false, true, true, false, false, TextCase.UPPER, "ffaa00ff");
		thisTextHandler.addStringToBuffer("Number 4 ", "arial", 20, "fff0f0f0", true, false, true, false, false, true, TextCase.UPPER, "ffaa00ff");
		thisTextHandler.addStringToBuffer("Number 5 ", "arial", 20, "ff0f0f0f", true, true, true, false, false, false, TextCase.CAPITALISED, "ffaa00ff");
		thisTextHandler.addStringToBuffer("Number 6 ", "Verdana", 20, "11000000", true, true, true, false, true, true, TextCase.CAPITALISED, "ffaa00ff");
		//thisTextHandler.printBuffer();
		
		thisTextHandler.drawBuffer(0, 0, 800, 200, "aabbccdd", Alignment.CENTER);
		
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
