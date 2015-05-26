package com.wavemedia.studentapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Open extends ActionBarActivity {

    // Intent Extra Labels
    public final static String SITE_ID = "com.wavemedia.studentapp.SITE_ID";
    public final static String HEX_CODE = "com.wavemedia.studentapp.HEX_CODE";

    Spinner spinner;                // Dropdown Box Declaration
    int site;                       // Dropdown Box Selected Value Declaration
    int[] sites;                    // IDs for Sites
    EditText hex;                   // Hex Code Text Box
    Button connectButton;           // Connect Button
    Thread connectEnableThread;     // Thread for Enabling Connect Button Declaration
    Boolean check;

    // Instantiate Listener for Institution Dropdown Box Selection
    CustomOnItemSelectedListener customOnItemSelectedListener = new CustomOnItemSelectedListener();

    Thread preliminaryNetworkingThread;

    // On Application Open, First Activity Runs:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set view to activity_open.xml
        setContentView(R.layout.activity_open);

        hex  = (EditText) findViewById(R.id.editText);  // Instantiate HexCode input to XML layout
        spinner = (Spinner) findViewById(R.id.spinner); // Instantiate DD Box input to XML layout

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

        // Instantiate Connect Button
        connectButton = (Button) findViewById(R.id.connectbutton);
        // Initially Disable until adequate data input
        connectButton.setEnabled(false);

        // Instantiate and start thread for enabling connect button
        connectEnableThread = new Thread(new ConnectEnableThread(this));
        connectEnableThread.start();
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
                if (hexCodeValidation() && !(customOnItemSelectedListener.getSelectedValue() == sites[0])) {
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // When the Connect Button is pressed
    public void connect(View view) throws InterruptedException {
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        Intent intent = new Intent(this,QuestionActivity.class);
        // Get Site ID of Site Selected
        site = customOnItemSelectedListener.getSelectedValue();
        // Get hexCode and Convert input hexCode to Uppercase if not already
        String hexCode = hex.getText().toString();
        hexCode = hexCode.toUpperCase();
        //System.out.println(hexCode);

        // Add hexCode and Site ID to Intent and start Question Activity.
        intent.putExtra(SITE_ID, site);
        intent.putExtra(HEX_CODE, hexCode);

        //Final Check
//        preliminaryNetworkingThread = new Thread(new PreliminaryNetworkingThread(this));
//        preliminaryNetworkingThread.start();
//        preliminaryNetworkingThread.join();




        startActivity(intent);
    }
}
