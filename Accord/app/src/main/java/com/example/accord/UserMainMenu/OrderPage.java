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
import android.view.ViewDebug;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.accord.Auth.EmailAuth;
import com.example.accord.Firestore.BookingAPI;
import com.example.accord.Firestore.LocationService;
import com.example.accord.Firestore.UserAPI;
import com.example.accord.Models.CustomLatLng;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.Session;
import com.example.accord.Models.User;
import com.example.accord.OrderConfirmation;
import com.example.accord.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderPage extends FragmentActivity {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    LocationService locationService = new LocationService();
    CustomLatLng currentLocation = null;
    String uid = "";
    EmailAuth emailAuth = new EmailAuth();
    String category = "";
    boolean getLocationCounter = false;

    public void CenterOnMap(Location location, String title) {
        LatLng SelectedLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.clear();

        //mMap.addMarker(new MarkerOptions().position(SelectedLocation).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SelectedLocation, 15));

    }

    void getCategoryFromArgs() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            this.category = args.getString("category");
            TextView textView=findViewById(R.id.orderName);
            textView.setText(category);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        getCategoryFromArgs();
        getUserDetails();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.trackOrderMap2);
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
    void setTextView(int id,String text){
        EditText editText=findViewById(id);
        editText.setText(text);
    }
    UserAPI userAPI=new UserAPI();
    void updateAddressUI(User user){
        setTextView(R.id.name,user.getName());
        setTextView(R.id.address,user.getadd1());
        setTextView(R.id.number,user.getPhone());
        setTextView(R.id.inputArea,user.getarea());
        setTextView(R.id.inputZip,String.valueOf(user.getpincode()));


    }
    void getUserDetails(){
        FirebaseUser user=new EmailAuth().checkSignIn();
        if(user!=null){
            userAPI.getUser("user", user.getUid(), new UserAPI.UserTask() {
                @Override
                public void onSuccess(Object object) {
                    User user=(User)object;
                    updateAddressUI(user);
                }

                @Override
                public void onFailure(String msg) {

                }
            });
        }

    }
    void pushUserLocationOnOrder() {
        uid = emailAuth.checkSignIn().getUid();
        if (!getLocationCounter) {
            locationService.pushLocation("user", uid, currentLocation, new LocationService.LocationTask() {
                @Override
                public void onGetDistance(String value) {

                }

                @Override
                public void onGetServiceProvidersWithinDistance(List<ServiceProvider> serviceProviders) {

                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(getApplicationContext(), "Push Location Failed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(Object location) {
                    getLocationCounter = true;
                  //  Toast.makeText(getApplicationContext(), "Pushing Location", Toast.LENGTH_LONG).show();
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
                currentLocation = new CustomLatLng();
                currentLocation.latitude = location.getLatitude();
                currentLocation.longitude = location.getLongitude();

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
        EditText textView = (EditText)findViewById(R.id.Price);
        /*int price = Integer.parseInt(textView.getText().toString());
        price+=100;
        String string = String.valueOf(price);*/
        textView.setText("₹ 350");

    }

    public void Toggle2(View view) {

        CompoundButton button = findViewById(R.id.radioButton1);
        button.setChecked(false);
        EditText textView = (EditText)findViewById(R.id.Price);
        /*int price = Integer.parseInt(textView.getText().toString());
        price-=100;
        String string = String.valueOf(price);*/
        textView.setText("₹ 250");

    }

    BookingAPI bookingAPI = new BookingAPI();

    public void Confirmation(View view) {
        String uid=new EmailAuth().checkSignIn().getUid();
        bookingAPI.bookService(uid, category, true, new BookingAPI.onBooked() {
            @Override
            public void onBooked(Session session) {
                Toast.makeText(getApplicationContext(), "Searching", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), OrderConfirmation.class);
                intent.putExtra("sessionID",session.sessionID);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }

            @Override
            public void onBookingFailed() {
                Toast.makeText(getApplicationContext(), "Cant start a booking, please try again", Toast.LENGTH_LONG).show();
            }
        });

    }

}