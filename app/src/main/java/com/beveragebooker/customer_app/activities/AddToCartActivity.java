package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.beveragebooker.customer_app.storage.SharedPrefManager.getInstance;

public class AddToCartActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    RadioGroup radioGroupMilk;
    RadioGroup radioGroupSugar;

    int userID;
    int itemID;
    String itemTitle;
    double itemPrice;
    int itemQuantity;
    String itemMilk;
    String itemSugar;

    int milk;
    int sugar;

    Button addToCartButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        linearLayout = findViewById(R.id.linearLayout);

        Intent intent = getIntent();
        itemID = intent.getIntExtra(BrowseMenu.ITEM_ID, 0);
        itemTitle = intent.getStringExtra(BrowseMenu.ITEM_NAME);
        itemPrice = intent.getDoubleExtra(BrowseMenu.ITEM_PRICE,0);

        milk = intent.getIntExtra(BrowseMenu.ITEM_MILK, 0);
        sugar = intent.getIntExtra(BrowseMenu.ITEM_SUGAR, 0);

        final User loggedUser = getInstance(AddToCartActivity.this).getUser();
        userID = loggedUser.getId();

        itemQuantity = 1;

        System.out.println("UserID: " + userID);
        System.out.println("ItemID: " + itemID);
        System.out.println("Item Name: " + itemTitle);
        System.out.println("Price: " + itemPrice);
        System.out.println("Milk: " + milk);
        System.out.println("Sugar: " + sugar);

        itemMilk = "soy milk";

        itemSugar = "sugar";
        System.out.println("Item Sugar: " + itemSugar);

        initViews(milk, sugar);
        setListeners(milk, sugar);

        addToCartButton = findViewById(R.id.addToCartButton);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .addToCart(userID, itemID, itemTitle, itemPrice, itemQuantity, itemMilk, itemSugar);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.code() == 200) {
                            Toast.makeText(AddToCartActivity.this, "Item added to cart", Toast.LENGTH_LONG).show();

                        } else if (response.code() == 403) {
                            Toast.makeText(AddToCartActivity.this, "Item already in cart",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(AddToCartActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

    }

    private void initViews(int milk, int sugar) {

        if (milk == 1) {

            View milkView = getLayoutInflater().inflate(R.layout.milk_selection, null, false);
            linearLayout.addView(milkView);
        }
        if (sugar == 1) {
            View sugarView = getLayoutInflater().inflate(R.layout.sugar_selection, null, false);
            linearLayout.addView(sugarView);
        }
    }

    private void setListeners(int milk, int sugar) {

        if (milk == 1) {
            radioGroupMilk = findViewById(R.id.milkRadioGroup);
            radioGroupMilk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.fullCream) {
                        String milkSelection = "full cream milk";
                        System.out.println(milkSelection);
                    }

                    if (checkedId == R.id.skim) {
                        String milkSelection = "skim milk";
                        System.out.println(milkSelection);
                    }

                    if (checkedId == R.id.almond) {
                        String milkSelection = "almond milk";
                        System.out.println(milkSelection);
                    }

                    if (checkedId == R.id.soy) {
                        String milkSelection = "soy milk";
                        System.out.println(milkSelection);
                    }
                }
            });

        }
        if (sugar == 1) {
            radioGroupSugar = findViewById(R.id.sugarRadioGroup);
            radioGroupSugar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.sugar) {
                        String sugarSelection = "sugar";
                        System.out.println(sugarSelection);
                    }

                    if (checkedId == R.id.sweetener) {
                        String sugarSelection = "sweetener";
                        System.out.println(sugarSelection);
                    }

                    if (checkedId == R.id.noSugar) {
                        String sugarSelection = "no sugar/sweetener";
                        System.out.println(sugarSelection);
                    }
                }
            });
        }
    }
}



