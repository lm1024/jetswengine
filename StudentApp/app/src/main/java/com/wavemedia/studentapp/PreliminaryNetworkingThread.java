package com.wavemedia.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Harrison on 26/05/15.
 */
public class PreliminaryNetworkingThread implements Runnable {

    Open parent;
    String serverIP = "0.0.0.0";
    int serverPort = 0;
    Socket socket;
    PrintWriter out;
    Boolean check;

    public PreliminaryNetworkingThread(Open ma) {
        this.parent = ma;
    }

    public Boolean getCheck() {
        return check;
    }

    @Override
    public void run() {
        try {
            try {
                if(out == null) {
                    new PreliminarySendingThread().run();
                    System.out.println("Check 2");
                    if (out == null){
                        check = true;
                    } else {
                        check = false;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    socket.close();
                    System.out.println("Check 3");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } /*finally {
                *//*try {
                    //listener.close();
                }*/ catch (Exception e) {
            e.printStackTrace();
        }
    }
}

