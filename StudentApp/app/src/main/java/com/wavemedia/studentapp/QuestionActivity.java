package com.wavemedia.studentapp;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;


public class QuestionActivity extends ActionBarActivity {

    //COMMS
    Thread networkingThread;
    Thread receivingThread;
    Thread sendingThread;
    Socket socket;

    TextView receivedMessagesTextView;

    private int serverPort;
    private String serverIP;
    private String localIP;
    private boolean clientErrorOccured = false;

    PrintWriter out;
    BufferedReader in;
    String message;

    private class ReceivingThread extends Thread {
        private Socket socket;
        private QuestionActivity parent;

        public ReceivingThread(Socket socket, QuestionActivity parent) {
            this.socket = socket;
            this.parent = parent;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    printToConsole("Received: \"" + input + "\"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    protected String getWifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            System.err.println("WIFIIP, Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;
    }

    public void printToConsole(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                receivedMessagesTextView.append(message);
            }
        });

    }

    class NetworkingThread implements Runnable {

        QuestionActivity parent;

        public NetworkingThread(QuestionActivity ma) {
            this.parent = ma;
        }

        @Override
        public void run() {
            ServerSocket listener = null;
            try {
                listener = new ServerSocket(serverPort);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                try {
                    try {
                        if(out == null) {
                            new SendingThread().run();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if(listener == null) {
                        System.err.println("listener is null");
                    } else {
                        System.err.println("listener isnt null");
                    }
                    receivingThread = new ReceivingThread(listener.accept(), parent);
                    receivingThread.start();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } finally {
                try {
                    listener.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }



    class SendingThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(serverIP);
                socket = new Socket(serverAddr, serverPort);
                out = new PrintWriter(socket.getOutputStream(), true);
                //out.println(localIP);
            } catch (Exception e) {
                clientErrorOccured = true;
                e.printStackTrace();
            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendOption() {
        //Set-Up Connection
        serverIP = "192.168.1.93";
        serverPort = 80;
        networkingThread = new Thread(new NetworkingThread(this));
        networkingThread.start();
        // Send Option
        if(out != null) {
            try {
                out.println(message);
                printToConsole("Sent: \"" + message + "\"\n");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    socket.close();
                } catch (Exception e1) {
                    System.out.println("Well something went wrong...");
                }
            }
        } else {
            System.err.println("Unable to send. Sending thread null");
        }
    }
    /** Called when User clicks the A Option */
    public void optionA(View view) {
        // Send Option
        message = "A";
        sendOption();
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        Intent intent = new Intent(this, OptionA.class);
        startActivity(intent);
    }

    /** Called when User clicks the B Option */
    public void optionB(View view) {
        // Send Option
        message = "B";
        sendOption();
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        Intent intent = new Intent(this, OptionB.class);
        startActivity(intent);
    }

    /** Called when User clicks the C Option */
    public void optionC(View view) {
        // Send Option
        message = "C";
        sendOption();
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        Intent intent = new Intent(this, OptionC.class);
        startActivity(intent);
    }

    /** Called when User clicks the D Option */
    public void optionD(View view) {
        // Send Option
        message = "D";
        sendOption();
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        Intent intent = new Intent(this, OptionD.class);
        startActivity(intent);
    }
}
