package comms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Data.Answer;
import Data.Question;

public class CommsHandler {

	// TODO COMMENT ME DAVID!!!!!!!!

	private Question currentQuestion;
	private ArrayList<String> recievedQuestionList;
	private Thread commsThread;
	private boolean isOnline;

	private static final String FILE_FOLDER = "logfiles";
	private static final String FILE_EXTENSION = ".csv";

	private static final String NEWLINE_DELIMITER = "\n";

	public CommsHandler() throws IOException {
		ServerSocket listener = new ServerSocket(80);
		recievedQuestionList = new ArrayList<String>();
		//System.out.println("ServerSocket opened");
		isOnline = true;
		commsThread = new Thread(new CommsThread(listener, this));
		commsThread.start();
	}

	/**
	 * @return the currentQuestion
	 */
	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	/**
	 * @param currentQuestion
	 *            the currentQuestion to set
	 */
	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	/**
	 * @return the recievedQuestionList
	 */
	public ArrayList<String> getRecievedQuestionList() {
		return recievedQuestionList;
	}

	protected void storeMessage(String message) {
		recievedQuestionList.add(message);
	}
	
	protected boolean isOnline() {
		return isOnline;
	}
	
	public void shutdown() {
		isOnline = false;
	}

	public void saveRecievedQuestions() {
		/* Create a new reference to the folder for the files. */
		File folder = new File(FILE_FOLDER);

		/* Try to create the folder if it doesn't already exist. */
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}

		/* Save the data if there is data to be saved, and the folder exists. */
		if ((recievedQuestionList.size() > 0) && (folder.isDirectory())) {
			try {
				/* The format for saving the date and time in the filename. */
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
				Date date = new Date();

				/* Create the filepath for the logfile */
				String filepath = FILE_FOLDER + "/"
						+ dateFormat.format(date)
						+ " "
						+ "received questions"
						+ FILE_EXTENSION;

				/* Create a new filewriter to create the csv file. */
				FileWriter writer = new FileWriter(filepath);

				/*
				 * Loop through all the answers, writing the relevent info to a
				 * new line.
				 */
				for (String currentQuestion : recievedQuestionList) {
					writer.append(currentQuestion);
					writer.append(NEWLINE_DELIMITER);
				}

				/* Flush the stream and close the filewriter */
				writer.flush();
				writer.close();

			} catch (IOException e) {
				e.printStackTrace(); // TODO
			}
		}
	}

	private class CommsThread implements Runnable {

		private ServerSocket listener;
		private CommsHandler parent;

		public CommsThread(ServerSocket listener, CommsHandler parent) {
			this.listener = listener;
			this.parent = parent;
		}
		
		public void run() {
			while (parent.isOnline()) {
				try {
					Thread commsThread2 = new Thread(new AnswerCollector(listener.accept(), parent));
					commsThread2.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private class AnswerCollector implements Runnable {
			private Socket socket;
			private CommsHandler parent;

			public AnswerCollector(Socket socket, CommsHandler parent) {
				this.socket = socket;
				this.parent = parent;
			}

			public void run() {
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					while (parent.isOnline()) {
						String input = in.readLine();
						if (input == null) {
							break;
						} else if (input.matches("^([0-9]{1,3})[.]([0-9]{1,3})[.]([0-9]{1,3})[.]([0-9]{1,3}[:](CLOSE_SOCKET))$")) {
							break;
						}
						if (input.matches("^([0-9]{1,3})[.]([0-9]{1,3})[.]([0-9]{1,3})[.]([0-9]{1,3}[:][0-3])$")) {

							String[] splitInput = input.split(":");

							if (parent.getCurrentQuestion() != null) {
								parent.getCurrentQuestion().increaseAnswerCount(
									Integer.parseInt(splitInput[1]),
									splitInput[0]);
							}
						} else {
							String[] splitMessageInput = input.split(":", 2);
							parent.storeMessage(splitMessageInput[1]);
						}
						System.out.println(input);
					}
				} catch (IOException e) {

				} finally {
					try {
						System.out.println("Closing Socket");
						socket.close();
					} catch (IOException e) {

					}
				}
			}

		}

	}

}