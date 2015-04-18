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

	public Slide(Defaults defaults) {
		this.itemList = new ArrayList<SlideItem>();
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
	
	public void add(SlideItem item) {
		this.currentItem = item;
		itemList.add(currentItem);
	}
	
	public void remove(int index) {
		itemList.remove(index);
	}
	
	public void addQuestion(Question question) {
		this.slideQuestion = question;
	}
	
	public boolean containsQuestion() {
		return slideQuestion != null;
	}
	
	public Question getQuestion() {
		return slideQuestion;
	}
	
	public Slide copySlide() {
		Slide slide = new Slide(new Defaults());
		Slide slideToBeCopied = this;
		
		slide.setBackgroundColor(slideToBeCopied.getBackgroundColor());
		slide.setDuration(((Float)slideToBeCopied.getDuration()).toString());
		
		for (SlideItem item : slideToBeCopied.getAll()) {
			slide.add(item);
		}
		
		return slide;
	}
}
