package com.example.laxmibhargavivaditala.practiceapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;

import com.example.laxmibhargavivaditala.practiceapp.model.OAuthResponse;
import com.example.laxmibhargavivaditala.practiceapp.service.ServiceManager;
import com.facebook.AccessToken;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<OAuthResponse> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportLoaderManager().initLoader(0,null,SplashActivity.this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new GetAuthTokenTask(SplashActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<OAuthResponse> loader, OAuthResponse data) {
        getSupportLoaderManager().destroyLoader(0);
        if (data != null && !TextUtils.isEmpty(data.getAccessToken())) {
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
        } else {
            finish();
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public static class  GetAuthTokenTask extends AsyncTaskLoader<OAuthResponse> {
        public GetAuthTokenTask(Context context) {
            super(context);
        }

        @Override
        public OAuthResponse loadInBackground() {
            try {
                return ServiceManager.getAuthToken();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

    }

}
