package Data;

import java.util.List;

public class Text {
	
	private List<textFragment> textFragments;
	private int xStart;
	private int yStart;
	private int startTime;
	private int duration;
	
	public Text(String text){
		this.textFragments.add(0, new textFragment(text));
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
	public List<textFragment> getTextFragments() {
		return textFragments;
	}

	/**
	 * @param text the text to set
	 */
	public void addText(textFragment text) {
		this.textFragments.add(text);
	}
	
	/**
	 * @return the textList
	 */
	public textFragment getTextFragment(int index) {
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
	 * @return the endTime
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
