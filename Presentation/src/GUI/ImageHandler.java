package GUI;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.effect.Bloom;
//import javafx.scene.effect.SepiaTone;

public class ImageHandler {

	private Group group;
	
	public ImageHandler(Group group) {
		this.group = group;
	}

	public static void main(String[] args) {
	}

	//IMAGE FUNCTION//
	public void drawImage(float xPos, float yPos, String filepath,
			double scaleX, double scaleY, float rotation, boolean vFlip,
			boolean hFlip, double cropLeft, double cropRight,
			double cropDown, double cropUp) {
		
		//Create image variable
		Image image = new Image(filepath);
		ImageView imageView = new ImageView();
		
		//Get height and width of image
		double width = image.getWidth();
		double height = image.getHeight();
		
		//Set image position values to centre of image
		double x = xPos - width/2;
		double y = yPos - height/2;
		
		//Flip image vertically
		if(vFlip) {
			scaleY *= -1;
		}
		
		//Flip image horizontally
		if(hFlip) {
			scaleX  *= -1;
		}
		
		//Scale image vertically and horizontally
		imageView.setScaleX(scaleX);
		imageView.setScaleY(scaleY);
		
		//Rotate image
		imageView.setRotate(rotation);
		
		//Crop image - create a rectangle of smaller size and view image through it
		Rectangle2D cropImage = new Rectangle2D(x + cropLeft, y + cropDown,
				width - cropRight - cropLeft, height - cropUp - cropDown);
		imageView.setViewport(cropImage);
		imageView.relocate(x + cropLeft, y + cropDown);
		
		//imageView.setEffect(new Bloom());
		//imageView.setEffect(new SepiaTone());
		
		//Draw image on screen
		imageView.setImage(image);
	
		//This is the line that will be used to add items
		group.getChildren().add(imageView);
	}
}
