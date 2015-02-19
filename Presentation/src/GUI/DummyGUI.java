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
		ImageHandler thisImageHandler = new ImageHandler(group);
		thisImageHandler.drawImage(100, 100, "file:me.png", 1, 1, 0, false, false);
		
		GraphicsHandler thisGraphicsHandler = new GraphicsHandler(group);
		thisGraphicsHandler.drawCircle(100, 100, 50, new Color(0, 0, 1, 1));
		thisGraphicsHandler.drawOval(200, 75, 300, 125, new Color(1,0,0,1), 0);
		thisGraphicsHandler.drawRectangle(350, 50, 30, 100, 20, 20, new Color(0,1,0,1));
		thisGraphicsHandler.drawPolygon(new Color(0,1,1,1),	new Double[] { 120.0, 270.0, 140.0, 310.0,
			110.0, 350.0, 160.0, 320.0		}, -50,-50);
		thisGraphicsHandler.drawRegularPolygon(80, 80, 220, 220, 5, new Color(0,0.5,0.8,1));
		thisGraphicsHandler.drawSquare(350, 200, 80, new Color(1,0,1,1));
		thisGraphicsHandler.drawLine(160, 160, 350, 165, new Color(1,0.2,0.5,1));
		
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
