package com.example.accord;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.airbnb.lottie.L;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.accord.Firestore.BookingAPI;
import com.example.accord.Firestore.UserAPI;
import com.example.accord.Models.CustomLatLng;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class TrackOrder extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, RoutingListener {

    //google map object
    private GoogleMap mMap;

    //current and destination location objects
    Location myLocation = null;
    Location destinationLocation = null;
    protected LatLng start = null;
    protected LatLng end = null;
    Session currentSession=new Session();
    //to get location permissions.
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;
    boolean initialLocation=false;
    //polyline object
    private List<Polyline> polylines = null;
    String serviceProviderID = "";
    UserAPI userAPI = new UserAPI();
    ServiceProvider serviceProvider = new ServiceProvider();
    String sessionID="";
    BookingAPI bookingAPI=new BookingAPI();
    void navigateToMainMenu(){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("type","user");
        startActivity(intent);
        finish();
    }
    void updateServiceProviderDetails(){
        TextView textView=findViewById(R.id.trackOrderServiceName);
        textView.setText(serviceProvider.getFirst_name());
        textView=findViewById(R.id.trackOrderServicePhone);
        textView.setText(serviceProvider.phone);
        final TextView finalTextView = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ finalTextView.getText()));
                startActivity(intent);
            }
        });
        Button button=findViewById(R.id.cancelOrderButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingAPI.cancelSession(sessionID, serviceProviderID,new BookingAPI.BookingTask() {
                    @Override
                    public void onSuccess(List<Session> sessions) {

                    }

                    @Override
                    public void onSuccess(Session session) {
                        
                    }

                    @Override
                    public void onSuccess() {
                       navigateToMainMenu();
                        Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailed(String msg) {
                        Toast.makeText(getApplicationContext(),"Cant cancel please try again",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    void onSessionOver(){
        navigateToMainMenu();
    }
    void getSession(){
        Bundle args = getIntent().getExtras();
        sessionID=args.getString("session");
        firebaseFirestore.collection("sessions").document(sessionID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error==null){
                    currentSession=value.toObject(Session.class);
                    if(currentSession!=null){
                        if(!currentSession.isActive){
                            if(currentSession.isCompleted){
                                Toast.makeText(getApplicationContext(),"Order Completed",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Order Canceled",Toast.LENGTH_LONG).show();

                            }


                            onSessionOver();
                        }
                    }
                }
                else{
                    try{
                        throw error;
                    }
                    catch (FirebaseFirestoreException firebaseFirestoreException){
                        onSessionOver();
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    void getServiceProviderLocation() {
        getSession();
        Bundle args = getIntent().getExtras();
        if (args != null) {
            serviceProviderID = args.getString("serviceProvider");

            userAPI.getUser("sp", serviceProviderID, new UserAPI.UserTask() {
                @Override
                public void onSuccess(Object object) {
                    serviceProvider = (ServiceProvider) object;
                    if (serviceProvider != null && serviceProvider.currentLocation != null) {
                        CustomLatLng customLatLng = serviceProvider.currentLocation;
                        end = new LatLng(customLatLng.getLatitude(), customLatLng.getLongitude());
                        Findroutes(start,end);
                        updateServiceProviderDetails();

                    }
                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        //request location permission.
        requestPermision();

        //init google map fragment to show map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.trackOrderMap2);
        mapFragment.getMapAsync(this);
    }

    private void requestPermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        } else {
            locationPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted.
                    locationPermission = true;
                    getMyLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    //to get user location
    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                myLocation = location;
                LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());
                start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                if(!initialLocation){
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                            ltlng, 16f);
                    mMap.animateCamera(cameraUpdate);
                    initialLocation=true;

                    getServiceProviderLocation();
                }




                //start route finding

            }
        });

        //get destination location when user click on map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getMyLocation();

    }


    // function to find Routes.
    public void Findroutes(LatLng Start, LatLng End) {

        if (Start == null || End == null) {
            //Toast.makeText(MainActivity.this,"Unable to get location",Toast.LENGTH_LONG).show();
        } else {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyAxTmixnyXR9YflWDcLQgcxspqyXEgyNWI")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }

    //Routing call back functions.
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
//        Findroutes(start,end);
    }

    @Override
    public void onRoutingStart() {
        //Toast.makeText(MainActivity.this,"Finding Route...",Toast.LENGTH_LONG).show();
    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

       CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if (polylines != null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i < route.size(); i++) {

            if (i == shortestRouteIndex) {
                polyOptions.color(getResources().getColor(R.color.colorPrimary));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng = polyline.getPoints().get(0);
                int k = polyline.getPoints().size();
                polylineEndLatLng = polyline.getPoints().get(k - 1);
                polylines.add(polyline);

            } else {

            }

        }




        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.tech));
        endMarker.position(polylineEndLatLng);
        endMarker.title(serviceProvider.first_name);
        mMap.addMarker(endMarker);
    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start, end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Findroutes(start, end);

    }
}







