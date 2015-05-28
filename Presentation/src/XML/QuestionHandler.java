/**
 * 
 */
package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import Data.Question;
import Data.Slide;

/**
 * @author David
 *
 */
public class QuestionHandler extends DefaultHandler {

	private Slide slide;
	private XMLReader reader;
	private SlideHandler parentHandler;
	private Question question;

	/**
	 * 
	 */
	public QuestionHandler(XMLReader reader, SlideHandler parent,
			Slide slide) {
		this.parentHandler = parent;
		this.reader = reader;
		this.slide = slide;
		
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		//TODO: Call start Element when handler is changed to parse duration
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
	
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
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
