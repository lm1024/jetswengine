package Data;

public class Video extends SlideItem {
	
	private float width;
	private boolean autoplay;
	private boolean loop;
	
	public Video(Defaults defaults) {
		super(defaults);
	}
	
	@Override
	public void printItem() {
		super.printItem();
		System.out.println("");
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the autoplay
	 */
	public boolean isAutoplay() {
		return autoplay;
	}

	/**
	 * @param autoplay the autoplay to set
	 */
	public void setAutoplay(boolean autoplay) {
		this.autoplay = autoplay;
	}

	/**
	 * @return the loop
	 */
	public boolean isLoop() {
		return loop;
	}

	/**
	 * @param loop the loop to set
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
}
