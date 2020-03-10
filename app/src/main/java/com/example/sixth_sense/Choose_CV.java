package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Choose_CV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__cv);
    }

    /** Called when the user taps Cyclo Voltammetry or Chronoamperometry button */
    public void TO_MAIN(View view) {
        Intent sndintent = new Intent(this, newdesignsettings.class);
        startActivity(sndintent);
    }

    /** Called when the user taps DB button */
    public void DB(View view) {
        Intent sndintent = new Intent(this, History.class);
        startActivity(sndintent);
    }

    /** Called when the user taps the Log Off button */
    public void Log_Off(View view) {
        // Updates device system setting
        User_Account_Class.setUserName(this, "0");
        Toast.makeText(this, "Logged off ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Log_in_Activity.class);
        startActivity(intent);
    }

}
