/** (c) Copyright by WaveMedia. */
package imageHandler;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;

import javax.swing.JOptionPane;

/**
 * Class for handling images on a JavaFX group.
 * 
 * @author vr561, ajar500, lp775, dk666, tjd511
 * @version 0.5 03/03/2015
 */
public class WMImage {
	private Group group;

	private ImageView imageView;

	public WMImage(Group group) {
		this.group = group;
	}

	/**
	 * Method for drawing a image on the screen.
	 * 
	 * @param image
	 *            a image object containing all the information about the image
	 *            to be drawn. Must be formed using the ImageBuilder.
	 * @see {@link imageHandler.ImageObject}
	 */
	public void drawImage(ImageObject image) {
		float xPos = image.getXStartPos();
		float yPos = image.getYStartPos();
		String filepath = image.getFilepath();
		double scaleX = image.getScaleX();
		double scaleY = image.getScaleY();
		float rotation = image.getRotation();
		boolean vFlip = image.isvFlip();
		boolean hFlip = image.ishFlip();
		double cropLeft = image.getCropLeft();
		double cropRight = image.getCropRight();
		double cropDown = image.getCropDown();
		double cropUp = image.getCropUp();
		float xEnd = image.getxEnd();
		float yEnd = image.getyEnd();
		float relativeXEnd = image.getRelativeXEnd();
		float relativeYEnd = image.getRelativeYEnd();
		ArrayList<ImageEffect> imageEffects = image.getImageEffects();
		drawImage(xPos, yPos, filepath, scaleX, scaleY, rotation, vFlip, hFlip,
				cropLeft, cropRight, cropDown, cropUp, xEnd, yEnd,
				relativeXEnd, relativeYEnd, imageEffects);
	}

	/**
	 * Method to set the visibility of the image.
	 * 
	 * @param visible
	 *            boolean containing if the image should be visible or not
	 */
	public void setVisible(boolean visible) {
		if (imageView != null) {
			imageView.setVisible(visible);
		}
	}

	/** Method to send the image to the back of the group. */
	public void toBack() {
		if (imageView != null) {
			imageView.toBack();
		}
	}

	/** Method to send the image to the back of the group. */
	public void toFront() {
		if (imageView != null) {
			imageView.toFront();
		}
	}

	/**
	 * Method adds a image to the group.
	 * 
	 * @param xPos
	 *            the x coordinate of the top left of the picture
	 * @param yPos
	 *            the y coordinate of the top left of the picture
	 * @param filepath
	 *            a string containing the filepath of an image
	 * @param scaleX
	 *            double containing the value of a horizontal scale. 1.0 is
	 *            original size.
	 * @param scaleY
	 *            double containing the value of a vertical scale. 1.0 is
	 *            original size.
	 * @param rotation
	 *            angle of rotation in degrees
	 * @param vFlip
	 *            boolean for if the image should be flipped vertically
	 * @param hFlip
	 *            boolean for if the image should be flipped horizontally
	 * @param cropLeft
	 *            size of crop from the left hand side of the image
	 * @param cropRight
	 *            size of crop from the right hand side of the image
	 * @param cropDown
	 *            size of crop from the top of the image
	 * @param cropUp
	 *            size of crop from the bottom of the image
	 * @param xEnd
	 * @param yEnd
	 *            /TODO
	 * @param imageEffects
	 *            a varargs containing all image effects that are to be applied
	 *            to the image. The first one in the list is the primary effect,
	 *            the effect following uses the previous effect as an input.
	 */
	private void drawImage(float xPos, float yPos, String filepath,
			double scaleX, double scaleY, float rotation, boolean vFlip,
			boolean hFlip, double cropLeft, double cropRight, double cropDown,
			double cropUp, float xEnd, float yEnd, float relativeXEnd, float relativeYEnd,
			ArrayList<ImageEffect> imageEffects) {
		/* Create image variable */
		Image image = null;
		try {
			/* Attempt to load image. (Takes a while... should be preloaded) */
			image = new Image("file:" + filepath);
			if (image.errorProperty().getValue()) {
				image = null;
			}
			// System.out.println("Image loaded with error state: " +
			// image.errorProperty().getValue());
		}
		/* Exception handling for simple URL/Image type errors */
		catch (IllegalArgumentException WrongURL) {
			image = null;
		} finally {
			if (image == null) {
				/* Display an error message to the user */
				Label label = new Label("Cannot find image");
				label.relocate(xPos, yPos);
				group.getChildren().add(label);
				System.out.println("Exception caught. Returning");
				return;
			}
		}

		imageView = new ImageView();

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
		WritableImage croppedImage = new WritableImage(reader, xStart, yStart,
				cropWidth, cropHeight);

		/* Assign the image to be the new cropped image */
		image = croppedImage;

		

		/* Get the new dimensions of the cropped image */
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();

		/*
		 * Recalculate the scale values based upon the required size for the
		 * image.
		 */
		if (relativeXEnd != 0 && relativeYEnd != 0) {
			scaleX = (xEnd - xPos) / imageWidth;
			scaleY = (yEnd - yPos) / imageHeight;
		} else if (relativeXEnd != 0) {
			scaleX = (xEnd - xPos) / imageWidth;
			scaleY = scaleX;
		} else if (relativeYEnd != 0) {
			scaleY = (yEnd - yPos) / imageHeight;
			scaleX = scaleY;
		}
		
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

		/* Move the image to a new position */
		double newXPos;
		double newYPos;
		newXPos = xPos - (imageWidth - imageWidth * scaleX) / 2;
		newYPos = yPos - (imageHeight - imageHeight * scaleY) / 2;
		
		if(vFlip) {
			newYPos -= (imageHeight * scaleY);
		}
		if(hFlip) {
			newXPos -= (imageWidth * scaleX);
		}
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
				System.err.println("Effect " + currentEffectName
						+ " not implemented yet.");
			}
			imageView.setEffect(bottomEffect);
		}

		/* Add the imageview to the group */
		group.getChildren().add(imageView);
	}
}