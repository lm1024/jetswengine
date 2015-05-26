package com.wavemedia.studentapp;

import android.app.Activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;


/**
 * Created by Harrison on 25/05/2015.
 */
public class CustomOnItemSelectedListener extends Activity implements AdapterView.OnItemSelectedListener {
    TextView selectedView;
    String selectedValue = "Select An Institution";

    public String getSelectedValue(){
        return selectedValue;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedView = (TextView) view;
        selectedValue = selectedView.getText().toString();
        Log.i("OnItemSelectedListener", "SelectValue =  " + selectedValue);

        //if (position != 0 && Open.hexCodeValidation(context)) {
        //    Open.setConnectButton(true, context);
        //} else {
        //    Open.setConnectButton(false, context);
        //}

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
