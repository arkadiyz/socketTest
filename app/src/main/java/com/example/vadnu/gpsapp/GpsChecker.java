package com.example.vadnu.gpsapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vadnu on 11/30/2018.
 */

public class GpsChecker implements LocationListener, Runnable {
    LocationManager locationManager;
    Context context;
    TextView txtview;
    public GpsChecker(LocationManager locationManager, Context context, TextView txtview) {
        this.locationManager = locationManager;
        this.context=context;
        this.txtview=txtview;
    }


    private void sendResult(Location location) {
        System.out.print(location);

    }



    @Override
    public void run() {


        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                sendResult(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

    }

    @Override
    public void onLocationChanged(Location location) {
//        Log.i("","Latitude is--> "+location.getLatitude());
//        Log.w("","Longtitude is--> "+location.getLongitude());
String x="Latitude is--> "+location.getLatitude();
String y="Longtitude is--> "+location.getLongitude();
        Toast.makeText(this.context,"LATITUDE="+x+" LONGTITUDE="+y, Toast.LENGTH_LONG).show();
        txtview.setText(x+"\n"+y);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
//        System.out.print(provider.toString());
        txtview.setText(provider.toString());

    }

    @Override
    public void onProviderEnabled(String provider) {
        txtview.setText(provider.toString());

    }

    @Override
    public void onProviderDisabled(String provider) {
        txtview.setText(provider.toString());

    }
}
