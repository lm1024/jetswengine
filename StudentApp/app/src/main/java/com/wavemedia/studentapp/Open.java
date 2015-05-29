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

public class Open extends ActionBarActivity {

    // Intent Extra Labels
    public final static String SERVER_IP = "com.wavemedia.studentapp.SERVER_IP";

    Spinner spinner;                // Dropdown Box Declaration
    int site;                       // Dropdown Box Selected Value Declaration
    int[] sites;                    // IDs for Sites
    String[] ips;                   // IPs for Sites
    EditText hex;                   // Hex Code Text Box
    Button connectButton;           // Connect Button
    Thread connectEnableThread;     // Thread for Enabling Connect Button Declaration
    Thread networkingThread;        // Thread for preliminary Network Test
    Socket socket;                  // Socket for preliminary Network Test
    PrintWriter out;                // PrintWriter for preliminary Network Test
    String serverIP;                // Server IP for preliminary Network Test
    Boolean testResult = false;
    String localIP;


    // Instantiate Listener for Institution Dropdown Box Selection
    CustomOnItemSelectedListener customOnItemSelectedListener = new CustomOnItemSelectedListener();

    // On Application Open, First Activity Runs:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set view to activity_open.xml
        setContentView(R.layout.activity_open);

        hex  = (EditText) findViewById(R.id.editText);  // Instantiate HexCode input to XML layout
        spinner = (Spinner) findViewById(R.id.spinner); // Instantiate DD Box input to XML layout

        localIP = getWifiIpAddress(this);

        // Adapt Array from arrays.xml for DD Box. Set layout style.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.site_array,android.R.layout.simple_spinner_item);
        // Set View Resource layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Add ArrayAdapter to Dropdown box
        spinner.setAdapter(adapter);
        // Add Item Selection Listener to Dropdown Box
        spinner.setOnItemSelectedListener(customOnItemSelectedListener);

        // Use Array of numbers 0 - Number of Institutions to compare with DD Box selection
        sites = getResources().getIntArray(R.array.site_id);

        // Get list of IPs for site IDS
        ips = getResources().getStringArray(R.array.ip_array);

        // Instantiate Connect Button
        connectButton = (Button) findViewById(R.id.connectbutton);
        // Initially Disable until adequate data input
        connectButton.setEnabled(false);

        // Instantiate and start thread for enabling connect button
        connectEnableThread = new Thread(new ConnectEnableThread(this));
        connectEnableThread.start();
    }

    class NetworkingThread implements Runnable {

        Open parent;

        public NetworkingThread(Open ma) {
            this.parent = ma;
        }

        @Override
        public void run() {
            try {
                if(out == null) {
                    new SendingThread().run();
                    System.out.println("Check 2");
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    socket.close();
                    out = null;
                    System.out.println("Check 3");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
                int serverPort = QuestionActivity.SERVER_PORT;
                socket = new Socket(serverAddr, serverPort);
                System.err.println("WM: Creating output");
                out = new PrintWriter(socket.getOutputStream(), true);
                System.err.println("WM: sending IP");
            } catch (Exception e) {
                System.err.println("WM: shits fucked");
                e.printStackTrace();
            }
        }
    }

    // Thread for Enabling Connect Button
    class ConnectEnableThread implements Runnable {
        // Thread Set-Up
        boolean finish = false;
        Open parent;
        public ConnectEnableThread(Open ma){
            this.parent = ma;
        }

        // Run thread.
        @Override
        public void run() {
            // While loop to run basically forever
            while (!finish) {
                // If A 4 digit Hex string present and an Institution selected, enable connect button
                if (hexCodeValidation() /*&& !(customOnItemSelectedListener.getSelectedValue() == sites[0])*/) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connectButton.setEnabled(true);
                        }
                    });
                    // Otherwise disable connect button.
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connectButton.setEnabled(false);
                        }
                    });
                }
                //Pause thread for 1/10th of a second so the Thread doesn't run faster than needed.
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    // Method for Validating HexCode
    public boolean hexCodeValidation(){
        //Set Result to False Initially
        boolean result = false;
        // If text is a 4 digit hex string, set Result to true.
        if (hex.getText().toString().matches("^([0-9a-fA-F]{4})$")) {
            result = true;
        } else {
            result = false;
        }
        // Return the Result
        return result;
    }

    // If we decide to use an action bar this is where we add the options.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_open, menu);
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



    // When the Connect Button is pressed
    public void connect(View view) throws InterruptedException {
        // Invalidate Test Result
        testResult = false;
        out = null;
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        Intent intent = new Intent(this,QuestionActivity.class);
        // Get Site ID of Site Selected
        site = customOnItemSelectedListener.getSelectedValue();
        // Get hexCode and Convert input hexCode to Uppercase if not already
        String hexCode = hex.getText().toString();
        hexCode = hexCode.toUpperCase();

        //Get IP
        IPDecoder ipDecoder = new IPDecoder(site,hexCode,sites,ips);
        serverIP = ipDecoder.getServerIP();

        //Network Test
        networkingThread = new Thread(new NetworkingThread(this));
        networkingThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Successful Connection?
        if (out != null) {
            testResult = true;
            //Close socket
            try {
                out.println(localIP+":"+"SOCKET_CLOSE");
                socket.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            testResult = false;
        }

        if (testResult) {
            intent.putExtra(SERVER_IP, serverIP);
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Connection Error")
                    .setMessage("Connection to Server has Failed")
                    .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            }
        }
    }




