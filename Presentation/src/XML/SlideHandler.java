/**
 * 
 */
package XML;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Defaults;
import Data.Slide;
import Data.Slideshow;

/**
 * @author David
 *
 */
public class SlideHandler extends DefaultHandler {

	private Slideshow slideshow;
	private XMLReader reader;
	private ContentHandler parentHandler;
	private Slide slide;
	
	public Defaults getDefaults() {
		return slideshow.getDefaults();
	}
	/**
	 * 
	 */
	public SlideHandler(XMLReader reader, ContentHandler parent,
			Slideshow slideshow) {
		this.parentHandler = parent;
		this.slideshow = slideshow;
		this.reader = reader;
		this.slide = new Slide(getDefaults());
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		// TODO: Call start Element when handler is changed to parse duration
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		switch (elementName) {
		case "slide":
			slide.setDuration(attributes.getValue("duration"));
			slide.setBackgroundColor(attributes.getValue("backgroundcolor"));
			break;
		case "text":
			reader.setContentHandler(new TextHandler(reader, this, slide));
			reader.getContentHandler().startElement(uri, localName, qName,
					attributes);
			break;
		case "graphic":
			reader.setContentHandler(new GraphicHandler(reader, this, slide));
			reader.getContentHandler().startElement(uri, localName, qName,
					attributes);
			break;
		case "image":
			reader.setContentHandler(new ImageHandler(reader, this, slide));
			reader.getContentHandler().startElement(uri, localName, qName,
					attributes);
			break;
		case "audio":
			reader.setContentHandler(new AudioHandler(reader, this, slide));
			reader.getContentHandler().startElement(uri, localName, qName,
					attributes);
			break;
		case "video":
			reader.setContentHandler(new VideoHandler(reader, this, slide));
			reader.getContentHandler().startElement(uri, localName, qName,
					attributes);
			break;
		default:
			break;
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("slide")) {
			slideshow.addSlide(slide);
			reader.setContentHandler(parentHandler);
		} else if (elementName.equals("audio")) {
			/* Suppress error warning on audio end element */
		} else if (elementName.equals("video")) {
			/* Suppress error warning on video end element */
		} else {
			System.err.println("Unknown end element encountered: "
					+ elementName);
		}
	}
}
