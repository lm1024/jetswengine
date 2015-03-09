/**
 * 
 */
package XML;

import java.util.HashMap;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import utils.Utils;
import Data.Defaults;
import Data.Graphic;
import Data.OtherShapes.Polygon;
import Data.OtherShapes.Triangle;
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
	private HashMap<String, String> currentObject = new HashMap<String, String>();
	private List<Float> xPoints;
	private List<Float> yPoints;

	/**
	 * 
	 */
	public GraphicHandler(XMLReader reader, SlideHandler parent, Slide slide) {
		this.parentHandler = parent;
		this.slide = slide;
		this.reader = reader;
		this.graphic = new Graphic(getDefaults());
	}

	public Defaults getDefaults() {
		return parentHandler.getDefaults();
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		switch (elementName) {
		case "graphic":
			Utils.parse(currentObject, attributes, "type", "xstart", "ystart",
					"yend", "xend", "solid", "graphiccolor", "duration",
					"starttime", "outlinecolor", "outlinethickness", "size",
					"radius", "numberofsides", "numberofpoints", "length",
					"arcangle");
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
			Utils.parse(currentObject, attributes, "type", "xstart", "ystart",
					"yend", "xend", "solid", "graphiccolor", "duration",
					"starttime", "outlinecolor", "outlinethickness", "size",
					"radius", "numberofsides", "numberofpoints", "length",
					"arcangle");
			currentObject.put("type", elementName);
			break;
		}
		currentObject.put("type", Utils.validShape(elementName) ? elementName
				: currentObject.get("type"));
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
			Graphic temp = Graphic.makeGraphic(currentObject, getDefaults());
			temp.setShadingList(this.graphic.getShadingList());
			temp.setShadingType(this.graphic.getShadingType());
			temp.setShadow(this.graphic.getShadow());
			if(temp.getClass().getSimpleName().equals("Triangle")) {
				((Triangle)temp).setxPoints(xPoints);
				((Triangle)temp).setyPoints(yPoints);
			} else if(temp.getClass().getSimpleName().equals("Polygon")) {
				((Polygon)temp).setxPoints(xPoints);
				((Polygon)temp).setyPoints(yPoints);
			}
			slide.add(temp);
			reader.setContentHandler(parentHandler);
		}
	}

}
