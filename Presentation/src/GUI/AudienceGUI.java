package GUI;

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
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AudienceGUI extends Application {

	final private Image smartSlidesIcon = new Image("file:Single_S.png");
	private final Rectangle2D primaryBounds = Screen.getPrimary().getBounds();

	/* Cascading style sheet and colours */
	final private String styleSheet = "file:resources/styles/style1.css";

	private TextField serverAddress;
	private Client client;
	private String institutionCode;

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

			Button connectbtn = (Button) e.getSource();

			if (connectbtn.getId().equals("connect")) {

				String serverIP = getHexCodeIP(serverAddress.getText());

				System.out.println(institutionCode + "." + serverIP);

				client = new Client(institutionCode + "." + serverIP);

				/* Enable buttons */
				for (int i = 0; i < 4; i++) {
					btn[i].setDisable(false);
				}

			} else {
				/* send button ID to server */
				client.sendToServer(connectbtn.getId());
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
				System.out.println("inst code");
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
		Image smartSlidesImage = new Image("file:Smartslides_DarkText.png",
				primaryBounds.getWidth() * 0.1, 0, true, true);
		smartSlides.setImage(smartSlidesImage);

		answersBox.getChildren().add(smartSlides);

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

		ComboBox institution = new ComboBox(FXCollections.observableArrayList(
				"University of York", "Institution 2", "Institution 3"));

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
