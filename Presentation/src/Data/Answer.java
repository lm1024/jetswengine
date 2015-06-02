/** (c) Copyright by WaveMedia. */
package Data;

public class Answer {

	private String id = null;
	private Boolean correct;

	private int answerCount;

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
	 * 
	 */
	public void increaseAnswerCount() {
		answerCount++;
	}

	/**
	 * 
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
