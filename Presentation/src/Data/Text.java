package Data;

import java.util.ArrayList;
import java.util.List;

public class Text extends SlideItem{
	
	private List<TextFragment> textFragments;
	private TextFragment currentTextFragment;
	private int xStart;
	private int yStart;
	private int startTime;
	private int duration;
	private String alignment; //left/right/center/justify/justified/centre
	public Text(){
		this.textFragments = new ArrayList<TextFragment>();
	}

	/**
	 * @return the xStart
	 */
	public int getxStart() {
		return xStart;
	}

	/**
	 * @param xStart the xStart to set
	 */
	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	/**
	 * @return the textList
	 */
	public List<TextFragment> getTextFragments() {
		return textFragments;
	}

	/**
	 * @param text the Text to add
	 */
	public void addText(TextFragment text) {
		this.textFragments.add(text);
	}
	
	/**
	 * @param text the String to add
	 */
	public void addText(String string) {
		this.currentTextFragment = new TextFragment(string);
		this.textFragments.add(currentTextFragment);
	}
	
	/**
	 * @return the textList
	 */
	public TextFragment getTextFragment(int index) {
		return textFragments.get(index);
	}

	/**
	 * @return the yStart
	 */
	public int getyStart() {
		return yStart;
	}

	/**
	 * @param yStart the yStart to set
	 */
	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	/**
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the currentTextFragment
	 */
	public TextFragment getCurrentTextFragment() {
		return currentTextFragment;
	}

	/**
	 * @param currentTextFragment the currentTextFragment to set
	 */
	public void setCurrentTextFragment(TextFragment currentTextFragment) {
		this.currentTextFragment = currentTextFragment;
	}

	/**
	 * @return the alignment
	 */
	public String getAlignment() {
		return alignment;
	}

	/**
	 * @param alignment the alignment to set
	 */
	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
}
