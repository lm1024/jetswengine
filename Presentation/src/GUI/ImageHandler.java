/**
 * 
 */
package GUI;

import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.SepiaTone;
//import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author tjd511
 * 
 */
public class ImageHandler {

	private Group group;

	/**
	 * 
	 */
	public ImageHandler(Group group) {
		// TODO Auto-generated constructor stub
		this.group = group;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/** 
	 * 
	 * */
	public void drawImage(float xPos, float yPos, String filepath,
			double d, double e, float rotation, boolean vFlip,
			boolean hFlip) {
		
		if(vFlip) {
			d *= -1;
		}
		
		if(hFlip) {
			e  *= -1;
		}
		
		/* Image section! */
		Image image = new Image(filepath);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setScaleX(e);
		imageView.setScaleY(d);
		imageView.setRotate(rotation);
		imageView.relocate(xPos, yPos);
		imageView.setEffect(new SepiaTone());
		//imageView.setEffect(new Bloom());

		/* This is the line that will be used to add items */
		 group.getChildren().add(imageView);
	}
}
