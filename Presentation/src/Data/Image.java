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
	public void setxStart(String xStart) {
		float x;
		try {
			x = Float.parseFloat(xStart);
			this.xStart = x;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
	public void setyStart(String yStart) {
		try {
			float y = Float.parseFloat(yStart);
			this.yStart = y;
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	public void setScale(String scale) {
		try {
			float s = Float.parseFloat(scale);
			this.scale = s;
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	public void setDuration(String duration) {
		try {
			float d = Float.parseFloat(duration);
			this.duration = d;
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	public void setStartTime(String startTime) {
		try {
			float s = Float.parseFloat(startTime);
			this.startTime = s;
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	public void setRotation(String rotation) {
		try {
			int r = Integer.parseInt(rotation);
			this.rotation = r;
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	public void setFlipHorizontal(String flipHorizontal) {
		try {
			this.flipHorizontal = Boolean.parseBoolean(flipHorizontal);
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	public void setFlipVertical(String flipVertical) {
		try {
			this.flipVertical = Boolean.parseBoolean(flipVertical);
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	public void setCropX1(String cropX1) {
		try {
			float c = Float.parseFloat(cropX1);
			this.cropX1 = c;
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	public void setCropY1(String cropY1) {
		try {
			float c = Float.parseFloat(cropY1);
			this.cropY1 = c;
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	public void setCropX2(String cropX2) {
		try {
			float c = Float.parseFloat(cropX2);
			this.cropX2 = c;
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	public void setCropY2(String cropY2) {
		try {
			float c = Float.parseFloat(cropY2);
			this.cropY2 = c;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
