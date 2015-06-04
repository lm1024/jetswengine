/** (c) Copyright by WaveMedia. */
package Data;

import utils.Utils;

/**
 * CommonShapes class for defining common shapes.
 * 
 * @author dk666
 * @version 3.6 02/11/14
 */
public class CommonShapes extends Graphic {

	/* Common properties of common shapes */
	private float xEnd;
	private float yEnd;
	private boolean solid;
	private String outlineColor;
	private float outlineThickness;
	private float rotation;

	/**
	 * Constructs a new CommonShapes object with the specified defaults.
	 * 
	 * @param defaults
	 */
	public CommonShapes(Defaults defaults) {
		super(defaults);
		this.solid = defaults.getShapeSolidity();
	}

	/**
	 * For debugging. Outputs the properties of this graphic.
	 
	@Override
	public void printItem() {
		super.printItem();
		System.out.println("xEnd: " + xEnd);
		System.out.println("yEnd: " + yEnd);
		System.out.println("Solid: " + solid);
		System.out.println("");
	}
	
	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return this.rotation;
	}

	/**
	 * @param string the rotation to set
	 */
	public void setRotation(String string) {
		try {
			float f = Float.parseFloat(string);
			this.rotation = f;
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * Returns the xEnd relative co-ordinate of this shape.
	 * 
	 * @return xEnd.
	 */
	public float getXEnd() {
		return xEnd;
	}

	/**
	 * Sets the relative xEnd of this shape.
	 * 
	 * @param string
	 *            the xEnd to set.
	 */
	public void setXEnd(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.xEnd = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * Returns the yEnd relative co-ordinate of this shape.
	 * 
	 * @return yEnd.
	 */
	public float getYEnd() {
		return yEnd;
	}

	/**
	 * Sets the relative yEnd of this shape.
	 * 
	 * @param string
	 *            the yEnd to set.
	 */
	public void setYEnd(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.yEnd = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return {@code true} if shape is solid
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * Sets the solidity of the shape.
	 * 
	 * @param string
	 */
	public void setSolid(String string) {
		boolean b = Boolean.parseBoolean(string);
		this.solid = b;
	}

	/**
	 * Defines Rectangle as a Common Shape. Rectangle also have arc widths and
	 * heights.
	 * 
	 * @author dk666
	 */
	public class Rectangle extends CommonShapes {
		
		/* Properties of a rectangle */
		private float arcWidth;
		private float arcHeight;
		
		/**
		 * Constructs a new Rectangle object with the specified defaults.
		 * 
		 * @param defaults
		 */
		public Rectangle(Defaults defaults) {
			super(defaults);
		}
		
		/**
		 * Returns the width of the arc.
		 * 
		 * @return arcWidth
		 */
		public float getArcWidth() {
			return this.arcWidth;
		}

		/**
		 * Sets the width of the arc.
		 * 
		 * @param string
		 *            The arc width to set.
		 */
		public void setArcWidth(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f >= 0) {
					this.arcWidth = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
		
		/**
		 * Returns the height of the arc.
		 * 
		 * @return arcHeight
		 */
		public float getArcHeight() {
			return this.arcHeight;
		}

		/**
		 * Sets the height of the arc.
		 * 
		 * @param string
		 *            The arc height to set.
		 */
		public void setArcHeight(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f >= 0) {
					this.arcHeight = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
	}

	/**
	 * Defines Oval as a Common Shape.
	 * 
	 * @author dk666
	 */
	public class Oval extends CommonShapes {
		public Oval(Defaults defaults) {
			super(defaults);
		}
	}
	
	/**
	 * @return the outlineColor
	 */
	public String getOutlineColor() {
		return this.outlineColor;
	}

	/**
	 * @param string
	 *            the outlineColor to set
	 */
	public void setOutlineColor(String string) {
		if (Utils.validARGB(string)) {
			this.outlineColor = string;
		}
	}
	
	/**
	 * Returns the thickness of the outline of the shape.
	 * @return outlineThickness The thickness of the outline
	 */
	public float getOutlineThickness() {
		return this.outlineThickness;
	}

	/**
	 * Sets the outline thickness of the graphic.
	 * 
	 * @param string
	 *            The thickness of the outline.
	 */
	public void setOutlineThickness(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.outlineThickness = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

}
