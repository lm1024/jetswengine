/**
 * 
 */
package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * @author tjd511
 * 
 */
public class JavaFXTest extends Application {

	/**
	 * 
	 */
	public JavaFXTest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("JavaFX Welcome");

		Group group = new Group();
		
		group.setStyle("-fx-background-color: white;");

		/* Graphics Section */
		Circle circle = new Circle(50, Color.BLUE);
		circle.relocate(150, 20);
		circle.setFill(new Color(0, 1, 0, 1)); // RGBa!!!!!

		Ellipse ellipse = new Ellipse(100, 150, 20, 30);
		ellipse.setFill(new Color(1, 0.8, 1, 1));

		Ellipse ellipse2 = new Ellipse(100, 150, 20, 30);
		ellipse2.setFill(new Color(0, 0.8, 1, 1));
		ellipse2.setRotate(45);// ROTATES ABOUT THE CENTER YAY!

		ellipse2.setEffect(new DropShadow());

		/* Video Section */
		Media media = new Media(
				"http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv");
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		// create mediaView and add media player to the viewer
		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.relocate(50, 50);
		//(scene.getRoot()).getChildren().add(mediaView);

		/* Image section! */
		Image image = new Image("file:me.png", 100, 100, true, true);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.relocate(200, 300);
		imageView.setEffect(new SepiaTone());
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		
		/* Text section */
		Text scenetitle = new Text("Welcome Hello \nWorld Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, FontPosture.ITALIC, 20));
		scenetitle.relocate(150, 400);
		scenetitle.setStrikethrough(true);
		scenetitle.setUnderline(true);
		scenetitle.setWrappingWidth(primaryScreenBounds.getWidth()-150);//screensize - xcoordinate
		scenetitle.setTextAlignment(TextAlignment.JUSTIFY);// or justify or whatever
		
		Text string2 = new Text("Welcome");
		string2.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		string2.relocate(250, 450);
		
		Text string3 = new Text("Welcome");
		string3.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 20));
		string3.relocate(250, 280);
		/* It looks like superscript and subscript may have to be done manually... shame*/
		
		//EventHandler<ActionEvent> brian = new EventHandler<ActionEvent>();
		Button btn = addButton(400, 400);
		btn.setId("ali2");
		
		group.getChildren().addAll(mediaView, circle, ellipse, ellipse2, imageView, scenetitle, string2, string3, btn);
		
		primaryStage.setFullScreen(true);
		
		
		Scene scene = new Scene(group, primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight());//500, 575);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private Button addButton(double xPos, double yPos) {
		/* Button section */
		Button btn = new Button();
		btn.setText("Hello Ali2!");
		btn.relocate(400, 400);
		btn.setOnAction(new buttonEventHandler());
		return btn;
	}
	
	public class buttonEventHandler implements EventHandler<ActionEvent> {
		@Override 
		public void handle(ActionEvent e) {
			Button buttonPressed = (Button)e.getSource();
			System.out.println(buttonPressed.getId());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		launch(args);
	}

}
