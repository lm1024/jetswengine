/** (c) Copyright by WaveMedia. */
package xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import data.Slideshow;


/**
 * ImprovedXMLReader class for parsing SmartSlidesshows.
 * Uses a SAXParser to parse an XML SmartSlideshow.
 * 
 * @author dk666
 * @version 1.1 02/04/15
 */
public class ImprovedXMLReader extends DefaultHandler {
	
	private Slideshow slideshow;
	

	/**
	 * Converts SmartSlideshow in XML to live data.
	 * 
	 * @param filename
	 *           location of XML file to open
	 */
	public ImprovedXMLReader(String filename) throws IOException {
		slideshow = new Slideshow();
		readXMLFile(filename);
	}

	/**
	 * Converts SmartSlideshow in XML to live data.
	 * 
	 * @return Slideshow
	 *           Slideshow represented by XML
	 */
	public Slideshow getSlideshow() {
		return slideshow;
	}

	/**
	 * Attempts to parse the XML file use the SAXParser.
	 * Sets the slideshow to null if errors occur.
	 * 
	 */
	private void readXMLFile(String inputFile) throws IOException {
		try {
			// use the default parser
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			XMLReader reader = saxParser.getXMLReader();
			reader.setContentHandler(new SlideShowHandler(reader,slideshow));
			reader.parse(inputFile);
			// parse the input
			//saxParser.parse(inputFile, this);
		} catch (ParserConfigurationException pce) {
			//pce.printStackTrace();
			this.slideshow = null;
		} catch (SAXException saxe) {
			//saxe.printStackTrace();
			this.slideshow = null;
		}
	}
}
