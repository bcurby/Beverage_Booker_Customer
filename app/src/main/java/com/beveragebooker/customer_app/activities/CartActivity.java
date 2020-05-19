package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.Toast;


import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.adapters.CartAdapter;
import com.beveragebooker.customer_app.api.RetrofitClient;

import com.beveragebooker.customer_app.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;

    private List<MenuItem> CartItemList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        
        CartItemList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.cartRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCartAdapter = new CartAdapter(CartItemList);

        mRecyclerView.setAdapter(mCartAdapter);

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCartItems();

        call.enqueue(new Callback<List<MenuItem>>() {

            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        CartItemList.add(response.body().get(i));
                    }

                    mCartAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
