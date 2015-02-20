package GUI;

import java.awt.font.GlyphMetrics;
import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class BindingsConcatTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		/*
		 * FlowPane flow = new FlowPane(); flow.setVgap(1); flow.setHgap(4);
		 * flow.setPrefWrapLength(100); // preferred width = 300
		 * flow.getChildren().addAll(label, label2);
		 */

		final HTMLEditor htmlEditor = new HTMLEditor();

		htmlEditor.setPrefSize(400, 300);
		htmlEditor.relocate(100, 100);

		htmlEditor.setHtmlText(" <p>This text contains <sup>superscript</sup> text.</p> ");

		final WebView webView = new WebView();
		webView.setPrefSize(200, 200);
		
		File f = new File("custom.css");
		try {
		    webView.getEngine().setUserStyleSheetLocation(f.toURI().toURL().toString());
		} catch (MalformedURLException ex) {
		    // not important here
		}
		
		webView.setDisable(true);
		webView.getEngine()
				.loadContent(
						"<body overflow-x: hidden; bgcolor=rgba(0, 0, 204, 0); contenteditable=\"false\"><p style=\"text-align: left;\"><sub>subscript</sub><sup>superscript</sup><font face=\"arial\" color=\"ff0000\"> color arial </font><span style=\"opacity:0.1\">opacity </span><span style=\"font-size:20px\">size 20 </span><span style=\"font-size:30px\">size 30 </span><p style=\"text-align: right;\">p tag alligned right </p><strike>strikethrough </strike> hjakfsdlhjlksadf hjklfdsa hjklsfd hjlkfds hjklfds hjklfdsah jlksdfah jlksafdh jlkfdsa hjlksfda hjkldfsah jlkfdsah  <a href=\"http://www.facebook.com\">link text</a> </p></body>");
		
		Font thisFont = new Font("arial", 20);
		
		
		// <html> </html> -- not required
		// <body bgcolor=\"#efe7b5\"; contenteditable=\"false\">STUFF GOES HERE </body>
		// <span style=\"opacity:0.1\">TEXT GOES HERE</span>
		// <font color=\"ff0000\">contains</font>
		// <p style=\"text-align: left;\"> STUFF GOES HERE </p>
		
		webView.relocate(100, 100);
		webView.setContextMenuEnabled(false);

		Button btn = new Button();
		btn.setText("Submit");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				webView.getEngine().loadContent(htmlEditor.getHtmlText());
				System.out.println(htmlEditor.getHtmlText());
			}
		});

		btn.relocate(500, 50);

		Group root = new Group();
		root.getChildren().addAll(webView);//, htmlEditor, btn);

		Scene scene = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}