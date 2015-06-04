/** (c) Copyright by WaveMedia. */
package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import data.Audio;
import data.Slide;


/**
 * Audio Handler class for parsing audio Tags from XML Slideshows
 * 
 * @author dk666
 * @version 1.5 02/06/15
 */
public class AudioHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
	private Audio audio;

	/** Creates a new AudioHandler */
	public AudioHandler(XMLReader reader, SlideHandler parent, Slide slide) {
		this.parentHandler = parent;
		this.reader = reader;
		this.slide = slide;

	}

	/**
	 * Called when the XML Parser encounters a start tag for an Audio element.
	 * Assigns all the attributes of the audio tag to an Audio object and adds
	 * it to the current slideshow.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		/* sort out element name if (no) namespace in use */
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("audio")) {
			audio = new Audio(parentHandler.getDefaults());
			audio.setSourceFile(attributes.getValue("sourcefile"));
			audio.setDuration(attributes.getValue("duration"));
			audio.setStartTime(attributes.getValue("starttime"));
			audio.setXStart(attributes.getValue("xstart"));
			audio.setYStart(attributes.getValue("ystart"));
			audio.setAutoplay(attributes.getValue("autoplay"));
			audio.setLoop(attributes.getValue("loop"));
			audio.setPlayButtonOnly(attributes.getValue("playbuttononly"));
			audio.setVisibleControlsOnly(attributes.getValue("visiblecontrols"));
			audio.setWidth(attributes.getValue("width"));
			slide.add(audio);
			reader.setContentHandler(parentHandler);
		} else {
			System.err.println("Unknown Audio element encountered: "
					+ elementName);
		}
	}
}
