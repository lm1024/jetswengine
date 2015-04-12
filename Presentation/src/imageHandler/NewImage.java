package imageHandler;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import javax.swing.JOptionPane;

public class NewImage {

	private Group group;
	private float xPos;
	private float yPos;
	private String filepath;
	private double scaleX;
	private double scaleY;
	private float rotation;
	private boolean vFlip;
	private boolean hFlip;
	private double cropLeft;
	private double cropRight;
	private double cropDown;
	private double cropUp;
	private ArrayList<ImageEffect> imageEffects;

	private ImageView imageView;
	private double imageWidth;
	private double imageHeight;

	public NewImage(Group group, ImageObject imageObject) {
		this.group = group;
		xPos = imageObject.getXStartPos();
		yPos = imageObject.getYStartPos();
		filepath = imageObject.getFilepath();
		scaleX = imageObject.getScaleX();
		scaleY = imageObject.getScaleY();
		rotation = imageObject.getRotation();
		vFlip = imageObject.isvFlip();
		hFlip = imageObject.ishFlip();
		cropLeft = imageObject.getCropLeft();
		cropRight = imageObject.getCropRight();
		cropDown = imageObject.getCropDown();
		cropUp = imageObject.getCropUp();
		imageEffects = imageObject.getImageEffects();

		drawImage();
	}

	/**
	 * Method adds a image to the group.
	 */
	private void drawImage() {
		/* Create image variable */
		Image image;
		try {
			/* Attempt to load image. (Takes a while... should be preloaded) */
			image = new Image(filepath);
		}
		/* Exception handling for simple URL/Image type errors */
		catch (IllegalArgumentException WrongURL) {
			/* Display an error message to the user */
			String errorMessage = "Could not locate/open image file at " + filepath;
			JOptionPane.showMessageDialog(null, errorMessage, "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}

		imageView = new ImageView();

		/* Get height and width of image */
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();

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

		/* Set the imageview to show the cropped image */
		imageView.setImage(image);
		/*
		 * Image filtering section. Initially declares an Effect variable for
		 * the bottom of the tree of effects. Then loops through all the varargs
		 * (if any) and makes new effects based on the enum entered, then adds
		 * the effect to the image. If a secondary effect is entered, then the
		 * input for this effect is the previous effect.
		 */
		Effect bottomEffect = null;
		for (ImageEffect currentEffectName : imageEffects) {
			switch (currentEffectName) {
			case SEPIA:
				SepiaTone sepiaTone = new SepiaTone();
				sepiaTone.setLevel(0.7);
				sepiaTone.setInput(bottomEffect);
				bottomEffect = sepiaTone;
				break;
			case BLOOM:
				Bloom bloom = new Bloom();
				bloom.setThreshold(0.7);
				bloom.setInput(bottomEffect);
				bottomEffect = bloom;
				break;
			case BLUR:
				BoxBlur boxBlur = new BoxBlur();
				boxBlur.setWidth(5);
				boxBlur.setHeight(5);
				boxBlur.setIterations(3);
				boxBlur.setInput(bottomEffect);
				bottomEffect = boxBlur;
				break;
			case GLOW:
				Glow glow = new Glow();
				glow.setInput(bottomEffect);
				bottomEffect = glow;
				break;
			case GRAYSCALE:
				ColorAdjust colorAdjust = new ColorAdjust();
				colorAdjust.setSaturation(-1);
				colorAdjust.setInput(bottomEffect);
				bottomEffect = colorAdjust;
				break;
			case REFLECTION:
				Reflection reflection = new Reflection();
				reflection.setFraction(0.3);
				reflection.setInput(bottomEffect);
				bottomEffect = reflection;
				break;
			default:
				System.err.println("Effect " + currentEffectName + " not implemented yet.");
			}
			imageView.setEffect(bottomEffect);
		}

		/* Add the imageview to the group */
		group.getChildren().add(imageView);
	}

	public void relocate(float xPos, float yPos) {
		/* Move the image to a new position */
		double newXPos = xPos - (imageWidth - imageWidth * scaleX) / 2;
		double newYPos = yPos - (imageHeight - imageHeight * scaleY) / 2;
		imageView.relocate(newXPos, newYPos);
	}

	public void resize(double scaleX, double scaleY) {
		imageView.setScaleX(scaleX);
		imageView.setScaleY(scaleY);
		imageView.relocate((float) imageView.getX(), (float) imageView.getY());
	}

	public void setVisible(boolean visible) {
		System.out.println("Setting image in handler visible " + visible);
		imageView.setVisible(visible);
		System.out.println(imageView.isVisible());
	}
	
	

}
