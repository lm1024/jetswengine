/** (c) Copyright by WaveMedia. */
package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import data.Question;
import data.Slide;

/**
 * Question Handler class for parsing question Tags from XML Slideshows.
 * 
 * @author dk666
 * @version 1.0 02/05/15
 */
public class QuestionHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
	private Question question;

	/** Creates a new QuestionHandler */
	public QuestionHandler(XMLReader reader, SlideHandler parent,
			Slide slide) {
		this.parentHandler = parent;
		this.reader = reader;
		this.slide = slide;
		
	}

	/**
	 * Called when the XML Parser encounters a start tag for a Question element.
	 * Assigns all the attributes of the question tag to a Question object.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		/* sort out element name if (no) namespace in use */
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		if(elementName.equals("question")) {
			question = new Question(attributes.getValue("id"),attributes.getValue("logfile"));
		} else if (elementName.equals("answer")) {	
			question.addAnswer(attributes.getValue("id"), Boolean.parseBoolean(attributes.getValue("correct")));
		} else {
			System.err.println("Unknown Question element encountered: " + elementName);
		}
	}
	
	/**
	 * Called when the XML Parser encounters a end tag for a Question element.
	 * Adds the question to the current slide.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		/* sort out element name if (no) namespace in use */
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}

		if (elementName.equals("question")) {
			slide.addQuestion(question);
			reader.setContentHandler(parentHandler);
		} else if (elementName.equals("answer")) {
			
		} else {
			System.err.println("Unknown element found");
		}
	}
}