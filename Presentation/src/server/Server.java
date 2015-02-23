/**
 * 
 */
package server;

import java.io.IOException;

import Data.Oval;
import Data.Slideshow;
import SwingGUI.Console;
import XML.XMLReader;

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
			this.currentSlideshow = new XMLReader(loc).getSlideshow();
		} catch (IOException ioe) {
			console.printToConsole(console.getProcessorUsername(),
					"Failed to open \"" + loc + "\"");
		}
		if (currentSlideshow == null) {
			console.printToConsole(console.getProcessorUsername(),
					"Attempting to re-open previous file");
			try {
				this.currentSlideshow = new XMLReader(filename).getSlideshow();
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
	private void writeSlides() {
		if (currentSlideshow != null) {
			System.out.println("\tSlideshow Author: "
					+ currentSlideshow.getInfo().getAuthor());
			System.out.println("\tSlideshow Version: "
					+ currentSlideshow.getInfo().getVersion());
			System.out.println("\tSlideshow Comment: "
					+ currentSlideshow.getInfo().getComment());
			System.out.println("\tSlideshow groupid: "
					+ currentSlideshow.getInfo().getGroupID());
			System.out.println("\tSlide 1 duration: "
					+ currentSlideshow.getSlides().get(0).getDuration());
			System.out.println("\tSlide 1 text 3.2: "
					+ currentSlideshow.getSlides().get(0).getTextList().get(2)
							.getTextFragments().get(1).getText());
			System.out.println("\tSlide 1 text 3.2: "
					+ currentSlideshow.getSlides().get(0).getTextList().get(2)
							.getTextFragments().get(1).isSubscript());
			System.out.println("\tSlide 1 text 1.1 case: "
					+ currentSlideshow.getSlides().get(0).getTextList().get(0)
							.getTextFragments().get(0).getTextCase());
			System.out.println("\tSlide 1 text 1.1 font: "
					+ currentSlideshow.getSlides().get(0).getTextList().get(0)
							.getTextFragments().get(0).getFont());
			System.out.println("\tSlide 1 image 1: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(0)
							.getSourceFile());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getSourceFile());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getxStart());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getyStart());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getScale());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getDuration());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getStartTime());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getRotation());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getFlipHorizontal());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getFlipVertical());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getCropX1());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getCropY1());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getCropX2());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getImagesList().get(1)
							.getCropY2());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getGraphicsList().get(0)
							.getClass().getSimpleName().toLowerCase());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getGraphicsList().get(0)
							.getxStart());
			System.out.println("\tSlide 1 image 2: "
					+ ((Oval) currentSlideshow.getSlides().get(0).getGraphicsList()
							.get(0)).getxEnd());
			System.out.println("\tSlide 1 image 2: "
					+ currentSlideshow.getSlides().get(0).getGraphicsList().get(0)
							.getyStart());
			System.out.println("\tSlide 1 image 2: "
					+ ((Oval) currentSlideshow.getSlides().get(0).getGraphicsList()
							.get(1)).getyEnd());
			System.out.println("\tSlide 1 image 2: "
					+ ((Oval) currentSlideshow.getSlides().get(0).getGraphicsList()
							.get(0)).isSolid());
			System.out.println("\tSlide 1 image 2: "
					+ ((Oval) currentSlideshow.getSlides().get(0).getGraphicsList()
							.get(1)).isSolid());

		} else {
			System.out.println("Invalid slideshow found");
		}

		// needs rewriting
		//
		// for (Slide slide : slideshow) {
		// System.out.println("\tSlide ID: " + slide.getId());
		// System.out.println("\t\tTitle: " + slide.getTitle());
		// System.out.println("\t\tFilename: " + slide.getFilename());
		// System.out.println("\t\tDescription: " + slide.getDescription());
		// }
		//
	}

	public Server() {

		long startTime = System.nanoTime();

		try {
			this.currentSlideshow = new XMLReader(filename).getSlideshow();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		writeSlides();
		System.out.println((endTime - startTime) / 1000000);
		// System.out.println(currentSlideshow.getInfo().getVersion());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// new Server();
	}

}
