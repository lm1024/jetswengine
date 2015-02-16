package GUI;

import java.awt.Color;
import java.awt.Graphics;

/** Abstract class that contains variables that all the graphics object
 *  contain. 
 * @author Tom Davidson */
public abstract class Shapes {

	protected float xStart;
	protected float yStart;
	protected float duration;
	protected float startTime;
	protected Color shapeColor;
	final boolean hasalpha = true;
	
	/** Constructor method for Shapes */
	public Shapes(float xStart, float yStart, Color shapeColor, float duration, float startTime) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.shapeColor = shapeColor;
		this.duration = duration;
		this.startTime = startTime;
	}

	public abstract void setXStart(float xStart);

	public abstract void setYStart(float yStart);

	public abstract void setXEnd(float xEnd);

	public abstract void setYEnd(float yEnd);
	
	/** Method setSolid required to support polymorphism */
	public void setSolid(boolean solid) {
	}
	
	public abstract void setDuration(float duration);
	
	public abstract void setStartTime(float startTime);

	public abstract void display(Graphics g);

}
