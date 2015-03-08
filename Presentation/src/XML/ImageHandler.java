/**
 * 
 */
package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Image;
import Data.Slide;

/**
 * @author David
 *
 */
public class ImageHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
	private Image image;
	private StringBuffer contentBuffer = new StringBuffer();

	/**
	 * 
	 */
	public ImageHandler(XMLReader reader, SlideHandler parent,
			Slide slide) {
		this.parentHandler = parent;
		this.slide = slide;
		this.reader = reader;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		//TODO: Call start Element when handler is changed to parse duration
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if(elementName.equals("image")) {
			image = new Image(parentHandler.getDefaults());
			image.setSourceFile(attributes.getValue("sourcefile"));
			image.setCropX1(attributes.getValue("cropx1"));
			image.setCropX2(attributes.getValue("cropx2"));
			image.setCropY1(attributes.getValue("cropy1"));
			image.setCropY2(attributes.getValue("cropy2"));
			image.setDuration(attributes.getValue("duration"));
			image.setFlipHorizontal(attributes.getValue("fliphorizontal"));
			image.setFlipVertical(attributes.getValue("flipvertical"));
			image.setRotation(attributes.getValue("rotation"));
			image.setScale(attributes.getValue("scale"));
			image.setStartTime(attributes.getValue("starttime"));
			image.setXStart(attributes.getValue("xstart"));
			image.setYStart(attributes.getValue("ystart"));
		} else {
			System.err.println("Unknown element encountered");
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}

		if (elementName.equals("image")) {
			slide.add(image);
			reader.setContentHandler(parentHandler);
		} else {
			System.err.println("Unknown element found");
		}

		contentBuffer.setLength(0);
	}

}
