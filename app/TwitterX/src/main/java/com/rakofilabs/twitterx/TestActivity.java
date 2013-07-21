package com.rakofilabs.twitterx;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import com.parse.ParseTwitterUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class TestActivity extends Activity {
    private static final String TAG = TestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpClient client = new DefaultHttpClient();
                HttpGet verifyGet = new HttpGet(
                        "https://api.twitter.com/1.1/statuses/home_timeline.json");
                ParseTwitterUtils.getTwitter().signRequest(verifyGet);
                try {
                    HttpResponse response = client.execute(verifyGet);
                    Log.d(TAG, "Received the following response: " + response.getStatusLine().getStatusCode());

                    String respStr = "FAILED!";
                    try {
                        respStr = EntityUtils.toString(response.getEntity());
                    } catch (IOException e) {
                        Log.wtf(TAG, "IOException while reading the response", e);
                    }

                    return respStr;
                } catch (IOException e) {
                    Log.wtf(TAG, "IOException while calling the HTTP", e);
                    return null;
                }
            }

            protected void onPostExecute(String response) {
                if (response != null) {
                    Log.d(TAG, "Response Body: " + response);
                } else {
                    Log.e(TAG, "null response received");
                }
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

}
