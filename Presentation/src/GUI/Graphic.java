package GUI;

import java.util.ArrayList;

public class Graphic {
	private GraphicType graphic;
	private float xStart;
	private float yStart;

	private String graphicColor;
	private float xEnd;
	private float yEnd;
	private boolean solid;
	private String outlineColor;
	private double outlineThickness;
	private float rotation;
	private float arcwidth;
	private float archeight;
	private float size;
	private float width;
	private float height;
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

	private float arcangle;
	private float length;

	private Shadow shadow;

	private Shading shadingType;

	ArrayList<String> shadingColors;
	ArrayList<Float> stops;

	// Any number of colors

	private Graphic(GraphicBuilder builder) {
		GraphicType graphic = builder.graphic;
		float xStart = builder.xStart;
		float yStart = builder.yStart;
		String graphicColor = builder.graphicColor;
		float xEnd = builder.xEnd;
		float yEnd = builder.yEnd;
		boolean solid = builder.solid;
		String outlineColor = builder.outlineColor;;
		double outlineThickness = builder.outlineThickness;
		float rotation = builder.rotation;
		float arcwidth = builder.arcwidth;
		float archeight = builder.archeight;
		float size = builder.size;
		float width = builder.width;
		float height = builder.height;
		int numberOfSides = builder.numberOfSides;
		int numberOfPoints = builder.numberOfPoints;
		// Setcoordinates
		float x1 = builder.x1;
		float x2 = builder.x2;
		float x3 = builder.x3;
		float y1 = builder.y1;
		float y2 = builder.y2;
		float y3 = builder.y3;
		// Any number of points
		ArrayList<Float> xCoordinates = builder.xCoordinates;
		ArrayList<Float> yCoordinates = builder.yCoordinates;
		float arcangle = builder.arcangle;
		float length = builder.length;
		Shadow shadow = builder.shadow;
		Shading shadingType = builder.shadingType;
		ArrayList<String> shadingColors = builder.shadingColors;
		ArrayList<Float> stops = builder.stops;
		// Any number of colors
	}

	public static class GraphicBuilder {
		private final GraphicType graphic;
		private final float xStart;
		private final float yStart;

		private String graphicColor;
		private float xEnd;
		private float yEnd;
		private boolean solid;
		private String outlineColor;
		private double outlineThickness;
		private float rotation;
		private float arcwidth;
		private float archeight;
		private float size;
		private float width;
		private float height;
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

		private float arcangle;
		private float length;

		private Shadow shadow;

		private Shading shadingType;

		ArrayList<String> shadingColors;
		ArrayList<Float> stops;

		// Any number of colors

		public GraphicBuilder(GraphicType graphic, float xStart, float yStart) {
			shadingColors = new ArrayList<String>();
			stops = new ArrayList<Float>();
			xCoordinates = new ArrayList<Float>();
			yCoordinates = new ArrayList<Float>();

			this.graphic = graphic;
			this.xStart = xStart;
			this.yStart = yStart;
		}

		public GraphicBuilder graphicColor(String graphicColor) {
			this.graphicColor = graphicColor;
			return this;
		}

		public GraphicBuilder xEnd(Float xEnd) {
			this.xEnd = xEnd;
			return this;
		}

		public GraphicBuilder yEnd(Float yEnd) {
			this.yEnd = yEnd;
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

		public GraphicBuilder arcwidth(float arcwidth) {
			this.arcwidth = arcwidth;
			return this;
		}

		public GraphicBuilder archeight(float archeight) {
			this.archeight = archeight;
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

		public GraphicBuilder arcangle(float arcangle) {
			this.arcangle = arcangle;
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
