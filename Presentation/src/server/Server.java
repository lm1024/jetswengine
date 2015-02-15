/**
 * 
 */
package server;

import java.io.IOException;

import Data.Slideshow;
import GUI.Console;
import XML.XMLReader;

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

	public void setCurrentSlideshow(String loc) {
		try {
			this.currentSlideshow = new XMLReader(loc).getSlideshow();
		} catch (IOException ioe) {
			console.printToConsole(console.getProcessorUsername(),"Failed to open \"" + loc + "\"");
		}
		if(currentSlideshow == null) {
			console.printToConsole(console.getProcessorUsername(),"Attempting to re-open previous file");
			try {
				this.currentSlideshow = new XMLReader(filename).getSlideshow();
			} catch (IOException ioe2) {
				this.filename = null;
			}
			if (filename == null) {
				console.printToConsole(console.getProcessorUsername(),"Failed to open fallback file");
				console.printToConsole(console.getProcessorUsername(),"Please open a valid Slideshow");
			} else {
				console.printToConsole(console.getProcessorUsername(),"Successfully opened fallback file");
			}
		} else {
			this.filename = loc;
		}
		
		
		this.filename = loc;
	}
	
	public Server() {
		//currentSlideshow = new XMLReader(filename).getSlideshow();
		//System.out.println(currentSlideshow.getInfo().getVersion());
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Server();
	}

}
