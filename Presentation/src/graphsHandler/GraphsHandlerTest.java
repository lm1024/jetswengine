package graphsHandler;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphsHandlerTest extends Application {

	private Group group;
	private GraphHandler graphHandler;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("JavaFX Welcome");
		group = new Group();
		Scene scene = new Scene(group, 500, 500);
		primaryStage.setScene(scene);

		graphHandler = new GraphHandler(group);
		
		PieChartObject pieChartObject = new PieChartObject();
		pieChartObject.setTitle("Graph");
		pieChartObject.setxStartPos(100);
		pieChartObject.setyStartPos(100);
		pieChartObject.setScale(1);

		ArrayList<String> dataNames = new ArrayList<String>();
		dataNames.add("a");
		dataNames.add("b");
		dataNames.add("c");
		dataNames.add("d");

		ArrayList<Float> dataValues = new ArrayList<Float>();
		for (int i = 0; i < 4; i++)
			dataValues.add((float) 25);

		pieChartObject.setPieChartData(dataNames, dataValues);

		graphHandler.drawPieChart(pieChartObject);
		
		primaryStage.show();
	}
}
