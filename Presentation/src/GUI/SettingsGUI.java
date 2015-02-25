/**
 * 
 */
package GUI;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
		 * 		!!!Apply moar styleClass moar!!!!!
		 *  */

		
		/* Set the title of the window */
		primaryStage.setTitle("SmartSlides Settings");
		
		// creates a scene within the stage of pixel size x by y
		Scene mainScene = new Scene(grid, 700, 500);
		mainScene.getStylesheets().add("file:resources/styles/style1.css");

		/* Set the layout as gridpane */
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		
		/* Set grid columns to be a third of the page 
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(33);
		grid.getColumnConstraints().add(column1);
		*/
			
		/* Wavemedia logo Home button */
		Button homeBtn = makeButton("", "lightButton");
		homeBtn.setId("home"); // id set so that source of event can be found
		/* logo image for button */	
		Image logo = new Image("file:WM_logo_transparent.png", 200, 200, true, true);
		ImageView imageView = new ImageView();
		imageView.setImage(logo);
		homeBtn.setGraphic(imageView); //put image into button
		grid.add(homeBtn, 0, 0);

		/* Create a HBox to put title in */
		HBox titleBox = new HBox();
		titleBox.getStyleClass().add("vbox");
		titleBox.setSpacing(10);
		titleBox.setStyle("-fx-background-color: #F0F0F0;");
		titleBox.setAlignment(Pos.TOP_LEFT);		
		
		/* Add a label as a title */
		Label titleLabel = makeLabel("Settings", "titleLabel");
		
		/* Add Logo and Title to titleBox */
		titleBox.getChildren().addAll(titleLabel);
		grid.add(titleBox, 1, 0, 2, 1);
		
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
		
		/* Add check boxes */
		CheckBox cb1 = new CheckBox();
		cb1.setText("Auto-Next");
		cb1.setSelected(false);
		cb1.selectedProperty().addListener(new checkHandler());
		
		/* Vbox to contain check boses */
		VBox vbox1 = makeVBox("clearBox", Pos.TOP_LEFT, 0);
		vbox1.setPadding(new Insets(5,5,5,5));
		vbox1.getChildren().addAll(cb1);
		grid.add(vbox1, 0, 2, 1, 2);
		
		/*
		// A toggle button without any caption or icon
		Image image = new Image("file:logo.png", 100, 100, true, true);
		ToggleButton tb1 = new ToggleButton("", new ImageView(image));
		grid.add(tb1, 0, 1);
		*/
		
		/* Add user name submission */
		VBox userBox = makeVBox("clearBox", Pos.TOP_CENTER, 5);//holding box
		
		Label userLabel =  makeLabel("Username:", "headdings");//label
		
		/*Text field declared outside the main so can be accessed elsewhere */
		userField.getStyleClass().add("textArea");
		
		Button userButton = makeButton("Submit", "darkButton");//submit button
		userButton.setId("submit");
		userBox.getChildren().addAll(userLabel, userField, userButton);//add to box
		grid.add(userBox, 1, 2);//add box to grid
		
		/* VBox to put banned words title and text box in */
		VBox bannedBox = makeVBox("clearBox", Pos.CENTER, 10);
		
		/* Label for BannedWords box */
		Label textLabel = makeLabel("Banned Words:", "headdings");

		
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
		Button btn = makeButton("Clear Text", "darkButton");
		btn.setId("clr"); // id set so that source of event can be found
				
		Button btn1 = makeButton("Save", "darkButton");
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

	private VBox makeVBox(String string, Pos position, int space) {
		
		VBox vbox = new VBox();
		vbox.getStyleClass().add(string);
		vbox.setAlignment(position);
		vbox.setSpacing(space);
		return vbox;
	}

	/** Utility function for adding button */
	private Button makeButton(String buttonText, String styleClass) {
		/* Button section */
		Button btn = new Button();
		btn.setText(buttonText);
		btn.getStyleClass().add(styleClass);
		btn.setPrefHeight(10);
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
				break;
			default:
				System.out.println("error");
			}

		}
	}

	private class checkHandler implements ChangeListener<Boolean> {

		@Override
		public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {

			/*CheckBox cbSelected = (CheckBox) t1.getToggleGroup()
					.getSelectedToggle();*/

			System.out.println(new_val);

		}

	}

}
