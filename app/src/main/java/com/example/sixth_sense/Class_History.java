package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class Class_History extends AppCompatActivity {

    private int Chosen_data = 1;
    String csv;
    CSVWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //////////////////////////////////////////////
        // Checks if data is available on local device storage
        //////////////////////////////////////////////



        // Access Database stored CSV if data is missing locally
        try {
            String CSV = Activity_Log_in.getUSER().getHistory();
            if (CSV != null) {
                File path = getFilesDir();
                File file = new File(path, "CV.csv");
                FileOutputStream stream = new FileOutputStream(file);
                try {
                    stream.write(CSV.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    stream.close();
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        Intent scan = new Intent(this, Activity_Scan_Results.class);
        startActivity(scan);

    }
}
