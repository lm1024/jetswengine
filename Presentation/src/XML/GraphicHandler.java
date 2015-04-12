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
import Data.CommonShapes;
import Data.Defaults;
import Data.Graphic;
import Data.OtherShapes;
import Data.RadialShapes;
import Data.CommonShapes.*;
import Data.OtherShapes.*;
import Data.RadialShapes.*;
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
	private String tempDuration, tempStartTime, tempGraphicColor;

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
	
	private void updateGraphicType(String graphicType) {
		switch(graphicType) {
		case "oval":
			this.graphic = (new CommonShapes(getDefaults())).new Oval(getDefaults());
			break;
		case "rectangle":
			this.graphic = (new CommonShapes(getDefaults())).new Rectangle(getDefaults());
			break;
		case "line":
			this.graphic = (new OtherShapes(getDefaults())).new Line(getDefaults());
			break;
		case "circle":
			this.graphic = (new RadialShapes(getDefaults())).new Circle(getDefaults());
			break;
		case "square":
			this.graphic = (new RadialShapes(getDefaults())).new Square(getDefaults());
			break;
		case "arrow":
			this.graphic = (new OtherShapes(getDefaults())).new Arrow(getDefaults());
			break;
		case "equitriangle":
			this.graphic = (new RadialShapes(getDefaults())).new EquilateralTriangle(getDefaults());
			break;
		case "triangle":
			this.graphic = (new OtherShapes(getDefaults())).new Triangle(getDefaults());
			break;
		case "regpolygon":
			this.graphic = (new RadialShapes(getDefaults())).new RegularPolygon(getDefaults());
			break;
		case "polygon":
			this.graphic = (new OtherShapes(getDefaults())).new Polygon(getDefaults());
			break;
		case "star":
			this.graphic = (new RadialShapes(getDefaults())).new Star(getDefaults());
			break;
		case "chord":
			this.graphic = (new OtherShapes(getDefaults())).new Chord(getDefaults());
			break;
		case "arc":
			this.graphic = (new OtherShapes(getDefaults())).new Arc(getDefaults());
			break;
		default:
			break;
		}
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
			if(attributes.getValue("type") != null) {
				updateGraphicType(attributes.getValue("type"));
				graphic.setDuration(attributes.getValue("duration"));
				graphic.setGraphicColor(attributes.getValue("graphiccolor"));
				graphic.setStartTime(attributes.getValue("starttime"));
				startElement(uri,attributes.getValue("type"),attributes.getValue("type"),attributes);
			} else {
				this.tempDuration = attributes.getValue("duration");
				this.tempGraphicColor = attributes.getValue("graphiccolor");
				this.tempStartTime = attributes.getValue("starttime");
			}
			break;
		case "shadow":
			graphic.setShadow(attributes.getValue("weight"));
//			reader.setContentHandler(new ShadowHandler(reader, this, graphic));
//			reader.getContentHandler().startElement(uri, localName, qName,
//					attributes);
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
			if(graphic instanceof Graphic) {
				Oval oval = (new CommonShapes(getDefaults()).new Oval(getDefaults()));
				oval.setDuration(tempDuration);
				oval.setGraphicColor(tempGraphicColor);
				oval.setStartTime(tempStartTime);
				this.graphic = oval;
			}
			((Oval)graphic).setSolid(attributes.getValue("solid"));
			((Oval)graphic).setXEnd(attributes.getValue("xend"));
			((Oval)graphic).setYEnd(attributes.getValue("yend"));
			((Oval)graphic).setXStart(attributes.getValue("xstart"));
			((Oval)graphic).setYStart(attributes.getValue("ystart"));
			((Oval)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
			((Oval)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
		case "rectangle":
			if(graphic instanceof Graphic) {
				Rectangle rectangle = (new CommonShapes(getDefaults()).new Rectangle(getDefaults()));
				rectangle.setDuration(tempDuration);
				rectangle.setGraphicColor(tempGraphicColor);
				rectangle.setStartTime(tempStartTime);
				this.graphic = rectangle;
			}
			((Rectangle)graphic).setSolid(attributes.getValue("solid"));
			((Rectangle)graphic).setXEnd(attributes.getValue("xend"));
			((Rectangle)graphic).setYEnd(attributes.getValue("yend"));
			((Rectangle)graphic).setXStart(attributes.getValue("xstart"));
			((Rectangle)graphic).setYStart(attributes.getValue("ystart"));
			((Rectangle)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
			((Rectangle)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
			
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
