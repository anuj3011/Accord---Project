package com.example.accord.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accord.OrderAdapter;
import com.example.accord.Order_Type;
import com.example.accord.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    //private static final String TAG = "DemoActivity";
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    ArrayList<Order_Type> order_typeArrayList = new ArrayList<>();

    public static final String[] Orders= {"Sample Order 1","Sample Order 2", "Sample Order 3","Sample Order 4","Sample Order 5"};
    public static final int[] OrderImg = {R.drawable.down2,R.drawable.down2,R.drawable.down2,R.drawable.down2,R.drawable.down2};

    //private SlidingUpPanelLayout mLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(com.example.accord.History.HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        int i=0;
        while (i<Orders.length) {
            Order_Type order_type = new Order_Type();

            order_type.SetOrder(Orders[i]);
            order_type.setImg(OrderImg[i]);

            order_typeArrayList.add(order_type);
            i++;
        }
        orderAdapter = new OrderAdapter(order_typeArrayList);
        recyclerView = (RecyclerView)root.findViewById(R.id.OrdersView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(orderAdapter);
        return root;
    }
}