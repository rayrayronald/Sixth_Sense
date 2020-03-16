package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class newdesignsettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdesignsettings);
    }

    public void TO_SCAN(View view) {
        Intent scan = new Intent(this, Scan_Activity.class);
        EditText editText = (EditText) findViewById(R.id.editText8);
        String message = editText.getText().toString();
        scan.putExtra("voltage", message);
        startActivity(scan);
    }
}
