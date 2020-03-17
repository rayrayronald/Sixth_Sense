package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;

public class Log_in_Activity extends AppCompatActivity {

    private static Object_Database db = new Object_Database();
    private static Object_User P = new Object_User();
    // Getters
    public static Object_Database getDb() {
        return db;
    }
    public static Object_User getP() {
        return P;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if (db.connect()) {
            // Passes on Statement s to parent
            P.setS(db.getS());
        } else {
            Toast.makeText(this, "CANNOT CONNECT TO DATABASE", Toast.LENGTH_LONG).show();
        }

        // Check device system settings to see if user is logged in
        if (Class_Log_In.getUserName(Log_in_Activity.this).length() == 1 || Class_Log_In.getUserName(Log_in_Activity.this) == "0") {
            // Continues app activity
        } else {
            // Prompts log in window
            Intent intent = new Intent(Log_in_Activity.this, Choose_CV.class);
            startActivity(intent);
        }


    }

    public void log_in_button (View view) {
        // Get user inputs from TextView
        TextView username_input = findViewById(R.id.username_input);
        String username_string = username_input.getText().toString();
        TextView password_input = findViewById(R.id.password_input);
        String password_string = password_input.getText().toString();



        try {
            // Check credentials with database
            if (getP().login(username_string, password_string)){
                // Save credentials to system file
                Toast.makeText(this, "LOGGED IN: " + username_string, Toast.LENGTH_SHORT).show();
                Class_Log_In.setUserName(Log_in_Activity.this,username_string);
                Class_Log_In.setPassword(Log_in_Activity.this,password_string);
                Intent intent = new Intent(Log_in_Activity.this, Choose_CV.class);
                startActivity(intent);
            } else {
                Toast.makeText(this,"WRONG LOG IN" , Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

