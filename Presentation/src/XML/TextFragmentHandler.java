/** (c) Copyright by WaveMedia. */
package XML;

import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Text;
import Data.TextFragment;

/**
 * Text Fragment Handler class for parsing slide Tags from XML Slideshows.
 * 
 * @author dk666
 * @version 2.3 02/05/15
 */
public class TextFragmentHandler extends DefaultHandler {

	private Text text;
	private XMLReader reader;
	private TextHandler parentHandler;
	private TextFragment tf;

	/* String buffer for storing the content of an element */
	private StringBuffer contentBuffer = new StringBuffer();

	/* Variables for preserving correct number of spaces after string trimmed. */
	private String afterSpaces;
	private String beforeSpaces;

	/** Creates a new TextFragmentHandler */
	public TextFragmentHandler(XMLReader reader, TextHandler parent, Text text) {
		this.parentHandler = parent;
		this.text = text;
		this.reader = reader;
	}

	/**
	 * Called when the XML Parser encounters a start tag for a textfragment
	 * element. Assigns all the attributes of the textfragment tag to a Text
	 * Fragment object.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		afterSpaces = "";
		beforeSpaces = "";
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("richtext")) {
			tf = new TextFragment(parentHandler.getDefaults());
			tf.setFont(text.getFont());
			tf.setFontColor(text.getFontColor());
			tf.setFontSize(text.getFontSize());
			tf.setHighlightColor(text.getHighlightColor());
			tf.setBold(attributes.getValue("bold"));
			tf.setUnderlined(attributes.getValue("underlined"));
			tf.setItalicised(attributes.getValue("italicised"));
			tf.setSuperscript(attributes.getValue("superscript"));
			tf.setSubscript(attributes.getValue("subscript"));
			tf.setStrikethrough(attributes.getValue("strikethrough"));
			tf.setHighlightColor(attributes.getValue("highlightcolor"));
			tf.setFont(attributes.getValue("font"));
			tf.setFontColor(attributes.getValue("fontcolor"));
			tf.setFontSize(attributes.getValue("fontsize"));
			tf.setText(attributes.getValue("text"));
			tf.setNewline(attributes.getValue("newline"));

			try {
				int spaces = Integer
						.parseInt(attributes.getValue("afterspace"));
				for (int i = 0; i < spaces; i++) {
					afterSpaces += " ";
				}
			} catch (Exception e) {
				afterSpaces = "";
			}

			try {
				int spaces = Integer.parseInt(attributes
						.getValue("beforespace"));
				for (int i = 0; i < spaces; i++) {
					beforeSpaces += " ";
				}
			} catch (Exception e) {
				beforeSpaces = "";
			}

		} else {
			System.err.println("Unknown start element encountered: "
					+ elementName);
		}
	}

	/**
	 * Called by the parser when it encounters characters in the main body of an
	 * element.
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		contentBuffer.append(ch, start, length);
	}

	/**
	 * Called when the XML Parser encounters a end tag for an element. Breaks
	 * text fragments into multiple text fragments if they contain newlines.
	 * Adds the correct spacing before and after each fragment. Returns control
	 * back to the parent Text handler.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("richtext")) {
			if (contentBuffer.toString().trim().contains("\\n")) {
				TextFragment temp;
				String strings[] = contentBuffer.toString().trim()
						.split(Pattern.quote("\\n"));
				for (int i = 0; i < strings.length - 1; i++) {
					temp = tf.clone();
					temp.setNewline(true);
					temp.setText(beforeSpaces + strings[i] + afterSpaces);
					text.addTextFragment(temp);
				}
				temp = tf.clone();
				temp.setNewline(contentBuffer.toString().trim().endsWith("\\n"));
				temp.setText(beforeSpaces + strings[strings.length - 1]
						+ afterSpaces);
				text.addTextFragment(temp);
			} else {
				tf.setText(beforeSpaces + contentBuffer.toString().trim()
						+ afterSpaces);
				text.addTextFragment(tf);
			}
			reader.setContentHandler(parentHandler);
		} else {
			System.err
					.println("Unknown Text Fragment end element encountered: "
							+ elementName);
		}
	}
}
