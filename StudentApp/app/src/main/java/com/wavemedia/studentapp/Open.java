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
    public final static String SITE_IP = "com.wavemedia.studentapp.SITE_IP";
    public final static String HEX_CODE = "com.wavemedia.studentapp.HEX_CODE";
    Spinner spinner;
    String site;
    String[] sites;
    EditText hex;
    CustomOnItemSelectedListener customOnItemSelectedListener = new CustomOnItemSelectedListener();
    Button connectButton;
    Thread connectEnableThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        hex  = (EditText) findViewById(R.id.editText);
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.site_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(customOnItemSelectedListener);

        sites = getResources().getStringArray(R.array.site_array);
        connectButton = (Button) findViewById(R.id.connectbutton);
        connectButton.setEnabled(false);

        connectEnableThread = new Thread(new ConnectEnableThread(this));
        connectEnableThread.start();
    }

    class ConnectEnableThread implements Runnable {
        boolean finish = false;
        Open parent;

        public ConnectEnableThread(Open ma){
            this.parent = ma;
        }

        @Override
        public void run() {
            while (!finish) {
                if (hexCodeValidation() && !(customOnItemSelectedListener.getSelectedValue().equals(sites[0]))) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connectButton.setEnabled(true);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connectButton.setEnabled(false);
                        }
                    });
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    /*public TextWatcher hexWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (hexCodeValidation() && !(customOnItemSelectedListener.getSelectedValue().equals(sites[0]))) {
                connectButton.setEnabled(true);
            } else {
                connectButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };*/

    public boolean hexCodeValidation(){
        boolean result = false;
        if (hex.getText().toString().matches("^([0-9a-fA-F]{4})$")) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_open, menu);
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

    public void connect(View view) {
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        site = customOnItemSelectedListener.getSelectedValue();
        Intent intent = new Intent(this,QuestionActivity.class);
        String hexCode = hex.getText().toString();
        hexCode = hexCode.toUpperCase();
        System.out.println(hexCode);
        intent.putExtra(SITE_IP, site);
        intent.putExtra(HEX_CODE, hexCode);
        startActivity(intent);
    }
}
