/** (c) Copyright by WaveMedia. */
package graphsHandler;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

/**
 * Class contains information about a pie chart to be displayed on screen.
 * 
 * @author tjd511
 * @version 1.0 03/03/2015
 */
public class PieChartObject {
	/*
	 * Final int for the maximum length of the string allowed as the IDs for the
	 * items.
	 */
	private final int maxDataNameLength = 70;

	/* Variables to store information about the graph. */
	private String title;
	private float xStartPos;
	private float yStartPos;
	private double prefWidth;
	private double prefHeight;

	private ObservableList<PieChart.Data> pieChartData;

	/**
	 * Constructor for the class.
	 */
	public PieChartObject() {
		pieChartData = FXCollections.observableArrayList();
	}

	/**
	 * @return the title of the graph
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the x start position of the graph
	 */
	public float getxStartPos() {
		return xStartPos;
	}

	/**
	 * @param xStartPos
	 */
	public void setxStartPos(float xStartPos) {
		this.xStartPos = xStartPos;
	}

	/**
	 * @return the y start position of the graph
	 */
	public float getyStartPos() {
		return yStartPos;
	}

	/**
	 * @param yStartPos
	 */
	public void setyStartPos(float yStartPos) {
		this.yStartPos = yStartPos;
	}

	/**
	 * @return the preferred width of the graph
	 */
	public double getPrefWidth() {
		return prefWidth;
	}

	/**
	 * @return the preferred height of the graph
	 */
	public double getPrefHeight() {
		return prefHeight;
	}

	/**
	 * 
	 * Method sets the preferred size of the graph.
	 * 
	 * @param prefWidth
	 *            the preferred width of the graph
	 * @param prefHeight
	 *            the preferred height of the graph
	 */
	public void setPrefSize(double prefWidth, double prefHeight) {
		this.prefWidth = prefWidth;
		this.prefHeight = prefHeight;
	}

	/**
	 * Method adds data to the pie chart object.
	 * 
	 * @param dataName
	 *            The name of the section of the pie chart.
	 * @param dataValue
	 *            The value for this section.
	 */
	public void setPieChartData(String dataName, float dataValue) {
		/*
		 * Ensure that the data is within several bounds so that the chart does
		 * not experiance odd behaviour.
		 */
		if ((dataValue != Float.NaN) && (dataValue != Float.POSITIVE_INFINITY)
				&& (dataValue != Float.NEGATIVE_INFINITY)
				&& (dataName.length() <= maxDataNameLength)) {
			pieChartData.add(new PieChart.Data(dataName, dataValue));
		}
	}

	/**
	 * Method adds data to the pie chart object.
	 * 
	 * @param dataNames
	 *            The names of the sections of the pie chart.
	 * @param dataValues
	 *            The values for the section.
	 */
	public void setPieChartData(ArrayList<String> dataNames, ArrayList<Float> dataValues) {
		int smallestSet = Math.min(dataNames.size(), dataValues.size());

		/*
		 * Ensure that only the amount given by the smallest set of data is
		 * added to the object.
		 */
		for (int i = 0; i < smallestSet; i++) {
			setPieChartData(dataNames.get(i), dataValues.get(i));
		}
	}

	/**
	 * @return the pie chart data
	 */
	public ObservableList<Data> getPieChartData() {
		return pieChartData;
	}

}
