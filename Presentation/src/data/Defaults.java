/** (c) Copyright by WaveMedia. */
package data;

import java.math.BigInteger;

import utils.Utils;
import javafx.scene.text.Font;

/**
 * Defaults class for containing the default values for properties of objects
 * within a slideshow.
 * 
 * @author dk666
 * @version 2.3 02/06/15
 */
public class Defaults {

	/*
	 * Properties with defined defaults. These values will only be used if not
	 * overwritten by the defaults specified in the XML.
	 */
	private String backgroundColor = "#00FFFFFF";
	private String font = Font.getDefault().getName();
	private double fontSize = Font.getDefault().getSize();
	private String fontColor = "#FF000000";
	private String graphicColor = "#FF000000";
	private String highlightColor = "#FFFF0000";
	private float startTime = 0;
	private float duration = Float.MAX_VALUE;
	private String alignment = "left";
	private float scale = 1;
	private float rotation = 0;
	private float cropX1 = 0;
	private float cropY1 = 0;
	private float cropX2 = 1;
	private float cropY2 = 1;
	private boolean shapeSolidity = true;
	private float stopValue = 0.5f;
	private float outlineThickness = 1;
	private String outlineColor = "#FF000000";
	private float audioWidth = 0.2f;
	private float videoWidth = 0.2f;
	private double xAspectRatio = 5;
	private double yAspectRatio = 4;
	private int originalXResolution = 1280;
	private int originalYResolution = 1024;
	private boolean audioHasVisibleControls = false;

	/**
	 * Constructs a new Defaults object. All defaults initialise to hard-coded
	 * sensible values.
	 */
	public Defaults() {

	}

	/**
	 * For debugging. Outputs all the default settings.
	 */
	public void printDefaults() {
		System.out.println("Start of Document Defaults:");
		System.out.println("BackgroundColour: " + backgroundColor);
		System.out.println("Font: " + font);
		System.out.println("FontSize: " + fontSize);
		System.out.println("FontColor: " + fontColor);
		System.out.println("GraphicColor: " + graphicColor);
		System.out.println("HighlightColor: " + highlightColor);
		System.out.println("StartTime: " + startTime);
		System.out.println("Duration: " + duration);
		System.out.println("Alignment: " + alignment);
		System.out.println("Scale: " + scale);
		System.out.println("Rotation: " + rotation);
		System.out.println("CropX1: " + cropX1);
		System.out.println("CropY1: " + cropY1);
		System.out.println("CropX2: " + cropX2);
		System.out.println("CropY2: " + cropY2);
		System.out.println("Shape Solidity: " + shapeSolidity);
		System.out.println("Stop Value" + stopValue);
		System.out.println("Outline Thickness: " + outlineThickness);
		System.out.println("Outline Color: " + outlineColor);
		System.out.println("Audio Width: " + audioWidth);
		System.out.println("Video Width: " + videoWidth);
		System.out.println("X Aspect Ratio: " + xAspectRatio);
		System.out.println("Y Aspect Ratio: " + yAspectRatio);
		System.out.println("Original X Resolution: " + originalXResolution);
		System.out.println("Original Y Resolution: " + originalYResolution);
		System.out.println("Audio Has Visible Controls: "
				+ audioHasVisibleControls);
		System.out.println("End of Document Defaults");
		System.out.println("");
	}

	/**
	 * @return the backgroundColour
	 */
	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * @param backgroundColour
	 *            the backgroundColour to set
	 */
	public void setBackgroundColor(String string) {
		if (Utils.validARGB(string)) {
			this.backgroundColor = string;
		}
	}

	/**
	 * @return the outlineColor
	 */
	public String getOutlineColor() {
		return this.outlineColor;
	}

	/**
	 * @param string
	 *            the outlineColor to set
	 */
	public void setOutlineColor(String string) {
		if (Utils.validARGB(string)) {
			this.outlineColor = string;
		}
	}

	/**
	 * Returns the default outline thickness.
	 * 
	 * @return outlineThickness The thickness of the outline
	 */
	public float getOutlineThickness() {
		return this.outlineThickness;
	}

	/**
	 * Sets the default outline thickness.
	 * 
	 * @param string
	 *            The thickness of the outline.
	 */
	public void setOutlineThickness(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.outlineThickness = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the font
	 */
	public String getFont() {
		return this.font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(String string) {
		string = Utils.capitaliseEachFirstLetter(string);
		if (Font.getFontNames().contains(string)) {
			this.font = string;
		}
	}

	/**
	 * @return the fontSize
	 */
	public double getFontSize() {
		return this.fontSize;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(String string) {
		try {
			double d = Double.parseDouble(string);
			if ((d > 0)) {
				this.fontSize = d;
			}
		} catch (Exception e) {
			/* Do nothing */
		}
	}

	/**
	 * @return the fontColor
	 */
	public String getFontColor() {
		return this.fontColor;
	}

	/**
	 * @param fontColor
	 *            the fontColor to set
	 */
	public void setFontColor(String string) {
		if (Utils.validARGB(string)) {
			this.fontColor = string;
		}
	}

	/**
	 * @return the graphicColour
	 */
	public String getGraphicColor() {
		return this.graphicColor;
	}

	/**
	 * @param fillColour
	 *            the graphicColour to set
	 */
	public void setGraphicColor(String string) {
		if (Utils.validARGB(string)) {
			this.graphicColor = string;
		}
	}

	/**
	 * @return the graphicColour
	 */
	public String getHighlightColor() {
		return this.highlightColor;
	}

	/**
	 * @param string
	 *            the graphicColor to set
	 */
	public void setHighlightColor(String string) {
		if (Utils.validARGB(string)) {
			this.highlightColor = string;
		}
	}

	/**
	 * @return the startTime
	 */
	public float getStartTime() {
		return this.startTime;
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
		return this.duration;
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

	/**
	 * @return the alignment
	 */
	public String getAlignment() {
		return this.alignment;
	}

	/**
	 * @param alignment
	 *            the alignment to set
	 */
	public void setAlignment(String string) {
		if (Utils.validAlignment(string)) {
			this.alignment = string;
		}
	}

	/**
	 * @return the scale
	 */
	public float getScale() {
		return this.scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(String string) {
		try {
			float f = Float.parseFloat(string);
			if (f > 0) {
				this.scale = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return this.rotation;
	}

	/**
	 * @param rotation
	 *            the rotation to set
	 */
	public void setRotation(String string) {
		try {
			int i = Integer.parseInt(string);
			if (Utils.withinRangeInclusive(0, 360, i)) {
				this.rotation = i;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropX1
	 */
	public float getCropX1() {
		return this.cropX1;
	}

	/**
	 * @param cropX1
	 *            the cropX1 to set
	 */
	public void setCropX1(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.cropX1 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropY1
	 */
	public float getCropY1() {
		return this.cropY1;
	}

	/**
	 * @param cropY1
	 *            the cropY1 to set
	 */
	public void setCropY1(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.cropY1 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropX2
	 */
	public float getCropX2() {
		return this.cropX2;
	}

	/**
	 * @param cropX2
	 *            the cropX2 to set
	 */
	public void setCropX2(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.cropX2 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropY2
	 */
	public float getCropY2() {
		return this.cropY2;
	}

	/**
	 * @param cropY2
	 *            the cropY2 to set
	 */
	public void setCropY2(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.cropY2 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the shapeSolidity
	 */
	public boolean getShapeSolidity() {
		return shapeSolidity;
	}

	/**
	 * @param string
	 *            the shapeSolidity to set
	 */
	public void setShapeSolidity(String string) {
		this.shapeSolidity = Boolean.parseBoolean(string);
	}

	/**
	 * @return the stopValue
	 */
	public float getStopValue() {
		return stopValue;
	}

	/**
	 * @param cropX1
	 *            the cropX1 to set
	 */
	public void setStopValue(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.stopValue = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * Returns the default width of an audio player.
	 * 
	 * @return the width
	 */
	public float getAudioWidth() {
		return audioWidth;
	}

	/**
	 * Sets the default width of the audio player.
	 * 
	 * @param width
	 *            the width to set
	 */
	public void setAudioWidth(String s) {
		try {
			float f = Float.parseFloat(s);
			if (f > 0) {
				this.audioWidth = f;
			}

		} catch (Exception e) {
			// Do nothing
		}
	}

	/**
	 * Returns the default width of a video player.
	 * 
	 * @return the width
	 */
	public float getVideoWidth() {
		return videoWidth;
	}

	/**
	 * Sets the default width of the video player.
	 * 
	 * @param width
	 *            the width to set
	 */
	public void setVideoWidth(String s) {
		try {
			float f = Float.parseFloat(s);
			if (f > 0) {
				this.videoWidth = f;
			}

		} catch (Exception e) {
			// Do nothing
		}
	}

	/**
	 * Returns the default visibility of the audio player controls.
	 * 
	 * @return
	 */
	public boolean isAudioHasVisibleControls() {
		return audioHasVisibleControls;
	}

	/**
	 * Sets the default visibility of the audio player controls.
	 * 
	 * @param string
	 *            the visibility of the controls.
	 */
	public void setAudioHasVisibleControls(String string) {
		this.audioHasVisibleControls = Boolean.parseBoolean(string);
	}

	/**
	 * Returns the xAspectRatio.
	 * 
	 * @return the xAspectRatio to return.
	 */
	public double getxAspectRatio() {
		return xAspectRatio;
	}

	/**
	 * Sets the default xAspectRatio
	 * 
	 * @param xAspectRatio
	 *            the xAspectRatio to set
	 */
	public void setxAspectRatio(String s) {
		try {
			double d = Double.parseDouble(s);
			if (d > 0) {
				this.xAspectRatio = d;
			}

		} catch (Exception e) {
			// Do nothing
		}
	}

	/**
	 * Returns the yAspectRatio
	 * 
	 * @return the yAspectRatio to return.
	 */
	public double getyAspectRatio() {
		return yAspectRatio;
	}

	/**
	 * Sets the default yAspectRatio
	 * 
	 * @param yAspectRatio
	 *            the yAspectRatio to set
	 */
	public void setyAspectRatio(String s) {
		try {
			double d = Double.parseDouble(s);
			if (d > 0) {
				this.yAspectRatio = d;
			}

		} catch (Exception e) {
			// Do nothing
		}
	}

	/**
	 * @return the originalXResolution
	 */
	public int getOriginalXResolution() {
		return originalXResolution;
	}

	/**
	 * @param originalXResolution
	 *            the originalXResolution to set
	 */
	public void setOriginalXResolution(String originalXResolution) {
		try {
			int i = Integer.parseInt(originalXResolution);
			if (i > 0) {
				this.originalXResolution = i;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the originalYResolution
	 */
	public int getOriginalYResolution() {
		return originalYResolution;
	}

	/**
	 * @param originalYResolution
	 *            the originalYResolution to set
	 */
	public void setOriginalYResolution(String originalYResolution) {
		try {
			int i = Integer.parseInt(originalYResolution);
			if (i > 0) {
				this.originalYResolution = i;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * Recalculates the aspect ratio from the original resolutions. Should be
	 * called after all default settings have been set.
	 */
	public void RecalculateRatios() {
		int gcd = BigInteger.valueOf(originalXResolution)
				.gcd(BigInteger.valueOf(originalYResolution)).intValue();
		this.xAspectRatio = originalXResolution / gcd;
		this.yAspectRatio = originalYResolution / gcd;
	}
}
