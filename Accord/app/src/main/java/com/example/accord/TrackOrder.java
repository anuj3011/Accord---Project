package com.example.accord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.accord.Firestore.LocationService;
import com.example.accord.Models.CustomLatLng;
import com.example.accord.Models.ServiceProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class TrackOrder extends AppCompatActivity {
    GoogleMap mMap;
    LocationManager locationManager;
    CustomLatLng currentLocation;
    CustomLatLng serviceProviderLocation;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted
            Log.d("location", "permission granted");
            requestLocationUpdates();

        } else {
            // toast need permission for location
        }


    }
    void centerOnCurrentLocation(){
        LatLng SelectedLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.clear();

        //mMap.addMarker(new MarkerOptions().position(SelectedLocation).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SelectedLocation, 15));
    }
    void requestLocationUpdates() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        Log.d("location", "getting location");
        mMap.setMyLocationEnabled(true);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d("location", location.toString());
                currentLocation=new CustomLatLng();
                currentLocation.latitude=location.getLatitude();
                currentLocation.longitude=location.getLongitude();
                centerOnCurrentLocation();
                showRoute();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("location", provider + status);
            }
        });
    }

    void checkPermissionsForLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else {
            requestLocationUpdates();
        }

    }
    void showRoute(){
   //     LatLng serviceProviderLocation=new LatLng(this.serviceProviderLocation.getLatitude(),this.serviceProviderLocation.getLongitude());
        LatLng currentLocation = new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude());
        LatLng serviceProviderLocation=new LatLng(currentLocation.latitude+10,currentLocation.longitude+10);


    }
    void getServiceProviderLocation(String serviceProviderID){
        LocationService locationService=new LocationService();
        locationService.getLocationOfActiveUser("sp", serviceProviderID, new LocationService.LocationTask() {
            @Override
            public void onGetDistance(String value) {

            }

            @Override
            public void onGetServiceProvidersWithinDistance(List<ServiceProvider> serviceProviders) {

            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onSuccess(Object location) {
                    serviceProviderLocation=(CustomLatLng) location;
                    showRoute();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.trackOrderMap);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                Log.d("location", "checking location");
                checkPermissionsForLocation();
            }
        });
    }
}