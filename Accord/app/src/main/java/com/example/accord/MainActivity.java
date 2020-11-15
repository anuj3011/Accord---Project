package com.example.accord;

//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.accord.Auth.SignIn;
//import com.example.accord.Firestore.FirestoreAPI;
//import com.example.accord.Models.NGO;
//
//public class MainActivity extends AppCompatActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // change 1
//        FirestoreAPI firestoreAPI = new FirestoreAPI();
//        firestoreAPI.pushFirestore("ngo", "uid", new NGO("full_name", "Phone", "Address", "email", "profession"));
//        firestoreAPI.getNGO("uid");
//        startActivity(new Intent(this, SignIn.class));
//        // EmailAuth emailAuth=new EmailAuth();
//        // emailAuth.signIn("dhruvddevasthale@gmail.com","test123",MainActivity.this);// signs in and prints uid
//        //check:https://console.firebase.google.com/u/0/project/accord-b1f26/authentication/users
//
//    }
//}


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accord.Auth.EmailAuth;
import com.example.accord.Firestore.FirebaseTaskInterface;
import com.example.accord.Firestore.OrderHistoryAPI;
import com.example.accord.Firestore.UserAPI;
import com.example.accord.Models.CustomUser;
import com.example.accord.Models.NGO;
import com.example.accord.Models.Order;
import com.example.accord.Models.User;
import com.example.accord.NGOMainMenu.ngo;
import com.example.accord.ServiceMainMenu.ServicesOption;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.accord.Firestore.LocationService;
import com.example.accord.Models.ServiceProvider;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private long backPressedTime;
    private Toast backToast;
    int realTimePush = 0;
    String type="";
    String userId = "";
    UserAPI firestoreAPI = new UserAPI();
    User user = new User();
    ServiceProvider serviceProvider=new ServiceProvider();
    EmailAuth emailAuth=new EmailAuth();
    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
            ;
        }
        backPressedTime = System.currentTimeMillis();
    }
    void updateNavHeader(ServiceProvider user) {
        try {
            TextView textView=navigationView.getHeaderView(0).findViewById(R.id.navHeaderName);
            textView.setText(user.getFirst_name());
            textView=navigationView.getHeaderView(0).findViewById(R.id.navHeaderEmail);
            textView.setText(user.getEmail());
        } catch (NullPointerException nullPointerException) {
            getUserProfile();
        } catch (Exception exception) {

        }
    }
    void updateNavHeader(NGO user) {
        try {
            TextView textView=navigationView.getHeaderView(0).findViewById(R.id.navHeaderName);
            textView.setText(user.getfull_name());
            textView=navigationView.getHeaderView(0).findViewById(R.id.navHeaderEmail);
            textView.setText(user.getemail());
        } catch (NullPointerException nullPointerException) {
            getUserProfile();
        } catch (Exception exception) {

        }
    }
    void updateNavHeader(User user) {
        try {
            TextView textView=navigationView.getHeaderView(0).findViewById(R.id.navHeaderName);
            textView.setText(user.getName());
            textView=navigationView.getHeaderView(0).findViewById(R.id.navHeaderEmail);
            textView.setText(user.getemail());
        } catch (NullPointerException nullPointerException) {
            getUserProfile();
        } catch (Exception exception) {

        }
    }
    NGO ngo=new NGO();
    void getUserProfile() {
        userId = emailAuth.checkSignIn().getUid();
        if(userId==null || userId.length()<1){

                emailAuth.logout();
            Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this,IntroActivity.class));

        }
        firestoreAPI.getUser(type, userId, new UserAPI.UserTask() {
            @Override
            public void onSuccess(Object object) {
                if(type.equals("user")){
                    user=(User) object;
                    serviceProvider=null;
                    updateNavHeader(user);
                }
                else if(type.equals("sp")){
                    serviceProvider=(ServiceProvider) object;
                    user=null;
                    updateNavHeader(serviceProvider);
                }
                else{
                    ngo=(NGO) object;

                }



            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    NavigationView navigationView;
    NavController navController ;

    void setupNavigationView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_logout)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        final Bundle args=new Bundle();
        args.putString("type",type);
        args.putString("id",userId);
       navController.navigate(R.id.nav_home,args);
       navigationView.getMenu().getItem(3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               emailAuth.logout();
               startActivity(new Intent(getApplicationContext(),IntroActivity.class));
               drawer.closeDrawers();
               return true;
           }
       });
       navigationView.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               args.putString("type",type);
               args.putString("id",userId);
               navController.navigate(R.id.nav_home,args);
               drawer.closeDrawers();
               return true;
           }
       });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle user=getIntent().getExtras();
        if(user!=null){
           String type=user.getString("type");
          this.type=type;
         String uid=user.getString("id");
         this.userId=uid;

        }
        getUserProfile();
        setupNavigationView();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void ToServices(View view) {
        Intent intent = new Intent(this, ServicesOption.class);
        startActivity(intent);
    }

    public void ToNGO(View view) {
        Intent intent = new Intent(this, ngo.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }
}