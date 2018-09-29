package com.example.user.locationtracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }

            //check if network is working or not
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get location
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    //latitude = 18.5;
                    //longitude = 73.8;


                    Geocoder geocoder = new Geocoder(getApplicationContext());      //instantiate the class Geocoder
                    try
                    {
                        LatLng latLng = new LatLng(latitude,longitude);     //instantiate the class LatLng
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String s = addressList.get(0).getSubLocality()+",";
                        s += addressList.get(0).getLocality()+",";
                        s += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(s));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18.2f));

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(getApplicationContext(),"Turn on Internet Service", Toast.LENGTH_LONG).show();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get location
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    //latitude = 18.5;
                    //longitude = 73.8;

                   // LatLng latLng = new LatLng(latitude,longitude);     //instantiate the class LatLng
                    Geocoder geocoder = new Geocoder(getApplicationContext());      //instantiate the class Geocoder
                    try
                    {
                        LatLng latLng = new LatLng(latitude,longitude);     //instantiate the class LatLng
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String s = addressList.get(0).getSubLocality()+",";
                        s += addressList.get(0).getLocality()+",";
                        s += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(s));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18.2f));

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            } );
        }
        else {
            Toast.makeText(getApplicationContext(),"Turn on Internet and Location Services", Toast.LENGTH_LONG).show();

        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*Location location = new Location(String.valueOf(googleMap));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng pune = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(pune).title("Marker in Pune"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pune,18.2f));*/
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
