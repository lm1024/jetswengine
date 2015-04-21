/**
 * 
 */
package XML;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import utils.Utils;
<<<<<<< HEAD
import Data.CommonShapes;
import Data.Defaults;
=======
>>>>>>> refs/heads/master
import Data.Graphic;
<<<<<<< HEAD
import Data.OtherShapes;
import Data.RadialShapes;
import Data.CommonShapes.*;
import Data.OtherShapes.*;
import Data.RadialShapes.*;
=======
>>>>>>> refs/heads/master
import Data.Slide;

/**
 * @author David
 *
 */
public class GraphicHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
<<<<<<< HEAD
	private Graphic graphic;
	private HashMap<String, String> currentObject = new HashMap<String, String>();
	private List<Float> xPoints;
	private List<Float> yPoints;
	private String tempDuration, tempStartTime, tempGraphicColor;
=======
	private HashMap<String,String> currentObject = new HashMap<String,String>();
>>>>>>> refs/heads/master

	/**
	 * 
	 */
	public GraphicHandler(XMLReader reader, SlideHandler parent,
			Slide slide) {
		this.parentHandler = parent;
		this.slide = slide;
		this.reader = reader;
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
<<<<<<< HEAD
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
				((Oval)graphic).setRotation(attributes.getValue("rotation"));
				
				break;
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
				((Rectangle)graphic).setArcHeight(attributes.getValue("archeight"));
				((Rectangle)graphic).setArcWidth(attributes.getValue("arcwidth"));
				((Rectangle)graphic).setRotation(attributes.getValue("rotation"));
				
				break;
			case "line":
				if(graphic instanceof Graphic) {
					Line line = (new OtherShapes(getDefaults()).new Line(getDefaults()));
					line.setDuration(tempDuration);
					line.setGraphicColor(tempGraphicColor);
					line.setStartTime(tempStartTime);
					this.graphic = line;
				}
				((Line)graphic).setSolid(attributes.getValue("solid"));
				((Line)graphic).setXEnd(attributes.getValue("xend"));
				((Line)graphic).setYEnd(attributes.getValue("yend"));
				((Line)graphic).setXStart(attributes.getValue("xstart"));
				((Line)graphic).setYStart(attributes.getValue("ystart"));
				((Line)graphic).setThickness(attributes.getValue("thickness"));
				break;
			case "circle":
				if(graphic instanceof Graphic) {
					Circle circle = (new RadialShapes(getDefaults()).new Circle(getDefaults()));
					circle.setDuration(tempDuration);
					circle.setGraphicColor(tempGraphicColor);
					circle.setStartTime(tempStartTime);
					this.graphic = circle;
				}
				((Circle)graphic).setSolid(attributes.getValue("solid"));
				((Circle)graphic).setXStart(attributes.getValue("xstart"));
				((Circle)graphic).setYStart(attributes.getValue("ystart"));
				((Circle)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
				((Circle)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
				((Circle)graphic).setSize(attributes.getValue("size"));
				((Circle)graphic).setRotation(attributes.getValue("rotation"));
				
				break;
			case "square":
				if(graphic instanceof Graphic) {
					Square square = (new RadialShapes(getDefaults()).new Square(getDefaults()));
					square.setDuration(tempDuration);
					square.setGraphicColor(tempGraphicColor);
					square.setStartTime(tempStartTime);
					this.graphic = square;
				}
				((Square)graphic).setSolid(attributes.getValue("solid"));
				((Square)graphic).setXStart(attributes.getValue("xstart"));
				((Square)graphic).setYStart(attributes.getValue("ystart"));
				((Square)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
				((Square)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
				((Square)graphic).setSize(attributes.getValue("size"));
				((Square)graphic).setRotation(attributes.getValue("rotation"));
				((Square)graphic).setArcHeight(attributes.getValue("archeight"));
				((Square)graphic).setArcWidth(attributes.getValue("arcwidth"));
				
				break;
			case "arrow":
				if(graphic instanceof Graphic) {
					Arrow arrow = (new OtherShapes(getDefaults()).new Arrow(getDefaults()));
					arrow.setDuration(tempDuration);
					arrow.setGraphicColor(tempGraphicColor);
					arrow.setStartTime(tempStartTime);
					this.graphic = arrow;
				}
				((Arrow)graphic).setSolid(attributes.getValue("solid"));
				((Arrow)graphic).setXEnd(attributes.getValue("xend"));
				((Arrow)graphic).setYEnd(attributes.getValue("yend"));
				((Arrow)graphic).setXStart(attributes.getValue("xstart"));
				((Arrow)graphic).setYStart(attributes.getValue("ystart"));
				((Arrow)graphic).setThickness(attributes.getValue("thickness"));
				break;
			case "equitriangle":
				if(graphic instanceof Graphic) {
					EquilateralTriangle equilateralTriangle = (new RadialShapes(getDefaults()).new EquilateralTriangle(getDefaults()));
					equilateralTriangle.setDuration(tempDuration);
					equilateralTriangle.setGraphicColor(tempGraphicColor);
					equilateralTriangle.setStartTime(tempStartTime);
					this.graphic = equilateralTriangle;
				}
				((EquilateralTriangle)graphic).setSolid(attributes.getValue("solid"));
				((EquilateralTriangle)graphic).setXStart(attributes.getValue("xstart"));
				((EquilateralTriangle)graphic).setYStart(attributes.getValue("ystart"));
				((EquilateralTriangle)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
				((EquilateralTriangle)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
				((EquilateralTriangle)graphic).setSize(attributes.getValue("size"));
				((EquilateralTriangle)graphic).setRotation(attributes.getValue("rotation"));
				break;
			case "triangle":
				xPoints = new ArrayList<Float>();
				yPoints = new ArrayList<Float>();
				if(graphic instanceof Graphic) {
					Triangle triangle = (new OtherShapes(getDefaults()).new Triangle(getDefaults()));
					triangle.setDuration(tempDuration);
					triangle.setGraphicColor(tempGraphicColor);
					triangle.setStartTime(tempStartTime);
					this.graphic = triangle;
				}
				((Triangle)graphic).setSolid(attributes.getValue("solid"));
				((Triangle)graphic).setXStart(attributes.getValue("xstart"));
				((Triangle)graphic).setYStart(attributes.getValue("ystart"));
				((Triangle)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
				((Triangle)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
				((Triangle)graphic).setRotation(attributes.getValue("rotation"));
				break;
			case "regpolygon":
				if(graphic instanceof Graphic) {
					RegularPolygon regularPolygon = (new RadialShapes(getDefaults()).new RegularPolygon(getDefaults()));
					regularPolygon.setDuration(tempDuration);
					regularPolygon.setGraphicColor(tempGraphicColor);
					regularPolygon.setStartTime(tempStartTime);
					this.graphic = regularPolygon;
				}
				((RegularPolygon)graphic).setSolid(attributes.getValue("solid"));
				((RegularPolygon)graphic).setXStart(attributes.getValue("xstart"));
				((RegularPolygon)graphic).setYStart(attributes.getValue("ystart"));
				((RegularPolygon)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
				((RegularPolygon)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
				((RegularPolygon)graphic).setSize(attributes.getValue("size"));
				((RegularPolygon)graphic).setRotation(attributes.getValue("rotation"));
				((RegularPolygon)graphic).setNumberOfSides(attributes.getValue("numberofsides"));
				break;
			case "polygon":
				xPoints = new ArrayList<Float>();
				yPoints = new ArrayList<Float>();
				if(graphic instanceof Graphic) {
					Polygon polygon = (new OtherShapes(getDefaults()).new Polygon(getDefaults()));
					polygon.setDuration(tempDuration);
					polygon.setGraphicColor(tempGraphicColor);
					polygon.setStartTime(tempStartTime);
					this.graphic = polygon;
				}
				((Polygon)graphic).setSolid(attributes.getValue("solid"));
				((Polygon)graphic).setXStart(attributes.getValue("xstart"));
				((Polygon)graphic).setYStart(attributes.getValue("ystart"));
				((Polygon)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
				((Polygon)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
				((Polygon)graphic).setRotation(attributes.getValue("rotation"));
				break;
			case "star":
				if(graphic instanceof Graphic) {
					Star star = (new RadialShapes(getDefaults()).new Star(getDefaults()));
					star.setDuration(tempDuration);
					star.setGraphicColor(tempGraphicColor);
					star.setStartTime(tempStartTime);
					this.graphic = star;
				}
				((Star)graphic).setSolid(attributes.getValue("solid"));
				((Star)graphic).setXStart(attributes.getValue("xstart"));
				((Star)graphic).setYStart(attributes.getValue("ystart"));
				((Star)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
				((Star)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
				((Star)graphic).setSize(attributes.getValue("size"));
				((Star)graphic).setRotation(attributes.getValue("rotation"));
				((Star)graphic).setNumberOfPoints(attributes.getValue("numberofpoints"));
				break;
			case "chord":
				if(graphic instanceof Graphic) {
					Chord chord = (new OtherShapes(getDefaults()).new Chord(getDefaults()));
					chord.setDuration(tempDuration);
					chord.setGraphicColor(tempGraphicColor);
					chord.setStartTime(tempStartTime);
					this.graphic = chord;
				}
				((Chord)graphic).setSolid(attributes.getValue("solid"));
				((Chord)graphic).setXStart(attributes.getValue("xstart"));
				((Chord)graphic).setYStart(attributes.getValue("ystart"));
				((Chord)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
				((Chord)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
				((Chord)graphic).setRotation(attributes.getValue("rotation"));
				((Chord)graphic).setWidth(attributes.getValue("width"));
				((Chord)graphic).setHeight(attributes.getValue("height"));
				((Chord)graphic).setArcAngle(attributes.getValue("arcangle"));
				((Chord)graphic).setLength(attributes.getValue("length"));
				break;
			case "arc":
				if(graphic instanceof Graphic) {
					Arc arc = (new OtherShapes(getDefaults()).new Arc(getDefaults()));
					arc.setDuration(tempDuration);
					arc.setGraphicColor(tempGraphicColor);
					arc.setStartTime(tempStartTime);
					this.graphic = arc;
				}
				((Arc)graphic).setSolid(attributes.getValue("solid"));
				((Arc)graphic).setXStart(attributes.getValue("xstart"));
				((Arc)graphic).setYStart(attributes.getValue("ystart"));
				((Arc)graphic).setOutlineColor(attributes.getValue("outlinecolor"));
				((Arc)graphic).setOutlineThickness(attributes.getValue("outlinethickness"));
				((Arc)graphic).setRotation(attributes.getValue("rotation"));
				((Arc)graphic).setWidth(attributes.getValue("width"));
				((Arc)graphic).setHeight(attributes.getValue("height"));
				((Arc)graphic).setArcAngle(attributes.getValue("arcangle"));
				((Arc)graphic).setLength(attributes.getValue("length"));
				break;
				
			case "point":
				System.out.println(attributes.getValue("x"));
				xPoints.add(new Float(attributes.getValue("x")));
				yPoints.add(new Float(attributes.getValue("y")));
				break;
			default:
				System.err.println("Unknown start tag encountered: " + elementName);
				break;
		}

=======
		currentObject.put("type", elementName.equals("cyclicshading") ? currentObject.get("type") : elementName);
		Utils.parse(currentObject, attributes, "type", "xstart", "ystart",
				"yend", "xend", "solid", "graphiccolor", "duration",
				"sourcefile", "starttime");
>>>>>>> refs/heads/master
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("graphic")) {
<<<<<<< HEAD
			//Graphic temp = Graphic.makeGraphic(currentObject, getDefaults());
			//graphic.setShadingList(this.graphic.getShadingList());
			//temp.setShadingType(this.graphic.getShadingType());
			//temp.setShadow(this.graphic.getShadow());
			if(graphic instanceof Triangle) {
				((Triangle)graphic).setxPoints(xPoints);
				((Triangle)graphic).setyPoints(yPoints);
			} else if(graphic instanceof Polygon ) {
				((Polygon)graphic).setxPoints(xPoints);
				((Polygon)graphic).setyPoints(yPoints);
			}
			slide.add(graphic);
=======
			slide.addGraphic(Graphic.makeGraphic(currentObject,parentHandler.getDefaults()));
>>>>>>> refs/heads/master
			reader.setContentHandler(parentHandler);
		}
	}

}
