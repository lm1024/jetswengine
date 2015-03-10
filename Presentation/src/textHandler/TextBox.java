package textHandler;

import java.util.ArrayList;

import javafx.scene.text.Font;

import Data.Defaults;
import Data.TextFragment;

public class TextBox {
	private final int xStart;
	private final int yStart;
	private final int xEnd;
	private final int yEnd;
	private final String backgroundColor;
	private final Alignment alignment;
	private ArrayList<TextFragment> stringBuffer = new ArrayList<TextFragment>();

	private TextBox(TextBoxBuilder builder) {
		this.xStart = builder.xStart;
		this.yStart = builder.yStart;
		this.xEnd = builder.xEnd;
		this.yEnd = builder.yEnd;
		this.backgroundColor = builder.backgroundColor;
		this.alignment = builder.alignment;
		this.stringBuffer = builder.stringBuffer;
	}

	public int getXStart() {
		return xStart;
	}

	public int getYStart() {
		return yStart;
	}

	public int getXEnd() {
		return xEnd;
	}

	public int getYEnd() {
		return yEnd;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public Alignment getAlignment() {
		return alignment;
	}
	
	public ArrayList<TextFragment> getStringBuffer() {
		return stringBuffer;
	}

	public static class TextBoxBuilder {
		private final int xStart;
		private final int yStart;

		private ArrayList<TextFragment> stringBuffer = new ArrayList<TextFragment>();

		private int xEnd;
		private int yEnd;
		private String backgroundColor = "#00000000";
		private Alignment alignment = Alignment.LEFT;

		public TextBoxBuilder(int xStart, int yStart) {
			this.xStart = xStart;
			this.yStart = yStart;
			this.xEnd = xStart + 200;
			this.yEnd = yStart + 200;
		}

		public TextBoxBuilder xEnd(int xEnd) {
			this.xEnd = xEnd;
			return this;
		}

		public TextBoxBuilder yEnd(int yEnd) {
			this.yEnd = yEnd;
			return this;
		}

		public TextBoxBuilder backgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}

		public TextBoxBuilder alignment(Alignment alignment) {
			this.alignment = alignment;
			return this;
		}

		public TextBoxBuilder textFragmentList(TextFragmentList textFragmentList) {
			this.stringBuffer = textFragmentList.getList();
			return this;
		}

		public TextBox build() {
			return new TextBox(this);
		}

	}
	
	public static class TextFragmentBuilder {

		private final String string;

		private String fontName = Font.getDefault().getName();
		private String fontColor = "#ff000000";
		private int fontSize = 20;
		private String highlightColor = "#00000000";
		private boolean newline;
		private boolean bold;
		private boolean italic;
		private boolean underline;
		private boolean strikethrough;
		private boolean superscript;
		private boolean subscript;

		public TextFragmentBuilder(String string) {
			this.string = string;
		}

		public TextFragmentBuilder fontName(String fontName) {
			this.fontName = fontName;
			return this;
		}

		public TextFragmentBuilder fontColor(String fontColor) {
			this.fontColor = fontColor;
			return this;
		}

		public TextFragmentBuilder fontSize(int fontSize) {
			this.fontSize = fontSize;
			return this;
		}

		public TextFragmentBuilder highlightColor(String highlightColor) {
			this.highlightColor = highlightColor;
			return this;
		}

		public TextFragmentBuilder newline(boolean newline) {
			this.newline = newline;
			return this;
		}

		public TextFragmentBuilder bold(boolean bold) {
			this.bold = bold;
			return this;
		}

		public TextFragmentBuilder italic(boolean italic) {
			this.italic = italic;
			return this;
		}

		public TextFragmentBuilder underline(boolean underline) {
			this.underline = underline;
			return this;
		}

		public TextFragmentBuilder strikethrough(boolean strikethrough) {
			this.strikethrough = strikethrough;
			return this;
		}

		public TextFragmentBuilder superscript(boolean superscript) {
			this.superscript = superscript;
			return this;
		}

		public TextFragmentBuilder subscript(boolean subscript) {
			this.subscript = subscript;
			return this;
		}

		public TextFragment build() {
			TextFragment textFragment = new TextFragment(new Defaults());
			textFragment.setText(string);
			textFragment.setFont(fontName);
			textFragment.setFontColor(fontColor);
			textFragment.setFontSize((int) fontSize);
			textFragment.setHighlightColor(highlightColor);
			textFragment.setNewline(newline);
			textFragment.setBold(bold);
			textFragment.setItalicised(italic);
			textFragment.setUnderlined(underline);
			textFragment.setStrikethrough(strikethrough);
			textFragment.setSuperscript(superscript);
			textFragment.setSubscript(subscript);

			return textFragment;
		}

	}	
}
