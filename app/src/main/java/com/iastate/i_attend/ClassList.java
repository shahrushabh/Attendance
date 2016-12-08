package com.iastate.i_attend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassList extends AppCompatActivity {

    private String username;
    private String type;
    private String email;
    private TextView textView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        username = i.getStringExtra("username");
        type = i.getStringExtra("type");
        email = i.getStringExtra("email");
        textView = (TextView) findViewById(R.id.userStatus);
        textView.setText(username + ": " + type);
        listView = (ListView) findViewById(R.id.listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClassList.this, AddCourse.class);
                startActivity(i);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: Update the action to lead the user to class detail (Attendance Activity)
                Context context = getApplicationContext();
                CharSequence text = "Clicked item " + i;
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();

                Intent intent = new Intent(ClassList.this, AttendanceActivity.class);
                intent.putExtra("courseID", i);
                intent.putExtra("username", username);
                intent.putExtra("type", type);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<String> values = new ArrayList<String>(Arrays.asList("CPRE 388", "CPRE 491"));

        // Set source of ListView to List of Events in database
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);
    }

}
