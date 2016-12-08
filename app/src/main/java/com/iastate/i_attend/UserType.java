package com.iastate.i_attend;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.iastate.i_attend.backend.myApi.MyApi;

import java.io.IOException;

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
        new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Manfred"));
//        Intent i = new Intent(this, ClassList.class);
//        i.putExtra("username", username);
//        i.putExtra("email", email);
//
//        if (view.getId() == R.id.instructor){
//            //Save user type as instructor to db
//            User u = dataSource.createUser(username, TYPE_INSTRUCTOR, email);
//            i.putExtra("type", TYPE_INSTRUCTOR);
//            Log.d("Click", TYPE_INSTRUCTOR);
//            Log.d("New user", u.getUserName() + " " + u.getUserType());
//            startActivity(i);
//
//        } else if (view.getId() == R.id.student){
//            //Save user type as student to db
//            User u = dataSource.createUser(username, TYPE_STUDENT, email);
//            i.putExtra("type", TYPE_STUDENT);
//            Log.d("Click", TYPE_STUDENT);
//            Log.d("New user", u.getUserName() + " " + u.getUserType());
//            startActivity(i);
//        }

    }




// TODO:: The following class needs to be removed later.

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private MyApi myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;

            try {
                return myApiService.sayHi(name).execute().getData();
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
