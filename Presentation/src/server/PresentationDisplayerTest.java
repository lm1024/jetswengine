/**
 *
 */
package server;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import sofia.AudioHandler;
import sofia.VideoHandler;

import XML.ImprovedXMLReader;
import Data.Audio;
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
import GUI.Shading;
import GUI.Shadow;
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
	private GraphicsHandler thisGraphicsHandler;
	private TextHandler thisTextHandler;
	private ImageHandler thisImageHandler;
	private VideoHandler thisVideoHandler;
	private AudioHandler thisAudioHandler;

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

		Scene scene = new Scene(group, xSize - 100, ySize - 100);
		primaryStage.setScene(scene);
		/* Line sets the screen to fullscreen */
		// primaryStage.setFullScreen(true);

		/* Calls to add items to screen */
		thisImageHandler = new ImageHandler(group);
		thisTextHandler = new TextHandler(group);
		thisGraphicsHandler = new GraphicsHandler(group);
		thisVideoHandler = new VideoHandler(group);
		thisAudioHandler = new AudioHandler(group);

		Slideshow currentSlideshow = new ImprovedXMLReader(filename).getSlideshow();

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
				System.out.println("	Current Graphic Coordinates: " + currentGraphic.getXStart() + " "
						+ currentGraphic.getYStart());
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
		thisVideoHandler.clearVideos();
		thisAudioHandler.clearAudios();
		group.getChildren().clear();
		System.out.println("");
		System.out.println("	New slide!");
		long startTextTime = System.nanoTime();
		
		/* Text section */
		for (Text currentText : currentSlide.getTextList()) {
			//System.out.println("	Current Text: " + currentText);
			for (TextFragment currentTextFragment : currentText.getTextFragments()) {
				//System.out.println("		Current Text Fragment: " + currentTextFragment.getFontColor());
				thisTextHandler
						.addStringToBuffer(currentTextFragment.getText(), currentTextFragment.getFont(),
								(int) currentTextFragment.getFontSize(), currentTextFragment.getFontColor(),
								"#00000000", false);
			}

			int absXStart = convertXRelToAbs(currentText.getXStart());
			int absYStart = convertYRelToAbs(currentText.getYStart());
			int absXEnd = convertXRelToAbs(currentText.getXStart() * 1.5); // TODO
			int absYEnd = convertYRelToAbs(currentText.getYStart() * 1.4); // TODO

			thisTextHandler.drawBuffer(absXStart, absYStart, absXEnd, absYEnd, "#ffaa0000", Alignment.LEFT);
		}
		long endTextTime = System.nanoTime();
		System.out.println("Text Execution time: " + TimeUnit.NANOSECONDS.toMillis(endTextTime - startTextTime) + "ms");

		long startImageTime = System.nanoTime();
		/* Image section */
		for (Image currentImage : currentSlide.getImagesList()) {
			//System.out.println("	Current Image: " + currentImage.getSourceFile());

			int absXStart = convertXRelToAbs(currentImage.getXStart());
			int absYStart = convertYRelToAbs(currentImage.getYStart());

			thisImageHandler.drawImage(absXStart, absYStart, currentImage.getSourceFile(), currentImage.getScale(),
					currentImage.getScale(), currentImage.getRotation(), currentImage.getFlipVertical(),
					currentImage.getFlipHorizontal(), currentImage.getCropX1(), currentImage.getCropX2(),
					currentImage.getCropY1(), currentImage.getCropY2());
		}
		long endImageTime = System.nanoTime();
		System.out.println("Image Execution time: " + TimeUnit.NANOSECONDS.toMillis(endImageTime - startImageTime) + "ms");

		long startGraphicTime = System.nanoTime();
		/* Graphics section */
		for (Graphic currentGraphic : currentSlide.getGraphicsList()) {
			//System.out.println("	Current Graphic: " + currentGraphic.getType());

			switch (currentGraphic.getType()) {
			case "Oval":
				break;
			case "Rectangle":
				int absXStart = convertXRelToAbs(currentGraphic.getXStart());
				int absYStart = convertYRelToAbs(currentGraphic.getYStart());
				int absXEnd = convertXRelToAbs(currentGraphic.getXStart() * 1.5); // TODO
				int absYEnd = convertYRelToAbs(currentGraphic.getYStart() * 1.4); // TODO
				thisGraphicsHandler.drawRectangle(absXStart, absYStart, absXEnd, absYEnd, 0, 0,
						currentGraphic.getGraphicColor(), true, "#00000000", 1, Shadow.NORMAL, 0, Shading.NONE);
				break;
			case "Line":
				break;
			default:
				break;
			}

		}
		long endGraphicsTime = System.nanoTime();
		System.out.println("Graphic Execution time: " + TimeUnit.NANOSECONDS.toMillis(endGraphicsTime - startGraphicTime) + "ms");
		
		long startVideoTime = System.nanoTime();
		/* Video section */
		for (Video currentVideo : currentSlide.getVideosList()) {
			//System.out.println("	Current Image: " + currentVideo);
			int absXStart = convertXRelToAbs(currentVideo.getXStart());
			int absYStart = convertYRelToAbs(currentVideo.getYStart());
			thisVideoHandler.createVideo(absXStart, absYStart, 500, currentVideo.getSourceFile(), true, true);
		}
		long endVideoTime = System.nanoTime();
		System.out.println("Video Execution time: " + TimeUnit.NANOSECONDS.toMillis(endVideoTime - startVideoTime) + "ms");
		
		long startAudioTime = System.nanoTime();
		/* Audio section */
		for (Audio currentAudio : currentSlide.getAudiosList()) {
			//System.out.println("	Current Audio: " + currentAudio.getSourceFile());
			int absXStart = convertXRelToAbs(currentAudio.getXStart());
			int absYStart = convertYRelToAbs(currentAudio.getYStart());
			thisAudioHandler.createAudio(absXStart, absYStart, 200, new File(currentAudio.getSourceFile()), false,
					true, true);
		}
		long endAudioTime = System.nanoTime();
		System.out.println("Audio Execution time: " + TimeUnit.NANOSECONDS.toMillis(endAudioTime - startAudioTime) + "ms");
		
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
			if (buttonPressed.getId() == "next") {
				if (currentSlideInt < numberOfSlides) {
					currentSlideInt++;
				}
			} else if (buttonPressed.getId() == "back") {
				if (currentSlideInt > 1) {
					currentSlideInt--;
				}
			}
			drawSlide(slides.get(currentSlideInt - 1));
		}
	}
}