package com.beveragebooker.customer_app.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beveragebooker.customer_app.models.MenuItem;
import com.beveragebooker.customer_app.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<MenuItem> menuItems;
    private OnItemClickListener mListener;

    DecimalFormat currency = new DecimalFormat("###0.00");

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //Add to Cart button listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mItemName;
        TextView mPrice;
        TextView mSoldOut;
        TextView mShortDesc;

        Button mAddToCart;


        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemName = itemView.findViewById(R.id.itemName);
            mPrice = itemView.findViewById(R.id.itemPrice);
            mAddToCart = itemView.findViewById(R.id.addToCart);
            mSoldOut = itemView.findViewById(R.id.soldOutStatus);
            mShortDesc = itemView.findViewById(R.id.itemShortDesc);

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
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        MenuItem currentItem = menuItems.get(position);

        int itemStock = currentItem.getItemStock();
        int displayedStock = itemStock - 5;
        String itemType = currentItem.getItemType();
        System.out.println("ItemStock: " + itemStock);
        System.out.println("DisplayedStock: " + displayedStock);

        holder.mItemName.setText(currentItem.getName());
        holder.mPrice.setText("$" + currency.format(currentItem.getPrice()));
        holder.mShortDesc.setText(currentItem.getDescription());

        if (itemStock <= 5 && itemType.equals("food")) {
            holder.mAddToCart.setEnabled(false);
            holder.mAddToCart.setText("SOLD OUT");
            holder.mSoldOut.setVisibility(TextView.VISIBLE);
            holder.mShortDesc.setVisibility(TextView.INVISIBLE);
        }

        if (itemStock >= 6 && itemType.equals("food")) {
            holder.mAddToCart.setEnabled(true);
            holder.mSoldOut.setVisibility(TextView.INVISIBLE);
            holder.mShortDesc.setVisibility(TextView.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
