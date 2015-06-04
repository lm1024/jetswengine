/** (c) Copyright by WaveMedia. */
package tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.junit.BeforeClass;
import org.junit.Test;

import Data.Question;
import PCapp.Client;

import comms.CommsHandler;

/**
 * Class for testing the comms unit.
 * 
 * @author lp775
 * @version 1.0 29/05/2015
 */
public class CommsTest {

	private static CommsHandler comms;
	private static Client clive;
	private static Question question;

	private static String ip;

	/**
	 * Initial set up of the test environment. Gets the IP of the current PC,
	 * and creates a server and a client.
	 */
	@BeforeClass
	public static void setUp() {
		try {
			comms = new CommsHandler();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		clive = new Client(ip);

		question = new Question("id", "filename");
		question.addAnswer("1", true);
		question.addAnswer("2", false);

		comms.setCurrentQuestion(question);
	}

	/**
	 * Tests that an answer is incremented when a client sends the ID of a
	 * answer.
	 */
	@Test
	public void testAnswerIsIncremented() {
		clive.sendToServerWithIP("0");

		/* Pause and wait for the comms thread to finish its work. */
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(1, comms.getCurrentQuestion().getAnswer(0).getAnswerCount());
		assertEquals(0, comms.getCurrentQuestion().getAnswer(1).getAnswerCount());
	}

	/**
	 * Tests that the server receives any sent questions from the clients. 
	 */
	@Test
	public void testQuestionIsRecieved() {
		clive.sendToServerWithIP("Hello?");

		/* Pause and wait for the comms thread to finish its work. */
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(ip + ":" + "Hello?", comms.getRecievedQuestionList().get(0));

	}

}
