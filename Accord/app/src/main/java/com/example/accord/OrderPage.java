package com.example.accord;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderPage extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener{

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    private FusedLocationProviderClient fusedLocationClient;
    LatLng CurrentLocation;
    //private MapView mapView;

    public void CenterOnMap(Location location, String title) {
        LatLng SelectedLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(SelectedLocation).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SelectedLocation, 14));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location)
                                {
                                    if (location != null) {
                                        CenterOnMap(location,"Your Location");
                                        //CurrentLocation = new LatLng(18.9998489,72.8257724);
                                    }
                                }
                            });
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location)
                    {
                        if (location != null) {
                            CenterOnMap(location,"Your Location");
                            //CurrentLocation = new LatLng(18.9998489,72.8257724);
                        }
                    }
                });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setOnMapLongClickListener((GoogleMap.OnMapLongClickListener) this);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                if(location!=null)
                {
                    //LatLng UserLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    //CenterOnMap(location,"Selected Location");
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
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }

    @Override
    public void onMapLongClick(LatLng latLng)
    {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String Address="";
        try {
            List<android.location.Address> ListAddress = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if(ListAddress!=null && ListAddress.size()>0)
            {
                if(ListAddress.get(0).getSubLocality()!= null)
                {
                    if(ListAddress.get(0).getThoroughfare() != null)
                    {
                        Address = Address + ListAddress.get(0).getThoroughfare()+" ";
                    }
                    Address = Address + ListAddress.get(0).getSubLocality()+", ";
                    Address = Address + ListAddress.get(0).getLocality();
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        if(Address.equals(""))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");
            Address = Address + sdf.format(new Date());
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title(Address));
        Toast.makeText(this, "Location Saved!", Toast.LENGTH_SHORT).show();
    }

    public void Toggle1(View view){

        CompoundButton button = findViewById(R.id.radioButton2);
        button.setChecked(false);




    }
    public void Toggle2(View view){

        CompoundButton button = findViewById(R.id.radioButton1);
        button.setChecked(false);

    }

}