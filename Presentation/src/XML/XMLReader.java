package XML;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Data.Oval;
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
	private StringBuffer contentBuffer;
	private String sectionName = "";
	private HashMap<String, String> currentObject = new HashMap<String, String>();

	// private String subSectionName = "";

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
		// System.out.println("Starting to process document.");
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
		/*
		 * System.out.println("\tFound the start of an element (" + elementName
		 * + ") ...");
		 */
		if (sectionName.equals("")) {
			if (elementName.matches("documentinfo|defaultsettings|slide")) {
				sectionName = elementName;
				if (elementName.equals("slide")) {
					try {
						slideshow.addSlide(Float.parseFloat(attributes
								.getValue("duration")));
					} catch (Exception e) {
						slideshow.addSlide();
					}
				} else {
					currentObject.put("type", elementName);
				}
			}
		} else if (sectionName.equals("slide")) {
			currentObject.put(
					"type",
					elementName.equals("cyclicshading") ? currentObject
							.get("type") : elementName);
			switch (elementName) {
			case "text":
				if (parse(currentObject, attributes, "xstart", "ystart")) {
					System.err.println("Required attribute missing");
					currentObject.clear();
					break;
				}
				parse(currentObject, attributes, "sourcefile", "font",
						"fontsize", "fontcolor", "duration", "starttime",
						"alignment");
				// System.out
				// .println("sourcefile not yet implemented.\ndelete this line when it is.");
				slideshow.add(currentObject);
				currentObject.clear();
				break;
			case "image":
				if (parse(currentObject, attributes, "sourcefile", "xstart",
						"ystart")) {
					System.err.println("Required attribute missing");
					currentObject.clear();
					break;
				}
				parse(currentObject, attributes, "scale", "duration",
						"starttime", "rotate", "fliphorizontal",
						"flipvertical", "cropx1", "cropx2", "cropy1", "cropy2");
				slideshow.add(currentObject);
				currentObject.clear();
				break;
			case "audio":
				if (parse(currentObject, attributes, "sourcefile", "xstart",
						"ystart")) {
					System.err.println("Required attribute missing");
					currentObject.clear();
					break;
				}
				parse(currentObject, attributes, "starttime");
				slideshow.add(currentObject);
				currentObject.clear();
				break;
			case "video":
				if (parse(currentObject, attributes, "sourcefile", "xstart",
						"ystart")) {
					System.err.println("Required attribute missing");
					currentObject.clear();
					break;
				}
				parse(currentObject, attributes, "starttime");
				slideshow.add(currentObject);
				currentObject.clear();
				break;
			case "graphic":
				parse(currentObject, attributes, "type", "xstart", "ystart",
						"yend", "xend", "solid", "graphiccolor", "duration",
						"subscript", "case");
				break;
			case "richtext":
				parse(currentObject, attributes, "font", "fontsize",
						"fontcolor", "b", "i", "u", "strikethrough",
						"superscript", "subscript", "case", "highlightcolor");
				slideshow.add(currentObject);
				currentObject.clear();
				break;
			case "cyclicshading":
				break;
			case "oval":
				/* Intentional fall through */
			case "rectangle":
				/* Intentional fall through */
			case "line":
				/* Intentional fall through */
			case "itriangle":
				currentObject.put("type", elementName);
				parse(currentObject, attributes, "type", "xstart", "ystart",
						"yend", "xend", "solid", "graphiccolor", "duration",
						"subscript", "case");
				break;
			default:
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
		/*
		 * System.out.println("\tFound the end of an element (" + elementName +
		 * ") ...");
		 */
		if (elementName.equals(sectionName)) {
			slideshow.add(currentObject);
			currentObject.clear();
			sectionName = "";
		} else
			switch (sectionName) {
			case "slide":
				switch (elementName) {
				case "text":
					if (!contentBuffer.toString().trim().equals("")) {
						currentObject.put("type", "textfragmentend");
						currentObject.put("text", contentBuffer.toString()
								.trim());
						slideshow.add(currentObject);
						currentObject.clear();
					}
					currentObject.put("type", "textend");
					slideshow.add(currentObject);
					currentObject.clear();
					break;
				case "graphic":
					slideshow.add(currentObject);
					currentObject.clear();
					break;
				case "richtext":
					currentObject.put("type", "textfragmentend");
					currentObject.put("text", contentBuffer.toString().trim());
					slideshow.add(currentObject);
					currentObject.clear();
					break;
				case "image":
					// Only required if image support is expanded
					break;
				case "video":
					// Only required if video support is expanded
					break;
				case "sound":
					// Only required if sound support is expanded
					break;
				default:
					break;
				}
				contentBuffer = null;
				break;
			case "defaultsettings":
				/* Intentional fall through */
			case "documentinfo":
				currentObject.put(elementName, contentBuffer.toString().trim());
				contentBuffer = null;
				break;
			default:
				break;
			}
	}

	public boolean parse(HashMap<String, String> hashMap,
			Attributes attributes, String... strings) {
		boolean errors = false;
		String temp;
		for (String string : strings) {
			temp = attributes.getValue(string);
			if (temp != null) {
				hashMap.put(string, temp);
			} else {
				errors = true;
			}
		}
		return errors;
	}

	/**
	 * Called by the parser when it encounters the end of the XML file.
	 */
	public void endDocument() throws SAXException {
		// System.out.println("Finished processing document.");
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
			System.out.println("\tSlide 1 text 1.1: "
					+ slideshow.getSlides().get(0).getTextList().get(0)
							.getTextFragments().get(0).getText());
			System.out.println("\tSlide 1 text 1.1 case: "
					+ slideshow.getSlides().get(0).getTextList().get(0)
							.getTextFragments().get(0).getTextCase());
			System.out.println("\tSlide 1 text 1.1 font: "
					+ slideshow.getSlides().get(0).getTextList().get(0)
							.getTextFragments().get(0).getFont());
			System.out.println("\tSlide 1 image 1: "
					+ slideshow.getSlides().get(0).getImagesList().get(0)
							.getSourceFile());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getSourceFile());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getxStart());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getyStart());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getScale());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getDuration());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getStartTime());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getRotation());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getFlipHorizontal());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getFlipVertical());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getCropX1());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getCropY1());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getCropX2());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getImagesList().get(1)
							.getCropY2());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getGraphicsList().get(0)
							.getClass().getSimpleName().toLowerCase());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getGraphicsList().get(0)
							.getxStart());
			System.out.println("\tSlide 1 image 2: "
					+ ((Oval) slideshow.getSlides().get(0).getGraphicsList()
							.get(0)).getxEnd());
			System.out.println("\tSlide 1 image 2: "
					+ slideshow.getSlides().get(0).getGraphicsList().get(0)
							.getyStart());
			System.out.println("\tSlide 1 image 2: "
					+ ((Oval) slideshow.getSlides().get(0).getGraphicsList()
							.get(1)).getyEnd());
			System.out.println("\tSlide 1 image 2: "
					+ ((Oval) slideshow.getSlides().get(0).getGraphicsList()
							.get(0)).isSolid());
			System.out.println("\tSlide 1 image 2: "
					+ ((Oval) slideshow.getSlides().get(0).getGraphicsList()
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
}
