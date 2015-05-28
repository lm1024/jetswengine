package GUI;

import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Rectangle2D;


public class UserPreferences {

	private int screenId = 0;
	private Rectangle2D bounds;
	private boolean isAudioPause = false;
	private boolean isVideoPause = false;
	private boolean isSlideAuto = false;
	private boolean isQuestionsLogged = false;
	private boolean isOTSLogged = false;
	private File initDir; // directory for open file
	
	/* Files to store user data in */
	private ArrayList<String> fileList = new ArrayList<String>();
	private ArrayList<String> buttonInfo = new ArrayList<String>();

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public boolean isAudioPause() {
		return isAudioPause;
	}

	public void setAudioPause(boolean isAudioPause) {
		this.isAudioPause = isAudioPause;
	}

	public boolean isVideoPause() {
		return isVideoPause;
	}

	public void setVideoPause(boolean isVideoPause) {
		this.isVideoPause = isVideoPause;
	}

	public boolean isSlideAuto() {
		return isSlideAuto;
	}

	public void setSlideAuto(boolean isSlideAuto) {
		this.isSlideAuto = isSlideAuto;
	}

	public boolean isQuestionsLogged() {
		return isQuestionsLogged;
	}

	public void setQuestionsLogged(boolean isQuestionsLogged) {
		this.isQuestionsLogged = isQuestionsLogged;
	}

	public boolean isOTSLogged() {
		return isOTSLogged;
	}

	public void setOTSLogged(boolean isOTSLogged) {
		this.isOTSLogged = isOTSLogged;
	}

	public File getInitDir() {
		return initDir;
	}

	public void setInitDir(File initDir) {
		this.initDir = initDir;
	}

	public ArrayList<String> getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList<String> fileList) {
		this.fileList = fileList;
	}

	public ArrayList<String> getButtonInfo() {
		return buttonInfo;
	}

	public void setButtonInfo(ArrayList<String> buttonInfo) {
		this.buttonInfo = buttonInfo;
	}

	public Rectangle2D getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle2D bounds) {
		this.bounds = bounds;
	}


}