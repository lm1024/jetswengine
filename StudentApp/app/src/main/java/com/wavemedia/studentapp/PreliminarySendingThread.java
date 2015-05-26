package com.wavemedia.studentapp;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Harrison on 26/05/15.
 */


public class PreliminarySendingThread implements Runnable {

    String serverIP = "0.0.0.0";
    int serverPort = 0;
    Socket socket;
    PrintWriter out;

    public void setServerIP(String sentServerIP){
        serverIP = sentServerIP;
    }

    public void setServerPort(int sentServerPort){
        serverPort = sentServerPort;
    }

    public PrintWriter getOut(){
        return out;
    }

    public Socket getSocket(){
        return socket;
    }

    @Override
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(serverIP);
            //socket = new Socket(serverAddr, serverPort);
            System.err.println("WM: Creating socket");
            socket = new Socket(serverAddr, serverPort);
            System.err.println("WM: Creating output");
            out = new PrintWriter(socket.getOutputStream(), true);
            //System.err.println("WM: sending IP");
            //out.println(localIP);
        } catch (Exception e) {
            System.err.println("WM: shits fucked");
            e.printStackTrace();
        }
    }

}
