/** (c) Copyright by WaveMedia. */
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
	private List<SlideItem> itemList;
	private SlideItem currentItem;
	private Question slideQuestion;
	private List<Slide> tangentSlides;

	/**
	 * @param tangentSlides
	 *            the tangentSlides to set
	 */
	public void removeTangentSlides() {
		this.tangentSlides.clear();
	}

	public Slide(Defaults defaults) {
		this.itemList = new ArrayList<SlideItem>();
		this.tangentSlides = new ArrayList<Slide>();
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
	 * @return the currentItem
	 */
	public SlideItem getCurrentItem() {
		return this.currentItem;
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
			if (string.toLowerCase().matches("float.max_value|infinite")) {
				this.duration = Float.MAX_VALUE;
			} else {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.duration = f;
				}
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the slideItems
	 */
	public List<SlideItem> getAll() {
		return itemList;
	}

	/**
	 * @return the slideItem
	 */
	public SlideItem get(int index) {
		return itemList.get(index);
	}

	/**
	 * Adds the SlideItem to the slide.
	 * 
	 * @param item
	 */
	public void add(SlideItem item) {
		this.currentItem = item;
		itemList.add(currentItem);
	}

	/**
	 * Remove the slide item at the specified index from the slide.
	 * 
	 * @param index
	 */
	public void remove(int index) {
		itemList.remove(index);
	}

	/**
	 * Sets the question on the slide to be the specified question.
	 * 
	 * @param question
	 */
	public void addQuestion(Question question) {
		this.slideQuestion = question;
	}

	/**
	 * Returns true if the current slide contains a question.
	 * 
	 * @return
	 */
	public boolean containsQuestion() {
		return slideQuestion != null;
	}

	/**
	 * Returns the current question.
	 * 
	 * @return
	 */
	public Question getQuestion() {
		return slideQuestion;
	}

	/**
	 * @return the tangentSlides
	 */
	public List<Slide> getTangentSlides() {
		return tangentSlides;
	}

	/**
	 * Adds the specified slide to the list of tangent slides.
	 * 
	 * @param tangentSlides
	 *            the tangentSlides to set
	 */
	public void addTangentSlide(Slide tangent) {
		this.tangentSlides.add(tangent);
	}
}
