package com.iastate.i_attend;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddCourse extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap googleMap;
    private MarkerOptions markerOptions;
    private String name;
    private LatLng givenLatlng;
    private boolean extraSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        givenLatlng = new LatLng(intent.getDoubleExtra("latitude",42.02821),intent.getDoubleExtra("longitude",-93.64892));
        ((EditText) findViewById(R.id.courseName)).setText(name);


        (findViewById(R.id.complete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setMinZoomPreference(15.0f);
        googleMap.setMaxZoomPreference(20.0f);
        // Creating a marker
        markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(givenLatlng);

        Geocoder geocoder = new Geocoder(AddCourse.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(givenLatlng.latitude, givenLatlng.longitude, 1);
            Address obj = addresses.get(0);
            String address = obj.getAddressLine(0);
            markerOptions.title(address);
            ((EditText) findViewById(R.id.courseLocation)).setText(address);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            markerOptions.title(givenLatlng.latitude + " : " + givenLatlng.longitude);
            String l = "Lat " + Double.toString(givenLatlng.latitude) + " Lang " + Double.toString(givenLatlng.longitude);
            ((EditText) findViewById(R.id.courseLocation)).setText(l);
            Log.e("Error ", e.toString());
        }

        // Clears the previously touched position
        googleMap.clear();

        // Animating to the touched position
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(givenLatlng));

        // Placing a marker on the touched position
        googleMap.addMarker(markerOptions);

        // Setting a click event handler for the map
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                Geocoder geocoder = new Geocoder(AddCourse.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    Address obj = addresses.get(0);
                    String address = obj.getAddressLine(0);
                    markerOptions.title(address);
                    ((EditText) findViewById(R.id.courseLocation)).setText(address);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                    String l = "Lat " + Double.toString(latLng.latitude) + " Lang " + Double.toString(latLng.longitude);
                    ((EditText) findViewById(R.id.courseLocation)).setText(l);
                    Log.e("Error ", e.toString());
                }

                // Clears the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
            }
        });

        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void finish(){
        if(markerOptions != null && !((EditText) findViewById(R.id.courseName)).getText().toString().equals("")){
            Intent i = new Intent();
            i.putExtra("courseName", ((EditText) findViewById(R.id.courseName)).getText().toString());
            i.putExtra("latitude", markerOptions.getPosition().latitude);
            i.putExtra("longitude", markerOptions.getPosition().longitude);
            setResult(RESULT_OK, i);
        }
        super.finish();
    }
}