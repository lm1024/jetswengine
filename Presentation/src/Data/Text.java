/** (c) Copyright by WaveMedia. */
package Data;

import java.util.ArrayList;
import java.util.List;

import utils.Utils;
import Data.Defaults;

/**
 * Text class for containing a single SmartSlides text data.
 * 
 * @author dk666
 * @version 2.3 02/06/15
 */
public class Text extends SlideItem {

	/*
	 * Text consists of a collection of text fragments, with a current fragment
	 * being set for easy access. Text can also have an alignment, font, color,
	 * size, background color, hightlight color, and end co-ordinates.
	 */
	private List<TextFragment> textFragments;
	private TextFragment currentTextFragment;
	private String alignment; // left/right/center
	private String font;
	private String fontColor;
	private double fontSize;
	private String backgroundColor;
	private String highlightColor;
	private float xEnd = 1;
	private float yEnd = 1;

	/**
	 * Constructs a new Text object with the specified defaults.
	 * 
	 * @param defaults
	 */
	public Text(Defaults defaults) {
		super(defaults);
		this.textFragments = new ArrayList<TextFragment>();
		this.font = defaults.getFont();
		this.fontColor = defaults.getFontColor();
		this.fontSize = defaults.getFontSize();
		this.backgroundColor = defaults.getBackgroundColor();
		this.alignment = defaults.getAlignment();
		this.highlightColor = defaults.getHighlightColor();
	}

	/**
	 * For debugging. Prints the content of this text object and its fragments.
	 */
	@Override
	public void printItem() {
		super.printItem();
		System.out.println("xEnd: " + ((xEnd < 0) ? "None specified" : xEnd));
		System.out.println("yEnd: " + ((yEnd < 0) ? "None specified" : yEnd));
		System.out.println("Alignment: " + alignment);
		System.out.println("Font: " + font);
		System.out.println("FontColor: " + fontColor);
		System.out.println("FontSize: " + fontSize);
		System.out.println("BackgroundColor: " + backgroundColor);
		System.out.println("Number of text fragments: " + textFragments.size());
		int i = 1;
		for (TextFragment tf : textFragments) {
			System.out.println("Start of Text fragment (" + i + ")");
			tf.printItem();
			System.out.println("End of Text fragment (" + i + ")\n");
			i++;
		}
		System.out.println("");
	}

	/**
	 * @return the highlightColor
	 */
	public String getHighlightColor() {
		return highlightColor;
	}

	/**
	 * @param highlightColor
	 *            the highlightColor to set
	 */
	public void setHighlightColor(String string) {
		if (Utils.validARGB(string)) {
			this.highlightColor = string;
		}
	}

	/**
	 * @return the xStart
	 */
	public float getXEnd() {
		return this.xEnd;
	}

	/**
	 * @param xStart
	 *            the xStart to set
	 */
	public void setXEnd(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.xEnd = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the yStart
	 */
	public float getYEnd() {
		return this.yEnd;
	}

	/**
	 * @param yStart
	 *            the yStart to set
	 */
	public void setYEnd(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeExclusive(0, 1, f)) {
				this.yEnd = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	public void addDefaults() {
		currentTextFragment.setFontColor(this.getFontColor());
		currentTextFragment.setFontSize(this.getFontSize());
		currentTextFragment.setFont(this.getFont());
	}

	/**
	 * @return the fontSize
	 */
	public double getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(String string) {
		try {
			double d = Double.parseDouble(string);
			if (d > 0) {
				this.fontSize = d;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the textList
	 */
	public List<TextFragment> getTextFragments() {
		return textFragments;
	}

	/**
	 * @param text
	 *            the Text to add
	 */
	public void addTextFragment(TextFragment text) {
		this.currentTextFragment = text;
		this.textFragments.add(currentTextFragment);
	}

	/**
	 * @return the textList
	 */
	public TextFragment getTextFragment(int index) {
		return textFragments.get(index);
	}

	/**
	 * @return the currentTextFragment
	 */
	public TextFragment getCurrentTextFragment() {
		return currentTextFragment;
	}

	/**
	 * @return the alignment
	 */
	public String getAlignment() {
		return alignment;
	}

	/**
	 * @param alignment
	 *            the alignment to set
	 */
	public void setAlignment(String string) {
		if (string != null) {
			if (Utils.validAlignment(string)) {
				this.alignment = string;
			}
		}
	}

	/**
	 * @return the font
	 */
	public String getFont() {
		return font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(String string) {
		string = Utils.capitaliseEachFirstLetter(string);
		if (Utils.validFont(string)) {
			this.font = string;
		}
	}

	/**
	 * @return the fontcolor
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * @param colour
	 *            the colour to set
	 */
	public void setFontColor(String string) {
		if (Utils.validARGB(string)) {
			this.fontColor = string;
		}
	}

	/**
	 * @return the backgroundcolor
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param colour
	 *            the colour to set
	 */
	public void setBackgroundColor(String string) {
		if (Utils.validARGB(string)) {
			this.backgroundColor = string;
		}
	}
}
