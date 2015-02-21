package Data;

public class Image extends SlideItem {
	private String sourceFile;
	private float xStart;
	private float yStart;
	private float scale;
	private float duration;
	private float startTime;
	private int rotation;
	private boolean flipHorizontal;
	private boolean flipVertical;
	private float cropX1;
	private float cropY1;
	private float cropX2;
	private float cropY2;
	
	public Image(String source) {
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
	 * @return the xStart
	 */
	public float getxStart() {
		return xStart;
	}

	/**
	 * @param xStart the xStart to set
	 */
	public void setxStart(float xStart) {
		this.xStart = xStart;
	}

	/**
	 * @return the yStart
	 */
	public float getyStart() {
		return yStart;
	}

	/**
	 * @param yStart the yStart to set
	 */
	public void setyStart(float yStart) {
		this.yStart = yStart;
	}

	/**
	 * @return the scale
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * @return the duration
	 */
	public float getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(float duration) {
		this.duration = duration;
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
	 * @return the rotation
	 */
	public int getRotation() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	/**
	 * @return the flipHorizontal
	 */
	public Boolean getFlipHorizontal() {
		return flipHorizontal;
	}

	/**
	 * @param flipHorizontal the flipHorizontal to set
	 */
	public void setFlipHorizontal(Boolean flipHorizontal) {
		this.flipHorizontal = flipHorizontal;
	}

	/**
	 * @return the flipVertical
	 */
	public Boolean getFlipVertical() {
		return flipVertical;
	}

	/**
	 * @param flipVertical the flipVertical to set
	 */
	public void setFlipVertical(Boolean flipVertical) {
		this.flipVertical = flipVertical;
	}

	/**
	 * @return the cropX1
	 */
	public float getCropX1() {
		return cropX1;
	}

	/**
	 * @param cropX1 the cropX1 to set
	 */
	public void setCropX1(float cropX1) {
		this.cropX1 = cropX1;
	}

	/**
	 * @return the cropY1
	 */
	public float getCropY1() {
		return cropY1;
	}

	/**
	 * @param cropY1 the cropY1 to set
	 */
	public void setCropY1(float cropY1) {
		this.cropY1 = cropY1;
	}

	/**
	 * @return the cropX2
	 */
	public float getCropX2() {
		return cropX2;
	}

	/**
	 * @param cropX2 the cropX2 to set
	 */
	public void setCropX2(float cropX2) {
		this.cropX2 = cropX2;
	}

	/**
	 * @return the cropY2
	 */
	public float getCropY2() {
		return cropY2;
	}

	/**
	 * @param cropY2 the cropY2 to set
	 */
	public void setCropY2(float cropY2) {
		this.cropY2 = cropY2;
	}
	
}
