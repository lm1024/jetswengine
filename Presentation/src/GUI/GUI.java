/**
 * 
 * Jake Feasey
 * Ashleyna Foo Inn Peng
 * 
 * Copyright (c) 2015 WaveMedia. All rights reserved.
 * 
 */

package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import Data.Slideshow;
import XML.XMLReader;

/**
 * @author Ashleyna Foo Inn Peng
 * @author Jake Feasey
 * @version 1.0 19/02/2015
 * 
 */
public class GUI extends Application {

	/**
	 * 
	 */

	/* Global text area and array in which to save words */
	private TextArea ta = new TextArea();
	private String bannedWords[];
	private String fileArray[] = null;
	private TextField userField = new TextField();// text field
	private File initDir;
	private String outputFile;
	private int slideNo = 0;

	private boolean isShadow = false;

	private String styleSheet = "file:resources/styles/style1.css";

	private Scene settingsScene;
	private Scene mainScene;
	private GridPane slidePane;

	private Stage stageRef;

	final GridPane grid = new GridPane();
	private Label lbl;

	public GUI() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Must implement the inherited abstract method Application.start(Stage).
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		/*
		 * TODO Jake, please make a nice GUI. You are our only hope. text file
		 * to make reference to image files and XML files
		 */

		readFile();

		stageRef = primaryStage;
		
		/* Set the title of the window */
		stageRef.setTitle("SmartSlides");
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stageRef.setWidth(primaryScreenBounds.getWidth());
		stageRef.setHeight(primaryScreenBounds.getHeight());
		
		/* sets the scale required to scale the height with change in width*/
		sizescale = primaryScreenBounds.getWidth()/primaryScreenBounds.getHeight();

		buildmain();

		primaryStage.show();



	}

	private void readFile() {
		// TODO Auto-generated method stub
		int i = 0;
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader("resources/files.csv"));
			String line;
			while ((line = br.readLine()) != null) {
				
				// use comma as separator
				fileArray = line.split(",");
				System.out.println("line: " + i + " Files: " + fileArray[0] + fileArray[1]);

				i++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

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

			/* Switch statement for multiple buttons */
			switch (buttonPressed.getId()) {

			case "Openfile":
				Slideshow currentSlideshow = null;
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				fileChooser.setInitialDirectory(initDir);

				/* Add filters to file chooser */
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("XML files ", "*.xml"),
						new FileChooser.ExtensionFilter("All files", "*.*"));

				File file = fileChooser.showOpenDialog(stageRef);

				/* Check that a file was selected */
				if (file != null) {
					try {
						currentSlideshow = new XMLReader(file.getAbsolutePath())
								.getSlideshow();
					} catch (IOException e1) {
					}
					initDir = file.getParentFile();
					System.out.println(initDir);

					if (currentSlideshow == null) {

						/* Display error scene */
						dispError();

						System.out.println("Null slideshow");
					}
				} else {
					System.out.println("No file");
				}

				break;

			case "Settings":
				buildSettings();
				System.out.println("building");
				stageRef.setScene(settingsScene);
				break;

			case "0":
			case "1":
			case "2":
			case "3":
			case "4":
			case "5":
				Slideshow currentSlideshow1 = null;
				int i = Integer.parseInt(buttonPressed.getId());
				System.out.println("Open pres. " + i);
				outputFile = fileArray[i];

				try {
					currentSlideshow1 = new XMLReader(outputFile)
							.getSlideshow();
				} catch (IOException e1) {
				
				}
				if (currentSlideshow1 != null) {
					/* Only build if there is a slideshow */
					buildSlides();

				}else{
					/* Display error scene */
					dispError();

					System.out.println("Null slideshow");
				}
				break;

			case "clr":
				ta.clear();
				break;

			case "saveWords":
				bannedWords = ta.getText().split("\n");
				if (!bannedWords[0].isEmpty()) {
					for (String string : bannedWords) {
						System.out.println(string);
					}
				}
				break;

			case "home":
				System.out.println("Going home");
				stageRef.setScene(mainScene);

				break;

			case "submit":
				System.out.println(userField.getText());

				break;
			case "userClr":
				userField.clear();
				break;

			default:
				System.out.println("error");
			}

		}
	}

	/**
	 * 
	 * Handler that applies and removes shading when mouse is hovering over a
	 * button
	 * 
	 */
	private class hoverHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent e) {

			Button btn = (Button) e.getSource();
			DropShadow shadow = new DropShadow();
			shadow.setColor(Color.web("#33B5E5"));

			if (!isShadow) {
				btn.setEffect(shadow);
			} else {
				btn.setEffect(null);
			}

			isShadow = !isShadow;

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
			int i = Integer.parseInt(cbSelected.getId());// checkbox number as
															// int

			System.out.println("Check box" + i + " was set to "
					+ cbSelected.isSelected());

		}

	}

	private class mouseClickHandler implements EventHandler<MouseEvent> {

		public void handle(MouseEvent e) {
			/* ID which side of the screen is clicked on */
			if (e.getX() > (slidePane.getWidth()) * 0.5) {
				System.out.println("right");
				slideNo++;
			} else {
				if (slideNo > 0) {
					slideNo--;
				}
				System.out.println("left");
			}

			lbl.setText(String.valueOf(slideNo));
		}
	}


private void buildmain(){
		
		GridPane grid = new GridPane();

		/* create a gridpane layout */
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.setAlignment(Pos.CENTER);

		/* creates a stackpane to add the grid in for resizable option*/
		StackPane rootpane = new StackPane();
		rootpane.getChildren().add(grid);
		
		/* creates a scene within the stage of pixel size x by y */
		mainScene = new Scene(rootpane, stageRef.getWidth(),
				stageRef.getHeight());

		/* Company icon in column 1-3, row 1-3 */
		grid.add(makeImageView("file:WM_logo_transparent.png", 0.25), 0, 0, 3,
				3);

		/* Product name in column 2-5, row 3 */
		Label titleLabel = makeLabel("     SmartSlides", 50, "#33B5E5");
		grid.add(titleLabel, 1, 2, 4, 1);

		/* Create first button for Slide Preview and add in column 1, row 4 */
		Button one = makeButton("Presentation 1", "invisiButton", true, "0",
				"file:me.png", 0.2);
		grid.add(one, 0, 3);

		/* Create second button for Slide Preview and add in column 3, row 4 */
		Button two = makeButton("Presentation 2", "invisiButton", true, "1",
				"file:me.png", 0.2);
		grid.add(two, 2, 3);

		/* Create third button for Slide Preview and add in column 5, row 4 */
		Button three = makeButton("Presentation 3", "invisiButton", true, "2",
				"file:me.png", 0.2);
		grid.add(three, 4, 3);

		/* Create forth button for Slide Preview and add in column 1, row 6 */
		Button four = makeButton("Presentation 4", "invisiButton", true, "3",
				"file:me.png", 0.2);
		grid.add(four, 0, 5);

		/* Create fifth button for Slide Preview and add in column 3, row 6 */
		Button five = makeButton("Presentation 5", "invisiButton", true, "4",
				"file:me.png", 0.2);
		grid.add(five, 2, 5);

		/* Create sixth button for Slide Preview and add in column 5, row 6 */
		Button six = makeButton("Presentation 6", "invisiButton", true, "5",
				"file:me.png", 0.2);
		grid.add(six, 4, 5);

		/* Insert blank in column 2 and 4, row 4-6 */
		grid.add(makePane(), 1, 3, 1, 3);
		grid.add(makePane(), 3, 3, 1, 3);

		/* Insert blank in row 5 */
		grid.add(makePane(), 0, 4, 5, 1);

		/* Create Openfile button in column 2-3, row 7 */
		Button openfile = makeButton("Open file", "darkButton", true,
				"Openfile");
		grid.add(openfile, 1, 6, 2, 1);

		/* Create Settings button in column 4-5, row 7 */
		Button settings = makeButton("Settings", "darkButton", true, "Settings");
		grid.add(settings, 3, 6, 2, 1);

		mainScene.getStylesheets().add(styleSheet);
		stageRef.setScene(mainScene);
		
		/* set the grid and its contents to resize to the stage size*/
		stageRef.maxHeightProperty().bind(mainScene.widthProperty().divide(sizescale));
		stageRef.minHeightProperty().bind(mainScene.widthProperty().divide(sizescale));
		grid.scaleXProperty().bind(mainScene.widthProperty().divide(stageRef.getWidth()));
	    grid.scaleYProperty().bind(mainScene.heightProperty().divide(stageRef.getHeight()));

		/* Line sets the screen to fullscreen */
		// primaryStage.setFullScreen(true);

		// thisGraphicsHandler.drawRectangle();
		
}
	

	private void buildSettings() {

		/*
		 * TODO Find more settings options to add
		 */

		/* Create gridpane in which to put objects */
		GridPane settingsGrid = new GridPane();

		// creates a scene within the stage of pixel size x by y
		settingsScene = new Scene(settingsGrid, stageRef.getWidth(),
				stageRef.getHeight());
		settingsScene.getStylesheets().add(styleSheet);

		/* Set the layout as settingsGridpane */
		settingsGrid.setPadding(new Insets(5, 5, 5, 5));
		settingsGrid.setAlignment(Pos.CENTER);
		settingsGrid.setHgap(10);
		settingsGrid.setVgap(10);

		/*
		 * Logo button and Title
		 */

		/* Wavemedia logo Home button */
		Button homeBtn = makeButton("", "invisiButton", true, "home",
				"file:WM_logo_transparent.png", 0.2);
		settingsGrid.add(homeBtn, 0, 0);

		/* Create a HBox to put title in */
		HBox titleBox = makeHBox("clearBox", Pos.CENTER, 10);

		/* Add Logo and Title to titleBox */
		titleBox.getChildren().addAll(makeLabel("Settings", 50, "#33B5E5"));
		settingsGrid.add(titleBox, 1, 0, 2, 1);

		/*
		 * Checkbox options:
		 */

		/* Add check boxes */
		CheckBox cb1 = makeCheckBox("Slide Timer", "checkLight", "0", false);
		CheckBox cb2 = makeCheckBox("Object Timer", "checkLight", "1", false);

		/* Vbox to contain check boses */
		VBox vbox1 = makeVBox("clearBox", Pos.TOP_LEFT, 10);
		vbox1.getChildren().addAll(makeLabel("Auto-Next:", 16, "#313131"), cb1,
				cb2);
		settingsGrid.add(vbox1, 0, 2, 1, 2);

		/*
		 * Back to main screen
		 */
		Button back = makeButton("Back", "lightButton", true, "home");
		back.setPrefSize(100, 50);
		settingsGrid.add(back, 0, 3);

		/*
		 * Username input
		 */

		/* Add user name submission */
		VBox userBox = makeVBox("clearBox", Pos.TOP_CENTER, 5);// holding box

		/* Text field declared outside the main so can be accessed elsewhere */
		userField.getStyleClass().add("textArea");
		userField.setPromptText("Username");

		/* Add buttons and add to a box */
		Button userSubmit = makeButton("Submit", "darkButton", true, "submit");
		Button userClr = makeButton("Clear", "darkButton", true, "userClr");

		/* Hbox for username Buttons */
		HBox userButtons = makeHBox("clearBox", Pos.CENTER, 10);
		userButtons.getChildren().addAll(userSubmit, userClr);

		/* Add everything to the box */
		userBox.getChildren().addAll(makeLabel("Username:", 16, "#313131"),
				userField, userButtons);// add
		// to
		// box
		settingsGrid.add(userBox, 1, 2);// add box to settingsGrid

		/* VBox to put banned words title and text box in */
		VBox bannedBox = makeVBox("clearBox", Pos.CENTER, 10);

		/* Editable text area for banned words */
		/* Defined outside of main class */
		ta.setPrefRowCount(12);
		ta.setPrefColumnCount(15);
		ta.setPromptText("Banned Words Here");
		ta.getStyleClass().add("textArea");

		/* Add items to banned words VBox */
		bannedBox.getChildren().addAll(
				makeLabel("Banned Words:", 16, "#313131"), ta);
		settingsGrid.add(bannedBox, 2, 2);

		/* HBox to put buttons controlling banned words in */
		HBox btnBox = makeHBox("clearBox", Pos.CENTER, 10);

		/* Save and Clear buttons */
		Button clrBtn = makeButton("Clear", "darkButton", true, "clr");
		Button saveBtn = makeButton("Save", "darkButton", true, "saveWords");

		/* Add buttons to box */
		btnBox.getChildren().addAll(saveBtn, clrBtn);

		/* Add box to the settingsGrid */
		settingsGrid.add(btnBox, 2, 3);

		stageRef.setScene(mainScene);

		/* Line sets the screen to full screen */
		// primaryStage.setFullScreen(true);

	}

	private void buildSlides() {

		Stage slideStage = new Stage();
		slideStage.setTitle("SmartSlides");
		slidePane = new GridPane();
		slidePane.setAlignment(Pos.CENTER);// set to center of screen
		Scene slides = new Scene(slidePane);

		/* Make and add a label */
		lbl = makeLabel("Slide", 10, "#313131");
		slidePane.add(lbl, 0, 0);

		slideStage.setScene(slides);
		slideStage.setFullScreen(true);

		slides.setOnMouseClicked(new mouseClickHandler());
		slideStage.show();

	}

	/*
	 * Method to build and display error screne
	 */
	private void dispError() {

		/* Create new stage and scene with gridpane */
		Stage errorStage = new Stage();
		errorStage.setTitle("Error!");
		GridPane errorGrid = new GridPane();
		Scene errorScene = new Scene(errorGrid);
		errorGrid.setAlignment(Pos.CENTER);

		/* Add style sheet to pop-up */
		errorScene.getStylesheets().add(styleSheet);

		/* Add image and label */
		errorGrid.add(makeImageView("file:resources/error.png", 0), 0, 0);

		HBox errBox = makeHBox("clearBox", Pos.CENTER, 5);
		errBox.getChildren().addAll(makeLabel("Error!", 16, "#313131"));
		errorGrid.add(errBox, 0, 1);

		/* Add scene to stage and make window Modal */
		errorStage.setScene(errorScene);
		/* User has to close window */
		errorStage.initModality(Modality.WINDOW_MODAL);
		errorStage.initOwner(stageRef);

		errorStage.show();

	}

	/*
	 * Utilities:
	 */

	/** Utility function for adding labels **/
	private Label makeLabel(String labelText, int size, String colour) {
		Font bold = Font.loadFont("file:resources/fonts/Roboto-Bold.ttf", 10);
		Label lbl = new Label(labelText);// create new instance of label
		lbl.setFont(bold);
		lbl.setStyle("-fx-text-fill:" + colour + ";-fx-font-size:" + size);// add
																			// colour
																			// to
																			// label

		return lbl;
	}

	/** Utility function for adding VBox **/
	private VBox makeVBox(String style, Pos position, int space) {
		VBox vbox = new VBox();// new instance of VBox
		vbox.getStyleClass().add(style);// add style to box
		vbox.setAlignment(position);// set position of objects in the box
		vbox.setSpacing(space);// set space between objects in the box
		return vbox;
	}

	/** Utility function for adding HBox **/
	private HBox makeHBox(String style, Pos position, int space) {
		HBox hbox = new HBox();// see above function for comments
		hbox.getStyleClass().add(style);
		hbox.setSpacing(space);
		hbox.setAlignment(position);
		return hbox;
	}

	/** Utility function for adding a CheckBox **/
	private CheckBox makeCheckBox(String text, String style, String id,
			boolean selected) {
		CheckBox cb = new CheckBox();// new instance of CheckBox
		cb.setText(text);// Text for box
		cb.setId(id);// give the box an id
		cb.setSelected(selected);// set its initial state
		cb.getStyleClass().add(style);// add style to cb
		cb.setOnAction(new checkEventHandler());// give it a handler
		return cb;
	}

	/** Utility function for adding button */
	private Button makeButton(String buttonText, String styleClass,
			boolean hover, String id) {

		Font medium = Font.loadFont("file:resources/fonts/Roboto-Regular.ttf",
				15);
		Button btn = new Button();// new instance of button
		btn.setText(buttonText);// add text
		btn.getStyleClass().add(styleClass);// add style
		btn.setPrefHeight(10);// button height
		btn.setId(id);// give it an ID
		/* Hover functionality */
		if (hover) {
			btn.setOnMouseEntered(new hoverHandler());
			btn.setOnMouseExited(new hoverHandler());
		}
		btn.setOnAction(new buttonEventHandler());// event handler for buttons
		btn.setFont(medium);
		return btn;
	}

	/**
	 * Utility function to make a button with an image and label
	 */
	private Button makeButton(String buttonText, String styleClass,
			boolean hover, String id, String file, double size) {

		ImageView image = makeImageView(file, size);// make an ImageView
		Button btn = makeButton(buttonText, styleClass, hover, id);// make a
																	// button
		btn.setGraphic(image);// add image to button
		btn.setContentDisplay(ContentDisplay.TOP);// put image at the top
		return btn;
	}

	/**
	 * Utility function for making an ImageView
	 */
	private ImageView makeImageView(String file, double width) {
		ImageView iv = new ImageView();// new instance of ImageView
		iv.setImage(new Image(file, stageRef.getWidth() * width, 0, true, true));// set
																					// the
																					// image
																					// in
		// the ImageView to
		// the Image in the
		// file 'File'

		return iv;
	}

	/**
	 * Utility function for making a clear pane
	 */
	private Pane makePane() {
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: #F0F0F0;");
		pane.setMinHeight(20);
		return pane;
	}

}
