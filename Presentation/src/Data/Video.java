/** (c) Copyright by WaveMedia. */
package Data;

/**
 * Video class for containing a single SmartSlides video data.
 * 
 * @author dk666
 * @version 2.3 02/06/15
 */
public class Video extends SlideItem {

	private float width;
	private boolean autoplay;
	private boolean loop;

	/**
	 * Constructs a new video object with the specified defaults.
	 * 
	 * @param defaults
	 */
	public Video(Defaults defaults) {
		super(defaults);
	}

	/**
	 * Returns the width of the video.
	 * 
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Sets the width of the video.
	 * 
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String s) {
		try {
			this.width = Float.parseFloat(s);
		} catch (Exception e) {
			// Do nothing
		}

	}

	/**
	 * Returns true if video should autoplay.
	 * 
	 * @return the autoplay
	 */
	public boolean isAutoplay() {
		return autoplay;
	}

	/**
	 * Sets whether the video should autoplay or not.
	 * 
	 * @param autoplay
	 *            the autoplay to set
	 */
	public void setAutoplay(String s) {
		this.autoplay = Boolean.parseBoolean(s);
	}

	/**
	 * Returns true if the video should loop.
	 * 
	 * @return the loop
	 */
	public boolean isLoop() {
		return loop;
	}

	/**
	 * Sets whether the video should loop or not.
	 * 
	 * @param loop
	 *            the loop to set
	 */
	public void setLoop(String s) {
		this.loop = Boolean.parseBoolean(s);
	}

}
