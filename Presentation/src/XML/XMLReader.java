package XML;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Data.Defaults;
import Data.DocumentInfo;
import Data.Slide;
import Data.Slideshow;

/**
 * enum type for keeping track of when we need to store content in certain elements
 *
 */

/**
 * Main class for reading XML file of students within a university
 * 
 */
public class XMLReader extends DefaultHandler {
	private Slideshow slideshow;
	private Slide currentSlide;
	private Defaults defaults;
	private DocumentInfo info;
	private StringBuffer contentBuffer;
	private String sectionName = "";

	public XMLReader(String filename) throws IOException {
		readXMLFile(filename);
		writeSlides();
	}

	public Slideshow getSlideshow() {
		return slideshow;
	}

	/**
	 * This method gets the parser and then starts the parser reading through
	 * the XML file.
	 * 
	 * As the parser reads through the XML file, it will call the appropriate
	 * methods within this class as it encounters new elements, end elements,
	 * characters in the main content of and element, etc.
	 * 
	 * This method is public so that a controlling class can build an instance
	 * of this class and then call this method to read the XML file and return
	 * the data.
	 * 
	 */
	public Slideshow readXMLFile(String inputFile) throws IOException {
		try {
			// use the default parser
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			// parse the input
			saxParser.parse(inputFile, this);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException saxe) {
			saxe.printStackTrace();
		}

		return slideshow;
	}

	/**
	 * Called by the parser when it encounters the start of the XML file.
	 */
	public void startDocument() throws SAXException {
		System.out.println("Starting to process document.");
	}

	/**
	 * Called by the parser when it encounters any start element tag.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		System.out.println("\tFound the start of an element (" + elementName
				+ ") ...");

		switch (sectionName) {
		case "":
			if (elementName.equals("slideshow")) {
				slideshow = new Slideshow();
			} else if (elementName.equals("slide")) {
				sectionName = elementName;
				currentSlide = new Slide();
				try {
					currentSlide.setDuration(Float.parseFloat(attributes
							.getValue("duration")));
				} catch (NumberFormatException e) {
					/* Do nothing if invalid duration is specified */
				} catch (NullPointerException npe) {
					/* Do nothing if invalid duration is specified */
				}
			} else if (elementName.equals("defaultsettings")) {
				sectionName = elementName;
				defaults = new Defaults();
			} else if (elementName.equals("documentinfo")) {
				sectionName = elementName;
				info = new DocumentInfo();
			}
			break;
		case "defaultsettings":

			break;
		case "a":

			break;
		case "b":

			break;
		case "c":

			break;

		}

	}

	/**
	 * Called by the parser when it encounters characters in the main body of an
	 * element.
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String newContent = new String(ch, start, length);
		if (contentBuffer == null) {
			contentBuffer = new StringBuffer(newContent);
		} else {
			contentBuffer.append(newContent);
		}

	}

	/**
	 * Called by the parser when it encounters any end element tag.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		System.out.println("\tFound the end of an element (" + elementName
				+ ") ...");

		if (elementName.equals(sectionName)) {
			switch (sectionName) {
			case "slide":
				slideshow.addSlide(currentSlide);
				currentSlide = null;
				break;
			case "documentinfo":
				slideshow.setInfo(info);
				info = null;
				break;
			case "defaultsettings":
				slideshow.setDefaults(defaults);
				defaults = null;
				break;
			default:

				break;

			}
			sectionName = "";

		} else if (sectionName.equals("slide")) {
			contentBuffer = null;
		} else if (sectionName.equals("documentinfo")) {
			if (elementName.equals("author")) {
				info.setAuthor(contentBuffer.toString().trim());
				contentBuffer = null;
			} else if (elementName.equals("version")) {
				info.setVersion(contentBuffer.toString().trim());
				contentBuffer = null;
			} else if (elementName.equals("comment")) {
				info.setComment(contentBuffer.toString().trim());
				contentBuffer = null;
			}
		} else if (sectionName.equals("defaultsettings")) {
			if (elementName.equals("backgroundcolour")) {
				defaults.setBackgroundColour(contentBuffer.toString().trim());
				contentBuffer = null;
			} else if (elementName.equals("font")) {
				defaults.setFont(contentBuffer.toString().trim());
				contentBuffer = null;
			} else if (elementName.equals("fontsize")) {
				try {
					defaults.setFontSize(Integer.parseInt(contentBuffer
							.toString().trim()));
				} catch (NumberFormatException e) {
					/* Do nothing if invalid font size specified */
				} catch (NullPointerException npe) {
					/* Do nothing if invalid font size specified */
				}
				contentBuffer = null;
			} else if (elementName.equals("fontcolor")) {
				defaults.setFontColour(contentBuffer.toString().trim());
				contentBuffer = null;
			} else if (elementName.equals("graphiccolor")) {
				defaults.setGraphicColour(contentBuffer.toString().trim());
				contentBuffer = null;
			}
		}
	}

	/**
	 * Called by the parser when it encounters the end of the XML file.
	 */
	public void endDocument() throws SAXException {
		System.out.println("Finished processing document.");
	}

	/**
	 * Utility method for this class, to output a quick check on the contents
	 * that were read in from the XML file.
	 */
	private void writeSlides() {
		if (slideshow != null) {
			System.out.println("\tSlideshow Author: "
					+ slideshow.getInfo().getAuthor());
			System.out.println("\tSlideshow Version: "
					+ slideshow.getInfo().getVersion());
			System.out.println("\tSlideshow Comment: "
					+ slideshow.getInfo().getComment());
			System.out.println("\tSlideshow groupid: "
					+ slideshow.getInfo().getGroupID());
			System.out.println("\tSlide 1 duration: "
					+ slideshow.getSlides().get(0).getDuration());
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
}
