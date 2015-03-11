/**
 * 
 */
package Data;

/**
 * @author dk666
 *
 */
public class RadialShapes extends Graphic {
	
	private boolean solid;
	private float size = 0.5f;

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
		public Square(Defaults defaults) {
			super(defaults);
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
