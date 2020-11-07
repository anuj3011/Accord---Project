package com.example.accord.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accord.Auth.EmailAuth;
import com.example.accord.Firestore.UserAPI;
import com.example.accord.Models.Order;
import com.example.accord.Models.User;
import com.example.accord.OrderAdapter;
import com.example.accord.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    //private static final String TAG = "DemoActivity";
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    ArrayList<Order> orderList = new ArrayList<>();
    UserAPI userAPI=new UserAPI();
    String uid="";
    User user=new User();

    View root;
    //private SlidingUpPanelLayout mLayout;
    void getOrders(){
       userAPI.getUser("user", uid, new UserAPI.UserTask() {
           @Override
           public void onSuccess(Object object) {
               user=(User) object;
               if(user!=null){
                   orderList=user.getOrders();
                   updateRecyclerView(root);
               }
           }

           @Override
           public void onFailure(String msg) {
               Toast.makeText(getContext(),"Could not get Orders:"+msg,Toast.LENGTH_LONG);
           }
       });
    }
    void updateRecyclerView(View root){

        orderAdapter = new OrderAdapter(orderList);
        recyclerView = (RecyclerView)root.findViewById(R.id.OrdersView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(orderAdapter);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(com.example.accord.History.HistoryViewModel.class);
         root = inflater.inflate(R.layout.fragment_history, container, false);

        uid=new EmailAuth().checkSignIn().getUid();
        getOrders();

        return root;
    }
}