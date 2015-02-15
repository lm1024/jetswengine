package GUI;

import java.awt.Graphics;

public abstract class Shapes {
	
	protected float xStart, yStart;
	protected int rgba;
	final boolean hasalpha = true;
	
	public abstract void reSize(int dummy);

	public abstract void moveXY(int newX, int newY);

	public abstract void outputXYCoords(Graphics g);

	public abstract void display(Graphics g);
}
