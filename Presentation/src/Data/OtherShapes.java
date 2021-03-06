/**
 * 
 */
package Data;

import java.util.ArrayList;
import java.util.List;
import utils.Utils;

/**
 * @author dk666
 *
 */
public class OtherShapes extends Graphic {
	
	private boolean solid;

	public OtherShapes(Defaults defaults) {
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
	 * @return {@code true} if shape is solid
	 */
	public boolean isSolid() {
		return solid;
	}

	public void setSolid(String string) {
		boolean b = Boolean.parseBoolean(string);
		this.solid = b;
	}
	
	public class Triangle extends OtherShapes {
		
		private float outlineThickness;
		private float rotation;
		private String outlineColor;
		private List<Float> xPoints = new ArrayList<Float>();
		private List<Float> yPoints = new ArrayList<Float>();
		
		public Triangle(Defaults defaults) {
			super(defaults);
			this.outlineColor = defaults.getOutlineColor();
			this.outlineThickness = defaults.getOutlineThickness();
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
		 * @return the xPoints
		 */
		public List<Float> getxPoints() {
			return xPoints;
		}

		/**
		 * @param xPoints the xPoints to set
		 */
		public void setxPoints(List<Float> xPoints) {
			this.xPoints = xPoints;
		}

		/**
		 * @return the yPoints
		 */
		public List<Float> getyPoints() {
			return yPoints;
		}

		/**
		 * @param yPoints the yPoints to set
		 */
		public void setyPoints(List<Float> yPoints) {
			this.yPoints = yPoints;
		}
	}
	
	public class Polygon extends OtherShapes {
		
		private float outlineThickness;
		private String outlineColor;
		private float rotation;
		private List<Float> xPoints = new ArrayList<Float>();
		private List<Float> yPoints = new ArrayList<Float>();
		
		public Polygon(Defaults defaults) {
			super(defaults);
			this.outlineColor = defaults.getOutlineColor();
			this.outlineThickness = defaults.getOutlineThickness();
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
		 * @return the xPoints
		 */
		public List<Float> getxPoints() {
			return xPoints;
		}

		/**
		 * @param xPoints the xPoints to set
		 */
		public void setxPoints(List<Float> xPoints) {
			this.xPoints = xPoints;
		}

		/**
		 * @return the yPoints
		 */
		public List<Float> getyPoints() {
			return yPoints;
		}

		/**
		 * @param yPoints the yPoints to set
		 */
		public void setyPoints(List<Float> yPoints) {
			this.yPoints = yPoints;
		}
	}
	
	public class Chord extends OtherShapes {
		
		private float outlineThickness;
		private String outlineColor;
		private float width;
		private float height;
		private float length;
		private float arcAngle;
		private float rotation;
		
		public Chord(Defaults defaults) {
			super(defaults);
			this.outlineColor = defaults.getOutlineColor();
			this.outlineThickness = defaults.getOutlineThickness();
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

		public float getWidth() {
			return width;
		}

		public void setWidth(String string) {
			try {
				float f = Float.parseFloat(string);
				if(f > 0) {
					this.width = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}

		public float getHeight() {
			return height;
		}

		public void setHeight(String string) {
			try {
				float f = Float.parseFloat(string);
				if(f > 0) {
					this.height = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}

		public float getLength() {
			return length;
		}

		public void setLength(String string) {
			try {
				float f = Float.parseFloat(string);
				if(f > 0) {
					this.length = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
		public float getArcAngle() {
			return arcAngle;
		}

		public void setArcAngle(String string) {
			try {
				float f = Float.parseFloat(string);
				if(f > 0) {
					this.arcAngle = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
	}
	
	public class Arc extends OtherShapes {
		
		private float outlineThickness;
		private String outlineColor;
		private float width;
		private float height;
		private float length;
		private float arcAngle;
		private float rotation;
		
		public Arc(Defaults defaults) {
			super(defaults);
			this.outlineColor = defaults.getOutlineColor();
			this.outlineThickness = defaults.getOutlineThickness();
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

		public float getWidth() {
			return width;
		}

		public void setWidth(String string) {
			try {
				float f = Float.parseFloat(string);
				if(f > 0) {
					this.width = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}

		public float getHeight() {
			return height;
		}

		public void setHeight(String string) {
			try {
				float f = Float.parseFloat(string);
				if(f > 0) {
					this.height = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}

		public float getLength() {
			return length;
		}

		public void setLength(String string) {
			try {
				float f = Float.parseFloat(string);
				if(f > 0) {
					this.length = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
		public float getArcAngle() {
			return arcAngle;
		}

		public void setArcAngle(String string) {
			try {
				float f = Float.parseFloat(string);
				if(f > 0) {
					this.arcAngle = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
	}
	
	public class Line extends OtherShapes {
		
		private float xEnd;
		private float yEnd;
		private float thickness = 1;
		
		public Line(Defaults defaults) {
			super(defaults);
		}
		
		@Override
		public void printItem() {
			super.printItem();
			System.out.println("xEnd: " + xEnd);
			System.out.println("yEnd: " + yEnd);
			System.out.println("thickness: " + thickness);
			System.out.println("");
		}
		
		public float getThickness() {
			return this.thickness;
		}

		public void setThickness(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.thickness = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
		
		public float getXEnd() {
			return xEnd;
		}

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

		public float getYEnd() {
			return yEnd;
		}

		/**
		 * @param yEnd
		 *            the yEnd to set
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


	}
	
	public class Arrow extends OtherShapes {
		
		private float xEnd;
		private float yEnd;
		private float thickness = 1;
		
		public Arrow(Defaults defaults) {
			super(defaults);
		}
		
		public float getThickness() {
			return this.thickness;
		}

		public void setThickness(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.thickness = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
		
		public float getXEnd() {
			return xEnd;
		}

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

		public float getYEnd() {
			return yEnd;
		}

		/**
		 * @param yEnd
		 *            the yEnd to set
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
	}
}
