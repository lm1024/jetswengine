package comms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import Data.Question;

public class CommsHandler {

	//TODO COMMENT ME DAVID!!!!!!!!
	
	private Question currentQuestion;
	private Thread commsThread;

	public CommsHandler() throws IOException {
		// initGui();
		ServerSocket listener = new ServerSocket(80);
		System.out.println("ServerSocket opened");
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

	private class CommsThread implements Runnable {

		private ServerSocket listener;
		private CommsHandler parent;

		public CommsThread(ServerSocket listener, CommsHandler parent) {
			this.listener = listener;
			this.parent = parent;
		}

		public void run() {
			while (true) {
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

					while (true) {
						String input = in.readLine();
						if (input == null) {
							break;
						}
						if (input.matches("^([0-9]{1,3})[.]([0-9]{1,3})[.]([0-9]{1,3})[.]([0-9]{1,3}[:][0-3])$")) {

							String[] splitInput = input.split(":");

							parent.getCurrentQuestion().increaseAnswerCount(
								Integer.parseInt(splitInput[1]),
								splitInput[0]);
						}
						System.out.println(input);
					}
				} catch (IOException e) {

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