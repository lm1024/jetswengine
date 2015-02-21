package Data;

public class Audio extends SlideItem{
	private String sourceFile;
	private float startTime;
	private float xStart;
	private float yStart;
	
	public Audio(String source) {
		this.sourceFile = source;
	}
	/**
	 * @return the sourceFile
	 */
	public String getSourceFile() {
		return sourceFile;
	}
	/**
	 * @param sourceFile the sourceFile to set
	 */
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	/**
	 * @return the startTime
	 */
	public float getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(float startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the xStart
	 */
	public float getXStart() {
		return xStart;
	}
	/**
	 * @param xStart the xStart to set
	 */
	public void setXStart(float xStart) {
		this.xStart = xStart;
	}
	/**
	 * @return the yStart
	 */
	public float getYStart() {
		return yStart;
	}
	/**
	 * @param yStart the yStart to set
	 */
	public void setYStart(float yStart) {
		this.yStart = yStart;
	}
	
}
