/**
 * 
 */
package Data;

/**
 * @author dk666
 *
 */
public class CommonShapes extends Graphic {

	public CommonShapes() {
	}

	private float xEnd;
	private float yEnd;
	private boolean solid;

	/**
	 * @return the xEnd
	 */
	public float getxEnd() {
		return xEnd;
	}

	/**
	 * @param xEnd
	 *            the xEnd to set
	 */
	public void setxEnd(String string) {
		try {
			float f = Float.parseFloat(string);
			this.xEnd = f;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @return the yEnd
	 */
	public float getyEnd() {
		return yEnd;
	}

	/**
	 * @param yEnd
	 *            the yEnd to set
	 */
	public void setyEnd(String string) {
		try {
			float f = Float.parseFloat(string);
			this.yEnd = f;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @return the solid
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * @param solid
	 *            the solid to set
	 */
	public void setSolid(String string) {
		try {
			boolean b = Boolean.parseBoolean(string);
			this.solid = b;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
