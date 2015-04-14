package graphsHandler;

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
	 * Constructor for the graphsHandler.
	 * 
	 * @param group
	 *            The group that all of the graphs are to be drawn to.
	 */
	public GraphsHandler(Group group) {
		this.group = group;
	}
	
	public void drawPieChart(PieChartObject pieChartObject) {
		ObservableList<PieChart.Data> pieChartData = pieChartObject.getPieChartData();
		String title = pieChartObject.getTitle();
		float xStartPos = pieChartObject.getxStartPos();
		float yStartPos = pieChartObject.getyStartPos();
		float scale = pieChartObject.getScale();
		
		PieChart pChart = new PieChart(pieChartData);
		pChart.setTitle(title);
		pChart.setLayoutX(xStartPos);
		pChart.setLayoutY(yStartPos);
		pChart.setScaleX(scale);
		pChart.setScaleY(scale);
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
