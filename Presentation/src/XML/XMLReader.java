package XML;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Data.Slideshow;

public class XMLReader extends DefaultHandler {
	private Slideshow slideshow;
	private StringBuffer contentBuffer;
	private String sectionName = "";
	private HashMap<String, String> currentObject = new HashMap<String, String>();

	public XMLReader(String filename) throws IOException {
		slideshow = new Slideshow();
		readXMLFile(filename);
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
			currentObject.put("type", elementName.equals("cyclicshading") ? currentObject.get("type"): elementName);
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
				System.err.println("sourcefile not yet implemented. Delete this line when it is.");
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
				break;
			case "audio":
				if (parse(currentObject, attributes, "sourcefile", "xstart",
						"ystart")) {
					System.err.println("Required attribute missing");
					currentObject.clear();
					break;
				}
				parse(currentObject, attributes, "starttime");
				break;
			case "video":
				if (parse(currentObject, attributes, "sourcefile", "xstart",
						"ystart")) {
					System.err.println("Required attribute missing");
					currentObject.clear();
					break;
				}
				parse(currentObject, attributes, "starttime");
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
				case "richtext":
					/* Intentional fall through */
				case "text":
					if (!contentBuffer.toString().trim().equals("")) {
						currentObject.put("type", "textfragmentend");
						currentObject.put("text", contentBuffer.toString()
								.trim());
						slideshow.add(currentObject);
						currentObject.clear();
					}

					if (elementName.equals("text")) {
						currentObject.put("type", "textend");
						slideshow.add(currentObject);
						currentObject.clear();
					}

					break;
				case "graphic":
					slideshow.add(currentObject);
					currentObject.clear();
					break;
				case "image":
					slideshow.add(currentObject);
					currentObject.clear();
					break;
				case "video":
					slideshow.add(currentObject);
					currentObject.clear();
					break;
				case "audio":
					slideshow.add(currentObject);
					currentObject.clear();
					break;
				default:
					break;
				}
				break;
			case "defaultsettings":
				/* Intentional fall through */
			case "documentinfo":
				currentObject.put(elementName, contentBuffer.toString().trim());
				break;
			default:
				break;
			}
		contentBuffer = null;
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
}
