package com.example.accord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accord.Models.User;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<SampleUser> UserList;
    Context context;

    public UserAdapter(List<SampleUser>UserList){
        this.UserList = UserList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView textorder;
        CardView cv;
        //Button trackOrderButton;
        public ViewHolder(View itemView){
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img1);
            textorder = (TextView)itemView.findViewById(R.id.user);
            cv = (CardView)itemView.findViewById(R.id.cvUser);
            //trackOrderButton=itemView.findViewById(R.id.trackOrderButton);
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

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SampleUser User_type = UserList.get(position);

        holder.textorder.setText(User_type.getText1());
        holder.img.setImageResource(R.drawable.person_male_black1);
        /*holder.trackOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchTrack=new Intent(context,TrackOrder.class);
                launchTrack.putExtra("serviceProvider", User_type.serviceProviderId);
                context.startActivity(launchTrack);
            }
        });*/


    }

    @Override
    public int getItemCount(){
        return UserList.size();
    }


}
