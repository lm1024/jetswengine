/**
 * 
 */
package GUI;

import java.util.List;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
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

	private void colorShape(Shape shape, boolean solid, Color shapeColor, Color outlineColor, double outlineThickness,
			Shadow shadowType, Shading shadingType, Color... shadingColors) {
		if (solid) {
			Stop[] stops = null;
			/* Code to generate stops for if a shading type is to be used. */
			if (shadingType != Shading.NONE) {
				/*
				 * Builds an array of stops evenly spaced through the shape
				 * consisting of the varargs of shadingColors. The last color in
				 * the varargs is the outer shading color.
				 */
				stops = new Stop[shadingColors.length + 1];
				/* Add the shape color as the central color */
				stops[0] = new Stop(0, shapeColor);
				/* Loop through the varargs adding the colors to the stop array */
				for (int i = 1; i <= shadingColors.length; i++)
					stops[i] = (new Stop((float) i / shadingColors.length, shadingColors[i - 1]));
			}

			/*
			 * Switch to set different types of shading using the stops created
			 * above
			 */
			switch (shadingType) {
			case CYCLIC:
				shape.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops));
				break;
			case HORIZONTAL:
				shape.setFill(new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE, stops));
				break;
			case VERTICAL:
				shape.setFill(new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE, stops));
				break;
			case NONE:
				/* Falls through */
			default:
				shape.setFill(shapeColor);
				break;
			}
		}

		/* Set the outline parameters */
		shape.setStroke(outlineColor);
		shape.setStrokeWidth(outlineThickness);

		/* Switch for the different allowed types of shadow */
		switch (shadowType) {
		case LIGHT:
			shape.setEffect(new DropShadow(10, 2, 2, Color.BLACK));
			break;
		case NORMAL:
			shape.setEffect(new DropShadow(20, 3, 3, Color.BLACK));
			break;
		case HEAVY:
			shape.setEffect(new DropShadow(30, 5, 5, Color.BLACK));
			break;
		case NONE:
			break;
		default:
			System.err.println("Shadow type not recognised.");
			break;
		}

	}

	public void drawOval(float xStartPos, float yStartPos, float xEndPos, float yEndPos, Color ovalColor,
			boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType, float rotation,
			Shading shadingType, Color... shadingColors) {
		double xCenter = (xEndPos + xStartPos) / 2;
		double yCenter = (yEndPos + yStartPos) / 2;
		double xRad = (xEndPos - xStartPos) / 2;
		double yRad = (yEndPos - yStartPos) / 2;

		Ellipse oval = new Ellipse(xCenter, yCenter, xRad, yRad);
		oval.setRotate(rotation);

		colorShape(oval, solid, ovalColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);

		group.getChildren().add(oval);
	}

	public void drawCircle(float xStartPos, float yStartPos, float radius, Color circleColor, boolean solid,
			Color outlineColor, double outlineThickness, Shadow shadowType, Shading shadingType, Color... shadingColors) {
		double xCenter = (xStartPos + radius);
		double yCenter = (yStartPos + radius);

		Circle circle = new Circle(xCenter, yCenter, radius);

		colorShape(circle, solid, circleColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);

		group.getChildren().add(circle);
	}

	public void drawRectangle(float xStart, float yStart, float xEnd, float yEnd, float arcWidth, float arcHeight,
			Color rectangleColor, boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType,
			float rotation, Shading shadingType, Color... shadingColors) {
		/*
		 * TODO rounded corners (look up arkheight and arkwidth javafx)
		 */
		Rectangle rectangle = new Rectangle(xStart, yStart, xEnd - xStart, yEnd - yStart);
		rectangle.setRotate(rotation);

		colorShape(rectangle, solid, rectangleColor, outlineColor, outlineThickness, shadowType, shadingType,
				shadingColors);

		group.getChildren().add(rectangle);

	}

	public void drawSquare(float xStart, float yStart, float length, Color squareColor, boolean solid,
			Color outlineColor, double outlineThickness, Shadow shadowType, float rotation, Shading shadingType,
			Color... shadingColors) {
		/*
		 * TODO rounded corners (look up arkheight and arkwidth javafx)
		 */
		drawRectangle(xStart, yStart, xStart + length, yStart + length, 0, 0, squareColor, solid, outlineColor,
				outlineThickness, shadowType, rotation, shadingType, shadingColors);
	}

	public void drawLine(float startX, float startY, float endX, float endY, Color lineColor, Shading shadingType,
			Color... shadingColors) {
		/*
		 * TODO Write a method that draws a line depending on params passed.
		 * Must be able to also do arrows!
		 */
		Line line = new Line(startX, startY, endX, endY);

		Stop[] stops = null;
		/* Code to generate stops for if a shading type is to be used. */
		if (shadingType != Shading.NONE) {
			/*
			 * Builds an array of stops evenly spaced through the shape
			 * consisting of the varargs of shadingColors. The last color in the
			 * varargs is the outer shading color.
			 */
			stops = new Stop[shadingColors.length + 1];
			/* Add the shape color as the central color */
			stops[0] = new Stop(0, lineColor);
			/* Loop through the varargs adding the colors to the stop array */
			for (int i = 1; i <= shadingColors.length; i++)
				stops[i] = (new Stop((float) i / shadingColors.length, shadingColors[i - 1]));
			line.setStroke(new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE, stops));
		} else
			line.setStroke(lineColor);

		group.getChildren().add(line);

	}

	public void drawArrow(float startX, float startY, float endX, float endY, Color arrowColor, Shading shadingType,
			Color... shadingColors) {

		int arrowHeadSide = 15;
		double arrowHeadHeight = (arrowHeadSide * (Math.sqrt(3) / (2.0)));

		// to work out the angle of rotation
		double dx = Math.abs(endX - startX);
		double dy = Math.abs(endY - startY);
		int length = (int) Math.sqrt((dx * dx + dy * dy));

		double x1 = endX;
		double y1 = endY - (arrowHeadHeight / 2);
		double x2 = endX + (arrowHeadSide / 2);
		double y2 = endY + (arrowHeadHeight / 2);
		double x3 = endX - (arrowHeadSide / 2);
		double y3 = endY + (arrowHeadHeight / 2);
		;

		// new arrow
		Line arrow = new Line(startX, startY, endX, endY);
		// arrow.setFill(arrowColor);
		// arrow.setStroke(arrowColor);

		Polygon arrowHead = new Polygon();
		// arrowHead.setFill(arrowColor);
		arrowHead.getPoints().addAll(new Double[] { x1, y1, x2, y2, x3, y3 });

		double rotation = 0.0;
		if ((endX > startX) && (endY > startY)) { // bottom right
			rotation = (90 + Math.toDegrees(Math.asin(dy / length)));
		} else if ((endX == startX) && (endY > startY)) {
			rotation = 180;
		} else if ((endX < startX) && (endY == startY)) {
			rotation = 270;
		} else if ((endX > startX) && (endY == startY)) {
			rotation = 90;
		} else if ((endX < startX) && (endY > startY)) { // bottom left
			rotation = (180 + Math.toDegrees(Math.asin(dx / length)));
		} else if ((endX < startX) && (endY < startY)) { // top right
			rotation = (270 + Math.toDegrees(Math.asin(dy / length)));
		} else if ((endX > startX) && (endY < startY)) { // top left
			rotation = (Math.toDegrees(Math.asin(dx / length)));
		}

		arrowHead.setRotate(rotation);

		Stop[] stops = null;
		/* Code to generate stops for if a shading type is to be used. */
		if (shadingType != Shading.NONE) {
			/*
			 * Builds an array of stops evenly spaced through the shape
			 * consisting of the varargs of shadingColors. The last color in the
			 * varargs is the outer shading color.
			 */
			stops = new Stop[shadingColors.length + 1];
			/* Add the shape color as the central color */
			stops[0] = new Stop(0, arrowColor);
			/* Loop through the varargs adding the colors to the stop array */
			for (int i = 1; i <= shadingColors.length; i++)
				stops[i] = (new Stop((float) i / shadingColors.length, shadingColors[i - 1]));
			arrow.setStroke(new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE, stops));
			arrowHead.setFill(new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE, stops));
		} else {
			arrow.setStroke(arrowColor);
			arrowHead.setFill(arrowColor);
		}

		group.getChildren().add(arrow);
		group.getChildren().add(arrowHead);
	}

	public void drawEquiTriangle() {
		/*
		 * TODO Write a method that draws an equilateral triangle depending on
		 * params passed.
		 */

	}

	public void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3, Color triangleColor,
			boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType, float rotation,
			Shading shadingType, Color... shadingColors) {
		/*
		 * TODO Write a method that draws a triangle depending on params passed.
		 * Takes 3 sets of coordinates so any triangle can be made.
		 */
		Polygon triangle = new Polygon();
		colorShape(triangle, solid, triangleColor, outlineColor, outlineThickness, shadowType, shadingType,
				shadingColors);
		triangle.getPoints().addAll(new Double[] { x1, y1, x2, y2, x3, y3 });

		group.getChildren().add(triangle);

	}

	public void drawRegularPolygon(double x, double y, float Width, float Height, int numberOfSides, Color regPolColor,
			boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType, float rotation,
			Shading shadingType, Color... shadingColors) {
		/*
		 * TODO Write a method that draws a regular polygon of n sides.
		 */
		Polygon regPolygon = new Polygon();
		colorShape(regPolygon, solid, regPolColor, outlineColor, outlineThickness, shadowType, shadingType,
				shadingColors);
		double radius = (double) (Math.min(0.5 * Width, Height * 0.5));
		double z;
		for (int i = 0; i < numberOfSides; i++) {
			z = ((i * 2 * Math.PI) / numberOfSides);
			regPolygon.getPoints().add(((double) Math.round((x + (radius) * Math.sin(z)) + (radius))));
			regPolygon.getPoints().add(((double) Math.round((y + (-radius) * Math.cos(z)) + (radius))));

		}
		group.getChildren().add(regPolygon);
	}

	public void drawPolygon(Double points[], float x, float y, Color polygonColor, boolean solid, Color outlineColor,
			double outlineThickness, Shadow shadowType, float rotation, Shading shadingType, Color... shadingColors) {
		/*
		 * TODO Write a method that draws ANY polygon given an array of
		 * coordinates. Investigate variable arguments in java (varargs).
		 */
		Polygon polygon = new Polygon();
		colorShape(polygon, solid, polygonColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);
		for (int i = 0; i < points.length; i++) {
			;
			points[i] += x;
			i++;
			points[i] += y;
		}
		polygon.getPoints().addAll(points);
		group.getChildren().add(polygon);
	}

	public void drawStar(double midX, double midY, int numPoints, float size, Color starColor, boolean solid,
			Color outlineColor, double outlineThickness, Shadow shadowType, float rotation, Shading shadingType,
			Color... shadingColors) {
		int a = 0;
		double z = 0;

		Polygon star = new Polygon();
		colorShape(star, solid, starColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);

		while (a < ((2 * numPoints) + 1)) {
			z = ((a * Math.PI) / numPoints);
			if ((a % 2 == 0)) {
				star.getPoints().add((double) Math.round(((size / 2) * Math.sin(z)) + midX + (size / 2)));
				star.getPoints().add((double) Math.round(((-size / 2) * Math.cos(z)) + midY + (size / 2)));
			} else {
				star.getPoints()
						.add((double) Math.round(((size / (2 * Math.pow(numPoints, 0.5))) * Math.sin(z)) + midX
								+ (size / 2)));
				star.getPoints().add(
						(double) Math.round(((-size / (2 * Math.pow(numPoints, 0.5))) * Math.cos(z)) + midY
								+ (size / 2)));
			}
			a++;
		}

		group.getChildren().add(star);
	}

	public void drawChord(float centerX, float centerY, float radiusX, float radiusY, float arcAngle, float Length,
			Color chordColor, boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType,
			float rotation, Shading shadingType, Color... shadingColors) {

		Arc chord = new Arc(centerX, centerY, radiusX, radiusY, arcAngle, Length);
		chord.setType(ArcType.CHORD);
		colorShape(chord, solid, chordColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);

		group.getChildren().add(chord);

	}

	public void drawArc(float centerX, float centerY, float radiusX, float radiusY, float arcAngle, float Length,
			Color arcColor, boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType,
			float rotation, Shading shadingType, Color... shadingColors) {

		Arc arc = new Arc(centerX, centerY, radiusX, radiusY, arcAngle, Length);
		arc.setType(ArcType.ROUND);
		colorShape(arc, solid, arcColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);

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
