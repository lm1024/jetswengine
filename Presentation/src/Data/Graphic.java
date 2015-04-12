package Data;

import java.util.HashMap;
import java.util.List;

import utils.Utils;
import Data.CommonShapes.*;
import Data.RadialShapes.*;
import Data.OtherShapes.*;

public class Graphic extends SlideItem {

	private String graphicColor;
	private String shadow;
	private List<String> shadingList;
	private List<Float> stopValuesList;
	private String shadingType;

	public Graphic(Defaults defaults) {
		super(defaults);
		this.graphicColor = defaults.getGraphicColor();
	}
	
	@Override
	public void printItem() {
		super.printItem();
		System.out.println("Graphic Color: " + graphicColor);
		if(shadow != null) {
			System.out.println("Shadow Weight: " + shadow);
		}
		if(shadingType != null ) {
			System.out.println("Shading Type: " + shadingType);
		}
		if(shadingList != null) {
			int i = 1;
			for(String string : shadingList) {
				System.out.println("Shading Color " + i + ": " + string);
				i++;
			}
		}
		if(stopValuesList != null) {
			int i = 1;
			for(Float f : stopValuesList) {
				System.out.println("Stop Value " + i + ": " + f.floatValue());
				i++;
			}
		}
		
	}

	public static Graphic makeGraphic(HashMap<String, String> hashMap, Defaults defaults) {
		Graphic g;
		switch (hashMap.get("type")) {
		case "oval":
			g = new CommonShapes(defaults).new Oval(defaults);
			((Oval) g).setSolid(hashMap.get("solid"));
			((Oval) g).setXEnd(hashMap.get("xend"));
			((Oval) g).setYEnd(hashMap.get("yend"));
			break;
		case "rectangle":
			g = new CommonShapes(defaults).new Rectangle(defaults);
			((Rectangle) g).setSolid(hashMap.get("solid"));
			((Rectangle) g).setXEnd(hashMap.get("xend"));
			((Rectangle) g).setYEnd(hashMap.get("yend"));
			break;
		case "line":
			g = new OtherShapes(defaults).new Line(defaults);
			((Line) g).setXEnd(hashMap.get("xend"));
			((Line) g).setYEnd(hashMap.get("yend"));
			break;
		case "circle":
			g = new RadialShapes(defaults).new Circle(defaults);
			((Circle) g).setSolid(hashMap.get("solid"));
			((Circle) g).setSize(hashMap.get("radius"));
			((Circle) g).setSize(hashMap.get("size"));
			break;
		case "square":
			g = new RadialShapes(defaults).new Square(defaults);
			((Square) g).setSolid(hashMap.get("solid"));
			((Square) g).setSize(hashMap.get("size"));
			break;
		case "arrow":
			g = new OtherShapes(defaults).new Arrow(defaults);
			((Arrow) g).setSolid(hashMap.get("solid"));
			((Arrow) g).setXEnd(hashMap.get("xend"));
			((Arrow) g).setYEnd(hashMap.get("yend"));
			break;
		case "equitriangle":
			g = new RadialShapes(defaults).new EquilateralTriangle(defaults);
			((EquilateralTriangle) g).setSolid(hashMap.get("solid"));
			((EquilateralTriangle) g).setSize(hashMap.get("size"));
			break;
		case "triangle":
			g = new OtherShapes(defaults).new Triangle(defaults);
			((Triangle) g).setSolid(hashMap.get("solid"));
			break;
		case "regpolygon":
			g = new RadialShapes(defaults).new RegularPolygon(defaults);
			((RegularPolygon) g).setSolid(hashMap.get("solid"));
			((RegularPolygon) g).setSize(hashMap.get("size"));
			((RegularPolygon) g).setNumberOfSides(hashMap.get("numberofsides"));
			break;
		case "polygon":
			g = new OtherShapes(defaults).new Triangle(defaults);
			((Triangle) g).setSolid(hashMap.get("solid"));
			break;
		case "star":
			g = new RadialShapes(defaults).new Star(defaults);
			((Star) g).setSolid(hashMap.get("solid"));
			((Star) g).setSize(hashMap.get("size"));
			((Star) g).setNumberOfPoints(hashMap.get("numberofpoints"));
			break;
		case "chord":
			g = new OtherShapes(defaults).new Chord(defaults);
			((Chord) g).setSolid(hashMap.get("solid"));
			((Chord) g).setWidth(hashMap.get("width"));
			((Chord) g).setHeight(hashMap.get("height"));
			((Chord) g).setLength(hashMap.get("length"));
			((Chord) g).setArcAngle(hashMap.get("arcangle"));
			break;
		case "arc":
			g = new OtherShapes(defaults).new Arc(defaults);
			((Arc) g).setSolid(hashMap.get("solid"));
			((Arc) g).setWidth(hashMap.get("width"));
			((Arc) g).setHeight(hashMap.get("height"));
			((Arc) g).setLength(hashMap.get("length"));
			((Arc) g).setArcAngle(hashMap.get("arcangle"));
			break;
		default:
			System.out.println("Unknown graphic: " + hashMap.get("type") + ". Returning Null");
			return null;

		}
		g.setDuration(hashMap.get("duration"));
		g.setGraphicColor(hashMap.get("graphiccolor"));
		g.setStartTime(hashMap.get("starttime"));
		g.setXStart(hashMap.get("xstart"));
		g.setYStart(hashMap.get("ystart"));
		return g;
	}

	/**
	 * @return the graphicColor
	 */
	public String getGraphicColor() {
		return graphicColor;
	}

	/**
	 * @param graphicColor
	 *            the graphicColor to set
	 */
	public void setGraphicColor(String string) {
		if (Utils.validARGB(string)) {
			this.graphicColor = string;
		}
	}

	public String getShadow() {
		return shadow;
	}

	public void setShadow(String string) {
		if(Utils.validShadow(string)) {
			this.shadow = string;
		}
	}

	/**
	 * @return the shadingList
	 */
	public List<String> getShadingList() {
		return shadingList;
	}

	/**
	 * @param shadingList the shadingList to set
	 */
	public void setShadingList(List<String> shadingList) {
		this.shadingList = shadingList;
	}

	/**
	 * @return the shadingType
	 */
	public String getShadingType() {
		return shadingType;
	}

	/**
	 * @param shadingType the shadingType to set
	 */
	public void setShadingType(String string) {
		if(Utils.validShadingType(string)) {
			this.shadingType = string;
		}
	}

	public List<Float> getStopValuesList() {
		return stopValuesList;
	}

	public void setStopValuesList(List<Float> stopValuesList) {
		this.stopValuesList = stopValuesList;
	}

}