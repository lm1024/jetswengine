/** (c) Copyright by WaveMedia. */
package tests;

import java.io.IOException;

import Data.Question;

import comms.CommsHandler;

/**
 * @author tjd511
 * 
 */
public class CommsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CommsHandler comms = null;

		Question question = new Question("Q1", "Q1");
		question.addAnswer("1", true);
		question.addAnswer("2", false);
		question.addAnswer("3", false);
		question.addAnswer("4", false);

		try {
			comms = new CommsHandler();
			System.out.println("Got here");
			comms.setCurrentQuestion(question);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(0);
			e.printStackTrace();
		}

		for (int i = 1; i <= 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Slept for " + i + " seconds.");
		}

		for (int i = 0; i < question.getNumberOfAnswers(); i++) {
			System.out.println("Question " + i + ": " + question.getAnswer(i).getAnswerCount());
		}
		
		for (String q : comms.getRecievedQuestionList()) {
			System.out.println("Question: " + q);
		}

	}

}
