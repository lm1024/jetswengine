package GUI;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
//import javafx.scene.effect.Bloom;
//import javafx.scene.effect.SepiaTone;
import javafx.scene.image.PixelReader;

public class ImageHandler {

	private Group group;
	
	public ImageHandler(Group group) {
		this.group = group;
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
		
		PixelReader reader = image.getPixelReader();
		WritableImage croppedImage = new WritableImage(reader, (int)cropLeft, (int)cropDown,
				(int)(width - cropRight - cropLeft - 1), (int)(height - cropUp - cropDown - 1));
		
//		PixelWriter writer = croppedImage.getPixelWriter();
		image = croppedImage;
		
		
//		WritableImage scaledCroppedImage = new WritableImage((int)(width * scaleX),(int)(height * scaleY));
		
		
		
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
//		Rectangle2D cropImage = new Rectangle2D(xPos + cropLeft, yPos + cropDown,
//				width - cropRight - cropLeft, height - cropUp - cropDown);
//		imageView.setViewport(cropImage);
		
		width = image.getWidth();
		height = image.getHeight();
		
		imageView.relocate(xPos - (width - width*scaleX)/2, yPos - (height - height*scaleY)/2);
				
		//imageView.resizeRelocate(0, 0, 0.2, 0.2);
		
		//imageView.relocate(xPos, yPos);
		
		//imageView.setEffect(new Bloom());
		//imageView.setEffect(new SepiaTone());
		
		//Draw image on screen
		imageView.setImage(image);
		
	
		//This is the line that will be used to add items
		group.getChildren().add(imageView);
	}
}
