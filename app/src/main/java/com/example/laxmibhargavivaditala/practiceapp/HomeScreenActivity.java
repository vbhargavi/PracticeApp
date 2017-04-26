package com.example.laxmibhargavivaditala.practiceapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.laxmibhargavivaditala.practiceapp.model.MyLocation;
import com.example.laxmibhargavivaditala.practiceapp.model.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by laxmibhargavivaditala on 4/23/17.
 */

public class HomeScreenActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = HomeScreenActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST = 100;
    private static final int SETTINGS_REQUEST = 101;

    private GoogleApiClient mGoogleApiClient;
    private Fragment homeScreenFragment;
    private ProgressBar progressbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_layout);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        getFacebookUserData();
    }

    public void getFacebookUserData() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.d(TAG, "FAcebook User Data: " + object);

                        try {
                            User user = User.createUser(object.getString("id"), object.getString("first_name"), object.getString("last_name"));
                            JSONObject locationObject = object.getJSONObject("location");
                            if (locationObject != null) {
                                String locationString = locationObject.getString("name");
                                String[] cityStateArr = locationString.split(",");
                                user.setCity(cityStateArr[0]);
                                user.setState(cityStateArr[1].substring(1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        connectToGoogleServices();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,location");
        request.setParameters(parameters);
        request.executeAsync();

    }

    public void connectToGoogleServices() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        } else {
            checkPermission();
        }
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (checkPermission()) {
            //Check settings and get location
            checkSettingsAndGetLocation();
        } else {
            showLocationPermissionDialog();
        }
    }

    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
    )
    @SuppressWarnings("MissingPermission")
    private void checkSettingsAndGetLocation() {
        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
//Resolution Not required
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                MyLocation.createLocation(location.getLatitude(), location.getLongitude());
                                addHomeFragment();
                                Log.d(TAG, "Received Location" + location);
                            }
                        });
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
// Location settings are not satisfied, but this can be fixed
// by showing the user a dialog.
                        try {
                            status.startResolutionForResult(HomeScreenActivity.this, SETTINGS_REQUEST);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
// Location settings are not satisfied. However, we have no way
// to fix the settings so we won't show the dialog.

                        break;
                }
            }
        });
    }

    public boolean checkPermission() {
        return !(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);

    }

    public void showLocationPermissionDialog() {
        ActivityCompat.requestPermissions(HomeScreenActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST);
    }


    @SuppressWarnings("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //check settigns and get location
                checkSettingsAndGetLocation();
            } else {
                onLocationError();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        onLocationError();
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST) {
            if (resultCode == RESULT_OK) {
                checkSettingsAndGetLocation();
            } else {
                onLocationError();
            }
        }
    }

    private void onLocationError() {
        if (User.getUser() != null && !TextUtils.isEmpty(User.getUser().getCity())) {
            MyLocation.createLocation(User.getUser().getCity(), User.getUser().getState());
            addHomeFragment();

        } else {
            showErrorDialog();
        }
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(HomeScreenActivity.this)
                .setTitle("Location Error")
                .setMessage("Location is required")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                }).show();

    }

    private void addHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        homeScreenFragment = new HomeScreenFragment();
        fragmentTransaction.add(R.id.fragment_container, homeScreenFragment);
        fragmentTransaction.commit();
        progressbar.setVisibility(View.GONE);

    }
}
