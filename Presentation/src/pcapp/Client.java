/** (c) Copyright by WaveMedia. */
package pcapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client class responsible for Client-Side communications to test the server
 * communications module until android development finishes.
 * 
 * @author dk666
 * @version 1.2 29/04/15
 * 
 */
public class Client {

	/* Printwriter for writing data to socket */
	private PrintWriter out;
	
	/* Socket for sending data to server */
	private Socket socket;

	/* IP of server */
	private String IP;

	
	/**
	 * Initialises the Client and its variables.
	 * Attempts to connect to the server at serverAddress.
	 * @param serverAddress The address of the target server.
	 */
	public Client(String serverAddress) {
		try {
			connectToServer(serverAddress);
			IP = InetAddress.getLocalHost().getHostAddress();
		} catch (IOException e) {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e1) {
				System.out.println("Well something went wrong...");
			}
		}

	}

	/**
	 * Attempts to open a socket on port 80 with target server at serverAddress.
	 * Attempts to open PrintWriter on output socket.
	 * @param serverAddress
	 * @throws IOException
	 */
	public void connectToServer(String serverAddress) throws IOException {

		// Make connection and initialise streams
		socket = new Socket(serverAddress, 80);
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	/**
	 * Sends message to server with local IP address appended to the front.
	 * @param message
	 */
	public void sendToServerWithIP(String message) {
		// System.out.println("Sending a message. " + message);
		out.println(IP + ":" + message);
	}

	
	/**
	 * Sends message to server without local IP address appended to the front.
	 * @param message
	 */
	public void sendToServerWithoutIP(String message) {
		// System.out.println("Sending a message. " + message);
		out.println(message);
	}

}