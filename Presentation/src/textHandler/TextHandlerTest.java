/** (c) Copyright by WaveMedia. */
package textHandler;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TextHandlerTest extends Application {

	TextHandler textHandler;

	/**
*
*/
	public TextHandlerTest() {
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
		Scene scene = new Scene(group, 1600, 900);
		primaryStage.setScene(scene);

		textHandler = new TextHandler(group);

		TextFragmentList textFragList = new TextFragmentList();
				
		textFragList.add(new TextObject.TextFragmentBuilder("Hello World!").build());
		
		textHandler.createTextbox(new TextObject.TextBoxBuilder(100, 200).textFragmentList(textFragList).build());

		Button button1 = makeButton(200, 100, "1");
		Button button2 = makeButton(250, 100, "2");

		group.getChildren().addAll(button1, button2);

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
			if (buttonPressed.getText() == "1") {
				textHandler.setVisible(0, false);
			} else {
				textHandler.setVisible(0, true);
			}
		}
	}
}
