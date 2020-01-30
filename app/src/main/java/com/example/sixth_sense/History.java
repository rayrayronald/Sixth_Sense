package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



        try {
            String csvFilename = getFilesDir() + "/exe.csv";
            CSVReader csvReader = null;
            csvReader = new CSVReader(new FileReader(csvFilename));
            String[] row = null;
            while((row = csvReader.readNext()) != null) {
                System.out.println(row[0]
                        + " # " + row[1]);
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
