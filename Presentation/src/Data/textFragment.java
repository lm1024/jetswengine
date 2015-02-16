package Data;

public class textFragment {
	
	private String text;
	private String font;
	private int fontSize;
	private int colour;
	private boolean bold;
	private boolean underlined;
	private boolean italicised;
	private boolean superscript;
	private boolean subscript;
	private boolean strikethrough;
	
	/**
	 * @return true if fragment is superscript
	 */
	public boolean isSuperscript() {
		return superscript;
	}
	/**
	 * @param superscript the superscript to set
	 */
	public void setSuperscript(boolean superscript) {
		this.superscript = superscript;
	}
	/**
	 * @return the subscript
	 */
	public boolean isSubscript() {
		return subscript;
	}
	/**
	 * @param subscript the subscript to set
	 */
	public void setSubscript(boolean subscript) {
		this.subscript = subscript;
	}
	/**
	 * @return the strikethrough
	 */
	public boolean isStrikethrough() {
		return strikethrough;
	}
	/**
	 * @param strikethrough the strikethrough to set
	 */
	public void setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
	}
	
	/**
	 * @Initialises a new text fragment
	 */
	public textFragment(String text) {
		this.text = text;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
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
	 * @param font the font to set
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
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	/**
	 * @return the colour
	 */
	public int getColour() {
		return colour;
	}
	/**
	 * @param colour the colour to set
	 */
	public void setColour(int colour) {
		this.colour = colour;
	}
	/**
	 * @return the bold
	 */
	public boolean isBold() {
		return bold;
	}
	/**
	 * @param bold the bold to set
	 */
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	/**
	 * @return the underlined
	 */
	public boolean isUnderlined() {
		return underlined;
	}
	/**
	 * @param underlined the underlined to set
	 */
	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}
	/**
	 * @return the italicised
	 */
	public boolean isItalicised() {
		return italicised;
	}
	/**
	 * @param italicised the italicised to set
	 */
	public void setItalicised(boolean italicised) {
		this.italicised = italicised;
	}

}
