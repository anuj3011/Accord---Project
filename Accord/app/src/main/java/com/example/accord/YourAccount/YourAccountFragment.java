package com.example.accord.YourAccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.accord.Auth.EmailAuth;
import com.example.accord.Firestore.UserAPI;
import com.example.accord.Models.User;
import com.example.accord.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

public class YourAccountFragment extends Fragment {

    private YourAccountModel yourAccountModel;
    String uid = " ";
    UserAPI firestoreAPI = new UserAPI();

    FloatingActionButton editButton;
    FloatingActionButton applyButton;
    EmailAuth emailAuth = new EmailAuth();
    User user=new User();

    void getUserProfile() {
        firestoreAPI.getUser("user", "testUid", new UserAPI.UserTask() {
            @Override
            public void onSuccess(Object object) {
                user = (User) object;
                updateUserProfile(user);
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    void editProfile() {
//        EditText textView = findViewById(R.id.userNameText);
//        user.name = textView.getText().toString();
//        textView = findViewById(R.id.userPhoneText);
//        user.phone = textView.getText().toString();
//        textView = findViewById(R.id.userEmailText);
//        user.email = textView.getText().toString();
//        textView = findViewById(R.id.userAddressText);
//        user.add1 = textView.getText().toString();
//        firestoreAPI.pushUserDetails("user",user.uid,user);
//        applyButton.setAlpha((float) 0.0);
//        setTextViewEnable(R.id.userNameText,false);
//        setTextViewEnable(R.id.userPhoneText,false);
//        setTextViewEnable(R.id.userEmailText,false);
//        setTextViewEnable(R.id.userAddressText,false);
//        Toast.makeText(getBaseContext(), "Details Updated", Toast.LENGTH_SHORT).show();
    }

    void setText(int textId, String text) {
        TextView textView = root.findViewById(textId);

        textView.setText(text);
    }


    void updateUserProfile(User user) {
        setText(R.id.Name, "Name:"+user.getName());
        setText(R.id.Email, "Email:"+user.getemail());

        setText(R.id.Phone, "Phone:"+user.getPhone());
        setText(R.id.OrderNumber, String.valueOf(user.getOrderCount()));
        setText(R.id.DonateNumber,String.valueOf(user.getDonationCount()));
        setText(R.id.Address,"Address:"+user.getadd1());

    }
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yourAccountModel =
                ViewModelProviders.of(this).get(YourAccountModel.class);

        root = inflater.inflate(R.layout.fragment_youraccount, container, false);
        getUserProfile();


        return root;
    }
}