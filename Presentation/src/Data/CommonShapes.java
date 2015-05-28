package Data;

import utils.Utils;

public class CommonShapes extends Graphic {

	private float xEnd;
	private float yEnd;
	private boolean solid;
	private String outlineColor;
	private float outlineThickness;
	private float rotation;

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
		
		private float arcWidth;
		private float arcHeight;
		
		public Rectangle(Defaults defaults) {
			super(defaults);
		}
		
		public float getArcWidth() {
			return this.arcWidth;
		}

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
		
		public float getArcHeight() {
			return this.arcHeight;
		}

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

}
