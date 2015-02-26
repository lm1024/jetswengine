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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Jake Feasey
 * @version 1.0 19/02/2015
 * 
 */
public class SettingsGUI extends Application {

	/* Create gridpane in which to put objects */
	GridPane grid = new GridPane();
	
	/* Global text area and array in which to save words */
	private TextArea ta = new TextArea();
	private String bannedWords[];
	private TextField userField = new TextField();//text field
	private boolean isShadow = false;


	/**
	 * 
	 */
	public SettingsGUI() {
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
		/* TODO Jake, please make a nice GUI. You are our only hope.
		 * TODO Javafx Ensemble
		 * TODO find a GOOD way to open more screens
		 * TODO	Find more settings options to add
		 *  */

		
		/* Set the title of the window */
		primaryStage.setTitle("SmartSlides Settings");
		
		// creates a scene within the stage of pixel size x by y
		Scene mainScene = new Scene(grid, 800, 500);
		mainScene.getStylesheets().add("file:resources/styles/style1.css");

		/* Set the layout as gridpane */
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
				
		
		/*
		 * 	Logo button and Title
		 */
		
		/* Wavemedia logo Home button */
		Button homeBtn = makeButton("", "invisiButton", true);
		homeBtn.setId("home"); // id set so that source of event can be found
		/* logo image for button */	
		Image logo = new Image("file:WM_logo_transparent.png", 200, 200, true, true);
		ImageView imageView = new ImageView();
		imageView.setImage(logo);
		homeBtn.setGraphic(imageView); //put image into button
		grid.add(homeBtn, 0, 0);

		/* Create a HBox to put title in */
		HBox titleBox = makeHBox("clearBox", Pos.CENTER, 10);	
		
		/* Add a label as a title */
		Label titleLabel = makeLabel("Settings", "title");
		
		/* Add Logo and Title to titleBox */
		titleBox.getChildren().addAll(titleLabel);
		grid.add(titleBox, 1, 0, 2, 1);
		
		
		/*
		 *	Checkbox options: 
		 */
		
		Label timLabel = makeLabel("Auto-Next:", "headding");
		
		/* Add check boxes */
		CheckBox cb1 = makeCheckBox("Slide Timer", "checkLight", "cb1",  false);	
		CheckBox cb2 = makeCheckBox("Object Timer", "checkLight", "cb2", false);
		
		/* Vbox to contain check boses */
		VBox vbox1 = makeVBox("clearBox", Pos.TOP_LEFT, 10);
		vbox1.getChildren().addAll(timLabel, cb1, cb2);
		grid.add(vbox1, 0, 2, 1, 2);
		
		
		/*
		 *	Username input 
		 */
		
		/* Add user name submission */
		VBox userBox = makeVBox("clearBox", Pos.TOP_CENTER, 5);//holding box
				
		Label userLabel =  makeLabel("Username:", "headding");//label
		
		/*Text field declared outside the main so can be accessed elsewhere */
		userField.getStyleClass().add("textArea");
		userField.setPromptText("Username");
		
		/* Add buttons and add to a box */
		Button userSubmit = makeButton("Submit", "darkButton", true);//submit button
		userSubmit.setId("submit");
		Button userClr = makeButton("Clear", "darkButton", true);
		userClr.setId("userClr");
		
		/* Hbox for username Buttons */
		HBox userButtons = makeHBox("clearBox", Pos.CENTER, 10);
		userButtons.getChildren().addAll(userSubmit, userClr);
		
		/* Add everything to the box */
		userBox.getChildren().addAll(userLabel, userField, userButtons);//add to box
		grid.add(userBox, 1, 2);//add box to grid

		/* VBox to put banned words title and text box in */
		VBox bannedBox = makeVBox("clearBox", Pos.CENTER, 10);
				
		/* Label for BannedWords box */
		Label textLabel = makeLabel("Banned Words:", "headding");

		/* Editable text area for banned words */
		/* Defined outside of main class */
		ta.setPrefRowCount(20);
		ta.setPrefColumnCount(20);
		ta.setPromptText("Banned Words Here");
		ta.getStyleClass().add("textArea");
		
		/* Add items to banned words VBox */
		bannedBox.getChildren().addAll(textLabel, ta);
		grid.add(bannedBox, 2, 2);
		
		/* HBox to put buttons controlling banned words in */
		HBox btnBox = new HBox();
		btnBox.getStyleClass().add("hbox");
		btnBox.setAlignment(Pos.TOP_CENTER);
		btnBox.setSpacing(5.0);

		/* Save and Clear buttons */
		Button btn = makeButton("Clear Text", "darkButton", true);
		btn.setId("clr"); // id set so that source of event can be found
				
		Button btn1 = makeButton("Save", "darkButton", true);
		btn1.setId("saveWords"); 
		
		/* Add buttons to box */
		btnBox.getChildren().addAll(btn, btn1);
		
		/* Add box to the grid*/
		grid.add(btnBox,2,3);
			
		
		primaryStage.setScene(mainScene);

		/* Line sets the screen to full screen */
		// primaryStage.setFullScreen(true);

		// thisGraphicsHandler.drawRectangle();

		primaryStage.show();
	}



	/** Utility function for adding VBox**/
	private VBox makeVBox(String style, Pos position, int space) {
		VBox vbox = new VBox();
		vbox.getStyleClass().add(style);
		vbox.setAlignment(position);
		vbox.setSpacing(space);
		return vbox;
	}
	
	/** Utility function for adding HBox**/
	private HBox makeHBox(String style, Pos position, int space) {
		HBox hbox = new HBox();
		hbox.getStyleClass().add(style);
		hbox.setSpacing(space);
		hbox.setAlignment(position);
		return hbox;
	}
	
	/** Utility function for adding a CheckBox **/
	private CheckBox makeCheckBox(String text, String style, String id, boolean selected) {
		CheckBox cb = new CheckBox();
		cb.setText(text);
		cb.setId(id);
		cb.setSelected(selected);
		cb.getStyleClass().add(style);
		cb.setOnAction(new checkEventHandler());
		return cb;
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
	
	/** Utility function for adding labels **/
	private Label makeLabel(String labelText, String styleClass){
		/* label section */
		Label lbl = new Label(labelText);
		lbl.getStyleClass().add(styleClass);
		return lbl;
	}

	/**
	 * Private class written so that multiple buttonEvents can be handled
	 * easily.
	 */
	private class buttonEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			/* Get event from action event and casting to type button */
			Button buttonPressed = (Button) e.getSource();
			
			/**/
			System.out.println(buttonPressed.getId() + " pressed");
			switch (buttonPressed.getId()) {
			case "clr":
				ta.clear();
				break;
			case "saveWords":
				bannedWords = ta.getText().split("\n");
				if(!bannedWords[0].isEmpty()){
					for(String string : bannedWords) {
						System.out.println(string);
					}
				}
				break;
			case "home" : System.out.println("Going home");
				break;
			case "submit" : System.out.println(userField.getText());
							Stage secondStage = new Stage();
							
							GridPane grid2 = new GridPane();
							Scene scene2 = new Scene(grid2, 800, 500);
							
							grid2.setPadding(new Insets(5, 5, 5, 5));
							grid2.setAlignment(Pos.CENTER);
							grid2.setHgap(10);
							grid2.setVgap(10);
							
							Image image = new Image("file:me.png", 100, 100, true, true);
							ImageView imageView = new ImageView();
							imageView.setImage(image);
							grid2.add(imageView, 0, 0);
							
							secondStage.setScene(scene2);
							secondStage.show();

				break;
			case "userClr" : userField.clear();
				break;
			default:
				System.out.println("error");
			}

		}
	}

	/**
	 * 
	 * private class so that checkButton Events can be handled
	 *
	 */
	private class checkEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {

			CheckBox cbSelected = (CheckBox) e.getSource();
			
			System.out.println(cbSelected.getId() + " was set to " + cbSelected.isSelected());

		}

	}
	
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

/*
// Radio buttons
final ToggleGroup group = new ToggleGroup();
RadioButton grouprb1 = new RadioButton("radioButton1");
grouprb1.setSelected(true);
grouprb1.setId("1");
grouprb1.setToggleGroup(group);

RadioButton grouprb2 = new RadioButton("radioButton2");
grouprb2.setId("2");
grouprb2.setToggleGroup(group);

RadioButton grouprb3 = new RadioButton("radioButton3");
grouprb3.setId("3");
grouprb3.setToggleGroup(group);

group.selectedToggleProperty().addListener(new OnToggleHandler());
*/
