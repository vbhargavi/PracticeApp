package com.example.laxmibhargavivaditala.practiceapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;

import com.facebook.AccessToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Login-Activity. */
                Intent mainIntent;
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken == null) {
                    mainIntent = new Intent(SplashActivity.this, LoginActivity.class);

                } else {
                    //Home screen
                    mainIntent = new Intent(SplashActivity.this, HomeScreenActivity.class);
                }
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public class GetAuthTokenTask extends AsyncTaskLoader<Object> {
        public GetAuthTokenTask(Context context) {
            super(context);
        }

        @Override
        public Object loadInBackground() {
            return null;
        }

    }

}
