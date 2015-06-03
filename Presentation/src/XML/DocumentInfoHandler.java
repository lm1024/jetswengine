/** (c) Copyright by WaveMedia. */
package XML;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.DocumentInfo;
import Data.Slideshow;

/**
 * Document Info Handler class for parsing documentinfo Tags from XML
 * Slideshows.
 * 
 * @author dk666
 * @version 1.3 02/06/15
 */
public class DocumentInfoHandler extends DefaultHandler {

	private XMLReader reader;
	private ContentHandler parentHandler;
	private DocumentInfo info;

	/* String buffer for storing the content of an element */
	private StringBuffer contentBuffer = new StringBuffer();

	/** Creates a new DocumentInfoHandler */
	public DocumentInfoHandler(XMLReader reader, ContentHandler parent,
			Slideshow slideshow) {
		this.parentHandler = parent;
		this.reader = reader;
		this.info = slideshow.getInfo();
	}

	/**
	 * Called by the parser when it encounters characters in the main body of an
	 * element.
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		contentBuffer.append(ch, start, length);
	}

	/**
	 * Called when the XML Parser encounters a end tag for an element. Assigns
	 * the content of each tag to its respective variable in the data structure.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		switch (elementName) {
		case "documentinfo":
			reader.setContentHandler(parentHandler);
			break;
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
		/* Clear content buffer after each tag */
		contentBuffer.setLength(0);
	}

}
