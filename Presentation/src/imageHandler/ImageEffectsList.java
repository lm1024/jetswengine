/** (c) Copyright by WaveMedia. */
package imageHandler;

import java.util.ArrayList;

/**
 * Class to handle a list of image effects to be applied to an image.
 * 
 * @author tjd511
 * @version 1.0 11/03/2015
 */
public class ImageEffectsList {

	private ArrayList<ImageEffect> imageEffectList;

	public ImageEffectsList() {
		imageEffectList = new ArrayList<ImageEffect>();
	}

	public void add(ImageEffect imageEffect) {
		imageEffectList.add(imageEffect);
	}

	public ArrayList<ImageEffect> getList() {
		return imageEffectList;
	}

	public void clear() {
		imageEffectList.clear();
	}

	public int size() {
		return imageEffectList.size();
	}

}
