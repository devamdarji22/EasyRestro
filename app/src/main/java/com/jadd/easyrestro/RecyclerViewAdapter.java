package com.jadd.easyrestro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.net.ConnectException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context mConext;
    ArrayList<Cart> items;
    String category;

    public RecyclerViewAdapter(Context mConext, ArrayList<Cart> items, String category) {
        this.mConext = mConext;
        this.items = items;
        this.category = category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mConext).inflate(R.layout.cart_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = items.get(position).getItem().getName();
        holder.itemName.setText(name);
        long price = items.get(position).getItem().getPrice();
        holder.itemPrice.setText(String.valueOf(price));
        String quan = String.valueOf(items.get(position).getQuantity());
        holder.itemQuantity.setNumber(quan);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName,itemPrice;
        ElegantNumberButton itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemQuantity = itemView.findViewById(R.id.item_quantity);

        }
    }

}
