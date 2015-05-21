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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import Data.Slideshow;
import XML.ImprovedXMLReader;

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
	private TextField userField = new TextField();// text field
	private ArrayList<String> fileList = new ArrayList<String>();
	// private String pictureArray[] = null;
	private File initDir;
	private String outputFile;
	private int slideNo = 0;

	private boolean isShadow = false;

	private String styleSheet = "file:resources/styles/style1.css";

	private Scene settingsScene;
	private Scene mainScene;
	private GridPane slidePane;
	private Stage stageRef;
	private double sizescale;

	final GridPane grid = new GridPane();
	private Label lbl;
	private double gridHeightRef;

	private File inputFile = new File("resources/files.csv");

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
		 * TODO Jake, please make a nice GUI. You are our only hope. CSV
		 * checking and absolute file paths CSV reading for images. Move fonts
		 * back to CSS. Windows gestures.
		 */

		/* Read CSV into an array list */
		parseFiles();

		stageRef = primaryStage;

		/* Set the title of the window */
		stageRef.setTitle("SmartSlides");
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stageRef.setWidth(primaryScreenBounds.getWidth());
		stageRef.setHeight(primaryScreenBounds.getHeight());

		/* sets the scale required to scale the height with change in width */
		sizescale = primaryScreenBounds.getWidth()
				/ primaryScreenBounds.getHeight();

		buildmain();

		primaryStage.show();

	}

	/**
	 * Creates csv for list of recently opened files
	 */
	private void parseFiles() {
		String theLine;
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			/* Loop through each line of the CSV */
			while ((theLine = br.readLine()) != null) {
				/* Add each line to the fileList */
				fileList.add(theLine);
			}
			/* Catch exceptions */
		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Private class written so that multiple buttonEvents can be handled
	 * easily.
	 */
	private class buttonEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {

			boolean isSameFile = false;

			/* get the source of the handler caller */
			Button buttonPressed = (Button) e.getSource();

			/* Print out ID of the button pressed */
			System.out.println("BUTTON: " + buttonPressed.getId() + " pressed");

			/* Switch statement for multiple buttons using their IDs */
			switch (buttonPressed.getId()) {

			case "Openfile":
				Slideshow currentSlideshow = null;
				/* Open a file chooser and give it a name */
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				/* Set initial directory as parent of last opened file */
				fileChooser.setInitialDirectory(initDir);

				/* Add filters to file chooser */
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("XML files ", "*.xml"),
						new FileChooser.ExtensionFilter("All files", "*.*"));

				/* File chooser is a dialog box on the home screen */
				File file = fileChooser.showOpenDialog(stageRef);

				/* Check that a file was selected */
				if (file != null) {
					try {
						currentSlideshow = new ImprovedXMLReader(
								file.getAbsolutePath()).getSlideshow();
					} catch (IOException e1) {
					}

					/* Get the directory of the last opened file */
					initDir = file.getParentFile();
					System.out.println(initDir);

					/* Show error if null slideshow is selected */
					if (currentSlideshow == null) {

						/* Display error scene */
						dispError();
						System.out.println("Null slideshow");
					}

					/*
					 * Write new file to CSV if not already there
					 */
					/* Compare new file to those in csv */
					for (int i = 0; i < 6; i++) {
						if (file.getAbsolutePath().equals(fileList.get(i))) {
							isSameFile = true;
							System.out.println("Same file detected");
						}
					}

					/* if not the same file then write to csv */
					if (isSameFile == false) {
						/* Shift values of fileList down */
						for (int i = 4; i > -1; i--) {
							fileList.set(i + 1, fileList.get(i));
						}
						/* add new file to start of list */
						fileList.set(0, file.getAbsolutePath());

						try (BufferedWriter bw = new BufferedWriter(
								new PrintWriter(inputFile))) {
							/* loop though values in the file list */
							for (int i = 0; i < 6; i++) {
								/* Write pos. i in fileList to the .csv */
								bw.write(fileList.get(i));
								bw.newLine();
							}
						} catch (IOException n) {
							n.printStackTrace();
						}
					}

				} else {
					/* If no file is selected do nothing */
					System.out.println("No file");
				}

				break;

			case "Settings":
				/* Build the settings scene */
				buildSettings();
				/* set settings Scene as the scene */
				stageRef.setScene(settingsScene);
				break;

			/* If any open button is pressed */
			case "0":
			case "1":
			case "2":
			case "3":
			case "4":
			case "5":
				Slideshow currentSlideshow1 = null;
				/* Get ID of button as an int */
				int i = Integer.parseInt(buttonPressed.getId());
				System.out.println("Open pres. " + i);
				/* open file at appropriate position in fileList */
				outputFile = fileList.get(i);

				if (outputFile != null) {
					try {
						currentSlideshow1 = new ImprovedXMLReader(outputFile)
								.getSlideshow();
					} catch (IOException e1) {

					}
					if (currentSlideshow1 != null) {
						/* Only build if there is a slideshow */
						buildSlides();

					} else {
						/* Display error scene */
						dispError();

						System.out.println("Null slideshow");
					}
					break;
				} else {
					/* Display the error message */
					dispError();
				}

			case "clr":
				/* Clear text area */
				ta.clear();
				break;

			case "saveWords":
				/* Save words in the text box into BannedWords */
				bannedWords = ta.getText().split(", ");
				if (!bannedWords[0].isEmpty()) {
					for (String string : bannedWords) {
						System.out.println(string);
					}
				}
				break;

			case "home":
				/* Build the scene for the main */
				buildmain();

				break;

			case "submit":
				System.out.println(userField.getText());

				break;
			case "userClr":
				/* Clear the text field */
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
			/* Get handler source */
			Button btn = (Button) e.getSource();
			/* new instance of a drop shadow and its colour */
			DropShadow shadow = new DropShadow();
			shadow.setColor(Color.web("#33B5E5"));

			/* if there is no shadow add it */
			if (!isShadow) {
				btn.setEffect(shadow);
			} else {
				/* If there is a shadow then remove it */
				btn.setEffect(null);
			}

			/* invert isShadow boolean */
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
			/* Get ID as an int */
			int i = Integer.parseInt(cbSelected.getId());

			/* Print the Id int */
			System.out.println("Check box" + i + " was set to "
					+ cbSelected.isSelected());

		}

	}

	/**
	 * 
	 * private class so that key press Events can be handled for the text boxes
	 * 
	 */
	private class keyPressedHandler implements EventHandler<KeyEvent> {
		/*
		 * TODO neaten up and comment, possibly use nested switch?
		 */

		@Override
		public void handle(KeyEvent e) {
			Object key = e.getCode();

			if (key.equals(KeyCode.ENTER)) {
				if (e.getSource().equals(userField)) {
					System.out.println("User name is: " + userField.getText());
				} else if (e.getSource().equals(ta)) {
					bannedWords = ta.getText().split(", ");
					if (!bannedWords[0].isEmpty()) {
						for (String string : bannedWords) {
							System.out.println(string);
						}
					}
				}
			} else if (e.getClass().equals(slidePane)) {
				switch (key.toString()) {
				case "RIGHT":
				case "SPACE":
					System.out.println("right");
					slideNo++;
					break;
				case "LEFT":
					if (slideNo > 0) {
						slideNo--;
					}
					break;
				case "B":
				case "W":
				case "DOWN":
				case "UP":
					System.out.println("blank");
					break;
				}
				lbl.setText(String.valueOf(slideNo));
			}
		}
	}

	/**
	 * 
	 * private class so that mouse click Events can be handled
	 * 
	 */
	private class mouseClickHandler implements EventHandler<MouseEvent> {

		public void handle(MouseEvent e) {
			/* ID which side of the screen is clicked on */
			if (e.getX() > (slidePane.getWidth()) * 0.5) {
				/* Change the value of slideNo accordingly */
				slideNo++;
			} else {
				if (slideNo > 0) {
					slideNo--;
				}
			}

			/* update the text in label */
			lbl.setText(String.valueOf(slideNo));
		}
	}

	/**
	 * 
	 * private method to build the main screen
	 * 
	 */
	private void buildmain() {

		GridPane grid = new GridPane();

		gridHeightRef = 0.62 * stageRef.getHeight();

		/* create a gridpane layout */
		grid.setHgap(0.02 * gridHeightRef);
		grid.setVgap(0.02 * gridHeightRef);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.setAlignment(Pos.CENTER);
		// grid.setGridLinesVisible(true);

		/* creates a stackpane to add the grid in for resizable option */
		StackPane rootpane = new StackPane();
		rootpane.getChildren().add(grid);

		/* creates a scene within the stage of pixel size x by y */
		mainScene = new Scene(rootpane, stageRef.getWidth(),
				stageRef.getHeight());

		/* Company logo and product logo in column 1-5, row 1 */
		HBox titleBox = makeHBox("box", Pos.CENTER_LEFT,
				(int) Math.round(0.5 * gridHeightRef));
		titleBox.getChildren().addAll(
				makeImageView("file:Smartslides_DarkText.png", 0.56),
				makeImageView("file:WM_logo_transparent.png", 0.4));
		grid.add(titleBox, 0, 0, 5, 1);

		/* Create first button for Slide Preview and add in column 1, row 2 */
		Button one = makeButton("Presentation 1", "invisiButton", true, "0",
				"file:me.png", 0.4);
		grid.add(one, 0, 1);

		/* Create second button for Slide Preview and add in column 3, row 2 */
		Button two = makeButton("Presentation 2", "invisiButton", true, "1",
				"file:me.png", 0.4);
		grid.add(two, 2, 1);

		/* Create third button for Slide Preview and add in column 5, row 2 */
		Button three = makeButton("Presentation 3", "invisiButton", true, "2",
				"file:me.png", 0.4);
		grid.add(three, 4, 1);

		/* Create forth button for Slide Preview and add in column 1, row 4 */
		Button four = makeButton("Presentation 4", "invisiButton", true, "3",
				"file:me.png", 0.4);
		grid.add(four, 0, 3);

		/* Create fifth button for Slide Preview and add in column 3, row 4 */
		Button five = makeButton("Presentation 5", "invisiButton", true, "4",
				"file:me.png", 0.4);
		grid.add(five, 2, 3);

		/* Create sixth button for Slide Preview and add in column 5, row 4 */
		Button six = makeButton("Presentation 6", "invisiButton", true, "5",
				"file:me.png", 0.4);
		grid.add(six, 4, 3);

		/* HBox for openFile and settings buttons */
		HBox buttons = makeHBox("hbox", Pos.CENTER,
				(int) Math.round(0.14 * gridHeightRef));
		/* Create openFile button in column 2-3, row 7 */
		Button openFile = makeButton("Open file", "darkButton", true,
				"Openfile");
		openFile.setMinSize(0.01 * gridHeightRef, 0.04 * gridHeightRef);

		/* Create Settings button in column 4-5, row 7 */
		Button settings = makeButton("Settings", "darkButton", true, "Settings");
		settings.setMinSize(0.01 * gridHeightRef, 0.04 * gridHeightRef);

		/* add buttons to box and box to grid */
		buttons.getChildren().addAll(openFile, settings);
		grid.add(buttons, 0, 5, 5, 1);

		mainScene.getStylesheets().add(styleSheet);
		stageRef.setScene(mainScene);

		/* set the grid and its contents to resize to the stage size */
		stageRef.maxHeightProperty().bind(
				mainScene.widthProperty().divide(sizescale));
		stageRef.minHeightProperty().bind(
				mainScene.widthProperty().divide(sizescale));
		grid.scaleXProperty().bind(
				mainScene.widthProperty().divide(stageRef.getWidth()));
		grid.scaleYProperty().bind(
				mainScene.heightProperty().divide(stageRef.getHeight()));

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

		gridHeightRef = 0.49 * stageRef.getHeight();

		/* creates a stackpane to add the grid in for resizable option */
		StackPane settingsrootpane = new StackPane();
		settingsrootpane.getChildren().add(settingsGrid);

		// creates a scene within the stage of pixel size x by y
		settingsScene = new Scene(settingsrootpane, stageRef.getWidth(),
				stageRef.getHeight());
		settingsScene.getStylesheets().add("file:resources/styles/style1.css");

		/* Set the layout as settingsGridpane */
		settingsGrid.setPadding(new Insets(5, 5, 5, 5));
		settingsGrid.setAlignment(Pos.CENTER);
		settingsGrid.setHgap(0.02 * gridHeightRef);
		settingsGrid.setVgap(0.02 * gridHeightRef);
		// settingsGrid.setGridLinesVisible(true);

		/*
		 * Logo button and Title
		 */

		/* Wavemedia logo Home button */
		Button homeBtn = makeButton("", "invisiButton", true, "home",
				"file:WM_logo_transparent.png", 0.5);
		settingsGrid.add(homeBtn, 0, 0, 1, 1);

		/* Create settings page title */
		Label titleLabel = makeLabel("     Settings", 0.15, "#33B5E5");
		settingsGrid.add(titleLabel, 2, 0, 1, 1);

		/*
		 * Checkbox options:
		 */

		/* Add check boxes */
		CheckBox cb1 = makeCheckBox("Slide Timer", "checkLight", "cb1", false);
		CheckBox cb2 = makeCheckBox("Object Timer", "checkLight", "cb2", false);
		cb1.setStyle("-fx-font-size:" + 0.03 * gridHeightRef);
		cb2.setStyle("-fx-font-size:" + 0.03 * gridHeightRef);

		/* Vbox to contain check boses */
		VBox vbox1 = makeVBox("clearBox", Pos.TOP_LEFT, 10);
		vbox1.getChildren().addAll(makeLabel("Auto-Next:", 0.05, "#313131"),
				cb1, cb2);
		settingsGrid.add(vbox1, 0, 1, 1, 1);

		/*
		 * Back to main screen
		 */
		Button back = makeButton("Back", "lightButton", true, "home");
		back.setPrefSize(gridHeightRef * 0.16, gridHeightRef * 0.08);
		settingsGrid.add(back, 0, 3, 1, 1);

		/*
		 * Username input
		 */

		/* Add user name submission */
		VBox userBox = makeVBox("clearBox", Pos.TOP_CENTER, 5);// holding box

		/* Text field declared outside the main so can be accessed elsewhere */
		userField.getStyleClass().add("textArea");
		userField.setPromptText("Username");
		userField.setStyle("-fx-font-size:" + gridHeightRef * 0.09);
		userField.setMinWidth(gridHeightRef * 0.5);
		userField.setMinHeight(gridHeightRef * 0.08);
		userField.setMaxWidth(gridHeightRef * 0.5);
		userField.setMaxHeight(gridHeightRef * 0.08);

		/* Add buttons and add to a box */
		Button userSubmit = makeButton("Submit", "darkButton", true, "submit");
		Button userClr = makeButton("Clear", "darkButton", true, "userClr");

		/* Hbox for username Buttons */
		HBox userButtons = makeHBox("clearBox", Pos.CENTER, 10);
		userButtons.getChildren().addAll(userSubmit, userClr);

		/* Add everything to the box */
		userBox.getChildren().addAll(makeLabel("Username:", 0.05, "#313131"),
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
		ta.setStyle("-fx-font-size:" + gridHeightRef * 0.09);
		ta.setMinWidth(gridHeightRef * 0.55);
		ta.setMinHeight(gridHeightRef * 0.75);
		ta.setMaxWidth(gridHeightRef * 0.55);
		ta.setMaxHeight(gridHeightRef * 0.75);

		/* Add items to banned words VBox */
		bannedBox.getChildren().addAll(
				makeLabel("Banned Words:", 0.05, "#313131"), ta);
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

		/* set the settings grid and its contents to resize to the stage size */
		stageRef.maxHeightProperty().bind(
				settingsScene.widthProperty().divide(sizescale));
		stageRef.minHeightProperty().bind(
				settingsScene.widthProperty().divide(sizescale));
		settingsGrid.scaleXProperty().bind(
				settingsScene.widthProperty().divide(stageRef.getWidth()));
		settingsGrid.scaleYProperty().bind(
				settingsScene.heightProperty().divide(stageRef.getHeight()));

		/* Line sets the screen to full screen */
		// primaryStage.setFullScreen(true);

	}

	/**
	 * Method to build a basic 'slides' stage etc. This is for debug purposes
	 * and will be replaced when interface is integrated.
	 */

	private void buildSlides() {

		slideNo = 0;
		Stage slideStage = new Stage();
		slideStage.setTitle("SmartSlides");
		slidePane = new GridPane();
		slidePane.setAlignment(Pos.CENTER);// set to center of screen
		Scene slides = new Scene(slidePane);

		/* Make and add a label */
		lbl = makeLabel("Slide", 1, "#313131");
		slidePane.add(lbl, 0, 0);

		slideStage.setScene(slides);
		slideStage.setFullScreen(true);

		slides.setOnMouseClicked(new mouseClickHandler());
		slides.setOnKeyReleased(new keyPressedHandler());
		slideStage.show();

	}

	/**
	 * Method to build and display error screen
	 */
	private void dispError() {

		/* Create new stage and scene with GridPane */
		Stage errorStage = new Stage();
		errorStage.setTitle("Error!");
		GridPane errorGrid = new GridPane();
		Scene errorScene = new Scene(errorGrid);
		errorGrid.setAlignment(Pos.CENTER);

		/* Add style sheet to pop-up */
		errorScene.getStylesheets().add(styleSheet);

		/* Add image and label */
		errorGrid.add(makeImageView("file:resources/error.png", 0), 0, 0);

		/* Make a box and add things to it */
		HBox errBox = makeHBox("clearBox", Pos.CENTER, 5);
		errBox.getChildren().addAll(makeLabel("Error!", 16, "#313131"));
		errorGrid.add(errBox, 0, 1);

		/* Add scene to stage and make window Modal */
		errorStage.setScene(errorScene);

		/* Modality means the user has to close window */
		errorStage.initModality(Modality.WINDOW_MODAL);
		errorStage.initOwner(stageRef);

		errorStage.show();

	}

	/*
	 * Utilities:
	 */

	/** Utility function for adding labels **/
	private Label makeLabel(String labelText, double d, String colour) {
		Font bold = Font.loadFont("file:resources/fonts/Roboto-Bold.ttf",
				gridHeightRef * d);
		Label lbl = new Label(labelText);// create new instance of label
		lbl.setFont(bold);
		lbl.setStyle("-fx-text-fill:" + colour);// add colour to label

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

		Button btn = new Button();// new instance of button
		Font medium = Font.loadFont("file:resources/fonts/Roboto-Bold.ttf",
				gridHeightRef * 0.035);
		btn.setFont(medium);// add font
		btn.setText(buttonText);// add text
		btn.getStyleClass().add(styleClass);// add style
		btn.setPrefHeight(gridHeightRef * 0.06);// button height
		btn.setId(id);// give it an ID
		/* Hover functionality */
		if (hover) {
			/* Handlers for when a mouse enters or exits */
			btn.setOnMouseEntered(new hoverHandler());
			btn.setOnMouseExited(new hoverHandler());
		}
		/* Add an event handler for button presses */
		btn.setOnAction(new buttonEventHandler());

		return btn;
	}

	/**
	 * Utility function to make a button with an image and label
	 */
	private Button makeButton(String buttonText, String styleClass,
			boolean hover, String id, String file, double size) {
		/* Make a button and an ImageView using utilities */
		ImageView image = makeImageView(file, size);
		Button btn = makeButton(buttonText, styleClass, hover, id);
		btn.setGraphic(image);// add image to button
		btn.setContentDisplay(ContentDisplay.TOP);// put image at the top
		return btn;
	}

	/**
	 * Utility function for making an ImageView
	 */
	private ImageView makeImageView(String file, double width) {
		/* Create new instance of ImageView */
		ImageView iv = new ImageView();
		/* Set the image in the ImageView to the Image in 'File' */
		iv.setImage(new Image(file, gridHeightRef * width, 0, true, true));

		return iv;
	}

}
