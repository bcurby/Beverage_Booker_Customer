package com.beveragebooker.customer_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<ListItem> mListItems;

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {


        TextView mItemName;
        TextView mDesc;
        TextView mPrice;


        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemName = itemView.findViewById(R.id.text1);
            mDesc = itemView.findViewById(R.id.text2);
            mPrice = itemView.findViewById(R.id.text3);
        }
    }

    public RecyclerAdapter(ArrayList<ListItem> listItems) {
        mListItems = listItems;
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
        ListItem currentItem = mListItems.get(position);

        holder.mItemName.setText(currentItem.getText1());
        holder.mDesc.setText(currentItem.getText2());
        holder.mPrice.setText(currentItem.getText3());
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }
}
