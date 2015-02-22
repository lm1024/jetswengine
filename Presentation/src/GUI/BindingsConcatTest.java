package GUI;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.text.DecimalFormat;

import com.sun.webpane.platform.WebPage;

import netscape.javascript.JSException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class BindingsConcatTest extends Application {

	Double height;

	@Override
	public void start(Stage primaryStage) {

		System.out.println(Integer.parseInt("0a", 16));
		System.out.println((Integer.parseInt("0a", 16)) / 255f);

		WebView webView = new WebView();
		webView.setPrefWidth(200);
		webView.setPrefHeight(200);
		
		File f = new File("custom.css");
		try {
			webView.getEngine().setUserStyleSheetLocation(f.toURI().toURL().toString());
		} catch (MalformedURLException ex) {
			// not important here
			System.out.println("custom.css does not exist.");
		}

		webView.setDisable(true);
		webView.getEngine()
				.loadContent(
						"<!DOCTYPE html><html><body><p><span style=\"background-color: hsla(120, 100%, 50%, 0.3)\">This is a text.</span> This is a text. This is a text. This is a text. This is a text. This is a text. This is a text. This is a text. This is a text. </p></body></html>");

		/*
		 * <html> </html> -- not required <div id=\"mydiv\"> </div> -- Used for
		 * calculating height of text... ESSENTIAL <body bgcolor=\"#efe7b5\";
		 * contenteditable=\"false\">STUFFGOESHERE</body> <span
		 * style=\"opacity:0.1\">TEXT GOES HERE</span> <font
		 * color=\"ff0000\">contains</font> <p style=\"text-align:left;\"> STUFF
		 * GOES HERE </p>
		 */

		webView.relocate(100, 100);
		webView.setContextMenuEnabled(true);
		//Double height = new Double(0);

		//adjustHeight(webView);
		//System.out.println(Thread.currentThread().getName());

		/*
		 * Remove the background from the webView panel. Adapted from
		 * http://stackoverflow
		 * .com/questions/12421250/transparent-background-in-
		 * the-webview-in-javafx
		 */
		Field field;
		try {
			field = webView.getEngine().getClass().getDeclaredField("page");
			field.setAccessible(true);
			WebPage page = (WebPage) field.get(webView.getEngine());
			page.setBackgroundColor((new java.awt.Color(0, 0, 0, 0)).getRGB());
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * This works, perhaps just say that backgrounds can be made using
		 * graphics handler?
		 */
		Rectangle rect = new Rectangle(100, 100, webView.getPrefWidth(), 200);
		rect.setFill(new Color(1, 0, 1, 1));

		Group root = new Group();
		root.getChildren().addAll(rect, webView);//rect, webView);

		Scene scene = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Method to resize webView based upon size of the HTML within div tags with
	 * the id mydiv. Adapted from code from
	 * java-no-makanaikata.blogspot.co.uk/2012/10/javafx-webview-size-trick.html
	 */
	private void adjustHeight(final WebView webView) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					/*
					 * Runs Javascript command on the webView to get height of
					 * the text enclosed by the mydiv element.
					 */
					Object result = webView.getEngine().executeScript("document.getElementById('mydiv').offsetHeight");
					if (result instanceof Integer) {
						Integer i = (Integer) result;
						height = new Double(i);
						height = height + 20;
						webView.setPrefHeight(height);
						System.out.println("fin");
					}
				} catch (JSException e) {
					System.out
							.println("Exception occured whilst trying to resize a text box. Possibly caused by badly formed HTML div tag.");
					System.exit(-1);
				}
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}