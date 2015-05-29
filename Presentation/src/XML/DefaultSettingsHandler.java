/**
 * 
 */
package XML;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Defaults;
import Data.Slideshow;

/**
 * @author David
 *
 */
public class DefaultSettingsHandler extends DefaultHandler {

	private XMLReader reader;
	private ContentHandler parentHandler;
	private Defaults defaults;
	private StringBuffer contentBuffer = new StringBuffer();

	/**
	 * 
	 */
	public DefaultSettingsHandler(XMLReader reader, ContentHandler parent, Slideshow slideshow) {
		this.defaults = slideshow.getDefaults();
		this.parentHandler = parent;
		this.reader = reader;
	}

	/**
	 * Called by the parser when it encounters characters in the main body of an
	 * element.
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		contentBuffer.append(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		switch (elementName) {
		case "defaultsettings":
			defaults.RecalculateRatios();
			reader.setContentHandler(parentHandler);
			break;
		case "backgroundcolor":
			defaults.setBackgroundColor(contentBuffer.toString().trim());
			break;
		case "font":
			defaults.setFont(contentBuffer.toString().trim());
			break;
		case "fontsize":
			defaults.setFontSize(contentBuffer.toString().trim());
			break;
		case "fontcolor":
			defaults.setFontColor(contentBuffer.toString().trim());
			break;
		case "graphiccolor":
			defaults.setGraphicColor(contentBuffer.toString().trim());
			break;
		case "highlightcolor":
			defaults.setHighlightColor(contentBuffer.toString().trim());
			break;
		case "starttime":
			defaults.setStartTime(contentBuffer.toString().trim());
			break;
		case "duration":
			defaults.setDuration(contentBuffer.toString().trim());
			break;
		case "alignment":
			defaults.setAlignment(contentBuffer.toString().trim());
			break;
		case "scale":
			defaults.setScale(contentBuffer.toString().trim());
			break;
		case "rotation":
			defaults.setRotation(contentBuffer.toString().trim());
			break;
		case "cropx1":
			defaults.setCropX1(contentBuffer.toString().trim());
			break;
		case "cropy1":
			defaults.setCropY1(contentBuffer.toString().trim());
			break;
		case "cropx2":
			defaults.setCropX2(contentBuffer.toString().trim());
			break;
		case "cropy2":
			defaults.setCropY2(contentBuffer.toString().trim());
		case "originalxresolution":
			defaults.setOriginalXResolution(contentBuffer.toString().trim());
		case "originalyresolution":
			defaults.setOriginalYResolution(contentBuffer.toString().trim());
			break;
		default:
			break;
		}
		contentBuffer.setLength(0);
	}

}
