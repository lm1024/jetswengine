package GUI;

import java.awt.Color;
import java.awt.Graphics;

public class Oval extends Shapes {

	protected float xEnd, yEnd;
	protected boolean solid;

	public Oval(int xStart, int yStart, int xEnd, int yEnd, boolean solid, Color shapeColor, float duration, float startTime ) {
		super(xStart, yStart, shapeColor);
		// Set initial values for boundary around Oval, and color of Oval
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.solid = solid;
		shapeColor = new Color(255, 255, 255);
	}
	
	@Override
	public void setXStart(int xStart) {
		this.xStart = xStart;
	}

	@Override
	public void setYStart(int yStart) {
		// TODO Auto-generated method stub
		this.yStart = yStart;
	}

	@Override
	public void setXEnd(int xEnd) {
		// TODO Auto-generated method stub
		this.xEnd = xEnd;
	}

	@Override
	public void setYEnd(int yEnd) {
		// TODO Auto-generated method stub
		this.yEnd = yEnd;
	}

	@Override
	public void display(Graphics g) {
		g.setColor(shapeColor);
		// Draws a oval or outline of oval depening on solid checkbox
		if (solid)
			g.fillOval((int) xStart, (int) yStart, (int) (xEnd - xStart), (int) (yEnd - yStart));
		else
			g.drawOval((int) xStart, (int) yStart, (int) (xEnd - xStart), (int) (yEnd - yStart));
	}
	
	@Override
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public void setColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}
	
	public void setDuration(float duration) {
		
	}
	
	

}
