/**
 * 
 */
package server;

/**
 * @author dk666
 * 
 */
public class Server {
	public static Console console;
	private Slideshow currentSlideshow;
	
	public Server() {
		console = new Console();
		XMLReader reader = new XMLReader();
		currentSlideshow = reader.getSlideshow();
		System.out.println(currentSlideshow.getInfo().getVersion());
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Server();
	}

}
