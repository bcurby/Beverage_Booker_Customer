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
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<MenuItem> cartItemList;
    private OnItemClickListener itemListener;


    DecimalFormat currency = new DecimalFormat("###0.00");

    public interface OnItemClickListener {
        void onItemClick(int selection, int position);
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
        View cartView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_item, parent, false);
        CartViewHolder cartViewHolder = new CartViewHolder(cartView);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, final int position) {
        MenuItem cartItem = cartItemList.get(position);


        String currentItemSize = cartItem.getItemSize();
        String currentItemMilk = cartItem.getItemMilk();
        String currentItemSugar = cartItem.getItemSugar();
        String currentItemDecaf = cartItem.getItemDecaf();
        String currentItemVanilla = cartItem.getItemVanilla();
        String currentItemCaramel = cartItem.getItemCaramel();
        String currentItemChocolate = cartItem.getItemChocolate();
        String currentItemWhippedCream = cartItem.getItemWhippedCream();
        String currentItemFrappe = cartItem.getItemFrappe();
        String currentItemHeated = cartItem.getItemHeated();
        String currentItemComment = cartItem.getItemComment();
        String currentItemQuantity = String.valueOf(cartItem.getQuantity());

        holder.textViewName.setText(cartItem.getName());
        holder.textViewPrice.setText("$" + currency.format(cartItem.getPrice()));
        holder.textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.qtyButtonCart.setNumber(currentItemQuantity);

        if (!currentItemSize.equals("-")) {
            holder.textViewSizeTitle.setVisibility(TextView.VISIBLE);
            holder.textViewSize.setVisibility(TextView.VISIBLE);
            holder.textViewSize.setText(cartItem.getItemSize());
        }

        if (!currentItemMilk.equals("-")) {
            holder.textViewMilk.setVisibility(TextView.VISIBLE);
            holder.textViewMilk.setText(cartItem.getItemMilk());
        }

        if (!currentItemSugar.equals("-")) {
            holder.textViewSugar.setVisibility(TextView.VISIBLE);
            holder.textViewSugar.setText(cartItem.getItemSugar());
        }

        if (!currentItemDecaf.equals("-")) {
            holder.textViewDecaf.setVisibility(TextView.VISIBLE);
            holder.textViewDecaf.setText(cartItem.getItemDecaf());
        }

        if (!currentItemVanilla.equals("-")) {
            holder.textViewVanilla.setVisibility(TextView.VISIBLE);
            holder.textViewVanilla.setText(cartItem.getItemVanilla());
        }

        if (!currentItemCaramel.equals("-")) {
            holder.textViewCaramel.setVisibility(TextView.VISIBLE);
            holder.textViewCaramel.setText(cartItem.getItemCaramel());
        }

        if (!currentItemChocolate.equals("-")) {
            holder.textViewChocolate.setVisibility(TextView.VISIBLE);
            holder.textViewChocolate.setText(cartItem.getItemChocolate());
        }

        if (!currentItemWhippedCream.equals("-")) {
            holder.textViewWhippedCream.setVisibility(TextView.VISIBLE);
            holder.textViewWhippedCream.setText(cartItem.getItemWhippedCream());
        }

        if (!currentItemFrappe.equals("-")) {
            holder.textViewFrappe.setVisibility(TextView.VISIBLE);
            holder.textViewFrappe.setText(cartItem.getItemFrappe());
        }

        if (!currentItemHeated.equals("-")) {
            holder.textViewHeated.setVisibility(TextView.VISIBLE);
            holder.textViewHeated.setText(cartItem.getItemHeated());
        }

        if (!currentItemComment.equals("-")) {
            holder.textViewCommentTitle.setVisibility(TextView.VISIBLE);
            holder.textViewComment.setVisibility(TextView.VISIBLE);
            holder.textViewComment.setText(cartItem.getItemComment());
        }

        /*
        holder.textViewSugar.setText(cartItem.getItemSugar());
        holder.textViewDecaf.setText(cartItem.getItemDecaf());
        holder.textViewVanilla.setText(cartItem.getItemVanilla());
        holder.textViewCaramel.setText(cartItem.getItemCaramel());
        holder.textViewChocolate.setText(cartItem.getItemChocolate());
        holder.textViewWhippedCream.setText(cartItem.getItemWhippedCream());
        holder.textViewFrappe.setText(cartItem.getItemFrappe());
        holder.textViewHeated.setText(cartItem.getItemHeated());
        holder.textViewComment.setText(cartItem.getItemComment());
        holder.textViewSize.setText(cartItem.getItemSize());*/

        holder.deleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null) {
                    int pos = position;
                    if (pos != RecyclerView.NO_POSITION) {
                        itemListener.onItemClick(1, pos);
                    }
                }
            }
        });

        holder.qtyButtonCart.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemListener != null) {
                    int pos = position;
                    if (pos != RecyclerView.NO_POSITION) {
                        itemListener.onItemClick(2, pos);
                        String stringItemQuantity = holder.qtyButtonCart.getNumber();
                        holder.textViewQuantity.setText(stringItemQuantity);
                        System.out.println("Qty: " + stringItemQuantity);
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
        textViewFrappe, textViewHeated, textViewCommentTitle, textViewComment, textViewSizeTitle, textViewSize;
        Button deleteCartItem;
        ElegantNumberButton qtyButtonCart;


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
            textViewCommentTitle = itemView.findViewById(R.id.commentTitle);
            textViewComment = itemView.findViewById(R.id.cartItemComment);
            deleteCartItem = itemView.findViewById(R.id.deleteCartItem);
            textViewSizeTitle = itemView.findViewById(R.id.sizeTitle);
            textViewSize = itemView.findViewById(R.id.cartItemSize);
            qtyButtonCart = itemView.findViewById(R.id.qtyButtonCart);


        }
    }
}
