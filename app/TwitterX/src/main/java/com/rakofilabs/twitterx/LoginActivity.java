package com.rakofilabs.twitterx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

public class LoginActivity extends Activity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseAnalytics.trackAppOpened(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null && currentUser.isAuthenticated()) {
            Log.d(TAG, "Logged in without the dialog! User: " + ParseTwitterUtils.getTwitter().getScreenName());
        } else {
            ParseTwitterUtils.logIn(LoginActivity.this, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException err) {
                    if (user == null) {
                        Log.d(TAG, "Uh oh. The user cancelled the Twitter login.");
                    } else {
                        if (user.isNew()) {
                            Log.d(TAG, "User signed up and logged in through Twitter!");
                        } else {
                            Log.d(TAG, "User logged in through Twitter!");
                        }
                    }
                }
            });
        }

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            LoginActivity.this.startActivity(new Intent(LoginActivity.this, TestActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

}
