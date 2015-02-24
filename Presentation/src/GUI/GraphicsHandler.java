/**
 * 
 */
package GUI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

	private void addOutline(Shape shape) {

	}

	public void drawOval(float xStartPos, float yStartPos, float xEndPos, float yEndPos, Color ovalColor,
			Color outline, int outlineThickness, boolean shadow, float rotation, Shading shadingType, Color shadingColor) {

	}

	public void drawOval(float xStartPos, float yStartPos, float xEndPos, float yEndPos, boolean solid, Color ovalColor) {
		if (solid)
			drawOval(xStartPos, yStartPos, xEndPos, yEndPos, ovalColor, ovalColor, 0);
		else
			drawOval(xStartPos, yStartPos, xEndPos, yEndPos, new Color(0, 0, 0, 0), ovalColor, 0);

	}

	public void drawOval(float xStartPos, float yStartPos, float xEndPos, float yEndPos, Color ovalColor,
			Color outline, float rotation) {
		/* TODO Write a method that draws an oval depending on params passed */
		double xCenter = (xEndPos + xStartPos) / 2;
		double yCenter = (yEndPos + yStartPos) / 2;
		double xRad = (xEndPos - xStartPos) / 2;
		double yRad = (yEndPos - yStartPos) / 2;

		Ellipse oval = new Ellipse(xCenter, yCenter, xRad, yRad);
		oval.setFill(ovalColor);
		oval.setStroke(outline);
		oval.setRotate(rotation);

		group.getChildren().add(oval);
	}

	public void drawCircle(float xStartPos, float yStartPos, float radius, Color circleColor, Color outline) {
		/*
		 * TODO Write a method that draws a circle depending on params passed,
		 * calling drawOval
		 */
		double xCenter = (xStartPos + radius) / 2;
		double yCenter = (yStartPos + radius) / 2;

		Circle circle = new Circle(xCenter, yCenter, radius);
		circle.setFill(circleColor);
		circle.setStroke(outline);

		group.getChildren().add(circle);
	}

	public void drawRectangle(float xStart, float yStart, float xEnd, float yEnd, float arcWidth, float arcHeight,
			Color rectColor, Color outline) {
		/*
		 * TODO Write a method that draws a rectangle depending on params
		 * passed. Must be able to do rounded corners (look up arkheight and
		 * arkwidth javafx)
		 */
		Rectangle rectangle = new Rectangle(xStart, yStart, xEnd - xStart, yEnd - yStart);
		rectangle.setFill(rectColor);
		rectangle.setStroke(outline);
		group.getChildren().add(rectangle);

	}

	public void drawSquare(float xStart, float yStart, float length, Color squareColor, Color outline) {
		/*
		 * TODO Write a method that draws a square depending on params passed,
		 * calling drawRectangle. Must be able to do rounded corners (look up
		 * arkheight and arkwidth javafx)
		 */
		Rectangle square = new Rectangle(xStart, yStart, length, length);
		square.setFill(squareColor);
		square.setStroke(outline);
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

	public void drawArrow() {

	}

	public void drawEquiTriangle() {
		/*
		 * TODO Write a method that draws an equilateral triangle depending on
		 * params passed.
		 */

	}

	public void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3, Color triangleColor,
			Color outline) {
		/*
		 * TODO Write a method that draws a triangle depending on params passed.
		 * Takes 3 sets of coordinates so any triangle can be made.
		 */
		Polygon triangle = new Polygon();
		triangle.setFill(triangleColor);
		triangle.setStroke(outline);
		triangle.getPoints().addAll(new Double[] { x1, y1, x2, y2, x3, y3 });

		group.getChildren().add(triangle);

	}

	public void drawRegularPolygon(float Width, float Height, double x, double y, int numberOfSides, Color regPolColor,
			Color outline) {
		/*
		 * TODO Write a method that draws a regular polygon of n sides.
		 */
		Polygon regPolygon = new Polygon();
		regPolygon.setFill(regPolColor);
		regPolygon.setStroke(outline);
		double radius = (double) (Math.min(0.5 * Width, Height * 0.5));
		double z;
		for (int i = 0; i < numberOfSides; i++) {
			z = ((i * 2 * Math.PI) / numberOfSides);
			regPolygon.getPoints().add(((double) Math.round((x + (radius) * Math.sin(z)) + (radius))));
			regPolygon.getPoints().add(((double) Math.round((y + (-radius) * Math.cos(z)) + (radius))));

		}
		group.getChildren().add(regPolygon);
	}

	public void drawPolygon(Color polygonColor, Double points[], float x, float y, Color outline) {
		/*
		 * TODO Write a method that draws ANY polygon given an array of
		 * coordinates. Investigate variable arguments in java (varargs).
		 */
		Polygon polygon = new Polygon();
		polygon.setFill(polygonColor);
		polygon.setStroke(outline);
		for (int i = 0; i < points.length; i++) {
			;
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

	public void drawChord(float centerX, float centerY, float radiusX, float radiusY, float arcAngle, float Length,
			Color chordColor, Color outline) {

		Arc chord = new Arc(centerX, centerY, radiusX, radiusY, arcAngle, Length);
		chord.setType(ArcType.CHORD);
		chord.setFill(chordColor);
		chord.setStroke(outline);

		group.getChildren().add(chord);

	}

	public void drawArc(float centerX, float centerY, float radiusX, float radiusY, float arcAngle, float Length,
			Color arcColor, Color outline) {

		Arc arc = new Arc(centerX, centerY, radiusX, radiusY, arcAngle, Length);
		arc.setType(ArcType.ROUND);
		arc.setFill(arcColor);
		arc.setStroke(outline);

		group.getChildren().add(arc);

	}

	public void drawPieChart(float x, float y, double r, float n, Double digits[], String names[], Color colors) {
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
