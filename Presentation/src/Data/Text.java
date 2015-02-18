package Data;

import java.util.List;

public class Text {
	
	private List<TextFragment> textFragments;
	private TextFragment currentTextFragment;
	private int xStart;
	private int yStart;
	private int startTime;
	private int duration;
	
	public Text(){
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
	 * @param text the text to set
	 */
	public void addText(TextFragment text) {
		this.textFragments.add(text);
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
}
