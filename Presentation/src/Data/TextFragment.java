package Data;

import javafx.scene.text.Font;

public class TextFragment {

	private String text;
	private String font;
	private int fontSize;
	private String color;
	private String highlightColor;
	private boolean bold;
	private boolean underlined;
	private boolean italicised;
	private boolean superscript;
	private boolean subscript;
	private boolean strikethrough;
	private String textCase;

	/**
	 * @Initialises a new text fragment
	 */
	public TextFragment(Text textcontainer) {
		System.out.println(textcontainer.getFont());
		this.font = textcontainer.getFont();
	}

	/**
	 * @return true if fragment is superscript
	 */
	public boolean isSuperscript() {
		return superscript;
	}

	/**
	 * @param superscript
	 *            the superscript to set
	 */
	public void setSuperscript(String string) {
		try {
			boolean b = Boolean.parseBoolean(string);
			this.superscript = b;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @return the subscript
	 */
	public boolean isSubscript() {
		return subscript;
	}

	/**
	 * @param subscript
	 *            the subscript to set
	 */
	public void setSubscript(String string) {
		try {
			boolean b = Boolean.parseBoolean(string);
			this.subscript = b;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @return the strikethrough
	 */
	public boolean isStrikethrough() {
		return strikethrough;
	}

	/**
	 * @param strikethrough
	 *            the strikethrough to set
	 */
	public void setStrikethrough(String string) {
		try {
			boolean b = Boolean.parseBoolean(string);
			this.strikethrough = b;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
		font = capitaliseEachFirstLetter(font);
		if (Font.getFontNames().contains(font)) {
			this.font = font;
		}

	}

	static String capitaliseEachFirstLetter(String s) {
		String[] words = s.split(" ");
		String finalString = "";
		for (String word : words) {
			finalString += word.substring(0, 1).toUpperCase()
					+ word.substring(1).toLowerCase() + " ";
		}
		return finalString.substring(0, finalString.length() - 1);
	}

	/**
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(String string) {
		try {
			int i = Integer.parseInt(string);
			this.fontSize = i;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param colour
	 *            the colour to set
	 */
	public void setColor(String color) {
		try {
			if (color.matches("^([#]([0-9a-fA-F]{8}))$")) {
				this.color = color;
			}
		} catch (NullPointerException npe) {
			/* Do nothing */
		}
	}

	/**
	 * @return the bold
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * @param bold
	 *            the bold to set
	 */
	public void setBold(String string) {
		try {
			boolean b = Boolean.parseBoolean(string);
			this.bold = b;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @return the underlined
	 */
	public boolean isUnderlined() {
		return underlined;
	}

	/**
	 * @param underlined
	 *            the underlined to set
	 */
	public void setUnderlined(String string) {
		try {
			boolean b = Boolean.parseBoolean(string);
			this.underlined = b;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @return the italicised
	 */
	public boolean isItalicised() {
		return italicised;
	}

	/**
	 * @param italicised
	 *            the italicised to set
	 */
	public void setItalicised(String string) {
		try {
			boolean b = Boolean.parseBoolean(string);
			this.italicised = b;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @return the textCase
	 */
	public String getTextCase() {
		return textCase;
	}

	/**
	 * @param textCase
	 *            the textCase to set
	 */
	public void setTextCase(String textCase) {
		this.textCase = textCase;
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
	public void setHighlightColor(String highlightColor) {
		try {
			if (highlightColor.matches("^[#]([0-9a-fA-F]{8}))$")) {
				this.highlightColor = highlightColor;
			}
		} catch (NullPointerException npe) {
			/* Do nothing */
		}

	}

}
