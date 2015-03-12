/**
 * 
 * Jake Feasey
 * Ashleyna Foo Inn Peng
 * 
 * Copyright (c) 2015 WaveMedia. All rights reserved.
 * 
 */

package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import SwingGUI.Console;
import XML.XMLReader;

import Data.Slideshow;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

	// @Override
	// public void init() throws Exception {
	// super.init();
	// Font font = Font.loadFont("file:resources/fonts/Roboto-Bold.ttf", 50);
	// //Font.loadFont("file:resources/fonts/Roboto-Medium.ttf", 50);
	// }

	/* Global text area and array in which to save words */
	private TextArea ta = new TextArea();
	private String bannedWords[];
	private String fileArray[] = { "pws.xml", "", "", "", "", "" };
	private TextField userField = new TextField();// text field
	private File initDir;
	private String outputFile;

	private boolean isShadow = false;

	private String styleSheet = "file:resources/styles/style1.css";

	private Scene settingsScene;
	private Scene mainScene;
	private Scene slides;

	private Stage stageRef;
	
	private double sizescale;
	private double gridHeightRef;
	

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
		 * TODO Jake, please make a nice GUI. You are our only hope. Screen
		 * resizing stuff!!
		 */
		
		stageRef = primaryStage;
		stageRef.widthProperty().addListener(new widthSizeListener());
		
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
				buildSlides();
				System.out.println("Open pres. " + i);
				outputFile = fileArray[i];

				try {
					currentSlideshow1 = new XMLReader(outputFile)
							.getSlideshow();
				} catch (IOException e1) {
					if (currentSlideshow1 == null) {

						/* Display error scene */
						dispError();

						System.out.println("Null slideshow");
					}

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
				buildmain();
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
				isShadow = true;
				btn.setEffect(shadow);
			} else {
				isShadow = false;
				btn.setEffect(null);
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

			System.out.println(cbSelected.getId() + " was set to "
					+ cbSelected.isSelected());

		}

	}

	private class mouseClickHandler implements EventHandler<MouseEvent> {

		public void handle(MouseEvent e) {

			if (e.getX() > (slides.getWidth()) * 0.5) {
				System.out.println("right");
			} else {
				System.out.println("left");
			}

		}
	}
	

	private class widthSizeListener implements ChangeListener<Number> {

		@Override
		public void changed(ObservableValue<? extends Number> observableValue,
				Number oldSceneWidth, Number newSceneWidth) {
			System.out.println("Width: " + newSceneWidth);	

		}

	}
	
	private void buildmain(){
		
		GridPane grid = new GridPane();
		
		double gridHeight = 0.62*stageRef.getHeight();
		gridHeightRef = gridHeight;
		
		/* create a gridpane layout */
		grid.setHgap(10);
		grid.setVgap(10);
		int gridpaddingsize = (int) (0.048*gridHeightRef);
		grid.setPadding(new Insets(gridpaddingsize, gridpaddingsize, gridpaddingsize, gridpaddingsize));
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(true);
		
		/* creates a stackpane to add the grid in for resizable option*/
		StackPane rootpane = new StackPane();
		rootpane.getChildren().add(grid);
		
		/* creates a scene within the stage of pixel size x by y */
		mainScene = new Scene(rootpane, stageRef.getWidth(),
				stageRef.getHeight());

		/* Company icon in column 1-3, row 1 */
		grid.add(makeImageView("file:WM_logo_transparent.png", 0.4), 0, 0, 3,
				1);

		/* Product name in column 2-5, row 1 */
		Label titleLabel = makeLabel("     SmartSlides", 0.08, "#33B5E5");
		grid.add(titleLabel, 1, 0, 4, 1);

		/* Create first button for Slide Preview and add in column 1, row 2 */
		Button one = makeButton("Presentation 1", "invisiButton", true, "0",
				"file:me.png", 0.32);
		grid.add(one, 0, 1);

		/* Create second button for Slide Preview and add in column 3, row 2 */
		Button two = makeButton("Presentation 2", "invisiButton", true, "1",
				"file:me.png", 0.32);
		grid.add(two, 2, 1);

		/* Create third button for Slide Preview and add in column 5, row 2 */
		Button three = makeButton("Presentation 3", "invisiButton", true, "2",
				"file:me.png", 0.32);
		grid.add(three, 4, 1);

		/* Create forth button for Slide Preview and add in column 1, row 4 */
		Button four = makeButton("Presentation 4", "invisiButton", true, "3",
				"file:me.png", 0.32);
		grid.add(four, 0, 3);

		/* Create fifth button for Slide Preview and add in column 3, row 4 */
		Button five = makeButton("Presentation 5", "invisiButton", true, "4",
				"file:me.png", 0.32);
		grid.add(five, 2, 3);

		/* Create sixth button for Slide Preview and add in column 5, row 4 */
		Button six = makeButton("Presentation 6", "invisiButton", true, "5",
				"file:me.png", 0.32);
		grid.add(six, 4, 3);

		/* Create Openfile button in column 1, row 6 */
		Button openfile = makeButton("Open file", "darkButton", true,
				"Openfile");
		grid.setHalignment(openfile,HPos.RIGHT);
		grid.add(openfile, 0, 5);

		/* Create Settings button in column 5, row 6 */
		Button settings = makeButton("Settings", "darkButton", true, "Settings");
		grid.setHalignment(settings,HPos.LEFT);
		grid.add(settings, 4, 5);

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
		
		double settingsGridHeight = 0.49*stageRef.getHeight();
		gridHeightRef = settingsGridHeight;
		
		/* creates a stackpane to add the grid in for resizable option*/
		StackPane settingsrootpane = new StackPane();
		settingsrootpane.getChildren().add(settingsGrid);

		// creates a scene within the stage of pixel size x by y
		settingsScene = new Scene(settingsrootpane, stageRef.getWidth(),
				stageRef.getHeight());
		settingsScene.getStylesheets().add("file:resources/styles/style1.css");

		/* Set the layout as settingsGridpane */
		settingsGrid.setPadding(new Insets(5, 5, 5, 5));
		settingsGrid.setAlignment(Pos.CENTER);
		settingsGrid.setHgap(10);
		settingsGrid.setVgap(10);
		settingsGrid.setGridLinesVisible(true);

		/*
		 * Logo button and Title
		 */

		/* Wavemedia logo Home button */
		Button homeBtn = makeButton("", "invisiButton", true, "home",
				"file:WM_logo_transparent.png", 0.4);
		settingsGrid.add(homeBtn, 0, 0, 1, 1);

		/*  Create settings page title */
		Label titleLabel = makeLabel("     Settings", 0.1, "#33B5E5");
		settingsGrid.add(titleLabel, 2, 0, 1, 1);

		/*
		 * Checkbox options:
		 */

		/* Add check boxes */
		CheckBox cb1 = makeCheckBox("Slide Timer", "checkLight", "cb1", false);
		CheckBox cb2 = makeCheckBox("Object Timer", "checkLight", "cb2", false);

		/* Vbox to contain check boses */
		VBox vbox1 = makeVBox("clearBox", Pos.TOP_LEFT, 10);
		vbox1.getChildren().addAll(makeLabel("Auto-Next:", 0.025, "#313131"), cb1,
				cb2);
		settingsGrid.add(vbox1, 0, 1, 1, 1);

		/*
		 * Back to main screen
		 */
		Button back = makeButton("Back", "lightButton", true, "home");
		back.setPrefSize(gridHeightRef*0.16, gridHeightRef*0.08);
		settingsGrid.add(back, 0, 3, 1, 1);

		/*
		 * Username input
		 */

		/* Add user name submission */
		VBox userBox = makeVBox("clearBox", Pos.TOP_CENTER, 5);// holding box
		
		/* Text field declared outside the main so can be accessed elsewhere */
		userField.getStyleClass().add("textArea");
		userField.setStyle("-fx-font-size:" + gridHeightRef*0.04);
		userField.setPromptText("Username");
		userField.setMinWidth(gridHeightRef*0.5);
		userField.setMinHeight(gridHeightRef*0.04);
		userField.setMaxWidth(gridHeightRef*0.5);
		userField.setMaxHeight(gridHeightRef*0.04);
		
		/* Add buttons and add to a box */
		Button userSubmit = makeButton("Submit", "darkButton", true, "submit");
		Button userClr = makeButton("Clear", "darkButton", true, "userClr");

		/* Hbox for username Buttons */
		HBox userButtons = makeHBox("clearBox", Pos.CENTER, 10);
		userButtons.getChildren().addAll(userSubmit, userClr);

		/* Add everything to the box */
		userBox.getChildren().addAll(makeLabel("Username:", 0.025, "#313131"),
				userField, userButtons);// add
		// to
		// box
		settingsGrid.add(userBox, 2, 1, 1, 1);// add box to settingsGrid

		/* VBox to put banned words title and text box in */
		VBox bannedBox = makeVBox("clearBox", Pos.CENTER, 5);
		
		/* Editable text area for banned words */
		/* Defined outside of main class */
		ta.setPrefRowCount(12);
		ta.setPrefColumnCount(15);
		ta.setPromptText("Banned Words Here");
		ta.getStyleClass().add("textArea");
		ta.setStyle("-fx-font-size:" + gridHeightRef*0.04);
		ta.setMinWidth(gridHeightRef*0.56);
		ta.setMinHeight(gridHeightRef*0.6);
		ta.setMaxWidth(gridHeightRef*0.56);
		ta.setMaxHeight(gridHeightRef*0.6);

		/* Add items to banned words VBox */
		bannedBox.getChildren().addAll(
				makeLabel("Banned Words:", 0.025, "#313131"), ta);
		settingsGrid.add(bannedBox, 4, 1, 1, 2);

		/* HBox to put buttons controlling banned words in */
		HBox btnBox = makeHBox("clearBox", Pos.CENTER, 10);

		/* Save and Clear buttons */
		Button clrBtn = makeButton("Clear", "darkButton", true, "clr");
		Button saveBtn = makeButton("Save", "darkButton", true, "saveWords");

		/* Add buttons to box */
		btnBox.getChildren().addAll(saveBtn, clrBtn);

		/* Add box to the settingsGrid */
		settingsGrid.add(btnBox, 4, 3, 1, 1);
		
		/* set the settings grid and its contents to resize to the stage size*/
		stageRef.maxHeightProperty().bind(settingsScene.widthProperty().divide(sizescale));
		stageRef.minHeightProperty().bind(settingsScene.widthProperty().divide(sizescale));
		settingsGrid.scaleXProperty().bind(settingsScene.widthProperty().divide(stageRef.getWidth()));
		settingsGrid.scaleYProperty().bind(settingsScene.heightProperty().divide(stageRef.getHeight()));


		/* Line sets the screen to full screen */
		// primaryStage.setFullScreen(true);

	}
	
	private void buildSlides() {
		
		Stage slideStage = new Stage();
		slideStage.setTitle("SmartSlides");
		GridPane slidePane = new GridPane();
		settingsScene = new Scene(slidePane);
		
		
	
		slidePane.add(makeImageView("file:resources/error.png", 0), 0, 0);
		
		slideStage.setScene(settingsScene);
		slideStage.setFullScreen(true);
		slideStage.show();
		
		slides.setOnMouseClicked(new mouseClickHandler());
		
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
	private Label makeLabel(String labelText, double d, String colour) {
		Font bold = Font.loadFont("file:resources/fonts/Roboto-Bold.ttf", gridHeightRef*d);
		Label lbl = new Label(labelText);// create new instance of label
		lbl.setFont(bold);
		lbl.setStyle("-fx-text-fill:" + colour);// add
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
				gridHeightRef*0.02);
		Button btn = new Button();// new instance of button
		btn.setText(buttonText);// add text
		btn.getStyleClass().add(styleClass);// add style
		btn.setPrefHeight(gridHeightRef*0.013);// button height
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
		iv.setImage(new Image(file, gridHeightRef * width, 0, true, true));// set
																					// the
																					// image
																					// in
		// the ImageView to
		// the Image in the
		// file 'File'

		return iv;
	}

}
