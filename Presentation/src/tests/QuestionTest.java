package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Data.Question;

public class QuestionTest {

	private Question question;
	
	@Before
	public void setUp() throws Exception {
		question = new Question("a", "a.txt");
	}

	@Test
	public void testIdAndLogfile() {
		assertTrue(question.getId() == "a");
		assertTrue(question.getLogfile() == "a.txt");
	}
	
	@Test
	public void testAnswers() {
		question.addAnswer("1", true);
		question.addAnswer("2", false);
		
		question.increaseAnswerCount(0);
		
		assertTrue(question.getAnswer(0).getId() == "1");
		assertTrue(question.getAnswer(0).getCorrect() == true);
		assertTrue(question.getAnswer(0).getAnswerCount() == 1);
		
		assertTrue(question.getAnswer(1).getId() == "2");
		assertTrue(question.getAnswer(1).getCorrect() == false);
		assertTrue(question.getAnswer(1).getAnswerCount() == 0);
	}
	
	@Test
	public void testHasAnswerData() {
		question = new Question("a", "a.txt");
		assertFalse(question.hasAnswerData());
		
		question.addAnswer("1", true);
		
		assertFalse(question.hasAnswerData());
		
		question.increaseAnswerCount(0);
		
		assertTrue(question.hasAnswerData());
		
	}

}
