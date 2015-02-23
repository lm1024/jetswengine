package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Slideshow {

	private List<Slide> slides;
	private static DocumentInfo info;
	private static Defaults defaults;
	private Slide currentSlide;

	public Slideshow() {
		this.slides = new ArrayList<Slide>();
		info = new DocumentInfo();
		defaults = new Defaults();
	}

	public void add(HashMap<String, String> hashMap) {
		
			if (hashMap.get("type") == null) {
				
			} else if (hashMap.get("type").equals("documentinfo")) {
				info.add(hashMap);
			} else if (hashMap.get("type").equals("defaultsettings")) {
				defaults.add(hashMap);
			} else {
				currentSlide.add(hashMap);
			}
	}

	/**
	 * @return the slides
	 */
	public List<Slide> getSlides() {
		return slides;
	}

	/**
	 * @param slides
	 *            the slides to set
	 */
	public void addSlide(float duration) {
		this.currentSlide = new Slide(duration);
		this.slides.add(currentSlide);
	}

	/**
	 * @param slides
	 *            the slides to set
	 */
	public void addSlide() {
		this.currentSlide = new Slide();
		this.slides.add(currentSlide);
	}

	/**
	 * @return the defaults
	 */
	public Defaults getDefaults() {
		return defaults;
	}

	/**
	 * @param defaults
	 *            the defaults to set
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
	 * @param info
	 *            the info to set
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
	 * @param currentSlide
	 *            the currentSlide to set
	 */
	public void setCurrentSlide(Slide currentSlide) {
		this.currentSlide = currentSlide;
	}

}
