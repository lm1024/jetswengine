package Data;

import utils.Utils;

public class CommonShapes extends Graphic {

	private float xEnd;
	private float yEnd;
	private boolean solid;

	public CommonShapes(Defaults defaults) {
		super(defaults);
		this.solid = defaults.getShapeSolidity();
	}

	@Override
	public void printItem() {
		super.printItem();
		System.out.println("xEnd: " + xEnd);
		System.out.println("yEnd: " + yEnd);
		System.out.println("Solid: " + solid);
		System.out.println("");
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

	public class Rectangle extends CommonShapes {
		public Rectangle(Defaults defaults) {
			super(defaults);
		}
	}

	public class Oval extends CommonShapes {
		public Oval(Defaults defaults) {
			super(defaults);
		}
	}

	public class Line extends CommonShapes {
		public Line(Defaults defaults) {
			super(defaults);
		}
	}

	public class IsoscelesTriangle extends CommonShapes {
		public IsoscelesTriangle(Defaults defaults) {
			super(defaults);
		}
	}
}
