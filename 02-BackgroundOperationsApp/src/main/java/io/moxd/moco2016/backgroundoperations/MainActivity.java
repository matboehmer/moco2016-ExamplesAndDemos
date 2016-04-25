package io.moxd.moco2016.backgroundoperations;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Controller of a small demo on concurrency using threads and services on Android.
 *
 * @author Matthias Boehmer, matthias.boehmer@th-koeln.de
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * references to view objects
     */
    private IntentFilter mStatusIntentFilter;
    private BroadcastReceiver mBroadcastReceiver;
    private TextView textViewAsyncTaskReturn;
    private TextView textViewIntenServiceReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // get references to view objects
        textViewAsyncTaskReturn = (TextView) findViewById(R.id.textViewAsyncTaskReturn);
        textViewIntenServiceReturn = (TextView) findViewById(R.id.textViewIntenServiceReturn);

        // prepare listener mechanism for intent service via BroadcastReceiver
        mStatusIntentFilter = new IntentFilter(MyIntentService.ACTION_NO1_RESPONSE);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getStringExtra(MyIntentService.EXTRA_PARAM1_RESPONSE);
                textViewIntenServiceReturn.setText(data);
            }
        };

    }


    @Override
    protected void onResume() {
        super.onResume();

        // unregister a receiver for broadcast intents from background service
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, mStatusIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister the receiver for broadcast intents from background service
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * onClick listener for button
     *
     * @param v
     */
    public void clickButtonTestResponsiveness(View v) {
        Toast.makeText(MainActivity.this, "UI is still responsive on thread #" + Thread.currentThread().getId(), Toast.LENGTH_SHORT).show();
    }


    /**
     * onClick listener for button, defined in activity_main.xml
     *
     * @param view
     */
    public void clickButtonFireIntentService(View view) {
        Intent serviceRequest = new Intent(this, MyIntentService.class);
        serviceRequest.setAction(MyIntentService.ACTION_NO1);
        serviceRequest.putExtra(MyIntentService.EXTRA_PARAM1, "Paul on Thread #" + Thread.currentThread().getId());
        startService(serviceRequest);
    }

    /**
     * onClick listener for button, defined in activity_main.xml
     *
     * @param view
     */
    public void clickButtonAsyncTask(View view) {

        /**
         * creating a new async task for working on inpt data and return some results on that
         */
        AsyncTask<String, Integer, String> asyncTask = new AsyncTask<String, Integer, String>() {


            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                textViewAsyncTaskReturn.setText("progress: " + values[0]);
                Log.d(TAG, "AsyncTask progress is " + values[0]);
            }

            /**
             * implementation of doInBackground to run the background work
             * @param params
             * @return
             */
            @Override
            protected String doInBackground(String[] params) {

                for (int i = 0; i < 100; i++) {
                    try {
                        publishProgress(i);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return "Hello " + params[0] + " from Thread #" + Thread.currentThread().getId() + "!";
            }

            @Override
            protected void onPostExecute(String result) {
                textViewAsyncTaskReturn.setText("result of AsyncTask is: " + result);
            }
        };

        String param1 = "Paul on Thread #" + Thread.currentThread().getId();

        // run the async task
        asyncTask.execute(param1);
    }
}
