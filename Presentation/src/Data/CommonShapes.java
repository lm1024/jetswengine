/**
 * 
 */
package Data;

/**
 * @author dk666
 *
 */
public class CommonShapes extends Graphic{
	
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
	 * @param xEnd the xEnd to set
	 */
	public void setxEnd(float xEnd) {
		this.xEnd = xEnd;
	}
	/**
	 * @return the yEnd
	 */
	public float getyEnd() {
		return yEnd;
	}
	/**
	 * @param yEnd the yEnd to set
	 */
	public void setyEnd(float yEnd) {
		this.yEnd = yEnd;
	}
	/**
	 * @return the solid
	 */
	public boolean isSolid() {
		return solid;
	}
	/**
	 * @param solid the solid to set
	 */
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

}
