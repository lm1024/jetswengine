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
import javafx.scene.text.Font;
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
		// group.setStyle("-fx-background-color: white;");

		/* Create new button */
		Button btn = makeButton(100, 100, "Button Text");
		btn.setId("button1"); // id set so that source of event can be found
		btn.getText();
		group.getChildren().add(btn);

		Scene scene = new Scene(group, 500, 500);

		primaryStage.setScene(scene);

		/* Line sets the screen to fullscreen */
		// primaryStage.setFullScreen(true);

		/* Calls to add items to screen */
		// ImageHandler thisImageHandler = new ImageHandler(group);
		// thisImageHandler.drawImage(100, 100, "file:me.png", 1, 1, 0, false,
		// false);
		GraphicsHandler thisGraphicsHandler = new GraphicsHandler(group);
		thisGraphicsHandler.drawOval(200, 200, 300, 350, false, new Color(0, 0, 1, 1));
		thisGraphicsHandler.drawOval(100, 200, 200, 350, true, new Color(0, 0, 1, 1));
		// thisGraphicsHandler.drawRectangle();
		TextHandler thisTextHandler = new TextHandler(group);
		//System.out.println(thisTextHandler.getClass().getSimpleName());
		thisTextHandler.addStringToBuffer("dsadsafas 1 ", Font.getDefault().getName(), 20, "#ff0000ff", "#ffaa00ff",
				TextAttribute.BOLD);
		
		String textBit = "There's a lady who's sure all that glitters is gold And she's buying a stairway to heaven. When she gets there she knows, if the stores are all closed With a word she can get what she came for. Ooh, ooh, and she's buying a stairway to heaven. There's a sign on the wall but she wants to be sure 'Cause you know sometimes words have two meanings. In a tree by the brook, there's a songbird who sings, Sometimes all of our thoughts are misgiven. Ooh, it makes me wonder, Ooh, it makes me wonder. There's a feeling I get when I look to the west, And my spirit is crying for leaving. In my thoughts I have seen rings of smoke through the trees, And the voices of those who stand looking. Ooh, it makes me wonder, Ooh, it really makes me wonder. And it's whispered that soon, if we all call the tune, Then the piper will lead us to reason. And a new day will dawn for those who stand long, And the forests will echo with laughter. If there's a bustle in your hedgerow, don't be alarmed now, It's just a spring clean for the May queen. Yes, there are two paths you can go by, but in the long run There's still time to change the road you're on. And it makes me wonder. Your head is humming and it won't go, in case you don't know, The piper's calling you to join him, Dear lady, can you hear the wind blow, and did you know Your stairway lies on the whispering wind? And as we wind on down the road Our shadows taller than our soul. There walks a lady we all know Who shines white light and wants to show How everything still turns to gold. And if you listen very hard The tune will come to you at last. When all are one and one is all To be a rock and not to roll. And she's buying a stairway to heaven.";
		
		thisTextHandler.drawString("Number 7 ", 200, 200, "Arial", 20, "#ffaabbcc", "#00000000");
		thisTextHandler.drawString("Hello", 300, 200);

		thisTextHandler.addStringToBuffer("Number 1 ", Font.getDefault().getName(), 20, "#ff0000ff", "#ffaa00ff",
				TextAttribute.BOLD);
		thisTextHandler.addStringToBuffer("Number 2 ", "Calibri", 20, "#ff00ff00", "#ffaa00ff",
				TextAttribute.BOLD, TextAttribute.ITALIC, TextAttribute.UNDERLINE, TextAttribute.STRIKETHROUGH,
				TextAttribute.SUPERSCRIPT, TextAttribute.SUBSCRIPT);
		thisTextHandler.addStringToBuffer("Number 3 ", "arial", 20, "#fffff000", "#ffaa00ff", TextAttribute.ITALIC);
		thisTextHandler.addStringToBuffer("Number 4 ", "Corbel", 20, "#fff0f0f0", "#ffaa00ff");
		thisTextHandler.addStringToBuffer("Number 5 ", "arial", 20, "#ff0f0f0f", "#ffaa00ff");
		thisTextHandler.addStringToBuffer("Number 6 ", "Verdana", 20, "#11000000", "#ffaa00ff");
		
		thisTextHandler.addStringToBuffer(textBit, "Arial", 20, "#ff000000", "#00000000");
		// thisTextHandler.printBuffer();

		thisTextHandler.drawBuffer(20, 800, 600, 1200, "#ac0bccdd", Alignment.LEFT);

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
