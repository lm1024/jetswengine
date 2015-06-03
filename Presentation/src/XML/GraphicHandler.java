/** (c) Copyright by WaveMedia. */
package XML;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

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
 * Graphic Handler class for parsing graphic Tags from XML Slideshows
 * 
 * @author dk666
 * @version 3.4 02/06/15
 */
public class GraphicHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
	private Graphic graphic;
	private List<Float> xPoints;
	private List<Float> yPoints;
	private String tempDuration, tempStartTime, tempGraphicColor, tempXStart,
			tempYStart;

	/* Creates a new GraphicHandler */
	public GraphicHandler(XMLReader reader, SlideHandler parent, Slide slide) {
		this.parentHandler = parent;
		this.slide = slide;
		this.reader = reader;
		this.graphic = new Graphic(getDefaults());
	}

	/* Returns slideshow defaults */
	protected Defaults getDefaults() {
		return parentHandler.getDefaults();
	}

	/* Updates object type of graphic for PWS Support */
	private void updateGraphicType(String graphicType) {
		switch (graphicType) {
		case "oval":
			this.graphic = (new CommonShapes(getDefaults())).new Oval(
					getDefaults());
			break;
		case "rectangle":
			this.graphic = (new CommonShapes(getDefaults())).new Rectangle(
					getDefaults());
			break;
		case "line":
			this.graphic = (new OtherShapes(getDefaults())).new Line(
					getDefaults());
			break;
		case "circle":
			this.graphic = (new RadialShapes(getDefaults())).new Circle(
					getDefaults());
			break;
		case "square":
			this.graphic = (new RadialShapes(getDefaults())).new Square(
					getDefaults());
			break;
		case "arrow":
			this.graphic = (new OtherShapes(getDefaults())).new Arrow(
					getDefaults());
			break;
		case "equitriangle":
			this.graphic = (new RadialShapes(getDefaults())).new EquilateralTriangle(
					getDefaults());
			break;
		case "triangle":
			this.graphic = (new OtherShapes(getDefaults())).new Triangle(
					getDefaults());
			break;
		case "regpolygon":
			this.graphic = (new RadialShapes(getDefaults())).new RegularPolygon(
					getDefaults());
			break;
		case "polygon":
			this.graphic = (new OtherShapes(getDefaults())).new Polygon(
					getDefaults());
			break;
		case "star":
			this.graphic = (new RadialShapes(getDefaults())).new Star(
					getDefaults());
			break;
		case "chord":
			this.graphic = (new OtherShapes(getDefaults())).new Chord(
					getDefaults());
			break;
		case "arc":
			this.graphic = (new OtherShapes(getDefaults())).new Arc(
					getDefaults());
			break;
		default:
			break;
		}
	}

	/*
	 * Called when the XML Parser encounters a start tag for an element. Assigns
	 * the content of each tag to its respective variable in the data structure.
	 * Recalculates ratios after all variables have been assigned.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		/* sort out element name if (no) namespace in use */
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		switch (elementName) {
		/* Sets attributes for graphics following the PWS standard */
		case "graphic":
			if (attributes.getValue("type") != null) {
				updateGraphicType(attributes.getValue("type"));
				graphic.setDuration(attributes.getValue("duration"));
				graphic.setGraphicColor(attributes.getValue("graphiccolor"));
				graphic.setStartTime(attributes.getValue("starttime"));
				graphic.setXStart(attributes.getValue("xstart"));
				graphic.setYStart(attributes.getValue("ystart"));
				startElement(uri, attributes.getValue("type"),
						attributes.getValue("type"), attributes);
			} else {
				this.tempXStart = attributes.getValue("xstart");
				this.tempYStart = attributes.getValue("ystart");
				this.tempDuration = attributes.getValue("duration");
				this.tempGraphicColor = attributes.getValue("graphiccolor");
				this.tempStartTime = attributes.getValue("starttime");
			}
			break;
		/* Sets the shadow for the current graphic */
		case "shadow":
			graphic.setShadow(attributes.getValue("weight"));
			break;

		/* Hands control to the shading handler */
		case "cyclicshading":
			/* Intentional fall through */
		case "horizontalshading":
			/* Intentional fall through */
		case "verticalshading":
			reader.setContentHandler(new ShadingHandler(reader, this, graphic));
			reader.getContentHandler().startElement(uri, localName, qName,
					attributes);
			break;
		/* Sets the oval attributes for the current graphic */
		case "oval":

			if (attributes.getValue("type") == null) {
				Oval oval = (new CommonShapes(getDefaults()).new Oval(
						getDefaults()));
				oval.setXStart(tempXStart);
				oval.setYStart(tempYStart);
				oval.setDuration(tempDuration);
				oval.setGraphicColor(tempGraphicColor);
				oval.setStartTime(tempStartTime);
				this.graphic = oval;
			}
			((Oval) graphic).setSolid(attributes.getValue("solid"));
			((Oval) graphic).setXEnd(attributes.getValue("xend"));
			((Oval) graphic).setYEnd(attributes.getValue("yend"));
			((Oval) graphic).setXStart(attributes.getValue("xstart"));
			((Oval) graphic).setYStart(attributes.getValue("ystart"));
			((Oval) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((Oval) graphic).setOutlineColor(attributes
					.getValue("outlinecolor"));
			((Oval) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((Oval) graphic).setRotation(attributes.getValue("rotation"));

			break;
		/* Sets the rectangle for the current graphic */
		case "rectangle":
			if (attributes.getValue("type") == null) {
				Rectangle rectangle = (new CommonShapes(getDefaults()).new Rectangle(
						getDefaults()));
				rectangle.setXStart(tempXStart);
				rectangle.setYStart(tempYStart);
				rectangle.setDuration(tempDuration);
				rectangle.setGraphicColor(tempGraphicColor);
				rectangle.setStartTime(tempStartTime);
				this.graphic = rectangle;
			}
			((Rectangle) graphic).setSolid(attributes.getValue("solid"));
			((Rectangle) graphic).setXEnd(attributes.getValue("xend"));
			((Rectangle) graphic).setYEnd(attributes.getValue("yend"));
			((Rectangle) graphic).setXStart(attributes.getValue("xstart"));
			((Rectangle) graphic).setYStart(attributes.getValue("ystart"));
			((Rectangle) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((Rectangle) graphic).setOutlineColor(attributes
					.getValue("outlinecolor"));
			((Rectangle) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((Rectangle) graphic)
					.setArcHeight(attributes.getValue("archeight"));
			((Rectangle) graphic).setArcWidth(attributes.getValue("arcwidth"));
			((Rectangle) graphic).setRotation(attributes.getValue("rotation"));

			break;
		/* Sets the line attributes for the current graphic */
		case "line":
			if (attributes.getValue("type") == null) {
				Line line = (new OtherShapes(getDefaults()).new Line(
						getDefaults()));
				line.setXStart(tempXStart);
				line.setYStart(tempYStart);
				line.setDuration(tempDuration);
				line.setGraphicColor(tempGraphicColor);
				line.setStartTime(tempStartTime);
				this.graphic = line;
			}
			((Line) graphic).setSolid(attributes.getValue("solid"));
			((Line) graphic).setXEnd(attributes.getValue("xend"));
			((Line) graphic).setYEnd(attributes.getValue("yend"));
			((Line) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((Line) graphic).setXStart(attributes.getValue("xstart"));
			((Line) graphic).setYStart(attributes.getValue("ystart"));
			((Line) graphic).setThickness(attributes.getValue("thickness"));
			break;
		/* Sets the circle attributes for the current graphic */
		case "circle":
			if (attributes.getValue("type") == null) {
				Circle circle = (new RadialShapes(getDefaults()).new Circle(
						getDefaults()));
				circle.setXStart(tempXStart);
				circle.setYStart(tempYStart);
				circle.setDuration(tempDuration);
				circle.setGraphicColor(tempGraphicColor);
				circle.setStartTime(tempStartTime);
				this.graphic = circle;
			}
			((Circle) graphic).setSolid(attributes.getValue("solid"));
			((Circle) graphic).setXStart(attributes.getValue("xstart"));
			((Circle) graphic).setYStart(attributes.getValue("ystart"));
			((Circle) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((Circle) graphic).setOutlineColor(attributes
					.getValue("outlinecolor"));
			((Circle) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((Circle) graphic).setSize(attributes.getValue("size"));
			((Circle) graphic).setRotation(attributes.getValue("rotation"));

			break;
		/* Sets the square attributes for the current graphic */
		case "square":
			if (attributes.getValue("type") == null) {
				Square square = (new RadialShapes(getDefaults()).new Square(
						getDefaults()));
				square.setXStart(tempXStart);
				square.setYStart(tempYStart);
				square.setDuration(tempDuration);
				square.setGraphicColor(tempGraphicColor);
				square.setStartTime(tempStartTime);
				this.graphic = square;
			}
			((Square) graphic).setSolid(attributes.getValue("solid"));
			((Square) graphic).setXStart(attributes.getValue("xstart"));
			((Square) graphic).setYStart(attributes.getValue("ystart"));
			((Square) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((Square) graphic).setOutlineColor(attributes
					.getValue("outlinecolor"));
			((Square) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((Square) graphic).setSize(attributes.getValue("size"));
			((Square) graphic).setRotation(attributes.getValue("rotation"));
			((Square) graphic).setArcHeight(attributes.getValue("archeight"));
			((Square) graphic).setArcWidth(attributes.getValue("arcwidth"));

			break;
		/* Sets the arrow attributes for the current graphic */
		case "arrow":
			if (attributes.getValue("type") == null) {
				Arrow arrow = (new OtherShapes(getDefaults()).new Arrow(
						getDefaults()));
				arrow.setXStart(tempXStart);
				arrow.setYStart(tempYStart);
				arrow.setDuration(tempDuration);
				arrow.setGraphicColor(tempGraphicColor);
				arrow.setStartTime(tempStartTime);
				this.graphic = arrow;
			}
			((Arrow) graphic).setSolid(attributes.getValue("solid"));
			((Arrow) graphic).setXEnd(attributes.getValue("xend"));
			((Arrow) graphic).setYEnd(attributes.getValue("yend"));
			((Arrow) graphic).setXStart(attributes.getValue("xstart"));
			((Arrow) graphic).setYStart(attributes.getValue("ystart"));
			((Arrow) graphic).setThickness(attributes.getValue("thickness"));
			((Arrow) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			break;
		/* Sets the Equilateral Triangle attributes for the current graphic */
		case "equitriangle":
			if (attributes.getValue("type") == null) {
				EquilateralTriangle equilateralTriangle = (new RadialShapes(
						getDefaults()).new EquilateralTriangle(getDefaults()));
				equilateralTriangle.setXStart(tempXStart);
				equilateralTriangle.setYStart(tempYStart);
				equilateralTriangle.setDuration(tempDuration);
				equilateralTriangle.setGraphicColor(tempGraphicColor);
				equilateralTriangle.setStartTime(tempStartTime);
				this.graphic = equilateralTriangle;
			}
			((EquilateralTriangle) graphic).setSolid(attributes
					.getValue("solid"));
			((EquilateralTriangle) graphic).setXStart(attributes
					.getValue("xstart"));
			((EquilateralTriangle) graphic).setYStart(attributes
					.getValue("ystart"));
			((EquilateralTriangle) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((EquilateralTriangle) graphic).setOutlineColor(attributes
					.getValue("outlinecolor"));
			((EquilateralTriangle) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((EquilateralTriangle) graphic)
					.setSize(attributes.getValue("size"));
			((EquilateralTriangle) graphic).setRotation(attributes
					.getValue("rotation"));
			break;
		/* Sets the triangle attributes for the current graphic */
		case "triangle":
			xPoints = new ArrayList<Float>();
			yPoints = new ArrayList<Float>();
			if (attributes.getValue("type") == null) {
				Triangle triangle = (new OtherShapes(getDefaults()).new Triangle(
						getDefaults()));
				triangle.setXStart(tempXStart);
				triangle.setYStart(tempYStart);
				triangle.setDuration(tempDuration);
				triangle.setGraphicColor(tempGraphicColor);
				triangle.setStartTime(tempStartTime);
				this.graphic = triangle;
			}
			((Triangle) graphic).setSolid(attributes.getValue("solid"));
			((Triangle) graphic).setXStart(attributes.getValue("xstart"));
			((Triangle) graphic).setYStart(attributes.getValue("ystart"));
			((Triangle) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((Triangle) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((Triangle) graphic).setOutlineColor(attributes
					.getValue("outlinecolor"));
			((Triangle) graphic).setRotation(attributes.getValue("rotation"));
			break;
		/* Sets the regular polygon attributes for the current graphic */
		case "regpolygon":
			if (attributes.getValue("type") == null) {
				RegularPolygon regularPolygon = (new RadialShapes(getDefaults()).new RegularPolygon(
						getDefaults()));
				regularPolygon.setXStart(tempXStart);
				regularPolygon.setYStart(tempYStart);
				regularPolygon.setDuration(tempDuration);
				regularPolygon.setGraphicColor(tempGraphicColor);
				regularPolygon.setStartTime(tempStartTime);
				this.graphic = regularPolygon;
			}
			((RegularPolygon) graphic).setSolid(attributes.getValue("solid"));
			((RegularPolygon) graphic).setXStart(attributes.getValue("xstart"));
			((RegularPolygon) graphic).setYStart(attributes.getValue("ystart"));
			((RegularPolygon) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((RegularPolygon) graphic).setOutlineColor(attributes
					.getValue("outlinecolor"));
			((RegularPolygon) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((RegularPolygon) graphic).setSize(attributes.getValue("size"));
			((RegularPolygon) graphic).setRotation(attributes
					.getValue("rotation"));
			((RegularPolygon) graphic).setNumberOfSides(attributes
					.getValue("numberofsides"));
			break;
		/* Sets the irregular polygon attributes for the current graphic */
		case "polygon":
			xPoints = new ArrayList<Float>();
			yPoints = new ArrayList<Float>();
			if (attributes.getValue("type") == null) {
				Polygon polygon = (new OtherShapes(getDefaults()).new Polygon(
						getDefaults()));
				polygon.setXStart(tempXStart);
				polygon.setYStart(tempYStart);
				polygon.setDuration(tempDuration);
				polygon.setGraphicColor(tempGraphicColor);
				polygon.setStartTime(tempStartTime);
				this.graphic = polygon;
			}
			((Polygon) graphic).setSolid(attributes.getValue("solid"));
			((Polygon) graphic).setXStart(attributes.getValue("xstart"));
			((Polygon) graphic).setYStart(attributes.getValue("ystart"));
			((Polygon) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((Polygon) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((Polygon) graphic).setOutlineColor(attributes
					.getValue("outlinecolor"));
			((Polygon) graphic).setRotation(attributes.getValue("rotation"));
			break;
		/* Sets the star attributes for the current graphic */
		case "star":
			if (attributes.getValue("type") == null) {
				Star star = (new RadialShapes(getDefaults()).new Star(
						getDefaults()));
				star.setXStart(tempXStart);
				star.setYStart(tempYStart);
				star.setDuration(tempDuration);
				star.setGraphicColor(tempGraphicColor);
				star.setStartTime(tempStartTime);
				this.graphic = star;
			}
			((Star) graphic).setSolid(attributes.getValue("solid"));
			((Star) graphic).setXStart(attributes.getValue("xstart"));
			((Star) graphic).setYStart(attributes.getValue("ystart"));
			((Star) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((Star) graphic).setOutlineColor(attributes
					.getValue("outlinecolor"));
			((Star) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((Star) graphic).setSize(attributes.getValue("size"));
			((Star) graphic).setRotation(attributes.getValue("rotation"));
			((Star) graphic).setNumberOfPoints(attributes
					.getValue("numberofpoints"));
			break;
		/* Sets the chord attributes for the current graphic */
		case "chord":
			if (attributes.getValue("type") == null) {
				Chord chord = (new OtherShapes(getDefaults()).new Chord(
						getDefaults()));
				chord.setXStart(tempXStart);
				chord.setYStart(tempYStart);
				chord.setDuration(tempDuration);
				chord.setGraphicColor(tempGraphicColor);
				chord.setStartTime(tempStartTime);
				this.graphic = chord;
			}
			((Chord) graphic).setSolid(attributes.getValue("solid"));
			((Chord) graphic).setXStart(attributes.getValue("xstart"));
			((Chord) graphic).setYStart(attributes.getValue("ystart"));
			((Chord) graphic).setGraphicColor(attributes
					.getValue("graphiccolor"));
			((Chord) graphic).setOutlineColor(attributes
					.getValue("outlinecolor"));
			((Chord) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((Chord) graphic).setRotation(attributes.getValue("rotation"));
			((Chord) graphic).setWidth(attributes.getValue("width"));
			((Chord) graphic).setHeight(attributes.getValue("height"));
			((Chord) graphic).setArcAngle(attributes.getValue("arcangle"));
			((Chord) graphic).setLength(attributes.getValue("length"));
			break;
		/* Sets the arc attributes for the current graphic */
		case "arc":
			if (attributes.getValue("type") == null) {
				Arc arc = (new OtherShapes(getDefaults()).new Arc(getDefaults()));
				arc.setXStart(tempXStart);
				arc.setYStart(tempYStart);
				arc.setDuration(tempDuration);
				arc.setGraphicColor(tempGraphicColor);
				arc.setStartTime(tempStartTime);
				this.graphic = arc;
			}
			((Arc) graphic).setSolid(attributes.getValue("solid"));
			((Arc) graphic).setXStart(attributes.getValue("xstart"));
			((Arc) graphic).setYStart(attributes.getValue("ystart"));
			((Arc) graphic)
					.setGraphicColor(attributes.getValue("graphiccolor"));
			((Arc) graphic)
					.setOutlineColor(attributes.getValue("outlinecolor"));
			((Arc) graphic).setOutlineThickness(attributes
					.getValue("outlinethickness"));
			((Arc) graphic).setRotation(attributes.getValue("rotation"));
			((Arc) graphic).setWidth(attributes.getValue("width"));
			((Arc) graphic).setHeight(attributes.getValue("height"));
			((Arc) graphic).setArcAngle(attributes.getValue("arcangle"));
			((Arc) graphic).setLength(attributes.getValue("length"));
			break;
		/* Sets the co-ordinates for the current graphic */
		case "point":
			xPoints.add(new Float(attributes.getValue("x")));
			yPoints.add(new Float(attributes.getValue("y")));
			break;
		/* Sets the current graphic to null to prevent corrupt shapes */
		default:
			graphic = null;
			break;
		}
	}

	/*
	 * Called when the XML Parser encounters a end tag for an element. Adds
	 * co-ordinates to shape if required. If end of graphic is detected, adds
	 * the shape to the Data Structure. Reassigns parent handler to the reader.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		/* sort out element name if (no) namespace in use */
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}

		if (elementName.equals("graphic")) {
			if (graphic instanceof Triangle) {
				((Triangle) graphic).setxPoints(xPoints);
				((Triangle) graphic).setyPoints(yPoints);
			} else if (graphic instanceof Polygon) {
				((Polygon) graphic).setxPoints(xPoints);
				((Polygon) graphic).setyPoints(yPoints);
			}
			if (graphic != null) {
				slide.add(graphic);
			}
			reader.setContentHandler(parentHandler);
		}
	}

}
