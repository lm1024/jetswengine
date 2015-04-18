package Data;

import java.util.ArrayList;
import java.util.List;

public class Slideshow {

	private List<Slide> slides = new ArrayList<Slide>();
	protected DocumentInfo info = new DocumentInfo();
	protected Defaults defaults = new Defaults();
	private Slide currentSlide;

	public Slideshow() {
		
	}

	public void printSlideshow() {
		
		info.printInfo();
		defaults.printDefaults();
		System.out.println("Listing contents of slides:\n");
		int i = 1;
		for (Slide slide : slides) {
			System.out.println("Start of slide (" + i + ") contents:\n");
			for (Text item : slide.getTextList()) {
				item.printItem();
			}
			for (Graphic item : slide.getGraphicsList()) {
				try {
					item.printItem();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (Image item : slide.getImagesList()) {
				item.printItem();
			}
			for (Audio item : slide.getAudiosList()) {
				item.printItem();
			}
			for (Video item : slide.getVideosList()) {
				item.printItem();
			}
			System.out.println("End of slide (" + i + ") contents:\n");
			i++;
		}

	}

	/**
	 * @return the slides
	 */
	public List<Slide> getSlides() {
		return slides;
	}
	
	/**
	 * @return the slide
	 */
	public Slide getSlide(int index) {
		this.currentSlide = slides.get(index);
		return currentSlide;
	}


	/**
	 * @param slides
	 *            the slides to set
	 */
	public void addSlide(Slide slide) {
		this.currentSlide = slide;
		this.slides.add(currentSlide);
	}

	/**
	 * @return the defaults
	 */
	public Defaults getDefaults() {
		return defaults;
	}

	/**
	 * @return the info
	 */
	public DocumentInfo getInfo() {
		return info;
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
	
	/**
	 * @param info the info to set
	 */
	public void setInfo(DocumentInfo info) {
		this.info = info;
	}

	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(Defaults defaults) {
		this.defaults = defaults;
	}

}
