package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Activity_CV_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_menu);

    }

    /** Called when the user taps Cyclo Voltammetry or Chronoamperometry button */
    public void TO_MAIN(View view) {
        Intent sndintent = new Intent(this, Activity_Pre_Scan_Settings.class);
        startActivity(sndintent);
    }


    /** Called when the user taps the Log Off button */
    public void Log_Off(View view) {
        // Updates device system setting
        Class_Log_In.setUserName(this, "0");
        Toast.makeText(this, "Logged off ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Activity_Log_in.class);
        startActivity(intent);
    }

}
