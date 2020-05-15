package com.beveragebooker.customer_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beveragebooker.customer_app.BrowseMenu;
import com.beveragebooker.customer_app.models.MenuItem;
import com.beveragebooker.customer_app.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<MenuItem> menuItems;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //Add to Cart button listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mItemID;
        TextView mItemName;
        TextView mShortDesc;
        TextView mPrice;

        Button mAddToCart;


        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemID = itemView.findViewById(R.id.itemID);
            mItemName = itemView.findViewById(R.id.itemName);
            mShortDesc = itemView.findViewById(R.id.itemDesc);
            mPrice = itemView.findViewById(R.id.itemPrice);
            mAddToCart = itemView.findViewById(R.id.addToCart);

            mAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecyclerAdapter(BrowseMenu browseMenu, ArrayList<MenuItem> listItems) {
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
        holder.mPrice.setText('$' + String.valueOf(currentItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
