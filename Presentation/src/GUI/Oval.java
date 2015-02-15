package GUI;

import java.awt.Color;
import java.awt.Graphics;

public class Oval extends Shapes {

	protected float xEnd, yEnd;

	public Oval() {
		// Set initial values for boundary around Oval, and color of Oval
		xStart = 100;
		yStart = 100;
		xEnd = 200;
		yEnd = 200;
		shapeColor = new Color(255, 255, 255);
	}

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
	public void setSolid(boolean solid) {
		this.solid = solid;
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

}
