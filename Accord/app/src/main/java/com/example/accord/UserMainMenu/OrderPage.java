package com.example.accord.UserMainMenu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.accord.Auth.EmailAuth;
import com.example.accord.Firestore.LocationService;
import com.example.accord.Models.CustomLatLng;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.OrderConfirmation;
import com.example.accord.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderPage extends FragmentActivity {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    LocationService locationService = new LocationService();
    CustomLatLng currentLocation=null;
    String uid="";
    EmailAuth emailAuth=new EmailAuth();
    boolean getLocationCounter=false;
    public void CenterOnMap(Location location, String title) {
        LatLng SelectedLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.clear();

        //mMap.addMarker(new MarkerOptions().position(SelectedLocation).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SelectedLocation, 15));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
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

    void pushUserLocationOnOrder() {
        uid=emailAuth.checkSignIn().getUid();
        if(!getLocationCounter){
            locationService.pushLocation("user", uid, currentLocation, new LocationService.LocationTask() {
                @Override
                public void onGetDistance(String value) {

                }

                @Override
                public void onGetServiceProvidersWithinDistance(List<ServiceProvider> serviceProviders) {

                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(getApplicationContext(),"Push Location Failed",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(Object location) {
                    getLocationCounter=true;
                    Toast.makeText(getApplicationContext(),"Pushing Location",Toast.LENGTH_LONG).show();
                }
            });
        }

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

                pushUserLocationOnOrder();
                CenterOnMap(location, "Test");
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


    public void onMapLongClick(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String Address = "";
        try {
            List<android.location.Address> ListAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (ListAddress != null && ListAddress.size() > 0) {
                if (ListAddress.get(0).getSubLocality() != null) {
                    if (ListAddress.get(0).getThoroughfare() != null) {
                        Address = Address + ListAddress.get(0).getThoroughfare() + " ";
                    }
                    Address = Address + ListAddress.get(0).getSubLocality() + ", ";
                    Address = Address + ListAddress.get(0).getLocality();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Address.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");
            Address = Address + sdf.format(new Date());
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title(Address));
        Toast.makeText(this, "Location Saved!", Toast.LENGTH_SHORT).show();
    }

    public void Toggle1(View view) {

        CompoundButton button = findViewById(R.id.radioButton2);
        button.setChecked(false);


    }

    public void Toggle2(View view) {

        CompoundButton button = findViewById(R.id.radioButton1);
        button.setChecked(false);

    }

    public void Confirmation(View view){
        Intent intent = new Intent(getApplicationContext(), OrderConfirmation.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        finish();
    }

}