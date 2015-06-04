/** (c) Copyright by WaveMedia. */
package data;

public class DocumentInfo {

	private String author;
	private String version;
	private String comment;
	private String groupID;

	public DocumentInfo() {
		//Exists to prevent instantiation
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the groupID
	 */
	public String getGroupID() {
		return groupID;
	}

	/**
	 * @param groupID
	 *            the groupID to set
	 */
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	
	public void printInfo() {
		System.out.println("Start of Document Info:");
		System.out.println("Author: " + author);
		System.out.println("Version: " + version);
		System.out.println("Comment: " + comment);
		System.out.println("Group ID: "+ groupID);
		System.out.println("End of Document Info\n");
	}

}
