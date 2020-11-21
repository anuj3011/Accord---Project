package com.example.accord;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accord.Firestore.BookingAPI;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.Session;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<String> sessionList;
    Context context;

    public OrderAdapter(List<String> sessionList) {
        this.sessionList = sessionList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView textorder;
        CardView cv;
        Button trackOrderButton;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            textorder = (TextView) itemView.findViewById(R.id.order);
            cv = (CardView) itemView.findViewById(R.id.cv);
            trackOrderButton = itemView.findViewById(R.id.trackOrderButton);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    BookingAPI bookingAPI = new BookingAPI();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    void setCanceled(Button button, final Session session){
        button.setText("Canceled");
        button.setActivated(false);
    }
    void setCompleted(Button button, final Session session){
        button.setText("View Order");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch order details activity
            }
        });
    }
    void setTrackOrder(Button button, final Session session){
        button.setText("Track Order");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchTrack = new Intent(context, TrackOrder.class);

                launchTrack.putExtra("serviceProvider", session.serviceProviderID);
                launchTrack.putExtra("session", session.sessionID);
                context.startActivity(launchTrack);
            }
        });
    }
    void updateOrderUI(ViewHolder holder, final Session session){
        holder.textorder.setText(session.serviceCategory);
        holder.img.setImageResource(R.drawable.down2);
        if(session.isActive){
            setTrackOrder(holder.trackOrderButton,session);
        }
        else if(session.isCompleted){
            setCompleted(holder.trackOrderButton,session);
        }
        else{
            setCanceled(holder.trackOrderButton,session);
        }

    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (sessionList.size() > 0) {
            String activeSession = sessionList.get(position);
            if (activeSession != null) {

                firebaseFirestore.collection("sessions").document(activeSession).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null) {
                            final Session session = value.toObject(Session.class);
                            if (session != null) {
                               updateOrderUI(holder,session);
                            }

                        }

                    }
                });
            }
            else{
                sessionList.remove(position);
            }
        }


    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }


}
