package pcapp;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AudienceGUI extends Application {

	final private Image smartSlidesIcon = new Image(
			"file:resources/img/Single_S.png");
	private final Rectangle2D primaryBounds = Screen.getPrimary().getBounds();

	/* Cascading style sheet and colours */
	final private String styleSheet = "file:resources/styles/style1.css";

	final private String smartSlidesTitle = "file:img/Smartslides_DarkText.png";

	/* Connection settings */
	private TextField serverAddress;
	private Client client;
	private String institutionCode;

	/* Sending a question */
	private TextField userQuestion;
	private Button questionSubmit;

	private Button[] btn = new Button[4];

	public AudienceGUI() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public String getHexCodeIP(String hexCode) {
		String[] hex;
		String hexCodeIP;
		hex = hexCode.split("(?!^)");
		String hexString1 = hex[0] + hex[1];
		String hexString2 = hex[2] + hex[3];
		int hexCodeIPString1 = hex2decimal(hexString1);
		System.out.println("WM IP: " + hexCode + " " + hex);
		System.out.println("WM IP: " + hexString1 + " -> " + hexCodeIPString1);
		int hexCodeIPString2 = hex2decimal(hexString2);
		System.out.println("WM IP: " + hexString2 + " -> " + hexCodeIPString2);
		hexCodeIP = Integer.toString(hexCodeIPString1);
		hexCodeIP += "." + Integer.toString(hexCodeIPString2);
		System.out.println("WM IP: " + hexCodeIP);
		return hexCodeIP;
	}

	public static int hex2decimal(String s) {
		String digits = "0123456789ABCDEF";
		s = s.toUpperCase();
		int val = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int d = digits.indexOf(c);
			val = 16 * val + d;
		}
		return val;
	}

	/**
	 * Method to handle button events when answering questions
	 */
	private class buttonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			Button btnPressed = (Button) e.getSource();

			if (btnPressed.getId().equals("connect")) {

				/* Submit the connection code */
				if (serverAddress.getText().matches("^([0-9a-fA-F]){4}$")
						&& (institutionCode != null)) {
					String serverIP = getHexCodeIP(serverAddress.getText());

					/* Setup the client */
					client = new Client(institutionCode + "." + serverIP);

					/* Enable buttons */
					for (int i = 0; i < 4; i++) {
						btn[i].setDisable(false);
					}
					questionSubmit.setDisable(false);

				}

				/* Submit the question in the test box */
			} else if (btnPressed.getId().equals("submit")) {
				client.sendToServerWithoutIP(userQuestion.getText());
				userQuestion.clear();

			} else {

				/* send button ID to server */
				client.sendToServerWithIP(btnPressed.getId());
			}
		}
	}

	/*
	 * A method to easily handle combo box events
	 */
	private class comboListener implements ChangeListener<Number> {

		@Override
		public void changed(ObservableValue<? extends Number> arg0,
				Number arg1, Number arg2) {
			/*
			 * Check which institution has been selected and append the first
			 * two parts of the IP as needed
			 */
			switch (arg2.toString()) {
			case "0":// UoY
				institutionCode = "144.32";
				break;

			}

		}

	}

	/**
	 * Must implement the inherited abstract method Application.start(Stage).
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		/* Set the title and icon of the window */
		primaryStage.setTitle("SmartSlides");
		primaryStage.getIcons().add(smartSlidesIcon);

		/* Do not allow the page to be resized */
		primaryStage.setResizable(false);

		/* Box to put everything in */
		VBox answersBox = new VBox();
		answersBox.setAlignment(Pos.CENTER);
		answersBox.getStyleClass().add("invisiBox");
		answersBox.setSpacing(primaryBounds.getHeight() * 0.01);

		/* creates a scene within the stage */
		Scene mainScene = new Scene(answersBox);

		/* add the stylesheet */
		mainScene.getStylesheets().add(styleSheet);

		/* Add title image */
		ImageView smartSlides = new ImageView();
		Image smartSlidesImage = new Image(smartSlidesTitle,
				primaryBounds.getWidth() * 0.1, 0, true, true);
		smartSlides.setImage(smartSlidesImage);
		answersBox.getChildren().add(smartSlides);

		/* User question layout box */
		HBox question = new HBox();
		question.getStyleClass().add("invisiBox");
		question.setAlignment(Pos.CENTER);

		/* User question text field and submit button */
		userQuestion = new TextField();

		questionSubmit = new Button("Submit");
		questionSubmit.setId("submit");
		questionSubmit.setDisable(true);
		questionSubmit.setOnAction(new buttonHandler());

		question.getChildren().addAll(userQuestion, questionSubmit);

		answersBox.getChildren().add(question);

		/* Add the 4 buttons for answering a question */
		for (int i = 0; i < 4; i++) {

			btn[i] = new Button();
			btn[i].setId(Integer.toString(i));
			btn[i].setOnAction(new buttonHandler());
			btn[i].getStyleClass().add("darkButton");
			btn[i].setPrefWidth(primaryBounds.getWidth() * 0.1);
			btn[i].setDisable(true);
			answersBox.getChildren().add(btn[i]);
		}

		/* Set text of buttons */
		btn[0].setText("A");
		btn[1].setText("B");
		btn[2].setText("C");
		btn[3].setText("D");

		/* Add the combo box for selecting an institution */
		ComboBox<String> institution = new ComboBox<String>();
		institution.setItems(FXCollections.observableArrayList(
				"University of York", "Institution 2", "Institution 3"));
		institution.setPromptText("Institution:");
		institution.getSelectionModel().selectedIndexProperty()
				.addListener(new comboListener());

		answersBox.getChildren().add(institution);

		/* For user to connect to server */
		serverAddress = new TextField();
		answersBox.getChildren().add(serverAddress);

		/* Submit server code */
		Button connect = new Button("Connect");
		connect.setId("connect");
		connect.setOnAction(new buttonHandler());
		connect.getStyleClass().add("darkButton");
		answersBox.getChildren().add(connect);

		/* Set the scene */
		primaryStage.setScene(mainScene);

		/* Show the main page */
		primaryStage.show();

	}

}
