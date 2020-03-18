package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;


public class Activity_Pre_Scan_Settings extends AppCompatActivity implements LocationListener {


    protected LocationManager locationManager;
    protected Context context;
    int PERMISSION_ID = 66;
    ProgressBar simpleProgressBar;
    //Metadata
    private static double latitude;
    private static double longitude;

    public static double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public static double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_scan_settings);
        simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        if ( isLocationEnabled() && checkPermissions()) {
            // Get location
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            requestPermissions();
        }
    }




    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Granted. Start getting the location information
                Intent sndintent = new Intent(this, Activity_Pre_Scan_Settings.class);
                startActivity(sndintent);
            }
        }
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }


    @Override
    public void onLocationChanged(Location this_location) {
        setLatitude(this_location.getLatitude());
        setLongitude(this_location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    private void setProgressValue(final int progress) {

        // set the progress
        simpleProgressBar.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 5);
            }
        });
        thread.start();
    }





    public void TO_SCAN(View view) {
        final Intent scan = new Intent(this, Activity_Scan_Results.class);
        EditText editText8 = (EditText) findViewById(R.id.editText8);
        scan.putExtra("VOLTAGE", editText8.getText().toString());
        EditText editText7 = (EditText) findViewById(R.id.editText7);
        scan.putExtra("DELAY", editText7.getText().toString());
        EditText editText6 = (EditText) findViewById(R.id.editText6);
        scan.putExtra("CYCLE", editText6.getText().toString());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(scan);

            }
        }, 500);
        setProgressValue(5);

    }
}
