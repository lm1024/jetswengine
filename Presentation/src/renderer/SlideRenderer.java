/**
 * 
 */
package renderer;

import imageHandler.ImageHandler;
import graphicsHandler.GraphicObject;
import graphicsHandler.GraphicObject.GraphicBuilder;
import graphicsHandler.GraphicType;
import graphicsHandler.GraphicsHandler;
import sofia.AudioHandler;
import sofia.VideoHandler;
import textHandler.TextHandler;
import Data.Audio;
import Data.Defaults;
import Data.Graphic;
import Data.Image;
import Data.OtherShapes;
import Data.Slide;
import Data.SlideItem;
import Data.Text;
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

	public SlideRenderer(Group group) {
		this.group = group;
		this.graphicsHandler = new GraphicsHandler(group);
		this.imageHandler = new ImageHandler(group);
		this.textHandler = new TextHandler(group);
		this.videoHandler = new VideoHandler(group);
		this.audioHandler = new AudioHandler(group);
		
	}

	public void drawSlide(Slide currentSlide) {
		for (SlideItem currentItem : currentSlide.getAll()) {
			System.out.println(currentItem.getType());

			switch (currentItem.getType()) {
			case "Text":
				addText((Text) currentItem);
				break;
			case "Image":
				addImage((Image) currentItem);
				break;
			case "Audio":
				addAudio((Audio) currentItem);
				break;
			case "Video":
				addVideo((Video) currentItem);
				break;
			/* Graphics */
			default:
				addGraphic((Graphic) currentItem);
			}
		}
	}

	private void addGraphic(Graphic currentGraphic) {
		GraphicType graphicType = GraphicType.RECTANGLE;
		
		GraphicBuilder graphicBuilder = new GraphicBuilder(graphicType, currentGraphic.getXStart(), currentGraphic.getYStart());
		GraphicObject currentSlideshowGraphic;
		
		switch(graphicType) {
		case ARC:
			OtherShapes.Arc currentArc = (OtherShapes.Arc) currentGraphic;
			//currentGraphic.getClass().cast(new OtherShapes.Arc(new Defaults()));
			// add attributes to slideshowGraphic?
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
		
		//graphicsHandler.drawShape(new SlideshowGraphic.GraphicBuilder(graphicType, currentGraphic.getXStart(), currentGraphic.getYStart()));
	}

	private void addText(Text currentText) {

	}

	private void addImage(Image currentImage) {

	}

	private void addAudio(Audio currentAudio) {

	}

	private void addVideo(Video currentVideo) {

	}

	public void clear() {
		// clear videos and audio
		group.getChildren().clear();
	}
}
