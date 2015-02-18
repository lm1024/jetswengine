package Data;

import java.io.Serializable;
import java.util.List;

public class Slide implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float duration;
	private List<Text> textList;
	private List<Graphic> graphicsList;
	private List<Sound> soundsList;
	private List<Image> imagesList;
	private List<Movie> moviesList;
	private Text currentText;
	/**
	 * @return the currentText
	 */
	public Text getCurrentText() {
		return currentText;
	}

	/**
	 * @param currentText the currentText to set
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
	 * @param currentGraphic the currentGraphic to set
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
	 * @param currentImage the currentImage to set
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
	 * @param currentMovie the currentMovie to set
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
	 * @param currentSound the currentSound to set
	 */
	public void setCurrentSound(Sound currentSound) {
		this.currentSound = currentSound;
	}

	private Graphic currentGraphic;
	private Image currentImage;
	private Movie currentMovie;
	private Sound currentSound;
	

	public Slide() {
		
	}

	/**
	 * @return The slide duration
	 */
	public float getDuration() {
		return duration;
	}

	/**
	 * @param duration </br>Set the duration of the slide. </br>Setting to -1 indicates
	 * slide that requires changing manually
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
	public void addText(Text someText) {
		this.textList.add(textList.size(), someText);
	}

	/**
	 * @return the graphicsList
	 */
	public List<Graphic> getGraphicsList() {
		return graphicsList;
	}

	/**
	 * @param add
	 *            a graphic to the graphicsList
	 */
	public void addGraphic(Graphic graphic) {
		this.graphicsList.add(graphicsList.size(), graphic);
	}

	/**
	 * @return the soundsList
	 */
	public List<Sound> getSoundsList() {
		return soundsList;
	}

	/**
	 * @param add
	 *            a sound to the soundsList
	 */
	public void addSound(Sound sound) {
		this.soundsList.add(soundsList.size(), sound);
	}

	/**
	 * @return the imagesList
	 */
	public List<Image> getImagesList() {
		return imagesList;
	}

	/**
	 * @param add
	 *            an image to the imagesList
	 */
	public void addImage(Image image) {
		this.imagesList.add(imagesList.size(), image);
	}

	/**
	 * @return the moviesList
	 */
	public List<Movie> getMoviesList() {
		return moviesList;
	}

	/**
	 * @param add
	 *            a movie to the moviesList
	 */
	public void addMovie(Movie movie) {
		this.moviesList.add(moviesList.size(), movie);
	}

}
