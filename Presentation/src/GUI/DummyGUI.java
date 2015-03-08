/**
 *
 */
package GUI;

import java.util.ArrayList;

import Data.TextFragment;
import sofia.VideoHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
		Scene scene = new Scene(group, 1600, 900);
		primaryStage.setScene(scene);
		TextHandler thisTextHandler = new TextHandler(group);
		
		TextFragmentList textFragmentList = new TextFragmentList();
		textFragmentList.add(TextBox.TextFragmentBuilder("Hello World "));
		textFragmentList.add(TextBox.TextFragmentBuilder("Hello World2 ").bold(true).fontName("Arial").fontSize(20).highlightColor("#ffaaffff"));
		textFragmentList.add(TextBox.TextFragmentBuilder("Hello World2 ").bold(true).fontName("Arial").fontSize(20).highlightColor("#ffaaffff"));
		
		textFragmentList.add(new TextBox.TextFragmentBuilder("Hello World "));
		
		thisTextHandler.drawTextTest(TextBox.TextBuilder(400, 400).textFragmentList(textFragmentList).build());

		primaryStage.show();
	}
}