package GUI;

import java.util.ArrayList;

public class SlideshowImage {
	private final String filepath;
	private final float xPos;
	private final float yPos;

	private final double scaleX;
	private final double scaleY;
	private final float rotation;
	private final boolean vFlip;
	private final boolean hFlip;
	private final double cropLeft;
	private final double cropRight;
	private final double cropDown;
	private final double cropUp;
	
	private final ArrayList<ImageEffect> imageEffects;

	private SlideshowImage(ImageBuilder builder) {
		this.xPos = builder.xPos;
		this.yPos = builder.yPos;
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
		this.imageEffects = builder.imageEffects;
	}

	public String getFilepath() {
		return filepath;
	}

	public float getxPos() {
		return xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public double getScaleX() {
		return scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public float getRotation() {
		return rotation;
	}

	public boolean isvFlip() {
		return vFlip;
	}

	public boolean ishFlip() {
		return hFlip;
	}

	public double getCropLeft() {
		return cropLeft;
	}

	public double getCropRight() {
		return cropRight;
	}

	public double getCropDown() {
		return cropDown;
	}

	public double getCropUp() {
		return cropUp;
	}
	
	public ArrayList<ImageEffect> getImageEffects() {
		return imageEffects;
	}

	public static class ImageBuilder {
		private final String filepath;
		private final float xPos;
		private final float yPos;

		private double scaleX = 1;
		private double scaleY = 1;
		private float rotation = 0;
		private boolean vFlip;
		private boolean hFlip;
		private double cropLeft;
		private double cropRight;
		private double cropDown;
		private double cropUp;
		private ArrayList<ImageEffect> imageEffects = new ArrayList<ImageEffect>();

		public ImageBuilder(String filepath, float xPos, float yPos) {
			this.filepath = filepath;
			this.xPos = xPos;
			this.yPos = yPos;
		}

		public ImageBuilder scaleX(double scaleX) {
			this.scaleX = scaleX;
			return this;
		}

		public ImageBuilder scaleY(double scaleY) {
			this.scaleY = scaleY;
			return this;
		}

		public ImageBuilder rotation(float rotation) {
			this.rotation = rotation;
			return this;
		}

		public ImageBuilder vFlip(boolean vFlip) {
			this.vFlip = vFlip;
			return this;
		}

		public ImageBuilder hFlip(boolean hFlip) {
			this.hFlip = hFlip;
			return this;
		}

		public ImageBuilder cropLeft(double cropLeft) {
			this.cropLeft = cropLeft;
			return this;
		}

		public ImageBuilder cropRight(double cropRight) {
			this.cropRight = cropRight;
			return this;
		}

		public ImageBuilder cropDown(double cropDown) {
			this.cropDown = cropDown;
			return this;
		}

		public ImageBuilder cropUp(double cropUp) {
			this.cropUp = cropUp;
			return this;
		}
		
		public ImageBuilder imageEffects(ImageEffectsList imageEffects) {
			this.imageEffects = imageEffects.getList();
			return this;
		}

		public SlideshowImage build() {
			return new SlideshowImage(this);
		}
	}
}
