package com.parrot.freeflight.milTools;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ibm.util.CoordinateConversion;

/**
 * Created by ronin on 4/2/16.
 */
public class DroneGps {
    LocationManager locationManager;
    Context mContext;
    double lat;
    double lng;
    public DroneGps(Context mContext){
        lat=0.0;
        lng =0.0;
        this.mContext = mContext;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        ContentResolver contentResolver = mContext.getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);


        if (gpsStatus) {
            LocationListener locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }

    }
    public String getEightGrid(){
        return eightGrid(lat, lng);
    }

    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            lat = loc.getLatitude();
            lng = loc.getLongitude();
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
    public static String eightGrid(double lat, double lon) {
        CoordinateConversion c = new CoordinateConversion();

        String fullgrid = c.latLon2MGRUTM(lat, lon);
        String easting = fullgrid.substring(5, 9);
        String northing = fullgrid.substring(10, 14);
        return easting + " " + northing;
    }

}
