package com.wavemedia.studentapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class QuestionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
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

    /** Called when User clicks the A Option */
    public void optionA(View view) {
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        Intent intent = new Intent(this, OptionA.class);
        startActivity(intent);
    }

    /** Called when User clicks the B Option */
    public void optionB(View view) {
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        Intent intent = new Intent(this, OptionB.class);
        startActivity(intent);
    }

    /** Called when User clicks the C Option */
    public void optionC(View view) {
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        Intent intent = new Intent(this, OptionC.class);
        startActivity(intent);
    }

    /** Called when User clicks the D Option */
    public void optionD(View view) {
        // Generate Intent. All new Activities are linked to old ones by Intent. "Provides Runtime Bindings"
        Intent intent = new Intent(this, OptionD.class);
        startActivity(intent);
    }
}
