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
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

/**
 * @author Harrison Holt-McHale
 * @author David Kilburn
 * @version 2.0 29/05/2015
 *
 */

/* Activity Created on App Launch */
public class Open extends ActionBarActivity {

    /* Labels for Intent Extra */
    public final static String SERVER_IP = "com.wavemedia.studentapp.SERVER_IP";

    Spinner spinner;                    // Dropdown Box Declaration
    int site;                           // Dropdown Box Selected Value Declaration
    int[] sites;                        // IDs for Sites
    String[] ips;                       // IPs for Sites
    EditText hex;                       // Hex Code Text Box
    Button connectButton;               // Connect Button
    Thread connectThread;               // Thread ran on pressing connect button
    Thread connectEnableThread;         // Thread for Enabling Connect Button Declaration
    Thread networkingThread;            // Thread for preliminary Network Test
    Socket socket;                      // Socket for preliminary Network Test
    PrintWriter out;                    // PrintWriter for preliminary Network Test
    String serverIP;                    // Server IP for preliminary Network Test
    Boolean testResult = false;         // Boolean for Connection Test Result
    Boolean networkAttempted;           // Boolean to indicate whether connection has been attempted
    Boolean connectPressed = false;     // Boolean to indicate whether connect method is executing
    String localIP;                     // String for Wifi Enabled Device IP
    Boolean whileFinish;                // Enables While Loop while network is attempted
    Intent intent;                      // Intent to move to Question Activity


    /* Instantiate Listener for Institution Dropdown Box Selection*/
    CustomOnItemSelectedListener customOnItemSelectedListener = new CustomOnItemSelectedListener();

    /* On Application Open, First Activity Runs: */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Runs the Standard OnCreate method before executing additions */
        super.onCreate(savedInstanceState);

        /* Set view to activity_open.xml. The layout currently designed */
        setContentView(R.layout.activity_open);

        hex  = (EditText) findViewById(R.id.editText);  // Instantiate HexCode input to XML layout
        spinner = (Spinner) findViewById(R.id.spinner); // Instantiate DD Box input to XML layout

        /* Get the local IP Address of the device */
        localIP = getWifiIpAddress(this);

        /* Adapt Array from arrays.xml for DD Box. Set layout style. */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.site_array,android.R.layout.simple_spinner_item);
        /* Set View Resource layout */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /* Add ArrayAdapter to Dropdown box */
        spinner.setAdapter(adapter);
        /* Add Item Selection Listener to Dropdown Box */
        spinner.setOnItemSelectedListener(customOnItemSelectedListener);

        /* Use Array of numbers 0 - Number of Institutions to compare with DD Box selection */
        sites = getResources().getIntArray(R.array.site_id);

        /* Get list of IPs for site IDS */
        ips = getResources().getStringArray(R.array.ip_array);

        /* Instantiate Connect Button */
        connectButton = (Button) findViewById(R.id.connectbutton);
        /* Initially Disable until adequate data input */
        connectButton.setEnabled(false);

        /* Instantiate and start thread for enabling connect button */
        connectEnableThread = new Thread(new ConnectEnableThread(this));
        connectEnableThread.start();

    }

    /* Thread for making App to Pc Connection */
    class NetworkingThread implements Runnable {

        /* Pass in the Activity being used as a parameter */
        Open parent;

        /* Thread Constructor */
        public NetworkingThread(Open ma) {
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
                }
                /* If there's an Exception */
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    /* Try to close the Socket and Uninstantiate the PrintStream out. */
                    socket.close();
                    out = null;
                    /* If this can't be done just print the Stack Trace */
                } catch (IOException e1) {
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
                int serverPort = QuestionActivity.SERVER_PORT;
                /* Create new Socket */
                socket = new Socket(serverAddr, serverPort);
                /* Create new PrintWriter on created socket */
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (Exception e) {
                /* If there's an exception Print the Stack Trace */
                e.printStackTrace();
            }
        }
    }

    /* Thread for Enabling Connect Button */
    class ConnectEnableThread implements Runnable {
        /* Declare and Instantiate boolean for while loop */
        boolean finish = false;
        /* Variable for Acitivity Parameter */
        Open parent;
        /* Pass parent in on Constructor */
        public ConnectEnableThread(Open ma){
            this.parent = ma;
        }

        /* On Thread Running */
        @Override
        public void run() {
            /* While loop to run until Activity dies */
            while (!finish) {
                /* If A 4 digit Hex string present and connect has not recently been
                   pressed, enable connect button */
                if (hexCodeValidation() && !connectPressed) {
                    /* Run method on Thread that controls UI */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /* Enable the Connect Button */
                            connectButton.setEnabled(true);
                        }
                    });
                    /* Otherwise disable connect button. */
                } else {
                    /* Run method on Thread that controls UI */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /* Disable the Connect Button */
                            connectButton.setEnabled(false);
                        }
                    });
                }
                /* Pause thread for 1/10th of a second so the Thread doesn't run faster than needed. */
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    /* Method for Validating HexCode */
    public boolean hexCodeValidation(){
        /* Set Result to False Initially */
        boolean result = false;
        /* If text is a 4 digit hex string, set Result to true. */
        if ((hex.getText().toString().matches("^([0-9a-fA-F]{4})$")) &&
                !(hex.getText().toString().equals("FFFF")) &&
                !(hex.getText().toString().equals("0000"))) {
            result = true;
        } else {
            result = false;
        }
        /* Return the Result */
        return result;
    }

    /* If we decide to use an action bar this is where we add the options. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_open, menu);
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

    /* Method for getting IP Address on Wifi-enabled devices */
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

    /* When the Connect Button is pressed */
    public void connect(View view) throws InterruptedException {
        /* If connect has already been pressed, do not allow it to be pressed again: */
        connectButton.setEnabled(false);
        connectPressed = true;

        /* Connect Thread Instantiation */
        connectThread = new Thread(new ConnectThread(this));
        connectThread.setPriority(Thread.NORM_PRIORITY + 1);
        connectThread.start();


    }

    /* Connect Thread */
    class ConnectThread implements Runnable {

        Open parent;

        /* Thread Constructor */
        public ConnectThread(Open ma) {
            this.parent = ma;
        }

        @Override
        public void run() {
            /* Invalidate Test Result and networkAttempt and whileFinish */
            testResult = false;
            /* Uninstantiate PrintWriter */
            System.out.println(testResult.toString());
            out = null;
            /* Generate Intent. All new Activities are linked to old ones by Intent.
               Provides Runtime Bindings"                                              */
            intent = new Intent(parent, QuestionActivity.class);
            /* Get Site ID of Site Selected */
            site = customOnItemSelectedListener.getSelectedValue();
            /* Get hexCode and convert to Uppercase if not already */
            String hexCode = hex.getText().toString();
            hexCode = hexCode.toUpperCase();
            /* Get IP */
            IPDecoder ipDecoder = new IPDecoder(site, hexCode, sites, ips);
            serverIP = ipDecoder.getServerIP();
            /* Network Test */
            networkingThread = new Thread(new NetworkingThread(parent));
            networkingThread.start();

            /* Wait for Network to finish */
            try {
                networkingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /* Check Connection */
            successfulConnectionCheck();

            /* If connection was successful */
            if (testResult) {
                /* Add serverIP to Intent and start the next Activity */
                Open.this.intent.putExtra(SERVER_IP, serverIP);
                Open.this.startActivity(intent);
                /* Enable connectButton again */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Open.this.connectButton.setEnabled(true);
                        connectPressed = false;
                        return;
                    }
                });
                /* If the connection was unsuccessful */
            } else {
                /* Display Alert Dialog showing Connection Failure */
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      new AlertDialog.Builder(Open.this)
                                              .setTitle(R.string.connection_error)
                                              .setMessage("Connection to Server has Failed")
                                              .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                                                  public void onClick(DialogInterface dialog, int which) {
                                                      Open.this.connectButton.setEnabled(true);
                                                      connectPressed = false;
                                                      return;
                                                  }
                                              })
                                              .setIcon(android.R.drawable.ic_dialog_alert)
                                              .show();
                                  }
                              }


                );
            }
            }
        }

    /* Network Attempt Test Method */
    public void successfulConnectionCheck() {
        /* If the PrintWriter has been successfully instantiated */
        if (out != null) {
            /* Set Test Result to true */
            testResult = true;
            /* Send Closing Socket command to server and close socket */
            try {
                System.out.println("SOCKET_CLOSE");
                out.println("SOCKET_CLOSE");
                socket.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        /* If the PrintWriter has not been successfully instantiated */
        } else {
            /* Set Test Result to false */
            testResult = false;
        }
    }
}