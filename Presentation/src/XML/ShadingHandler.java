package XML;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Graphic;

public class ShadingHandler extends DefaultHandler {

	private Graphic graphic;
	private XMLReader reader;
	private GraphicHandler parentHandler;
	private List<String> shadingList = new ArrayList<String>();

	public ShadingHandler(XMLReader reader, GraphicHandler parent, Graphic graphic) {
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
		if (elementName.equals("richtext")) {

		} else {
			System.err.println("Unknown start element encountered: "
					+ elementName);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("richtext")) {
			reader.setContentHandler(parentHandler);
		} else {
			System.err
					.println("Unknown Text Fragment end element encountered: "
							+ elementName);
		}
	}
}
