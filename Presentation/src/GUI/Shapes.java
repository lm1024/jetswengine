package GUI;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shapes {

	protected float xStart, yStart;
	protected Color shapeColor;
	final boolean hasalpha = true;

	public Shapes(int xStart, int yStart, Color shapeColor) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.shapeColor = shapeColor;
	}

	public abstract void setXStart(int xStart);

	public abstract void setYStart(int yStart);

	public abstract void setXEnd(int xEnd);

	public abstract void setYEnd(int yEnd);

	public void setSolid(boolean solid) {
	}

	public abstract void display(Graphics g);

}
