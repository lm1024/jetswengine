/**
 * 
 */
package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
        //grid.setAlignment(Pos.CENTER);
        //grid.setHgap(10);
        //grid.setVgap(10);
        //grid.setPadding(new Insets(25, 25, 25, 25));
        
        /*Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        //grid.setGridLinesVisible(true);
        
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in button pressed");
            }
        });

        Scene scene = new Scene(grid, 300, 275);*/
        //Scene scene = new Scene(grid, 300, 275);
        //primaryStage.setScene(scene);
        
        grid.setStyle("-fx-background-color: white;");
        //grid.setPrefSize(200,200);
        Circle circle = new Circle(50,Color.BLUE);
        circle.relocate(150, 20);
        circle.setFill(new Color(0, 1, 0, 1)); //RGBa!!!!!
        
        Ellipse ellipse = new Ellipse(100, 100, 20, 30);
        ellipse.setFill(new Color(0, 0.8, 1, 1));

        
        
        Scene scene = new Scene(grid, 300, 275);
        
        Media media = new Media("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
     // create mediaView and add media player to the viewer
        MediaView mediaView = new MediaView(mediaPlayer);
        ((Group)scene.getRoot()).getChildren().add(mediaView);
        grid.getChildren().addAll(circle, ellipse);
        
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
