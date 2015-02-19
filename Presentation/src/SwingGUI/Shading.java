package SwingGUI;

import java.awt.Color;
import java.awt.Graphics;

public class Shading {

	int xStart = 100;
	int xEnd = 200;
	int yStart = 300;
	int yEnd = 400;

	public Shading() {

	}

	public void display(Graphics g) {
		int originalWidth = (int) (xEnd - xStart);
		int originalHeight = (int) (yEnd - yStart);
		int width = originalWidth;
		int height = originalHeight;

		g.fillOval((int) xStart, (int) yStart, width, height);

		int maxShadingNumber = 50;
		for (int i = 0; i != maxShadingNumber; i++) {
			g.setColor(new Color(255 * i / maxShadingNumber, 255 * i
					/ maxShadingNumber, 255 * i / maxShadingNumber));
			width = (int) (originalWidth - i * (1.0f / maxShadingNumber)
					* originalWidth);
			height = (int) (originalHeight - i * (1.0f / maxShadingNumber)
					* originalHeight);

			g.fillOval((int) (xStart + i * (0.5f / maxShadingNumber)
					* originalWidth), (int) (yStart + i
					* (0.5f / maxShadingNumber) * originalHeight), width,
					height); // (int) (Math.pow(0.9,i)*(xEnd - xStart)), (int)
								// (Math.pow(0.9,i)*(yEnd - yStart)));
		}
	}

}
