package XML;

import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Text;
import Data.TextFragment;

public class ShadingHandler extends DefaultHandler {

	private Text text;
	private XMLReader reader;
	private TextHandler parentHandler;
	private TextFragment tf;
	private StringBuffer contentBuffer = new StringBuffer();

	public ShadingHandler(XMLReader reader, TextHandler parent, Text text) {
		this.parentHandler = parent;
		this.text = text;
		this.reader = reader;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
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
			tf.setHighlightColor(attributes.getValue("highlightColor"));
			tf.setFont(attributes.getValue("font"));
			tf.setFontColor(attributes.getValue("fontcolor"));
			tf.setFontSize(attributes.getValue("fontsize"));
			tf.setText(attributes.getValue("text"));
			tf.setUnderlined(attributes.getValue("newline"));
		} else {
			System.err.println("Unknown start element encountered: "
					+ elementName);
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		contentBuffer.append(ch, start, length);
	}

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
				String strings[] = contentBuffer.toString().trim().split(Pattern.quote("\\n"));
				for (int i = 0; i < strings.length - 1; i++) {
					temp = tf.clone();
					temp.setNewline(true);
					temp.setText(strings[i]);
					text.addTextFragment(temp);
				}
				temp = tf.clone();
				temp.setNewline(contentBuffer.toString().trim().endsWith("\\n"));
				temp.setText(strings[strings.length - 1]);
				text.addTextFragment(temp);
			} else {
				tf.setText(contentBuffer.toString().trim());
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
