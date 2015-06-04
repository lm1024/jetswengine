/** (c) Copyright by WaveMedia. */
package xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import data.Defaults;
import data.Slide;
import data.Text;
import data.TextFragment;


/**
 * Text Handler class for parsing slide Tags from XML Slideshows.
 * 
 * @author dk666
 * @version 2.3 02/05/15
 */
public class TextHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
	private Text text;

	/* String buffer for storing the content of an element */
	private StringBuffer contentBuffer = new StringBuffer();

	/** Creates a new TextHandler */
	public TextHandler(XMLReader reader, SlideHandler parent, Slide slide) {
		this.parentHandler = parent;
		this.slide = slide;
		this.reader = reader;
	}

	/** Returns current slideshow defaults */
	public Defaults getDefaults() {
		return parentHandler.getDefaults();
	}

	/**
	 * Called when the XML Parser encounters a start tag for a text element.
	 * Assigns all the attributes of the text tag to a Text object.
	 * Reassigns the correct content handler after parsing completes.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		/* sort out element name if (no) namespace in use */
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		contentBuffer.setLength(0);
		if (elementName.equals("text")) {
			text = new Text(getDefaults());
			text.setSourceFile(attributes.getValue("sourcefile"));
			text.setDuration(attributes.getValue("duration"));
			text.setStartTime(attributes.getValue("starttime"));
			text.setXStart(attributes.getValue("xstart"));
			text.setYStart(attributes.getValue("ystart"));
			text.setXEnd(attributes.getValue("xend"));
			text.setYEnd(attributes.getValue("yend"));
			text.setAlignment(attributes.getValue("alignment"));
			text.setFont(attributes.getValue("font"));
			text.setFontColor(attributes.getValue("fontcolor"));
			text.setFontSize(attributes.getValue("fontsize"));
			text.setBackgroundColor(attributes.getValue("backgroundcolor"));
			text.setHighlightColor(attributes.getValue("highlightcolor"));
		} else if (elementName.equals("richtext")) {
			reader.setContentHandler(new TextFragmentHandler(reader, this, text));
			reader.getContentHandler().startElement(uri, localName, qName,
					attributes);
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
	 * Called when the XML Parser encounters a end tag for an element. 
	 * Creates a text fragment if PWS text is being used.
	 * Adds the text to the current slide.
	 * Returns control to the parent handler.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		
		if(text.getSourceFile() != null) {
			StringBuffer textFromFile = new StringBuffer();
			String line;
			try (BufferedReader br = new BufferedReader(new FileReader(new File(text.getSourceFile())))) {
				/* Loop through and add each line of the CSV to buttonInfo */
				while ((line = br.readLine()) != null) {
					textFromFile.append(line + "\n");
				}
				textFromFile.deleteCharAt(textFromFile.length() - 1);
			} catch (Exception e) {
				textFromFile = null;
			}
			if(textFromFile != null) {
				contentBuffer = textFromFile;
			}
		}
		if (elementName.equals("text")) {
			if (!contentBuffer.toString().trim().equals("")) {
				TextFragment tf = new TextFragment(parentHandler.getDefaults());
				tf.setFont(text.getFont());
				tf.setFontColor(text.getFontColor());
				tf.setFontSize(text.getFontSize());
				tf.setText(contentBuffer.toString().trim());
				text.addTextFragment(tf);
			}
			slide.add(text);
			reader.setContentHandler(parentHandler);
		} else {
			System.err.println("Unknown Text end element encountered: "
					+ elementName);
		}
	}

}
