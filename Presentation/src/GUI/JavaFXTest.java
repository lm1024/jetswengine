/**
 * 
 */
package GUI;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
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
import javafx.scene.shape.Rectangle;
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

		Group grid = new Group();
		/*
		 * GridPane grid = new GridPane(); grid.setAlignment(Pos.CENTER);
		 * grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(25,
		 * 25, 25, 25));
		 * 
		 * Text scenetitle = new Text("Welcome");
		 * scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		 * grid.add(scenetitle, 0, 0, 2, 1);
		 * 
		 * Label userName = new Label("User Name:"); grid.add(userName, 0, 1);
		 * 
		 * TextField userTextField = new TextField(); grid.add(userTextField, 1,
		 * 1);
		 * 
		 * Label pw = new Label("Password:"); grid.add(pw, 0, 2);
		 * 
		 * PasswordField pwBox = new PasswordField(); grid.add(pwBox, 1, 2);
		 * 
		 * //grid.setGridLinesVisible(true);
		 * 
		 * Button btn = new Button("Sign in"); HBox hbBtn = new HBox(10);
		 * hbBtn.setAlignment(Pos.BOTTOM_RIGHT); hbBtn.getChildren().add(btn);
		 * grid.add(hbBtn, 1, 4);
		 * 
		 * final Text actiontarget = new Text(); grid.add(actiontarget, 1, 6);
		 * 
		 * btn.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent e) {
		 * actiontarget.setFill(Color.FIREBRICK);
		 * actiontarget.setText("Sign in button pressed"); } });
		 */

		// Scene scene = new Scene(grid, 300, 275);*/
		// Scene scene = new Scene(grid, 300, 275);
		// primaryStage.setScene(scene);

		grid.setStyle("-fx-background-color: white;");
		// grid.setPrefSize(200,200);
		/* Graphics Section */
		Circle circle = new Circle(50, Color.BLUE);
		circle.relocate(150, 20);
		circle.setFill(new Color(0, 1, 0, 1)); // RGBa!!!!!

		Ellipse ellipse = new Ellipse(100, 150, 20, 30);
		ellipse.setFill(new Color(0, 0.8, 1, 0.1));

		Ellipse ellipse2 = new Ellipse(200, 250, 20, 30);
		ellipse2.setFill(new Color(0, 0.8, 1, 1));
		ellipse2.setRotate(90);

		ellipse.setEffect(new DropShadow());
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		System.out.println(primaryStage.getMaxHeight());
		System.out.println(primaryScreenBounds.getHeight());
		Scene scene = new Scene(grid, primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight());//500, 575);

		/* Video Section */
		Media media = new Media(
				"http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv");
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		// create mediaView and add media player to the viewer
		MediaView mediaView = new MediaView(mediaPlayer);
		((Group) scene.getRoot()).getChildren().add(mediaView);

		/* Image section! */
		Image image = new Image("file:me.png", 100, 100, true, true);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.relocate(200, 300);
		imageView.setEffect(new SepiaTone());
		
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
		string2.relocate(250, 250);
		
		Text string3 = new Text("Welcome");
		string3.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 20));
		string3.relocate(250, 280);
		/* It looks like superscript and subscript may have to be done manually... shame*/
		
		grid.getChildren().addAll(circle, ellipse, ellipse2, imageView, scenetitle, string2, string3);
		
		// Scene scene = new Scene(grid, 300, 275);
		
		primaryStage.setFullScreen(true);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		launch(args);
	}

}
