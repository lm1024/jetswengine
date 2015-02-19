/**
 * 
 */
package GUI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

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

		double xCenter = (xEndPos + xStartPos) / 2;
		double yCenter = (yEndPos + yStartPos) / 2;
		double xRad = (xEndPos - xStartPos) / 2;
		double yRad = (yEndPos - yStartPos) / 2;

		Ellipse oval = new Ellipse(xCenter, yCenter, xRad, yRad);
		oval.setFill(ovalColor);

		group.getChildren().add(oval);
	}

	public void drawCircle(float xStartPos, float yStartPos, float radius,
			Color circleColor) {
		/*
		 * TODO Write a method that draws a circle depending on params passed,
		 * calling drawOval
		 */
		Circle circle = new Circle(xStartPos, yStartPos, radius);
		circle.setFill(circleColor);

		group.getChildren().add(circle);
	}

	public void drawRectangle(float xStart, float yStart, float width,
			float height, float arcWidth, float arcHeight, Color rectColor) {
		/*
		 * TODO Write a method that draws a rectangle depending on params
		 * passed. Must be able to do rounded corners (look up arkheight and
		 * arkwidth javafx)
		 */
		Rectangle rectangle = new Rectangle(xStart, yStart, width, height);
		rectangle.setFill(rectColor);
		group.getChildren().add(rectangle);

	}

	public void drawSquare(float xStart, float yStart, float length, Color squareColor) {
		/*
		 * TODO Write a method that draws a square depending on params passed,
		 * calling drawRectangle. Must be able to do rounded corners (look up
		 * arkheight and arkwidth javafx)
		 */
		Rectangle square = new Rectangle(xStart, yStart, length, length);
		square.setFill(squareColor);
		group.getChildren().add(square);
	}

	public void drawLine(float startX, float startY, float endX, float endY, Color lineColor) {
		/*
		 * TODO Write a method that draws a line depending on params passed.
		 * Must be able to also do arrows!
		 */
		Line line = new Line(startX, startY, endX, endY);
		line.setStroke(lineColor);
		group.getChildren().add(line);
		

	}

	public void drawEquiTriangle() {
		/*
		 * TODO Write a method that draws an equilateral triangle depending on
		 * params passed.
		 */

	}

	public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3,
			double points[], Color triangleColor) {
		/*
		 * TODO Write a method that draws a triangle depending on params passed.
		 * Takes 3 sets of coordinates so any triangle can be made.
		 */
		Polygon triangle = new Polygon();
		triangle.setFill(triangleColor);
		
	}

	public void drawRegularPolygon(float Width, float Height, double x,
			double y, int numberOfSides, Color regPolColor) {
		/*
		 * TODO Write a method that draws a regular polygon of n sides.
		 */
		Polygon regPolygon = new Polygon();
		regPolygon.setFill(regPolColor);
		double radius = (double) (Math.min( 0.5 * Width, Height * 0.5));
		double z;
		for (int i = 0; i < numberOfSides; i++) {
			z = ((i * 2 * Math.PI) / numberOfSides );
			regPolygon.getPoints().add(((double)Math.round((x + (radius) * Math.sin(z)) + (radius))));
			regPolygon.getPoints().add(((double)Math.round((y + (-radius) * Math.cos(z)) + (radius))));

		}
		group.getChildren().add(regPolygon);
	}

	public void drawPolygon(Color polygonColor, Double points[], float x, float y) {
		/*
		 * TODO Write a method that draws ANY polygon given an array of
		 * coordinates. Investigate variable arguments in java (varargs).
		 */
		Polygon polygon = new Polygon();
		polygon.setFill(polygonColor);
		for(int i = 0; i < points.length ; i++) {;
			points[i] += x;
			i++;
			points[i] += y;
		}
		polygon.getPoints().addAll(points);
		group.getChildren().add(polygon);
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
