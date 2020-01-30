package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class History extends AppCompatActivity {

    private int Chosen_data = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        // Access device stored CSV
        /*try {
            String csvFilename = getFilesDir() + "/exe.csv";
            CSVReader csvReader = null;
            csvReader = new CSVReader(new FileReader(csvFilename));
            String[] row = null;
            while((row = csvReader.readNext()) != null) {
                System.out.println(row[0] + "," + row[1]);
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // Access Database stored CSV
        try {
            String[] CSV = MainActivity.getP().getHistory();
            for(String log : CSV) {
                if (log != null) {
                    Log.v("Tag",log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
