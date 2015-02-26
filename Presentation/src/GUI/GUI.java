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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * @author
 * @version 1.0 19/02/2015
 * 
 */
public class GUI extends Application {

	/**
	 * 
	 */
	
	
	public GUI() {
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
		/* TODO Jake, please make a nice GUI. You are our only hope. */
		
		/* Set the title of the window */
		primaryStage.setTitle("SmartSlides");
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(650);

		
		//create a grid pane
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5,5,5,5));
		grid.setStyle("-fx-background-color: white;");
		grid.setPrefSize(600, 560);
		grid.setAlignment(Pos.CENTER);
		
		
		//creates a scene within the stage of pixel size x by y
		Scene mainScene = new Scene(grid, 900, 900);
		mainScene.getStylesheets().add("file:resources/styles/style1.css");

		
		//Company icon in column 1, row 1-3
		Image companylogo = new Image("file:logo.png", 130, 130, true, true);
		ImageView companyimage = new ImageView();
		companyimage.setImage(companylogo);
		grid.add(companyimage, 0, 0, 1, 3);
		
		
		//Product name in column 2-4, row 3
		Label productName =  makeLabel("SmartSlides", "titleLabel");
		//Text productName = new Text("SmartSlides");
		//productName.setFont(Font.font("Garamond", FontWeight.BOLD, 35));
		grid.add(productName, 1, 2, 3, 1);
	
		//Create first button for Slide Preview and add in column 1, row 4
		Image buttonimage1 = new Image("file:me.png", 130, 150, true, true);
		ImageView presentationimage1 = new ImageView();
		presentationimage1.setImage(buttonimage1);
		Button one = makeButton("Presentation 1", "button");
		one.setGraphic(presentationimage1);
		one.setStyle("-fx-background-color: white;");
		one.setContentDisplay(ContentDisplay.TOP);
		one.setId("Presentation 1");
		grid.add(one, 0, 3, 1, 1);
		
		//Insert blank in column 2, row 4-6
		Pane emptycell = new Pane();
		emptycell.setStyle("-fx-background-color: white;");
		emptycell.setMinWidth(40);
		grid.add(emptycell, 1, 3, 1, 3);
		
		//Create second button for Slide Preview and add in column 3, row 4
		Image buttonimage2 = new Image("file:me.png", 130, 150, true, true);
		ImageView presentationimage2 = new ImageView();
		presentationimage2.setImage(buttonimage2);
		Button two = makeButton("Presentation 2", "button");
		two.setGraphic(presentationimage2);
		two.setStyle("-fx-background-color: white;");
		two.setContentDisplay(ContentDisplay.TOP);
		two.setId("Presentation 2");
		grid.add(two, 2, 3, 1, 1);
		
		//Insert blank in column 4, row 4-6
		Pane emptycell1 = new Pane();
		emptycell1.setStyle("-fx-background-color: white;");
		emptycell1.setMinWidth(40);
		grid.add(emptycell1, 3, 3, 1, 3);
				
		//Create third button for Slide Preview and add in column 5, row 4
		Image buttonimage3 = new Image("file:me.png", 130, 150, true, true);
		ImageView presentationimage3 = new ImageView();
		presentationimage3.setImage(buttonimage3);
		Button three = makeButton("Presentation 3", "button");
		three.setGraphic(presentationimage3);
		three.setStyle("-fx-background-color: white;");
		three.setContentDisplay(ContentDisplay.TOP);
		three.setId("Presentation 3");
		grid.add(three, 4, 3, 1, 1);
		
		//Insert blank in row 5
		Pane emptyrow = new Pane();
		emptyrow.setStyle("-fx-background-color: white;");
		emptyrow.setMinHeight(20);
		grid.add(emptyrow, 0, 4, 5, 1);
		
		//Create forth button for Slide Preview and add in column 1, row 6
		Image buttonimage4 = new Image("file:me.png", 130, 150, true, true);
		ImageView presentationimage4 = new ImageView();
		presentationimage4.setImage(buttonimage4);
		Button four = makeButton("Presentation 4", "button");
		four.setGraphic(presentationimage4);
		four.setStyle("-fx-background-color: white;");
		four.setContentDisplay(ContentDisplay.TOP);
		four.setId("Presentation 4");
		grid.add(four, 0, 5, 1, 1);
		
		//Create fifth button for Slide Preview and add in column 3, row 6
		Image buttonimage5 = new Image("file:me.png", 130, 150, true, true);
		ImageView presentationimage5 = new ImageView();
		presentationimage5.setImage(buttonimage5);
		Button five = makeButton("Presentation 5", "button");
		five.setGraphic(presentationimage5);
		five.setStyle("-fx-background-color: white;");
		five.setContentDisplay(ContentDisplay.TOP);
		five.setId("Presentation 5");
		grid.add(five, 2, 5, 1, 1);
		
		//Create sixth button for Slide Preview and add in column 5, row 6
		Image buttonimage6 = new Image("file:me.png", 130, 150, true, true);
		ImageView presentationimage6 = new ImageView();
		presentationimage6.setImage(buttonimage6);
		Button six = makeButton("Presentation 6", "button");
		six.setGraphic(presentationimage6);
		six.setStyle("-fx-background-color: white;");
		six.setContentDisplay(ContentDisplay.TOP);
		six.setId("Presentation 6");
		grid.add(six, 4, 5, 1, 1);
		
		//Create Openfile button in column 2-3, row 7
		Button openfile = makeButton("Openfile", "button");
		openfile.setId("Openfile");
		//Button openfile = new makeButton("OpenFile", "button");
		grid.add(openfile, 1, 6, 2, 1);
		
		//Create Settings button in column 4-5, row 7
		Button settings = makeButton("Settings", "button");
		settings.setId("Settings");
		grid.add(settings, 3, 6, 2, 1);
		
		primaryStage.setScene(mainScene);
		
		/* Line sets the screen to fullscreen */
		//primaryStage.setFullScreen(true);

		//thisGraphicsHandler.drawRectangle();
		
		primaryStage.show();
	}
	
	/** Utility function for adding labels **/
	private Label makeLabel(String labelText, String styleClass){
		/* label section */
		Label lbl = new Label(labelText);
		lbl.getStyleClass().add(styleClass);
		return lbl;
	}
	
	/** Utility function for adding button */
	private Button makeButton(String buttonText, String styleClass) {
		/* Button section */
		Button btn = new Button();
		btn.setText(buttonText);
		btn.getStyleClass().add(styleClass);
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
			
			System.out.println(buttonPressed.getId() + " pressed");
			
		}
	}

}
