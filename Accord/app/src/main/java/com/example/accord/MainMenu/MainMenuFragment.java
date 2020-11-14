package com.example.accord.MainMenu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import com.example.accord.Firestore.BookingAPI;
import com.example.accord.Firestore.LocationService;
import com.example.accord.Firestore.UserAPI;
import com.example.accord.IntroActivity;
import com.example.accord.Models.CustomLatLng;
import com.example.accord.Models.NGO;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.Session;
import com.example.accord.Models.User;
import com.example.accord.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Objects;
import java.util.Random;


public class MainMenuFragment extends Fragment implements OnMapReadyCallback {


    private MainMenuModel mainMenuModel;
    boolean User = false;
    boolean Service = false;
    String type;
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    LocationService locationService = new LocationService();
    BookingAPI bookingAPI = new BookingAPI();
    CustomLatLng currentLocation = null;
    String uid = "";
    EmailAuth emailAuth = new EmailAuth();
    UserAPI firestoreAPI = new UserAPI();
    User user = new User();
    ServiceProvider serviceProvider = new ServiceProvider();
    NGO ngo = new NGO();
    boolean getLocationCounter = false;
    List<Session> sessions;

    public void CenterOnMap(CustomLatLng location, String title) {
        LatLng SelectedLocation = new LatLng(location.getLatitude(), location.getLongitude());


        mMap.addMarker(new MarkerOptions().position(SelectedLocation).title(title));
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()), 15));

    }

    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        return fragment;
    }

    View setupUserMainMenu(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_aboutapp, container, false);
        return root;
    }

    void getUserProfile() {
        uid = emailAuth.checkSignIn().getUid();
        if (uid == null || uid.length() < 1) {

            emailAuth.logout();
            Toast.makeText(getContext(), "Logging out", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getContext(), IntroActivity.class));

        }
        firestoreAPI.getUser(type, uid, new UserAPI.UserTask() {
            @Override
            public void onSuccess(Object object) {
                if (type.equals("user")) {
                    user = (com.example.accord.Models.User) object;
                    serviceProvider = null;

                } else if (type.equals("sp")) {
                    serviceProvider = (ServiceProvider) object;

                    user = null;
                    getOpenSessions();

                } else {
                    ngo = (NGO) object;

                }


            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }
    void dummyOpenSessionMarkers(){
        try{
            for(int i=0;i<5;i++){
                CustomLatLng customLatLng=new CustomLatLng();
                Random random=new Random();
                int rand=random.nextInt(6);
                boolean rbool=random.nextBoolean();

                double offset1=rand/1000.0;
                rand=random.nextInt(5);

                double offset2=rand/1000.0;
                if(rbool){
                    offset1=offset1*-1;
                    offset2=offset2*-1;
                }
                customLatLng.latitude=currentLocation.latitude+offset1;
                customLatLng.longitude=currentLocation.longitude+offset2;
                CenterOnMap(customLatLng,String.valueOf(i));
            }
        }
        catch (Exception e){

        }

    }
    void addOpenSessionsMarkers(){
       for(int i=0;i<sessions.size();i++){
           Session session=sessions.get(i);
           User openUser=new User();
           firestoreAPI.getUser("user", session.userID, new UserAPI.UserTask() {
               @Override
               public void onSuccess(Object object) {
                   user=(User) object;

               //    CenterOnMap(user.currentLocation,user.getName());
               }

               @Override
               public void onFailure(String msg) {

               }
           });
       }
    }
    void getOpenSessions() {

        bookingAPI.getOpenSessionsForProviders(serviceProvider, new BookingAPI.BookingTask() {
            @Override
            public void onSuccess(List<Session> openSessions) {
                //place open session markers
                sessions=openSessions;
                if(sessions!=null){
                   // addOpenSessionsMarkers();
                }
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed(String msg) {

            }
        });
    }

    View setupServiceMainMenu(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_aboutapp_service, container, false);
        getOpenSessions();
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.trackOrderMap2);
        mapFragment.getMapAsync(this);

        return root;
    }

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mainMenuModel =
                ViewModelProviders.of(this).get(MainMenuModel.class);

        Bundle args = getArguments();
        if (args != null) {
            type = args.getString("type");
            uid = args.getString("id");
            if (type.equals("user")) {
                User = true;
                Service = false;
            } else if (type.equals("sp")) {
                User = false;
                Service = true;
            }
        }
        checkPermissionsForLocation();
        getUserProfile();
        if (User) {
            return setupUserMainMenu(inflater, container, savedInstanceState);

        } else {
            return setupServiceMainMenu(inflater, container, savedInstanceState);
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
        uid = emailAuth.checkSignIn().getUid();
        if (!getLocationCounter) {
            locationService.pushLocation(type, uid, currentLocation, new LocationService.LocationTask() {
                @Override
                public void onGetDistance(String value) {

                }

                @Override
                public void onGetServiceProvidersWithinDistance(List<ServiceProvider> serviceProviders) {

                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(getActivity(), "Push Location Failed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(Object location) {
                    getLocationCounter = true;
                    Toast.makeText(getActivity(), "Pushing Location", Toast.LENGTH_LONG).show();
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

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d("location", location.toString());
                currentLocation = new CustomLatLng();
                currentLocation.latitude = location.getLatitude();
                currentLocation.longitude = location.getLongitude();
                dummyOpenSessionMarkers();
                pushUserLocationOnOrder();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("location", provider + status);
            }
        });
    }

    void checkPermissionsForLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else {
            requestLocationUpdates();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
       // dummyOpenSessionMarkers();
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}