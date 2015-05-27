package com.wavemedia.studentapp;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    //Thread receivingThread;
    //Thread sendingThread;
    Socket socket;

    //TextView receivedMessagesTextView;

    int siteIP;
    String hexCode;

    private String serverIP;
    private String localIP;
    //private boolean clientErrorOccured = false;

    PrintWriter out;
    //BufferedReader in;
    String message;

    /*private class ReceivingThread extends Thread {
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
                    //printToConsole("Received: \"" + input + "\"\n");
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
*/
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

    /*public void printToConsole(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                receivedMessagesTextView.append(message);
            }
        });

    }
*/
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
        //siteIP = intent.getIntExtra(Open.SITE_ID, 0);
        //hexCode = intent.getStringExtra(Open.HEX_CODE);
        serverIP = intent.getStringExtra(Open.SERVER_IP);
        localIP = getWifiIpAddress(this);

        //// Set IP for selected Site
        //switch (siteIP) {
        //    case 1:
        //        serverIP = ips[1];
        //        break;
        //    default:
        //        serverIP = "0.0";
        //        System.err.println("No site selected");
        //        break;
        //}
//
        //// Construct and display Final IP
        //serverIP += "." + getHexCodeIP(hexCode);
        //System.err.println(serverIP);

        // Network
        networkingThread = new Thread(new NetworkingThread(this));
        networkingThread.start();

        // Set Layout
        setContentView(R.layout.activity_question);
    }

    public String getHexCodeIP(String hexCode) {
        String[] hex;
        String hexCodeIP;
        hex = hexCode.split("(?!^)");
        String hexString1 = hex[0] + hex[1];
        String hexString2 = hex[2] + hex[3];
        int hexCodeIPString1 = hex2decimal(hexString1);
        System.out.println("WM IP: " + hexCode + " " + hex);
        System.out.println("WM IP: " + hexString1 + " -> " + hexCodeIPString1);
        int hexCodeIPString2 = hex2decimal(hexString2);
        System.out.println("WM IP: " + hexString2 + " -> " + hexCodeIPString2);
        hexCodeIP =  Integer.toString(hexCodeIPString1);
        hexCodeIP += "." + Integer.toString(hexCodeIPString2);
        System.out.println("WM IP: " + hexCodeIP);
        return hexCodeIP;
    }

    public static int hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
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
        // Send Option
        if(out != null) {
            try {
                out.println(localIP + ":" + message);
                //printToConsole("Sent: \"" + message + "\"\n");
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
        //Intent intent = new Intent(this, OptionA.class);
        //startActivity(intent);
    }

    /** Called when User clicks the B Option */
    public void optionB(View view) {
        // Send Option
        message = "B";
        sendOption();
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        //Intent intent = new Intent(this, OptionB.class);
        //startActivity(intent);
    }

    /** Called when User clicks the C Option */
    public void optionC(View view) {
        // Send Option
        message = "C";
        sendOption();
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        //Intent intent = new Intent(this, OptionC.class);
        //startActivity(intent);
    }

    /** Called when User clicks the D Option */
    public void optionD(View view) {
        // Send Option
        message = "D";
        sendOption();
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        //Intent intent = new Intent(this, OptionD.class);
        //startActivity(intent);
    }
}
