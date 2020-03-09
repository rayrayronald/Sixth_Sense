package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
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
//    private static Database_Object_Class db = new Database_Object_Class();
//    private static User_Object_Class P = new User_Object_Class();
//    // Getters
//    public static Database_Object_Class getDb() {
//        return db;
//    }
//    public static User_Object_Class getP() {
//        return P;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Check device system settings to see if user is logged in
//        if (User_Account_Class.getUserName(MainActivity.this).length() == 0) {
//            // Prompts log in window
//            Intent intent = new Intent(MainActivity.this, Log_in_Activity.class);
//            startActivity(intent);
//        } else {
//            // Continues app activity
//        }

        // Connects Database_Object_Class and displays relevant parent & child info
//        if (db.connect()) {
//            // Passes on Statement s to parent
//            P.setS(db.getS());
//            // Logs in with saved account credentials to retrieve data
//            try {
//                P.login(User_Account_Class.getUserName(MainActivity.this), User_Account_Class.getPassword(MainActivity.this));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            //display_child_info(child_selected);
//
//        } else {
//            Toast.makeText(this, "CANNOT CONNECT TO DATABASE", Toast.LENGTH_LONG).show();
//        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy_HH:mm");
        Date date = new Date();
        System.out.println(formatter.format(date));

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(formatter.format(date));

    }

    /** Called when the user taps start scan button */
    public void TO_SCAN(View view) {
        Intent scan = new Intent(this, MainActivity.class);
        startActivity(scan);
    }

    /** Called when the user taps the preset button */
    public void presets(View view) {
        Intent preintent = new Intent(this, DisplayMessageActivity.class);
        startActivity(preintent);
    }

    /** Called when the user taps the Connect button */
    public void connect(View view) {
        Intent intent = new Intent(this, Scan_Activity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }



    /** Called when the user taps the Settings button */
    public void SubSet(View view) {
        Intent sndintent = new Intent(this, SubSettingActivity.class);
        startActivity(sndintent);
    }


    /** Called when the user taps the Log Off button */
    public void Log_Off(View view) {
        // Updates device system setting
        User_Account_Class.setUserName(MainActivity.this, "0");
        Toast.makeText(this, "Logged off ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, Log_in_Activity.class);
        startActivity(intent);
    }

    /** Called when the user taps the History button */
    public void History(View view) {
        Intent sndintent = new Intent(this, History.class);
        startActivity(sndintent);
    }
    /** Called when the user taps the History button */
    public void NFCCC(View view) {
        Intent haha = new Intent(this, NFC.class);
        startActivity(haha);
    }
}
