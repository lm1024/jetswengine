/**
 * 
 */
package GUI;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.sun.webpane.platform.WebPage;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import Data.TextFragment;

/**
 * @author tjd511
 * 
 */
public class TextHandler {

	private ArrayList<TextFragment> stringBuffer = new ArrayList<TextFragment>();

	private Group group;

	/**
	 * 
	 */
	public TextHandler(Group group) {
		this.group = group;
	}

	/**
	 * Method adds a string to the buffer for later drawing on the screen. A
	 * text box can be formed and printed on the screen by calling the
	 * drawBuffer(int xStartPos, int yStartPos, int xEndPos, int yEndPos, String
	 * alignment) method.
	 * 
	 * @param string
	 *            the string to be added to the buffer
	 * @param fontName
	 *            the name of the font for the string
	 * @param fontSize
	 *            the size of the font in pt
	 * @param fontColor
	 *            the color for the string to be drawn in, in the form of a 8
	 *            digit hex number, ARGB.
	 * @param bold
	 *            boolean controlling if the text should be bold or not
	 * @param italic
	 *            boolean controlling if the text should be italic or not
	 * @param underline
	 *            boolean controlling if the text should be underlined or not
	 * @param strikethrough
	 *            boolean controlling if the text should be strikethough or not
	 * @param superscript
	 *            boolean controlling if the text should be superscript or not
	 * @param subscript
	 *            boolean controlling if the text should be subscript or not. In
	 *            the case that both superscript and subscript are true,
	 *            superscript takes precedence
	 * @param stringCase
	 *            enum that controls the case of the string stored. Options:
	 *            TextCase.UPPER - all chars get changed to upper case.
	 *            TextCase.LOWER - all chars get changed to lower case.
	 *            TextCase.CAPITALISED - the first letter of each word in the
	 *            string is capitalised
	 * */
	public void addStringToBuffer(String string, String fontName, int fontSize, String fontColor, boolean bold,
			boolean italic, boolean underline, boolean strikethrough, boolean superscript, boolean subscript,
			TextCase stringCase) {
		TextFragment currentString;

		/* Initial string manipulation depending on stringCase variable */
		switch (stringCase) {
		case UPPER:
			currentString = new TextFragment(string.toUpperCase());
			break;
		case LOWER:
			currentString = new TextFragment(string.toLowerCase());
			break;
		case CAPITALISED:
			/*
			 * Converts string to capitalised (first letter of each word
			 * capitalised)
			 */
			String[] parts = string.split(" ");
			String capitalisedString = "";

			for (String part : parts) {
				capitalisedString = capitalisedString + toProperCase(part) + " ";
			}
			currentString = new TextFragment(capitalisedString.trim());
			break;
		default:
			currentString = new TextFragment(string);
		}

		currentString.setFont(fontName);
		currentString.setFontSize(fontSize);
		currentString.setColor(fontColor);
		currentString.setBold(bold);
		currentString.setItalicised(italic);
		currentString.setUnderlined(underline);
		currentString.setStrikethrough(strikethrough);
		currentString.setSubscript(subscript);
		currentString.setSuperscript(superscript);

		stringBuffer.add(currentString);
	}

	/** Error checking method for printing all the stored strings in the buffer. */
	public void printBuffer() {
		for (int i = 0; i < stringBuffer.size(); i++)
			System.out.println(stringBuffer.get(i).getText());
	}

	/**
	 * Method forms a text box of a set size and adds all the strings contained
	 * in the buffer to it. Then draws the text box on the group specified
	 * during the instantiation of the object.
	 * 
	 * @param xStartPos
	 *            the starting x coordinate of the text box
	 * @param yStartPos
	 *            the starting y coordinate of the text box
	 * @param xEndPos
	 *            the ending x coordinate of the text box
	 * @param yEndPos
	 *            the ending y coordinate of the text box
	 * @param alignment
	 *            a string that can be used to control the alignment of the text
	 *            within the text box. Options: "centre" - centres the text.
	 *            "right" - sets the text to be right aligned. "justify" -
	 *            justifies the text. "left" - sets the text to be left aligned.
	 * 
	 * */
	public void drawBuffer(int xStartPos, int yStartPos, int xEndPos, int yEndPos, Alignment alignment) {
		/* Swaps around coordinates if incorrectly passed in */
		if (xStartPos > xEndPos) {
			int temp = xStartPos;
			xStartPos = xEndPos;
			xEndPos = temp;
		}
		if (yStartPos > yEndPos) {
			int temp = yStartPos;
			yStartPos = yEndPos;
			yEndPos = temp;
		}

		/* Instantiate the WebView that will be used to display the text */
		WebView webView = new WebView();

		/* Set starting position and width of the panel */
		webView.relocate(xStartPos, yStartPos);
		webView.setPrefWidth(xEndPos - xStartPos);

		File f = new File("custom.css");
		try {
			webView.getEngine().setUserStyleSheetLocation(f.toURI().toURL().toString());
		} catch (MalformedURLException ex) {
			System.out.println("custom.css does not exist.");
		}

		/*
		 * Disable the WebView so that scrolling + text selecting cannot happen.
		 * Also disables the right click menu popup.
		 */
		webView.setDisable(true);

		/*
		 * Loads a HTML string containing all the data about the strings into
		 * the webView
		 */
		webView.getEngine().loadContent(createHTMLStringFromBuffer(alignment));

		/* Section for resizing the text to fit in the box. */
		webView.setPrefHeight(yEndPos - yStartPos);

		/*
		 * Remove the background from the webView panel. Adapted from
		 * http://stackoverflow
		 * .com/questions/12421250/transparent-background-in-
		 * the-webview-in-javafx
		 */
		Field field;
		try {
			field = webView.getEngine().getClass().getDeclaredField("page");
			field.setAccessible(true);
			WebPage page = (WebPage) field.get(webView.getEngine());
			page.setBackgroundColor((new java.awt.Color(0, 0, 0, 0)).getRGB());
		} catch (NoSuchFieldException e) {
			// TODO this shit
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		/*
		 * TODO Decide how to handle background color. Could just be clear, then
		 * let user add box themselves.
		 */
		Rectangle rect = new Rectangle(xStartPos, yStartPos, xEndPos, yEndPos);
		rect.setFill(new Color(1, 0, 0, 0.2));

		group.getChildren().addAll(rect, webView);
	}

	/**
	 * Formats a HTML string in order to display a rich text box full with the
	 * fragments from the buffer.
	 * 
	 * @param alignment
	 *            an enum used to specify how the text is aligned in the text
	 *            box. Can be CENTER, RIGHT, JUSTIFY or LEFT. Defaults to LEFT.
	 */
	private String createHTMLStringFromBuffer(Alignment alignment) {
		/*
		 * Initialise string with the initial attribute of a body element that
		 * has a tag that stops the html being edited by the user.
		 */
		String htmlString = "<body contenteditable=\"false\">";

		/*
		 * Append the tag for the type of text alignment required. Set
		 * alignment, with the default value being left
		 */
		switch (alignment) {
		case CENTER:
			htmlString = htmlString + "<p style=\"text-align: center;\">";
			break;
		case RIGHT:
			htmlString = htmlString + "<p style=\"text-align: right;\">";
			break;
		case JUSTIFY:
			htmlString = htmlString + "<p style=\"text-align: justify;\">";
			break;
		default:
			htmlString = htmlString + "<p style=\"text-align: left;\">";
			break;
		}

		/*
		 * Loops through all the items in the buffer, and
		 */
		for (int i = 0; i < stringBuffer.size(); i++) {
			stringBuffer.get(i).getText();

			TextFragment currentString = stringBuffer.get(i);

			/*
			 * Initialise two strings that will be used to store the tags before
			 * and after the text body.
			 */
			String preBodyAttributes = "";
			String postBodyAttributes = "";

			/*
			 * Appends basic tags (tags that require no custom info) depending
			 * on the values of booleans
			 */
			if (currentString.isBold()) {
				preBodyAttributes = "<b>";
				postBodyAttributes = "</b>";
			}
			if (currentString.isItalicised()) {
				preBodyAttributes = preBodyAttributes + "<i>";
				postBodyAttributes = postBodyAttributes + "</i>";
			}
			if (currentString.isUnderlined()) {
				preBodyAttributes = preBodyAttributes + "<u>";
				postBodyAttributes = postBodyAttributes + "</u>";
			}
			if (currentString.isStrikethrough()) {
				preBodyAttributes = preBodyAttributes + "<strike>";
				postBodyAttributes = postBodyAttributes + "</strike>";
			}

			/*
			 * Still appending basic tags, but giving priority to superscript as
			 * opposed to subscript
			 */
			if (currentString.isSuperscript()) {
				preBodyAttributes = preBodyAttributes + "<sup>";
				postBodyAttributes = postBodyAttributes + "</sup>";
			} else if (currentString.isSubscript()) {
				preBodyAttributes = preBodyAttributes + "<sub>";
				postBodyAttributes = postBodyAttributes + "</sub>";
			}

			/* Section for font, font size and font color */
			String colorString = currentString.getColor();

			/*
			 * Initialise new string to store the preBody font name, and font
			 * color information (last 6 chars of color string contain RRGGBB in
			 * hex).
			 */
			String fontNameAndColorString = "<font face =\"" + currentString.getFont() + "\" color=\""
					+ colorString.substring(2, colorString.length()) + "\">";

			/*
			 * Convert the opacity to a 1 decimal place number so that it works
			 * in the opacity tag (first 2 chars of color string contain alpha
			 * in hex).
			 */
			DecimalFormat df = new DecimalFormat("0.0");
			String opacityFormatted = df.format((Integer.parseInt(colorString.substring(0, 2), 16)) / 255f);

			/*
			 * Initialise new string to store the preBody font opacity
			 * information
			 */
			String fontOpacityString = "<span style=\"opacity:" + opacityFormatted + "\">";

			/* Initialise new string to store the preBody font size information */
			String fontSizeString = "<span style=\"font-size:" + currentString.getFontSize() + "px\">";

			/*
			 * Highlighting section. Initialise string with the 8bit hex value
			 * for highlight.
			 */
			String highlightString = currentString.getHighlight();

			/*
			 * Use the decimal format to convert the opacity to 1dp number so it
			 * works with rgba css tag
			 */
			df = new DecimalFormat("0.0");
			String highlightingOpacityFormatted = df
					.format((Integer.parseInt(highlightString.substring(0, 2), 16)) / 255f);

			/*
			 * Convert r, g and b from 2digit hex to integer values to work with
			 * rgba css tag
			 */
			String redHighlightingFormatted = Integer.toString((Integer.parseInt(highlightString.substring(2, 4), 16)));
			String greenHighlightingFormatted = Integer
					.toString((Integer.parseInt(highlightString.substring(4, 6), 16)));
			String blueHighlightingFormatted = Integer
					.toString((Integer.parseInt(highlightString.substring(6, 8), 16)));

			/* Combine all of the highlight strings */
			String highlightingString = "<span style=\"background-color: rgba(" + redHighlightingFormatted + ","
					+ greenHighlightingFormatted + "," + blueHighlightingFormatted + "," + highlightingOpacityFormatted
					+ ")\">";

			/* Combines the attribute strings */
			preBodyAttributes = preBodyAttributes + fontNameAndColorString + fontOpacityString + fontSizeString
					+ highlightingString;
			/*
			 * Close all of the elements that have been added to the html
			 * string. These are not correctly ordered, but it does not matter.
			 */
			postBodyAttributes = postBodyAttributes + "</font>" + "</span>" + "</span>" + "</span>";

			/*
			 * Combines the current htmlstring (to preserve anything already
			 * there), the preBodyAttributes, the body of the string and then
			 * postBodyAttributes.
			 */
			htmlString = htmlString + preBodyAttributes + currentString.getText() + postBodyAttributes;
		}

		/*
		 * Append the closing tags for the initial attribute and the text
		 * alignment paragraph tag.
		 */
		htmlString = htmlString + "</p></body>";

		/* Empty the buffer so new strings can be added */
		stringBuffer.clear();
		
		return htmlString;
	}

	/**
	 * Method draws a string passed to the group set by the constructor.
	 * */
	public void drawString(String string, float xPos, float yPos, String fontName, int fontSize, Color fontColor,
			boolean bold, boolean italic, boolean underline, boolean strikethrough, boolean superscript,
			boolean subscript, TextCase stringCase, Alignment alignment) {
		/* Initial string manipulation depending on stringCase variable */

		/* Make new text object for the passed string */
		Text textObject;
		switch (stringCase) {
		case UPPER:
			textObject = new Text(string.toUpperCase());
			break;
		case LOWER:
			textObject = new Text(string.toLowerCase());
			break;
		case CAPITALISED:
			/*
			 * Converts string to capitalised (first letter of each word
			 * capitalised)
			 */
			String[] parts = string.split(" ");
			String capitalisedString = "";

			for (String part : parts) {
				capitalisedString = capitalisedString + toProperCase(part) + " ";
			}
			textObject = new Text(capitalisedString.trim());
			break;
		default:
			textObject = new Text(string);
		}

		/* Move string to required position */
		textObject.relocate(xPos, yPos);

		/*
		 * Set font depending upon the fontName, size, and the values of bold
		 * and italic
		 */
		if (bold && italic)
			textObject.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.ITALIC, (double) fontSize));
		else if (bold)
			textObject.setFont(Font.font(fontName, FontWeight.BOLD, (double) fontSize));
		else if (italic)
			textObject.setFont(Font.font(fontName, FontPosture.ITALIC, (double) fontSize));
		else
			textObject.setFont(Font.font(fontName, (double) fontSize));

		/* Set other parameters about the text */
		textObject.setUnderline(underline);
		textObject.setStrikethrough(strikethrough);

		textObject.setWrappingWidth(70);// screensize - xcoordinate

		/* Set alignment, with the default value being left */
		switch (alignment) {
		case CENTER:
			textObject.setTextAlignment(TextAlignment.CENTER);
			break;
		case RIGHT:
			textObject.setTextAlignment(TextAlignment.RIGHT);
			break;
		case LEFT:
			textObject.setTextAlignment(TextAlignment.JUSTIFY);
			break;
		default:
			break;
		}

		group.getChildren().add(textObject);
	}

	private String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

}
