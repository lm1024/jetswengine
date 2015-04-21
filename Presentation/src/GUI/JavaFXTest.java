/**
 * 
 */
package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * @author tjd511
 * 
 */
public class JavaFXTest extends Application {

<<<<<<< HEAD
	private Circle circle;
	
	private MediaView mediaView;
	
	private Stage stage;
	
	private ContextMenu contextMenu;
	
=======
>>>>>>> refs/heads/master
	/**
	 * 
	 */
	public JavaFXTest() {
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("JavaFX Welcome");
		
		this.stage = primaryStage;
		
		contextMenu = new ContextMenu();
		
		Group group = new Group();

		group.setStyle("-fx-background-color: white;");

		/* Graphics Section */
		Circle circle = new Circle(150, 20, 50);
		circle.relocate(250, 20);
		circle.setFill(new Color(0, 1, 0, 1)); // RGBa!!!!!

		Ellipse ellipse = new Ellipse(100, 150, 20, 30);
		// ellipse.setFill(new Color(1, 0.8, 1, 1));
		//ellipse.setStroke(new Color(1, 1, 1, 1));
		// ellipse.setStrokeWidth(100);

		RadialGradient gradient1 = new RadialGradient(0, .1, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, new Stop(0,
				Color.RED), new Stop(1, Color.BLACK)); // this one works for all
														// shapes (maybe)
		LinearGradient lg1 = new LinearGradient(0, 0, 0.5, .5, true, CycleMethod.NO_CYCLE,  new Stop(0, Color.BLACK), new Stop(1, Color.RED));
		ellipse.setFill(lg1);

		// final Circle circle2 = new Circle(300, 400, 20);
		// circle.setFill(gradient1);

		RadialGradient gradient3 = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, new Stop(0,
				Color.RED), new Stop(1, Color.BLACK)); // this one works for all
														// shapes (maybe)
		
		Rectangle rect = new Rectangle(150, 100, 50, 50);
		rect.setFill(gradient3);

		final Circle ball = new Circle(400, 400, 20);

		RadialGradient gradient2 = new RadialGradient(0, .1, 400, 400, 20, false, CycleMethod.NO_CYCLE, new Stop(0,
				Color.RED), new Stop(1, Color.BLACK));

		ball.setFill(gradient2);

		Ellipse ellipse2 = new Ellipse(800, 150, 20, 30);
		ellipse2.setFill(new Color(0, 0.8, 1, 1));
		ellipse2.setRotate(45);// ROTATES ABOUT THE CENTER YAY!

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5);
		dropShadow.setOffsetX(2.0);
		dropShadow.setOffsetY(2.0);

		ellipse2.setStroke(new Color(1, 1, 0, 1));
		ellipse2.getStrokeDashArray().addAll(2d);

		circle.setEffect(dropShadow);

		/* Video Section */
		Media media = new Media("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv");
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		// create mediaView and add media player to the viewer
		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.relocate(50, 50);
		// (scene.getRoot()).getChildren().add(mediaView);

		/* Image section! */
		Image image = new Image("file:me.png", 100, 100, true, true);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.relocate(200, 300);
		imageView.setEffect(new SepiaTone());

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		/* Text section */
		Text scenetitle = new Text(
				"Welcome Hello \nWorld Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, FontPosture.ITALIC, 20));
		scenetitle.relocate(150, 400);
		scenetitle.setStrikethrough(true);
		scenetitle.setUnderline(true);
		scenetitle.setWrappingWidth(primaryScreenBounds.getWidth() - 150);// screensize
																			// -
																			// xcoordinate
		scenetitle.setTextAlignment(TextAlignment.JUSTIFY);// or justify or
															// whatever

		Text string2 = new Text("Welcome");
		string2.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		string2.relocate(250, 450);

		Text string3 = new Text("Welcome");
		string3.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 20));
		string3.relocate(250, 280);
		/*
		 * It looks like superscript and subscript may have to be done
		 * manually... shame
		 */

		// EventHandler<ActionEvent> brian = new EventHandler<ActionEvent>();
		Button btn = addButton(400, 400);
		btn.setId("ali2");

		group.getChildren().addAll(mediaView, rect, ball, circle, ellipse, ellipse2, imageView, scenetitle, string2,
				string3, btn);
		
		/*for (int i=1; i<100; i++) {
			Rectangle thisCircle = new Rectangle(100+i/2f, 100+i/2f, 100-i, 100-i);
			if (i==50)
				thisCircle.setFill(new Color(0,1,0,1));
			else
				thisCircle.setFill(new Color(1-(i/100f),0,0,1));
			group.getChildren().add(thisCircle);
			
		}*/

		// primaryStage.setFullScreen(true);
		
		
		contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
		    public void handle(WindowEvent e) {
		        System.out.println("showing");
		    }
		});
		contextMenu.setOnShown(new EventHandler<WindowEvent>() {
		    public void handle(WindowEvent e) {
		        System.out.println("shown");
		    }
		});

		MenuItem item1 = new MenuItem("About");
		item1.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        System.out.println("About");
		    }
		});
		MenuItem item2 = new MenuItem("Preferences");
		item2.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        System.out.println("Preferences");
		    }
		});
		contextMenu.getItems().addAll(item1, item2);

		


		Scene scene = new Scene(group, 800, 800);// primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight());//500,
													// 575);
		
		scene.setOnMouseClicked(new MouseClickHandler());
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private class MouseClickHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent e) {
			contextMenu.show(stage, e.getScreenX(), e.getScreenY());
		}
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
			Button buttonPressed = (Button) e.getSource();
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
