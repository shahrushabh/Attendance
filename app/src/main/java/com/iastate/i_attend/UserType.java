package com.iastate.i_attend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class UserType extends AppCompatActivity implements View.OnClickListener {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type_chooser);

        Intent i = getIntent();
        username = i.getStringExtra("username");
        Log.d("Username: ", username);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.instructor){
            //Save user type as instructor to db


        } else if (view.getId() == R.id.student){
            //Save user type as student to db
        }
    }
}
