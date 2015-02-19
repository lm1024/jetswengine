package SwingGUI;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Abstract class that contains variables that all the graphics object contain.
 * 
 * @author Tom Davidson
 */
public abstract class Shapes {

	protected int xStart;
	protected int yStart;
	protected float duration;
	protected float startTime;
	protected Color shapeColor;
	final boolean hasalpha = true;

	/** Constructor method for Shapes */
	public Shapes(int xStart, int yStart, Color shapeColor, float duration,
			float startTime) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.shapeColor = shapeColor;
		this.duration = duration;
		this.startTime = startTime;
	}

	/** List of methods that all classes that extend from Shapes must use */
	public abstract void setXStart(int xStart);

	public abstract void setYStart(int yStart);

	public abstract void setXEnd(int xEnd);

	public abstract void setYEnd(int yEnd);

	/**
	 * Method setSolid required to support polymorphism even though line will
	 * not require a solid tag. If this is called on a line object, this method
	 * will be called, so nothing will happen.
	 */
	public void setSolid(boolean solid) {
	}

	public abstract void setDuration(float duration);

	public abstract void setStartTime(float startTime);

	public abstract void setColor(Color shapeColor);

	/**
	 * Method setRotation required to support polymorphism even though some
	 * objects will not require rotation. If this is called on a (for example)
	 * circle object, this method will be called, so nothing will happen.
	 */
	public void setRotation(float rotation) {
	}

	public abstract void display(Graphics g);

}
