package com.iastate.i_attend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ClassList extends AppCompatActivity {

    private String username;
    private String type;
    private String email;
    private TextView textView;
    private ListView listView;
    private Toolbar toolbar;
    private EditText searchClass;
    ArrayList<String> values = new ArrayList<String>(Arrays.asList("CPRE 388", "CPRE 491", "EE 230", "COMS 550", "ABC 120", "LIB 160", "CPRE 288"));
    ArrayList<String> filtered = values;
    HashMap<String,LatLng> latlngs = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        Intent i = getIntent();
        username = i.getStringExtra("username");
        type = i.getStringExtra("type");
        email = i.getStringExtra("email");
        textView = (TextView) findViewById(R.id.userStatus);
        textView.setText(username + ": " + type);
        listView = (ListView) findViewById(R.id.listView);
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        searchClass = (EditText) findViewById(R.id.searchClass);
        // Library.
        latlngs.put("ABC 120", new LatLng(42.028129028547276,-93.64882349967957));
        latlngs.put("LIB 160", new LatLng(42.028129028547276,-93.64882349967957));

        // Coover.
        latlngs.put("CPRE 388", new LatLng(42.028415931827865,-93.65097999572754));

        // State Gym.
        latlngs.put("EE 230", new LatLng(42.02466217822298,-93.65397334098816));
        latlngs.put("COMS 550", new LatLng(42.02466217822298,-93.65397334098816));

        // Catt Hall.
        latlngs.put("CPRE 491", new LatLng(42.02789791107405,-93.64576578140259));

        //Durham Hall.
        latlngs.put("CPRE 288", new LatLng(42.027493, -93.649696));

        // Filter classes here.
        searchClass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                update_list(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: Update the action to lead the user to class detail (Attendance Activity)
                Context context = getApplicationContext();
                CharSequence text = filtered.get(i);
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();

                Intent intent = new Intent(ClassList.this, AttendanceActivity.class);
                intent.putExtra("courseID", i);
                intent.putExtra("username", username);
                intent.putExtra("type", type);
                intent.putExtra("email", email);
                intent.putExtra("latitude",latlngs.get(text).latitude);
                intent.putExtra("longitude",latlngs.get(text).longitude);
                Log.d("Lat is ", Double.toString(latlngs.get(text).latitude));
                Log.d("Lng is ", Double.toString(latlngs.get(text).longitude));
                startActivity(intent);
            }
        });
    }

    private void update_list(CharSequence charSequence){
        String filter = charSequence.toString();
        filtered = new ArrayList<>();
        for(String course: values){
            if(course.toLowerCase().contains(filter.toLowerCase())){
                filtered.add(course);
            }
        }
        if(filtered.size() == 0){
            (findViewById(R.id.no_item_error)).setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }else{
            // Set source of ListView to List of Events in database
            ArrayAdapter<String> filteredAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, filtered);
            listView.setAdapter(filteredAdapter);
            listView.setVisibility(View.VISIBLE);
            (findViewById(R.id.no_item_error)).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                update_list(((EditText) findViewById(R.id.searchClass)).getText());
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set source of ListView to List of Events in database
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);
    }

}
