package com.wavemedia.studentapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;


public class QuestionActivity extends ActionBarActivity {

    public final static int SERVER_PORT = 80;

    //COMMS
    Thread networkingThread;
    Thread networkCheckThread;
    Thread runNetworkCheckThread;
    Socket socket;
    private String serverIP;
    private String localIP;
    PrintWriter out;
    String message;
    EditText questionBoxJ;
    String question = "";
    Button buttonA, buttonB, buttonC, buttonD;
    int last = Color.rgb(0, 130, 223);
    int defaultColor;
    boolean finish, ready = false;
    boolean socketOpen = true;


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
                            out.println(localIP+":"+"SOCKET_CLOSE");
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
                ready = true;
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
        serverIP = intent.getStringExtra(Open.SERVER_IP);
        localIP = getWifiIpAddress(this);

        //Network
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

        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonD = (Button) findViewById(R.id.buttonD);

        defaultColor = buttonA.getCurrentTextColor();

        //policy = new StrictMode.ThreadPolicy.Builder().permitAll();
        //StrictMode.setThreadPolicy(policy);

        runNetworkCheckThread = new Thread(new RunNetworkCheckThread());
        System.out.println("runNetworkCheckThread 1");
        runNetworkCheckThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    // This is the OnClickListener for the Action Bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.action_info)
                    .setMessage("Help and Contact Email:\n" +
                            "help@smartslides.co.uk")
                    .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
        return true;
    }

    public void sendOption(String option) {
            // Send Option
            if (out != null) {
                try {
                    out.println(localIP + ":" + option);
                    System.err.println("WM Sent: \"" + option + "\"\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        out.println("SOCKET_CLOSE");
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
            if (socketOpen) {
                socketOpen = false;
                // Send Option
                message = "0";
                sendOption(message);

                buttonA.setTextColor(last);
                buttonB.setTextColor(defaultColor);
                buttonC.setTextColor(defaultColor);
                buttonD.setTextColor(defaultColor);
                socketOpen = true;
            }
    }

    /** Called when User clicks the B Option */
    public void optionB(View view) {
        if (socketOpen) {
            socketOpen = false;
            // Send Option
            message = "1";
            sendOption(message);

            buttonA.setTextColor(defaultColor);
            buttonB.setTextColor(last);
            buttonC.setTextColor(defaultColor);
            buttonD.setTextColor(defaultColor);
            socketOpen = true;
        }
    }

    /** Called when User clicks the C Option */
    public void optionC(View view) {
        if (socketOpen) {
            socketOpen = false;
            // Send Option
            message = "2";
            sendOption(message);

            buttonA.setTextColor(defaultColor);
            buttonB.setTextColor(defaultColor);
            buttonC.setTextColor(last);
            buttonD.setTextColor(defaultColor);
            socketOpen = true;
        }
    }

    /** Called when User clicks the D Option */
    public void optionD(View view) {
        if (socketOpen) {
            socketOpen = false;

            // Send Option
            message = "3";
            sendOption(message);

            buttonA.setTextColor(defaultColor);
            buttonB.setTextColor(defaultColor);
            buttonC.setTextColor(defaultColor);
            buttonD.setTextColor(last);
            socketOpen = true;
        }
    }

    public void sendQuestion(View view) {
        if (socketOpen) {
            socketOpen = false;
            if (question.length() <= 250) {
                sendOption(question);
                questionBoxJ.setText("");
            } else {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.length_error)
                        .setMessage("Your Question is too long. Please edit or cancel.")
                        .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                questionBoxJ.setText("");
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
            socketOpen = true;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (socket != null) {
            try {
                out.println("SOCKET_CLOSE");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class RunNetworkCheckThread implements Runnable {

        @Override
        public void run() {
            System.out.println("runNetworkCheckThread 2");
            while (!finish) {
                if (ready) {
                    System.out.println("runNetworkCheckThread 3");
                    networkCheckThread = new Thread(new NetworkCheckThread());
                    System.out.println("runNetworkCheckThread 4");
                    networkCheckThread.run();
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    class NetworkCheckThread implements Runnable {

        @Override
        public void run() {
                System.out.println("runNetworkCheckThread 5");
                System.out.println("NetworkCheckThreadRun");
                System.out.println("SOCKET_CLOSE");
                out.println("SOCKET_CLOSE");
                try {
                    socket.close();
                    System.out.println("runNetworkCheckThread 6");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("runNetworkCheckThread 7");
                new SendingCheckThread().run();
        }
    }

    class SendingCheckThread implements Runnable {

        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(serverIP);
                System.out.println("runNetworkCheckThread 8");
                //socket = new Socket(serverAddr, serverPort);
                System.err.println("WM: Creating socket");
                int serverPort = SERVER_PORT;
                socket = new Socket(serverAddr, serverPort);
                System.out.println("runNetworkCheckThread 9");
                System.err.println("WM: Creating output");
                out = new PrintWriter(socket.getOutputStream(), true);
                System.out.println("runNetworkCheckThread 10");
                System.err.println("WM: sending IP");
            } catch (Exception a) {
                a.printStackTrace();
                finish = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(QuestionActivity.this)
                                .setTitle(R.string.connection_error)
                                .setMessage("Returning to Connect Screen.")
                                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    }
                });
                return;
            }
        }
    }
//
//    new AlertDialog.Builder(QuestionActivity.this)
//            .setTitle(R.string.connection_error)
//    .setMessage("Returning to Connect Screen.")
//    .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
//        public void onClick(DialogInterface dialog, int which) {
//            finish();
//        }
//    })
//            .setIcon(android.R.drawable.ic_dialog_info)
//    .show();

}
