package GUI;

import java.util.ArrayList;

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
