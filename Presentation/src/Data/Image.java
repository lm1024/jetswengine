/** (c) Copyright by WaveMedia. */
package Data;

import imageHandler.ImageEffect;

import java.util.ArrayList;

/**
 * Image class for containing a single SmartSlides Image data.
 * 
 * @author dk666
 * @version 2.3 02/06/15
 */
public class Image extends SlideItem {

	/* Properties of an Image */
	private float rotation;
	private float scaleX;
	private float scaleY;
	private float cropX1;
	private float cropY1;
	private float cropX2;
	private float cropY2;
	private boolean flipHorizontal;
	private boolean flipVertical;
	private float xEnd;
	private float yEnd;

	/* Images can contain multiple effects, so store them all in a list */
	private ArrayList<ImageEffect> imageEffects;

	/**
	 * Constructs a new Image object with the specified defaults.
	 * 
	 * @param defaults
	 */
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

	/**
	 * For debugging. Outputs the properties of this graphic.
	 */
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

	/**
	 * Adds the specified image effect to the list of image effects to be
	 * applied to the image.
	 * 
	 * @param imageEffectName
	 *            the image effect to add to the shape.
	 */
	public void addImageEffect(String imageEffectName) {
		switch (imageEffectName) {
		case "sepia":
			imageEffects.add(ImageEffect.SEPIA);
			break;
		case "bloom":
			imageEffects.add(ImageEffect.BLOOM);
			break;
		case "blur":
			imageEffects.add(ImageEffect.BLUR);
			break;
		case "glow":
			imageEffects.add(ImageEffect.GLOW);
			break;
		case "grayscale":
			imageEffects.add(ImageEffect.GRAYSCALE);
			break;
		case "reflection":
			imageEffects.add(ImageEffect.REFLECTION);
			break;
		default:
			break;

		}

	}

	
	/**
	 * Returns the list of image effects to be applied to the image.
	 * @return imageEffects the list of image effects.
	 */
	public ArrayList<ImageEffect> getImageEffects() {
		return imageEffects;
	}

	/**
	 * Returns the scale factor for the x-direction.
	 * @return scaleX the x-direction scale factor.
	 */
	public float getScaleX() {
		return scaleX;
	}

	/**
	 * Sets the scale factor to be applied to the image in the x-direction.
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
	 * Returns the scale factor for the y-direction.
	 * @return scaleY the y-direction scale factor.
	 */
	public float getScaleY() {
		return scaleY;
	}

	/**
	 * Sets the scale factor to be applied to the image in the y-direction.
	 * @param scaleY
	 *            the scaleY to set
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
			if ((f >= 0) && (f <= 1)) {
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
			if ((f >= 0) && (f <= 1)) {
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
			if ((f >= 0) && (f <= 1)) {
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

	/**
	 * @return the xEnd
	 */
	public float getxEnd() {
		return xEnd;
	}

	/**
	 * @param xEnd
	 *            the xEnd to set
	 */
	public void setxEnd(String string) {
		try {
			float f = Float.parseFloat(string);
			if (f >= 0) {
				this.xEnd = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the yEnd
	 */
	public float getyEnd() {
		return yEnd;
	}

	/**
	 * @param yEnd
	 *            the yEnd to set
	 */
	public void setyEnd(String string) {
		try {
			float f = Float.parseFloat(string);
			if (f >= 0) {
				this.yEnd = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

}
