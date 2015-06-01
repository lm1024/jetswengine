/**
 * 

 * Jake Feasey
 * Ashleyna Foo Inn Peng
 * 
 * Copyright (c) 2015 WaveMedia. All rights reserved.
 * 
 */

package GUI;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import runtime.SlideshowRuntimeData;

import comms.CommsHandler;

import utils.IPEncoder;
import utils.SimpleLogger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Data.Slideshow;
import XML.ImprovedXMLReader;

/**
 * @author Jake Feasey
 * @author Ashleyna Foo Inn Peng
 * @version 2.0 24/05/2015
 * 
 */
public class GUI extends Application {

	/**
	 * 
	 */

	/* for shadow on buttons */
	private boolean isShadow = false;

	/* Cascading style sheet and colours */
	final private String styleSheet = "file:resources/styles/style1.css";
	final private String grey = "#313131";
	final private String blue = "#0082DF";

	final private Image smartSlidesIcon = new Image("file:Single_S.png");
	final private Image errorIcon = new Image("file:resources/error.png");

	/* files to store prev. presentation data */
	final private File xmlFiles = new File("resources/files.csv");
	final private File buttonscsv = new File("resources/buttons.csv");
	final private File settingsFile = new File("resources/settings.csv");

	/* Screen select data */
	private int numScreens = Screen.getScreens().size();
	private final Rectangle2D primaryBounds = Screen.getPrimary().getBounds();
	private double windowWidth;
	private double windowHeight;

	/* Scenes and layouts etc. */
	private Stage stageRef;
	private ColumnConstraints columnWidth;
	private double settingsButtonWidth;

	/* Select screens sync */
	private RadioMenuItem[] menuScreen = new RadioMenuItem[numScreens];
	private RadioButton[] screenSetting = new RadioButton[numScreens];

	/* auto-next sync */
	private CheckBox slideTimerCheckBox;
	private RadioMenuItem autoNext;

	/* Blank out sync */
	private CheckBox audioToggle, videoToggle, allToggle;
	private RadioMenuItem audio, video;

	/* For menu bar */
	private HBox mainMenu = makeHBox("", Pos.CENTER, 0);
	private MenuItem home, settings;
	private Button back;

	private UserPreferences preferences;
	
	private final int maxNumberOfCharsInButtonLine = 15;

	/* For the comms channel */
	CommsHandler comms;
	
	private static String[] launchArguements;

	public GUI() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launchArguements = args;
		launch(args);
	}

	/**
	 * Must implement the inherited abstract method Application.start(Stage).
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		/* Create new instance of settings */
		preferences = new UserPreferences();

		//SimpleLogger.init();
		

		//SimpleLogger.log(false, "GUI start");

		/* Read CSV into array list */
		parseFiles();

		/* Attempt to start the comms. */
		try {
			comms = new CommsHandler();
		} catch (IOException e) {
			System.out.println("Communications could not be opened. Opening in offline mode.");
		}

		stageRef = primaryStage;

		/* Set the title and icon of the window */
		primaryStage.setTitle("SmartSlides");
		primaryStage.getIcons().add(smartSlidesIcon);

		/* Set size of window */
		windowWidth = primaryBounds.getWidth() * 0.6;
		windowHeight = primaryBounds.getHeight() * 0.65;
		primaryStage.setWidth(windowWidth);
		primaryStage.setHeight(windowHeight);

		/* Store bounds into preferences */
		preferences.setBounds(primaryBounds);

		/* Width of columns in the grid pane */
		columnWidth = new ColumnConstraints(windowWidth / 3);
		settingsButtonWidth = windowWidth * 0.2;

		/* Load settings into user preferences */
		loadSettings();

		/* Build menus and settings and main pages */
		buildMenus();
		buildSettings();
		buildmain();
		//System.out.println("Built");

		/* Do not allow the page to be resized */
		primaryStage.setResizable(false);

		/* Sets the handler for if the window is closed. */
		primaryStage.setOnCloseRequest(new ClosingWindowHandler());

		/* Show the main page */
		primaryStage.show();
		
		Slideshow currentSlideshow;
		try {
			currentSlideshow = new ImprovedXMLReader(launchArguements[0]).getSlideshow();
		} catch (Exception e1) {
			currentSlideshow = null;
		}
		if (currentSlideshow != null) {
			buildSlideshow(currentSlideshow);
		}
	}

	private void loadSettings() {

		//System.out.println("Loading settings...");

		if (settingsFile.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(settingsFile))) {

				preferences.setAudioPause(Boolean.valueOf(br.readLine()));

				preferences.setInitDir(new File(br.readLine()));

				preferences.setOTSLogged(Boolean.valueOf(br.readLine()));

				preferences.setQuestionsLogged(Boolean.valueOf(br.readLine()));

				preferences.setScreenId(Integer.parseInt(br.readLine()));

				preferences.setSlideAuto(Boolean.valueOf(br.readLine()));

				preferences.setVideoPause(Boolean.valueOf(br.readLine()));

				/* Catch exceptions */
			} catch (IOException e) {
				System.out.println(e.toString());
				System.out.println("IO Exception loading");
				resetSettings();
			} catch (Exception e) {
				System.out.println(e.toString());
				System.out.println("Exception in loading");
				resetSettings();
			}

		} else if (!settingsFile.exists()) {

			//System.out.println("creating settings file");

			/* if file does not exist then create it */
			try {
				settingsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			resetSettings();

		}

	}

	/**
	 * Method to change settings back to known values. This is used if the
	 * original file becomes corrupt or similar.
	 */
	private void resetSettings() {

		/* Reset all settings to known values */
		preferences.setAudioPause(true);
		preferences.setInitDir(new File(System.getProperty("user.home")));
		preferences.setOTSLogged(true);
		preferences.setQuestionsLogged(true);
		preferences.setScreenId(0);
		preferences.setSlideAuto(true);
		preferences.setVideoPause(true);

		/* Save new settings */
		saveSettings();

	}

	/**
	 * Method to write the current user selected settings to the CSV
	 */
	private void saveSettings() {
		//System.out.println("Saving settings");

		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(settingsFile))) {

			/* Save each preference with a new line between each */
			bw.write(String.valueOf(preferences.isAudioPause()));
			bw.newLine();

			bw.write(String.valueOf(preferences.getInitDir()));
			bw.newLine();

			bw.write(String.valueOf(preferences.isOTSLogged()));
			bw.newLine();

			bw.write(String.valueOf(preferences.isQuestionsLogged()));
			bw.newLine();

			bw.write(String.valueOf(preferences.getScreenId()));
			bw.newLine();

			bw.write(String.valueOf(preferences.isSlideAuto()));
			bw.newLine();

			bw.write(String.valueOf(preferences.isVideoPause()));

		} catch (IOException n) {
			n.printStackTrace();
			System.out.println("failed to save");
		}
	}

	/**
	 * Method to read information from files to put into buttons. If there is no
	 * file then it create and populates it.
	 */
	private void parseFiles() {

		/*
		 * TODO: make this code better, it sucks at the mo
		 */

		//System.out.println("parsing files");

		String theLine;

		if ((!xmlFiles.exists()) || (!buttonscsv.exists())) {

			//System.out.println("creating files");

			for (int i = 0; i < 6; i++) {
				preferences.getFileList().add(i, "No File");
				preferences.getButtonInfo().add(i, "No File, , , ");
			}
			/* if file does not exist then create it */
			try {
				xmlFiles.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			updateButtonsCSV();

		} else {

			if (xmlFiles.exists()) {
				/* if file exists then read it */
				
				try (BufferedReader br = new BufferedReader(new FileReader(xmlFiles))) {
					/* Loop through each line of the CSV */
					while ((theLine = br.readLine()) != null) {
						/* Add each line to the fileList */
						preferences.getFileList().add(theLine);
					}
					/* Catch exceptions */
				} catch (IOException e) {
					System.out.println(e.toString());
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
			if (buttonscsv.exists()) {

				try (BufferedReader br = new BufferedReader(new FileReader(buttonscsv))) {
					/* Loop through each line of the CSV */
					while ((theLine = br.readLine()) != null) {
						/* Add each line to the fileList, limiting the individual line lengths */
						
						String[] lines = theLine.split(",");
						theLine = "";
						
						for (int i = 0; i < lines.length; i++ ) {
							int lineLength = lines[i].length();
							lines[i] = lines[i].substring(0, Math.min(lineLength, maxNumberOfCharsInButtonLine));
							theLine += lines[i] + ",";
							
						}
						preferences.getButtonInfo().add(theLine.substring(0, theLine.length()));
					}
					/* Catch exceptions */
				} catch (IOException e) {
					System.out.println(e.toString());
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}

		}

		//System.out.println("files parsed");
	}

	private void buildSlideshow(Slideshow slideshow) {
		new SlideshowRuntimeData(slideshow, comms, preferences);
	}

	/**
	 * Handler buttonEvents can be handled easily.
	 */
	private class buttonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			/* get the source of the handler caller */
			Button buttonPressed = (Button) e.getSource();

			/* Print out ID of the button pressed */
			//System.out.println("BUTTON: " + buttonPressed.getId() + " pressed");

			/* Switch statement for multiple buttons using their IDs */
			switch (buttonPressed.getId()) {

			/* Build the scene for the main */
			case "home":
				saveSettings();
				buildmain();
				break;

			/* change buttonInfo and fileList */
			case "histClear":
				for (int n = 0; n < 6; n++) {
					preferences.getButtonInfo().set(n, "No File, , , ");
					preferences.getFileList().set(n, "null");
				}
				/* update the CSV's */
				updateButtonsCSV();
				break;

			default:
				System.out.println(buttonPressed.getId() + "  No action for this button");
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

			/* new instance of a drop shadow and its colour */
			DropShadow shadow = new DropShadow();
			shadow.setColor(Color.web(blue));

			/* if there is no shadow add it */
			if (!isShadow) {
				btn.setEffect(shadow);
			} else {
				/* If there is a shadow then remove it */
				btn.setEffect(null);
			}

			/* invert isShadow */
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

			boolean isCB = cbSelected.isSelected();
			String cbID = cbSelected.getId();

			/* None linked checkBoxes */
			switch (cbID) {

			case "slides":
				preferences.setSlideAuto(isCB);
				/* Sync auto next with menu */
				autoNext.setSelected(preferences.isSlideAuto());
				break;

			case "allQ":
				preferences.setQuestionsLogged(isCB);
				break;

			case "OTSQ":
				preferences.setOTSLogged(isCB);
				break;

			}

			/* if all then select all buttons */
			if (cbID.equals("all")) {
				audioToggle.setSelected(isCB);
				videoToggle.setSelected(isCB);
			}

			/* adjust settings values as needed */
			preferences.setAudioPause(audioToggle.isSelected());
			preferences.setVideoPause(videoToggle.isSelected());

			/* Update the blank options both button */
			if (preferences.isAudioPause() && preferences.isVideoPause()) {
				allToggle.setSelected(true);
			} else {
				allToggle.setSelected(false);
			}

			/* Set menu items as needed */
			audio.setSelected(preferences.isAudioPause());
			video.setSelected(preferences.isVideoPause());

			/* Print the checkBox Id */
			//System.out.println(cbSelected.getId() + "check box" + " was set to " + isCB);

		}

	}

	/**
	 * 
	 * private class so that mouse clicks on buttons Events can be handled
	 * 
	 */
	private class mouseClickHandler implements EventHandler<MouseEvent> {

		public void handle(MouseEvent e) {

			Button btn = (Button) e.getSource();

			/* Get ID of button as an int */
			int buttonNo = Integer.parseInt(btn.getId());

			/* open file at appropriate position in fileList */
			String outputFile = preferences.getFileList().get(buttonNo);

			/* create new instance of slideshow */
			Slideshow currentSlideshow = null;

			/* If file is chosen then parse the XML */
			if (outputFile != null) {
				try {
					currentSlideshow = new ImprovedXMLReader(outputFile).getSlideshow();
				} catch (IOException e1) {

				}
				if (currentSlideshow != null) {
					/* Build slideshow */

					buildSlideshow(currentSlideshow);

					/* Move info up in the lists upto the chosen button */
					for (int i = buttonNo - 1; i > -1; i--) {
						preferences.getFileList().set(i + 1, preferences.getFileList().get(i));
						preferences.getButtonInfo().set(i + 1, preferences.getButtonInfo().get(i));
					}

					/* Add chosen button info to start of lists */
					preferences.getFileList().set(0, outputFile);
					preferences.getButtonInfo().set(0, btn.getText().replace("\n", ","));

					/* Update the CSVs and repain the button texts */
					updateButtonsCSV();
					buildmain();

				} else {
					/* Display error scene if slideshow = null */
					dispError();
				}

			} else {
				/* Display the error message if file = null */
				dispNoFile();
			}
		}
	}

	/**
	 * 
	 * Handler for screen select buttons and menu items
	 * 
	 */
	private class screenSelectHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent source) {

			/* Determine what called the handler */
			if (source.getSource().getClass().getName().endsWith("Item")) {

				/* get and cast the source */
				RadioMenuItem a = (RadioMenuItem) source.getSource();

				/* Set screen number as source ID */
				preferences.setScreenId(Integer.parseInt(a.getId()));

				/* Set selected screen in settings to be the same */
				screenSetting[preferences.getScreenId()].setSelected(true);

			} else if (source.getSource().getClass().getName().endsWith("Button")) {

				/* get and cast the source */
				ToggleButton e = (ToggleButton) source.getSource();

				/* Set screen number as source ID */
				preferences.setScreenId(Integer.parseInt(e.getId()));

				/* Set selected screen in menu to be the same as in settings */
				menuScreen[preferences.getScreenId()].setSelected(true);

			} else {
				/* As a fall back set screen ID to 1 */
				preferences.setScreenId(1);
			}

			/* set bounds with reference to new screen selection */
			preferences.setBounds(Screen.getScreens().get(preferences.getScreenId()).getVisualBounds());

			/* Print the new bounds */
			/*System.out.println("Screen " + preferences.getScreenId()
					+ " bound points: ("
					+ preferences.getBounds().getMinX()
					+ ","
					+ preferences.getBounds().getMinY()
					+ ") ("
					+ preferences.getBounds().getMaxX()
					+ ","
					+ preferences.getBounds().getMaxY()
					+ ")");*/
		}

	}

	/**
	 * 
	 * Handler for menu actions on radio buttons
	 * 
	 */

	private class radioMenuHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {

			RadioMenuItem item = (RadioMenuItem) e.getSource();

			boolean isItem = item.selectedProperty().get();

			/* set setting data as needed */
			switch (item.getText()) {
			case "Pause Audio":
				preferences.setAudioPause(isItem);
				audioToggle.setSelected(isItem);
				break;

			case "Pause Video":
				preferences.setVideoPause(isItem);
				videoToggle.setSelected(isItem);
				break;

			case "Auto Next":
				preferences.setSlideAuto(isItem);
				slideTimerCheckBox.setSelected(isItem);
				break;
			}

			if (preferences.isAudioPause() && preferences.isVideoPause()) {
				allToggle.setSelected(true);
			} else {
				allToggle.setSelected(false);
			}

		}
	}

	/**
	 * 
	 * Handler for menu actions
	 * 
	 */

	private class menuHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent t) {
			MenuItem item = (MenuItem) t.getTarget();

			boolean isSameFile = false;

			switch (item.getText()) {

			case "Home":
				buildmain();
				break;

			case "Minimise":
				stageRef.setIconified(true);
				break;

			case "Exit":
				System.exit(0);
				break;

			case "Open":
				Slideshow currentSlideshow = null;
				File file = null;

				try {
					/* Open a file chooser and give it a name */
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Open File");
					/* Set initial directory as parent of last opened file */
					fileChooser.setInitialDirectory(preferences.getInitDir());

					/* Add filters to file chooser */
					fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("XML files ", "*.xml"),
						new FileChooser.ExtensionFilter("All files", "*.*"));

					/* File chooser is a dialog box on the home screen */
					file = fileChooser.showOpenDialog(stageRef);

				} catch (IllegalArgumentException e) {

					System.out.println("failed to open");

					/* if it has been changed then reset it */
					resetSettings();

				}

				/* Check that a file was selected */
				if (file != null) {
					try {
						currentSlideshow = new ImprovedXMLReader(file.getAbsolutePath()).getSlideshow();
					} catch (IOException e1) {
					}

					/* Get the directory of the last opened file */
					preferences.setInitDir(file.getParentFile());

					/* Show error if null slideshow is selected */
					if (currentSlideshow == null) {

						/* Display error scene */
						dispError();
					} else {

						/*
						 * Update buttons info if the slideshow is not null
						 */

						int same = 0;
						/* Compare new file to those in csv */
						do {
							if (file.getAbsolutePath().equals(preferences.getFileList().get(same))) {
								isSameFile = true;

								/* Move all info down */
								for (int i = same - 1; i > -1; i--) {
									preferences.getFileList().set(i + 1, preferences.getFileList().get(i));
									preferences.getButtonInfo().set(i + 1, preferences.getButtonInfo().get(i));
								}
								/* if its not the same check the next one */
							} else {
								same++;
							}
							/* do until the same or end of list */
						} while ((!isSameFile) && same < 6);

						/* if not the same file then write to csv */
						if (isSameFile == false) {
							/* Shift values of csv's */
							for (int i = 4; i > -1; i--) {
								preferences.getFileList().set(i + 1, preferences.getFileList().get(i));
								preferences.getButtonInfo().set(i + 1, preferences.getButtonInfo().get(i));
							}
						}
						/* add new line to start of lists */
						preferences.getFileList().set(0, file.getAbsolutePath());
						preferences.getButtonInfo().set(0, currentSlideshow.getInfo().getAuthor().substring(0, Math.min(currentSlideshow.getInfo().getAuthor().length(), maxNumberOfCharsInButtonLine)) // author
								+ ","
								+ currentSlideshow.getInfo().getVersion().substring(0, Math.min(currentSlideshow.getInfo().getVersion().length(), maxNumberOfCharsInButtonLine)) // version
								+ ","
								+ currentSlideshow.getInfo().getComment().substring(0, Math.min(currentSlideshow.getInfo().getComment().length(), maxNumberOfCharsInButtonLine)) // comment
								+ ","
								+ file.getName().substring(0, Math.min(file.getName().length(), maxNumberOfCharsInButtonLine))); // file name

						/* update CSV and rebuild the buttons */
						updateButtonsCSV();
						buildmain();

						/* Build slideshow */
						buildSlideshow(currentSlideshow);
					}

				} else {
					/* If no file is selected do nothing */
				}
				break;

			case "More settings":
				/* set settings Scene as the scene */
				buildSettings();
				break;

			/* open Q and A document */
			case "FAQ":
				try {
					Desktop.getDesktop().open(new File("resources/QandA.pdf"));
				} catch (Exception e) {
				}
				break;

			/* open UserManual */
			case "User Manual":
				try {
					Desktop.getDesktop().open(new File("resources/helpDoc.pdf"));
				} catch (Exception e) {
				}
				break;

			/* Open product info box */
			case "About SmartSlides":
				buildInfo();
				break;

			/* Open a browser at smartslides.co.uk */
			case "Website":
				try {
					Desktop.getDesktop().browse(new URI("http://www.smartslides.co.uk"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}

				break;
			}

			/* Save any changed settings */
			saveSettings();

		}
	}

	/**
	 * 
	 * Private method to build menu bars with all menu items as well as adding
	 * all handler etc.
	 * 
	 */

	private void buildMenus() {

		/**
		 * TODO: BUILDMENUS - develop menu, improve code
		 */

		MenuBar menu = new MenuBar();

		/* File section */
		Menu menuFile = new Menu("File");

		/* Create items for file */
		MenuItem openFile = new MenuItem("Open");
		openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

		home = new MenuItem("Home");
		home.setAccelerator(new KeyCodeCombination(KeyCode.BACK_SPACE));

		MenuItem min = new MenuItem("Minimise");
		MenuItem exit = new MenuItem("Exit");
		/* add items to File */
		menuFile.getItems().addAll(openFile, new SeparatorMenuItem(), home, new SeparatorMenuItem(), min, exit);

		/* Settings section */
		Menu menuSettings = new Menu("Settings");

		/* On blank settings */
		Menu onBlank = new Menu("On Blank");
		/* Blank options */
		audio = new RadioMenuItem("Pause Audio");
		video = new RadioMenuItem("Pause Video");
		/* add handlers */
		audio.setOnAction(new radioMenuHandler());
		video.setOnAction(new radioMenuHandler());

		onBlank.getItems().addAll(audio, video);// add to menu

		/* create items for settings */
		Menu screenSelect = new Menu("Select Screen");// sub-menu

		/* Determine how many screen options to show */
		if (numScreens > 1) { // if there is multi-screens

			/* Toggle group for screens */
			ToggleGroup screens = new ToggleGroup();
			screenSelect.setDisable(false);// enable screen select

			for (int i = 0; i < numScreens; i++) {

				menuScreen[i] = new RadioMenuItem("Screen " + (i + 1));
				menuScreen[i].setToggleGroup(screens); // add to toggle group
				menuScreen[i].setId(Integer.toString(i));// give an ID
				screenSelect.getItems().add(menuScreen[i]); // add to menu
				menuScreen[i].setOnAction(new screenSelectHandler()); // add
																		// handler

			}
			menuScreen[0].setSelected(true); // initialise on prime screen
		} else {
			screenSelect.setDisable(true);// disable screen select
		}

		/* Autonext option */
		autoNext = new RadioMenuItem("Auto Next");
		autoNext.setSelected(preferences.isSlideAuto()); // initialise to false
		autoNext.setOnAction(new radioMenuHandler()); // add handler

		/* To settings button */
		settings = new MenuItem("More settings");
		settings.setAccelerator(new KeyCodeCombination(KeyCode.F12));
		settings.setOnAction(new menuHandler());// handler for settings

		/* add items to settings */
		menuSettings.getItems().addAll(onBlank, screenSelect, autoNext, new SeparatorMenuItem(), settings);

		/* help section */
		Menu menuHelp = new Menu("Help");

		/* create items for help */
		MenuItem qanda = new MenuItem("FAQ");
		MenuItem help = new MenuItem("User Manual");
		help.setAccelerator(new KeyCodeCombination(KeyCode.F1));
		MenuItem info = new MenuItem("About SmartSlides");
		MenuItem website = new MenuItem("Website");

		/* add items to help */
		menuHelp.getItems().addAll(qanda, help, new SeparatorMenuItem(), website, info);

		/* Action Listener to each Menu */
		menuFile.setOnAction(new menuHandler());
		menuHelp.setOnAction(new menuHandler());

		/* add menus to menu bar */
		menu.getMenus().addAll(menuFile, menuSettings, menuHelp);

		/* spread the menu bar across whole page */
		HBox.setHgrow(menu, Priority.ALWAYS);

		back = makeSettingButton("Back", "menuButton", false, "home");
		back.setStyle("-fx-text-fill:white");
		back.setPrefHeight(mainMenu.getHeight());
		VBox backBox = makeVBox("darkBox", Pos.CENTER, 0);
		backBox.getChildren().add(back);

		menu.setStyle("-fx-background-color:" + grey);

		mainMenu.getChildren().addAll(menu, backBox);

	}

	/**
	 * 
	 * Method to build the main scene and all objects within it.
	 * 
	 */
	private void buildmain() {

		/* Change menu bar disables */
		home.setDisable(true);
		back.setDisable(true);
		settings.setDisable(false);

		GridPane grid = new GridPane();

		/* creates a scene within the stage of pixel size x by y */
		Scene mainScene = new Scene(grid, stageRef.getWidth(), stageRef.getHeight());

		/* add the stylesheet */
		mainScene.getStylesheets().add(styleSheet);

		/* settings of the grid layout */
		grid.setVgap(primaryBounds.getWidth() / 100);
		grid.setAlignment(Pos.TOP_CENTER); // alignment on screen
		// grid.setGridLinesVisible(true);

		/* each column to be a third of the page */
		grid.getColumnConstraints().addAll(columnWidth, columnWidth, columnWidth);

		/* add menu bar to main page */
		grid.add(mainMenu, 0, 0, 3, 1);

		HBox ssText = makeHBox("", Pos.CENTER, 5);

		/* Company logo and product logo in column 1-3, row 1 */
		ssText.getChildren().add(makeImageView("file:Smartslides_DarkText.png", 2 * windowWidth * 0.3, 0));

		/* add images to grid */
		grid.add(ssText, 0, 1, 3, 1);

		/* Make 6 buttons to open presentations */
		Button[][] buttons = new Button[3][2];// 2 rows of 3
		VBox[] btnBox = new VBox[6];// 6 boxes for buttons
		int i = 0;

		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 3; x++) {
				buttons[x][y] = makeMainButton(
					preferences.getButtonInfo().get(i).replace(",", "\n"),
					"lightButton",
					true,
					String.valueOf(i));
				buttons[x][y].setFont(Font.loadFont("file:resources/fonts/Roboto-Bold.ttf", 15));// add
																									// font
				buttons[x][y].setPrefWidth(windowWidth * 0.3); // set width
				btnBox[i] = makeVBox("", Pos.CENTER, 5);// put button in a box
				btnBox[i].getChildren().add(buttons[x][y]);
				grid.add(btnBox[i], x, y + 2);// add to grid
				i++;
			}
		}

		/* Create an image, add this to a box and add this to the grid */
		ImageView wmImage = makeImageView("file:Background_long_tail.png", windowWidth, 0);
		HBox hbox = makeHBox("invisiBox", Pos.CENTER, 5);
		GridPane.setVgrow(hbox, Priority.ALWAYS);
		hbox.getChildren().add(wmImage);
		grid.add(hbox, 0, 4);

		stageRef.setScene(mainScene);

	}

	/**
	 * Method to build and display the settings scene and all objects within
	 * that
	 */
	private void buildSettings() {

		/* Padding for boxes to keep things in line */
		double gap = windowWidth * 0.05;
		Insets padding = new Insets(0, 0, 0, gap);

		/* Change menu bar disables */
		home.setDisable(false);
		back.setDisable(false);
		settings.setDisable(true);

		/* Create gridpane in which to put objects */
		GridPane settingsGrid = new GridPane();

		// creates a scene within the stage of pixel size x by y
		Scene settingsScene = new Scene(settingsGrid, stageRef.getWidth(), stageRef.getHeight());
		settingsScene.getStylesheets().add(styleSheet);

		/* Set the layout as settingsGridpane */
		settingsGrid.setAlignment(Pos.TOP_CENTER);
		settingsGrid.setVgap(primaryBounds.getWidth() / 100);
		// settingsGrid.setGridLinesVisible(true);

		/* each column to be a third of the page */
		settingsGrid.getColumnConstraints().addAll(columnWidth, columnWidth, columnWidth);

		/* add menu bar to settings */
		settingsGrid.add(mainMenu, 0, 0, 3, 1);

		/* Create settings page title */
		HBox settingsBox = makeHBox("", Pos.CENTER, 5);
		settingsBox.getChildren().add(makeImageView("file:Settings.png", 2 * windowWidth * 0.3, 0));
		settingsGrid.add(settingsBox, 0, 1, 3, 1);

		/*
		 * Column 0
		 */

		/* Add check boxes */
		slideTimerCheckBox = makeCheckBox("Slide Timer", "checkLight", "slides", preferences.isSlideAuto());

		/* Vbox to contain auto next checkbox */
		VBox autoNextBox = makeVBox("clearBox", Pos.TOP_LEFT, 5);
		autoNextBox.setPadding(padding);
		autoNextBox.getChildren().addAll(makeLabel("Auto-Next:", 20, grey), slideTimerCheckBox);
		settingsGrid.add(autoNextBox, 0, 2);

		/* Boxes for blank out options layout */
		VBox blankBox = makeVBox("clearBox", Pos.TOP_LEFT, 5);
		blankBox.setPadding(padding);

		/* Toggle Buttons */
		audioToggle = makeCheckBox("Pause Audio", "checkLight", "audio", preferences.isAudioPause());
		videoToggle = makeCheckBox("Pause Video", "checkLight", "video", preferences.isVideoPause());
		allToggle = makeCheckBox("Both", "checkLight", "all", preferences.isAudioPause() && preferences.isVideoPause());

		/* add buttons and label to box and box to grid */
		blankBox.getChildren().addAll(makeLabel("On blank:", 20, grey), allToggle, audioToggle, videoToggle);
		settingsGrid.add(blankBox, 0, 3);

		/*
		 * Column 1
		 */

		/* connection code */
		VBox codeBox = makeVBox("clearBox", Pos.TOP_CENTER, 5);
		Label codeLabel = makeLabel(new IPEncoder().getIPCode(), 20, grey);

		codeBox.getChildren().addAll(makeLabel("Connection Code", 20, grey), codeLabel);

		settingsGrid.add(codeBox, 1, 2);

		/* Clear History button */
		Button histClear = makeSettingButton("Clear Presentation History", "lightButton", true, "histClear");
		VBox clearBox = makeVBox("clearBox", Pos.CENTER, 5);// box for button
		clearBox.getChildren().add(histClear);// add button
		settingsGrid.add(clearBox, 1, 3);

		/*
		 * Column 2
		 */

		/* vbox to add screen settings */
		VBox screenBox = makeVBox("clearBox", Pos.TOP_LEFT, 10);
		screenBox.setPadding(padding);

		/* add title label */
		screenBox.getChildren().add(makeLabel("Screen Select:", 20, grey));

		/* Toggle group for screens */
		ToggleGroup screenGroup = new ToggleGroup();

		/* same number of buttons as screens */
		for (int i = 0; i < numScreens; i++) {

			screenSetting[i] = new RadioButton("Screen " + (i + 1));// create
																	// button
			screenSetting[i].getStyleClass().add("radioButton");// add style
			screenSetting[i].setToggleGroup(screenGroup); // add to group
			screenSetting[i].setStyle("-fx-font-size:" + 15);// set font size
			screenBox.getChildren().add(screenSetting[i]); // add to box
			screenSetting[i].setId(Integer.toString(i));
			screenSetting[i].setOnAction(new screenSelectHandler());
		}

		/* initialise on prime screen */
		screenSetting[preferences.getScreenId()].setSelected(true);

		/* add screenBox to grid */
		settingsGrid.add(screenBox, 2, 2);

		/**/
		VBox questionsBox = makeVBox("clearBox", Pos.TOP_LEFT, 5);
		questionsBox.setPadding(padding);
		CheckBox logAllQuestions = makeCheckBox("All questions", "checkLight", "allQ", preferences.isQuestionsLogged());
		CheckBox logOTS = makeCheckBox("OTS Questions", "checkLight", "OTSQ", preferences.isOTSLogged());

		questionsBox.getChildren().addAll(makeLabel("Log Answers for:", 20, grey), logAllQuestions, logOTS);
		settingsGrid.add(questionsBox, 2, 3);

		/*
		 * bottom bar
		 */

		/* Create an image, add this to a box and add this to the grid */
		ImageView wmImage = makeImageView("file:Background_long_tail.png", windowWidth, 0);
		HBox hbox = makeHBox("invisiBox", Pos.CENTER, 5);
		GridPane.setVgrow(hbox, Priority.ALWAYS);
		hbox.getChildren().add(wmImage);
		settingsGrid.add(hbox, 0, 4);

		stageRef.setScene(settingsScene);

	}

	/**
	 * Method that builds the about smart slides pop up
	 */

	private void buildInfo() {

		/**
		 * TODO: hyper link into label
		 */

		/* Create stage and give it a title */
		final Stage infoStage = new Stage();
		infoStage.setTitle("About SmartSlides");
		infoStage.getIcons().add(smartSlidesIcon);

		/* Create pane */
		GridPane infoGrid = new GridPane();
		infoGrid.setAlignment(Pos.CENTER);// set to center of screen
		infoGrid.setPadding(new Insets(10, 10, 0, 10));

		/* make a scene and add infoGrid */
		Scene infoScene = new Scene(infoGrid);
		infoScene.getStylesheets().add("file:resources/styles/style1.css");

		infoGrid.add(makeImageView("file:Smartslides_DarkText.png", primaryBounds.getWidth() * 0.2, 0), 0, 0);

		/* Make and add a label */
		final Label lbl = makeLabel("Developed by WaveMedia \n" + "Version: 2.0 \n"
				+ "Release Date: June 2015 \n"
				+ "Email:", 15, grey);
		infoGrid.add(lbl, 0, 1);

		/* Link for email */
		final Hyperlink emailLink = new Hyperlink("help@smartslides.co.uk");
		emailLink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Email is clicked");
				getHostServices().showDocument("mailto:" + emailLink.getText());
			}
		});

		infoGrid.add(emailLink, 0, 2);

		/* Create and add button to exit window */
		VBox ok = makeVBox("invisiBox", Pos.CENTER_RIGHT, 20);
		ok.setPadding(new Insets(10, 10, 10, 10));
		Button okBtn = makeSettingButton("OK", "darkButton", true, "infoOK");
		ok.getChildren().add(okBtn);
		infoGrid.add(ok, 0, 3);
		okBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				/* close the stage when button is pressed */
				infoStage.close();
			}
		});

		/* Add WM bar to bottom of window */
		infoGrid.add(makeImageView("file:Background_long_tail.png", primaryBounds.getWidth() * 0.2, 0), 0, 4);

		/* Set the scene */
		infoStage.setScene(infoScene);

		/* set as modal and not resizable */
		infoStage.initModality(Modality.WINDOW_MODAL);
		infoStage.initOwner(stageRef);
		infoStage.setResizable(false);

		/* show the stage */
		infoStage.show();

	}

	/**
	 * Method to build and display error screen
	 */
	private void dispError() {

		/* Create stage and give it a title */
		final Stage errorStage = new Stage();
		errorStage.setTitle("Error");
		errorStage.getIcons().add(errorIcon);

		/* Create pane */
		GridPane errorGrid = new GridPane();
		errorGrid.setAlignment(Pos.CENTER);// set to center of screen
		errorGrid.setPadding(new Insets(10, 10, 0, 10));

		/* make a scene and add infoGrid */
		Scene errorScene = new Scene(errorGrid);
		errorScene.getStylesheets().add(styleSheet);

		/* Make and add a label */
		Label lbl = makeLabel("Sorry, there was an error loading the file.\n" + "For assistance please contact:\n"
				+ "help@smartslides.co.uk\n"
				+ "Or visit www.smartslides.co.uk", 15, grey);
		errorGrid.add(lbl, 1, 0);

		/* Create and add button to exit window */
		VBox ok = makeVBox("invisiBox", Pos.CENTER_RIGHT, 20);
		ok.setPadding(new Insets(10, 10, 10, 10));
		Button okBtn = makeSettingButton("OK", "darkButton", true, "infoOK");
		ok.getChildren().add(okBtn);
		errorGrid.add(ok, 1, 1, 2, 1);
		okBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				/* close the stage when button is pressed */
				errorStage.close();
			}
		});

		/* Set the scene */
		errorStage.setScene(errorScene);

		/* set as modal and not resizable */
		errorStage.initModality(Modality.WINDOW_MODAL);
		errorStage.initOwner(stageRef);
		errorStage.setResizable(false);

		/* show the stage */
		errorStage.show();

	}

	private void dispNoFile() {
		// TODO Auto-generated method stub

		/* Set stage for pop up */
		final Stage noFileStage = new Stage();
		noFileStage.setTitle("No File");

		noFileStage.getIcons().add(errorIcon);

		GridPane noFilePane = new GridPane();
		noFilePane.setAlignment(Pos.CENTER);

		/* make a scene and add infoGrid */
		Scene noFileScene = new Scene(noFilePane);
		noFileScene.getStylesheets().add(styleSheet);

		final Label lbl = makeLabel("No File!", 15, grey);
		noFilePane.add(lbl, 0, 0);

		final Button okBtn = makeSettingButton("OK", "darkButton", true, "infoOK");
		okBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				/* close the stage when button is pressed */
				noFileStage.close();
			}
		});
		noFilePane.add(okBtn, 0, 1);

		/* Set the scene */
		noFileStage.setScene(noFileScene);

		/* set as modal and not resizable */
		noFileStage.initModality(Modality.WINDOW_MODAL);
		noFileStage.initOwner(stageRef);
		noFileStage.setResizable(false);

		noFileStage.show();

	}

	/**
	 * Method to update the CSV's holding recent presentation info
	 */
	private void updateButtonsCSV() {

		/* rewrite csv files */
		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(xmlFiles))) {
			/* loop though values in the file list */
			for (int i = 0; i < 6; i++) {
				/* Write pos. i in fileList to the .csv */
				bw.write(preferences.getFileList().get(i));
				bw.newLine();
			}
		} catch (IOException n) {
			n.printStackTrace();
		}

		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(buttonscsv))) {
			/* loop though values in the file list */
			for (int i = 0; i < 6; i++) {
				/* Write pos. i in fileList to the .csv */
				bw.write(preferences.getButtonInfo().get(i));
				bw.newLine();
			}
		} catch (IOException n) {
			n.printStackTrace();
		}
	}

	/*
	 * Utilities:
	 */

	private class ClosingWindowHandler implements EventHandler<WindowEvent> {
		@Override
		public void handle(WindowEvent arg0) {
			/*
			 * If the window is closed, close the slideshow and exit the
			 * program.
			 */
			if (comms != null) {
				comms.shutdown();
			}
			System.exit(0);
		}
	}

	/** Utility function for adding labels **/
	private Label makeLabel(String labelText, int d, String colour) {
		Font bold = Font.loadFont("file:resources/fonts/Roboto-Bold.ttf", d);
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
	private CheckBox makeCheckBox(String text, String style, String id, boolean selected) {
		CheckBox cb = new CheckBox();// new instance of CheckBox
		cb.setText(text);// Text for box
		cb.setId(id);// give the box an id
		cb.setSelected(selected);// set its initial state
		cb.getStyleClass().add(style);// add style to cb
		cb.setStyle("-fx-font-size: 15");// add font
		cb.setOnAction(new checkEventHandler());// give it a handler
		return cb;
	}

	/** Utility function to make button with eventHandler */
	private Button makeSettingButton(String buttonText, String styleClass, boolean hover, String id) {

		Button btn = makeButton(buttonText, styleClass, hover, id);
		btn.setPrefWidth(settingsButtonWidth);

		/* Add an event handler for button presses */
		btn.setOnAction(new buttonEventHandler());

		return btn;
	}

	/** Utility function to make button with mouseclickhandler */
	private Button makeMainButton(String buttonText, String styleClass, boolean hover, String id) {

		Button btn = makeButton(buttonText, styleClass, hover, id);

		/* Add an event handler for button presses */
		btn.setOnMouseClicked(new mouseClickHandler());

		return btn;
	}

	/** Utility function for making a button */
	private Button makeButton(String buttonText, String styleClass, boolean hover, String id) {

		/* Create new instance of button */
		Button btn = new Button();

		/* Create and add font */
		Font medium = Font.loadFont("file:resources/fonts/Roboto-Bold.ttf", 15);
		btn.setFont(medium);

		/* Set text and ID */
		btn.setText(buttonText);
		btn.setId(id);

		btn.getStyleClass().add(styleClass);

		/* Hover functionality */
		if (hover) {
			/* Handlers for when a mouse enters or exits */
			btn.setOnMouseEntered(new hoverHandler());
			btn.setOnMouseExited(new hoverHandler());
		}

		/* make the text wrap */
		btn.wrapTextProperty().setValue(true);

		return btn;
	}

	/** Utility function for making an ImageView */
	private ImageView makeImageView(String file, double width, double height) {
		/* Create new instance of ImageView */
		ImageView iv = new ImageView();
		Image image = new Image(file, width, height, true, true);
		/* Set the image in the ImageView to the Image in 'File' */
		iv.setImage(image);
		return iv;
	}

}
