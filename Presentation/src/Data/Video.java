package Data;

public class Video extends SlideItem {
	private String sourceFile;
	private float xStart;
	private float yStart;
	private float startTime;

	public Video(String source) {
		this.sourceFile = source;
	}

	/**
	 * @return the sourceFile
	 */
	public String getSourceFile() {
		return sourceFile;
	}

	/**
	 * @param sourceFile
	 *            the sourceFile to set
	 */
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	/**
	 * @return the xStart
	 */
	public float getXStart() {
		return xStart;
	}

	/**
	 * @param xStart
	 *            the xStart to set
	 */
	public void setXStart(String xStart) {
		float x = Float.parseFloat(xStart);
		this.xStart = x;
	}

	/**
	 * @return the yStart
	 */
	public float getYStart() {
		return yStart;
	}

	/**
	 * @param yStart
	 *            the yStart to set
	 */
	public void setYStart(String yStart) {
		Float y = Float.parseFloat(yStart);
		this.yStart = y;
	}

	/**
	 * @return the startTime
	 */
	public float getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		Float s = Float.parseFloat(startTime);
		this.startTime = s;
	}

}
