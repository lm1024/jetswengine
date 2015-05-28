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
	
	public void importTangents() {
		int index = slides.indexOf(currentSlide);
		slides.addAll(index + 1,currentSlide.getTangentSlides());
	}

	public void printSlideshow() {

		info.printInfo();
		defaults.printDefaults();
		System.out.println("Listing contents of slides:\n");
		int i = 1;
		for (Slide slide : slides) {
			System.out.println("Start of slide (" + i + ") contents:\n");
			for (SlideItem item : slide.getAll()) {
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
	 * @param info
	 *            the info to set
	 */
	public void setInfo(DocumentInfo info) {
		this.info = info;
	}

	/**
	 * @param defaults
	 *            the defaults to set
	 */
	public void setDefaults(Defaults defaults) {
		this.defaults = defaults;
	}

	/**
	 * Method saves all the question data to logfiles.
	 */
	public void saveQuestionData() {
		/* Loop through all slides and save the question data. */
		for (Slide slide : this.getSlides()) {
			if (slide.containsQuestion()) {
				slide.getQuestion().writeLogfile();
			}
		}
	}

}
