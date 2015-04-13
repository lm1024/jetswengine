/** (c) Copyright by WaveMedia. */
package textHandler;

import java.util.ArrayList;
import javafx.scene.Group;

/**
 * Class for handling multiple textboxes on a javafx group.
 * 
 * @author tjd511
 * @version 2.0 02/03/2015
 */
public class TextHandler {
	private Group group;

	private ArrayList<Text> texts;

	/** Constructor Method */
	public TextHandler(Group group) {
		this.group = group;

		/* Initialise the list of all the graphics */
		this.texts = new ArrayList<Text>();
	}

	/**
	 * Method creates a new textbox, and draws it on the group specified in the
	 * constructor.
	 * 
	 * @param textBox
	 *            a textBox object containing all the information about the
	 *            textBox to be drawn. Must be formed using the TextBuilder.
	 * @see {@link textHandler.TextObject}
	 */
	public void createGraphic(TextObject textBox) {
		texts.add(new Text(group));
		texts.get(texts.size() - 1).drawText(textBox);
	}

	/**
	 * Method toggles the visibility of a textbox.
	 * 
	 * @param graphicNumber
	 *            the index of the textbox to be changed
	 * @param visible
	 *            boolean containing the state of the visibility of the textbox
	 */
	public void setVisible(int graphicNumber, boolean visible) {
		if (graphicNumber < texts.size() && graphicNumber >= 0) {
			texts.get(graphicNumber).setVisible(visible);
		}
	}

	/**
	 * Method to send a particular textbox to the back of the group.
	 * 
	 * @param graphicNumber
	 *            the index of the textbox to be sent to back
	 */
	public void sendToBack(int graphicNumber) {
		if (graphicNumber < texts.size() && graphicNumber >= 0) {
			texts.get(graphicNumber).toBack();
		}
	}

	/**
	 * Method to send a particular textbox to the front of the group.
	 * 
	 * @param graphicNumber
	 *            the index of the textbox to be sent to front
	 */
	public void sendToFront(int graphicNumber) {
		if (graphicNumber < texts.size() && graphicNumber >= 0) {
			texts.get(graphicNumber).toFront();
		}
	}

	/**
	 * Method gets the number of textboxes.
	 * 
	 * @return the current number of instantiated textboxes
	 */
	public int getTextCount() {
		return texts.size();
	}

	/**
	 * Method clears the current array of textboxes
	 */
	public void clearTexts() {
		texts.clear();
	}
}