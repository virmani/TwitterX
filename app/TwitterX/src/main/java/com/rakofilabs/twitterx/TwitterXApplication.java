package com.rakofilabs.twitterx;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseTwitterUtils;

public class TwitterXApplication extends Application {
    @Override
    public void onCreate() {
        Parse.initialize(this,
                this.getRString(R.string.parse_application_id),
                this.getRString(R.string.parse_client_key));

        ParseTwitterUtils.initialize(
                this.getRString(R.string.twitter_consumer_key),
                this.getRString(R.string.twitter_consumer_secret));
    }

    private String getRString(int resourceId) {
        return this.getResources().getText(resourceId).toString();
    }
}
