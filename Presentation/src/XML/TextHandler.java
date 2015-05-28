package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import Data.Defaults;
import Data.Text;
import Data.Slide;
import Data.TextFragment;

public class TextHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
	private Text text;
	private StringBuffer contentBuffer = new StringBuffer();

	public TextHandler(XMLReader reader, SlideHandler parent,
			Slide slide) {
		this.parentHandler = parent;
		this.slide = slide;
		this.reader = reader;
	}
	
	public Defaults getDefaults() {
		return parentHandler.getDefaults();
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		contentBuffer.setLength(0);
		if(elementName.equals("text")) {
			text = new Text(getDefaults());
			text.setSourceFile(attributes.getValue("sourcefile"));
			text.setDuration(attributes.getValue("duration"));
			text.setStartTime(attributes.getValue("starttime"));
			text.setXStart(attributes.getValue("xstart"));
			text.setYStart(attributes.getValue("ystart"));
			text.setXEnd(attributes.getValue("xend"));
			text.setYEnd(attributes.getValue("yend"));
			text.setAlignment(attributes.getValue("alignment"));
			text.setFont(attributes.getValue("font"));
			text.setFontColor(attributes.getValue("fontcolor"));
			text.setFontSize(attributes.getValue("fontsize"));
			text.setBackgroundColor(attributes.getValue("backgroundcolor"));
			text.setHighlightColor(attributes.getValue("highlightcolor"));
		} else if(elementName.equals("richtext")) {
			reader.setContentHandler(new TextFragmentHandler(reader,this,text));
			reader.getContentHandler().startElement(uri, localName, qName, attributes);
		} else  {
			System.err.println("Unknown start element encountered: " + elementName);
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
			contentBuffer.append(ch,start,length);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if (elementName.equals("text")) {
			if(!contentBuffer.toString().trim().equals("")) {
				TextFragment tf = new TextFragment(parentHandler.getDefaults());
				tf.setFont(text.getFont());
				tf.setFontColor(text.getFontColor());
				tf.setFontSize(text.getFontSize());
				tf.setText(contentBuffer.toString().trim());
				text.addTextFragment(tf);
			}
			slide.add(text);
			reader.setContentHandler(parentHandler);
		} else {
			System.err.println("Unknown Text end element encountered: " + elementName);
		}
	}

}
