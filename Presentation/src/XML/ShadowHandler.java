/** (c) Copyright by WaveMedia. */
package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Graphic;

public class ShadowHandler extends DefaultHandler {

	private Graphic graphic;
	private XMLReader reader;
	private GraphicHandler parentHandler;

	public ShadowHandler(XMLReader reader, GraphicHandler parent, Graphic graphic) {
		this.parentHandler = parent;
		this.graphic = graphic;
		this.reader = reader;
	}

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
