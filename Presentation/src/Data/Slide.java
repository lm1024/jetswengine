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
	private List<Sound> soundsList;
	private List<Image> imagesList;
	private List<Movie> moviesList;
	private Text currentText;
	private Graphic currentGraphic;
	private Image currentImage;
	private Movie currentMovie;
	private Sound currentSound;

	public Slide() {
		this.textList = new ArrayList<Text>();
		this.soundsList = new ArrayList<Sound>();
		this.graphicsList = new ArrayList<Graphic>();
		this.moviesList = new ArrayList<Movie>();
		this.imagesList = new ArrayList<Image>();
	}

	public Slide(float duration) {
		this.duration = duration;
		this.textList = new ArrayList<Text>();
		this.soundsList = new ArrayList<Sound>();
		this.graphicsList = new ArrayList<Graphic>();
		this.moviesList = new ArrayList<Movie>();
		this.imagesList = new ArrayList<Image>();
	}
	
	public void add(HashMap<String,Object> hashMap) {
		
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
		} else if (obj instanceof Movie) {
			this.currentMovie = (Movie) obj;
			this.moviesList.add(currentMovie);
		} else if (obj instanceof Sound) {
			this.currentSound = (Sound) obj;
			this.soundsList.add(currentSound);
		}
	}

	/**
	 * @param add
	 *            a sound to the soundsList
	 */
	public void addSound(String source) {
		this.currentSound = new Sound(source);
		this.soundsList.add(currentSound);
	}

	/**
	 * @param add
	 *            an image to the imagesList
	 */
	public void addImage(String source) {
		this.currentImage = new Image(source);
		this.imagesList.add(currentImage);
	}

	/**
	 * @param add
	 *            a movie to the moviesList
	 */
	public void addMovie(String source) {
		this.currentMovie = new Movie(source);
		this.moviesList.add(currentMovie);
	}

	/**
	 * @param add
	 *            a graphic to the graphicsList
	 */
	public void newGraphic(String type) {
		switch (type) {
		case "oval":
			this.currentGraphic = new Oval();
			break;
		}

		this.graphicsList.add(currentGraphic);
	}

	/**
	 * @return the currentText
	 */
	public Text getCurrentText() {
		return currentText;
	}

	/**
	 * @param currentText
	 *            the currentText to set
	 */
	public void setCurrentText(Text currentText) {
		this.currentText = currentText;
	}

	/**
	 * @return the currentGraphic
	 */
	public Graphic getCurrentGraphic() {
		return currentGraphic;
	}

	/**
	 * @param currentGraphic
	 *            the currentGraphic to set
	 */
	public void setCurrentGraphic(Graphic currentGraphic) {
		this.currentGraphic = currentGraphic;
	}

	/**
	 * @return the currentImage
	 */
	public Image getCurrentImage() {
		return currentImage;
	}

	/**
	 * @param currentImage
	 *            the currentImage to set
	 */
	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}

	/**
	 * @return the currentMovie
	 */
	public Movie getCurrentMovie() {
		return currentMovie;
	}

	/**
	 * @param currentMovie
	 *            the currentMovie to set
	 */
	public void setCurrentMovie(Movie currentMovie) {
		this.currentMovie = currentMovie;
	}

	/**
	 * @return the currentSound
	 */
	public Sound getCurrentSound() {
		return currentSound;
	}

	/**
	 * @param currentSound
	 *            the currentSound to set
	 */
	public void setCurrentSound(Sound currentSound) {
		this.currentSound = currentSound;
	}

	/**
	 * @return The slide duration
	 */
	public float getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            </br>Set the duration of the slide. </br>Setting to -1
	 *            indicates slide that requires changing manually
	 */
	public void setDuration(float duration) {
		this.duration = duration;
	}

	/**
	 * @return the textList
	 */
	public List<Text> getTextList() {
		return textList;
	}

	/**
	 * @param add
	 *            some text to the textList
	 */
	public void newText() {
		this.currentText = new Text();
		this.textList.add(currentText);
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
	public List<Sound> getSoundsList() {
		return soundsList;
	}

	/**
	 * @return the imagesList
	 */
	public List<Image> getImagesList() {
		return imagesList;
	}

	/**
	 * @return the moviesList
	 */
	public List<Movie> getMoviesList() {
		return moviesList;
	}

	public void removeGraphic() {
		this.graphicsList.remove(currentGraphic);
		currentGraphic = null;
	}

}
