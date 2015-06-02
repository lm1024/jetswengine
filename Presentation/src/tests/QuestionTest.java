package tests;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import Data.Question;

public class QuestionTest {

	private Question question;
	
	private static final String filename = "test";
	
	private static final String questionID = "Example question ID";
	
	@Before
	public void setUp() throws Exception {
		question = new Question(questionID, filename);
	}

	@Test
	public void testIdAndLogfile() {
		assertTrue(question.getId() == questionID);
		assertTrue(question.getLogfile() == filename);
	}
	
	@Test
	public void testAnswers() {
		question.addAnswer("1", true);
		question.addAnswer("2", false);
		
		question.increaseAnswerCount(0, "111.111.111.111");
		
		assertTrue(question.getAnswer(0).getId() == "1");
		assertTrue(question.getAnswer(0).getCorrect() == true);
		assertTrue(question.getAnswer(0).getAnswerCount() == 1);
		
		assertTrue(question.getAnswer(1).getId() == "2");
		assertTrue(question.getAnswer(1).getCorrect() == false);
		assertTrue(question.getAnswer(1).getAnswerCount() == 0);
	}
	
	@Test
	public void testCSVFileExists() {
		question.addAnswer("1", true);
		question.addAnswer("2", false);
		question.increaseAnswerCount(0, "111.111.111.111");
		
		question.writeLogfile();
	}
	
	@Test
	public void testHasAnswerData() {
		question = new Question(questionID, filename);
		assertFalse(question.hasAnswerData());
		
		question.addAnswer("1", true);
		
		assertFalse(question.hasAnswerData());
		
		question.increaseAnswerCount(0, "111.111.111.111");
		
		assertTrue(question.hasAnswerData());
	}

}
