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
import android.os.LocaleList;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.accord.Firestore.LocationService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private long backPressedTime;
    private Toast backToast;

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

    void testLocation() {
        new LocationService().getDistance("19.237500, 72.971638",
                "19.236477, 72.972089", new LocationService.LocationTask() {
                    @Override
                    public void onGetDistance(String value) {

                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(Object location) {
                        Toast.makeText(getApplicationContext(),location.toString(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testLocation();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
}