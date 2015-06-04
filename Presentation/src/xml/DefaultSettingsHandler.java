/** (c) Copyright by WaveMedia. */
package xml;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import data.Defaults;
import data.Slideshow;


/**
 * Default Settings Handler class for parsing defaultsettings Tags from XML
 * Slideshows.
 * 
 * @author dk666
 * @version 1.3 02/06/15
 */
public class DefaultSettingsHandler extends DefaultHandler {

	private XMLReader reader;
	private ContentHandler parentHandler;
	private Defaults defaults;

	/* String buffer for storing the content of an element */
	private StringBuffer contentBuffer = new StringBuffer();

	/** Creates a new DefaultSettingsHandler */
	public DefaultSettingsHandler(XMLReader reader, ContentHandler parent,
			Slideshow slideshow) {
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

	/**
	 * Called when the XML Parser encounters a end tag for an element. Assigns
	 * the content of each tag to its respective variable in the data structure.
	 * Recalculates ratios after all variables have been assigned.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		/* sort out element name if (no) namespace in use */
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
			break;
		case "audiowidth":
			defaults.setAudioWidth(contentBuffer.toString().trim());
			break;
		case "videowidth":
			defaults.setVideoWidth(contentBuffer.toString().trim());
			break;
		case "audiocontrolsvisible":
			defaults.setAudioHasVisibleControls(contentBuffer.toString().trim());
			break;
		case "originalxresolution":
			defaults.setOriginalXResolution(contentBuffer.toString().trim());
			break;
		case "originalyresolution":
			defaults.setOriginalYResolution(contentBuffer.toString().trim());
			break;
		case "xaspectratio":
			defaults.setxAspectRatio(contentBuffer.toString().trim());
			break;
		case "yaspectratio":
			defaults.setyAspectRatio(contentBuffer.toString().trim());
			break;
		case "shapesolidity":
			defaults.setShapeSolidity(contentBuffer.toString().trim());
			break;
		case "stopvalue":
			defaults.setStopValue(contentBuffer.toString().trim());
			break;
		case "outlinethickness":
			defaults.setOutlineThickness(contentBuffer.toString().trim());
			break;
		case "outlinecolor":
			defaults.setOutlineColor(contentBuffer.toString().trim());
			break;
		default:
			break;
		}
		/* Clear content buffer after each tag */
		contentBuffer.setLength(0);
	}

}
