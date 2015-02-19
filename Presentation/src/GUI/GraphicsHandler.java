/**
 * 
 */
package GUI;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * @author tjd511
 * 
 */
public class GraphicsHandler {

	private Group group;

	/**
	 * @param group
	 * 
	 */
	public GraphicsHandler(Group group) {
		// TODO Auto-generated constructor stub
		this.group = group;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void drawOval(float xStartPos, float yStartPos, float xEndPos,
			float yEndPos, Color ovalColor, float rotation) {
		/* TODO Write a method that draws an oval depending on params passed */

		// group.getChildren().add(ITEM GOES HERE);
	}

	public void drawCircle(float xStartPos, float yStartPos, float radius,
			Color circleColor) {
		/*
		 * TODO Write a method that draws a circle depending on params passed,
		 * calling drawOval
		 */

		// group.getChildren().add(ITEM GOES HERE);
	}

	public void drawRectangle() {
		/*
		 * TODO Write a method that draws a rectangle depending on params
		 * passed. Must be able to do rounded corners (look up arkheight and
		 * arkwidth javafx)
		 */

	}

	public void drawSquare() {
		/*
		 * TODO Write a method that draws a square depending on params passed,
		 * calling drawRectangle. Must be able to do rounded corners (look up
		 * arkheight and arkwidth javafx)
		 */
	}

	public void drawLine() {
		/*
		 * TODO Write a method that draws a line depending on params passed.
		 * Must be able to also do arrows!
		 */

	}

	public void drawEquiTriangle() {
		/*
		 * TODO Write a method that draws an equilateral triangle depending on
		 * params passed.
		 */

	}

	public void drawTriangle() {
		/*
		 * TODO Write a method that draws a triangle depending on params passed.
		 * Takes 3 sets of coordinates so any triangle can be made.
		 */

	}

	public void drawRegularPolygon() {
		/*
		 * TODO Write a method that draws a regular polygon of n sides.
		 */

	}

	public void drawPolygon() {
		/*
		 * TODO Write a method that draws ANY polygon given an array of
		 * coordinates. Investigate variable arguments in java (varargs).
		 */
	}

	public void drawStar() {
		/*
		 * TODO Write a method that draws a n pointed star depending on params
		 * passed.
		 */
	}

	public void drawPieChart() {
		/*
		 * TODO Write a method that draws a pie chart, varying upon input of 2
		 * arrays, one of name of input and one of numbers in input. Toggle key,
		 * and maybe array of colors for sections?
		 */
	}

	public void drawBarChart() {
		/*
		 * TODO Write a method that draws a bar chart, varying upon input of 2
		 * arrays, one of name of input and one of numbers in input. Toggle key,
		 * and maybe array of colors for sections? Color schemes?
		 */
	}

}
