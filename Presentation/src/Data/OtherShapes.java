/**
 * 
 */
package Data;

import java.util.ArrayList;
import java.util.List;

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
		private List<Float> xPoints = new ArrayList<Float>();
		private List<Float> yPoints = new ArrayList<Float>();
		
		public Triangle(Defaults defaults) {
			super(defaults);
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
		private List<Float> xPoints = new ArrayList<Float>();
		private List<Float> yPoints = new ArrayList<Float>();
		
		public Polygon(Defaults defaults) {
			super(defaults);
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
		private float width;
		private float height;
		private float length;
		private float arcAngle;
		
		public Chord(Defaults defaults) {
			super(defaults);
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
		private float width;
		private float height;
		private float length;
		private float arcAngle;
		
		public Arc(Defaults defaults) {
			super(defaults);
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
}
