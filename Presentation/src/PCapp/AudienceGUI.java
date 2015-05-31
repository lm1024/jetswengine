package PCapp;

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

	final private Image smartSlidesIcon = new Image("file:img/Single_S.png");
	private final Rectangle2D primaryBounds = Screen.getPrimary().getBounds();

	/* Cascading style sheet and colours */
	final private String styleSheet = "file:resources/styles/style1.css";

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
		int hexCode2 = Integer.parseInt(hexCode,16);
		return (hexCode2 >> 8) + "." + (hexCode2 & 0x00FF);
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
		
		System.out.println("HELLO " + s);
		System.out.println(val);
		System.out.println(Integer.parseInt(s, 16));
		
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

				if (serverAddress.getText().matches("^([0-9a-fA-F]){4}$")
						&& (institutionCode != null)) {
					String serverIP = getHexCodeIP(serverAddress.getText());

					System.out.println(institutionCode + "." + serverIP);

					client = new Client(institutionCode + "." + serverIP);

					/* Enable buttons */
					for (int i = 0; i < 4; i++) {
						btn[i].setDisable(false);
					}
					questionSubmit.setDisable(false);
					
				}

			} else if (btnPressed.getId().equals("submit")) {
				System.out.println("submit pressed");
				client.sendToServerWithoutIP(userQuestion.getText());
				userQuestion.clear();

			} else {

				/* send button ID to server */
				client.sendToServerWithIP(btnPressed.getId());
			}
		}
	}
	

	private class comboListener implements ChangeListener<Number> {

		@Override
		public void changed(ObservableValue<? extends Number> arg0,
				Number arg1, Number arg2) {

			System.out.println(arg2);

			switch (arg2.toString()) {
			case "0":
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

		/* creates a scene within the stage */
		Scene mainScene = new Scene(answersBox);

		/* add the stylesheet */
		mainScene.getStylesheets().add(styleSheet);

		answersBox.setAlignment(Pos.CENTER);
		answersBox.getStyleClass().add("invisiBox");
		answersBox.setSpacing(primaryBounds.getHeight() * 0.01);

		ImageView smartSlides = new ImageView();
		Image smartSlidesImage = new Image("file:img/Smartslides_DarkText.png",
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

		/* add 4 buttons */
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
