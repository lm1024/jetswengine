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

	/* Global text area and array in which to save words */
	private TextArea ta = new TextArea();
	private String bannedWords[];
	/* Create gridpane in which to put objects */
	GridPane grid = new GridPane();

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
		grid.setStyle("-fx-background-color: #F0F0F0;");
		
		/* Set grid columns to be a third of the page 
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(33);
		grid.getColumnConstraints().add(column1);
		*/
			
		/* Wavemedia logo Home button */
		Button homeBtn = makeButton(100, 100, "");
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
		titleBox.setAlignment(Pos.CENTER);		
		
		/* Use a label to create a title for screen */
		Label titleLabel = makeLabel("Settings", "titleStyle");
		//titleLabel.getStyleClass().add("titleStyle");
		/* load custom font  */
		//titleLabel.setFont(Font.loadFont("file:resources/fonts/Roboto-Bold.ttf", 120));
		
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
		VBox vbox1 = new VBox();
		vbox1.getStyleClass().add("vbox");
		vbox1.setAlignment(Pos.TOP_LEFT);
		vbox1.setPadding(new Insets(5,5,5,5));
		vbox1.getChildren().addAll(cb1);
		grid.add(vbox1, 0, 2, 1, 2);
		
		/*
		// A toggle button without any caption or icon
		Image image = new Image("file:logo.png", 100, 100, true, true);
		ToggleButton tb1 = new ToggleButton("", new ImageView(image));
		grid.add(tb1, 0, 1);
		*/

		/* Add user name text field with label */
		Label userLabel =  makeLabel("Username:", "headdings");
		//userLabel.setFont(Font.loadFont("file:resources/fonts/Roboto-Bold.ttf", 120));
		//userLabel.setTextFill(Color.web("#33B5E5"));
		grid.add(userLabel, 1, 2);
		
		/* VBox to put banned words title and text box in */
		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		vbox.setSpacing(10);
		vbox.setStyle("-fx-background-color: #F0F0F0;");
		vbox.setAlignment(Pos.CENTER);
		
		/* Label for BannedWords box */
		Label textLabel = makeLabel("Banned Words:", "headdings");
//		textLabel.setTextFill(Color.web("#313131"));
//		textLabel.setFont(Font.loadFont("file:resources/fonts/Roboto-Medium.ttf", 120));
//		textLabel.setStyle("-fx-font-size: 15pt;");
		
		/* Editable text area for banned words */
		ta.setPrefRowCount(20);
		ta.setPrefColumnCount(20);
		ta.setPromptText("Banned Words Here");
		ta.getStyleClass().add("textArea");
		
		/* Add items to banned words VBox */
		vbox.getChildren().addAll(textLabel, ta);
		grid.add(vbox, 2, 2);
		
		/* HBox to put buttons controlling banned words in */
		HBox btnBox = new HBox();
		btnBox.getStyleClass().add("hbox");
		btnBox.setAlignment(Pos.TOP_CENTER);
		btnBox.setSpacing(5.0);

		/* Save and Clear buttons */
		Button btn = makeButton("Clear Text", "button");
		btn.setId("clr"); // id set so that source of event can be found
		btn.getText();
				
		Button btn1 = makeButton("Save", "button");
		btn1.setId("saveWords"); 
		btn1.getText();
		
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

	/** Utility function for adding button */
	private Button makeButton(String buttonText, String styleClass) {
		/* Button section */
		Button btn = new Button();
		btn.setText(buttonText);
		btn.getStyleClass().add(styleClass);
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
			System.out.println(buttonPressed.getText() + " pressed");
			switch (buttonPressed.getId()) {
			case "clr":
				ta.clear();
				break;
			case "saveWords":
				bannedWords = ta.getText().split("\n");
				for(String string : bannedWords) {
					System.out.println(string);
				}
				break;
			case "home" : 
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
