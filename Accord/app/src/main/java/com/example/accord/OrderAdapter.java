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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accord.Models.Order;

import java.util.List;




public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Order> OrderList;
    Context context;

    public OrderAdapter(List<Order>OrderList){
        this.OrderList = OrderList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView textorder;
        CardView cv;
        Button trackOrderButton;
        public ViewHolder(View itemView){
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img);
            textorder = (TextView)itemView.findViewById(R.id.order);
            cv = (CardView)itemView.findViewById(R.id.cv);
            trackOrderButton=itemView.findViewById(R.id.trackOrderButton);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Order order_type = OrderList.get(position);

        holder.textorder.setText(order_type.getServiceName());
        holder.img.setImageResource(R.drawable.down2);
        holder.trackOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchTrack=new Intent(context,TrackOrder.class);
                launchTrack.putExtra("serviceProvider", order_type.serviceProviderID);
                context.startActivity(launchTrack);
            }
        });


    }

    @Override
    public int getItemCount(){
        return OrderList.size();
    }


}
