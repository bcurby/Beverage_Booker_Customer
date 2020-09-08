package com.beveragebooker.customer_app.activities;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beveragebooker.customer_app.R;

import com.beveragebooker.customer_app.adapters.RecyclerAdapter;
import com.beveragebooker.customer_app.api.RetrofitClient;

import com.beveragebooker.customer_app.models.MenuItem;
import com.beveragebooker.customer_app.models.User;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.beveragebooker.customer_app.storage.SharedPrefManager.*;

public class BrowseMenu extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {

    public static final String ITEM_ID = "com.beveragebooker.customer_app.ITEM_ID";
    public static final String ITEM_NAME = "com.beveragebooker.customer_app.ITEM_NAME";
    public static final String ITEM_PRICE = "com.beveragebooker.customer_app.ITEM_PRICE";
    public static final String ITEM_MILK = "com.beveragebooker.customer_app.ITEM_MILK";
    public static final String ITEM_DECAF = "com.beveragebooker.customer_app.ITEM_DECAF";
    public static final String ITEM_SUGAR = "com.beveragebooker.customer_app.ITEM_SUGAR";
    public static final String ITEM_EXTRAS = "com.beveragebooker.customer_app.ITEM_EXTRAS";
    public static final String ITEM_FRAPPE = "com.beveragebooker.customer_app.ITEM_FRAPPE";
    public static final String ITEM_HEATED = "com.beveragebooker.customer_app.ITEM_HEATED";
    public static final String ITEM_TYPE = "com.beveragebooker.customer_app.ITEM_TYPE";


    private RecyclerView mRecyclerView;

    //Added Fill Cart
    private RecyclerAdapter mRecyclerAdapter;
    private ArrayList<MenuItem> mMenuItems;

    //View Cart Button
    private Button viewCart;

    private String itemType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_menu);

        Intent intent = getIntent();

        if (intent.getStringExtra(PrimaryMenu.ITEM_TYPE_MENU) != null) {
            itemType = intent.getStringExtra(PrimaryMenu.ITEM_TYPE_MENU);
        } else if (intent.getStringExtra(AddToCartActivity.ITEM_TYPE_RETURN) != null) {
            itemType = intent.getStringExtra(AddToCartActivity.ITEM_TYPE_RETURN);
        }


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


        mMenuItems = new ArrayList<>();

        mRecyclerAdapter = new RecyclerAdapter(mMenuItems);

        mRecyclerView.setAdapter(mRecyclerAdapter);


        //Listener for Add to Cart
        mRecyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                //Item ID of clicked item
                int itemID = mMenuItems.get(position).getId();

                //Check position
                MenuItem itemClicked = mMenuItems.get(position);
                System.out.println("position: " + position);
                System.out.println("Item ID: " + itemID);

                //Logged in User ID
                final User loggedUser = getInstance(BrowseMenu.this).getUser();
                int userID = loggedUser.getId();
                System.out.println("UserID: " + userID);

                //Title of clicked item
                String itemTitle = itemClicked.getName();
                System.out.println("Title: " + itemTitle);

                //Price of clicked item
                double itemPrice = itemClicked.getPrice();
                System.out.println("Price: " + itemPrice);

                //Quantity of clicked item
                itemClicked.setQuantity(1);
                int itemQuantity = itemClicked.getQuantity();
                System.out.println("Quantity: " + itemQuantity);


                int milkStatus = itemClicked.getMilk();
                System.out.printf("Milk: %d%n", milkStatus);

                int sugarStatus = itemClicked.getSugar();
                System.out.printf("Sugar: %d%n", sugarStatus);

                int decafStatus = itemClicked.getDecaf();
                System.out.printf("Decaf: %d%n", decafStatus);

                int extrasStatus = itemClicked.getExtras();
                System.out.printf("Extras: %d%n", extrasStatus);

                int frappeStatus = itemClicked.getFrappe();
                System.out.printf("Frappe: %d%n", frappeStatus);

                int heatedStatus = itemClicked.getHeated();
                System.out.printf("Heated: %d%n", heatedStatus);

                goToAddToCart(itemID, itemTitle, itemPrice, milkStatus, sugarStatus, decafStatus,
                        extrasStatus, frappeStatus, heatedStatus, itemType);

            }
        });

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getItems(itemType);

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

    private void goToAddToCart(int itemID, String itemTitle, double itemPrice, int milkStatus,
                               int sugarStatus, int decafStatus, int extrasStatus, int frappeStatus,
                               int heatedStatus, String itemType) {

        Intent intent = new Intent(this, AddToCartActivity.class);
        intent.putExtra(ITEM_ID, itemID);
        intent.putExtra(ITEM_NAME, itemTitle);
        intent.putExtra(ITEM_PRICE, itemPrice);
        intent.putExtra(ITEM_MILK, milkStatus);
        intent.putExtra(ITEM_SUGAR, sugarStatus);
        intent.putExtra(ITEM_DECAF, decafStatus);
        intent.putExtra(ITEM_EXTRAS, extrasStatus);
        intent.putExtra(ITEM_FRAPPE, frappeStatus);
        intent.putExtra(ITEM_HEATED, heatedStatus);
        intent.putExtra(ITEM_TYPE, itemType);

        startActivity(intent);
    }


    @Override
    public void onItemClick(int position) {
    }


    private void openCart() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}
