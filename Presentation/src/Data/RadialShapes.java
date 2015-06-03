/** (c) Copyright by WaveMedia. */
package Data;

import utils.Utils;

/**
 * RadialShapes class for defining shapes that have a radius.
 * 
 * @author dk666
 * @version 2.1 02/03/15
 */
public class RadialShapes extends Graphic {

	/**/
	private boolean solid;
	private float size;
	private float rotation;
	private String outlineColor;
	private float outlineThickness;

	/**
	 * Constructs a new RadialShapes object with the specified defaults.
	 * 
	 * @param defaults
	 */
	public RadialShapes(Defaults defaults) {
		super(defaults);
		this.solid = defaults.getShapeSolidity();
	}

	/**
	 * For debugging. Outputs the properties of this graphic.
	 */
	@Override
	public void printItem() {
		super.printItem();
		System.out.println("Solid: " + solid);
		System.out.println("Size: " + size);
		System.out.println("Rotation: " + rotation);
		System.out.println("Outline Color: " + outlineColor);
		System.out.println("Outline Thickness: " + outlineThickness);
		System.out.println("");
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

	/**
	 * @return {@code true} if shape is solid
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * Sets the solidity of the shape.
	 * @param string
	 */
	public void setSolid(String string) {
		boolean b = Boolean.parseBoolean(string);
		this.solid = b;
	}

	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return this.rotation;
	}

	/**
	 * @param string
	 *            the rotation to set
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
	 * @return the radius
	 */
	public float getSize() {
		return this.size;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setSize(String string) {
		try {
			float f = Float.parseFloat(string);
			if (f > 0) {
				this.size = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * Defines Circle as a radial shape.
	 * @author dk666 
	 * 
	 */
	public class Circle extends RadialShapes {
		public Circle(Defaults defaults) {
			super(defaults);
		}
	}

	/**
	 * Defines Square as a radial shape. Squares also have arc widths and
	 * heights.
	 * 
	 * @author dk666
	 */
	public class Square extends RadialShapes {

		private float arcHeight;
		private float arcWidth;

		/**
		 * Constructs a new Square object with the specified defaults.
		 * 
		 * @param defaults
		 */
		public Square(Defaults defaults) {
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
	 * Defines EquilateralTriangle as a radial shape.
	 * @author dk666 
	 * 
	 */
	public class EquilateralTriangle extends RadialShapes {
		/**
		 * Constructs a new EquilateralTriangle object with the specified defaults.
		 * 
		 * @param defaults
		 */
		public EquilateralTriangle(Defaults defaults) {
			super(defaults);
		}
	}

	/**
	 * Defines RegularPolygon as a radial shape. Regular polygons
	 *         can also have any number of sides
	 * @author dk666 
	 * 
	 */
	public class RegularPolygon extends RadialShapes {
		private int numberOfSides;

		/**
		 * Constructs a new RegularPolygon object with the specified defaults.
		 * 
		 * @param defaults
		 */
		public RegularPolygon(Defaults defaults) {
			super(defaults);
		}

		/**
		 * Returns the number of sides of the polygon.
		 * @return numberOfSides
		 */
		public int getNumberOfSides() {
			return numberOfSides;
		}

		/**
		 * Sets the number of sides of the polygon.
		 * @param string
		 * 			The number of sides to set
		 */			
		public void setNumberOfSides(String string) {
			try {
				int i = Integer.parseInt(string);
				if (i > 0) {
					this.numberOfSides = i;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
	}

	/**
	 * Defines Star as a radial shape. Stars
	 *         can also have any number of points > 3.
	 * @author dk666 
	 * 
	 */
	public class Star extends RadialShapes {
		
		/* Default nuber of points is 5 */
		private int numberOfPoints = 5;

		/**
		 * Constructs a new Star object with the specified defaults.
		 * 
		 * @param defaults
		 */
		public Star(Defaults defaults) {
			super(defaults);
		}

		/**
		 * Returns the number of points on the star.
		 * @return numberOfPoints
		 */
		public int getNumberOfPoints() {
			return numberOfPoints;
		}

		/**
		 * Sets the number of points on the star.
		 * @param string
		 *			The number of points to set.
		 */			
		public void setNumberOfPoints(String string) {
			try {
				int i = Integer.parseInt(string);
				if (i > 3) {
					this.numberOfPoints = i;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
	}

}
