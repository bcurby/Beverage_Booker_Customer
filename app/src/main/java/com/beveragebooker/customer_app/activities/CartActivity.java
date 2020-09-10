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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.beveragebooker.customer_app.storage.SharedPrefManager.getInstance;


public class CartActivity extends AppCompatActivity {

    public static String CART_TOTAL = "com.beveragebooker.customer_app.CART_TOTAL";
    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;
    private ArrayList<MenuItem> cartItemList;
    MenuItem itemClicked;
    int id;

    String itemTitle, itemSize, itemMilk, itemSugar, itemDecaf, itemVanilla, itemCaramel, itemChocolate,
            itemWhippedCream, itemFrappe, itemHeated, itemComment, itemType;

    double itemPrice;
    int itemQuantity;

    private TextView cartTotal;
    private Button checkoutButton;
    private Button emptyCartButton;

    DecimalFormat currency = new DecimalFormat("###0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mRecyclerView = findViewById(R.id.cartRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItemList = new ArrayList<>();
        mCartAdapter = new CartAdapter(cartItemList);
        mRecyclerView.setAdapter(mCartAdapter);
        cartTotal = findViewById(R.id.cartTotal);

        //delete button
        mCartAdapter.setOnButtonClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                itemClicked = cartItemList.get(position);
                //ItemID
                id = itemClicked.getId();
                itemTitle = cartItemList.get(position).getName();
                itemPrice = cartItemList.get(position).getPrice();
                itemSize = cartItemList.get(position).getItemSize();
                itemMilk = cartItemList.get(position).getItemMilk();
                itemSugar = cartItemList.get(position).getItemSugar();
                itemDecaf = cartItemList.get(position).getItemDecaf();
                itemVanilla = cartItemList.get(position).getItemVanilla();
                itemCaramel = cartItemList.get(position).getItemCaramel();
                itemChocolate = cartItemList.get(position).getItemChocolate();
                itemWhippedCream = cartItemList.get(position).getItemWhippedCream();
                itemFrappe = cartItemList.get(position).getItemFrappe();
                itemHeated = cartItemList.get(position).getItemHeated();
                itemComment = cartItemList.get(position).getItemComment();
                itemType = cartItemList.get(position).getItemType();
                itemQuantity = cartItemList.get(position).getQuantity();
                System.out.println("ItemID: " + id);
                System.out.println("ItemTitle: " + itemTitle);
                deleteCartItem();
            }
        });

        //Checkout Button
        checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCartTotal() > 0) {
                    goToCheckout();
                } else {
                    Toast.makeText(CartActivity.this,
                            "Please add an item to your cart before clicking Checkout", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //EmptyCart Button
        emptyCartButton = findViewById(R.id.emptyCartButton);
        emptyCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCartTotal() > 0) {
                    emptyTheCart();
                    //goToMenu();
                } else {
                    Toast.makeText(CartActivity.this,
                            "There are no items in the cart to empty", Toast.LENGTH_SHORT).show();
                }
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
                if (response.code() == 201) {
                    for (int i = 0; i < response.body().size(); i++) {
                        cartItemList.add(response.body().get(i));
                    }
                    mCartAdapter.notifyDataSetChanged();

                //Cart is empty
                } else if (response.code() == 303) {
                    Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                }
                //Display the total of items in the cart
                cartTotal.setText("Cart Total: $" + currency.format(getCartTotal()));
                CART_TOTAL = String.valueOf(getCartTotal());
                System.out.println("CART TOTAL: " + CART_TOTAL);
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

    public void emptyTheCart() {
        System.out.println("testEmptyTheCartSuccess"); // works
        final User loggedUser = getInstance(CartActivity.this).getUser();
        int userID = loggedUser.getId();
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .emptyCart(userID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 201) {
                    Toast.makeText(CartActivity.this, "Cart emptied", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CartActivity.this, CartActivity.class);
                    startActivity(intent);

                } else if (response.code() == 402) {
                    Toast.makeText(CartActivity.this, "Cart failed to empty",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(CartActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void goToCheckout() {
        String orderTotal = String.valueOf(getCartTotal());
        System.out.println("Order Test: " +orderTotal);
        Intent intent = new Intent(this, SelectOrderTypeActivity.class );
        intent.putExtra(CART_TOTAL, orderTotal);

        startActivity(intent);
    }

    private void goToMenu() {
        Intent intent = new Intent(this, BrowseMenu.class );
        startActivity(intent);
    }

    private void deleteCartItem() {
        final User loggedUser = getInstance(CartActivity.this).getUser();
        int userID = loggedUser.getId();
        System.out.println(userID);
        System.out.println(itemTitle);
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .deleteCartItem(id, itemTitle, itemPrice, itemSize, itemMilk, itemSugar,
                        itemDecaf, itemVanilla, itemCaramel, itemChocolate, itemWhippedCream, itemFrappe,
                        itemHeated, itemComment, itemType, userID, itemQuantity);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(CartActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();
                } else if (response.code() == 402) {
                    Toast.makeText(CartActivity.this, "Item Failed To Delete", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println(response.code());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
