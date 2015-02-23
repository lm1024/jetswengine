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
		Graphic g;
		switch (hashMap.get("type")) {
		case "oval":
			g = new Oval();
			((Oval)g).setSolid(hashMap.get("solid"));
			((Oval)g).setxEnd(hashMap.get("xend"));
			((Oval)g).setyEnd(hashMap.get("yend"));
			break;
		case "rectangle":
			g = new Rectangle();
			((Rectangle)g).setSolid(hashMap.get("solid"));
			((Rectangle)g).setxEnd(hashMap.get("xend"));
			((Rectangle)g).setyEnd(hashMap.get("yend"));
			break;
		case "itriangle":
			g = new IsoscelesTriangle();
			((IsoscelesTriangle)g).setSolid(hashMap.get("solid"));
			((IsoscelesTriangle)g).setxEnd(hashMap.get("xend"));
			((IsoscelesTriangle)g).setyEnd(hashMap.get("yend"));
			break;
		case "line":
			g = new Line();
			((Line)g).setxEnd(hashMap.get("xend"));
			((Line)g).setyEnd(hashMap.get("yend"));
			break;
			default:
				return null;
			
		}
		g.setDuration(hashMap.get("duration"));
		g.setGraphicColor(hashMap.get("graphiccolor"));
		g.setStartTime(hashMap.get("starttime"));
		g.setxStart(hashMap.get("xstart"));
		g.setyStart(hashMap.get("ystart"));
		return g;
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