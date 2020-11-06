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

import java.util.List;




public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Order_Type> OrderList;
    Context context;

    public OrderAdapter(List<Order_Type>OrderList){
        this.OrderList = OrderList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView textorder;
        CardView cv;

        public ViewHolder(View itemView){
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img);
            textorder = (TextView)itemView.findViewById(R.id.order);
            cv = (CardView)itemView.findViewById(R.id.cv);
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Order_Type order_type = OrderList.get(position);

        holder.textorder.setText(order_type.GetOrder());
        holder.img.setImageResource(order_type.GetImg());


    }

    @Override
    public int getItemCount(){
        return OrderList.size();
    }


}
