package PCapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private PrintWriter out;
    Socket socket;
    
    private String IP;

	public Client(String serverAddress) {
		try {
			connectToServer(serverAddress);
			IP = InetAddress.getLocalHost().getHostAddress();
		} catch (IOException e) {
			try {
				if(socket != null) {
					socket.close();
				}
			} catch (IOException e1) {
				System.out.println("Well something went wrong...");
			} 
		}

	}
	
	public void connectToServer(String serverAddress) throws IOException {

        // Make connection and initialise streams
        socket = new Socket(serverAddress, 80);
        out = new PrintWriter(socket.getOutputStream(), true);
    }
	
	public void sendToServer(String message) {
		System.out.println("Sending a message. " + message);
		out.println(IP + ":" + message);
	}

}