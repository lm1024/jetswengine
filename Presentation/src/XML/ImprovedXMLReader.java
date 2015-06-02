/** (c) Copyright by WaveMedia. */
package XML;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Slideshow;

public class ImprovedXMLReader extends DefaultHandler {
	
	private Slideshow slideshow;
	

	public ImprovedXMLReader(String filename) throws IOException {
		slideshow = new Slideshow();
		readXMLFile(filename);
	}

	public Slideshow getSlideshow() {
		return slideshow;
	}

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
