package Data;

public class Audio extends SlideItem {

	private float width;
	private boolean loop;
	private boolean autoplay;
	private boolean visibleControlsOnly;
	private boolean playButtonOnly;

	public Audio(Defaults defaults) {
		super(defaults);
		this.width = defaults.getAudioWidth();
		visibleControlsOnly = defaults.isAudioHasVisibleControls();
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
	 * @return the visibleControlsOnly
	 */
	public boolean isVisibleControlsOnly() {
		return visibleControlsOnly;
	}

	/**
	 * @param visibleControlsOnly the visibleControlsOnly to set
	 */
	public void setVisibleControlsOnly(boolean visibleControlsOnly) {
		this.visibleControlsOnly = visibleControlsOnly;
	}

	/**
	 * @return the playButtonOnly
	 */
	public boolean isPlayButtonOnly() {
		return playButtonOnly;
	}

	/**
	 * @param playButtonOnly the playButtonOnly to set
	 */
	public void setPlayButtonOnly(boolean playButtonOnly) {
		this.playButtonOnly = playButtonOnly;
	}
	
}