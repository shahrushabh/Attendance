package com.iastate.i_attend;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class AttendanceActivity extends AppCompatActivity implements LocationListener {

    int courseID;
    String type;
    LocationManager locationManager;
    Button b;
    Double lat, lon;

    SharedPreferences SP;
    boolean gpsAllowed;

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        courseID = i.getIntExtra("courseID", -1);
        type = i.getStringExtra("type");
        lat = i.getDoubleExtra("latitude", 0);
        lon = i.getDoubleExtra("longitude", 0);

        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        gpsAllowed = SP.getBoolean("allowGpsUsage",false);

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        Log.d("GPS Usage Allowance", Boolean.toString(gpsAllowed));


        setContentView(R.layout.activity_attendance_student);

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        b = (Button) findViewById(R.id.check);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                    if (isGPSEnabled) {

                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, AttendanceActivity.this);

                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(getApplicationContext(), "LA: " + location.getLatitude() + " LO: " + location.getLongitude(), duration).show();

        double curLat = location.getLatitude();
        double curLon = location.getLongitude();

        if (Math.abs(curLat - lat) <= 0.0003 || Math.abs(curLon - lon) <= 0.0003){
            b.setText("Marked Attendance");
            b.setBackgroundColor(Color.GREEN);
        } else {
            b.setText("Don't LIE!!");
            b.setBackgroundColor(Color.RED);
        }

        try {

            Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled) {

                locationManager.removeUpdates(this);

            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
