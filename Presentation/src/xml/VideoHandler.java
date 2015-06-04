/** (c) Copyright by WaveMedia. */
package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import data.Slide;
import data.Video;


/**
 * Video Handler class for parsing video Tags from XML Slideshows
 * 
 * @author dk666
 * @version 1.5 02/06/15
 */
public class VideoHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
	private Video video;

	/** Creates a new VideoHandler */
	public VideoHandler(XMLReader reader, SlideHandler parent,
			Slide slide) {
		this.parentHandler = parent;
		this.slide = slide;
		this.reader = reader;
	}

	/**
	 * Called when the XML Parser encounters a start tag for a video element.
	 * Assigns all the attributes of the video tag to a Video object and adds
	 * it to the current slideshow.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if(elementName.equals("video")) {
			video = new Video(parentHandler.getDefaults());
			video.setSourceFile(attributes.getValue("sourcefile"));
			video.setDuration(attributes.getValue("duration"));
			video.setStartTime(attributes.getValue("starttime"));
			video.setXStart(attributes.getValue("xstart"));
			video.setYStart(attributes.getValue("ystart"));
			video.setWidth(attributes.getValue("width"));
			video.setAutoplay(attributes.getValue("autoplay"));
			video.setLoop(attributes.getValue("loop"));
			slide.add(video);
			reader.setContentHandler(parentHandler);
		} else {
			System.err.println("Unknown Video start element encountered: " + elementName);
		}
	}
}
