package server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import Data.Slideshow;
import SwingGUI.Console;
import XML.ImprovedXMLReader;

/**
 * @author dk666
 * 
 */
public class Server {
	public static Console console = new Console();
	private Slideshow currentSlideshow;
	private String filename = "pws.xml";

	public String getCurrentSlideshow() {
		return filename;
	}

	public void setCurrentSlideshow(String loc) {
		try {
			this.currentSlideshow = new ImprovedXMLReader(loc).getSlideshow();
		} catch (IOException ioe) {
			console.printToConsole(console.getProcessorUsername(),
					"Failed to open \"" + loc + "\"");
		}
		if (currentSlideshow == null) {
			console.printToConsole(console.getProcessorUsername(),
					"Attempting to re-open previous file");
			try {
				this.currentSlideshow = new ImprovedXMLReader(filename).getSlideshow();
			} catch (IOException ioe2) {
				this.filename = null;
			}
			if (filename == null) {
				console.printToConsole(console.getProcessorUsername(),
						"Failed to open fallback file");
				console.printToConsole(console.getProcessorUsername(),
						"Please open a valid Slideshow");
			} else {
				console.printToConsole(console.getProcessorUsername(),
						"Successfully opened fallback file");
			}
		} else {
			this.filename = loc;
		}
	}

	/**
	 * Utility method for this class, to output a quick check on the contents
	 * that were read in from the XML file.
	*/
	
	public Server() {

		long startTime = System.nanoTime();

		try {
			this.currentSlideshow = new ImprovedXMLReader(filename).getSlideshow();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		currentSlideshow.printSlideshow();
		System.out.println("Execution time: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + "ms");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// new Server();
	}

}
