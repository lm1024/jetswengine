/** (c) Copyright by WaveMedia. */
package Data;

/**
 * Audio class for containing a single SmartSlides Audio data.
 * 
 * @author dk666
 * @version 2.3 02/03/15
 */
public class Audio extends SlideItem {

	/* Properties of an audio object */
	private float width;
	private boolean loop;
	private boolean autoplay;
	private boolean visibleControlsOnly;
	private boolean playButtonOnly;

	/**
	 * Constructs a new Audio object with the specified defaults.
	 * 
	 * @param defaults
	 */
	public Audio(Defaults defaults) {
		super(defaults);
		this.width = defaults.getAudioWidth();
		visibleControlsOnly = defaults.isAudioHasVisibleControls();
	}
	
	/**
	 * For debugging. Outputs the properties of this graphic.
	 */
	@Override
	public void printItem() {
		super.printItem();
		System.out.println("Width: " + width);
		System.out.println("Loop: " + loop);
		System.out.println("Autoplay: " + autoplay);
		System.out.println("VisibleControlsOnly: " + visibleControlsOnly);
		System.out.println("PlayButtonOnly: " + playButtonOnly);
		System.out.println("");
	}
	
	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Sets the width of the audio player.
	 * @param width the width to set
	 */
	public void setWidth(String s) {
		try {
			float f = Float.parseFloat(s);
			if(f > 0) {
				this.width = f;
			}
			
		} catch (Exception e) {
			//Do nothing
		}
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
	public void setLoop(String s) {
		this.loop = Boolean.parseBoolean(s);
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
	public void setAutoplay(String s) {
		this.autoplay = Boolean.parseBoolean(s);
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
	public void setVisibleControlsOnly(String s) {
		this.visibleControlsOnly = Boolean.parseBoolean(s);
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
	public void setPlayButtonOnly(String s) {
		this.playButtonOnly = Boolean.parseBoolean(s);
	}
	
}