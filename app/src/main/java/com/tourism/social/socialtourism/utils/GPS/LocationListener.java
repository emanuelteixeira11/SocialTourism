package com.tourism.social.socialtourism.utils.GPS;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by emanuelteixeira on 15/03/15.
 */
public class LocationListener implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private Activity activity;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    final static int INTERVAL = 1000;
    final static int FASTEST_INTERVAL = 2000;
    private Location lastLocation;

    public LocationListener(Activity activity) {
        this.activity = activity;
        this.buildGoogleApiClient();
        //this.createLocationRequest();
        this.mGoogleApiClient.connect();

    }

    public void createLocationRequest() {
        this.mLocationRequest = new LocationRequest();
        this.mLocationRequest.setInterval(INTERVAL);
        this.mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        this.mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this.activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(this.activity.getBaseContext(), mLastLocation.getLatitude() + "", Toast.LENGTH_LONG).show();
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        this.lastLocation = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (ConnectionResult.SERVICE_MISSING == connectionResult.getErrorCode())
        {
            Toast.makeText(this.activity, "Not Work", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onLocationChanged(Location location) {}

    public Location getLastLocation() {
        return this.lastLocation;
    }
}
