package io.moxd.moco2016.mvcshoppinglist.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.moxd.moco2016.mvcshoppinglist.R;

public class ThreadsActivity extends AppCompatActivity {

    private static final String TAG = ThreadsActivity.class.getSimpleName();

    private Button buttonLRO;
    private Button buttonJustAButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        buttonLRO = (Button) findViewById(R.id.buttonLongRunningOperation);
        buttonLRO.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        simulateLongRunningOperation();
                    }
                }
        );

        buttonJustAButton = (Button) findViewById(R.id.buttonJustAButton);
        buttonJustAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThreadsActivity.this, "i am active", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * simulate something that takes some time
     */
    private void simulateLongRunningOperation() {
        try {
            for (int i = 1; i < 20; i++) {
                Log.d(TAG, "running long in loop no. " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void click_buttonDemoIntentService(View v) {

    }

    public void click_buttonDemoAsyncTask(View v) {

        AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... params) {
                simulateLongRunningOperation();
                return "Input has " + params[0].length() + " characters";
            }

        };

        asyncTask.execute("input for task");


    }

}
