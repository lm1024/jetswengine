package GUI;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
//import javafx.scene.effect.Bloom;
//import javafx.scene.effect.SepiaTone;
import javafx.scene.image.PixelReader;

/**
 * Class for handling images on a JavaFX group.
 * 
 * @author vr561, ajar500, lp775, dk666, tjd511
 * @version 0.5 03/03/2015
 */
public class ImageHandler {

	private Group group;

	public ImageHandler(Group group) {
		this.group = group;
	}

	/**
	 * Method adds a image to the group. 
	 * 
	 * @param xPos the x coordinate of the top left of the picture
	 * @param yPos the y coordinate of the top left of the picture
	 * @param filepath a string containing the filepath of an image
	 * @param scaleX 
	 * @param scaleY
	 * @param rotation
	 * @param vFlip
	 * @param hFlip
	 * @param cropLeft
	 * @param cropRight
	 * @param cropDown
	 * @param cropUp
	 */
	public void drawImage(float xPos, float yPos, String filepath, double scaleX, double scaleY, float rotation,
			boolean vFlip, boolean hFlip, double cropLeft, double cropRight, double cropDown, double cropUp) {

		/* Create image variable */
		Image image = new Image(filepath);
		ImageView imageView = new ImageView();

		/* Get height and width of image */
		double imageWidth = image.getWidth();
		double imageHeight = image.getHeight();
		
		/* Define coordinates for cropping the image */
		int xStart = (int) cropLeft;
		int yStart = (int) cropDown;
		int cropWidth = (int) (imageWidth - cropRight - cropLeft - 1);
		int cropHeight = (int) (imageHeight - cropUp - cropDown - 1);
		
		/* Define a pixel reader of the image so that it can be cropped */
		PixelReader reader = image.getPixelReader();
		
		/* Make a new cropped image using the coordinates calculated */
		WritableImage croppedImage = new WritableImage(reader, xStart, yStart, cropWidth, cropHeight);

		/* Assign the image to be the new cropped image */
		image = croppedImage;

		/* Flip image vertically and horizontally if required */
		if (vFlip) {
			scaleY *= -1;
		}

		if (hFlip) {
			scaleX *= -1;
		}

		/* Set rotation and horizontal and vertical scale of image */
		imageView.setRotate(rotation);
		imageView.setScaleX(scaleX);
		imageView.setScaleY(scaleY);
		
		/* Get the new dimensions of the cropped image */
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();

		/* Move the image to a new position */
		double newXPos = xPos - (imageWidth - imageWidth * scaleX) / 2;
		double newYPos = yPos - (imageHeight - imageHeight * scaleY) / 2;
		imageView.relocate(newXPos, newYPos);

		/* Set the imageview to show the cropped image*/
		imageView.setImage(image);

		/* Add the imageview to the group */
		group.getChildren().add(imageView);
	}
}
