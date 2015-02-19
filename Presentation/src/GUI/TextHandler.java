/**
 * 
 */
package GUI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author tjd511
 * 
 */
public class TextHandler {

	private Group group;

	/**
	 * 
	 */
	public TextHandler(Group group) {
		// TODO Auto-generated constructor stub
		this.group = group;
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
