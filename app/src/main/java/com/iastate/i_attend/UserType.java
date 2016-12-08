package com.iastate.i_attend;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;

public class UserType extends AppCompatActivity implements View.OnClickListener {

    public static final String TYPE_INSTRUCTOR = "instructor";
    public static final String TYPE_STUDENT = "student";

    String username;
    String email;
    private UsersDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type_chooser);

        dataSource = UsersDataSource.getDsInstance(this);
        dataSource.open();

        Intent i = getIntent();
        username = i.getStringExtra("username");
        email = i.getStringExtra("email");
        Log.d("Username", username);

        findViewById(R.id.instructor).setOnClickListener(this);
        findViewById(R.id.student).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, ClassList.class);
        i.putExtra("username", username);
        i.putExtra("email", email);

        if (view.getId() == R.id.instructor){
            //Save user type as instructor to db
            User u = dataSource.createUser(username, TYPE_INSTRUCTOR, email);
            i.putExtra("type", TYPE_INSTRUCTOR);
            Log.d("Click", TYPE_INSTRUCTOR);
            Log.d("New user", u.getUserName() + " " + u.getUserType());
            startActivity(i);

        } else if (view.getId() == R.id.student){
            //Save user type as student to db
            User u = dataSource.createUser(username, TYPE_STUDENT, email);
            i.putExtra("type", TYPE_STUDENT);
            Log.d("Click", TYPE_STUDENT);
            Log.d("New user", u.getUserName() + " " + u.getUserType());
            startActivity(i);
        }

    }
}
