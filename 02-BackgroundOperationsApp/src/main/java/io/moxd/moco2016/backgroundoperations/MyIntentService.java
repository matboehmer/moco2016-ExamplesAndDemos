package io.moxd.moco2016.backgroundoperations;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Random;

/**
 * This class implements a service component as an intent service, i.e. work requests can be send through intents. Other
 * components will be notified through broadcast intents if work was done. This service is also declared in the Android
 * manifest file.
 *
 * @author Matthias Boehmer, matthias.boehmer@th-koeln.de
 */
public class MyIntentService extends IntentService {

    /**
     * constant strings for definition of intent actions
     */
    public static final String ACTION_NO1 = "actionNo1";
    public static final String ACTION_NO1_RESPONSE = "actionNo1response";

    /**
     * constant strings for definition of intent extras
     */
    public static final String EXTRA_PARAM1 = "extraParam1";
    public static final String EXTRA_PARAM1_RESPONSE = "extraParam1Response";

    // TAG for logging
    private static final String TAG = MyIntentService.class.getSimpleName();

    /**
     * service constructor, to be used by framework
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * callback handler for sent intents
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NO1.equals(action)) {

                // get input for our work from intent extras
                String listUrl = intent.getStringExtra(EXTRA_PARAM1);

                // invoke method for background operations
                String result = handleActionDownloadList(listUrl);

                // notify components interested in results of this action
                Intent serviceRequestResult = new Intent(ACTION_NO1_RESPONSE);
                serviceRequestResult.putExtra(EXTRA_PARAM1_RESPONSE, result);
                LocalBroadcastManager.getInstance(this).sendBroadcast(serviceRequestResult);

            }
        }
    }

    /**
     * method for conducting all our background work
     *
     * @param param1
     */
    private String handleActionDownloadList(String param1) {
        Log.d(TAG, "started handleActionDownloadList on thread #" + Thread.currentThread().getId());

        int randomData = new Random().nextInt(1000);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "done handleActionDownloadList on thread #" + Thread.currentThread().getId());

        return "Hello " + param1 + " from thread #" + Thread.currentThread().getId();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart");
    }

}