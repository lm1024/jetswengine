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

	ArrayList<TextFragment> stringBuffer = new ArrayList<TextFragment>();

	private Group group;

	/**
	 * 
	 */
	public TextHandler(Group group) {
		this.group = group;
	}

	/** Method adds a string to the buffer for later drawing on the screen. */
	public void addStringToBuffer(String string, String fontName, int fontSize, String fontColor, boolean bold,
			boolean italic, boolean underline, boolean strikethrough, boolean superscript, boolean subscript,
			String stringCase) {
		/* Initialise a new textFragment for the current string */
		TextFragment currentString = new TextFragment(string);

		currentString.setFont(fontName);
		currentString.setFontSize(fontSize);
		currentString.setColor(fontColor);
		currentString.setBold(bold);
		currentString.setItalicised(italic);
		currentString.setUnderlined(underline);
		currentString.setStrikethrough(strikethrough);

		currentString.setSubscript(subscript);
		currentString.setSuperscript(superscript);

		// TODO STILL NEED TO DO SOMETHING ABOUT STRINGCASE

		stringBuffer.add(currentString);
	}

	/** Error checking method for printing all the stored strings in the buffer. */
	public void printBuffer() {
		for (int i = 0; i < stringBuffer.size(); i++)
			System.out.println(stringBuffer.get(i).getText());
	}

	public void drawBuffer(int xStartPos, int yStartPos, int xEndPos, int yEndPos, String allignment) {
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
		webView.getEngine().loadContent(createHTMLStringFromBuffer(allignment));
		
		/* Section for resizing the text to fit in the box.  */
		webView.setPrefHeight(yEndPos-yStartPos);
		
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
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		Rectangle rect = new Rectangle(xStartPos, yStartPos, xEndPos, yEndPos);
		rect.setFill(new Color(1, 0, 1, 1));

		group.getChildren().addAll(rect, webView);
	}

	/**
	 * Formats a HTML string in order to display a rich text box full with the
	 * fragments from the buffer.
	 */
	private String createHTMLStringFromBuffer(String alignment) {
		/*
		 * Initialise string with the initial attributes. First is the div tag
		 * with a unique ID that is used for calculating the height of the text
		 * in the text box. Second is the body element that has a tag that stops
		 * the html being edited by the user.
		 */
		String htmlString = "<div id=\"mydiv\"><body contenteditable=\"false\">";

		/* Append the tag for the type of text alignment required. */
		/* Set alignment, with the default value being left */
		switch (alignment.toLowerCase()) {
		case "center":
			/* falls through */
		case "centre":
			htmlString = htmlString + "<p style=\"text-align: center;\">";
			break;
		case "right":
			htmlString = htmlString + "<p style=\"text-align: right;\">";
			break;
		case "justify":
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

			/* Combines the attribute strings */
			preBodyAttributes = preBodyAttributes + fontNameAndColorString + fontOpacityString + fontSizeString;
			postBodyAttributes = postBodyAttributes + "</font>" + "</span>" + "</span>";

			/*
			 * Combines the current htmlstring (to preserve anything already
			 * there), the preBodyAttributes, the body of the string and then
			 * postBodyAttributes.
			 */
			htmlString = htmlString + preBodyAttributes + currentString.getText() + postBodyAttributes;
		}

		/* Append the closing tags for the initial attributes. */
		htmlString = htmlString + "</p></body></div>";

		/* Empty the buffer so new strings can be added */
		stringBuffer.clear();

		return htmlString;
	}

	/**
	 * Method draws a string passed to the group set by the constructor.
	 * */
	public void drawString(String string, float xPos, float yPos, String fontName, int fontSize, Color fontColor,
			boolean bold, boolean italic, boolean underline, boolean strikethrough, boolean superscript,
			boolean subscript, String stringCase, String alignment) {
		/* Make new text object for the passed string */
		Text textObject;

		/* Initial string manipulation depending on stringCase variable */

		String[] parts = string.split(" ");
		String camelCaseString = "";

		switch (stringCase.toLowerCase()) {
		case "upper":
			textObject = new Text(string.toUpperCase());
			break;
		case "lower":
			textObject = new Text(string.toLowerCase());
			break;
		case "camel":
			/* Converts string to camel case (first letter capital) */
			for (String part : parts) {
				camelCaseString = camelCaseString + toProperCase(part) + " ";
			}
			textObject = new Text(camelCaseString.trim());
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

		// scenetitle.setWrappingWidth(111);// screensize - xcoordinate

		/* Set alignment, with the default value being left */
		switch (alignment.toLowerCase()) {
		case "center":
			/* falls through */
		case "centre":
			textObject.setTextAlignment(TextAlignment.CENTER);
			break;
		case "right":
			textObject.setTextAlignment(TextAlignment.RIGHT);
			break;
		case "justify":
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
