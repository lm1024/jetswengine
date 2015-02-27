/**
 * 
 */
package GUI;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.SepiaTone;
//import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

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
			boolean hFlip, double cropRight,  double cropLeft, 
			double cropDown, double cropUp) {
		
		if(vFlip) {
			d *= -1;
		}
		
		if(hFlip) {
			e  *= -1;
		}
		
		/* Image section! */
		Image image = new Image(filepath);
		ImageView imageView = new ImageView();
		imageView.setScaleX(e);
		imageView.setScaleY(d);
		double width = image.getWidth();
		double height = image.getHeight();
		double x = xPos - width/2;
		double y = yPos - height/2;
		
		imageView.setImage(image);
		imageView.setRotate(rotation);

		imageView.relocate(x, y);
		
		//imageView.setEffect(new Bloom());
		//imageView.setEffect(new SepiaTone());
		
		Rectangle2D cropImage = new Rectangle2D(x + cropLeft, y + cropDown, width - cropRight - cropLeft, height - cropUp - cropDown);
		imageView.setViewport(cropImage);
		imageView.relocate(x + cropLeft, y + cropDown);
	
		/* This is the line that will be used to add items */
		 group.getChildren().add(imageView);
	}
}
