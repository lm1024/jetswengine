package Data;

import java.util.ArrayList;
import java.util.List;

public class Slideshow {
	
	//private String groupID;
	private List<Slide> slides;
	private DocumentInfo info;
	private Defaults defaults;
	private Slide currentSlide;
	
	public Slideshow() {
		this.slides = new ArrayList<Slide>();
		this.info = new DocumentInfo();
		this.defaults = new Defaults();
	}
	
	/**
	 * @return the slides
	 */
	public List<Slide> getSlides() {
		return slides;
	}
	/**
	 * @param slides the slides to set
	 */
	public void addSlide(float duration) {
		this.currentSlide = new Slide();
		currentSlide.setDuration(duration);
		this.slides.add(currentSlide);
	}
	/**
	 * @return the defaults
	 */
	public Defaults getDefaults() {
		return defaults;
	}
	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(Defaults defaults) {
		this.defaults = defaults;
	}
	/**
	 * @return the info
	 */
	public DocumentInfo getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(DocumentInfo info) {
		this.info = info;
	}

	/**
	 * @return the currentSlide
	 */
	public Slide getCurrentSlide() {
		return currentSlide;
	}

	/**
	 * @param currentSlide the currentSlide to set
	 */
	public void setCurrentSlide(Slide currentSlide) {
		this.currentSlide = currentSlide;
	}
	

}
