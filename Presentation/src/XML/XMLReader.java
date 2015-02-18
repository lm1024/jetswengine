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
import Data.TextFragment;

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
			switch (elementName) {
			case "slideshow":
				slideshow = new Slideshow();
				break;
			case "slide":
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
				break;
			case "defaultsettings":
				sectionName = elementName;
				defaults = new Defaults();
				break;
			case "documentinfo":
				sectionName = elementName;
				info = new DocumentInfo();
				break;
			}
			break;
		case "slide":

			break;
		default:
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

		} else
			switch (sectionName) {
			case "slide":
				switch (elementName) {
				case "text":
					if (contentBuffer != null) {
						TextFragment t = new TextFragment(contentBuffer
								.toString().trim());

					}
					break;
				case "image":
					break;
				case "video":
					break;
				case "graphic":
					break;
				case "sound":
					break;

				}
				break;
			case "documentinfo":
				switch (elementName) {
				case "author":
					info.setAuthor(contentBuffer.toString().trim());
					break;
				case "version":
					info.setVersion(contentBuffer.toString().trim());
					break;
				case "comment":
					info.setComment(contentBuffer.toString().trim());
					break;
				case "groupid":
					info.setGroupID(contentBuffer.toString().trim());
					break;
				default:
					break;
				}
				contentBuffer = null;
				break;
			case "defaultsettings":
				switch (elementName) {
				case "backgroundcolor":
					defaults.setBackgroundColour(contentBuffer.toString()
							.trim());
					break;
				case "font":
					defaults.setFont(contentBuffer.toString().trim());
					break;
				case "fontsize":
					try {
						defaults.setFontSize(Integer.parseInt(contentBuffer
								.toString().trim()));
					} catch (NumberFormatException e) {
						/* Do nothing if invalid font size specified */
					} catch (NullPointerException npe) {
						/* Do nothing if invalid font size specified */
					}
					break;
				case "fontcolor":
					defaults.setFontColour(contentBuffer.toString().trim());
					break;
				case "graphiccolor":
					defaults.setGraphicColour(contentBuffer.toString().trim());
					break;
				default:
					break;
				}
				contentBuffer = null;
				break;
			default:
				break;
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
