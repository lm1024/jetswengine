package Data;

import java.util.ArrayList;

/**
 * @author tjd511
 * @version 1.0 14/04/2015
 */
/**
 * @author tjd511
 * 
 */
public class Question {
	private String id = null;
	private String logfile = null;
	private ArrayList<Answer> answers;

	/**
	 * 
	 */
	public Question(String id, String logfile) {
		/* Set the variables based upon the constructor arguments */
		this.id = id;
		this.logfile = logfile;

		/* Instantiate the answer list */
		answers = new ArrayList<Answer>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the logfile
	 */
	public String getLogfile() {
		return logfile;
	}

	/**
	 * @return the answer
	 */
	public Answer getAnswer(int index) {
		return answers.get(index);
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers
	 *            the answers to set
	 */
	public void addAnswer(String answerId, boolean correct) {
		/* Make a new answer and add it to the list of answers */
		answers.add(new Answer(answerId, correct));
	}

	/**
	 * Validates that the question has answers stored inside of it.
	 * 
	 * @return a boolean containing if the question has at least one answer that
	 *         is non zero.
	 */
	public boolean hasAnswerData() {
		for (Answer answer : answers) {
			if (answer.getAnswerCount() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return
	 */
	public int getNumberOfAnswers() {
		return answers.size();
	}

	/**
	 * @param index
	 */
	public void increaseAnswerCount(int index) {
		if (index < answers.size() && index >= 0) {
			answers.get(index).increaseAnswerCount();
		}
	}

}
