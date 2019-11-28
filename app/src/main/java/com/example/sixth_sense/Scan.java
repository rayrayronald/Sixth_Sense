package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;



public class Scan extends AppCompatActivity {

    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private double[]  x_array = new double[300];
    private double[]  y_array = new double[300];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView12);
        textView.setText(message);

        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.graph);
        // data
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(12);
        viewport.setMinX(00);
        viewport.setMaxX(50);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Potential (V)");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Current (A)");

    }
    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 50; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }

    // add random data to graph
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        //series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), false, 50);
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), false, 50);

    }

}




        /*


import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import android.content.Context;
import android.widget.Toast;

        InputStream inputStream = getResources().openRawResource(R.raw.cv_plot);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String line;
        try {
            int i = 0;
            while((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                //textView.setText(tokens[1]);
                if (i > 10 && i%2==0){
                    x_array[i] = Double.parseDouble(String.valueOf(tokens[0]));
                    y_array[i] = Double.parseDouble(String.valueOf(tokens[1]));
                    Log.d("VariableTag", tokens[0]);
                    series.appendData(new DataPoint(x_array[i], x_array[i]), false, 300);
                }

                i++;
            }


        } catch (IOException e) {

        }

    }*/
        /*ItemArrayAdapter itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.activity_scan);

        InputStream inputStream = getResources().openRawResource(R.raw.cv_plot);
        CSVFile csvFile = new CSVFile(inputStream);
        List <String> scoreList = csvFile.read();
        String var = scoreList.get(4);
        textView.setText(var);

        try {
            CSVReader reader = new CSVReader(new FileReader("/raw/cv_plot.csv"));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + nextLine[1] + "etc...");
                Log.d("VariableTag", nextLine[0]);
                textView.setText(String.valueOf(nextLine[0]));
            }
        } catch (IOException e) {

        }


        try{
            int a = 1;
            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(R.raw.cv_plot)));//Specify asset file name
            String [] nextLine;
            int b = 2;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + nextLine[1] + "etc...");
                Log.d("VariableTag", nextLine[0]);
                textView.setText(String.valueOf(nextLine[0]));

            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }



        for(String[] scoreData:scoreList ) {
            itemArrayAdapter.add(scoreData);
        }


        try {

            // Create an object of filereader
            // class with CSV file as a parameter.
            System.out.print("HAHAHAHAHAHA\t");

            FileReader filereader = new FileReader("raw/cv_plot.csv");

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                int i = 0;
                float x = 0, y = 0;
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                    if(i==0){
                        x=Float.parseFloat(cell);
                    } else{
                        y=Float.parseFloat(cell);
                    }
                    i++;
                }
                series.appendData(new DataPoint(x, y), false, 300);
                //TextView textView = findViewById(R.id.textView12);
                textView.setText(String.valueOf(y));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph

    }

    // add random data to graph
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        //series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), false, 50);
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), false, 50);
    }

}*/

