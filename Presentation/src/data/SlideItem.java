/** (c) Copyright by WaveMedia. */
package data;

import utils.Utils;

/**
 * Slide Item abstract class for describing the properties of all slide items.
 * 
 * @author dk666
 * @version 2.1 02/02/15
 */
public abstract class SlideItem {

	/* Properties required by all slide items */
	private String sourceFile = null;
	private float xStart;
	private float yStart;
	private float startTime;
	private float duration;

	/**
	 * For debugging. Prints the properties of this slide item.
	 */
	public void printItem() {
		System.out.println("Class name:" + this.getClass().getSimpleName());
		System.out.println("Sourcefile: " + sourceFile);
		System.out.println("xStart: " + xStart);
		System.out.println("yStart: " + yStart);
		System.out.println("startTime: " + startTime);
		System.out.println("duration: " + duration);
	}

	
	/**
	 * Constructs a new SlideItem object with the specified defaults.
	 * 
	 * @param defaults
	 */
	public SlideItem(Defaults defaults) {
		this.startTime = defaults.getStartTime();
		this.duration = defaults.getDuration();
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
	public void setSourceFile(String string) {
		try {
			if (!string.equals("null")) {
				this.sourceFile = string;
			}
		} catch (NullPointerException e) {
			// Do Nothing
		}
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
	public void setXStart(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.xStart = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
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
	public void setYStart(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeExclusive(0, 1, f)) {
				this.yStart = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
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
			if (f >= 0) {
				this.startTime = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
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
			if (string.toLowerCase().matches("float.max_value|infinite")) {
				this.duration = Float.MAX_VALUE;
			} else {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.duration = f;
				}
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	public String getType() {
		return this.getClass().getSimpleName();
	}

}
