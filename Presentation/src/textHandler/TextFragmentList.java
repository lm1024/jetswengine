/** (c) Copyright by WaveMedia. */
package textHandler;

import java.util.ArrayList;

import data.TextFragment;


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

	public void add(TextFragment textFragment) {
		textFragmentList.add(textFragment);
	}
	
	public void addAll(TextFragment... fragments) {
		for(TextFragment tf : fragments) {
			textFragmentList.add(tf);
		}
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
