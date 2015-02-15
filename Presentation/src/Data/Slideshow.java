package Data;

import java.util.ArrayList;
import java.util.List;

public class Slideshow {
	
	//private String groupID;
	private List<Slide> slides;
	private DocumentInfo info;
	private Defaults defaults;
	
	public Slideshow() {
		slides = new ArrayList<>();
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
	public void addSlide(Slide slide) {
		this.slides.add(slide);
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
	

}
