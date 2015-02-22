package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Slide {
	/**
	 * 
	 */
	private float duration;
	private List<Text> textList;
	private List<Graphic> graphicsList;
	private List<Audio> audioList;
	private List<Image> imagesList;
	private List<Video> videoList;
	private Text currentText;
	private Graphic currentGraphic;
	private Image currentImage;
	private Video currentVideo;
	private Audio currentAudio;

	public Slide() {
		this.textList = new ArrayList<Text>();
		this.audioList = new ArrayList<Audio>();
		this.graphicsList = new ArrayList<Graphic>();
		this.videoList = new ArrayList<Video>();
		this.imagesList = new ArrayList<Image>();
	}

	public Slide(float duration) {
		this.duration = duration;
		this.textList = new ArrayList<Text>();
		this.audioList = new ArrayList<Audio>();
		this.graphicsList = new ArrayList<Graphic>();
		this.videoList = new ArrayList<Video>();
		this.imagesList = new ArrayList<Image>();
	}

	public void add(HashMap<String, String> hashMap) {
		switch (hashMap.get("type")) {
		case "image":
			this.currentImage = new Image(hashMap.get("sourcefile"));
			currentImage.setCropX1(hashMap.get("cropx1"));
			currentImage.setCropX2(hashMap.get("cropx2"));
			currentImage.setCropY1(hashMap.get("cropy1"));
			currentImage.setCropY2(hashMap.get("cropy2"));
			currentImage.setDuration(hashMap.get("duration"));
			currentImage.setFlipHorizontal(hashMap.get("fliphorizontal"));
			currentImage.setFlipVertical(hashMap.get("flipvertical"));
			currentImage.setRotation(hashMap.get("rotate"));
			currentImage.setScale(hashMap.get("scale"));
			currentImage.setStartTime(hashMap.get("starttime"));
			currentImage.setxStart(hashMap.get("xstart"));
			currentImage.setyStart(hashMap.get("ystart"));
			this.imagesList.add(currentImage);
			break;
		case "audio":
			this.currentAudio = new Audio(hashMap.get("sourcefile"));
			currentAudio.setStartTime(hashMap.get("starttime"));
			currentAudio.setXStart(hashMap.get("xstart"));
			currentAudio.setXStart(hashMap.get("ystart"));
			this.audioList.add(currentAudio);
			break;
		case "video":
			this.currentVideo = new Video(hashMap.get("sourcefile"));
			currentVideo.setStartTime(hashMap.get("starttime"));
			currentVideo.setXStart(hashMap.get("xstart"));
			currentVideo.setXStart(hashMap.get("ystart"));
			this.videoList.add(currentVideo);
			break;
		case "graphic":
			break;
		case "textstart":
			this.currentText = new Text();
			currentText.setStartTime(hashMap.get("starttime"));
			currentText.setxStart(hashMap.get("xstart"));
			currentText.setyStart(hashMap.get("ystart"));
			currentText.setFont(hashMap.get("font"));
			currentText.setAlignment(hashMap.get("alignment"));
			break;
		case "textend":
			textList.add(currentText);
			break;
		case "textfragmentstart":
			/* intentional fall through */
		case "textfragmentend":
			currentText.add(hashMap);
			break;
		case "rectangle":
			/* intentional fall through */
		case "line":
			/* intentional fall through */
		case "itriangle":
			/* intentional fall through */
		case "oval":
			this.currentGraphic = Graphic.makeGraphic(hashMap);
			this.graphicsList.add(currentGraphic);
			break;
		default:
			break;
		}
	}

	public void add(SlideItem obj) {
		if (obj instanceof Text) {
			this.currentText = (Text) obj;
			this.textList.add(currentText);
		} else if (obj instanceof Graphic) {
			this.currentGraphic = (Graphic) obj;
			this.graphicsList.add(currentGraphic);
		} else if (obj instanceof Image) {
			this.currentImage = (Image) obj;
			this.imagesList.add(currentImage);
		} else if (obj instanceof Video) {
			this.currentVideo = (Video) obj;
			this.videoList.add(currentVideo);
		} else if (obj instanceof Audio) {
			this.currentAudio = (Audio) obj;
			this.audioList.add(currentAudio);
		}
	}

	/**
	 * @return the currentText
	 */
	public Text getCurrentText() {
		return currentText;
	}

	/**
	 * @return the currentGraphic
	 */
	public Graphic getCurrentGraphic() {
		return currentGraphic;
	}

	/**
	 * @return the currentImage
	 */
	public Image getCurrentImage() {
		return currentImage;
	}

	/**
	 * @return the currentVideo
	 */
	public Video getCurrentMovie() {
		return currentVideo;
	}

	/**
	 * @return the currentSound
	 */
	public Audio getCurrentAudio() {
		return currentAudio;
	}

	/**
	 * @return The slide duration
	 */
	public float getDuration() {
		return duration;
	}

	/**
	 * @return the textList
	 */
	public List<Text> getTextList() {
		return textList;
	}

	/**
	 * @return the graphicsList
	 */
	public List<Graphic> getGraphicsList() {
		return graphicsList;
	}

	/**
	 * @return the soundsList
	 */
	public List<Audio> getAudioList() {
		return audioList;
	}

	/**
	 * @return the imagesList
	 */
	public List<Image> getImagesList() {
		return imagesList;
	}

	/**
	 * @return the videoList
	 */
	public List<Video> getMoviesList() {
		return videoList;
	}

}
