/**
 * 
 */
package server;

/**
 * @author dk666
 * 
 */
public class server {
	public static Console console;
	private Slideshow currentSlideshow;
	
	public server() {
		console = new Console();
		XMLReader reader = new XMLReader();
		currentSlideshow = reader.getSlideshow();
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new server();
	}

}
