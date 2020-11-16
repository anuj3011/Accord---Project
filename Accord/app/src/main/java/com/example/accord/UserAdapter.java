package com.example.accord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accord.Firestore.BookingAPI;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.Session;
import com.example.accord.Models.User;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<Session> sessionList;
    Context context;
    ServiceProvider serviceProvider=new ServiceProvider();
    public UserAdapter(List<Session>UserList, ServiceProvider serviceProvider){
        this.sessionList = UserList;
        this.serviceProvider=serviceProvider;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView textorder;
        CardView cv;

        ImageButton acceptButton;
        //Button trackOrderButton;
        public ViewHolder(View itemView){
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img1);
            textorder = (TextView)itemView.findViewById(R.id.user);
            cv = (CardView)itemView.findViewById(R.id.cvUser);
            acceptButton=itemView.findViewById(R.id.acceptOrderButton);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }
    BookingAPI bookingAPI=new BookingAPI();
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Session session = sessionList.get(position);

        holder.textorder.setText(session.serviceCategory);
        holder.img.setImageResource(R.drawable.person_male_black1);
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                bookingAPI.acceptServiceSession(serviceProvider.uid, session.sessionID, new BookingAPI.BookingTask() {
                    @Override
                    public void onSuccess(List<Session> sessions) {
                        Toast.makeText(v.getContext(),"Accepted, navigate to user Location",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(Session session) {
                        Toast.makeText(v.getContext(),"Accepted, navigate to user Location",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onSuccess() {
                        Toast.makeText(v.getContext(),"Accepted, navigate to user Location",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailed(String msg) {
                        Toast.makeText(v.getContext(),"Cant Accept, please try again",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });


    }

    @Override
    public int getItemCount(){
        return sessionList.size();
    }


}
