/**
 * 
 */
package XML;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import utils.Utils;
import Data.Graphic;
import Data.Slide;

/**
 * @author David
 *
 */
public class GraphicHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
	private Graphic graphic;
	private HashMap<String,String> currentObject = new HashMap<String,String>();

	/**
	 * 
	 */
	public GraphicHandler(XMLReader reader, SlideHandler parent,
			Slide slide) {
		this.parentHandler = parent;
		this.slide = slide;
		this.reader = reader;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		switch(elementName) {
		case "graphic":
			Utils.parse(currentObject, attributes, "type", "xstart", "ystart",
					"yend", "xend", "solid", "graphiccolor", "duration",
					"sourcefile", "starttime");
			break;
		case "shadow":
			reader.setContentHandler(new ShadowHandler(reader, this, graphic));
			reader.getContentHandler().startElement(uri, localName, qName,
					attributes);
			break;
		case "cyclicshading":
			/* Intentional fall through */
		case "horizontalshading":
			/* Intentional fall through */
		case "verticalshading":
			reader.setContentHandler(new ShadingHandler(reader, this, graphic));
			reader.getContentHandler().startElement(uri, localName, qName,
					attributes);
			break;
		case "oval":
		case "rectangle":
		case "line":
		case "circle":
		case "square":
		case "arrow":
		case "equitriangle":
		case "triangle":
		case "regpolygon":
		case "polygon":
		case "star":
		case "chord":
		case "arc":
			Utils.parse(currentObject, attributes, "xstart", "ystart",
					"yend", "xend", "solid", "graphiccolor", "duration",
					"sourcefile", "starttime");
			currentObject.put("type", elementName);
			break;
			
			
			
		}
		currentObject.put("type", Utils.validShape(elementName) ? elementName : currentObject.get("type"));
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("graphic")) {
			System.out.println(currentObject.get("type"));
			this.graphic = Graphic.makeGraphic(currentObject,parentHandler.getDefaults());
			slide.add(graphic);
			reader.setContentHandler(parentHandler);
		}
	}

}
