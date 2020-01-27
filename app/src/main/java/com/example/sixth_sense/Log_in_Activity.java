package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;

public class Log_in_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void log_in_button (View view) {
        // Get user inputs from TextView
        TextView username_input = findViewById(R.id.username_input);
        String username_string = username_input.getText().toString();
        TextView password_input = findViewById(R.id.password_input);
        String password_string = password_input.getText().toString();
        try {
            // Check credentials with database
            if (MainActivity.getP().login(username_string, password_string)){
                // Save credentials to system file
                Toast.makeText(this, "LOGGED IN: " + username_string, Toast.LENGTH_SHORT).show();
                User_Account_Class.setUserName(Log_in_Activity.this,username_string);
                User_Account_Class.setPassword(Log_in_Activity.this,password_string);
                Intent intent = new Intent(Log_in_Activity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this,"WRONG LOG IN" , Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

