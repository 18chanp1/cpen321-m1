package com.beaker.cpen321tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PhoneDetailsActivity extends AppCompatActivity implements LocationListener {
    private TextView currentLocationText;
    private TextView phoneModelText;
    private TextView manufText;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_details);

        currentLocationText = findViewById(R.id.LocationLabel);
        phoneModelText = findViewById(R.id.model_label);
        manufText = findViewById(R.id.manu_label);

        //get information about manufacturers

        phoneModelText.setText("Model: " + Build.MODEL);
        manufText.setText("Manufacturer: " + Build.MANUFACTURER);

        //check location permissions
        //checkLocationPermissions();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        }
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 500, 0, this);

    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int [] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int i : grantResults)
        {
            if(i != PackageManager.PERMISSION_GRANTED)
            {
                currentLocationText.setText("Location: You did not allow permissions. Lame");
                return;
            }
        }

        //suppressed, since we know that permissions were granted.
        onLocationChanged(locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER));

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocationText.setText("City: " + locateCity(location.getLatitude(), location.getLongitude()));

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void checkLocationPermissions()
    {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(PhoneDetailsActivity.this, "Permissions Grangted!", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Toast.makeText(PhoneDetailsActivity.this, "We need these location permissions to run!", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setTitle("Need location permissions")
                        .setMessage("Need location permissions to mark location on map")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(PhoneDetailsActivity.this, "We need these permissions to run!", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(PhoneDetailsActivity.this, new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                            }
                        })
                        .create()
                        .show();
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }


    }

    private String locateCity(double lat, double lon)
    {
        String cityname = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 10);
            if(addresses.size() > 0)
            {
                for(Address adr: addresses)
                {
                    if(adr.getLocality() != null && adr.getLocality().length() > 0)
                    {
                        cityname = adr.getLocality();
                        break;
                    }
                }
            }

        } catch (IOException e) {
            return "";
        }

        return cityname;
    }
}