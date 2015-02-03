/**
 * 
 */
package server;

/**
 * @author dk666
 * 
 */
public class Server {
	public static Console console = new Console();
	private Slideshow currentSlideshow;
	private String filename = "Sample.xml";
	
	public String getCurrentSlideshow() {
		return filename;
	}

	public void setCurrentSlideshow(String loc) throws IllegalArgumentException {
		try {
			this.filename = loc;
			this.currentSlideshow = new XMLReader(filename).getSlideshow();
		} catch (IllegalArgumentException e) {
			System.out.println("Failed to open \"" + filename + "\"");
		}
	}
	
	public Server() {
		currentSlideshow = new XMLReader(filename).getSlideshow();
		System.out.println(currentSlideshow.getInfo().getVersion());
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Server();
	}

}
