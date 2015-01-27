package tjd511;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class Parser extends DefaultHandler {

	private StringBuffer contentBuffer;

	private List<VideoFile> videoList = new ArrayList<VideoFile>();

	public List<VideoFile> getList(String inputFile) {
		try {
			// use the default parser
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			// parse the input
			saxParser.parse(inputFile, this);
			
		} catch (FileNotFoundException e) {
			VideoFile failFile = new VideoFile();
			failFile.setID("FileNotFoundError");
			videoList.add(failFile);
		} catch (SAXParseException e) {
			VideoFile failFile = new VideoFile();
			failFile.setID("SAXParseError");
			videoList.add(failFile);
		}
		catch (ParserConfigurationException pce) {
			System.out.println("Parser Configured Incorrectly.");
			System.exit(-1);
		}
		catch (SAXException saxe) {
			System.out.println("SAX Error.");
			System.exit(-1);
		}
		catch (IOException ioe) {
			System.out.println("Error whilst parsing.");
			System.exit(-1);
		}
		
		return videoList;
	}

	public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}

		if (elementName == "video") {
			videoList.add(new VideoFile());
		}

		// if there are attributes, loop over them
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				// sort out attribute name
				String attributeName = attrs.getLocalName(0);
				if ("".equals(attributeName))
					attributeName = attrs.getQName(0);

				if (attributeName == "id")
					videoList.get(videoList.size() - 1).setID(
							new String(attrs.getValue(0)));
			}
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		String elementName = localName;

		if ("".equals(elementName))
			elementName = qName;

		if (elementName == "title")
			videoList.get(videoList.size() - 1).setTitle(new String(contentBuffer));
		else if (elementName == "filename")
			videoList.get(videoList.size() - 1).setFilename(new String(contentBuffer));
		else if (elementName == "cover")
			videoList.get(videoList.size() - 1).setCoverFilename(new String(contentBuffer));
		else if (elementName == "directors")
			videoList.get(videoList.size() - 1).setDirectors(new String(contentBuffer));
		else if (elementName == "plot")
			videoList.get(videoList.size() - 1).setPlot(new String(contentBuffer));
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		String newContent = new String(ch, start, length).trim();

		if (contentBuffer == null)
			contentBuffer = new StringBuffer(newContent);
		else {
			contentBuffer.setLength(0);
			contentBuffer.append(newContent);
		}
	}
}
