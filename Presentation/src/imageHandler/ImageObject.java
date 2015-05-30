/** (c) Copyright by WaveMedia. */
package imageHandler;

import java.util.ArrayList;

/**
 * Image object class for passing to the image handler to draw images to screen.
 * 
 * @author tjd511
 * @version 1.0 11/03/2015
 */
public class ImageObject {
	/* Variables containing all relevant data about images. */
	private final String filepath;
	private final float xStartPos;
	private final float yStartPos;
	private final double scaleX;
	private final double scaleY;
	private final float rotation;
	private final boolean vFlip;
	private final boolean hFlip;
	private final double cropLeft;
	private final double cropRight;
	private final double cropDown;
	private final double cropUp;
	private final float xEnd;
	private final float yEnd;
	private final float relativeXEnd;
	
	private final float relativeYEnd;
	private final ArrayList<ImageEffect> imageEffects;

	/* Constructor must be called using the builder */
	private ImageObject(ImageBuilder builder) {
		this.xStartPos = builder.xStartPos;
		this.yStartPos = builder.yStartPos;
		this.filepath = builder.filepath;
		this.scaleX = builder.scaleX;
		this.scaleY = builder.scaleY;
		this.rotation = builder.rotation;
		this.vFlip = builder.vFlip;
		this.hFlip = builder.hFlip;
		this.cropLeft = builder.cropLeft;
		this.cropRight = builder.cropRight;
		this.cropDown = builder.cropDown;
		this.cropUp = builder.cropUp;
		this.xEnd = builder.xEnd;
		this.yEnd = builder.yEnd;
		this.relativeXEnd = builder.relativeXEnd;
		this.relativeYEnd = builder.relativeYEnd;
		this.imageEffects = builder.imageEffects;
	}

	/**
	 * @return the filepath of the image
	 */
	public String getFilepath() {
		return filepath;
	}

	/**
	 * @return the top left x coordinate of the image
	 */
	public float getXStartPos() {
		return xStartPos;
	}

	/**
	 * @return the top left y coordinate of the image
	 */
	public float getYStartPos() {
		return yStartPos;
	}

	/**
	 * @return the horizontal scale of the image
	 */
	public double getScaleX() {
		return scaleX;
	}

	/**
	 * @return the vertical scale of the image
	 */
	public double getScaleY() {
		return scaleY;
	}

	/**
	 * @return the rotation around the center of the image in degrees
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @return boolean containing if the image should be vertically flipped or
	 *         not
	 */
	public boolean isvFlip() {
		return vFlip;
	}

	/**
	 * @return boolean containing if the image should be horizontally flipped or
	 *         not
	 */
	public boolean ishFlip() {
		return hFlip;
	}

	/**
	 * @return the amount by which the left side of the image should be cropped
	 */
	public double getCropLeft() {
		return cropLeft;
	}

	/**
	 * @return the amount by which the right side of the image should be cropped
	 */
	public double getCropRight() {
		return cropRight;
	}

	/**
	 * @return the amount by which the top side of the image should be cropped
	 */
	public double getCropDown() {
		return cropDown;
	}

	/**
	 * @return the amount by which the bottom side of the image should be
	 *         cropped
	 */
	public double getCropUp() {
		return cropUp;
	}

	/**
	 * @return the xStartPos
	 */
	public float getxStartPos() {
		return xStartPos;
	}

	/**
	 * @return the yStartPos
	 */
	public float getyStartPos() {
		return yStartPos;
	}

	/**
	 * @return the xEnd
	 */
	public float getxEnd() {
		return xEnd;
	}

	/**
	 * @return the yEnd
	 */
	public float getyEnd() {
		return yEnd;
	}
	
	/**
	 * @return the relativeXEnd
	 */
	public float getRelativeXEnd() {
		return relativeXEnd;
	}

	/**
	 * @return the relativeYEnd
	 */
	public float getRelativeYEnd() {
		return relativeYEnd;
	}

	/**
	 * @return a list of ImageEffects that are to be applied to the image
	 * @see {@link imageHandler.ImageEffect}
	 */
	public ArrayList<ImageEffect> getImageEffects() {
		return imageEffects;
	}

	public static class ImageBuilder {
		/* Required values for all images */
		private final String filepath;
		private final float xStartPos;
		private final float yStartPos;

		/* Optional values for images */
		private double scaleX = 1;
		private double scaleY = 1;
		private float rotation;
		private boolean vFlip;
		private boolean hFlip;
		private double cropLeft;
		private double cropRight;
		private double cropDown;
		private double cropUp;
		private float xEnd;
		private float yEnd;
		private float relativeXEnd;
		private float relativeYEnd;
		private ArrayList<ImageEffect> imageEffects = new ArrayList<ImageEffect>();

		/**
		 * Constructor for ImageBuilder takes the 3 required values for each
		 * shape.
		 * 
		 * @param filepath
		 *            a string containing the filename of the image to be
		 *            displayed.
		 * @param xStartPos
		 *            the x coordinate of the top left position of the image.
		 * @param yStartPos
		 *            the y coordinate of the top left position of the image.
		 */
		public ImageBuilder(String filepath, float xStartPos, float yStartPos) {
			this.filepath = filepath;
			this.xStartPos = xStartPos;
			this.yStartPos = yStartPos;
		}

		/**
		 * Method sets the horizontal scale of the image.
		 * 
		 * @param scaleX
		 *            the horizontal scale of the image
		 */
		public ImageBuilder scaleX(double scaleX) {
			this.scaleX = scaleX;
			return this;
		}

		/**
		 * Method sets the vertical scale of the image.
		 * 
		 * @param scaleY
		 *            the vertical scale of the image
		 */
		public ImageBuilder scaleY(double scaleY) {
			this.scaleY = scaleY;
			return this;
		}

		/**
		 * Method sets the rotation of the image.
		 * 
		 * @param rotation
		 *            the rotation of the image in degrees
		 */
		public ImageBuilder rotation(float rotation) {
			this.rotation = rotation;
			return this;
		}

		/**
		 * Method sets if the image should be vertically flipped or not.
		 * 
		 * @param vFlip
		 *            boolean containing if the image should be vertically
		 *            flipped
		 */
		public ImageBuilder vFlip(boolean vFlip) {
			this.vFlip = vFlip;
			return this;
		}

		/**
		 * Method sets if the image should be horizontally flipped or not.
		 * 
		 * @param jFlip
		 *            boolean containing if the image should be horizontally
		 *            flipped
		 */
		public ImageBuilder hFlip(boolean hFlip) {
			this.hFlip = hFlip;
			return this;
		}

		/**
		 * The amount by which the left side of the image should be cropped.
		 * 
		 * @param cropLeft
		 *            the amount by which the left side of the image is cropped
		 */
		public ImageBuilder cropLeft(double cropLeft) {
			this.cropLeft = cropLeft;
			return this;
		}

		/**
		 * The amount by which the right side of the image should be cropped.
		 * 
		 * @param cropRight
		 *            the amount by which the right side of the image is cropped
		 */
		public ImageBuilder cropRight(double cropRight) {
			this.cropRight = cropRight;
			return this;
		}

		/**
		 * The amount by which the top side of the image should be cropped.
		 * 
		 * @param dropDown
		 *            the amount by which the top side of the image is cropped
		 */
		public ImageBuilder cropDown(double cropDown) {
			this.cropDown = cropDown;
			return this;
		}

		/**
		 * The amount by which the bottom side of the image should be cropped.
		 * 
		 * @param cropUp
		 *            the amount by which the bottom side of the image is
		 *            cropped
		 */
		public ImageBuilder cropUp(double cropUp) {
			this.cropUp = cropUp;
			return this;
		}
		
		
		public ImageBuilder xEnd(float xEnd) {
			this.xEnd = xEnd;
			return this;
		}

		
		public ImageBuilder yEnd(float yEnd) {
			this.yEnd = yEnd;
			return this;
		}
		

		public ImageBuilder relativeXEnd(float relativeXEnd) {
			this.relativeXEnd = relativeXEnd;
			return this;
		}

		
		public ImageBuilder relativeYEnd(float relativeYEnd) {
			this.relativeYEnd = relativeYEnd;
			return this;
		}

		/**
		 * Method sets the effects that should be applied to the image.
		 * 
		 * @param imageEffects
		 *            a list of imageEffects that should be applied to the
		 *            image.
		 * @see {@link imageHandler.ImageEffectsList}
		 */
		public ImageBuilder imageEffects(ImageEffectsList imageEffects) {
			this.imageEffects = imageEffects.getList();
			return this;
		}

		/**
		 * Method builds the image object.
		 * 
		 * Defaults:
		 * 
		 * ScaleX = 1. ScaleY = 1.
		 * 
		 * @return a image object containing all the paramaters about the image.
		 */
		public ImageObject build() {
			return new ImageObject(this);
		}
	}
}
