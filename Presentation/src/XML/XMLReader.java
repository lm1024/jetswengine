package XML;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
	private StringBuffer contentBuffer;
	private String sectionName = "";
	private String subSectionName = "";

	public XMLReader(String filename) throws IOException {
		slideshow = new Slideshow();
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
		if (sectionName.equals("")) {
			if (elementName.matches("documentinfo|defaultsettings|slide")) {
				sectionName = elementName;
			}
			if (elementName.equals("slide")) {
				try {
					slideshow.addSlide(Float.parseFloat(attributes
							.getValue("duration")));
				} catch (Exception e) {
					slideshow.addSlide(0);
				}
			}
		} else if (sectionName.equals("slide")) {
			switch(elementName) {
			case "text":
				slideshow.getCurrentSlide().newText();
				break;
			}
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
			sectionName = "";
		} else
			switch (sectionName) {
			case "slide":
				switch (elementName) {
				case "text":
					if (contentBuffer != null) {
						System.out.println(contentBuffer.toString().trim());
						slideshow.getCurrentSlide().getCurrentText().addText(
								contentBuffer.toString().trim());

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
					slideshow.getInfo().setAuthor(
							contentBuffer.toString().trim());
					break;
				case "version":
					slideshow.getInfo().setVersion(
							contentBuffer.toString().trim());
					break;
				case "comment":
					slideshow.getInfo().setComment(
							contentBuffer.toString().trim());
					break;
				case "groupid":
					slideshow.getInfo().setGroupID(
							contentBuffer.toString().trim());
					break;
				default:
					break;
				}
				contentBuffer = null;
				break;
			case "defaultsettings":
				switch (elementName) {
				case "backgroundcolor":
					slideshow.getDefaults().setBackgroundColour(
							contentBuffer.toString().trim());
					break;
				case "font":
					slideshow.getDefaults().setFont(
							contentBuffer.toString().trim());
					break;
				case "fontsize":
					try {
						slideshow.getDefaults().setFontSize(
								Integer.parseInt(contentBuffer.toString()
										.trim()));
					} catch (NumberFormatException e) {
						/* Do nothing if invalid font size specified */
					} catch (NullPointerException npe) {
						/* Do nothing if invalid font size specified */
					}
					break;
				case "fontcolor":
					slideshow.getDefaults().setFontColour(
							contentBuffer.toString().trim());
					break;
				case "graphiccolor":
					slideshow.getDefaults().setGraphicColour(
							contentBuffer.toString().trim());
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
			System.out.println("\tSlide 1 duration: "
					+ slideshow.getSlides().get(0).getTextList().get(0).getTextFragments().get(0).getText());
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
