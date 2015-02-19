package SwingGUI;

import java.awt.Color;
import java.awt.Graphics;

/** Class for drawing an oval on a graphics window
 * 
 * @version 1.1 16/02/2015
 * @author Tom Davidson */
public class Oval extends Shapes {

	protected int xEnd;
	protected int yEnd;
	protected boolean solid;
	//private float rotation;
	
	/** Constructor method */
	public Oval(int xStart, int yStart, int xEnd, int yEnd, boolean solid, Color shapeColor, float rotation, float duration, float startTime ) {
		/* Required call to super constructor */
		super(xStart, yStart, shapeColor, duration, startTime);
		
		/* Set initial values for Oval */
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.solid = solid;
		this.shapeColor = shapeColor;
		//this.rotation = rotation;
	}
	
	/** Method for setting the X Start Coordinate */
	@Override
	public void setXStart(int xStart) {
		this.xStart = xStart;
	}

	/** Method for setting the Y Start Coordinate */
	@Override
	public void setYStart(int yStart) {
		this.yStart = yStart;
	}

	/** Method for setting the X End Coordinate */
	@Override
	public void setXEnd(int xEnd) {
		this.xEnd = xEnd;
	}

	/** Method for setting the Y End Coordinate */
	@Override
	public void setYEnd(int yEnd) {
		this.yEnd = yEnd;
	}
	
	/** Method for setting the solid boolean */
	@Override
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	/** Method for setting the duration */
	@Override
	public void setDuration(float duration) {
		this.duration = duration;
	}
	
	/** Method for setting the startime */
	@Override
	public void setStartTime(float startTime) {
		this.startTime = startTime;
	}

	/** Method for setting the color of the shape */
	@Override
	public void setColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}
	
	/** Method for setting the rotation of the shape */
	@Override
	public void setRotation(float rotation) {
		//this.rotation = rotation;
	}	
	
	/** Method for displaying the object */
	@Override
	public void display(Graphics g) {
		g.setColor(shapeColor);
		
		/* Draws a oval or outline of oval depending on solid boolean */
		if (solid)
			g.fillOval((int) xStart, (int) yStart, (int) (xEnd - xStart), (int) (yEnd - yStart));
		else {
			g.drawOval((int) xStart, (int) yStart, (int) (xEnd - xStart), (int) (yEnd - yStart));			
		}
	}
	
}
