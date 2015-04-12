/**
 * 
 */
package Data;

import utils.Utils;

/**
 * @author dk666
 *
 */
public class RadialShapes extends Graphic {
	
	private boolean solid;
	private float size;
	private float rotation;
	private String outlineColor;
	private float outlineThickness;
	

	public RadialShapes(Defaults defaults) {
		super(defaults);
		this.solid = defaults.getShapeSolidity();
	}
	
	@Override
	public void printItem() {
		super.printItem();
		System.out.println("Solid: " + solid);
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
	
	public float getOutlineThickness() {
		return this.outlineThickness;
	}

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
	 * @return the radius
	 */
	public float getSize() {
		return this.size;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setSize(String string) {
		try {
			float f = Float.parseFloat(string);
			if(f > 0) {
				this.size = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	public class Circle extends RadialShapes {
		public Circle(Defaults defaults) {
			super(defaults);
		}
	}
	
	public class Square extends RadialShapes {
		
		private float arcHeight;
		private float arcWidth;
		
		public Square(Defaults defaults) {
			super(defaults);
		}
		
		public float getArcWidth() {
			return this.arcWidth;
		}

		public void setArcWidth(String string) {
			try {
				float f = Float.parseFloat(string);
				if (Utils.withinRangeInclusive(0, 1, f)) {
					this.arcWidth = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
		
		public float getArcHeight() {
			return this.arcHeight;
		}

		public void setArcHeight(String string) {
			try {
				float f = Float.parseFloat(string);
				if (Utils.withinRangeInclusive(0, 1, f)) {
					this.arcHeight = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
	}
	
	public class EquilateralTriangle extends RadialShapes {
		public EquilateralTriangle(Defaults defaults) {
			super(defaults);
		}
	}
	
	public class RegularPolygon extends RadialShapes {
		private int numberOfSides;
		public RegularPolygon(Defaults defaults) {
			super(defaults);
		}
		public int getNumberOfSides() {
			return numberOfSides;
		}
		public void setNumberOfSides(String string) {
			try {
				int i = Integer.parseInt(string);
				if(i > 0) {
					this.numberOfSides = i;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
	}
	
	public class Star extends RadialShapes {
		private int numberOfPoints = 5;
		public Star(Defaults defaults) {
			super(defaults);
		}
		
		public int getNumberOfSides() {
			return numberOfPoints;
		}
		public void setNumberOfPoints(String string) {
			try {
				int i = Integer.parseInt(string);
				if(i > 3) {
					this.numberOfPoints = i;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
	}

}
