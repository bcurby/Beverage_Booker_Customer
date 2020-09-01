package com.beveragebooker.customer_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beveragebooker.customer_app.R;

import com.beveragebooker.customer_app.models.MenuItem;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<MenuItem> cartItemList;
    private OnItemClickListener itemListener;


    DecimalFormat currency = new DecimalFormat("###0.00");

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnButtonClickListener(OnItemClickListener listener) {
        itemListener = listener;
    }

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
    public void onBindViewHolder(@NonNull CartViewHolder holder, final int position) {
        MenuItem cartItem = cartItemList.get(position);

        holder.textViewName.setText(cartItem.getName());
        holder.textViewPrice.setText("$" + currency.format(cartItem.getPrice()));
        holder.textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.textViewMilk.setText(cartItem.getItemMilk());
        holder.textViewSugar.setText(cartItem.getItemSugar());
        holder.textViewDecaf.setText(cartItem.getItemDecaf());
        holder.textViewVanilla.setText(cartItem.getItemVanilla());
        holder.textViewCaramel.setText(cartItem.getItemCaramel());
        holder.textViewChocolate.setText(cartItem.getItemChocolate());
        holder.textViewWhippedCream.setText(cartItem.getItemWhippedCream());
        holder.textViewFrappe.setText(cartItem.getItemFrappe());
        holder.textViewHeated.setText(cartItem.getItemHeated());
        holder.textViewComment.setText(cartItem.getItemComment());

        holder.deleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null) {
                    int pos = position;
                    if (pos != RecyclerView.NO_POSITION) {
                        itemListener.onItemClick(pos);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPrice, textViewQuantity, textViewMilk, textViewSugar,
        textViewDecaf, textViewVanilla, textViewCaramel, textViewChocolate, textViewWhippedCream,
        textViewFrappe, textViewHeated, textViewComment;
        Button deleteCartItem;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.cartItemName);
            textViewPrice = itemView.findViewById(R.id.cartItemPrice);
            textViewQuantity = itemView.findViewById(R.id.cartItemQuantity);
            textViewMilk = itemView.findViewById(R.id.cartItemMilk);
            textViewSugar = itemView.findViewById(R.id.cartItemSugar);
            textViewDecaf = itemView.findViewById(R.id.cartItemDecaf);
            textViewVanilla = itemView.findViewById(R.id.cartItemVanilla);
            textViewCaramel = itemView.findViewById(R.id.cartItemCaramel);
            textViewChocolate = itemView.findViewById(R.id.cartItemChocolate);
            textViewWhippedCream = itemView.findViewById(R.id.cartItemWhippedCream);
            textViewFrappe = itemView.findViewById(R.id.cartItemFrappe);
            textViewHeated = itemView.findViewById(R.id.cartItemHeated);
            textViewComment = itemView.findViewById(R.id.cartItemComment);
            deleteCartItem = itemView.findViewById(R.id.deleteCartItem);


        }
    }
}
