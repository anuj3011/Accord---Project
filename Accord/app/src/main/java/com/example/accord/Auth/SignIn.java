package com.example.accord.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.accord.Firebase.EmailAuth;
import com.example.accord.Firebase.GoogleAuth;
import com.example.accord.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;

public class SignIn extends AppCompatActivity {
    private   int RC_SIGN_IN = 1;
    private long backPressedTime;
    private Toast backToast;
    int flag=0;
    String email;
    String pass;
    EditText textInput;
    EmailAuth emailAuth= new EmailAuth();
    Button signInButton;
    GoogleAuth googleAuth= new GoogleAuth();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Window g = getWindow();
        //g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_sign_in);
        signInButton= (Button)findViewById(R.id.LoginButton) ;


    }
    private void signInWithGoogle() {
        googleAuth.mGoogleSignInClient = GoogleSignIn.getClient(this, googleAuth.gso);
        Intent signInIntent = googleAuth.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                String token=account.getIdToken();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
    }
    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }else
        {
            backToast= Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();;
        }
        backPressedTime = System.currentTimeMillis();
    }

    public void signIn(){
        textInput=findViewById(R.id.EmailText);
        email=textInput.getText().toString();
        textInput=findViewById(R.id.signInPass);
        pass=textInput.getText().toString();
        emailAuth.signIn(email,pass,this);

    }
    public void NewUser(View view){
        TextView textView = (TextView) findViewById(R.id.PasswordText);
        textView.animate().alpha(0).setDuration(500);
        textView = (TextView) findViewById(R.id.NewUser);
        textView.animate().alpha(0).setDuration(500);
        textView = (TextView) findViewById(R.id.ForgotPass);
        textView.setText("Or");
        textView = (TextView) findViewById(R.id.TopText2);
        textView.animate().alpha(0).setDuration(600);
        textView = (TextView) findViewById(R.id.TopText3);
        textView.animate().alpha(1).setDuration(600);
        textView = (TextView) findViewById(R.id.GoogleText);
        textView.animate().alpha(1).setDuration(600);
        Button button = (Button) findViewById(R.id.LoginButton);
        button.setText("Get OTP");
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView2 = (TextView) findViewById(R.id.OTP);
                textView2.animate().alpha(1).setDuration(600);
                textView2 = (TextView) findViewById(R.id.TopText3);
                textView2.animate().alpha(0).setDuration(600);
                textView2 = (TextView) findViewById(R.id.TopText4);
                textView2.animate().alpha(1).setDuration(600);
                textView2 = (TextView) findViewById(R.id.GoogleText);
                textView2.animate().alpha(1).setDuration(600);
                Button button = (Button) findViewById(R.id.LoginButton);
                button.setText("Sign Up");
                signInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });



    }

}
