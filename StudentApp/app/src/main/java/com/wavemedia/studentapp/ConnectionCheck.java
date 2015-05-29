package com.wavemedia.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Harrison on 28/05/15.
 */
public class ConnectionCheck {
    Thread networkingThread;
    Socket socket;
    String serverIP;
    String localIP;
    Boolean check = true;
    Boolean finish = false;
    PrintWriter out;

    public ConnectionCheck(String sentServerIP, String sentLocalIP) {
        serverIP = sentServerIP;
        localIP = sentLocalIP;
        networkingThread = new Thread(new NetworkingThread(this));
        networkingThread.run();
    }

    class NetworkingThread implements Runnable {

        ConnectionCheck parent;

        public NetworkingThread(ConnectionCheck ma) {
            this.parent = ma;
        }

        @Override
        public void run() {
            try {
                if (socket == null) {
                    new SendingThread().run();
                    System.out.println("Check 2");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    class SendingThread implements Runnable {

        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(serverIP);
                System.err.println("WM: Creating socket");
                int serverPort = QuestionActivity.SERVER_PORT;
                socket = new Socket(serverAddr, serverPort);
            } catch (Exception e) {
                // Test Failed
                check = false;
                return;
            }
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println(localIP+":"+"SOCKET_CLOSE");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean getCheck() {
        return check;
    }

    public void setFinish(Boolean setting) {
        finish = setting;
    }
}