/** (c) Copyright by WaveMedia. */
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

import Data.Question;

/**
 * CommsHandler class for managing server communications.
 * 
 * @author dk666
 * @version 2.3 02/06/15
 * 
 */
public class CommsHandler {

	/* private variable declarations */
	private Question currentQuestion;
	private ArrayList<String> recievedQuestionList;
	private Thread commsThread;
	private boolean isOnline;

	private static final String FILE_FOLDER = "receivedQuestions";
	private static final String FILE_EXTENSION = ".csv";

	private static final String NEWLINE_DELIMITER = "\n";

	/**
	 * Initialises a new CommsHandler object. Attempts to open a new server
	 * socket on port 80.
	 * 
	 * @throws IOException
	 *             if server socket cannot be opened.
	 */
	public CommsHandler() throws IOException {
		ServerSocket listener = new ServerSocket(80);
		recievedQuestionList = new ArrayList<String>();
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

	/**
	 * Stores a message into the question list.
	 * 
	 * @param message
	 *            the message to store.
	 */
	protected void storeMessage(String message) {
		recievedQuestionList.add(message);
	}

	/**
	 * Checks if server is still online.
	 * 
	 * @return isOnline : true if still online.
	 */
	protected boolean isOnline() {
		return isOnline;
	}

	/**
	 * Tells the server to close all communication threads.
	 */
	public void shutdown() {
		isOnline = false;
	}

	/**
	 * Saves all received questions to a .CSV file.
	 */
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
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH-mm-ss");
				Date date = new Date();

				/* Create the filepath for the logfile */
				String filepath = FILE_FOLDER + "/" + dateFormat.format(date)
						+ " " + "received questions" + FILE_EXTENSION;

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

	/**
	 * Thread which spawns listeners for each client as they connect to the
	 * server. Closes listeners when server is shutting down.
	 * 
	 * @author dk666
	 * 
	 */
	private class CommsThread implements Runnable {

		/* Server socket to receive client connections on */
		private ServerSocket listener;

		/* Reference to parent to check when server is shutting down */
		private CommsHandler parent;

		/**
		 * Initialises a new comms thread.
		 * 
		 * @param listener
		 *            the ServerSocket to listen on.
		 * @param parent
		 *            the parent CommsHandler to check for shutdown.
		 */
		public CommsThread(ServerSocket listener, CommsHandler parent) {
			this.listener = listener;
			this.parent = parent;
		}

		/**
		 * Starts the CommsThread. Will start accepting clients while this
		 * thread is running.
		 */
		public void run() {
			while (parent.isOnline()) {
				try {
					Thread commsThread2 = new Thread(new AnswerCollector(
							listener.accept(), parent));
					commsThread2.start();
				} catch (IOException e) {
					/*
					 * Do nothing. Client socket will close if exception occurs.
					 * ServerSocket Exceptions are handled at a higher level.
					 */
				}
			}
		}

		/**
		 * Receives messages from client devices. Handles messages correctly
		 * depending on their content.
		 * 
		 * @author dk666
		 * 
		 */
		private class AnswerCollector implements Runnable {

			/* Socket to read data from */
			private Socket socket;

			/* (grand)Parent CommsHandler to check when to shutdown */
			private CommsHandler parent;

			/**
			 * Initialises a new AnswerCollector thread.
			 * 
			 * @param socket
			 *            the Socket to receive data on.
			 * @param parent
			 *            the parent CommsHandler to check for shutdown.
			 */
			public AnswerCollector(Socket socket, CommsHandler parent) {
				this.socket = socket;
				this.parent = parent;
			}

			/**
			 * Starts the AnswerCollector. Will read data from socket while
			 * running and handle it correctly depending on its form.
			 */
			public void run() {
				try {
					/* Set up a buffered reader to read from the socket */
					BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
					while (parent.isOnline()) {

						/* Reads from the socket line by line */
						String input = in.readLine();

						/* Stop reading if null data is being sent */
						if (input == null) {
							break;
						} else if (input.matches("^(SOCKET_CLOSE)$")) {
							/* Close the socket if correct command is sent */
							break;
						}
						/*
						 * If input is an IP address followed by an answer ID
						 * then increase the correct answer count
						 */
						if (input
								.matches("^([0-9]{1,3})[.]([0-9]{1,3})[.]([0-9]{1,3})[.]([0-9]{1,3}[:][0-3])$")) {

							String[] splitInput = input.split(":");

							if (parent.getCurrentQuestion() != null) {
								parent.getCurrentQuestion()
										.increaseAnswerCount(
												Integer.parseInt(splitInput[1]),
												splitInput[0]);
							}
						} else {
							/*
							 * If message isn't a command or an answer then log
							 * the message
							 */
							parent.storeMessage(input);
						}
					}
				} catch (IOException e) {
					/* Do nothing Error handling carried out at higher level. */
				} finally {
					try {
						socket.close();
					} catch (IOException e) {

					}
				}
			}

		}

	}

}