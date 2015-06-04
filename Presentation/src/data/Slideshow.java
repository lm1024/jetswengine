/** (c) Copyright by WaveMedia. */
package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Slideshow class for containing a single SmartSlides slideshow data.
 * 
 * @author dk666
 * @version 3.1 02/06/15
 */
public class Slideshow {

	/*
	 * Slideshows consist of a collection of slides, a single document info and
	 * a single defaults. The slideshow also tracks the current slide to speed
	 * up repeated accesses to the current slide.
	 */
	private List<Slide> slides = new ArrayList<Slide>();
	protected DocumentInfo info = new DocumentInfo();
	protected Defaults defaults = new Defaults();
	private Slide currentSlide;

	/**
	 * Initialises a Slideshow object.
	 */
	public Slideshow() {

	}

	/**
	 * Inserts all tangents embedded into the current slide into the slideshow.
	 */
	public void importTangents() {
		int index = slides.indexOf(currentSlide);
		slides.addAll(index + 1, currentSlide.getTangentSlides());
		currentSlide.removeTangentSlides();
	}

	/**
	 * For Debugging only. Prints out entire data structure to verify XML was
	 * imported correctly.
	 */
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
	 * Returns all slides in the Slideshow
	 * 
	 * @return the slides
	 */
	public List<Slide> getSlides() {
		return slides;
	}

	/**
	 * Returns slide located at specified index (Starting from 0).
	 * 
	 * @param index
	 * @return
	 */
	public Slide getSlide(int index) {
		this.currentSlide = slides.get(index);
		return currentSlide;
	}

	/**
	 * Adds a slide to the slideshow.
	 * 
	 * @param slide
	 */
	public void addSlide(Slide slide) {
		this.currentSlide = slide;
		this.slides.add(currentSlide);
	}

	/**
	 * Returns the slideshow defaults.
	 * 
	 * @return the defaults
	 */
	public Defaults getDefaults() {
		return defaults;
	}

	/**
	 * Returns the Slideshow document info.
	 * 
	 * @return the info
	 */
	public DocumentInfo getInfo() {
		return info;
	}

	/**
	 * Returns the currently selected slide.
	 * 
	 * @return the currentSlide
	 */
	public Slide getCurrentSlide() {
		return currentSlide;
	}

	/**
	 * Sets the current Slide.
	 * 
	 * @param currentSlide
	 */
	public void setCurrentSlide(Slide currentSlide) {
		this.currentSlide = currentSlide;
	}

	/**
	 * Sets the Slideshow Document info.
	 * @param info
	 */
	public void setInfo(DocumentInfo info) {
		this.info = info;
	}

	/**
	 * Sets the Slideshow defaults.
	 * @param defaults
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
