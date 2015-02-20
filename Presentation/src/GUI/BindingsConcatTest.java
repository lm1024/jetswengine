package GUI;

import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class BindingsConcatTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		TextField tf1 = new TextField();
		Label label = new Label();

		label.textProperty().bind(tf1.textProperty());
		// label.setMaxHeight(50);
		label.setMaxWidth(100);
		label.setWrapText(true);
		label.relocate(100, 100);

		// Label label2 = new Label("Test label whoo lots of words");
		// label2.setWrapText(false);
		// label2.relocate(100, 100);

		/*
		 * FlowPane flow = new FlowPane(); flow.setVgap(1); flow.setHgap(4);
		 * flow.setPrefWrapLength(100); // preferred width = 300
		 * flow.getChildren().addAll(label, label2);
		 */

		HTMLEditor htmlEditor = new HTMLEditor();
		htmlEditor.setPrefSize(200, 100);
		htmlEditor.relocate(100, 100);
		htmlEditor.setStyle("-fx-border: transparent;" + "-top-toolbar: transparant");
		
		highlight(htmlEditor);

		htmlEditor.setHtmlText(" <p>This text contains <sup>superscript</sup> text.</p> ");

		// flow.relocate(0, 50);

		WebView webView = new WebView();
		webView.setPrefSize(200, 200);
		webView.getEngine().loadContent("<html><body bgcolor=#000000></body></html>");
		webView.relocate(100, 220);
		
		Group root = new Group();
		root.getChildren().addAll(tf1, htmlEditor, webView);
		
		

		Scene scene = new Scene(root, 250, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
		Node a = htmlEditor.lookup(".tool-bar");
		dump(a);
		/* Black magic */
		for (Node toolBar = htmlEditor.lookup(".tool-bar"); toolBar != null; toolBar = htmlEditor.lookup(".tool-bar")) {
			System.out.println(toolBar);
			((Pane) toolBar.getParent()).getChildren().remove(toolBar);
		}
	}
	
	/** Debugging routine to dump the scene graph. */
    public static void dump(Node n) {
        dump(n, 0);
    }

    private static void dump(Node n, int depth) {
        for (int i = 0; i < depth; i++) System.out.print("  ");
        System.out.println(n);
        if (n instanceof Parent) {
            for (Node c : ((Parent) n).getChildrenUnmodifiable()) {
                dump(c, depth + 1);
            }
        }
    }

    /** Debugging routine to highlight the borders of nodes. **/
    public static void highlight(Node n) {
        highlight(n, 0);
    }

    private static void highlight(Node n, int depth) {
        n.setStyle("-fx-stroke: red; -fx-stroke-width: 1; -fx-stroke-type: inside;");
        if (n instanceof Parent) {
            for (Node c : ((Parent) n).getChildrenUnmodifiable()) {
                highlight(c, depth + 1);
            }
        }
    }


	public static void main(String[] args) {
		launch(args);
	}

}