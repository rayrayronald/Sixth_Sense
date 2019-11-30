package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.sixth_sense.MESSAGE";
    /*public static double VALUE_1_1 = 1.1;
    public double VALUE_1_2 = 1.2;
    public double VALUE_1_3 = 1.3;
    public double VALUE_1_4 = 1.4;
    public double VALUE_2_1 = 2.1;
    public double VALUE_2_2 = 2.2;
    public double VALUE_3_1 = 3.1;
    public double VALUE_3_2 = 3.2;
    public static String NAME_1_1 = "VAL 1.1";
    public String NAME_1_2 = "VAL 1.2";
    public String NAME_1_3 = "VAL 1.3";
    public String NAME_1_4 = "VAL 1.4";
    public String NAME_2_1 = "VAL 2.1";
    public String NAME_2_2 = "VAL 2.2";
    public String NAME_3_1 = "VAL 3.1";
    public String NAME_3_2 = "VAL 3.2";*/
    public static String NAMES[] = {"VAL 1.1","VAL 1.2","VAL 1.3","VAL 1.4","VAL 2.1","VAL 2.2","VAL 3.1","VAL 4.1"};
    public static double VALUES[] = {1.1,1.2,1.3,1.4,2.1,2.2,3.1,4.1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy_HH:mm");
        Date date = new Date();
        System.out.println(formatter.format(date));

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(formatter.format(date));

    }

    /** Called when the user taps the preset button */
    public void presets(View view) {
        Intent preintent = new Intent(this, DisplayMessageActivity.class);
        startActivity(preintent);
    }

    /** Called when the user taps the Connect button */
    public void connect(View view) {
        Intent intent = new Intent(this, Scan.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }



    /** Called when the user taps the Settings button */
    public void SubSet(View view) {
        Intent sndintent = new Intent(this, SubSettingActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        sndintent.putExtra(EXTRA_MESSAGE, message);
        startActivity(sndintent);
    }


    /** Called when the user taps the Settings button */
    public void NFC_con(View view) {
        Intent sndintent = new Intent(this, SubSettingActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        sndintent.putExtra(EXTRA_MESSAGE, message);
        startActivity(sndintent);
    }
}
