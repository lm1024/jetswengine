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
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
 * @author Jake Feasey
 * @author Ashleyna Foo Inn Peng
 * @version 2.0 24/05/2015
 * 
 */
public class GUI extends Application {

	/**
	 * 
	 */

	/* User options: */
	private int screenId = 0;
	private boolean isAudioPause = false;
	private boolean isVideoPause = false;
	private boolean isOutputMute = false;
	private boolean isSlideAuto = false;
	private boolean isObjectAuto = false;
	private File initDir; // directory for open file

	/* Global text area and array in which to save words */
	private TextArea ta = new TextArea();
	private String bannedWords[];
	private TextField userField = new TextField();// text field
	private ArrayList<String> fileList = new ArrayList<String>();
	private ArrayList<String> buttonInfo = new ArrayList<String>();
	private String outputFile;

	/* for shadow on buttons */
	private boolean isShadow = false;

	/* Cascading style sheet */
	private String styleSheet = "file:resources/styles/style1.css";

	/* Scenes and layouts etc. */
	private Scene settingsScene;
	private Scene mainScene;
	private Stage stageRef;

	/* slideshow test */
	/*
	 * private int slideNo = 0; private GridPane slidePane;
	 */

	/* files to store prev. presentation data */
	private File xmlFiles = new File("resources/files.csv");
	private File buttonscsv = new File("resources/buttons.csv");

	/* Screen select data */
	private int numScreens = Screen.getScreens().size();
	private final Rectangle2D primaryBounds = Screen.getPrimary().getBounds();
	private Rectangle2D bounds = primaryBounds;
	private double windowWidth;
	private double windowHeight;

	/* Select screens sync */
	private RadioMenuItem[] menuScreen = new RadioMenuItem[numScreens];
	private RadioButton[] settingsScreen = new RadioButton[numScreens];

	/* auto-next sync */
	private CheckBox cb1, cb2;
	private RadioMenuItem autoNext;

	/* Blank out sync */
	private ToggleButton audioToggle, videoToggle, muteToggle, allToggle;
	private RadioMenuItem audio, video;

	/* For menu bar */
	private MenuBar mainMenu = new MenuBar();
	private MenuItem home, settings;

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
		 * TODO Jake, please make a nice GUI. You are our only hope. Move fonts
		 * back to CSS. Windows gestures.
		 */

		/* Read CSV into an array list */
		parseFiles();

		stageRef = primaryStage;

		/* Set the title of the window */
		primaryStage.setTitle("SmartSlides");

		/* set size of window */
		windowWidth = primaryBounds.getWidth() * 0.6;
		windowHeight = primaryBounds.getHeight() * 0.5;
		primaryStage.setWidth(windowWidth);
		primaryStage.setHeight(windowHeight);

		buildMenus();

		buildSettings();

		buildmain(); // Build main page
		System.out.println("Main Built");

		primaryStage.setResizable(false);
		primaryStage.show(); // show main page

	}

	/**
	 * Fill arrayLists for CSV data
	 */
	private void parseFiles() {
		String theLine;
		try (BufferedReader br = new BufferedReader(new FileReader(xmlFiles))) {
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

		System.out.println("files.csv done");

		try (BufferedReader br = new BufferedReader(new FileReader(buttonscsv))) {
			/* Loop through each line of the CSV */
			while ((theLine = br.readLine()) != null) {
				/* Add each line to the fileList */
				buttonInfo.add(theLine);
			}
			/* Catch exceptions */
		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("buttons.csv done");
	}

	/**
	 * Private class written so that multiple buttonEvents can be handled
	 * easily.
	 */
	private class buttonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			/* get the source of the handler caller */
			Button buttonPressed = (Button) e.getSource();

			/* Print out ID of the button pressed */
			System.out.println("BUTTON: " + buttonPressed.getId() + " pressed");

			/* Switch statement for multiple buttons using their IDs */
			switch (buttonPressed.getId()) {

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
				settings.setDisable(false);
				home.setDisable(true);
				break;

			case "submit":
				System.out.println(userField.getText());

				break;

			case "userClr":
				userField.clear();
				break;

			case "histClear":
				/* clear presentation history */
				for (int n = 0; n < 6; n++) {
					buttonInfo.set(n, "Author,Version,Comment,File");
					fileList.set(n, "No File");
				}
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

		/**
		 * TODO: sync with menu buttons
		 */

		@Override
		public void handle(ActionEvent e) {

			CheckBox cbSelected = (CheckBox) e.getSource();

			switch (cbSelected.getId()) {

			case "slides":
				isSlideAuto = cbSelected.isSelected();
				break;

			case "objects":
				isObjectAuto = cbSelected.isSelected();
				break;

			}

			if (isSlideAuto && isObjectAuto) {
				autoNext.setSelected(true);
			} else {
				autoNext.setSelected(false);
			}

			/* Print the Id int */
			System.out.println(cbSelected.getId() + "check box"
					+ " was set to " + cbSelected.isSelected());

		}

	}

	/**
	 * 
	 * private class so that key press Events can be handled for the text boxes
	 * 
	 */
	/*
	 * private class keyPressedHandler implements EventHandler<KeyEvent> {
	 * 
	 * @Override public void handle(KeyEvent e) { Object key = e.getCode();
	 * 
	 * if (key.equals(KeyCode.ENTER)) { if (e.getSource().equals(userField)) {
	 * System.out.println("User name is: " + userField.getText()); } else if
	 * (e.getSource().equals(ta)) { bannedWords = ta.getText().split(", "); if
	 * (!bannedWords[0].isEmpty()) { for (String string : bannedWords) {
	 * System.out.println(string); } } } } else if
	 * (e.getClass().equals(slidePane)) { switch (key.toString()) { case
	 * "RIGHT": case "SPACE": System.out.println("right"); slideNo++; break;
	 * case "LEFT": if (slideNo > 0) { slideNo--; } break; case "B": case "W":
	 * case "DOWN": case "UP": System.out.println("blank"); break; } } } }
	 */

	/**
	 * 
	 * private class so that mouse click Events can be handled
	 * 
	 */
	private class mouseClickHandler implements EventHandler<MouseEvent> {

		/**
		 * TODO build slideshow now taken out
		 */
		public void handle(MouseEvent e) {

			Button btn = (Button) e.getSource();

			System.out.println(btn.getId() + " pressed with " + e.getButton());

			/* if selected with the left mouse button */
			if (e.getButton().equals(MouseButton.PRIMARY)) {
				switch (btn.getId()) {

				/* If any open button is pressed */
				case "0":
				case "1":
				case "2":
				case "3":
				case "4":
				case "5":
					Slideshow currentSlideshow1 = null;
					/* Get ID of button as an int */
					int i = Integer.parseInt(btn.getId());
					System.out.println("Open pres. " + i);
					/* open file at appropriate position in fileList */
					outputFile = fileList.get(i);
					System.out.println(outputFile);

					if (outputFile != null) {
						try {
							currentSlideshow1 = new ImprovedXMLReader(
									outputFile).getSlideshow();
						} catch (IOException e1) {

						}
						if (currentSlideshow1 != null) {
							/* Build slideshow */
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
				}
				/* if selected with the right mouse button */
			} else if (e.getButton().equals(MouseButton.SECONDARY)) {
				buildInfo(btn.getId());
			}
		}
	}

	/**
	 * 
	 * Handler for toggle buttons
	 * 
	 */
	private class toggleHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			// TODO Make method

			ToggleButton tb = (ToggleButton) e.getSource();

			boolean isTB = tb.isSelected();
			String tbID = tb.getId();

			/* if all then select all buttons */
			if (tbID.equals("all")) {
				audioToggle.setSelected(isTB);
				videoToggle.setSelected(isTB);
				muteToggle.setSelected(isTB);
			}

			/* adjust settings values as needed */
			isAudioPause = audioToggle.isSelected();
			isVideoPause = videoToggle.isSelected();
			isOutputMute = muteToggle.isSelected();

			if (isAudioPause && isVideoPause && isOutputMute) {
				allToggle.setSelected(true);
			} else {
				allToggle.setSelected(false);
			}

			/* Set menu items as needed */
			audio.setSelected(isAudioPause);
			video.setSelected(isVideoPause);

		}
	}

	/**
	 * 
	 * Handler for screen select buttons and menu items
	 * 
	 */
	private class screensHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent source) {

			/* Determine what called the handler */
			if (source.getSource().getClass().getName().endsWith("Item")) {

				/* get and cast the source */
				RadioMenuItem a = (RadioMenuItem) source.getSource();

				screenId = Integer.parseInt(a.getId());// set source id

				settingsScreen[screenId].setSelected(true); // set other option

			} else if (source.getSource().getClass().getName()
					.endsWith("Button")) {

				/* get and cast the source */
				ToggleButton e = (ToggleButton) source.getSource();

				screenId = Integer.parseInt(e.getId());// set source id

				menuScreen[screenId].setSelected(true);// set other option

			} else {
				screenId = 1;
			}

			bounds = Screen.getScreens().get(screenId).getVisualBounds();
			System.out.println("Screen " + screenId + " bound points: ("
					+ bounds.getMinX() + "," + bounds.getMinY() + ") ("
					+ bounds.getMaxX() + "," + bounds.getMaxY() + ")");

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
				isAudioPause = isItem;
				audioToggle.setSelected(isItem);
				break;

			case "Pause Video":
				isVideoPause = isItem;
				videoToggle.setSelected(isItem);
				break;

			case "Auto Next":
				isSlideAuto = isItem;
				isObjectAuto = isItem;
				cb1.setSelected(isItem);
				cb2.setSelected(isItem);
				break;
			}

			if (isAudioPause && isVideoPause && isOutputMute) {
				allToggle.setSelected(true);
			} else {
				allToggle.setSelected(false);
			}

			System.out
					.println("MENU ITEM" + item.getText() + "set to" + isItem);
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
			System.out.println("MENU ITEM: " + item.getText() + "Pressed");

			boolean isSameFile = false;

			switch (item.getText()) {

			case "Home":
				settings.setDisable(false);
				home.setDisable(true);
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
						/* Shift values of csv's */
						for (int i = 4; i > -1; i--) {
							fileList.set(i + 1, fileList.get(i));
							buttonInfo.set(i + 1, buttonInfo.get(i));
						}
						/* add new line to start of lists */
						fileList.set(0, file.getAbsolutePath());
						buttonInfo.set(0, currentSlideshow.getInfo()
								.getAuthor() // author
								+ "," + currentSlideshow.getInfo().getVersion() // version
								+ "," + currentSlideshow.getInfo().getComment() // comment
								+ "," + file.getName()); // file name

						/* rewrite csv files */
						try (BufferedWriter bw = new BufferedWriter(
								new PrintWriter(xmlFiles))) {
							/* loop though values in the file list */
							for (int i = 0; i < 6; i++) {
								/* Write pos. i in fileList to the .csv */
								bw.write(fileList.get(i));
								bw.newLine();
							}
						} catch (IOException n) {
							n.printStackTrace();
						}

						try (BufferedWriter bw = new BufferedWriter(
								new PrintWriter(buttonscsv))) {
							/* loop though values in the file list */
							for (int i = 0; i < 6; i++) {
								/* Write pos. i in fileList to the .csv */
								bw.write(buttonInfo.get(i));
								bw.newLine();
							}
						} catch (IOException n) {
							n.printStackTrace();
						}

						buildmain();
					}

				} else {
					/* If no file is selected do nothing */
					System.out.println("No file");
				}
				break;

			case "More settings":
				/* set settings Scene as the scene */
				buildSettings();
				stageRef.setScene(settingsScene);
				settings.setDisable(true);
				home.setDisable(false);
				break;

			/* open Q and A document */
			case "Quick Q & A":
				try {
					Desktop.getDesktop().open(new File("resources/QandA.pdf"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			/* open UserManual */
			case "User Manual":
				try {
					Desktop.getDesktop()
							.open(new File("resources/helpDoc.pdf"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case "Website":
				try {
					Desktop.getDesktop().browse(
							new URI("http://www.smartslides.com"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}

				break;
			}

		}
	}

	/**
	 * 
	 * Private method to build menu bars
	 * 
	 */

	private void buildMenus() {

		/**
		 * TODO: BUILDMENUS - develop menu, improve code
		 */

		/* File section */
		Menu menuFile = new Menu("File");

		/* Create items for file */
		MenuItem openFile = new MenuItem("Open");
		home = new MenuItem("Home");
		home.setDisable(true);

		MenuItem min = new MenuItem("Minimise");
		MenuItem exit = new MenuItem("Exit");
		/* add items to File */
		menuFile.getItems().addAll(openFile, new SeparatorMenuItem(), home,
				new SeparatorMenuItem(), min, exit);

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
				menuScreen[i].setOnAction(new screensHandler()); // add handler

			}
			menuScreen[0].setSelected(true); // initialise on prime screen
		} else {
			screenSelect.setDisable(true);// disable screen select
		}

		/* Autonext option */
		autoNext = new RadioMenuItem("Auto Next");
		autoNext.setSelected(false); // initialise to false
		autoNext.setOnAction(new radioMenuHandler()); // add handler

		/* To settings button */
		settings = new MenuItem("More settings");
		settings.setOnAction(new menuHandler());// handler for settings

		/* add items to settings */
		menuSettings.getItems().addAll(onBlank, screenSelect, autoNext,
				new SeparatorMenuItem(), settings);

		/* help section */
		Menu menuHelp = new Menu("Help");

		/* create items for help */
		MenuItem qanda = new MenuItem("Quick Q & A");
		MenuItem help = new MenuItem("User Manual");
		MenuItem website = new MenuItem("Website");

		/* add items to help */
		menuHelp.getItems().addAll(qanda, help, new SeparatorMenuItem(),
				website);

		/* Action Listener to each Menu */
		menuFile.setOnAction(new menuHandler());
		menuHelp.setOnAction(new menuHandler());

		/* add menus to menu bar */
		mainMenu.getMenus().addAll(menuFile, menuSettings, menuHelp);

	}

	/**
	 * 
	 * private method to build the main screen
	 * 
	 */
	private void buildmain() {
		/**
		 * TODO: Buildmain
		 */

		GridPane grid = new GridPane();

		/* create a gridpane layout */
		grid.setVgap(primaryBounds.getWidth() / 100);
		grid.setAlignment(Pos.TOP_CENTER); // alignment on screen
		// grid.setGridLinesVisible(true);
		grid.getColumnConstraints().addAll(
				new ColumnConstraints(windowWidth / 3),
				new ColumnConstraints(windowWidth / 3),
				new ColumnConstraints(windowWidth / 3));

		/* creates a scene within the stage of pixel size x by y */
		mainScene = new Scene(grid, stageRef.getWidth(), stageRef.getHeight());

		/* add menu bar to main page */
		grid.add(mainMenu, 0, 0, 3, 1);

		HBox ssText = makeHBox("", Pos.CENTER, 5);
		HBox wmLogo = makeHBox("", Pos.CENTER, 5);

		/* Company logo and product logo in column 1-3, row 1 */
		ssText.getChildren().add(
				makeImageView("file:Smartslides_DarkText.png",
						2 * windowWidth * 0.3, 0));
		wmLogo.getChildren().add(
				makeImageView("file:WM_logo_transparent.png",
						windowWidth * 0.3, 0));

		/* add images to grid */
		grid.add(ssText, 0, 1, 2, 1);
		grid.add(wmLogo, 2, 1);

		/* Make 6 buttons to open presentations */
		Button[][] buttons = new Button[3][2];// 2 rows of 3
		VBox[] btnBox = new VBox[6];// 6 boxes for buttons
		int i = 0;

		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 3; x++) {
				buttons[x][y] = makeMainButton(
						buttonInfo.get(i).replace(",", "\n"), "lightButton",
						true, String.valueOf(i));
				buttons[x][y].setFont(Font.loadFont(
						"file:resources/fonts/Roboto-Bold.ttf", 15));// add font
				buttons[x][y].setPrefWidth(windowWidth * 0.3); // set width
				btnBox[i] = makeVBox("", Pos.CENTER, 5);// put button in a box
				btnBox[i].getChildren().add(buttons[x][y]);
				grid.add(btnBox[i], x, y + 2);// add to grid
				i++;
			}
		}

		mainScene.getStylesheets().add(styleSheet);
		stageRef.setScene(mainScene);

	}

	private void buildSettings() {

		/**
		 * TODO: buildSettings
		 */

		/* Create gridpane in which to put objects */
		GridPane settingsGrid = new GridPane();

		// creates a scene within the stage of pixel size x by y
		settingsScene = new Scene(settingsGrid, stageRef.getWidth(),
				stageRef.getHeight());
		settingsScene.getStylesheets().add("file:resources/styles/style1.css");

		/* Set the layout as settingsGridpane */
		settingsGrid.setAlignment(Pos.TOP_CENTER);
		settingsGrid.setHgap(primaryBounds.getHeight() / 100);
		settingsGrid.setVgap(primaryBounds.getWidth() / 100);
		settingsGrid.setGridLinesVisible(true);
		/* each column to be a third of the page */
		settingsGrid.getColumnConstraints().addAll(
				new ColumnConstraints(windowWidth / 3),
				new ColumnConstraints(windowWidth / 3),
				new ColumnConstraints(windowWidth / 3));

		/* add menu bar to settings */
		settingsGrid.add(mainMenu, 0, 0, 3, 1);

		/*
		 * Logo button and Title
		 */

		/* Wavemedia logo Home button */
		Button homeBtn = makeButton("", "invisiButton", true, "home",
				"file:WM_logo_transparent.png", windowWidth * 0.15);

		HBox titleBox = makeHBox("", Pos.TOP_LEFT, 5);
		/* Create settings page title */
		titleBox.getChildren().addAll(homeBtn,
				makeLabel("Settings", 75, "#33B5E5"));
		settingsGrid.add(titleBox, 0, 1, 3, 1);

		/*
		 * Column 0
		 */

		/* Add check boxes */
		cb1 = makeCheckBox("Slide Timer", "checkLight", "slides", isSlideAuto);
		cb2 = makeCheckBox("Object Timer", "checkLight", "objects",
				isObjectAuto);
		cb1.setStyle("-fx-font-size:" + 15);
		cb2.setStyle("-fx-font-size:" + 15);

		/* Vbox to contain check boses */
		VBox vbox1 = makeVBox("clearBox", Pos.TOP_CENTER, 10);
		vbox1.getChildren().addAll(makeLabel("Auto-Next:", 20, "#313131"), cb1,
				cb2);
		settingsGrid.add(vbox1, 0, 2);

		/* Blank out options */
		HBox blankBox = makeHBox("clearBox", Pos.CENTER, 5);
		VBox blankOptionsBox = makeVBox("clearBox", Pos.CENTER, 5);
		VBox allBox = makeVBox("clearBox", Pos.CENTER, 5);

		/* Toggle Buttons */
		audioToggle = makeToggle("Pause Audio", "audio", "lightToggle",
				isAudioPause);
		videoToggle = makeToggle("Pause Video", "video", "lightToggle",
				isVideoPause);
		muteToggle = makeToggle("Mute Output", "mute", "lightToggle",
				isOutputMute);
		allToggle = makeToggle("All", "all", "lightToggle", false);

		/* add buttons to box and box to grid */
		blankOptionsBox.getChildren().addAll(audioToggle, videoToggle,
				muteToggle);
		allBox.getChildren().add(allToggle);
		blankBox.getChildren().addAll(blankOptionsBox, allBox);
		settingsGrid.add(blankBox, 0, 3);

		Button back = makeSettingButton("Back", "lightButton", true, "home");
		settingsGrid.add(back, 0, 4);

		/*
		 * Column 1
		 */

		/* Add user name submission */
		VBox userBox = makeVBox("clearBox", Pos.TOP_CENTER, 5);// holding box

		/* Text field declared outside the main so can be accessed elsewhere */
		userField.getStyleClass().add("textArea");
		userField.setPromptText("Username");
		userField.setStyle("-fx-font-size:" + 10);

		/* Add buttons and add to a box */
		Button userSubmit = makeSettingButton("Submit", "darkButton", true,
				"submit");
		Button userClr = makeSettingButton("Clear", "darkButton", true,
				"userClr");

		/* Hbox for username Buttons */
		HBox userButtons = makeHBox("clearBox", Pos.CENTER, 10);
		userButtons.getChildren().addAll(userSubmit, userClr);

		/* Add everything to the box */
		userBox.getChildren().addAll(makeLabel("Username:", 20, "#313131"),
				userField, userButtons);
		settingsGrid.add(userBox, 1, 2);

		/* Clear History button */
		Button histClear = makeSettingButton("Clear Presentation History",
				"lightButton", true, "histClear");
		VBox clearBox = makeVBox("clearBox", Pos.CENTER, 5);// box for button
		clearBox.getChildren().add(histClear);// add button
		settingsGrid.add(clearBox, 1, 3);

		/*
		 * Column 2
		 */

		/* vbox to add screen settings */
		VBox screenBox = makeVBox("clearBox", Pos.TOP_CENTER, 10);

		/* add title label */
		screenBox.getChildren().add(makeLabel("Screen Select:", 20, "#313131"));

		/* Toggle group for screens */
		ToggleGroup screenGroup = new ToggleGroup();

		/* same number of buttons as screens */
		for (int i = 0; i < numScreens; i++) {

			settingsScreen[i] = new RadioButton("Screen " + (i + 1));// create
																		// button
			settingsScreen[i].getStyleClass().add("radioButton");// add style
			settingsScreen[i].setToggleGroup(screenGroup); // add to group
			settingsScreen[i].setStyle("-fx-font-size:" + 15);// set font size
			screenBox.getChildren().add(settingsScreen[i]); // add to box
			settingsScreen[i].setId(Integer.toString(i));
			settingsScreen[i].setOnAction(new screensHandler());
		}

		settingsScreen[screenId].setSelected(true); // initialise on prime
													// screen

		/* add screenBox to grid */
		settingsGrid.add(screenBox, 2, 2);

		/* Line sets the screen to full screen */
		// primaryStage.setFullScreen(true);

	}

	/**
	 * Method to build a basic 'slides' stage etc. This is for debug purposes
	 * and will be replaced when interface is integrated.
	 */

	private void buildInfo(String btnId) {

		/* Create stage and give it a title */
		Stage infoStage = new Stage();
		infoStage.setTitle("SmartSlides");

		/* Create pane */
		GridPane infoGrid = new GridPane();
		infoGrid.setAlignment(Pos.CENTER);// set to center of screen

		/* make a scene and add infoGrid */
		Scene infoScene = new Scene(infoGrid);

		/* Make and add a label */
		Label lbl = makeLabel("More info on slideshow", 1, "#313131");
		infoGrid.add(lbl, 0, 0);

		infoStage.setScene(infoScene);

		infoGrid.setOnMouseClicked(new mouseClickHandler());

		infoStage.show();

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
		errorGrid.add(makeImageView("file:resources/error.png", 0, 0), 0, 0);

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

	/** Utility function for adding Toggle Buttons **/
	private ToggleButton makeToggle(String text, String id, String style,
			boolean selected) {
		ToggleButton tb = new ToggleButton(text);// create button
		tb.setId(id);// add id
		tb.getStyleClass().add(style);// add style
		tb.setSelected(selected);// initial state
		tb.setOnAction(new toggleHandler());// add handler
		return tb;
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

	/** Utility function to make button with eventHandler */
	private Button makeSettingButton(String buttonText, String styleClass,
			boolean hover, String id) {

		Button btn = makeButton(buttonText, styleClass, hover, id);

		/* Add an event handler for button presses */
		btn.setOnAction(new buttonEventHandler());

		return btn;
	}

	/** Utility function to make button with mouseclickhandler */
	private Button makeMainButton(String buttonText, String styleClass,
			boolean hover, String id) {

		Button btn = makeButton(buttonText, styleClass, hover, id);

		/* Add an event handler for button presses */
		btn.setOnMouseClicked(new mouseClickHandler());

		return btn;
	}

	/** Utility function to make a button with an image and label */
	private Button makeButton(String buttonText, String styleClass,
			boolean hover, String id, String file, double size) {
		/* Make a button and an ImageView using utilities */
		ImageView image = makeImageView(file, size, 0);
		Button btn = makeSettingButton(buttonText, styleClass, hover, id);
		btn.setGraphic(image);// add image to button
		btn.setContentDisplay(ContentDisplay.TOP);// put image at the top
		return btn;
	}

	/** Utility function for making a button */
	private Button makeButton(String buttonText, String styleClass,
			boolean hover, String id) {

		Button btn = new Button();// new instance of button
		Font medium = Font.loadFont("file:resources/fonts/Roboto-Bold.ttf", 15);
		btn.setFont(medium);// add font
		btn.setText(buttonText);// add text
		btn.getStyleClass().add(styleClass);// add style
		btn.setId(id);// give it an ID
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

	/**
	 * Utility function for making an ImageView
	 */
	private ImageView makeImageView(String file, double width, double height) {
		/* Create new instance of ImageView */
		ImageView iv = new ImageView();
		Image image = new Image(file, width, height, true, true);
		/* Set the image in the ImageView to the Image in 'File' */
		iv.setImage(image);
		return iv;
	}

}