/** (c) Copyright by WaveMedia. */
package imageHandler;

import java.util.ArrayList;

import javafx.scene.Group;

/**
 * Class to handle multiple images at once.
 * 
 * @author tjd511
 * @version 1.0 13/04/2015
 */
public class ImageHandler {

	private Group group;

	private ArrayList<WMImage> images;

	/** Constructor Method */
	public ImageHandler(Group group) {
		/* Set the group reference */
		this.group = group;

		/* Initialise the image list */
		this.images = new ArrayList<WMImage>();
	}

	/**
	 * Method creates a new image, and draws it on the group specified in the
	 * constructor.
	 * 
	 * @param imageObject
	 *            a image object containing all the information about the image
	 *            to be drawn. Must be formed using the ImageBuilder.
	 * @see {@link imageHandler.ImageObject}
	 */
	public void createImage(ImageObject imageObject) {
		images.add(new WMImage(group));
		images.get(images.size() - 1).drawImage(imageObject);
	}

	/**
	 * Method toggles the visibility of a image.
	 * 
	 * @param imageNumber
	 *            the index of the image to be changed
	 * @param visible
	 *            boolean containing the state of the visibility of the image
	 */
	public void setVisible(int imageNumber, boolean visible) {
		if (imageNumber < images.size() && imageNumber >= 0) {
			images.get(imageNumber).setVisible(visible);
		}
	}

	/**
	 * Method to send a particular image to the back of the group.
	 * 
	 * @param imageNumber
	 *            the index of the image to be sent to back
	 */
	public void sendToBack(int imageNumber) {
		if (imageNumber < images.size() && imageNumber >= 0) {
			images.get(imageNumber).toBack();
		}
	}

	/**
	 * Method to send a particular image to the front of the group.
	 * 
	 * @param imageNumber
	 *            the index of the image to be sent to front
	 */
	public void sendToFront(int imageNumber) {
		if (imageNumber < images.size() && imageNumber >= 0) {
			images.get(imageNumber).toFront();
		}
	}

	/**
	 * Method gets the number of images.
	 * 
	 * @return the current number of instantiated images
	 */
	public int getImageCount() {
		return images.size();
	}

	/**
	 * Method clears the current array of images
	 */
	public void clearImages() {
		images.clear();
	}

}
