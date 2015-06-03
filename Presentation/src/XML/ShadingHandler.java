/** (c) Copyright by WaveMedia. */
package XML;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Graphic;

/**
 * Shading Handler class for parsing shading Tags from XML Slideshows.
 * 
 * @author dk666
 * @version 1.0 02/05/15
 */
public class ShadingHandler extends DefaultHandler {

	private Graphic graphic;
	private XMLReader reader;
	private GraphicHandler parentHandler;

	/* Lists of shading color(s) and their relative location(s) */
	private List<String> shadingList = new ArrayList<String>();
	private List<Float> stopValuesList = new ArrayList<Float>();

	/** Creates a new ShadingHandler */
	public ShadingHandler(XMLReader reader, GraphicHandler parent,
			Graphic graphic) {
		this.parentHandler = parent;
		this.graphic = graphic;
		this.reader = reader;
	}

	/**
	 * Called when the XML Parser encounters a start tag for a Shading element.
	 * Sets the shading type and any shading colors and stop values specified.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("cyclicshading")) {
			graphic.setShadingType("cyclic");
		} else if (elementName.equals("verticalshading")) {
			graphic.setShadingType("vertical");
		} else if (elementName.equals("horizontalshading")) {
			graphic.setShadingType("horizontal");
		} else if (elementName.equals("shading")) {

		} else {
			System.err.println("Unknown start element encountered: "
					+ elementName);
		}
		shadingList.add(attributes.getValue("shadingcolor"));
		Float f;
		try {
			f = new Float(attributes.getValue("stop"));
		} catch (Exception e) {
			f = parentHandler.getDefaults().getStopValue();
		}
		stopValuesList.add(f);
	}

	/**
	 * Called when the XML Parser encounters an end tag for a Shading element.
	 * Assigns the shading to the current graphic and returns to the parent
	 * handler.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName
				.matches("cyclicshading|horizontalshading|verticalshading")) {
			graphic.setShadingList(shadingList);
			graphic.setStopValuesList(stopValuesList);
			reader.setContentHandler(parentHandler);
		} else if (elementName.equals("shading")) {

		} else {
			System.err.println("Unknown Shading end element encountered: "
					+ elementName);
		}
	}
}
