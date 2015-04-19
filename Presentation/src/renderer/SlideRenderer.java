/**
 * 
 */
package renderer;

import java.io.File;
import java.util.ArrayList;

import imageHandler.ImageHandler;
import imageHandler.ImageObject;
import graphicsHandler.GraphicObject.GraphicBuilder;
import graphicsHandler.GraphicType;
import graphicsHandler.GraphicsHandler;
import graphicsHandler.Shading;
import graphicsHandler.Shadow;
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
import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * @author Fiery
 * 
 */
public class SlideRenderer {

	private Group group;

	private GraphicsHandler graphicsHandler;
	private ImageHandler imageHandler;
	private TextHandler textHandler;
	private VideoHandler videoHandler;
	private AudioHandler audioHandler;
	private GraphHandler graphHandler;

	private float xSlideStart;
	private float ySlideStart;
	private float slideWidth;
	private float slideHeight;

	private Slide currentSlide;

	public SlideRenderer(Group group) {
		this.group = group;
		this.graphicsHandler = new GraphicsHandler(group);
		this.imageHandler = new ImageHandler(group);
		this.textHandler = new TextHandler(group);
		this.videoHandler = new VideoHandler(group);
		this.audioHandler = new AudioHandler(group);
		this.graphHandler = new GraphHandler(group);
	}

	public void updateSlideDimentions(double xSlideStart, double ySlideStart, double slideWidth, double slideHeight) {
		System.out.println(xSlideStart + " " + ySlideStart + " " + slideWidth + " " + slideHeight);
		this.xSlideStart = (float) xSlideStart;
		this.ySlideStart = (float) ySlideStart;
		this.slideWidth = (float) slideWidth;
		this.slideHeight = (float) slideHeight;
	}

	public void drawSlide(Slide currentSlide) {
		clear();
		System.out.println("");
		System.out.println("");
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

	public void updateSlide(long currentTimeIntoSlide) {
		int currentTextNumber = 0;
		int currentImageNumber = 0;
		int currentGraphicNumber = 0;
		int currentAudioNumber = 0;
		int currentVideoNumber = 0;
		int currentObjectNumber;
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

			if (currentSlideItem.getStartTime() == (float) currentTimeIntoSlide / 1000f) {
				updateVisibilityOfObject(currentSlideItem.getType(), currentObjectNumber - 1, true);
				System.out.println("Showing a " + currentSlideItem.getType() + " object!");
			} else if (currentSlideItem.getDuration() + currentSlideItem.getStartTime() == (float) currentTimeIntoSlide / 1000f) {
				updateVisibilityOfObject(currentSlideItem.getType(), currentObjectNumber - 1, false);
				System.out.println("Removing a " + currentSlideItem.getType() + " object!");
			}

		}
	}

	private void updateVisibilityOfObject(String objectType, int numberOfObject, boolean visible) {
		switch (objectType) {
		case "Text":
			textHandler.setVisible(numberOfObject, visible);
			break;
		case "Image":
			imageHandler.setVisible(numberOfObject, visible);
			break;
		case "Audio":
			audioHandler.setVisible(numberOfObject, visible); //TODO
			break;
		case "Video":
			videoHandler.setVisible(numberOfObject, visible);
			videoHandler.playVideo(numberOfObject);
			break;
		default:
			graphicsHandler.setVisible(numberOfObject, visible);
		}
	}

	private void addGraphic(Graphic currentGraphic) {
		/* */
		GraphicType graphicType = GraphicType.valueOf(currentGraphic.getType().toUpperCase());

		float xStartPos = convXRelCoordToAbsCoord(currentGraphic.getXStart());
		float yStartPos = convYRelCoordToAbsCoord(currentGraphic.getYStart());

		GraphicBuilder graphicBuilder;

		switch (graphicType) {
		case ARC:
			Arc arc = (Arc) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos).width(arc.getWidth())
					.height(arc.getHeight()).arcAngle(arc.getArcAngle()).length(arc.getLength())
					.color(arc.getGraphicColor()).solid(arc.isSolid()).outlineColor(arc.getOutlineColor())
					.outlineThickness(arc.getOutlineThickness()).shadow(arc.getShadow()).rotation(arc.getRotation())
					.shadingType(arc.getShadingType());
			break;
		case ARROW:
			Arrow arrow = (Arrow) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos).xEndPos(convXRelCoordToAbsCoord(arrow.getXEnd()))
					.yEndPos(convYRelCoordToAbsCoord(arrow.getYEnd())).color(arrow.getGraphicColor()).shadingType(arrow.getShadingType());
			break;
		case CHORD:
			Chord chord = (Chord) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos).width(chord.getWidth())
					.height(chord.getHeight()).arcAngle(chord.getArcAngle()).length(chord.getLength())
					.color(chord.getGraphicColor()).solid(chord.isSolid()).outlineColor(chord.getOutlineColor())
					.outlineThickness(chord.getOutlineThickness()).shadow(chord.getShadow())
					.rotation(chord.getRotation()).shadingType(chord.getShadingType());
			break;
		case CIRCLE:
			Circle circle = (Circle) currentGraphic;
			/*
			 * TODO circle . getRadius ( )
			 */
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos).radius(10)
					.color(circle.getGraphicColor()).solid(circle.isSolid()).outlineColor(circle.getOutlineColor())
					.outlineThickness(circle.getOutlineThickness()).shadow(circle.getShadow())
					.rotation(circle.getRotation()).shadingType(circle.getShadingType());
			break;
		case EQUILATERALTRIANGLE:
			EquilateralTriangle eTri = (EquilateralTriangle) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos).length(eTri.getSize())
					.color(eTri.getGraphicColor()).solid(eTri.isSolid()).outlineColor(eTri.getOutlineColor())
					.outlineThickness(eTri.getOutlineThickness()).shadow(eTri.getShadow()).rotation(eTri.getRotation())
					.shadingType(eTri.getShadingType());
			break;
		case LINE:
			Line line = (Line) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos).xEndPos(convXRelCoordToAbsCoord(line.getXEnd()))
					.yEndPos(convYRelCoordToAbsCoord(line.getYEnd())).color(line.getGraphicColor()).outlineThickness(line.getThickness())
					.shadow(line.getShadow()).shadingType(line.getShadingType());
			break;
		case OVAL:
			Oval oval = (Oval) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos).color(oval.getGraphicColor())
					.solid(oval.isSolid()).outlineColor(oval.getOutlineColor())
					.outlineThickness(oval.getOutlineThickness()).shadow(oval.getShadow()).rotation(oval.getRotation())
					.shadingType(oval.getShadingType());
			break;
		case POLYGON:
			Polygon pol = (Polygon) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos).color(pol.getGraphicColor())
					.solid(pol.isSolid()).outlineColor(pol.getOutlineColor())
					.outlineThickness(pol.getOutlineThickness()).shadow(pol.getShadow()).rotation(pol.getRotation())
					.shadingType(pol.getShadingType());

			/* Add all the points of the polygon to the graphic builder */
			for (int i = 0; i < pol.getxPoints().size(); i++) {
				graphicBuilder.polygonCoordinate(convXRelCoordToAbsCoord(pol.getxPoints().get(i)), convYRelCoordToAbsCoord(pol.getyPoints().get(i)));
			}

			break;
		case REGULARPOLYGON:
			RegularPolygon regPol = (RegularPolygon) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
					.numberOfSides(regPol.getNumberOfSides()).color(regPol.getGraphicColor()).solid(regPol.isSolid())
					.outlineColor(regPol.getOutlineColor()).outlineThickness(regPol.getOutlineThickness())
					.shadow(regPol.getShadow()).rotation(regPol.getRotation()).shadingType(regPol.getShadingType());

			break;
		case SQUARE:
			Square square = (Square) currentGraphic;
			/*
			 * TODO square . getLength ( )
			 */
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos).length(10)
					.color(square.getGraphicColor()).solid(square.isSolid()).outlineColor(square.getOutlineColor())
					.outlineThickness(square.getOutlineThickness()).shadow(square.getShadow())
					.rotation(square.getRotation()).shadingType(square.getShadingType());
			break;
		case STAR:
			Star star = (Star) currentGraphic;
			/*
			 * TODO this needs fixing star . getNumberOfPoints ( )
			 */
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos).numberOfPoints(5)
					.color(star.getGraphicColor()).solid(star.isSolid()).outlineColor(star.getOutlineColor())
					.outlineThickness(star.getOutlineThickness()).shadow(star.getShadow()).rotation(star.getRotation())
					.shadingType(star.getShadingType());
			break;
		case TRIANGLE:
			Triangle triangle = (Triangle) currentGraphic;
			graphicBuilder = new GraphicBuilder(graphicType, xStartPos, yStartPos)
					.triangleCoordinates(convXRelCoordToAbsCoord(triangle.getxPoints().get(0)), convYRelCoordToAbsCoord(triangle.getyPoints().get(0)),
							convXRelCoordToAbsCoord(triangle.getxPoints().get(1)), convYRelCoordToAbsCoord(triangle.getyPoints().get(1)), convXRelCoordToAbsCoord(triangle.getxPoints().get(2)),
							convYRelCoordToAbsCoord(triangle.getyPoints().get(2))).color(triangle.getGraphicColor()).solid(triangle.isSolid())
					.outlineColor(triangle.getOutlineColor()).outlineThickness(triangle.getOutlineThickness())
					.shadow(triangle.getShadow()).rotation(triangle.getRotation())
					.shadingType(triangle.getShadingType());

			break;
		default:
			/* For any other shape, by default make a rectangle */
			Rectangle rectangle = (Rectangle) currentGraphic;
			rectangle.printItem();
			graphicBuilder = new GraphicBuilder(GraphicType.RECTANGLE, xStartPos, yStartPos)
					.xEndPos(convXRelCoordToAbsCoord(rectangle.getXEnd())).yEndPos(convYRelCoordToAbsCoord(rectangle.getYEnd())).arcWidth(rectangle.getArcWidth())
					.arcHeight(rectangle.getArcHeight()).color(rectangle.getGraphicColor()).solid(rectangle.isSolid())
					.outlineColor(rectangle.getOutlineColor()).outlineThickness(rectangle.getOutlineThickness())
					.shadow(rectangle.getShadow()).rotation(rectangle.getRotation())
					.shadingType(rectangle.getShadingType());
			break;

		}

		/*
		 * Loop through all the stops for the current graphic and add them to
		 * the graphicHandler
		 */
		if (currentGraphic.getStopValuesList() != null) {
			for (int i = 0; i < currentGraphic.getStopValuesList().size(); i++) {
				graphicBuilder.shadingElement(currentGraphic.getShadingList().get(i), currentGraphic.getStopValuesList()
						.get(i));
			}
		}

		graphicsHandler.createGraphic(graphicBuilder.build());

		if (currentGraphic.getStartTime() != 0) {
			graphicsHandler.setVisible(graphicsHandler.getGraphicCount() - 1, false);
		}
	}

	private void addText(Text currentText) {
		float xStart = convXRelCoordToAbsCoord(currentText.getXStart());
		float yStart = convYRelCoordToAbsCoord(currentText.getYStart());
		float xEnd = convXRelCoordToAbsCoord(currentText.getXEnd());
		float yEnd = convYRelCoordToAbsCoord(currentText.getYEnd());
		String backgroundColor = currentText.getBackgroundColor();
		Alignment alignment = Alignment.valueOf(currentText.getAlignment().toUpperCase());

		TextFragmentList textFragmentList = new TextFragmentList();

		/*
		 * Loop through all the text fragments for the box, adding them to the
		 * textFragmentList
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

			textFragmentList.add(new TextObject.TextFragmentBuilder(text).bold(bold).underline(underline)
					.italic(italic).superscript(superscript).subscript(subscript).strikethrough(strikethrough)
					.newline(newline).fontColor(fontColor).highlightColor(highlightColor).fontName(font)
					.fontSize((int) fontSize).build());
		}

		textHandler.createTextbox(new TextObject.TextBoxBuilder(xStart, yStart).xEnd(xEnd).yEnd(yEnd)
				.backgroundColor(backgroundColor).alignment(alignment).textFragmentList(textFragmentList).build());

		if (currentText.getStartTime() != 0) {
			textHandler.setVisible(textHandler.getTextCount() - 1, false);
		}
	}

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

		imageHandler.createImage(new ImageObject.ImageBuilder(filepath, xStartPos, yStartPos).rotation(rotation)
				.scaleX(scale).scaleY(scale).cropLeft(cropX1).cropRight(cropX2).cropDown(cropY1).cropUp(cropY2)
				.hFlip(flipHorizontal).vFlip(flipVertical).build());

		if (currentImage.getStartTime() != 0) {
			imageHandler.setVisible(imageHandler.getImageCount() - 1, false);
		}
	}

	private void addAudio(Audio currentAudio) {
		float x = convXRelCoordToAbsCoord(currentAudio.getXStart());
		float y = convYRelCoordToAbsCoord(currentAudio.getYStart());
		float width = 400; // TODO currentAudio.getWidth();

		String sourceFile = currentAudio.getSourceFile(); //TODO
		//File sourceFile = new File(currentAudio.getSourceFile());
		
		boolean loop = false;// TODO currentAudio.isLoop();
		boolean autoPlay = false;// TODO currentAudio.isAutoPlay();
		boolean visibleControls = true;// TODO currentAudio.isVisibleControls();
		boolean playButtonOnly = false;// TODO currentAudio.isPlayButtonOnly();

		audioHandler.createAudio(x, y, width, sourceFile, loop, autoPlay, visibleControls, playButtonOnly);
	}

	private void addVideo(Video currentVideo) {
		float x = convXRelCoordToAbsCoord(currentVideo.getXStart());
		float y = convYRelCoordToAbsCoord(currentVideo.getYStart());
		float width = 400; // TODO currentVideo.getWidth();
		String sourceFile = currentVideo.getSourceFile();
		boolean autoPlay = false; // TODO currentVideo.isAutoPlay();
		boolean loop = false; // TODO currentVideo.isLoop();

		videoHandler.createVideo(x, y, width, sourceFile, autoPlay, loop);

		if (currentVideo.getStartTime() != 0) {
			videoHandler.pauseVideo(videoHandler.getVideoCount());
			videoHandler.setVisible(videoHandler.getVideoCount() - 1, false);
		}

	}

	private float convXRelCoordToAbsCoord(float x) {
		return (xSlideStart + x * slideWidth);
	}

	private float convYRelCoordToAbsCoord(float y) {
		return (ySlideStart + y * slideHeight);
	}

	public void clear() {
		/* Clear all the handlers */
		audioHandler.clearAudios();
		videoHandler.clearVideos();
		imageHandler.clearImages();
		textHandler.clearTexts();
		graphicsHandler.clearGraphics();
		group.getChildren().clear();
	}

	public void buildAnswerSlide(Question question) {
		System.out.println("Building graph");

		PieChartObject answerChart = new PieChartObject();
		answerChart.setTitle(question.getId());
		answerChart.setxStartPos(0);
		answerChart.setyStartPos(0);
		answerChart.setScale(1);

		ArrayList<String> answerNames = new ArrayList<String>();
		ArrayList<Float> answerValues = new ArrayList<Float>();

		for (Answer tempAnswer : question.getAnswers()) {
			answerNames.add(tempAnswer.getId());
			answerValues.add((float) tempAnswer.getAnswerCount());
		}

		answerChart.setPieChartData(answerNames, answerValues);

		graphHandler.drawPieChart(answerChart);
	}
}
