package Data;

import java.util.HashMap;

import utils.Utils;
import Data.CommonShapes.*;

public class Graphic extends SlideItem {

	private String graphicColor;

	public Graphic(Defaults defaults) {
		super(defaults);
		this.graphicColor = defaults.getGraphicColor();
	}

	public static Graphic makeGraphic(HashMap<String, String> hashMap, Defaults defaults) {
		Graphic g;
		CommonShapes c = new CommonShapes(defaults);
		switch (hashMap.get("type")) {
		case "oval":
			g = c.new Oval(defaults);
			((Oval) g).setSolid(hashMap.get("solid"));
			((Oval) g).setXEnd(hashMap.get("xend"));
			((Oval) g).setYEnd(hashMap.get("yend"));
			break;
		case "rectangle":
			g = c.new Rectangle(defaults);
			((Rectangle) g).setSolid(hashMap.get("solid"));
			((Rectangle) g).setXEnd(hashMap.get("xend"));
			((Rectangle) g).setYEnd(hashMap.get("yend"));
			break;
		case "itriangle":
			g = c.new IsoscelesTriangle(defaults);
			((IsoscelesTriangle) g).setSolid(hashMap.get("solid"));
			((IsoscelesTriangle) g).setXEnd(hashMap.get("xend"));
			((IsoscelesTriangle) g).setYEnd(hashMap.get("yend"));
			break;
		case "line":
			g = c.new Line(defaults);
			((Line) g).setXEnd(hashMap.get("xend"));
			((Line) g).setYEnd(hashMap.get("yend"));
			break;
		default:
			System.out.println("Unknown graphic. Returning Null");
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

	@Override
	public void printItem() {
		super.printItem();
		System.out.println("Graphic Color: " + graphicColor);
	}

}