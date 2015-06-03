/** (c) Copyright by WaveMedia. */
package Data;

import java.util.ArrayList;
import java.util.List;
import utils.Utils;

/**
 * OtherShapes class for defining shapes that don't fit the other categories.
 * 
 * @author dk666
 * @version 2.1 02/03/15
 */
public class OtherShapes extends Graphic {

	/* Other shapes all have a solid variable */
	private boolean solid;

	/**
	 * Constructs a new OtherShapes object with the specified defaults.
	 * 
	 * @param defaults
	 */
	public OtherShapes(Defaults defaults) {
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
		System.out.println("");
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
	 * Defines Triangle as an OtherShape.
	 * 
	 * @author dk666
	 * 
	 */
	public class Triangle extends OtherShapes {

		private float outlineThickness;
		private float rotation;
		private String outlineColor;

		/* Triangles have a list of x and y points */
		private List<Float> xPoints = new ArrayList<Float>();
		private List<Float> yPoints = new ArrayList<Float>();

		/**
		 * Constructs a new Triangle object with the specified defaults.
		 * 
		 * @param defaults
		 */
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
		 * Returns the outline thickness of the shape.
		 * 
		 * @return outlineThickness The outline thickness
		 */
		public float getOutlineThickness() {
			return this.outlineThickness;
		}

		/**
		 * Sets the outline thickness of the shape.
		 * 
		 * @param string
		 *            The outline thickness to set.
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
		 * Returns a list of the xPoints of the triangle.
		 * 
		 * @return the xPoints
		 */
		public List<Float> getxPoints() {
			return xPoints;
		}

		/**
		 * Sets the xPoints of the triangle.
		 * 
		 * @param xPoints
		 *            the xPoints to set
		 */
		public void setxPoints(List<Float> xPoints) {
			this.xPoints = xPoints;
		}

		/**
		 * Returns a list of the yPoints of the triangle.
		 * 
		 * @return the yPoints
		 */
		public List<Float> getyPoints() {
			return yPoints;
		}

		/**
		 * Sets the yPoints of the triangle.
		 * 
		 * @param yPoints
		 *            the yPoints to set
		 */
		public void setyPoints(List<Float> yPoints) {
			this.yPoints = yPoints;
		}
	}

	/**
	 * Defines Polygon as an OtherShape.
	 * 
	 * @author dk666
	 * 
	 */
	public class Polygon extends OtherShapes {

		private float outlineThickness;
		private String outlineColor;
		private float rotation;

		/* Polygons have a list of x and y points */
		private List<Float> xPoints = new ArrayList<Float>();
		private List<Float> yPoints = new ArrayList<Float>();

		/**
		 * Constructs a new Polygon object with the specified defaults.
		 * 
		 * @param defaults
		 */
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
		 * Returns the outline thickness of the shape.
		 * 
		 * @return outlineThickness the thickness of the shapes outline.
		 */
		public float getOutlineThickness() {
			return this.outlineThickness;
		}

		/**
		 * Sets the thickness of the outline of the shape.
		 * 
		 * @param string
		 *            the thickness of the outline.
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
		 * @return the xPoints
		 */
		public List<Float> getxPoints() {
			return xPoints;
		}

		/**
		 * @param xPoints
		 *            the xPoints to set
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
		 * @param yPoints
		 *            the yPoints to set
		 */
		public void setyPoints(List<Float> yPoints) {
			this.yPoints = yPoints;
		}
	}

	/**
	 * Defines Chord as an OtherShape.
	 * 
	 * @author dk666
	 * 
	 */
	public class Chord extends OtherShapes {

		/* Parameters of a chord */
		private float outlineThickness;
		private String outlineColor;
		private float width;
		private float height;
		private float length;
		private float arcAngle;
		private float rotation;

		/**
		 * Constructs a new Chord object with the specified defaults.
		 * 
		 * @param defaults
		 */
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
		 * 
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
		 * Returns the width of the chord.
		 * 
		 * @return width the width of the chord.
		 */
		public float getWidth() {
			return width;
		}

		/**
		 * Sets the width of the chord.
		 * 
		 * @param string
		 *            the width to set.
		 */
		public void setWidth(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.width = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}

		/**
		 * Returns the height of the chord.
		 * 
		 * @return height the height of the chord.
		 */
		public float getHeight() {
			return height;
		}

		/**
		 * Sets the height of the chord.
		 * 
		 * @param string
		 *            the height to set.
		 */
		public void setHeight(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.height = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}

		/**
		 * Returns the length of the chord.
		 * 
		 * @return length the length of the chord.
		 */
		public float getLength() {
			return length;
		}

		/**
		 * Sets the length of the chord.
		 * 
		 * @param string
		 *            the length to set.
		 */
		public void setLength(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.length = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}

		/**
		 * Returns the arcAngle of the chord.
		 * 
		 * @return arcAngle the arcAngle of the chord.
		 */
		public float getArcAngle() {
			return arcAngle;
		}

		/**
		 * Sets the arcAngle of the chord.
		 * 
		 * @param string
		 *            the arcAngle to set.
		 */
		public void setArcAngle(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.arcAngle = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
	}

	/**
	 * Defines Arc as an OtherShape.
	 * 
	 * @author dk666
	 * 
	 */
	public class Arc extends OtherShapes {

		/* Parameters of an Arc */
		private float outlineThickness;
		private String outlineColor;
		private float width;
		private float height;
		private float length;
		private float arcAngle;
		private float rotation;

		/**
		 * Constructs a new Arc object with the specified defaults.
		 * 
		 * @param defaults
		 */
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
		 * 
		 * @return outlineThickness The thickness of the outline
		 */
		public float getOutlineThickness() {
			return this.outlineThickness;
		}

		/**
		 * Sets the outline thickness of the shape.
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
		 * Returns the width of the arc.
		 * 
		 * @return width the width of the arc.
		 */
		public float getWidth() {
			return width;
		}

		/**
		 * Sets the width of the arc.
		 * 
		 * @param string
		 *            the width to set.
		 */
		public void setWidth(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.width = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}

		/**
		 * Returns the height of the arc.
		 * 
		 * @return height the height of the arc.
		 */
		public float getHeight() {
			return height;
		}

		/**
		 * Sets the height of the arc.
		 * 
		 * @param string
		 *            the height to set.
		 */
		public void setHeight(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.height = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}

		/**
		 * Returns the length of the arc.
		 * 
		 * @return length the length of the arc.
		 */
		public float getLength() {
			return length;
		}

		/**
		 * Sets the length of the arc.
		 * 
		 * @param string
		 *            the length to set.
		 */
		public void setLength(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.length = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}

		/**
		 * Returns the arcAngle of the arc.
		 * 
		 * @return arcAngle the arcAngle of the arc.
		 */
		public float getArcAngle() {
			return arcAngle;
		}

		/**
		 * Sets the arcAngle of the arc.
		 * 
		 * @param string
		 *            the arcAngle to set.
		 */
		public void setArcAngle(String string) {
			try {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.arcAngle = f;
				}
			} catch (Exception e) {
				/* Do Nothing */
			}
		}
	}

	/**
	 * Defines Line as an OtherShape.
	 * 
	 * @author dk666
	 * 
	 */
	public class Line extends OtherShapes {

		/* Parameters of a Line */
		private float xEnd;
		private float yEnd;

		/* Lines have a default thickness of 1 */
		private float thickness = 1;

		/**
		 * Constructs a new Line object with the specified defaults.
		 * 
		 * @param defaults
		 */
		public Line(Defaults defaults) {
			super(defaults);
		}

		/**
		 * For Debugging. Prints the properties of this line.
		 */
		@Override
		public void printItem() {
			super.printItem();
			System.out.println("xEnd: " + xEnd);
			System.out.println("yEnd: " + yEnd);
			System.out.println("thickness: " + thickness);
			System.out.println("");
		}

		/**
		 * Returns the thickness of the outline of the shape.
		 * 
		 * @return outlineThickness The thickness of the outline
		 */
		public float getThickness() {
			return this.thickness;
		}

		/**
		 * Sets the outline thickness of the shape.
		 * 
		 * @param string
		 *            The thickness of the outline.
		 */
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

		/**
		 * Returns the xEnd relative co-ordinate of this line.
		 * 
		 * @return xEnd.
		 */
		public float getXEnd() {
			return xEnd;
		}

		/**
		 * Sets the relative xEnd of this line.
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
		 * Returns the yEnd relative co-ordinate of this line.
		 * 
		 * @return yEnd.
		 */
		public float getYEnd() {
			return yEnd;
		}

		/**
		 * Sets the relative yEnd of this line.
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

	}

	/**
	 * Defines Arrow as an OtherShape.
	 * 
	 * @author dk666
	 * 
	 */
	public class Arrow extends OtherShapes {

		/* Parameters of an Arrow */
		private float xEnd;
		private float yEnd;

		/* Arrows have a default thickness of 1 */
		private float thickness = 1;

		/**
		 * Constructs a new Arrow object with the specified defaults.
		 * 
		 * @param defaults
		 */
		public Arrow(Defaults defaults) {
			super(defaults);
		}

		/**
		 * Returns the thickness of the outline of the shape.
		 * 
		 * @return outlineThickness The thickness of the outline
		 */
		public float getThickness() {
			return this.thickness;
		}

		/**
		 * Sets the outline thickness of the shape.
		 * 
		 * @param string
		 *            The thickness of the outline.
		 */
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

		/**
		 * Returns the xEnd relative co-ordinate of this Arrow.
		 * 
		 * @return xEnd.
		 */
		public float getXEnd() {
			return xEnd;
		}

		/**
		 * Sets the relative xEnd of this Arrow.
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
		 * Returns the yEnd relative co-ordinate of this Arrow.
		 * 
		 * @return yEnd.
		 */
		public float getYEnd() {
			return yEnd;
		}

		/**
		 * Sets the relative yEnd of this Arrow.
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
	}
}
