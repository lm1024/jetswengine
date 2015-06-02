/** Copyright (c) 2015 WaveMedia. All rights reserved */

package com.wavemedia.studentapp;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * Class for Listening to the Institution Dropdown Box
 * 
 * @author Harrison Holt-McHale
 * @version 1.0 29/05/2015
 *
 */
public class CustomOnItemSelectedListener extends Activity implements AdapterView.OnItemSelectedListener {

    /* Instantiate selected value for dropdown box as position 0. */
    int selectedValue = 0;

    /**
     * Getter to get the selected value 
     */
    public int getSelectedValue(){
        return selectedValue;
    }

    /**
     * When the user selects an item in the dropdown box, updated selected Value 
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedValue = position;
    }

    /* 
     * Required method to implement OnItemSelectedListener 
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
