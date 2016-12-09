package com.iastate.i_attend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AttendanceActivity extends AppCompatActivity {

    int courseID;
    String type;
    TextView courseName, count;
    SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    boolean gpsAllowed = SP.getBoolean("allowGpsUsage",false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        courseID = i.getIntExtra("courseID", -1);
        type = i.getStringExtra("type");

        Log.d("GPS Usage Allowance", Boolean.toString(gpsAllowed));

        if (type.equals(UserType.TYPE_INSTRUCTOR)) {
            setContentView(R.layout.activity_attendance);
            courseName = (TextView) findViewById(R.id.courseName);
            count = (TextView) findViewById(R.id.count);

            findViewById(R.id.attd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: 1. Change Button Text
                    Button b = (Button) view;
                    String text = b.getText().toString();
                    Log.d("Button Text", text);
                    if (b.getText().toString().equals("Start")) {
                        b.setText("Stop");
                    } else if (b.getText().toString().equals("Stop")) {
                        b.setText("Start");
                    }

                    //TODO: 2. Start Timer

                }
            });
        } else if(type.equals(UserType.TYPE_STUDENT)){
            setContentView(R.layout.activity_attendance_student);

            findViewById(R.id.check).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Button b = (Button) view;
                    b.setText("Checked");
                }
            });
        }

    }
}
