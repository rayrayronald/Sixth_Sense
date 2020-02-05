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

        //////////////////////////////////////////////
        // Checks if data is available on local device storage
        //////////////////////////////////////////////



        // Access Database stored CSV if data is missing locally
        try {
            String[] CSV = MainActivity.getP().getHistory();
            Log.v("Tag", "FOLLOWING DATA HAS BEEN RETRIEVED FROM DATABASE");
            for(String log : CSV) {
                if (log != null) {
                    Log.v("Tag", "Dataset \n"+ log);
                }
            }
            Log.v("Tag", "ABOVE DATA HAS BEEN RETRIEVED FROM DATABASE");
        } catch (SQLException e) {
            e.printStackTrace();
        }




        // Access device stored CSV and plot on graph
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


    }
}
