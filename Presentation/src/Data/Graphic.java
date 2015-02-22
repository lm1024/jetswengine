package Data;

import java.util.HashMap;

public class Graphic extends SlideItem {

	private float xStart;
	private float yStart;
	private float duration;
	private float startTime;
	private String graphicColor;

	public Graphic() {

	}
	
	public static Graphic makeGraphic(HashMap<String, String> hashMap) {
		switch (hashMap.get("type")) {
		case "oval":
			Oval g = new Oval();
			g.setDuration(hashMap.get("duration"));
			g.setGraphicColor(hashMap.get("graphiccolor"));
			g.setSolid(hashMap.get("solid"));
			g.setStartTime(hashMap.get("starttime"));
			g.setxEnd(hashMap.get("xend"));
			g.setxStart(hashMap.get("xstart"));
			g.setyEnd(hashMap.get("yend"));
			g.setyStart(hashMap.get("ystart"));
			return g;
			default:
				return null;
			
		}
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
	public void setxStart(String string) {
		try {
			float f = Float.parseFloat(string);
			this.xStart = f;
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
	 * @param yStart
	 *            the yStart to set
	 */
	public void setyStart(String string) {
		try {
			float f = Float.parseFloat(string);
			this.yStart = f;
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
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(String string) {
		try {
			float f = Float.parseFloat(string);
			this.duration = f;
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
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String string) {
		try {
			float f = Float.parseFloat(string);
			this.startTime = f;
		} catch (Exception e) {
			// TODO: handle exception
		}
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

}