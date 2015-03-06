package Data;

import java.util.ArrayList;
import java.util.List;

import utils.Utils;

public class Slide {
	/**
	 * 
	 */
	private float duration;
	private String backgroundColor;
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

	public Slide(Defaults defaults) {
		this.textList = new ArrayList<Text>();
		this.audioList = new ArrayList<Audio>();
		this.graphicsList = new ArrayList<Graphic>();
		this.videoList = new ArrayList<Video>();
		this.imagesList = new ArrayList<Image>();
		this.duration = defaults.getDuration();
		this.backgroundColor = defaults.getBackgroundColor();
	}
	
	/**
	 * @return the backgroundColour
	 */
	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * @param backgroundColour
	 *            the backgroundColour to set
	 */
	public void setBackgroundColor(String string) {
		if (Utils.validARGB(string)) {
			this.backgroundColor = string;
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
	public Video getCurrentVideo() {
		return currentVideo;
	}

	/**
	 * @return the currentSound
	 */
	public Audio getCurrentAudio() {
		return currentAudio;
	}

	/**
	 * @return the currentText
	 */
	public Text getText(int index) {
		return textList.get(index);
	}

	/**
	 * @return the currentGraphic
	 */
	public Graphic getGraphic(int index) {
		return graphicsList.get(index);
	}

	/**
	 * @return the currentImage
	 */
	public Image getImage(int index) {
		return imagesList.get(index);
	}

	/**
	 * @return the currentVideo
	 */
	public Video getVideo(int index) {
		return videoList.get(index);
	}

	/**
	 * @return the currentSound
	 */
	public Audio getAudio(int index) {
		return audioList.get(index);
	}

	/**
	 * @return the duration
	 */
	public float getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(String string) {
		try {
			float f = Float.parseFloat(string);
			if (f > 0) {
				this.duration = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
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
	public List<Audio> getAudiosList() {
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
	public List<Video> getVideosList() {
		return videoList;
	}

	public void addImage(Image image) {
		this.currentImage = image;
		imagesList.add(currentImage);
	}

	public void addVideo(Video video) {
		this.currentVideo = video;
		videoList.add(currentVideo);
	}

	public void addAudio(Audio audio) {
		this.currentAudio = audio;
		audioList.add(currentAudio);
	}

	public void addText(Text text) {
		this.currentText = text;
		textList.add(currentText);
	}

	public void addGraphic(Graphic graphic) {
		this.currentGraphic = graphic;
		graphicsList.add(currentGraphic);
	}
}
