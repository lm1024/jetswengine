/** (c) Copyright by WaveMedia. */
package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Slide;
import Data.Slideshow;

public class SlideShowHandler extends DefaultHandler {
	private XMLReader reader;
	private Slideshow slideshow;

	public SlideShowHandler(XMLReader reader, Slideshow slideshow) {
		this.slideshow = slideshow;
		this.reader = reader;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("slide")) {
			reader.setContentHandler(new SlideHandler(reader, this, slideshow));
			reader.getContentHandler().startElement(uri, localName, qName,
					attributes);
		} else if (elementName.equals("documentinfo")) {
			reader.setContentHandler(new DocumentInfoHandler(reader, this, slideshow));
		} else if (elementName.equals("defaultsettings")) {
			reader.setContentHandler(new DefaultSettingsHandler(reader, this, slideshow));
		}
	}
	
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("slideshow")) {
			Slide endSlide = new Slide(slideshow.getDefaults());
			
		} else {
			System.err.println("Unknown end element encountered: "
					+ elementName);
		}
	}
}