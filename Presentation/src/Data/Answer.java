/** (c) Copyright by WaveMedia. */
package Data;

/**
 * Class that contains an answer to a question.
 * 
 * @author tjd511
 * @version 2.0 1/06/2015
 */
public class Answer {

	/*
	 * Variables to keep track of the current answers name, if it is correct and
	 * the number of recieved responces.
	 */
	private String id;
	private Boolean correct;
	private int answerCount;

	/**
	 * Constructor for this class.
	 * 
	 * @param id
	 *            the id of the question
	 * @param correct
	 *            boolean containing if the answer is correct or not.
	 */
	public Answer(String id, boolean correct) {
		/* Set the variables based upon the constructor arguments */
		this.id = id;
		this.correct = correct;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the correct
	 */
	public Boolean getCorrect() {
		return correct;
	}

	/**
	 * Increase the current count for this answer.
	 */
	public void increaseAnswerCount() {
		answerCount++;
	}

	/**
	 * Decrease the current count for this answer.
	 */
	public void decreaseAnswerCount() {
		answerCount--;
	}

	/**
	 * @return answerCount
	 */
	public int getAnswerCount() {
		return answerCount;
	}

}
