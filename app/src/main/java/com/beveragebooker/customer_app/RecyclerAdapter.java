package com.beveragebooker.customer_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<MenuItem> menuItems;

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mItemID;
        TextView mItemName;
        TextView mShortDesc;
        TextView mPrice;



        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemID = itemView.findViewById(R.id.itemID);
            mItemName = itemView.findViewById(R.id.itemName);
            mShortDesc = itemView.findViewById(R.id.itemDesc);
            mPrice = itemView.findViewById(R.id.itemPrice);
        }
    }

    public RecyclerAdapter(ArrayList<MenuItem> listItems) {
        menuItems = listItems;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v);
        return rvh;
    }

    //Pass values to the views
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        MenuItem currentItem = menuItems.get(position);

        holder.mItemID.setText(String.valueOf(currentItem.getId()));
        holder.mItemName.setText(currentItem.getName());
        holder.mShortDesc.setText(currentItem.getDescription());
        holder.mPrice.setText(String.valueOf(currentItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
