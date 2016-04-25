package io.moxd.moco2016.backgroundoperations;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadDataActivity extends AppCompatActivity {


    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_data);

        imageView = (ImageView) findViewById(R.id.imageView);
    }


    /**
     * click listener for download button
     */
    public void clickButtonDownloadImage(View view) {
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bmp = null;
                try {
                    URL url = new URL(params[0]);
                    URLConnection urlConnection = url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    bmp = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        };

        asyncTask.execute("http://ktdss02.inf.fh-koeln.de/webcam/bilder/copy1.jpg");
    }
}
