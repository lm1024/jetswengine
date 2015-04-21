/** (c) Copyright by WaveMedia. */
package renderer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import imageHandler.ImageHandler;
import imageHandler.ImageObject;
import graphicsHandler.GraphicObject.GraphicBuilder;
import graphicsHandler.GraphicType;
import graphicsHandler.GraphicsHandler;
import graphsHandler.PieChartObject;
import graphsHandler.GraphHandler;
import sofia.audio.AudioHandler;
import sofia.video.VideoHandler;
import textHandler.Alignment;
import textHandler.TextFragmentList;
import textHandler.TextHandler;
import textHandler.TextObject;
import Data.Answer;
import Data.Audio;
import Data.CommonShapes.Oval;
import Data.CommonShapes.Rectangle;
import Data.Graphic;
import Data.Image;
import Data.OtherShapes.Arc;
import Data.OtherShapes.Arrow;
import Data.OtherShapes.Chord;
import Data.OtherShapes.Line;
import Data.OtherShapes.Polygon;
import Data.OtherShapes.Triangle;
import Data.Question;
import Data.RadialShapes.Circle;
import Data.RadialShapes.EquilateralTriangle;
import Data.RadialShapes.RegularPolygon;
import Data.RadialShapes.Square;
import Data.RadialShapes.Star;
import Data.Slide;
import Data.SlideItem;
import Data.Text;
import Data.TextFragment;
import Data.Video;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Class for rendering slides to the screen, and updating the visibility of
 * items on the slides.
 * 
 * @author tjd511
 * @version 1.0 21/04/2015
 */
public class SlideRenderer {

	/* Track the current stage and group. */
	private Stage stage;
	private Group group;

	/* Class level variables for the different handlers. */
	private GraphicsHandler graphicsHandler;
	private ImageHandler imageHandler;
	private TextHandler textHandler;
	private VideoHandler videoHandler;
	private AudioHandler audioHandler;
	private GraphHandler graphHandler;

	/*
	 * Class level variables to keep track of where the slide position is on the
	 * scene.
	 */
	private float xSlideStart;
	private float ySlideStart;
	private float slideWidth;
	private float slideHeight;

	/* Class level slide to keep track of the currently drawn slide. */
	private Slide currentSlide;

	/**
	 * Constructor method for the class.
	 * 
	 * @param stage
	 *            the stage that includes a group which all of the elements are
	 *            to be drawn on.
	 */
	public SlideRenderer(Stage stage) {
		this.stage = stage;

		/*
		 * Ensures that the stage has a scene for all of slide objects to be
		 * added to.
		 */
		if (stage.getScene() == null) {
			System.err.println("Stage passed to the slideRenderer has no scene, quitting.");
			System.exit(-1);
		}

		/* Gets the group of the stage. */
		this.group = (Group) stage.getScene().getRoot();

		this.graphicsHandler = new GraphicsHandler(group);
		this.imageHandler = new ImageHandler(group);
		this.textHandler = new TextHandler(group);
		this.videoHandler = new VideoHandler(group, new FullscreenEventHandler());
		this.audioHandler = new AudioHandler(group);
		this.graphHandler = new GraphHandler(group);
	}

	/**
	 * Updates the current dimensions for the slide.
	 * 
	 * @param xSlideStart
	 *            the starting x position of the slide, relative to the top left
	 *            of the scene.
	 * @param ySlideStart
	 *            the starting y position of the slide, relative to the top left
	 *            of the scene.
	 * @param slideWidth
	 *            the width of the slide.
	 * @param slideHeight
	 *            the height of the slide.
	 */
	public void updateSlideDimensions(double xSlideStart, double ySlideStart, double slideWidth, double slideHeight) {
		this.xSlideStart = (float) xSlideStart;
		this.ySlideStart = (float) ySlideStart;
		this.slideWidth = (float) slideWidth;
		this.slideHeight = (float) slideHeight;
	}

	/**
	 * Method clears the window, then draws the current slide.
	 * 
	 * @param currentSlide
	 *            the slide to be drawn.
	 */
	public void drawSlide(Slide currentSlide) {
		clear();
		this.currentSlide = currentSlide;
		for (SlideItem currentSlideItem : currentSlide.getAll()) {
			switch (currentSlideItem.getType()) {
			case "Text":
				addText((Text) currentSlideItem);
				break;
			case "Image":
				addImage((Image) currentSlideItem);
				break;
			case "Audio":
				addAudio((Audio) currentSlideItem);
				break;
			case "Video":
				addVideo((Video) currentSlideItem);
				break;
			default:
				/* Graphics */
				addGraphic((Graphic) currentSlideItem);
			}
		}
	}

	/**
	 * Method updates the visibility of the required items upon the slide if the
	 * update occurs at the time specified.
	 * 
	 * @param currentTimeIntoSlide
	 *            the time of the update on the slide.
	 */
	public void updateSlide(long currentTimeIntoSlide) {
		int currentTextNumber = 0;
		int currentImageNumber = 0;
		int currentGraphicNumber = 0;
		int currentAudioNumber = 0;
		int currentVideoNumber = 0;
		int currentObjectNumber;

		/*
		 * Loops through all the slide items in the slide, keeping track of how
		 * many of them there have been, then updates the visibility of them
		 * depending upon if they have to be displayed or hidden.
		 */
		for (SlideItem currentSlideItem : currentSlide.getAll()) {
			switch (currentSlideItem.getType()) {
			case "Text":
				currentTextNumber++;
				currentObjectNumber = currentTextNumber;
				break;
			case "Image":
				currentImageNumber++;
				currentObjectNumber = currentImageNumber;
				break;
			case "Audio":
				currentAudioNumber++;
				currentObjectNumber = currentAudioNumber;
				break;
			case "Video":
				currentVideoNumber++;
				currentObjectNumber = currentVideoNumber;
				break;
			default:
				/* Graphics */
				currentGraphicNumber++;
				currentObjectNumber = currentGraphicNumber;

			}

			/* Displays the current item. */
			if (currentSlideItem.getStartTime() == (float) currentTimeIntoSlide / 1000f) {
				updateVisibilityOfObject(currentSlideItem.getType(), currentObjectNumber - 1, true);
			}
			/* Hides the current item. */
			else if (currentSlideItem.getDuration() + currentSlideItem.getStartTime() == (float) currentTimeIntoSlide / 1000f) {
				updateVisibilityOfObject(currentSlideItem.getType(), currentObjectNumber - 1, false);
			}

		}
	}

	/**
	 * Method either hides or shows a slide object based upon the boolean flag.
	 * 
	 * @param objectType
	 *            a string containing the type of the object. TODO should be
	 *            enum?
	 * @param numberOfObject
	 *            the object number of the object to be updated in the handler.
	 * @param visible
	 *            boolean containing if the object should be hidden or
	 *            displayed.
	 */
	private void updateVisibilityOfObject(String objectType, int numberOfObject, boolean visible) {
		switch (objectType) {
		case "Text":
			textHandler.setVisible(numberOfObject, visible);
			break;
		case "Image":
			imageHandler.setVisible(numberOfObject, visible);
			break;
		case "Audio":
			audioHandler.setVisible(numberOfObject, visible);
			break;
		case "Video":
			videoHandler.setVisible(numberOfObject, visible);
			break;
		default:
			graphicsHandler.setVisible(numberOfObject, visible);
		}
	}

	/**
	 * Adds a graphic to the screen using the graphics handler.
	 * 
	 * @param currentGraphic
	 *            the graphic object containing the data about the shape to be
	 *            drawn.
	 */
	private void addGraphic(Graphic currentGraphic) {
		/* Get the x and y start coordinates, which are common to all graphics. */
		float xStartPos = convXRelCoordToAbsCoord(currentGraphic.getXStart());
		float yStartPos = convYRelCoordToAbsCoord(currentGraphic.getYStart());

		/*
		 * The graphicsbuilder object to be used to create the graphics object
		 * to be passed to the graphics handler.
		 */
		GraphicBuilder graphicBuilder;

		/*
		 * Converts the type of shape to an enum of all of the shape types to be
		 * used in a switch case.
		 */
		GraphicType graphicType = GraphicType.valueOf(currentGraphic.getType().toUpperCase());

		switch (graphicType) {
		case ARC:
			/*
			 * Casts the graphic to the arc type to access the info specfic to
			 * that type of shape.
			 */
			Arc arc = (Arc) currentGraphic;

			/* Build the graphics builder object with all of the shape info. */
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.width(arc.getWidth())
				.height(arc.getHeight())
				.arcAngle(arc.getArcAngle())
				.length(arc.getLength())
				.color(arc.getGraphicColor())
				.solid(arc.isSolid())
				.outlineColor(arc.getOutlineColor())
				.outlineThickness(arc.getOutlineThickness())
				.shadow(arc.getShadow())
				.rotation(arc.getRotation())
				.shadingType(arc.getShadingType());
			break;
		case ARROW:
			Arrow arrow = (Arrow) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.xEndPos(convXRelCoordToAbsCoord(arrow.getXEnd()))
				.yEndPos(convYRelCoordToAbsCoord(arrow.getYEnd()))
				.color(arrow.getGraphicColor())
				.shadingType(arrow.getShadingType());
			break;
		case CHORD:
			Chord chord = (Chord) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.width(chord.getWidth())
				.height(chord.getHeight())
				.arcAngle(chord.getArcAngle())
				.length(chord.getLength())
				.color(chord.getGraphicColor())
				.solid(chord.isSolid())
				.outlineColor(chord.getOutlineColor())
				.outlineThickness(chord.getOutlineThickness())
				.shadow(chord.getShadow())
				.rotation(chord.getRotation())
				.shadingType(chord.getShadingType());
			break;
		case CIRCLE:
			Circle circle = (Circle) currentGraphic;
			/*
			 * TODO circle . getRadius ( )
			 */
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.radius(10)
				.color(circle.getGraphicColor())
				.solid(circle.isSolid())
				.outlineColor(circle.getOutlineColor())
				.outlineThickness(circle.getOutlineThickness())
				.shadow(circle.getShadow())
				.rotation(circle.getRotation())
				.shadingType(circle.getShadingType());
			break;
		case EQUILATERALTRIANGLE:
			EquilateralTriangle eTri = (EquilateralTriangle) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.length(eTri.getSize())
				.color(eTri.getGraphicColor())
				.solid(eTri.isSolid())
				.outlineColor(eTri.getOutlineColor())
				.outlineThickness(eTri.getOutlineThickness())
				.shadow(eTri.getShadow())
				.rotation(eTri.getRotation())
				.shadingType(eTri.getShadingType());
			break;
		case LINE:
			Line line = (Line) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.xEndPos(convXRelCoordToAbsCoord(line.getXEnd()))
				.yEndPos(convYRelCoordToAbsCoord(line.getYEnd()))
				.color(line.getGraphicColor())
				.outlineThickness(line.getThickness())
				.shadow(line.getShadow())
				.shadingType(line.getShadingType());
			break;
		case OVAL:
			Oval oval = (Oval) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.color(oval.getGraphicColor())
				.solid(oval.isSolid())
				.outlineColor(oval.getOutlineColor())
				.outlineThickness(oval.getOutlineThickness())
				.shadow(oval.getShadow())
				.rotation(oval.getRotation())
				.shadingType(oval.getShadingType());
			break;
		case POLYGON:
			Polygon pol = (Polygon) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.color(pol.getGraphicColor())
				.solid(pol.isSolid())
				.outlineColor(pol.getOutlineColor())
				.outlineThickness(pol.getOutlineThickness())
				.shadow(pol.getShadow())
				.rotation(pol.getRotation())
				.shadingType(pol.getShadingType());

			/* Add all the points of the polygon to the graphic builder */
			for (int i = 0; i < pol.getxPoints().size(); i++) {
				graphicBuilder.polygonCoordinate(
					convXRelCoordToAbsCoord(pol.getxPoints().get(i)),
					convYRelCoordToAbsCoord(pol.getyPoints().get(i)));
			}

			break;
		case REGULARPOLYGON:
			RegularPolygon regPol = (RegularPolygon) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.numberOfSides(regPol.getNumberOfSides())
				.color(regPol.getGraphicColor())
				.solid(regPol.isSolid())
				.outlineColor(regPol.getOutlineColor())
				.outlineThickness(regPol.getOutlineThickness())
				.shadow(regPol.getShadow())
				.rotation(regPol.getRotation())
				.shadingType(regPol.getShadingType());

			break;
		case SQUARE:
			Square square = (Square) currentGraphic;
			/*
			 * TODO square . getLength ( )
			 */
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.length(10)
				.color(square.getGraphicColor())
				.solid(square.isSolid())
				.outlineColor(square.getOutlineColor())
				.outlineThickness(square.getOutlineThickness())
				.shadow(square.getShadow())
				.rotation(square.getRotation())
				.shadingType(square.getShadingType());
			break;
		case STAR:
			Star star = (Star) currentGraphic;
			/*
			 * TODO this needs fixing star . getNumberOfPoints ( )
			 */
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.numberOfPoints(5)
				.color(star.getGraphicColor())
				.solid(star.isSolid())
				.outlineColor(star.getOutlineColor())
				.outlineThickness(star.getOutlineThickness())
				.shadow(star.getShadow())
				.rotation(star.getRotation())
				.shadingType(star.getShadingType());
			break;
		case TRIANGLE:
			Triangle triangle = (Triangle) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
				.triangleCoordinates(
					convXRelCoordToAbsCoord(triangle.getxPoints().get(0)),
					convYRelCoordToAbsCoord(triangle.getyPoints().get(0)),
					convXRelCoordToAbsCoord(triangle.getxPoints().get(1)),
					convYRelCoordToAbsCoord(triangle.getyPoints().get(1)),
					convXRelCoordToAbsCoord(triangle.getxPoints().get(2)),
					convYRelCoordToAbsCoord(triangle.getyPoints().get(2)))
				.color(triangle.getGraphicColor())
				.solid(triangle.isSolid())
				.outlineColor(triangle.getOutlineColor())
				.outlineThickness(triangle.getOutlineThickness())
				.shadow(triangle.getShadow())
				.rotation(triangle.getRotation())
				.shadingType(triangle.getShadingType());
			break;
		default:
			/* For any other shape, by default make a rectangle */
			Rectangle rectangle = (Rectangle) currentGraphic;
			rectangle.printItem();
			graphicBuilder = new GraphicBuilder(GraphicType.RECTANGLE, xStartPos, yStartPos)
				.xEndPos(convXRelCoordToAbsCoord(rectangle.getXEnd()))
				.yEndPos(convYRelCoordToAbsCoord(rectangle.getYEnd()))
				.arcWidth(rectangle.getArcWidth())
				.arcHeight(rectangle.getArcHeight())
				.color(rectangle.getGraphicColor())
				.solid(rectangle.isSolid())
				.outlineColor(rectangle.getOutlineColor())
				.outlineThickness(rectangle.getOutlineThickness())
				.shadow(rectangle.getShadow())
				.rotation(rectangle.getRotation())
				.shadingType(rectangle.getShadingType());
			break;

		}

		/*
		 * Loop through all the stops (shading colors and distances) for the
		 * current graphic and add them to the graphicHandler.
		 */
		if (currentGraphic.getStopValuesList() != null) {
			for (int i = 0; i < currentGraphic.getStopValuesList().size(); i++) {
				graphicBuilder.shadingElement(currentGraphic.getShadingList().get(i), currentGraphic
					.getStopValuesList()
					.get(i));
			}
		}

		/* Create the shape using the graphics handler. */
		graphicsHandler.createGraphic(graphicBuilder.build());

		/*
		 * If the graphic has a start time tag, hide the object. Will be shown
		 * later by the updateSlide class.
		 */
		if (currentGraphic.getStartTime() != 0) {
			graphicsHandler.setVisible(graphicsHandler.getGraphicCount() - 1, false);
		}
	}

	/**
	 * Method to add a text box to the screen using the text handler.
	 * 
	 * @param currentText
	 *            the text object containing information about the text box to
	 *            be drawn.
	 */
	private void addText(Text currentText) {
		float xStart = convXRelCoordToAbsCoord(currentText.getXStart());
		float yStart = convYRelCoordToAbsCoord(currentText.getYStart());
		float xEnd = convXRelCoordToAbsCoord(currentText.getXEnd());
		float yEnd = convYRelCoordToAbsCoord(currentText.getYEnd());
		String backgroundColor = currentText.getBackgroundColor();
		Alignment alignment = Alignment.valueOf(currentText.getAlignment().toUpperCase());

		/*
		 * Initialise a new textFragmentList for passing to the text box
		 * builder.
		 */
		TextFragmentList textFragmentList = new TextFragmentList();

		/*
		 * Loop through all the text fragments for the box, adding them to the
		 * textFragmentList.
		 */
		for (TextFragment currentFragment : currentText.getTextFragments()) {
			boolean bold = currentFragment.isBold();
			boolean underline = currentFragment.isUnderlined();
			boolean italic = currentFragment.isItalicised();
			boolean superscript = currentFragment.isSuperscript();
			boolean subscript = currentFragment.isSubscript();
			boolean strikethrough = currentFragment.isStrikethrough();
			boolean newline = currentFragment.isBold();
			String fontColor = currentFragment.getFontColor();
			String highlightColor = currentFragment.getHighlightColor();
			String font = currentFragment.getFont();
			double fontSize = currentFragment.getFontSize();
			String text = currentFragment.getText();

			/* Add the attributes to the list using the textFragmentBuilder. */
			textFragmentList.add(new TextObject.TextFragmentBuilder(text)
				.bold(bold)
				.underline(underline)
				.italic(italic)
				.superscript(superscript)
				.subscript(subscript)
				.strikethrough(strikethrough)
				.newline(newline)
				.fontColor(fontColor)
				.highlightColor(highlightColor)
				.fontName(font)
				.fontSize((int) fontSize)
				.build());
		}

		/*
		 * Create the text object using the text box builder and pass it to the
		 * text handler to be drawn on screen.
		 */
		textHandler.createTextbox(new TextObject.TextBoxBuilder(xStart, yStart)
			.xEnd(xEnd)
			.yEnd(yEnd)
			.backgroundColor(backgroundColor)
			.alignment(alignment)
			.textFragmentList(textFragmentList)
			.build());

		/*
		 * If the text box has a start time tag, hide the object. Will be shown
		 * later by the updateSlide class.
		 */
		if (currentText.getStartTime() != 0) {
			textHandler.setVisible(textHandler.getTextCount() - 1, false);
		}
	}

	/**
	 * 
	 * 
	 * @param currentImage
	 *            the image object containing information about the image to be
	 *            drawn.
	 */
	private void addImage(Image currentImage) {
		String filepath = currentImage.getSourceFile();
		float xStartPos = convXRelCoordToAbsCoord(currentImage.getXStart());
		float yStartPos = convYRelCoordToAbsCoord(currentImage.getYStart());
		int rotation = currentImage.getRotation(); // TODO should be float
		float scale = currentImage.getScale();
		float cropX1 = currentImage.getCropX1();
		float cropY1 = currentImage.getCropY1();
		float cropX2 = currentImage.getCropX2();
		float cropY2 = currentImage.getCropY2();
		boolean flipHorizontal = currentImage.getFlipHorizontal();
		boolean flipVertical = currentImage.getFlipVertical();

		// TODO Image effects, and scale in 2 directions.

		/*
		 * Build the image object using the builder and pass it to the handler
		 * to be drawn to screen.
		 */
		imageHandler.createImage(new ImageObject.ImageBuilder(filepath, xStartPos, yStartPos)
			.rotation(rotation)
			.scaleX(scale)
			.scaleY(scale)
			.cropLeft(cropX1)
			.cropRight(cropX2)
			.cropDown(cropY1)
			.cropUp(cropY2)
			.hFlip(flipHorizontal)
			.vFlip(flipVertical)
			.build());

		/*
		 * If the image has a start time tag, hide it. Will be shown again later
		 * by the updateSlide method.
		 */
		if (currentImage.getStartTime() != 0) {
			imageHandler.setVisible(imageHandler.getImageCount() - 1, false);
		}
	}

	/**
	 * Method to add audio to the screen using sofia's audio handler.
	 * 
	 * @param currentAudio
	 *            the audio object containing information about the object to be
	 *            drawn.
	 */
	private void addAudio(Audio currentAudio) {
		float x = convXRelCoordToAbsCoord(currentAudio.getXStart());
		float y = convYRelCoordToAbsCoord(currentAudio.getYStart());
		float width = 400; // TODO currentAudio.getWidth();

		String sourceFile = currentAudio.getSourceFile();

		boolean loop = false;// TODO currentAudio.isLoop();
		boolean autoPlay = false;// TODO currentAudio.isAutoPlay();
		boolean visibleControls = true;// TODO currentAudio.isVisibleControls();
		boolean playButtonOnly = false;// TODO currentAudio.isPlayButtonOnly();

		audioHandler.createAudio(x, y, width, sourceFile, loop, autoPlay, visibleControls, playButtonOnly);

		/*
		 * Hides and pauses the audio if it has a start time tag. TODO
		 * isautoplay??
		 */
		if (currentAudio.getStartTime() != 0) {
			audioHandler.pauseAudio(audioHandler.getAudioCount() - 1);
			audioHandler.setVisible(audioHandler.getAudioCount() - 1, false);
		}
	}

	/**
	 * Method to add video to the screen using sofia's video handler.
	 * 
	 * @param currentVideo
	 *            the video object containing information about the video object
	 *            to be drawn.
	 */
	private void addVideo(Video currentVideo) {
		float x = convXRelCoordToAbsCoord(currentVideo.getXStart());
		float y = convYRelCoordToAbsCoord(currentVideo.getYStart());
		float width = 400; // TODO currentVideo.getWidth();
		String sourceFile = currentVideo.getSourceFile();
		boolean autoPlay = false; // TODO currentVideo.isAutoPlay();
		boolean loop = false; // TODO currentVideo.isLoop();

		videoHandler.createVideo(x, y, width, sourceFile, autoPlay, loop);

		/* Hides and pauses the video if it has a start time tag. */
		if (currentVideo.getStartTime() != 0) {
			videoHandler.pauseVideo(videoHandler.getVideoCount() - 1);
			videoHandler.setVisible(videoHandler.getVideoCount() - 1, false);
		}
	}

	/**
	 * Converts a relative (0 to 1) coordinate in the x dimension to an absolute
	 * slide position using the current slide size.
	 * 
	 * @param x
	 *            the relative x position.
	 * @return the absolute x position.
	 */
	private float convXRelCoordToAbsCoord(float x) {
		return (xSlideStart + x * slideWidth);
	}

	/**
	 * Converts a relative (0 to 1) coordinate in the y dimension to an absolute
	 * slide position using the current slide size.
	 * 
	 * @param y
	 *            the relative y position.
	 * @return the absolute y position.
	 */
	private float convYRelCoordToAbsCoord(float y) {
		return (ySlideStart + y * slideHeight);
	}

	/**
	 * Clears all of the handlers.
	 */
	public void clear() {
		/* Clear all the handlers */
		audioHandler.clearAudios();
		videoHandler.clearVideos();
		imageHandler.clearImages();
		textHandler.clearTexts();
		graphicsHandler.clearGraphics();
		group.getChildren().clear();
	}

	/**
	 * Builds the answer slide from the data stored in a question. Should
	 * validate that there is answer data in the question object by using the
	 * method question.hasAnswerData() before using this method.
	 * 
	 * @param question
	 */
	public void buildAnswerSlide(Question question) {
		PieChartObject answerChart = new PieChartObject();

		/*
		 * Sets the width and the height of the chart to take up 70% of each
		 * dimension.
		 */
		double chartWidth = convXRelCoordToAbsCoord(0.7f);
		double chartHeight = convYRelCoordToAbsCoord(0.7f);

		answerChart.setPrefSize(chartWidth, chartHeight);

		/* Sets the title to be displayed above the data. */
		answerChart.setTitle(question.getId());

		/* Sets the chart to appear in the middle of the screen. */
		answerChart.setxStartPos((float) (convXRelCoordToAbsCoord(0.5f) - chartWidth / 2));
		answerChart.setyStartPos((float) (convYRelCoordToAbsCoord(0.5f) - chartHeight / 2));

		/* Initialise the new arraylists for storing the answer data. */
		ArrayList<String> answerNames = new ArrayList<String>();
		ArrayList<Float> answerValues = new ArrayList<Float>();

		/*
		 * Loop through all the answers, adding them and their count to the
		 * answer data arrays.
		 */
		for (Answer tempAnswer : question.getAnswers()) {
			answerNames.add(tempAnswer.getId());
			answerValues.add((float) tempAnswer.getAnswerCount());
		}
		/* Add the answer data to the pie charts. */
		answerChart.setPieChartData(answerNames, answerValues);

		/*
		 * Pass the populated answerChart object to the graph handler to be
		 * drawn.
		 */
		graphHandler.drawPieChart(answerChart);
	}

	/**
	 * EventHandler class for passing to the video handler, which sets the
	 * slideshow to fullscreen after being changed by a video going fullscreen.
	 * 
	 * @author tjd511
	 */
	private class FullscreenEventHandler implements EventHandler<WindowEvent> {
		@Override
		public void handle(WindowEvent arg0) {
			/*
			 * Set the screen to fullscreen again. Just calling
			 * setFullScreen(true) does not set the window fullscreen, even
			 * though stage.isFullScreen() returns true.
			 */
			stage.setFullScreen(false);
			stage.setFullScreen(true);
		}
	}
}
