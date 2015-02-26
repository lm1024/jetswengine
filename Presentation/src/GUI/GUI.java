/**
 * 
 */
package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
	private boolean isShadow = false;
	
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

		
		//create a gridpane layout
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5,5,5,5));
		grid.setPrefSize(600, 560);
		grid.setAlignment(Pos.CENTER);
		
		
		//creates a scene within the stage of pixel size x by y
		Scene mainScene = new Scene(grid, 900, 900);
		mainScene.getStylesheets().add("file:resources/styles/style1.css");

		
		//Company icon in column 1-3, row 1-3
		//Image companylogo = new Image("file:WM_logo_transparent.png", 225, 225, true, true);
		//ImageView companyimage = new ImageView();
		//companyimage.setImage(companylogo);
		ImageView companyimage = getimage("file:WM_logo_transparent.png", 225, 225);
		grid.add(companyimage, 0, 0, 3, 3);
		
		
		//Product name in column 2-5, row 3
		Label productName =  makeLabel("     SmartSlides", "title");
		grid.add(productName, 1, 2, 4, 1);
	
		//Create first button for Slide Preview and add in column 1, row 4
		//Image buttonimage1 = new Image("file:me.png", 130, 150, true, true);
		//ImageView presentationimage1 = new ImageView();
		//presentationimage1.setImage(buttonimage1);
		ImageView presentationimage1 = getimage("file:me.png", 130, 150);
		Button one = makeButton("Presentation 1", "invisiButton", true);
		one.setStyle("-fx-border-width: 0;");
		one.setGraphic(presentationimage1);
		one.setContentDisplay(ContentDisplay.TOP);
		one.setId("Presentation 1");
		grid.add(one, 0, 3, 1, 1);
		
		//Insert blank in column 2, row 4-6
		Pane emptycell = new Pane();
		emptycell.setStyle("-fx-background-color: #F0F0F0;");
		emptycell.setMinWidth(40);
		grid.add(emptycell, 1, 3, 1, 3);
		
		//Create second button for Slide Preview and add in column 3, row 4
		//Image buttonimage2 = new Image("file:me.png", 130, 150, true, true);
		//ImageView presentationimage2 = new ImageView();
		//presentationimage2.setImage(buttonimage2);
		ImageView presentationimage2 = getimage("file:me.png", 130, 150);
		Button two = makeButton("Presentation 2", "invisiButton", true);
		two.setStyle("-fx-border-width: 0;");
		two.setGraphic(presentationimage2);
		two.setContentDisplay(ContentDisplay.TOP);
		two.setId("Presentation 2");
		grid.add(two, 2, 3, 1, 1);
		
		//Insert blank in column 4, row 4-6
		Pane emptycell1 = new Pane();
		emptycell1.setStyle("-fx-background-color: #F0F0F0;");
		emptycell1.setMinWidth(40);
		grid.add(emptycell1, 3, 3, 1, 3);
				
		//Create third button for Slide Preview and add in column 5, row 4
		//Image buttonimage3 = new Image("file:me.png", 130, 150, true, true);
		//ImageView presentationimage3 = new ImageView();
		//presentationimage3.setImage(buttonimage3);
		ImageView presentationimage3 = getimage("file:me.png", 130, 150);
		Button three = makeButton("Presentation 3", "invisiButton", true);
		three.setGraphic(presentationimage3);
		three.setStyle("-fx-border-width: 0;");
		three.setContentDisplay(ContentDisplay.TOP);
		three.setId("Presentation 3");
		grid.add(three, 4, 3, 1, 1);
		
		//Insert blank in row 5
		Pane emptyrow = new Pane();
		emptyrow.setStyle("-fx-background-color: #F0F0F0;");
		emptyrow.setMinHeight(20);
		grid.add(emptyrow, 0, 4, 5, 1);
		
		//Create forth button for Slide Preview and add in column 1, row 6
		//Image buttonimage4 = new Image("file:me.png", 130, 150, true, true);
		//ImageView presentationimage4 = new ImageView();
		//presentationimage4.setImage(buttonimage4);
		ImageView presentationimage4 = getimage("file:me.png", 130, 150);
		Button four = makeButton("Presentation 4", "invisiButton", true);
		four.setGraphic(presentationimage4);
		four.setStyle("-fx-border-width: 0;");
		four.setContentDisplay(ContentDisplay.TOP);
		four.setId("Presentation 4");
		grid.add(four, 0, 5, 1, 1);
		
		//Create fifth button for Slide Preview and add in column 3, row 6
		//Image buttonimage5 = new Image("file:me.png", 130, 150, true, true);
		//ImageView presentationimage5 = new ImageView();
		//presentationimage5.setImage(buttonimage5);
		ImageView presentationimage5 = getimage("file:me.png", 130, 150);
		Button five = makeButton("Presentation 5", "invisiButton", true);
		five.setGraphic(presentationimage5);
		five.setStyle("-fx-border-width: 0;");
		five.setContentDisplay(ContentDisplay.TOP);
		five.setId("Presentation 5");
		grid.add(five, 2, 5, 1, 1);
		
		//Create sixth button for Slide Preview and add in column 5, row 6
		//Image buttonimage6 = new Image("file:me.png", 130, 150, true, true);
		//ImageView presentationimage6 = new ImageView();
		//presentationimage6.setImage(buttonimage6);
		ImageView presentationimage6 = getimage("file:me.png", 130, 150);
		Button six = makeButton("Presentation 6", "invisiButton", true);
		six.setGraphic(presentationimage6);
		six.setStyle("-fx-border-width: 0;");
		six.setContentDisplay(ContentDisplay.TOP);
		six.setId("Presentation 6");
		grid.add(six, 4, 5, 1, 1);
		
		//Create Openfile button in column 2-3, row 7
		Button openfile = makeButton("Openfile", "darkButton", true);
		openfile.setId("Openfile");
		grid.add(openfile, 1, 6, 2, 1);
		
		//Create Settings button in column 4-5, row 7
		Button settings = makeButton("Settings", "darkButton", true);
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
	
	/** Utility function for getting images **/
	private ImageView getimage(String imagefilename, double width, double height){
		/* image section */
		Image image = new Image(imagefilename, width, height, true, true);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		return imageView;
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
	
	/** Utility function for adding button */
	private Button makeButton(String buttonText, String styleClass, boolean hover) {
		/* Button section */
		Button btn = new Button();
		btn.setText(buttonText);
		btn.getStyleClass().add(styleClass);
		btn.setPrefHeight(10);
		if(hover){
			btn.setOnMouseEntered(new hoverHandler());
			btn.setOnMouseExited(new hoverHandler());
		}
		btn.setOnAction(new buttonEventHandler());
		return btn;
	}
	
	/**
	 * 
	 * Handler that applies and removes shading when mouse is hovering over a
	 * button
	 * 
	 */
	private class hoverHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent e) {
			
			Button btn = (Button) e.getSource();
			DropShadow shadow = new DropShadow();
			shadow.setColor(Color.web("#33B5E5"));
					
			if(!isShadow){
				isShadow = true;
				btn.setEffect(shadow);
			}
			else{
				isShadow = false;
				btn.setEffect(null);
			}
			
		}
		
		
	}

}
