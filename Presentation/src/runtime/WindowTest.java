/**
 *
 */
package runtime;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * @author
 * @version 1.0 19/02/2015
 * 
 */
public class WindowTest extends Application {
	private final double xDim = 4;
	private final double yDim = 3;

	private Stage stage;
	private Rectangle rect;

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
		/* Set the title of the window */
		stage = primaryStage;
		primaryStage.setTitle("JavaFX Welcome");
		Group group = new Group();

		// Button button = makeButton(0, 0, "Calculate");
		// group.getChildren().add(button);

		rect = new Rectangle(0, 0, 0, 0);
		group.getChildren().add(rect);

		Scene scene = new Scene(group, 400, 400);

		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
				updateRect();
			}
		});

		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				updateRect();
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setFullScreen(true);
		updateRect();
		// System.out.println("scene X Coordinate: " + scene.getX() +
		// " scene Y Coordinate: " + scene.getY());
		// System.out.println("scene Height: " + scene.getHeight() +
		// " scene Width: " + scene.getWidth());
	}

	/**
	 * Updates size of a rectangle to be of the ratio specified in final
	 * variables xDim and yDim
	 */
	private void updateRect() {
		double sceneHeight = stage.getScene().getHeight();
		double sceneWidth = stage.getScene().getWidth();

		double rectHeight;
		double rectWidth;
		double xPos;
		double yPos;

		if (sceneWidth / xDim < sceneHeight / yDim) {
			rectHeight = ((sceneWidth / xDim) * yDim);
			rectWidth = sceneWidth;
			xPos = 0;
			yPos = (sceneHeight - rectHeight) / 2;
		} else {
			rectHeight = sceneHeight;
			rectWidth = (((sceneHeight) / yDim) * xDim);
			xPos = (sceneWidth - rectWidth) / 2;
			yPos = 0;
		}

		rect.setX(xPos);
		rect.setY(yPos);
		rect.setHeight(rectHeight);
		rect.setWidth(rectWidth);
	}

	/** Utility function for adding button */
	private Button makeButton(double xPos, double yPos, String buttonText) {
		/* Button section */
		Button btn = new Button();
		btn.setText(buttonText);
		btn.relocate(xPos, yPos);
		btn.setOnAction(new buttonEventHandler());
		return btn;
	}

	/**
	 * Private class written so that multiple buttonEvents can be handled
	 * easily.
	 */
	private class buttonEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			// Button buttonPressed = (Button) e.getSource();
			updateRect();
			System.out.println("X Coordinate: " + stage.getX() + " Y Coordinate: " + stage.getY());
			System.out.println("Height: " + stage.getHeight() + " Width: " + stage.getWidth());
		}
	}
}