package com.iastate.i_attend;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.iastate.i_attend.backend.iAttend.IAttend;

import org.json.JSONObject;

import java.io.IOException;

public class AddCourse extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap googleMap;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();
        new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Manfred"));
//        setOnClickListener();
    }

    public void setOnClickListener(){
        findViewById(R.id.createCourse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.courseName)).getText().toString();
                String number = ((EditText) findViewById(R.id.courseNumber)).getText().toString();
                if(!name.equals("") && !number.equals("")){
                    String url = "https://i-attend-1.appspot.com/_ah/api/";
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest
                            (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("Test ", "Works");
                                    Log.d("Response: ", response.toString());
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub

                                }
                            });
//                    mRequestQueue.add(jsObjRequest);
                }
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

        // Setting a click event handler for the map
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);

                String l = "Lat " + Double.toString(latLng.latitude) + " Lang " + Double.toString(latLng.longitude);
                ((EditText) findViewById(R.id.courseLocation)).setText(l);
            }
        });

        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }





    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private IAttend myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if(myApiService == null) {  // Only do this once
                IAttend.Builder builder = new IAttend.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://i-attend-1.appspot.com/_ah/api/");
//                IAttend.Builder builder = new IAttend.Builder(AndroidHttp.newCompatibleTransport(),
//                        new AndroidJsonFactory(), null)
//                        // options for running against local devappserver
//                        // - 10.0.2.2 is localhost's IP address in Android emulator
//                        // - turn off compression when running against local devappserver
//                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                            @Override
//                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                                abstractGoogleClientRequest.setDisableGZipContent(true);
//                            }
//                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;

            try {
//                return myApiService.sayHello().execute().getData();//getUser("rvshah@iastate.edu").execute().getKind();
                return myApiService.getUser("rvshah@iastate.edu").execute().getUserName();//get_course().execute().getCourseName();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }
}
