/**
 *
 */
package server;

import java.util.List;

import sofia.VideoHandler;

import XML.XMLReader;
import Data.Graphic;
import Data.Image;
import Data.Slide;
import Data.Slideshow;
import Data.Text;
import Data.TextFragment;
import Data.Video;
import GUI.Alignment;
import GUI.GraphicsHandler;
import GUI.ImageHandler;
import GUI.TextHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author
 * @version 1.0 19/02/2015
 * 
 */
public class PresentationDisplayerTest extends Application {

	private String filename = "test.xml";
	private Group group;
	private GraphicsHandler graphicsHandler;
	private TextHandler thisTextHandler;
	private ImageHandler thisImageHandler;
	private VideoHandler thisVideoHandler;

	private List<Slide> slides;
	private int currentSlideInt = 1;
	private int numberOfSlides;

	private static Double xSize;
	private static Double ySize;

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
		primaryStage.setTitle("JavaFX Welcome");
		group = new Group();

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		xSize = primaryScreenBounds.getWidth();
		ySize = primaryScreenBounds.getHeight();

		Scene scene = new Scene(group, xSize, ySize);
		primaryStage.setScene(scene);
		/* Line sets the screen to fullscreen */
		// primaryStage.setFullScreen(true);

		/* Calls to add items to screen */
		thisImageHandler = new ImageHandler(group);
		thisTextHandler = new TextHandler(group);
		// GraphicsHandler thisGraphicsHandler = new GraphicsHandler(group);
		thisVideoHandler = new VideoHandler(group);

		Slideshow currentSlideshow = new XMLReader(filename).getSlideshow();

		System.out.println(currentSlideshow.getSlides());

		for (Slide currentSlide : currentSlideshow.getSlides()) {
			System.out.println("Current Slide: " + currentSlide);
			for (Text currentText : currentSlide.getTextList()) {
				System.out.println("	Current Text: " + currentText);
				for (TextFragment currentTextFragment : currentText.getTextFragments()) {
					System.out.println("		Current Text Fragment: " + currentTextFragment.getText());
				}
			}
			for (Image currentImage : currentSlide.getImagesList()) {
				System.out.println("	Current Image: " + currentImage.getSourceFile());
			}
			for (Graphic currentGraphic : currentSlide.getGraphicsList()) {
				System.out.println("	Current Graphic Coordinates: " + currentGraphic.getxStart() + " "
						+ currentGraphic.getyStart());
			}
		}
		slides = currentSlideshow.getSlides();
		numberOfSlides = slides.size();

		drawSlide(slides.get(0));

		primaryStage.show();
	}

	private int convertXRelToAbs(double coordinate) {
		return (int) (coordinate * xSize);
	}

	private int convertYRelToAbs(double coordinate) {
		return (int) (coordinate * ySize);
	}

	/** Method draws slide based upon what is in the datastructure */
	private void drawSlide(Slide currentSlide) {
		group.getChildren().clear();

		/* Text section */
		for (Text currentText : currentSlide.getTextList()) {
			System.out.println("	Current Text: " + currentText);
			for (TextFragment currentTextFragment : currentText.getTextFragments()) {
				System.out.println("		Current Text Fragment: " + currentTextFragment.getColor());
				thisTextHandler.addStringToBuffer(currentTextFragment.getText(), currentTextFragment.getFont(),
						(int) currentTextFragment.getFontSize(), currentTextFragment.getColor(), "#00000000", false);
			}

			int absXStart = convertXRelToAbs(currentText.getxStart());
			int absYStart = convertYRelToAbs(currentText.getyStart());
			int absXEnd = convertXRelToAbs(currentText.getxStart() * 1.5); // TODO
			int absYEnd = convertYRelToAbs(currentText.getyStart() * 1.4); // TODO

			thisTextHandler.drawBuffer(absXStart, absYStart, absXEnd, absYEnd, "#10aa0000", Alignment.LEFT);
		}
		
		/* Image section */
		for (Image currentImage : currentSlide.getImagesList()) {
			System.out.println("	Current Image: " + currentImage);
			thisImageHandler.drawImage(currentImage.getxStart(), currentImage.getxStart(),
					currentImage.getSourceFile(), currentImage.getScale(), currentImage.getScale(),
					currentImage.getRotation(), currentImage.getFlipVertical(), currentImage.getFlipHorizontal(),
					currentImage.getCropX1(), currentImage.getCropX2(), currentImage.getCropY1(),
					currentImage.getCropY2());
		}
		
		/* Image section */
		for (Video currentVideo : currentSlide.getMoviesList()) {
			System.out.println("	Current Image: " + currentVideo);
			int absXStart = convertXRelToAbs(currentVideo.getXStart());
			int absYStart = convertYRelToAbs(currentVideo.getYStart());
			System.out.println(absXStart + " " + absYStart + " " + currentVideo.getYStart());
			
			
			thisVideoHandler.createVideo(absXStart, absYStart, 500, currentVideo.getSourceFile(), true, true);
		}

		Button b1 = makeButton(200, 800, "Next");
		b1.setId("next");
		Button b2 = makeButton(100, 800, "Back");
		b2.setId("back");
		group.getChildren().addAll(b1, b2);
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
			Button buttonPressed = (Button) e.getSource();
			System.out.println("Current position: " + currentSlideInt);
			if (buttonPressed.getId() == "next") {
				if (currentSlideInt < numberOfSlides) {
					currentSlideInt++;
				}
			} else if (buttonPressed.getId() == "back") {
				if (currentSlideInt > 1) {
					currentSlideInt--;
				}
			}
			System.out.println("Next position: " + currentSlideInt);

			drawSlide(slides.get(currentSlideInt - 1));
		}
	}
}