/**
 * 
 */
package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Audio;
import Data.Slide;

/**
 * @author David
 *
 */
public class AudioHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
	private Audio audio;

	/**
	 * 
	 */
	public AudioHandler(XMLReader reader, SlideHandler parent,
			Slide slide) {
		this.parentHandler = parent;
		this.reader = reader;
		this.slide = slide;
		
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		//TODO: Call start Element when handler is changed to parse duration
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if(elementName.equals("audio")) {
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
			System.err.println("Unknown Audio element encountered: " + elementName);
		}
	}
}
