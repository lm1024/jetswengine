package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Data.Defaults;

public class Text extends SlideItem {

	private List<TextFragment> textFragments;
	private TextFragment currentTextFragment;
	private float xStart;
	private float yStart;
	private float startTime;
	private float duration;
	private String alignment; // left/right/center/justify /justified/centre
	private String font;
	private String fontcolor;

	public Text() {
		this.textFragments = new ArrayList<TextFragment>();
		this.currentTextFragment = new TextFragment(this);
	}

	public void add(HashMap<String, String> hashMap) {
		
		if (hashMap.get("type").equals("richtext")) {
			this.currentTextFragment = new TextFragment(this);
			currentTextFragment.setFont(hashMap.get("font"));
			currentTextFragment.setBold(hashMap.get("bold"));
			currentTextFragment.setColor(hashMap.get("fontcolor"));
			currentTextFragment.setFontSize(hashMap.get("fontsize"));
			currentTextFragment.setItalicised(hashMap.get("italic"));
			currentTextFragment.setStrikethrough(hashMap.get("strikethrough"));
			currentTextFragment.setSubscript(hashMap.get("subscript"));
			currentTextFragment.setSuperscript(hashMap.get("superscript"));
			currentTextFragment.setUnderlined(hashMap.get("underlined"));
			currentTextFragment.setTextCase(hashMap.get("case"));
			currentTextFragment.setHighlightColor(hashMap.get("highlightcolor"));
		} else {
			currentTextFragment.setText(hashMap.get("text"));
			this.textFragments.add(currentTextFragment);
			
		}

	}

	/**
	 * @return the xStart
	 */
	public float getxStart() {
		return xStart;
	}

	/**
	 * @param string
	 *            the xStart to set
	 */
	public void setxStart(String xStart) {
		try {
			float x = Float.parseFloat(xStart);
			this.xStart = x;
		} catch (Exception e) {
			// TODO: handle exception
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
	public void addText(TextFragment text) {
		this.textFragments.add(text);
	}

	/**
	 * @return the textList
	 */
	public TextFragment getTextFragment(int index) {
		return textFragments.get(index);
	}

	/**
	 * @return the yStart
	 */
	public float getyStart() {
		return yStart;
	}

	/**
	 * @param string
	 *            the yStart to set
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
	 * @return the startTime
	 */
	public float getStartTime() {
		return startTime;
	}

	/**
	 * @param string
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		try {
			Float s = Float.parseFloat(startTime);
			this.startTime = s;
		} catch (Exception e) {
			this.startTime = Defaults.getStartTime();
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
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the currentTextFragment
	 */
	public TextFragment getCurrentTextFragment() {
		return currentTextFragment;
	}

	/**
	 * @param currentTextFragment
	 *            the currentTextFragment to set
	 */
	public void setCurrentTextFragment(TextFragment currentTextFragment) {
		this.currentTextFragment = currentTextFragment;
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
	public void setAlignment(String alignment) {
		this.alignment = alignment;
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
	public void setFont(String font) {
		this.font = font;
	}

	/**
	 * @return the fontcolor
	 */
	public String getFontcolor() {
		return fontcolor;
	}

	/**
	 * @param fontcolor
	 *            the fontcolor to set
	 */
	public void setFontcolor(String fontcolor) {
		this.fontcolor = fontcolor;
	}
}
