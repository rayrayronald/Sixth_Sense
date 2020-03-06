package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


import java.util.ArrayList;
import java.util.Random;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;



public class Scan_Activity extends AppCompatActivity {

    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private double y = 0;
    String csv;
    CSVWriter writer;
    List<String[]> data = new ArrayList<String[]>();
    String TimeStamp;
    String CSV_String = "";

    // NEW CODE GRAPH
    private static final String TAG = "MainActivity";

    //add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> xySeries;

    PointsGraphSeries<DataPoint> onClickSeries;

    //create graphview object
    GraphView mScatterPlot;

    //make xyValueArray global
    ArrayList<XYValue> xyValueArray;
    // NEW CODE GRAPH


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

        // Get time stamp
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy_HH:mm");
        java.util.Date date = new Date();
        TimeStamp = formatter.format(date);


        // NEW CODE GRAPH

        //declare graphview object
        mScatterPlot = (GraphView) findViewById(R.id.graph);

        xySeries = new PointsGraphSeries<>();

        //generate two lists of random values, one for x and one for y.
        xyValueArray = new ArrayList<>();
        double start = -100;
        double end = 100;
        for(int i = 0; i<40; i++){
            double randomX = new Random().nextDouble();
            double randomY = new Random().nextDouble();
            double x = start + (randomX * (end - start));
            double y = start + (randomY * (end - start));
            xyValueArray.add(new XYValue(x,y));
        }
        //sort it in ASC order
        xyValueArray = sortArray(xyValueArray);
        //add the data to the series
        for(int i = 0;i <xyValueArray.size(); i++){
            double x = xyValueArray.get(i).getX();
            double y = xyValueArray.get(i).getY();
            xySeries.appendData(new DataPoint(x,y),true, 1000);
        }

        createScatterPlot();

        // NEW CODE GRAPH


        // we get graph view instance
        //GraphView graph = (GraphView) findViewById(R.id.graph);
        // data
        //series = new LineGraphSeries<DataPoint>();
        //graph.addSeries(series);
        // customize a little bit viewport
        //Viewport viewport = graph.getViewport();
        //viewport.setYAxisBoundsManual(true);
        //viewport.setMinY(0);
        //viewport.setMaxY(12);
        //viewport.setMinX(00);
        //viewport.setMaxX(50);
        //viewport.setScrollable(true);
        //viewport.setScalable(true);
        //graph.getGridLabelRenderer().setHorizontalAxisTitle("Potential (V)");
        //graph.getGridLabelRenderer().setVerticalAxisTitle("Current (A)");
        //if (android.os.Build.VERSION.SDK_INT > 9) {
            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            //StrictMode.setThreadPolicy(policy);
        //}
        // Saves CSV
        //try {
            //csv = getFilesDir() + "/" + TimeStamp + ".csv";
            //writer = new CSVWriter(new FileWriter(csv));
        //} catch (FileNotFoundException e) {
            //e.printStackTrace();
        //} catch (IOException e) {
            //e.printStackTrace();
        //}

    }
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
                // Uploads CSV onto Database with fake data
                try {
                    String location = "1,2";
                    String NFC_ID = "0x111";
                    Boolean Virus = false;
                    MainActivity.getP().create(TimeStamp, location,NFC_ID, CSV_String, Virus);
                    System.out.println("ABOVE DATA HAS BEEN WRITTEN ONTO DATABASE");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
    private void compileCSV() {
        try {
            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String csvFilename = getFilesDir() + "/" + TimeStamp + ".csv";
            CSVReader csvReader = null;
            csvReader = new CSVReader(new FileReader(csvFilename));
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
    }*/


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
        xySeries.setColor(Color.BLUE);
        xySeries.setSize(20f);

        //set Scrollable and Scaleable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(150);
        mScatterPlot.getViewport().setMinY(-150);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(150);
        mScatterPlot.getViewport().setMinX(-150);

        mScatterPlot.addSeries(xySeries);
    }

    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size()-1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");

        while(true){
            m--;
            if(m <= 0){
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try{
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
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
                    Log.d(TAG, "sortArray: count = " + count);
                }

                else if(array.get(m).getX() > array.get(m-1).getX()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
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



        /*String dbUrl = "jdbc:postgresql://ec2-46-137-120-243.eu-west-1.compute.amazonaws.com:5432/daku93qk12ot3o?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&user=ejzndyzesfoyqk&password=e16216b71f70ac9b098db783817afd90c45b71a0b4c117590985968f9ea31bb8";
        try {
            // Registers the driver
            Class.forName("org.postgresql.Driver");

            Connection conn= DriverManager.getConnection(dbUrl);

            Statement s=conn.createStatement();
            String sqlStr = "SELECT * FROM patients WHERE id>1;";
            ResultSet rset=s.executeQuery(sqlStr);
            while(rset.next()){
                Log.d("66666666666666666666666", rset.getInt("id")+" "+ rset.getString("familyname"));
                textView.setText(rset.getString("familyname"));

            }
            rset.close();
            s.close();
            conn.close();
        }
        catch (Exception e){
            String stackTrace = Log.getStackTraceString(e);
            Log.d("DEBUG!",stackTrace);
        }*/



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

