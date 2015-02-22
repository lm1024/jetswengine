package Data;

/**
 * Abstract class that contains variables that all the graphics object contain.
 * 
 * @author Tom Davidson
 */
public class Graphic extends SlideItem {

	private Graphic graphic;
	private float xStart;
	private float yStart;
	private float duration;
	private float startTime;
	private String graphicColor;

	public Graphic() {

	}

	/**
	 * @return the xStart
	 */
	public float getxStart() {
		return xStart;
	}

	/**
	 * @param xStart
	 *            the xStart to set
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
	 * @param yStart
	 *            the yStart to set
	 */
	public void setyStart(float yStart) {
		this.yStart = yStart;
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
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(float startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the graphicColor
	 */
	public String getGraphicColor() {
		return graphicColor;
	}

	/**
	 * @param graphicColor
	 *            the graphicColor to set
	 */
	public void setGraphicColor(String graphicColor) {
		this.graphicColor = graphicColor;
	}

	/**
	 * @return the graphic
	 */
	public Graphic getGraphic() {
		return graphic;
	}

	/**
	 * @param graphic
	 *            the graphic to set
	 */
	public void setGraphic(Graphic graphic) {
		this.graphic = graphic;
	}

}