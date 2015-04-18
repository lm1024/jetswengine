package graphsHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class GraphsHandler {
	
	private Group group;
	
	/**
	 * Constructor for the graphicsHandler.
	 * 
	 * @param group
	 *            The group that all of the graphics are to be drawn to.
	 */
	public GraphsHandler(Group group) {
		this.group = group;
	}
	
	public void drawPieChart(int x, int y, float size, String title, String[] dataNames, float[] dataValues) {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (int i = 0; i < dataValues.length; i++) {
			pieChartData.add(new PieChart.Data(dataNames[i], dataValues[i]));
		}
		PieChart pChart = new PieChart(pieChartData);
		pChart.setTitle(title);
		pChart.setLayoutX(x);
		pChart.setLayoutY(y);
		pChart.setScaleX(size);
		pChart.setScaleY(size);
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
