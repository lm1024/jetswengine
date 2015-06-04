/** (c) Copyright by WaveMedia. */
package data;

import java.util.List;

import utils.Utils;

/**
 * Graphic class for containing a single SmartSlides Graphic data.
 * 
 * @author dk666
 * @version 2.3 02/06/15
 */
public class Graphic extends SlideItem {

	/* Properties of all graphics */
	private String graphicColor;
	private String shadow = "None";
	private List<String> shadingList;
	private List<Float> stopValuesList;
	private String shadingType = "None";

	/**
	 * Constructs a new Graphic object with the specified defaults.
	 * 
	 * @param defaults
	 */
	public Graphic(Defaults defaults) {
		super(defaults);
		this.graphicColor = defaults.getGraphicColor();
	}
	
	/**
	 * For debugging. Outputs the properties of this graphic.
	 */
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

	/**
	 * Returns the weight of the shadow of this graphic.
	 * @return shadow the weight of the shadow.
	 */
	public String getShadow() {
		return shadow;
	}

	
	/**
	 * Sets the weight of the shadow of the this graphic.
	 * @param string the weight of the shadow.
	 */
	public void setShadow(String string) {
		if(Utils.validShadow(string)) {
			this.shadow = string;
		}
	}

	/**
	 * Returns the list of shading colors of this graphic.
	 * @return shadingList the list of shading colors.
	 */
	public List<String> getShadingList() {
		return shadingList;
	}

	/**
	 * Sets the list of shading colors of this shape.
	 * @param shadingList the shadingList to set.
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

	
	/**
	 * Returns the list of stop values of the shading colors of this graphic.
	 * @return stopValuesList the list of stop values.
	 */
	public List<Float> getStopValuesList() {
		return stopValuesList;
	}

	/**
	 * Sets the list of stop values of the shading colors of this shape.
	 * @param stopValuesList the stopValuesList to set.
	 */
	public void setStopValuesList(List<Float> stopValuesList) {
		this.stopValuesList = stopValuesList;
	}

}