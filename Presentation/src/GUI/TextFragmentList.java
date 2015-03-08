package GUI;

import java.util.ArrayList;

import Data.TextFragment;
import GUI.TextBox.TextFragmentBuilder;

public class TextFragmentList {

	private ArrayList<TextFragment> textFragmentList;
	
	public TextFragmentList() {
		textFragmentList = new ArrayList<TextFragment>();
	}
	
	public void add(TextFragmentBuilder textFragmentBuilder) {
		textFragmentList.add(textFragmentBuilder.build());
	}
	
	public ArrayList<TextFragment> getList() {
		return textFragmentList;
	}
	
	public void clear() {
		textFragmentList.clear();
	}
	
	public int size() {
		return textFragmentList.size();
	}
	
}
