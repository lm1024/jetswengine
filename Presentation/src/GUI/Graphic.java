package GUI;

import java.util.ArrayList;

public class Graphic {
	protected final static float defaultSize = 200;

	private GraphicType graphic;
	private float xStartPos;
	private float yStartPos;

	private String color;
	private float xEndPos;
	private float yEndPos;
	private boolean solid;
	private String outlineColor;
	private double outlineThickness;
	private float rotation;
	private float radius;
	private float arcWidth;
	private float arcHeight;
	private float size;
	private float width;
	private float height;
	private int numberOfSides;
	private int numberOfPoints;
	private float x1;
	private float x2;
	private float x3;
	private float y1;
	private float y2;
	private float y3;
	private ArrayList<Float> xCoordinates;
	private ArrayList<Float> yCoordinates;
	private float arcAngle;
	private float length;
	private Shadow shadow;
	private Shading shadingType;
	private ArrayList<String> shadingColors;
	private ArrayList<Float> stops;

	private Graphic(GraphicBuilder builder) {
		graphic = builder.graphic;
		xStartPos = builder.xStartPos;
		yStartPos = builder.yStartPos;
		color = builder.color;
		xEndPos = builder.xEndPos;
		yEndPos = builder.yEndPos;
		solid = builder.solid;
		outlineColor = builder.outlineColor;
		outlineThickness = builder.outlineThickness;
		rotation = builder.rotation;
		radius = builder.radius;
		arcWidth = builder.arcWidth;
		arcHeight = builder.arcHeight;
		size = builder.size;
		width = builder.width;
		height = builder.height;
		numberOfSides = builder.numberOfSides;
		numberOfPoints = builder.numberOfPoints;
		x1 = builder.x1;
		x2 = builder.x2;
		x3 = builder.x3;
		y1 = builder.y1;
		y2 = builder.y2;
		y3 = builder.y3;
		// Any number of points
		xCoordinates = builder.xCoordinates;
		yCoordinates = builder.yCoordinates;
		arcAngle = builder.arcAngle;
		length = builder.length;
		shadow = builder.shadow;
		shadingType = builder.shadingType;
		shadingColors = builder.shadingColors;
		stops = builder.stops;
	}

	public GraphicType getGraphic() {
		return graphic;
	}

	public float getxStartPos() {
		return xStartPos;
	}

	public float getyStartPos() {
		return yStartPos;
	}

	public String getColor() {
		return color;
	}

	public float getxEndPos() {
		return xEndPos;
	}

	public float getyEndPos() {
		return yEndPos;
	}

	public boolean isSolid() {
		return solid;
	}

	public String getOutlineColor() {
		return outlineColor;
	}

	public double getOutlineThickness() {
		return outlineThickness;
	}

	public float getRotation() {
		return rotation;
	}

	public float getRadius() {
		return radius;
	}

	public float getArcWidth() {
		return arcWidth;
	}

	public float getArcHeight() {
		return arcHeight;
	}

	public float getSize() {
		return size;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public int getNumberOfSides() {
		return numberOfSides;
	}

	public int getNumberOfPoints() {
		return numberOfPoints;
	}

	public float getX1() {
		return x1;
	}

	public float getX2() {
		return x2;
	}

	public float getX3() {
		return x3;
	}

	public float getY1() {
		return y1;
	}

	public float getY2() {
		return y2;
	}

	public float getY3() {
		return y3;
	}

	public ArrayList<Float> getxCoordinates() {
		return xCoordinates;
	}

	public ArrayList<Float> getyCoordinates() {
		return yCoordinates;
	}

	public float getArcAngle() {
		return arcAngle;
	}

	public float getLength() {
		return length;
	}

	public Shadow getShadow() {
		return shadow;
	}

	public Shading getShadingType() {
		return shadingType;
	}

	public ArrayList<String> getShadingColors() {
		return shadingColors;
	}

	public ArrayList<Float> getStops() {
		return stops;
	}

	public static class GraphicBuilder {
		private final GraphicType graphic;
		private final float xStartPos;
		private final float yStartPos;

		private String color = "#ffffffff";
		private float xEndPos;
		private float yEndPos;
		private boolean solid;
		private String outlineColor = "#00000000";
		private double outlineThickness = 1;
		private float rotation;
		private float radius = defaultSize;
		private float arcWidth;
		private float arcHeight;
		private float size = defaultSize;
		private float width = defaultSize;
		private float height = defaultSize;
		private int numberOfSides;
		private int numberOfPoints;

		// Setcoordinates
		private float x1;
		private float x2;
		private float x3;
		private float y1;
		private float y2;
		private float y3;

		// Any number of points
		ArrayList<Float> xCoordinates;
		ArrayList<Float> yCoordinates;

		private float arcAngle;
		private float length;

		private Shadow shadow = Shadow.NONE;

		private Shading shadingType = Shading.NONE;

		ArrayList<String> shadingColors;
		ArrayList<Float> stops;

		// Any number of colors

		public GraphicBuilder(GraphicType graphic, float xStartPos, float yStartPos) {
			shadingColors = new ArrayList<String>();
			stops = new ArrayList<Float>();
			xCoordinates = new ArrayList<Float>();
			yCoordinates = new ArrayList<Float>();

			this.graphic = graphic;
			this.xStartPos = xStartPos;
			this.yStartPos = yStartPos;

			this.xEndPos = xStartPos + defaultSize;
			this.yEndPos = yStartPos + defaultSize;
		}

		public GraphicBuilder color(String color) {
			this.color = color;
			return this;
		}

		public GraphicBuilder xEndPos(Float xEndPos) {
			this.xEndPos = xEndPos;
			return this;
		}

		public GraphicBuilder yEndPos(Float yEndPos) {
			this.yEndPos = yEndPos;
			return this;
		}

		public GraphicBuilder solid(boolean solid) {
			this.solid = solid;
			return this;
		}

		public GraphicBuilder outlineColor(String outlineColor) {
			this.outlineColor = outlineColor;
			return this;
		}

		public GraphicBuilder outlineThickness(Double outlineThickness) {
			this.outlineThickness = outlineThickness;
			return this;
		}

		public GraphicBuilder rotation(float rotation) {
			this.rotation = rotation;
			return this;
		}

		public GraphicBuilder radius(float radius) {
			this.radius = radius;
			return this;
		}

		public GraphicBuilder arcWidth(float arcWidth) {
			this.arcWidth = arcWidth;
			return this;
		}

		public GraphicBuilder arcHeight(float arcHeight) {
			this.arcHeight = arcHeight;
			return this;
		}

		public GraphicBuilder size(float size) {
			this.size = size;
			return this;
		}

		public GraphicBuilder width(float width) {
			this.width = width;
			return this;
		}

		public GraphicBuilder height(float height) {
			this.height = height;
			return this;
		}

		public GraphicBuilder numberOfSides(int numberOfSides) {
			this.numberOfSides = numberOfSides;
			return this;
		}

		public GraphicBuilder numberOfPoints(int numberOfPoints) {
			this.numberOfPoints = numberOfPoints;
			return this;
		}

		public GraphicBuilder triangleCoordinates(float x1, float y1, float x2, float y2, float x3, float y3) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.x3 = x3;
			this.y3 = y3;
			return this;
		}

		public GraphicBuilder polygonCoordinate(float xCoordinate, float yCoordinate) {
			xCoordinates.add(xCoordinate);
			yCoordinates.add(yCoordinate);
			return this;
		}

		public GraphicBuilder arcAngle(float arcAngle) {
			this.arcAngle = arcAngle;
			return this;
		}

		public GraphicBuilder length(float length) {
			this.length = length;
			return this;
		}

		public GraphicBuilder shadow(Shadow shadow) {
			this.shadow = shadow;
			return this;
		}

		public GraphicBuilder shadingType(Shading shadingType) {
			this.shadingType = shadingType;
			return this;
		}

		public GraphicBuilder shadingElement(String shadingColor, float stop) {
			shadingColors.add(shadingColor);
			stops.add(stop);
			return this;
		}

		public Graphic build() {
			return new Graphic(this);
		}
	}
}
