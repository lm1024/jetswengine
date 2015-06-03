/** (c) Copyright by WaveMedia. */
package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Graphic;

/**
 * Shadow Handler class for parsing shadow Tags from XML Slideshows.
 * 
 * @author dk666
 * @version 1.0 02/05/15
 */
public class ShadowHandler extends DefaultHandler {

	private Graphic graphic;
	private XMLReader reader;
	private GraphicHandler parentHandler;

	/** Creates a new QuestionHandler */
	public ShadowHandler(XMLReader reader, GraphicHandler parent, Graphic graphic) {
		this.parentHandler = parent;
		this.graphic = graphic;
		this.reader = reader;
	}

	/**
	 * Called when the XML Parser encounters a start tag for a shadow element.
	 * Assigns all the attributes of the shadow tag to a graphic object.
	 * Returns control back to parent handler after parsing.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("shadow")) {
			graphic.setShadow(attributes.getValue("weight"));
			reader.setContentHandler(parentHandler);
		} else {
			System.err.println("Unknown Shadow start element encountered: "
					+ elementName);
		}
	}
}
