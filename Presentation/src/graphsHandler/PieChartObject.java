package graphsHandler;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

public class PieChartObject {
	
	private final int maxDataNameLength = 70;

	private String title;

	private float xStartPos;
	private float yStartPos;
	private float scale;

	private ObservableList<PieChart.Data> pieChartData;

	public PieChartObject() {
		pieChartData = FXCollections.observableArrayList();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getxStartPos() {
		return xStartPos;
	}

	public void setxStartPos(float xStartPos) {
		this.xStartPos = xStartPos;
	}

	public float getyStartPos() {
		return yStartPos;
	}

	public void setyStartPos(float yStartPos) {
		this.yStartPos = yStartPos;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setPieChartData(String dataName, float dataValue) {
		if ((dataValue != Float.NaN) && (dataValue != Float.POSITIVE_INFINITY)
									 && (dataValue != Float.NEGATIVE_INFINITY) 
									 && (dataName.length() <= maxDataNameLength)) {
			pieChartData.add(new PieChart.Data(dataName, dataValue));
		}
	}

	public void setPieChartData(ArrayList<String> dataNames, ArrayList<Float> dataValues) {
		int smallestSet = Math.min(dataNames.size(), dataValues.size());

		for (int i = 0; i < smallestSet; i++) {
			setPieChartData(dataNames.get(i), dataValues.get(i));
		}
	}

	public  ObservableList<Data> getPieChartData() {
		return pieChartData;
	}

}
