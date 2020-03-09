package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
}
