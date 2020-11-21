package com.example.accord.MainMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.accord.Auth.EmailAuth;
import com.example.accord.Firestore.BookingAPI;
import com.example.accord.Firestore.LocationService;
import com.example.accord.Firestore.UserAPI;
import com.example.accord.MainActivity;
import com.example.accord.Models.CustomLatLng;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.User;
import com.example.accord.Models.Session;
import com.example.accord.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

public class CurrentServiceSession extends AppCompatActivity implements OnMapReadyCallback, RoutingListener {
    Session session = new Session();

    User user = new User();
    boolean locationPermission = false;
    FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    void getSession() {
        Bundle args = getIntent().getExtras();
        if (args != null) {

            session.sessionID = args.getString("sessionID");
            firebaseFirestore.collection("sessions").document(session.sessionID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error==null){
                        session=value.toObject(Session.class);
                        getUserLocation();
                    }
                }
            });
        }
    }

    //google map object
    private GoogleMap mMap;

    //current and destination location objects
    Location myLocation = null;
    Location destinationLocation = null;
    protected LatLng start = null;
    protected LatLng end = null;

    //to get location permissions.
    private final static int LOCATION_REQUEST_CODE = 23;

    boolean initialLocation = false;
    //polyline object
    private List<Polyline> polylines = null;
    String userID = "";
    UserAPI userAPI = new UserAPI();

    String sessionID = "";
    BookingAPI bookingAPI = new BookingAPI();
    void navigateToMainMenu(){
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("type","sp");
        startActivity(intent);
        finish();
    }
    void updateUserDetails() {
        Button complete=findViewById(R.id.completeOrderButton);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              bookingAPI.endService(session.sessionID, session.serviceProviderID, new BookingAPI.BookingTask() {
                  @Override
                  public void onSuccess(List<Session> sessions) {

                  }

                  @Override
                  public void onSuccess(Session session) {

                  }

                  @Override
                  public void onSuccess() {
                            navigateToMainMenu();
                  }

                  @Override
                  public void onFailed(String msg) {

                  }
              });
            }
        });
        Button cancel=findViewById(R.id.cancelOrderButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingAPI.cancelSession(session.sessionID, session.serviceProviderID, new BookingAPI.BookingTask() {
                    @Override
                    public void onSuccess(List<Session> sessions) {

                    }

                    @Override
                    public void onSuccess(Session session) {

                    }

                    @Override
                    public void onSuccess() {
                    navigateToMainMenu();
                    }

                    @Override
                    public void onFailed(String msg) {

                    }
                });
            }
        });
        TextView textView = findViewById(R.id.trackOrderUserName);
        textView.setText(user.getName());
        textView = findViewById(R.id.trackOrderUserPhone);
        textView.setText(user.getPhone());
        final TextView finalTextView = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + finalTextView.getText()));
                startActivity(intent);
            }
        });
        Button button = findViewById(R.id.cancelOrderButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingAPI.cancelSession(sessionID, session.serviceProviderID, new BookingAPI.BookingTask() {
                    @Override
                    public void onSuccess(List<Session> sessions) {

                    }

                    @Override
                    public void onSuccess(Session session) {

                    }

                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailed(String msg) {
                        Toast.makeText(getApplicationContext(), "Cant cancel please try again", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    LocationService locationService=new LocationService();
    EmailAuth emailAuth=new EmailAuth();
    void pushServiceProviderLocation() {

            String uid = emailAuth.checkSignIn().getUid();

            locationService.pushLocation("sp", uid, end, new LocationService.LocationTask() {
                @Override
                public void onGetDistance(String value) {

                }

                @Override
                public void onGetServiceProvidersWithinDistance(List<ServiceProvider> serviceProviders) {

                }

                @Override
                public void onFailure(String msg) {
                  //  Toast.makeText(getActivity(), "Push Location Failed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(Object location) {

                    // Toast.makeText(getActivity(), "Pushing Location", Toast.LENGTH_LONG).show();
                }
            });



    }
    void getUserLocation() {


        userAPI.getUser("user", session.userID, new UserAPI.UserTask() {
            @Override
            public void onSuccess(Object object) {
                user = (User) object;
                if (user != null && user.currentLocation != null) {
                    CustomLatLng customLatLng = user.currentLocation;
                    end = new LatLng(customLatLng.getLatitude(), customLatLng.getLongitude());
                    pushServiceProviderLocation();
                    Findroutes(start, end);
                    updateUserDetails();
                    pushServiceProviderLocation();

                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_service_session);

        //request location permission.
        requestPermision();
        Toast.makeText(getApplicationContext(),"You are currently in an active session",Toast.LENGTH_LONG).show();
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
                if (!initialLocation) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                            ltlng, 16f);
                    mMap.animateCamera(cameraUpdate);
                    initialLocation = true;

                    getSession();
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
        endMarker.position(polylineEndLatLng);
        endMarker.title(user.getName());
        mMap.addMarker(endMarker);
    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start, end);
    }


}