package com.tourism.social.socialtourism.utils.GPS;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by emanuelteixeira on 02/03/15.
 */
public class GPSManager implements LocationListener{
    private double MY_LAT = 0.0;
    private double MY_LNG = 0.0;
    boolean gpsEnabled = false;
    boolean wifiEnabled = false;

    private String provider;

    public LocationManager getLocationManager() {
        return locationManager;
    }

    private LocationManager locationManager;

    public GPSManager(LocationManager locationManager) {
       this.locationManager = locationManager;
       this.gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
       this.wifiEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public double getMY_LNG() {
        return this.MY_LNG;
    }

    public void setMY_LNG(double MY_LNG) {
        this.MY_LNG = MY_LNG;
    }

    public double getMY_LAT() {
        return this.MY_LAT;
    }

    public void setMY_LAT(double MY_LAT) {
        this.MY_LAT = MY_LAT;
    }

    public boolean isGpsEnabled() {
        return gpsEnabled;
    }

    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.setMY_LAT(location.getLatitude());
        this.setMY_LNG(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        if (s.equals("gps"))
            gpsEnabled = true;
        else if (s.equals("network"))
            wifiEnabled = true;
        run();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (s.equals("gps"))
            gpsEnabled = false;
        else if (s.equals("network"))
            wifiEnabled = false;
        run();
    }

    public String getProvider() {
        return provider;
    }

    public void updates(){
        getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
        getLocationManager().requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1, this);
    }

    public void run() {
        Criteria criteria = new Criteria();
        this.provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        this.setMY_LAT(location.getLatitude());
        this.setMY_LNG(location.getLongitude());
        updates();
    }
}
