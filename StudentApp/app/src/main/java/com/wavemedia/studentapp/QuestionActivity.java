package com.wavemedia.studentapp;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;


public class QuestionActivity extends ActionBarActivity {

    public static final int SERVER_PORT = 80;

    //COMMS
    Thread networkingThread;
    Socket socket;
    private String serverIP;
    private String localIP;
    PrintWriter out;
    String message;
    EditText questionBoxJ;
    String question;

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

    class NetworkingThread implements Runnable {

        QuestionActivity parent;

        public NetworkingThread(QuestionActivity ma) {
            this.parent = ma;
        }

        @Override
        public void run() {
            try {
                try {
                        if(out == null) {
                            new SendingThread().run();
                            System.out.println("Check 2");
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




    class SendingThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(serverIP);
                //socket = new Socket(serverAddr, serverPort);
                System.err.println("WM: Creating socket");
                int serverPort = SERVER_PORT;
                socket = new Socket(serverAddr, serverPort);
                System.err.println("WM: Creating output");
                out = new PrintWriter(socket.getOutputStream(), true);
                System.err.println("WM: sending IP");
                //out.println(localIP);
            } catch (Exception e) {
                System.err.println("WM: shits fucked");
                //clientErrorOccured = true;
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String[] ips = getResources().getStringArray(R.array.ip_array);
        serverIP = intent.getStringExtra(Open.SERVER_IP);
        localIP = getWifiIpAddress(this);

        // Network
        networkingThread = new Thread(new NetworkingThread(this));
        networkingThread.start();

        // Set Layout
        setContentView(R.layout.activity_question);

        // Set up Question Box
        questionBoxJ = (EditText) findViewById(R.id.questionBox);
        questionBoxJ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    question = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    public void sendOption(String option) {
        // Send Option
        if(out != null) {
            try {
                out.println(localIP + ":" + option);
                System.err.println("WM Sent: \"" + option + "\"\n");
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
        message = "0";
        sendOption(message);
    }

    /** Called when User clicks the B Option */
    public void optionB(View view) {
        // Send Option
        message = "1";
        sendOption(message);
    }

    /** Called when User clicks the C Option */
    public void optionC(View view) {
        // Send Option
        message = "2";
        sendOption(message);
    }

    /** Called when User clicks the D Option */
    public void optionD(View view) {
        // Send Option
        message = "3";
        sendOption(message);
    }

    public void sendQuestion(View view) {
        // Send message
        //System.err.println(question);
        //System.err.println("WM QUESTION CHECK");
        sendOption(question);
    }


}
