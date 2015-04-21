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
		/* Line sets the screen to fullscreen */
		// primaryStage.setFullScreen(true);

		/* Calls to add items to screen */
		/*ImageHandler thisImageHandler = new ImageHandler(group);
		thisImageHandler.drawImage(1100, 200, "file:me.png", 0.4, 0.4, 0, false, false, 0, 0, 0, 0,
				ImageEffect.REFLECTION, ImageEffect.SEPIA);

		GraphicsHandler thisGraphicsHandler = new GraphicsHandler(group);

		thisGraphicsHandler.drawOval(100f, 100f, 150f, 150f, "#ffff0000", true, "#ff0000ff", 1, Shadow.HEAVY, 0,
				Shading.CYCLIC, "#ffff00ff", "#ff00ffff", "#ffffffff", "#ff000000");

		thisGraphicsHandler.drawCircle(200f, 100f, 25f, "#ffff0000", true, "#ff0000ff", 1.0, Shadow.NONE, Shading.NONE,
				"#ffff00ff", "#ff00ffff", "#ffffffff", "#ff000000");

		thisGraphicsHandler.drawRectangle(300f, 100f, 400f, 150f, 0f, 0f, "#ffff0000", true, "#ff0000ff", 1,
				Shadow.LIGHT, 0, Shading.HORIZONTAL, "#ffff00ff", "#ff00ffff", "#ffffffff", "#ff000000");

		thisGraphicsHandler.drawSquare(450f, 100f, 50f, "#ffff0000", true, "#ff0000ff", 1.0, Shadow.NORMAL, 0,
				Shading.VERTICAL, "#ffff00ff", "#ff00ffff", "#ffffffff", "#ff000000");

		thisGraphicsHandler.drawSquare(550f, 100f, 50f, "#ffff0000", true, "#ff0000ff", 1.0, Shadow.NORMAL, 45,
				Shading.CYCLIC, "#ffff00ff", "#ff00ffff", "#ffffffff", "#ff000000");

		thisGraphicsHandler.drawLine(100f, 200f, 220f, 200f, "#ffff0000", 2, Shading.HORIZONTAL, "#ffff00ff",
				"#ff00ffff", "#ffffffff", "#ff000000");

		thisGraphicsHandler.drawLine(230f, 200f, 350f, 200f, "#ffff0000", 2, Shading.NONE, "#ffff00ff", "#ff00ffff",
				"#ffffffff", "#ff000000");

		thisGraphicsHandler.drawTriangle(400.0, 200, 450, 200, 425, 250, "#ffff0000", true, "#ff0000ff", 1.0,
				Shadow.NORMAL, 45, Shading.CYCLIC, "#ffff00ff", "#ff00ffff", "#ffffffff", "#ff000000");

		thisGraphicsHandler.drawSquare(220, 220, 80, "#ffff0000", true, "#ff0000ff", 0, Shadow.NONE, 0, Shading.NONE);

		thisGraphicsHandler.drawRegularPolygon(220, 220, 80, 80, 5, "#ffff0000", true, "#ff0000ff", 1.0, Shadow.NORMAL,
				0, Shading.HORIZONTAL, "#ffff00ff", "#ff00ffff", "#ffffffff", "#ff000000");

		for (int i = 50; i < 250; i = i + 25)
			for (int j = 300; j < 500; j = j + 25)
				thisGraphicsHandler.drawArrow(150, 400, i, j, "#ffff0000", Shading.HORIZONTAL, "#ffff00ff",
						"#ff00ffff", "#ffffffff", "#ff000000");

		thisGraphicsHandler.drawStar(300, 300, 8, 50, "#ffff0000", true, "#ff0000ff", 1.0, Shadow.NORMAL, 45,
				Shading.CYCLIC, "#ffff00ff", "#ff00ffff", "#ffffffff", "#ff000000");

		thisGraphicsHandler.drawPolygon(new Double[] { 120.0, 270.0, 140.0, 310.0, 110.0, 340.0, 160.0, 320.0 }, -50,
				-50, "#ffff0000", true, "#ff0000ff", 1.0, Shadow.NORMAL, 45, Shading.CYCLIC, "#ffff00ff", "#ff00ffff",
				"#ffffffff", "#ff000000");

		thisGraphicsHandler.drawChord(300, 400, 50, 50, 45, 200, "#ffff0000", true, "#ff0000ff", 1.0, Shadow.NORMAL,
				45, Shading.CYCLIC, "#ffff00ff", "#ff00ffff", "#ffffffff", "#ff000000");

		thisGraphicsHandler.drawArc(450, 400, 50, 50, 100, 100, "#ffff0000", true, "#ff0000ff", 1.0, Shadow.NORMAL, 45,
				Shading.CYCLIC, "#ffff00ff", "#ff00ffff", "#ffffffff", "#ff000000");
		
		Rectangle rect = new Rectangle(600, 500, 200, 200);
		group.getChildren().add(rect);
		
		String textBit = "There's a lady who's sure all that glitters is gold And she's buying a stairway to heaven. When she gets there she knows, if the stores are all closed With a word she can get what she came for. Ooh, ooh, and she's buying a stairway to heaven. There's a sign on the wall but she wants to be sure 'Cause you know sometimes words have two meanings. In a tree by the brook, there's a songbird who sings, Sometimes all of our thoughts are misgiven. Ooh, it makes me wonder, Ooh, it makes me wonder. There's a feeling I get when I look to the west, And my spirit is crying for leaving. In my thoughts I have seen rings of smoke through the trees, And the voices of those who stand looking. Ooh, it makes me wonder, Ooh, it really makes me wonder. And it's whispered that soon, if we all call the tune, Then the piper will lead us to reason. And a new day will dawn for those who stand long, And the forests will echo with laughter. If there's a bustle in your hedgerow, don't be alarmed now, It's just a spring clean for the May queen. Yes, there are two paths you can go by, but in the long run There's still time to change the road you're on. And it makes me wonder. Your head is humming and it won't go, in case you don't know, The piper's calling you to join him, Dear lady, can you hear the wind blow, and did you know Your stairway lies on the whispering wind? And as we wind on down the road Our shadows taller than our soul. There walks a lady we all know Who shines white light and wants to show How everything still turns to gold. And if you listen very hard The tune will come to you at last. When all are one and one is all To be a rock and not to roll. And she's buying a stairway to heaven.";
		TextHandler thisTextHandler = new TextHandler(group);
		thisTextHandler.addStringToBuffer(textBit, "Arial", 20, "#ff000000", "#00000000", true);
		thisTextHandler.drawBuffer(600, 20, 1000, 800, "#ac0bccdd", Alignment.LEFT);

		String []dataNames = new String[]{"All of the dicks", "None of the dicks"};
		Double []dataValues = new Double[]{99.0, 1.0};
		thisGraphicsHandler.drawPieChart(1700, 50, 1, "Probability of amount of dicks Ali sucks", dataNames, dataValues);
		
		String []dataNames2 = new String[]{"pac", "man"};
		Double []dataValues2 = new Double[]{85.0, 15.0};
		thisGraphicsHandler.drawPieChart(1700, 500, 1, "Pacman", dataNames2, dataValues2);
		
		VideoHandler thisVideoHandler = new VideoHandler(group);
		thisVideoHandler.createVideo(50, 700, 400, "M:/Sweng/avengers-featurehp.mp4", true, true);
		
		primaryStage.show();*/
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