package com.beveragebooker.customer_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beveragebooker.customer_app.activities.CartActivity;
import com.beveragebooker.customer_app.adapters.RecyclerAdapter;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.MenuItem;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseMenu extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;

    //Added Fill Cart
    private RecyclerAdapter mRecyclerAdapter;
    private ArrayList<MenuItem> mMenuItems;
    private RecyclerView.LayoutManager mLayoutManager;

    //View Cart Button
    private Button viewCart;

    //Cart arraylist
    private ArrayList<MenuItem> cartArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_menu);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //View Cart Button
        viewCart = findViewById(R.id.viewCart);
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCart();
            }

        });


        mMenuItems = new ArrayList<MenuItem>();

        mRecyclerAdapter = new RecyclerAdapter(BrowseMenu.this, mMenuItems);

        mRecyclerView.setAdapter(mRecyclerAdapter);


        //Listener for Add to Cart
        mRecyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (!cartArrayList.contains(mMenuItems.get(position))) {
                    cartArrayList.add(mMenuItems.get(position));
                    cartArrayList.get(position).setQuantity(1);
                }else {
                    cartArrayList.get(position).incrementQuantity();
                }

                //Used for checking cart items are being updated
                for (int i = 0; i < cartArrayList.size(); i++)
                    Log.d("Cart", String.valueOf(cartArrayList));


                if (cartArrayList.get(position) == mMenuItems.get(position))
                    Toast.makeText(BrowseMenu.this,    "item added to cart",
                            Toast.LENGTH_LONG).show();

                    saveData();
            }
        });

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getItems();

        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                            mMenuItems.add(response.body().get(i));
                    }

                    mRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Toast.makeText(BrowseMenu.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onItemClick(int position) {
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartArrayList);

        //Prints the contents of cartArraylist to Logcat for visual checking of values
        System.out.println(json);

        editor.putString("cart", json);
        editor.apply();
    }



    private void openCart() {
        Intent intent = new Intent(this, CartActivity.class );
        startActivity(intent);
    }
}
