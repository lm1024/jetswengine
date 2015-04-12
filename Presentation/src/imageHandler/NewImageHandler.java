package imageHandler;

import java.util.ArrayList;

import javafx.scene.Group;


public class NewImageHandler {

	
	private Group group;
	
	private ArrayList<NewImage> images;
	
	/** Constructor Method */
	public NewImageHandler(Group group) {
		/* Set the group reference */
		this.group = group;
		 
        /* Initialise the image list */
        this.images = new ArrayList<NewImage>();
	}
	
	public void createImage(ImageObject imageObject) {
		images.add(new NewImage(group, imageObject));
	}
	
	public void relocate(int imageNumber, float xPos, float yPos) {
		if (imageNumber < images.size() && imageNumber >= 0) {
			images.get(imageNumber).relocate(xPos, yPos);
		} 
	}
	
	public void resize(int imageNumber, float scaleX, float scaleY) {
		if (imageNumber < images.size() && imageNumber >= 0) {
			images.get(imageNumber).resize(scaleX, scaleY);
		} 
	}
	
	public void setVisible(int imageNumber, boolean visible) {
		if (imageNumber < images.size() && imageNumber >= 0) {
			images.get(imageNumber).setVisible(visible);
		} 
		else System.out.println("Not recognised");
	}

	public int getImageCount() {
		return images.size();
	}
	
	public void clearImages() {
		images.clear();
	}
	
}
