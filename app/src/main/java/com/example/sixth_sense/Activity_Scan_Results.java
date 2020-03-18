package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Activity_Scan_Results extends AppCompatActivity {

    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;

    String CSV_PATH;
    CSVWriter writer;
    List<String[]> data = new ArrayList<String[]>();
    List<String[]> metadata = new ArrayList<String[]>();

    String TimeStamp;
    String CSV_String = "";
    String TAG = "DEBUGGING_SIXTH_SENSE";

    //add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> xySeries;
    PointsGraphSeries<DataPoint> onClickSeries;
    //create graphview object
    GraphView mScatterPlot;
    //make xyValueArray global
    ArrayList<Class_XYValue> xyValueArray;


    //Metadata
    String Hardware_ID;
    Boolean Virus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_scan_results);

        // Get time stamp
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy_HH:mm:ss");
        java.util.Date date = new Date();
        TimeStamp = formatter.format(date);


        // Declare graphview object
        mScatterPlot = (GraphView) findViewById(R.id.graph);
        xySeries = new PointsGraphSeries<>();
        xyValueArray = new ArrayList<>();

        // Check if CV.csv exists and imports if not
        String csvFilename = getFilesDir() + "/CV.csv";
        File f = new File(csvFilename);
        if (!f.exists()) {
            // Access Database stored CSV if data is missing locally
            try {
                String CSV = Activity_Log_in.getUSER().getHistory();
                if (CSV != null) {
                    FileOutputStream stream = new FileOutputStream(f);
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
        }






        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
            String[] row = null;
            boolean first = true;
            double x,y,ran;
            while((row = csvReader.readNext()) != null) {
                x = Double.valueOf(row[0]);
                ran = new Random().nextDouble();
                y = Double.valueOf(row[1]) + ran/50;
                System.out.println(x + "," + y);
                xyValueArray.add(new Class_XYValue(Double.valueOf(x),Double.valueOf(y)));
                data.add(new String[] {String.valueOf(x),String.valueOf(y)});

            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (new Random().nextBoolean()) {
            Virus = true;
        } else {
            Virus = false;
        }


        metadata.add(new String[] {"Voltage Step (mV)",intent.getStringExtra("VOLTAGE")});
        metadata.add(new String[] {"Time Delay (ms)",intent.getStringExtra("DELAY")});
        metadata.add(new String[] {"Cycles",intent.getStringExtra("CYCLE")});
        metadata.add(new String[] {"Infection", Virus.toString()});
        compileCSV();







        //sort it in ASC order
        xyValueArray = sortArray(xyValueArray);
        //add the data to the series
        for(int i = 0;i <xyValueArray.size(); i++){
            double x = xyValueArray.get(i).getX();
            double y = xyValueArray.get(i).getY();
            xySeries.appendData(new DataPoint(x,y),true, 1000);
        }

        createScatterPlot();

    }





    private void compileCSV() {

        //Saves CSV
        try {
            CSV_PATH = getFilesDir() + "/" + TimeStamp + ".csv";
            writer = new CSVWriter(new FileWriter(CSV_PATH));
            writer.writeAll(metadata);
            writer.writeAll(data);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            CSVReader csvReader = new CSVReader(new FileReader(CSV_PATH));
            String[] row = null;
            System.out.println("FOLLOWING DATA HAS BEEN WRITTEN ONTO LOCAL DEVICE STORAGE");
            while((row = csvReader.readNext()) != null) {
                System.out.println(row[0] + " , " + row[1]);
                CSV_String = CSV_String + row[0] + "," + row[1] + "\n";
            }
            csvReader.close();
            System.out.println("ABOVE DATA HAS BEEN WRITTEN ONTO LOCAL DEVICE STORAGE");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Uploads CSV onto Database with fake data
        try {
            Hardware_ID = "0x111";
            Activity_Log_in.getUSER().create(TimeStamp, Activity_Pre_Scan_Settings.getLatitude(), Activity_Pre_Scan_Settings.getLongitude(), Hardware_ID, CSV_String, Virus);
            System.out.println("ABOVE DATA HAS BEEN WRITTEN ONTO DATABASE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    /** Called when the user taps Refresh button */
    public void Refresh(View view) {
        Intent Refresh = new Intent(this, Activity_Scan_Results.class);
        startActivity(Refresh);
    }
    public void HOME(View view) {
        Intent Activity_CV_Menu = new Intent(this, Activity_CV_Menu.class);
        startActivity(Activity_CV_Menu);
    }








    private void createScatterPlot() {
        Log.d(TAG, "createScatterPlot: Creating scatter plot.");



        xySeries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Log.d(TAG, "onTap: You clicked on: (" + dataPoint.getX() +
                        "," + dataPoint.getY() + ")");
                //declare new series
                onClickSeries = new PointsGraphSeries<>();
                onClickSeries.appendData(new DataPoint(dataPoint.getX(),dataPoint.getY()),true, 100);
                onClickSeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
                onClickSeries.setColor(Color.RED);
                onClickSeries.setSize(25f);
                mScatterPlot.removeAllSeries();
                mScatterPlot.addSeries(onClickSeries);
                toastMessage("x = " + dataPoint.getX() + "\n" +
                        "y = " + dataPoint.getY() );
                createScatterPlot();
            }
        });

        //set some properties
        xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setColor(Color.DKGRAY);
        xySeries.setSize(10f);

        //set Scrollable and Scaleable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(0.1);
        mScatterPlot.getViewport().setMinY(-0.1);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(1);
        mScatterPlot.getViewport().setMinX(-1);

        mScatterPlot.addSeries(xySeries);
    }


    //Sorts an ArrayList<Class_XYValue> with respect to the x values.
    private ArrayList<Class_XYValue> sortArray(ArrayList<Class_XYValue> array){
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size()-1;
        int count = 0;
        while(true){
            m--;
            if(m <= 0){
                m = array.size() - 1;
            }
            try{
                double tempY = array.get(m-1).getY();
                double tempX = array.get(m-1).getX();
                if(tempX > array.get(m).getX() ){
                    array.get(m-1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m-1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                }
                else if(tempY == array.get(m).getY()){
                    count++;
                    //Log.d(TAG, "sortArray: count = " + count);
                }

                else if(array.get(m).getX() > array.get(m-1).getX()){
                    count++;
                    //Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if(count == factor ){
                    break;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}


/*
        if (f.exists() && !f.isDirectory()) {
            // Access device stored CSV and plot on graph
            try {
                CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
                String[] row = null;
                boolean first = true;
                while((row = csvReader.readNext()) != null) {
                    if (!first) {
                        System.out.println(row[0] + "," + row[1]);
                        xyValueArray.add(new Class_XYValue(Double.valueOf(row[0]),Double.valueOf(row[1])));
                    } else {
                        first = false;
                    }


                }
                csvReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            textView.setText("Using real data");
            buttonview.setText("Delete");
        } else {

            //generate two lists of random values, one for x and one for y.
            xyValueArray = new ArrayList<>();
            double start = -0.1;
            double end = 0.1;
            for(int i = 0; i<40; i++){
                double randomX = new Random().nextDouble();
                double randomY = new Random().nextDouble();
                double x = start * 10 + (randomX * (end - start) * 10);
                double y = start + (randomY * (end - start));
                //delete previous lines of code and take in values from CSV from res folder https://stackoverflow.com/questions/19974708/reading-csv-file-in-resources-folder-android/19976110#19976110
                xyValueArray.add(new Class_XYValue(x,y));
                data.add(new String[] {String.valueOf(x),String.valueOf(y)});
            }


            // Sets scan background information

            if (new Random().nextBoolean()) {
                Virus = true;
            } else {
                Virus = false;
            }


            metadata.add(new String[] {"Voltage Step (mV)",intent.getStringExtra("VOLTAGE"),"Time Delay (ms)",intent.getStringExtra("DELAY"),"Cycles",intent.getStringExtra("CYCLE"),"Infection", Virus.toString()});

            compileCSV();
            textView.setText("Using random data");
            buttonview.setText("Import");

        }*/




    /*@Override
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
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
                // Saves compiled CSV onto device storage
                compileCSV();

            }
        }).start();
    }

    // add random data to graph
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        //series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), false, 50);
        y = RANDOM.nextDouble() * 10d;
        series.appendData(new DataPoint(lastX, y), false, 50);
        data.add(new String[] {String.valueOf(lastX),String.valueOf(y)});
        lastX++;
    }
*/
