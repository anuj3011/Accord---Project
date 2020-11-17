package com.example.accord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.accord.Firestore.BookingAPI;
import com.example.accord.Firestore.OrderHistoryAPI;
import com.example.accord.Models.Session;
import com.example.accord.UserMainMenu.OrderPage;

import java.util.List;

public class OrderConfirmation extends AppCompatActivity {

    boolean isSearching=true;
    boolean found=false;
    void navigateToOrderPage(){
        Intent intent = new Intent(getApplicationContext(), OrderPage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
        finish();
    }
    void navigateToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("type","user");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        finish();
    }
    @Override
    public void onBackPressed() {
       if(!found){
           cancelSession();
       }
       else {
           navigateToMainActivity();
       }
    }

    String sessionID = "";
    BookingAPI bookingAPI = new BookingAPI();
    void cancelSession(){
        bookingAPI.cancelSession(sessionID,session.userID, new BookingAPI.BookingTask() {
            @Override
            public void onSuccess(List<Session> sessions) {

            }

            @Override
            public void onSuccess(Session session) {

            }

            @Override
            public void onSuccess() {
                    //canceled
                navigateToOrderPage();
            }

            @Override
            public void onFailed(String msg) {
                //failed
            }
        });
    }
    OrderHistoryAPI orderHistoryAPI=new OrderHistoryAPI();
    Session session=new Session();
    void getSession() {
        Bundle args = getIntent().getExtras();
        sessionID = args.getString("sessionID");
        bookingAPI.checkIfSessionAccepted(sessionID, new BookingAPI.BookingTask() {
            @Override
            public void onSuccess(List<Session> sessions) {

            }

            @Override
            public void onSuccess(Session acceptedSession) {
                found=true;

                session=acceptedSession;
                acceptedAnimation();
                navigateToMainActivity();


            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed(String msg) {

            }
        });
    }

    void acceptedAnimation() {
        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottieAnimationView);
        lottieAnimationView.animate().alpha(0).setDuration(100);
        TextView textView = (TextView) findViewById(R.id.searchingTextView);
        textView.animate().alpha(0).setDuration(100);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottieAnimationView5);
        lottieAnimationView.animate().alpha(0).setDuration(100);

        LottieAnimationView lottieAnimationView2 = (LottieAnimationView) findViewById(R.id.lottieAnimationView2);
        lottieAnimationView2.animate().alpha(1).setDuration(750);
        textView = (TextView) findViewById(R.id.successTextView);
        textView.animate().alpha(1).setDuration(750);
        Toast.makeText(getApplicationContext(),"Open Track Order to view current Order",Toast.LENGTH_LONG).show();
    }

    void searchingAnimation() {

        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottieAnimationView);
        lottieAnimationView.animate().alpha(1).setDuration(750);
        TextView pressBackTextView=findViewById(R.id.pressBackTextView);
        pressBackTextView.animate().alpha(1).setDuration(10);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        getSession();

        searchingAnimation();
    }
}