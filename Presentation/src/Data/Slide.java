package Data;

import java.io.Serializable;
import java.util.List;

public class Slide implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private int duration;
	private Text title;
	private List<Text> textList;
	private List<Graphic> graphicsList;
	private List<Sound> soundsList;
	private List<Image> imagesList;
	private List<Movie> moviesList;

	public Slide(String id) {
		this.id = id;
	}

	/**
	 * @return The slide id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return The slide duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration </br>Set the duration of the slide. </br>Setting to -1 indicates
	 * slide that requires changing manually
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return The slide title
	 */
	public Text getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(Text title) {
		this.title = title;
		this.title.setxStart(20);
		this.title.setyStart(20);
		this.title.getTextFragment(0).setBold(true);
		this.title.getTextFragment(0).setFontSize(20);
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
