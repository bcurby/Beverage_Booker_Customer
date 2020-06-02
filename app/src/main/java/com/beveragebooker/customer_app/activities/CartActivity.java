package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.adapters.CartAdapter;
import com.beveragebooker.customer_app.api.RetrofitClient;

import com.beveragebooker.customer_app.models.MenuItem;
import com.beveragebooker.customer_app.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.beveragebooker.customer_app.storage.SharedPrefManager.getInstance;


public class CartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;

    private List<MenuItem> cartItemList;

    private TextView cartTotal;

    private Button checkoutButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        
        cartItemList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.cartRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCartAdapter = new CartAdapter(cartItemList);

        mRecyclerView.setAdapter(mCartAdapter);

        cartTotal = findViewById(R.id.cartTotal);

        //Checkout Button
        checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCheckout();
            }

        });

        final User loggedUser = getInstance(CartActivity.this).getUser();
        int userID = loggedUser.getId();
        System.out.println("Get Cart Items UserID:" + userID);

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCartItems(userID);

        call.enqueue(new Callback<List<MenuItem>>() {

            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {

                //Cart items are retrieved from database
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        cartItemList.add(response.body().get(i));
                    }
                    mCartAdapter.notifyDataSetChanged();

                //Cart is empty
                } else if (response.code() == 305) {
                    Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_LONG).show();
                }
                //Display the total of items in the cart
                cartTotal.setText("Cart Total: $" + (getCartTotal()));
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    //Method that calculates the total of items in the cart
    private double getCartTotal() {

        double cartTotal = 0;

        for (int i = 0; i < cartItemList.size(); i++) {

            int quantity = cartItemList.get(i).getQuantity();
            System.out.println("Quantity: " + quantity);

            double price = cartItemList.get(i).getPrice();
            System.out.println("Price: " + price);

            double itemTotal = quantity * price;
            System.out.println("Item Total: " + itemTotal);

            cartTotal += itemTotal;
            System.out.println("Cart Total: " + cartTotal);


        }
        return cartTotal;
    }

    private void goToCheckout() {
        Intent intent = new Intent(this, PaymentActivity.class );
        startActivity(intent);
    }
}
