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
	public Shapes(int xStart, int yStart, Color shapeColor, float duration, float startTime) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.shapeColor = shapeColor;
		this.duration = duration;
		this.startTime = startTime;
	}

	public abstract void setXStart(int xStart);

	public abstract void setYStart(int yStart);

	public abstract void setXEnd(int xEnd);

	public abstract void setYEnd(int yEnd);
	
	/** Method setSolid required to support polymorphism */
	public void setSolid(boolean solid) {
	}
	
	public abstract void setDuration(float duration);
	
	public abstract void setStartTime(float startTime);

	public abstract void display(Graphics g);

}
