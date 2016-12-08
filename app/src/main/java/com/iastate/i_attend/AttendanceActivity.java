package com.iastate.i_attend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AttendanceActivity extends AppCompatActivity implements View.OnClickListener {

    int courseID;
    TextView courseName, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Intent i = getIntent();
        courseID = i.getIntExtra("courseID", -1);
        courseName = (TextView) findViewById(R.id.courseName);
        count = (TextView) findViewById(R.id.count);

        findViewById(R.id.attd).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        //TODO: 1. Change Button Text
        Button b = (Button) view;
        String text = b.getText().toString();
        Log.d("Button Text", text);
        if(b.getText().toString().equals("Start")){
            b.setText("Stop");
        } else if (b.getText().toString().equals("Stop")){
            b.setText("Start");
        }


        //TODO: 2. Start Timer

    }
}
