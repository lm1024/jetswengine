package Data;

import imageHandler.ImageEffect;

import java.util.ArrayList;

public class Image extends SlideItem {
	
	private float rotation;
	private float scaleX;
	private float scaleY;
	private float cropX1;
	private float cropY1;
	private float cropX2;
	private float cropY2;
	private boolean flipHorizontal;
	private boolean flipVertical;
	
	private ArrayList<ImageEffect> imageEffects;
	
	public Image(Defaults defaults) {
		super(defaults);
		this.rotation = defaults.getRotation();
		this.scaleX = defaults.getScale();
		this.scaleY = defaults.getScale();
		this.cropX1 = defaults.getCropX1();
		this.cropX2 = defaults.getCropX2();
		this.cropY1 = defaults.getCropY1();
		this.cropY2 = defaults.getCropY2();
		
		imageEffects = new ArrayList<ImageEffect>();
	}	
	
	@Override
	public void printItem() {
		super.printItem();
		System.out.println("Rotation: " + rotation);
		System.out.println("Scale X: " + scaleX);
		System.out.println("Scale X: " + scaleY);
		System.out.println("CropX1: " + cropX1);
		System.out.println("CropY1: " + cropY1);
		System.out.println("CropX2: " + cropX2);
		System.out.println("CropX1: " + cropX1);
		System.out.println("CropY2: " + cropY2);
		System.out.println("FlipHorizontal: " + flipHorizontal);
		System.out.println("FlipVertical: " + flipVertical);
		System.out.println("");
	}
	
	public void addImageEffect(ImageEffect imageEffect) {
		imageEffects.add(imageEffect);
	}
	
	public ArrayList<ImageEffect> getImageEffects() {
		return imageEffects;
	}

	/**
	 * @return the scaleX
	 */
	public float getScaleX() {
		return scaleX;
	}

	/**
	 * @param scalex
	 *            the scalex to set
	 */
	public void setScaleX(String string) {
		try {
			float f = Float.parseFloat(string);
			if (f > 0) {
				this.scaleX = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}
	
	/**
	 * @return the scaleY
	 */
	public float getScaleY() {
		return scaleY;
	}
	
	/**
	 * @param scaley
	 *            the scaley to set
	 */
	public void setScaleY(String string) {
		try {
			float f = Float.parseFloat(string);
			if (f > 0) {
				this.scaleY = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @param rotation
	 *            the rotation to set
	 */
	public void setRotation(String string) {
		try {
			int i = Integer.parseInt(string);
			if ((i >= 0) && (i <= 360)) {
				this.rotation = i;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the flipHorizontal
	 */
	public Boolean getFlipHorizontal() {
		return flipHorizontal;
	}

	/**
	 * @param flipHorizontal
	 *            the flipHorizontal to set
	 */
	public void setFlipHorizontal(String flipHorizontal) {
		this.flipHorizontal = Boolean.parseBoolean(flipHorizontal);
	}

	/**
	 * @return the flipVertical
	 */
	public Boolean getFlipVertical() {
		return flipVertical;
	}

	/**
	 * @param flipVertical
	 *            the flipVertical to set
	 */
	public void setFlipVertical(String flipVertical) {
		this.flipVertical = Boolean.parseBoolean(flipVertical);
	}

	/**
	 * @return the cropX1
	 */	
	public float getCropX1() {
		return cropX1;
	}

	/**
	 * @param cropX1
	 *            the cropX1 to set
	 */
	public void setCropX1(String string) {
		try {
			float f = Float.parseFloat(string);
			if((f >= 0) && (f <= 1)) {
				this.cropX1 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropY1
	 */
	public float getCropY1() {
		return cropY1;
	}

	/**
	 * @param cropY1
	 *            the cropY1 to set
	 */
	public void setCropY1(String string) {
		try {
			float f = Float.parseFloat(string);
			if((f >= 0) && (f <= 1)) {
				this.cropY1 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropX2
	 */
	public float getCropX2() {
		return cropX2;
	}

	/**
	 * @param cropX2
	 *            the cropX2 to set
	 */
	public void setCropX2(String string) {
		try {
			float f = Float.parseFloat(string);
			if((f >= 0) && (f <= 1)) {
				this.cropX2 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropY2
	 */
	public float getCropY2() {
		return cropY2;
	}

	/**
	 * @param cropY2
	 *            the cropY2 to set
	 */
	public void setCropY2(String string) {
		try {
			float f = Float.parseFloat(string);
			if ((f >= 0) && (f <= 1)) {
				this.cropY2 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

}
