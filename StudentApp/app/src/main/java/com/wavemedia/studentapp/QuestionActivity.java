/**
 *
 * Harrison Holt-McHale
 * David Kilburn
 *
 * Copyright (c) 2015 WaveMedia. All rights reserved
 *
 */

package com.wavemedia.studentapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

/**
 * @author Harrison Holt-McHale
 * @author David Kilburn
 * @version 2.0 29/05/2015
 *
 */

/* Activity created by Open Activity */
public class QuestionActivity extends ActionBarActivity {

    /* Server Port is always 80 */
    public final static int SERVER_PORT = 80;

    public final static String SC = "SOCKET_CLOSE";

    /* Comms Variables */
    Thread networkingThread;
    Thread networkCheckThread;
    Thread runNetworkCheckThread;
    Socket socket;
    private String serverIP;
    private String localIP;
    PrintWriter out;
    boolean socketOpen = true;
    Boolean wifiDisconnect = false;

    /* EditText for the Question TextBox */
    EditText questionBoxJ;
    /* Declare and Instantiate Question to be sent */
    String question = "";
    /* Delare Buttons to send Answers */
    Button buttonA, buttonB, buttonC, buttonD;
    /* Instantiate Custom colour to indicate last answer sent to PC Application */
    int last = Color.rgb(0, 130, 223);
    /* Declare Default Colour to save the initial colour of the Button Text */
    int defaultColor;
    /* Boolean to mark whether Connection has been lost yet */
    boolean finish= false;
    /* Boolean to mark whether Connection has be initially established yet */
    Boolean ready = false;
    /* Boolean to prevent GUI Edits in finished activities */
    private boolean visible = false;
    private boolean timeoutDialog;
    AlertDialog connectionAlertDialog;
    WifiManager wifiManager;
    NetworkInfo mWifi;                  // WIFI Network Info
    ConnectivityManager connManager;    // Connectivity Manager

    /* Method for getting IP Address on Wifi-enabled devices */
    protected String getWifiIpAddress(Context context) {
        /* Get WifiService Info */
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

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

    /* Thread for making App to Pc Connection */
    class NetworkingThread implements Runnable {

        /* Pass in the Activity being used as a parameter */
        QuestionActivity parent;

        /* Thread Constructor */
        public NetworkingThread(QuestionActivity ma) {
            this.parent = ma;
        }

        /* Thread Method */
        @Override
        public void run() {
            try {
                /* If the PrintStream is null */
                if(out == null) {
                    /* Try and make a new Socket and PrintStream */
                    new SendingThread().run();
                    System.out.println("Check 2");
                }
                /* If there's an Exception */
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    /* Try to close the Socket and Uninstantiate the PrintStream out. */
                    out.println("SOCKET_CLOSE");
                    socket.close();
                    out = null;
                } catch (IOException e1) {
                    /* If this can't be done just print the Stack Trace */
                    e1.printStackTrace();
                }
            }
        }
    }

    /* Thread for executing the Socket and PrintStream connection */
    class SendingThread implements Runnable {

        @Override
        public void run() {
            try {
                /* Get the InetAddress version from the serverIP String */
                InetAddress serverAddr = InetAddress.getByName(serverIP);
                /* server Port is always 80 */
                int serverPort = SERVER_PORT;
                /* Create new Socket */
                socket = new Socket();
                socket.connect(new InetSocketAddress(serverAddr,serverPort),3000);
                /* Create new PrintWriter on created socket */
                out = new PrintWriter(socket.getOutputStream(), true);
                ready = true;
            } catch (Exception e) {
                /* If there's an exception Print the Stack Trace */
                e.printStackTrace();
            }
        }
    }

     /* When the Activity is Created, run this Method first */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Runs the Standard OnCreate method before executing additions */
        super.onCreate(savedInstanceState);

        /* Get Intent from the Open Activity. Contains serverIP */
        Intent intent = getIntent();
        /* Get serverIP from Intent Extra and use to instantiate class variable */
        serverIP = intent.getStringExtra(Open.SERVER_IP);
        /* Get WifiAddress of Wifi Enabled Device */
        localIP = getWifiIpAddress(this);

        /* Make Networking Thread for initial connection */
        networkingThread = new Thread(new NetworkingThread(this));
        /* Start Networking Thread */
        networkingThread.start();

        /* Set the content view of Activity to the Question Activity XML */
        setContentView(R.layout.activity_question);

        /* Set Wifi Manager */
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

        /*Get Connectivity Manager and Network Info */
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        /* Instantiate EditText for TextBox used for sending a question */
        questionBoxJ = (EditText) findViewById(R.id.questionBox);
        questionBoxJ.addTextChangedListener(new TextWatcher() {
            /* Do Nothing Before Text is Changed */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            /* Trim Question Box Text and add to Question variable */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    question = s.toString().trim();
                }
            }
            /* Do nothing after text is changed */
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /* Instantiate Answer Buttons */
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonD = (Button) findViewById(R.id.buttonD);

        /* Instantiate Original colour of the Button Text */
        defaultColor = buttonA.getCurrentTextColor();

        /* Instantiate and run the thread that runs the Heartbeat Network Check */
        runNetworkCheckThread = new Thread(new RunNetworkCheckThread());
        runNetworkCheckThread.start();
    }

    /* If we decide to use an action bar this is where we add the options. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    /* This is the OnClickListener for the Action Bar */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handle action bar item clicks here. The action bar will
            automatically handle clicks on the Home/Up button, so long
            as you specify a parent activity in AndroidManifest.xml.   */
        int id = item.getItemId();
        /* If Information is selected */
        if (id == R.id.action_info) {
            /* Display Information Alert Dialog */
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

    /*  This is the Method for sending the localIP and String parameter passed to
        the method                                                                  */
    public void sendOption(String option) {
        /* If the PrintStream is not null */
        if (out != null) {
            /* Try and print the local IP and the string to the Print Stream */
            try {
                out.println(option);
            } catch (Exception e) {
                /* Print the stack-trace and try and close the socket on exception */
                e.printStackTrace();
                try {
                    out.println("SOCKET_CLOSE");
                    socket.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            /* If PrintStream is null then Throw IOException and Return */
            try {
                System.err.println("Unable to send. Sending thread null");
                throw new IOException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Called when User clicks the A Option */
    public void optionA(View view) {
            /* If the socket is not already being used by another button or a question */
            if (socketOpen) {
                /* Make socket occupied */
                socketOpen = false;
                /* Send the option being chosen */
                sendOption(localIP + ":" + "0");
                /* Change the button text colours to reflect A is last answer */
                buttonA.setTextColor(last);
                buttonB.setTextColor(defaultColor);
                buttonC.setTextColor(defaultColor);
                buttonD.setTextColor(defaultColor);
                /* Un-occupy Socket */
                socketOpen = true;
            }
    }

    /* Called when User clicks the B Option */
    public void optionB(View view) {
        /* If the socket is not already being used by another button or a question */
        if (socketOpen) {
            /* Make socket occupied */
            socketOpen = false;
            /* Send the option being chosen */
            sendOption(localIP + ":" + "1");
            /* Change the button text colours to reflect B is last answer */
            buttonA.setTextColor(defaultColor);
            buttonB.setTextColor(last);
            buttonC.setTextColor(defaultColor);
            buttonD.setTextColor(defaultColor);
            /* Un-occupy Socket */
            socketOpen = true;
        }
    }

    /* Called when User clicks the C Option */
    public void optionC(View view) {
        /* If the socket is not already being used by another button or a question */
        if (socketOpen) {
            /* Make socket occupied */
            socketOpen = false;
            /* Send the option being chosen */
            sendOption(localIP + ":" + "2");
            /* Change the button text colours to reflect C is last answer */
            buttonA.setTextColor(defaultColor);
            buttonB.setTextColor(defaultColor);
            buttonC.setTextColor(last);
            buttonD.setTextColor(defaultColor);
            /* Un-occupy Socket */
            socketOpen = true;
        }
    }

    /* Called when User clicks the D Option */
    public void optionD(View view) {
        /* If the socket is not already being used by another button or a question */
        if (socketOpen) {
            /* Make socket occupied */
            socketOpen = false;
            /* Send the option being chosen */
            sendOption(localIP + ":" + "3");
            /* Change the button text colours to reflect C is last answer */
            buttonA.setTextColor(defaultColor);
            buttonB.setTextColor(defaultColor);
            buttonC.setTextColor(defaultColor);
            buttonD.setTextColor(last);
            /* Un-occupy Socket */
            socketOpen = true;
        }
    }

    /* Called when User tries to send a question */
    public void sendQuestion(View view) {
        /* If the socket is not already being used by another button or a question */
        if (socketOpen) {
            /* Make socket occupied */
            socketOpen = false;
            /* If the question is more than 0 characters but less than 250 characters  */
            if ((question.length() <= 250) && (question.length() > 0) && !question.toLowerCase().contains(SC.toLowerCase())) {
                /* Send Questions and Clear Question Box */
                sendOption(question);
                questionBoxJ.setText("");
            /* Else if contains SERVER_CLOSE */
            }else if (question.toLowerCase().contains(SC.toLowerCase())) {
                /* Build and display an Alert Dialog Informing the User */
                new AlertDialog.Builder(this)
                        .setTitle(R.string.sc_error)
                        .setMessage("Your Question contains SmartSlides Reserved/Invalid Words")
                        /* If Edit is pressed, do nothing */
                        .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        /* If Cancel is pressed, clear the Question Box */
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                questionBoxJ.setText("");
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            /* Else if question is more than 250 characters */
            } else if (question.length() > 250) {
                /* Build and display an Alert Dialog Informing the User */
                new AlertDialog.Builder(this)
                        .setTitle(R.string.length_error)
                        .setMessage("Your Question is too long. Please edit or cancel.")
                        /* If Edit is pressed, do nothing */
                        .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        /* If Cancel is pressed, clear the Question Box */
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                questionBoxJ.setText("");
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
            /* Un-occupy socket */
            socketOpen = true;
        }
    }

    /* When the Activity is destroyed, this method is called */
    @Override
    protected void onDestroy(){

        /* Set Wifi Disconnected Flag to False */
        wifiDisconnect = false;

        /* If the socket exists */
        if (socket != null) {
            try {
                /* Close the Socket */
                out.println("SOCKET_CLOSE");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* Carry out normal onDestroy method */
        super.onDestroy();
    }

    /* Thread that runs the NetworkCheckThread */
    class RunNetworkCheckThread implements Runnable {

        @Override
        public void run() {
            /* While no connection error found */
            while (!finish) {
                /* If intial connection has been established */
                if (ready) {
                    /* Run the Network Check */
                    networkCheckThread = new Thread(new NetworkCheckThread());
                    networkCheckThread.run();
                }
                /* Sleep for 4 seconds */
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* Thread that runs the Network Check */
    class NetworkCheckThread implements Runnable {

        @Override
        public void run() {
                /* Try and close the socket */
                try {
                    out.println("SOCKET_CLOSE");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /* Try and Open a new Socket */
                new SendingCheckThread().run();
        }
    }

    /* Thread that opens a new socket */
    class SendingCheckThread implements Runnable {

        @Override
        public void run() {
            try {
                mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                System.out.println("Detailed State: " + mWifi.getDetailedState());
                /* Check if Wifi is enabled */
                if ((mWifi.getDetailedState() != NetworkInfo.DetailedState.CONNECTED)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
                            builder.setTitle(R.string.connection_error);
                            builder.setMessage("Returning to Connect Screen.");
                        /* Set OK Button to finish the activity */
                            builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                        /* If user cancels the alert dialog in any way, also finish the activty */
                            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                            builder.setIcon(android.R.drawable.ic_dialog_info);
                            connectionAlertDialog = builder.create();
                        }
                    });
                    while (!timeoutDialog) {
                        if (visible && !wifiDisconnect) {
                            wifiDisconnect = true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                            /* If Activity is still visible, show the Alert Dialog */
                                    if (visible) connectionAlertDialog.show();
                                }
                            });
                            timeoutDialog = true;
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Waiting for visible");
                    }
                    timeoutDialog = false;
                }
                if (wifiDisconnect == false) {
                /* Get the InetAddress version from the serverIP String */
                    InetAddress serverAddr = InetAddress.getByName(serverIP);
                /* server Port is always 80 */
                    int serverPort = SERVER_PORT;
                /* Create new Socket */
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(serverAddr, serverPort), 3000);
                /* Create new PrintWriter on created socket */
                    out = new PrintWriter(socket.getOutputStream(), true);
                }
            }
            /* If there's an exception */
            catch (Exception a) {
                /* Print the Stack Trace */
                a.printStackTrace();
                /* Set the Boolean to reflect that there's been a connection error */
                finish = true;
                /* Run Alert Dialog Builder on UI Thread, showing user that it can't connect
                 * to the server                                                             */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
                        builder.setTitle(R.string.connection_error);
                        builder.setMessage("Returning to Connect Screen.");
                        /* Set OK Button to finish the activity */
                        builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        /* If user cancels the alert dialog in any way, also finish the activty */
                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                    finish();
                                }
                        });
                        builder.setIcon(android.R.drawable.ic_dialog_info);
                        connectionAlertDialog = builder.create();
                    }
                });
                while (!timeoutDialog) {
                    if (visible) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            /* If Activity is still visible, show the Alert Dialog */
                                if (visible) connectionAlertDialog.show();
                            }
                        });
                        timeoutDialog = true;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Waiting for visible");
                }
                timeoutDialog = false;
            }
        }
    }

    /* Called when the User Resumes an Activity */
    @Override
    protected void onResume()
    {
        /* Make Visible True */
        super.onResume();
        visible = true;
    }

    /* Called when the User Pauses an Activity */
    @Override
    protected void onStop()
    {
        /* Make Visible False */
        visible = false;
        super.onStop();
    }

    /* Finish Activity on Back Pressed */
    @Override
    public void onBackPressed(){
        finish();
        super.onBackPressed();
    }
}
