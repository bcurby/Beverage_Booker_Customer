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

    private RecyclerView mRecyclerView;

    //Added Fill Cart
    private RecyclerAdapter mRecyclerAdapter;
    private ArrayList<MenuItem> mMenuItems;

    //View Cart Button
    private Button viewCart;


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
                System.out.println("position: " +position);
                System.out.println("Item ID: " +itemID);

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

                //Set cart status to active cart
                String cartStatus = "active";

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .addToCart(userID, itemID, itemTitle, itemPrice, itemQuantity, cartStatus);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.code() == 303) {
                            Toast.makeText(BrowseMenu.this, "Item added to cart", Toast.LENGTH_LONG).show();

                        } else if (response.code() == 304) {
                            Toast.makeText(BrowseMenu.this, "Item already in cart",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(BrowseMenu.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });
            }




                //Yesterday
                //for(MenuItem menuItem : mCartItems) {
                //if (mCartItems == null) {
                   // itemClicked.setQuantity(1);
                   // mCartItems.add(itemClicked);
               // } //else if (menuItem.getId() == itemClickedID) {
                   // menuItem.incrementQuantity();
               // } //else {
                   // mCartItems.add(mMenuItems.get(position));
               // }
            //}




            //MenuItem itemClicked = mMenuItems.get(position);

            
            
            //If Id of Cart item equals Id of item clicked
           // for (MenuItem cartItem : mCartItems) {
                //if(cartItem.getId() == itemClickedID) {

                    //add 1 to cartItem quantity
                  //  cartItem.incrementQuantity();


           // for (int i = 0; i < mCartItems.size(); i++)

               // MenuItem itemCheck = mCartItems.getId();
                //if(mCartItems.getId().contains(itemClickedID)) {

                    //if (!mCartItems.contains(itemClickedID)) {

                        //MenuItem thisMenuItem = mMenuItems.get(position);
                        //thisMenuItem.setQuantity(1);

                        //System.out.println(thisMenuItem.getId());
                        //mCartItems.add(thisMenuItem);
                        //mCartItems.get(position).setQuantity(1);
                        //saveData();
                   // }else {
                        //add the clicked item to the cart
                      //  mCartItems.add(mMenuItems.get(position));
                      //  //mCartItems.get(position).incrementQuantity();

                //    }
              //  }

                //Used for checking cart items are being updated in Logcat
                //for (int i = 0; i < mCartItems.size(); i++)
                   // Log.d("Cart", String.valueOf(mCartItems));


               // if (mCartItems.get(position) == mMenuItems.get(position))
                  //  Toast.makeText(BrowseMenu.this,    "item added to cart",
                           // Toast.LENGTH_LONG).show();


                    //SharedPrefManager.getInstance(BrowseMenu.this).saveData(mCartItems);
           // }
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


    private void openCart() {
        Intent intent = new Intent(this, CartActivity.class );
        startActivity(intent);
    }
}
