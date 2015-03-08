/**
 * 
 */
package Data;

/**
 * @author dk666
 *
 */
public abstract class RadialShape extends Graphic {
	
	private boolean solid;
	private float radius = 0.5f;
	private float sideLength = 0.5f;

	public RadialShape(Defaults defaults) {
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
	public float getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(String string) {
		try {
			float f = Float.parseFloat(string);
			if(f > 0) {
				this.radius = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the sideLength
	 */
	public float getSideLength() {
		return sideLength;
	}

	/**
	 * @param string the sideLength to set
	 */
	public void setSideLength(String string) {
		try {
			float f = Float.parseFloat(string);
			if(f > 0) {
				this.sideLength = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	public class Circle extends RadialShape {
		public Circle(Defaults defaults) {
			super(defaults);
		}
		
		@Override
		public void setSideLength(String string) {
			System.err.println("Unable to set side length of circle");
		}
	}
	
	public class Square extends RadialShape {
		public Square(Defaults defaults) {
			super(defaults);
		}
	}
	
	public class EquilateralTriangle extends RadialShape {
		public EquilateralTriangle(Defaults defaults) {
			super(defaults);
		}
	}
	
	public class RegularPolygon extends RadialShape {
		public RegularPolygon(Defaults defaults) {
			super(defaults);
		}
	}
	
	public class Star extends RadialShape {
		public Star(Defaults defaults) {
			super(defaults);
		}
	}

}
