package graphsHandler;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class GraphHandler {

	private Group group;

	/**
	 * Constructor for the graphsHandler.
	 * 
	 * @param group
	 *            The group that all of the graphs are to be drawn to.
	 */
	public GraphHandler(Group group) {
		this.group = group;
		/*
		 * Initialises a new pie chart. The first instantiate of a pie chart
		 * takes 100ms, so this moves that delay to the start of the handler
		 * instead of the first graph being drawn, which is preferential.
		 */
		new PieChart(null);
	}

	/**
	 * Draws a pie chart on the group passed to the constructor of the handler.
	 * 
	 * @param pieChartObject
	 *            A PieChartObject containing all the data of the chart to be
	 *            drawn.
	 * @see {@link graphsHandler.PieChartObject}
	 */
	public void drawPieChart(PieChartObject pieChartObject) {
		ObservableList<PieChart.Data> pieChartData = pieChartObject.getPieChartData();
		String title = pieChartObject.getTitle();
		float xStartPos = pieChartObject.getxStartPos();
		float yStartPos = pieChartObject.getyStartPos();
		double prefWidth = pieChartObject.getPrefWidth();
		double prefHeight = pieChartObject.getPrefHeight();

		PieChart pChart = new PieChart(pieChartData);
		pChart.setTitle(title);
		pChart.setStyle(".chart-title { -fx-text-fill: #ffffff; -fx-font-size: 2em; }");
		pChart.setLayoutX(xStartPos);
		pChart.setLayoutY(yStartPos);
		pChart.setPrefSize(prefWidth, prefHeight);

		group.getChildren().add(pChart);
	}

	@SuppressWarnings("unchecked")
	// safe
	public void drawBarChart(int x, int y, float size, String title, String xLabel, String yLabel, String[] xLabels,
			float[] yValues) {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart<String, Number> bChart = new BarChart<String, Number>(xAxis, yAxis);
		bChart.setTitle(title);
		xAxis.setLabel(xLabel);
		yAxis.setLabel(yLabel);
		XYChart.Series<String, Number> series1 = new Series<String, Number>();
		series1.setName("series1");
		for (int i = 0; i < yValues.length; i++) {
			series1.getData().add(new XYChart.Data<String, Number>(xLabels[i], yValues[i]));
		}
		bChart.getData().addAll(series1);
		bChart.setLayoutX(x);
		bChart.setLayoutY(y);
		bChart.setScaleX(size);
		bChart.setScaleY(size);
		bChart.setRotate(90);
		group.getChildren().add(bChart);
	}

}
