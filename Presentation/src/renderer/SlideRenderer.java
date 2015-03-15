/**
 * 
 */
package renderer;

import java.io.File;

import imageHandler.ImageHandler;
import imageHandler.ImageObject;
import graphicsHandler.GraphicObject.GraphicBuilder;
import graphicsHandler.GraphicType;
import graphicsHandler.GraphicsHandler;
import sofia.AudioHandler;
import sofia.VideoHandler;
import textHandler.Alignment;
import textHandler.TextFragmentList;
import textHandler.TextHandler;
import textHandler.TextObject;
import Data.Audio;
import Data.Graphic;
import Data.Image;
import Data.Slide;
import Data.SlideItem;
import Data.Text;
import Data.TextFragment;
import Data.Video;
import javafx.scene.Group;

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
	}

	public void updateSlideDimentions(double xSlideStart, double ySlideStart, double slideWidth, double slideHeight) {
		this.xSlideStart = (float) xSlideStart;
		this.ySlideStart = (float) ySlideStart;
		this.slideWidth = (float) slideWidth;
		this.slideHeight = (float) slideHeight;
	}

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

	public void relocateSlideObjects() {
		for (SlideItem currentSlideItem : currentSlide.getAll()) {
			int currentTextNumber = 0;
			int currentImageNumber = 0;
			int currentGraphicNumber = 0;
			int currentAudioNumber = 0;
			int currentVideoNumber = 0;

			switch (currentSlideItem.getType()) {
			case "Text":

				break;
			case "Image":

				break;
			case "Audio":

				break;
			case "Video":
				videoHandler.relocateVideo(currentVideoNumber,
						convXRelCoordToAbsCoord(((Video) currentSlideItem).getXStart()),
						convYRelCoordToAbsCoord(((Video) currentSlideItem).getYStart()));
				currentVideoNumber++;
				break;
			default:
				/* Graphics */

			}
		}
	}

	public void updateSlide(long currentTimeIntoSlide) {
		for (SlideItem currentSlideItem : currentSlide.getAll()) {
			int currentTextNumber = 0;
			int currentImageNumber = 0;
			int currentGraphicNumber = 0;
			int currentAudioNumber = 0;
			int currentVideoNumber = 0;
			int currentObjectNumber;

			switch (currentSlideItem.getType()) {
			case "Text":
				currentObjectNumber = currentTextNumber;
				break;
			case "Image":

				currentObjectNumber = currentImageNumber;
				break;
			case "Audio":

				currentObjectNumber = currentAudioNumber;
				break;
			case "Video":
				currentVideoNumber++;
				currentObjectNumber = currentVideoNumber;
				break;
			default:
				/* Graphics */
				currentObjectNumber = currentGraphicNumber;

			}

			if (currentSlideItem.getStartTime() == (float) currentTimeIntoSlide / 1000f) {
				updateVisibilityOfObject(currentSlideItem.getType(), currentObjectNumber-1, true);
				System.out.println("Showing an object!");
			} else if (currentSlideItem.getDuration() + currentSlideItem.getStartTime() == (float) currentTimeIntoSlide / 1000f) {
				updateVisibilityOfObject(currentSlideItem.getType(), currentObjectNumber-1, false);
				System.out.println("Removing an object!");
			}

		}
	}

	private void updateVisibilityOfObject(String objectType, int numberOfObject, boolean visible) {
		switch (objectType) {
		case "Text":
			break;
		case "Image":
			break;
		case "Audio":
			break;
		case "Video":
			videoHandler.setVisible(numberOfObject, visible);
			videoHandler.playVideo(numberOfObject);
			break;
		default:
			/* Graphics */
		}
	}

	private void addGraphic(Graphic currentGraphic) {
		/* */
		GraphicType graphicType = GraphicType.valueOf(currentGraphic.getType().toUpperCase());

		float xStartPos = convXRelCoordToAbsCoord(currentGraphic.getXStart());
		float yStartPos = convYRelCoordToAbsCoord(currentGraphic.getYStart());

		GraphicBuilder graphicBuilder = new GraphicBuilder(GraphicType.RECTANGLE, xStartPos, yStartPos).xEndPos(
				convXRelCoordToAbsCoord(1f)).yEndPos(convYRelCoordToAbsCoord(1f));

		switch (graphicType) {
		case ARC:

			// graphicBuilder.
			break;
		case ARROW:
			break;
		case CHORD:
			break;
		case CIRCLE:
			break;
		case EQUITRIANGLE:
			break;
		case LINE:
			break;
		case OVAL:
			break;
		case POLYGON:
			break;
		case RECTANGLE:
			/* TODO rect.getArcWidth() */
			/* TODO rect.getArcHeight() */
			break;
		case REGULARPOLYGON:
			break;
		case SQUARE:
			break;
		case STAR:
			break;
		case TRIANGLE:
			break;
		default:
			break;

		}

		graphicsHandler.drawShape(graphicBuilder.build());

		// TODO if hidden, hide
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

		textHandler.drawText(new TextObject.TextBoxBuilder(xStart, yStart).xEnd(xEnd).yEnd(yEnd)
				.backgroundColor(backgroundColor).alignment(alignment).textFragmentList(textFragmentList).build());
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

		imageHandler.drawImage(new ImageObject.ImageBuilder(filepath, xStartPos, yStartPos).rotation(rotation)
				.scaleX(scale).scaleY(scale).cropLeft(cropX1).cropRight(cropX2).cropDown(cropY1).cropUp(cropY2)
				.hFlip(flipHorizontal).vFlip(flipVertical).build());
	}

	private void addAudio(Audio currentAudio) {
		float x = convXRelCoordToAbsCoord(currentAudio.getXStart());
		float y = convYRelCoordToAbsCoord(currentAudio.getYStart());
		float width = 400; // TODO currentAudio.getWidth();

		// String sourceFile = currentAudio.getSourceFile(); //TODO
		File sourceFile = new File(currentAudio.getSourceFile());

		boolean autoPlay = false;// TODO currentAudio.isAutoPlay();
		boolean visibleControls = true;// TODO currentAudio.isVisibleControls();
		boolean playButtonOnly = false;// TODO currentAudio.isPlayButtonOnly();

		audioHandler.createAudio(x, y, width, sourceFile, autoPlay, visibleControls, playButtonOnly);
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
			System.out.println("DOING IT");
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
		// clear videos and audio
		audioHandler.clearAudios();
		videoHandler.clearVideos();
		group.getChildren().clear();
	}
}
