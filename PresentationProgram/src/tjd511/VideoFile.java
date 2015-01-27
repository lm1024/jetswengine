package tjd511;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VideoFile implements Serializable {
	
	private String ID;
	private String title;
	private String filename;
	private String coverFilename;
	private String directors;
	private String plot;
	
	public String getID() {
		return ID;
	}

	public String getTitle() {
		return title;
	}

	public String getFilename() {
		return filename;
	}
	
	public String getCoverFilename() {
		return coverFilename;
	}
	
	public String getDirectors() {
		return directors;
	}
	
	public String getPlot() {
		return plot;
	}
	
	public void setID(String IDTemp) {
		ID = IDTemp;
	}
	
	public void setTitle(String titleTemp) {
		title = titleTemp;
	}

	public void setFilename(String filenameTemp) {
		filename = filenameTemp;
	}
	
	public void setCoverFilename(String coverFilenameTemp) {
		coverFilename = coverFilenameTemp;
	}
	
	public void setDirectors(String directorsTemp) {
		directors = directorsTemp;
	}
	
	public void setPlot(String plotTemp) {
		plot = plotTemp;
	}
}
