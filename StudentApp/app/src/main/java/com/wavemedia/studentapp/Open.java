package com.wavemedia.studentapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;



public class Open extends ActionBarActivity {
    public final static String SITE_IP = "com.wavemedia.studentapp.SITE_IP";
    public final static String HEX_CODE = "com.wavemedia.studentapp.HEX_CODE";
    Spinner spinner;
    String site;
    EditText hex;
    CustomOnItemSelectedListener customOnItemSelectedListener = new CustomOnItemSelectedListener();

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
        intent.putExtra(SITE_IP, site);
        intent.putExtra(HEX_CODE, hexCode);
        startActivity(intent);
    }
}
