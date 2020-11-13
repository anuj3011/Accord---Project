package com.example.accord.MainMenu;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.accord.Auth.EmailAuth;
import com.example.accord.Firestore.LocationService;
import com.example.accord.Models.CustomLatLng;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;



public class MainMenuFragment extends Fragment implements OnMapReadyCallback{


    private MainMenuModel mainMenuModel;
    boolean User = false;
    boolean Service = false;
    String type;
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

    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mainMenuModel =
                ViewModelProviders.of(this).get(MainMenuModel.class);
        View root;
        Bundle args=getArguments();
        if(args!=null){
            type=args.getString("type");
            uid=args.getString("id");
            if(type.equals("user")){
                User=true;
                Service=false;
            }
            else if(type.equals("sp")){
                User=false;
                Service=true;
            }
        }
        if(User) {
             root = inflater.inflate(R.layout.fragment_aboutapp, container, false);
            return root;

        }else{
             root = inflater.inflate(R.layout.fragment_aboutapp_service, container, false);
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.trackOrderMap2);
            mapFragment.getMapAsync(this);

            return root;
        }

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
            locationService.pushLocation(type, uid, currentLocation, new LocationService.LocationTask() {
                @Override
                public void onGetDistance(String value) {

                }

                @Override
                public void onGetServiceProvidersWithinDistance(List<ServiceProvider> serviceProviders) {

                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(getActivity(),"Push Location Failed",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(Object location) {
                    getLocationCounter=true;
                    Toast.makeText(getActivity(),"Pushing Location",Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    void requestLocationUpdates() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else {
            requestLocationUpdates();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}