package Data;

public class TextFragment {

	private String text;
	private String font;
	private int fontSize;
	private String color;
	private boolean bold;
	private boolean underlined;
	private boolean italicised;
	private boolean superscript;
	private boolean subscript;
	private boolean strikethrough;
	private String textCase;

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
		boolean b = Boolean.parseBoolean(string);
		this.superscript = b;
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
		boolean b = Boolean.parseBoolean(string);
		this.subscript = b;
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
		boolean b = Boolean.parseBoolean(string);
		this.strikethrough = b;
	}

	/**
	 * @Initialises a new text fragment
	 */
	public TextFragment() {

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
		this.font = font;
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
		int i = Integer.parseInt(string);
		this.fontSize = i;
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
		this.color = color;
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
		boolean b = Boolean.parseBoolean(string);
		this.bold = b;
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
		boolean b = Boolean.parseBoolean(string);
		this.underlined = b;
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
		boolean b = Boolean.parseBoolean(string);
		this.italicised = b;
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

}
