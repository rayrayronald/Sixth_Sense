package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;

public class Activity_Log_in extends AppCompatActivity {

    private static Object_Database db = new Object_Database();
    private static Object_User USER = new Object_User();
    // Getters
    public static Object_Database getDb() {
        return db;
    }
    public static Object_User getUSER() {
        return USER;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if (db.connect()) {
            // Passes on Statement s to parent
            getUSER().setS(getDb().getS());
        } else {
            Toast.makeText(this, "CANNOT CONNECT TO DATABASE", Toast.LENGTH_LONG).show();
        }

        // Check device system settings to see if user is logged in
        if (Class_Log_In.getUserName(Activity_Log_in.this).length() == 1 || Class_Log_In.getUserName(Activity_Log_in.this) == "0") {
            // Continues app activity
        } else {
            // Prompts log in window
            try {
                if(getUSER().login(Class_Log_In.getUserName(Activity_Log_in.this), Class_Log_In.getPassword(Activity_Log_in.this))) {
                    Intent intent = new Intent(Activity_Log_in.this, Activity_CV_Menu.class);
                    startActivity(intent);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

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
            if (getUSER().login(username_string, password_string)){
                // Save credentials to system file
                Toast.makeText(this, "LOGGED IN: " + username_string, Toast.LENGTH_SHORT).show();
                Class_Log_In.setUserName(Activity_Log_in.this,username_string);
                Class_Log_In.setPassword(Activity_Log_in.this,password_string);
                Intent intent = new Intent(Activity_Log_in.this, Activity_CV_Menu.class);
                startActivity(intent);
            } else {
                Toast.makeText(this,"WRONG LOG IN" , Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

