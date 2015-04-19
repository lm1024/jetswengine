package runtime;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Data.Question;
import Data.Slide;
import Data.Slideshow;

public class RuntimeDataWithGraphTest extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Must must implement the inherited abstract method
	 * Application.start(Stage).
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		/* Set the title of the window */
		primaryStage.setTitle(this.getClass().getSimpleName());
		Group group = new Group();
		Scene scene = new Scene(group, 800, 800);
		primaryStage.setScene(scene);

		Slideshow slideshow = new Slideshow();
		
		Slide slide1 = new Slide(slideshow.getDefaults());
		
		Question question = new Question("How many ducks are in York?", "ducks.txt");
		question.addAnswer("10", true);
		question.increaseAnswerCount(0);
		question.addAnswer("100000000000000", false);
		question.increaseAnswerCount(1);
		question.increaseAnswerCount(1);
		
		slide1.addQuestion(question);
		slideshow.addSlide(slide1);
		
		Slide slide2 = new Slide(slideshow.getDefaults());
		slideshow.addSlide(slide2);

		final SlideshowRuntimeData slideshowRuntimeData = new SlideshowRuntimeData(slideshow);

		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				slideshowRuntimeData.closeSlideshow();
			}
		});

		//primaryStage.show();

	}

}
