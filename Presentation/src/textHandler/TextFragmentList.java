/** (c) Copyright by WaveMedia. */
package textHandler;

import java.util.ArrayList;

import textHandler.TextObject.TextFragmentBuilder;

import Data.TextFragment;

/**
 * Class to handle a list of text fragments for within a text box.
 * 
 * @author tjd511
 * @version 1.0 02/03/2015
 */
public class TextFragmentList {

	private ArrayList<TextFragment> textFragmentList;

	public TextFragmentList() {
		textFragmentList = new ArrayList<TextFragment>();
	}

	public void add(TextFragmentBuilder textFragmentBuilder) {
		textFragmentList.add(textFragmentBuilder.build());
	}

	ArrayList<TextFragment> getList() {
		return textFragmentList;
	}

	public void clear() {
		textFragmentList.clear();
	}

	public int size() {
		return textFragmentList.size();
	}

}
