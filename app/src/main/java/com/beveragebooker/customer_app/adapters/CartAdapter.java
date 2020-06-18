package com.beveragebooker.customer_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beveragebooker.customer_app.R;

import com.beveragebooker.customer_app.models.MenuItem;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<MenuItem> cartItemList;

    DecimalFormat currency = new DecimalFormat("###0.00");

    public CartAdapter(List<MenuItem> cartItemList) {
        this.cartItemList = cartItemList;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cartView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_item, null);
        CartViewHolder cartViewHolder = new CartViewHolder(cartView);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        MenuItem cartItem = cartItemList.get(position);

        holder.textViewName.setText(cartItem.getName());
        holder.textViewPrice.setText("$" + currency.format(cartItem.getPrice()));
        holder.textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewPrice, textViewQuantity;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.cartItemName);
            textViewPrice = itemView.findViewById(R.id.cartItemPrice);
            textViewQuantity = itemView.findViewById(R.id.cartItemQuantity);

        }
    }
}
