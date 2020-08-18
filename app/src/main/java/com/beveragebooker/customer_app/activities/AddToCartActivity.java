package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    CheckBox vanilla;
    CheckBox caramel;
    CheckBox chocolate;
    CheckBox whippedCream;

    int userID;
    int itemID;
    String itemTitle;
    double itemPrice;
    int itemQuantity;
    String itemMilk;
    String itemSugar;
    String itemVanilla;
    String itemCaramel;
    String itemChocolate;
    String itemWhippedCream;
    String itemFrappe;
    String itemHeated;

    int milkStatus;
    int sugarStatus;
    int extrasStatus;
    int frappeStatus;
    int heatedStatus;

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

        milkStatus = intent.getIntExtra(BrowseMenu.ITEM_MILK, 0);
        sugarStatus = intent.getIntExtra(BrowseMenu.ITEM_SUGAR, 0);
        extrasStatus = intent.getIntExtra(BrowseMenu.ITEM_EXTRAS, 0);
        frappeStatus = intent.getIntExtra(BrowseMenu.ITEM_FRAPPE, 0);
        heatedStatus = intent.getIntExtra(BrowseMenu.ITEM_HEATED, 0);

        final User loggedUser = getInstance(AddToCartActivity.this).getUser();
        userID = loggedUser.getId();

        itemQuantity = 1;

        System.out.println("UserID: " + userID);
        System.out.println("ItemID: " + itemID);
        System.out.println("Item Name: " + itemTitle);
        System.out.println("Price: " + itemPrice);
        System.out.println("Milk: " + milkStatus);
        System.out.println("Sugar: " + sugarStatus);
        System.out.println("Extras: " + extrasStatus);
        System.out.println("Frappe: " + frappeStatus);
        System.out.println("Heated: " + heatedStatus);

        itemMilk = "-";
        itemSugar = "-";
        itemVanilla = "-";
        itemCaramel = "-";
        itemChocolate = "-";
        itemWhippedCream = "-";
        itemFrappe = "-";
        itemHeated = "-";

        System.out.println("Item Milk: " + itemMilk);
        System.out.println("Item Sugar: " + itemSugar);
        System.out.println("Item Frappe: " + itemFrappe);
        System.out.println("Item Heated: " + itemHeated);

        initViews(milkStatus, sugarStatus, extrasStatus);
        setListeners(milkStatus, sugarStatus, extrasStatus);

        addToCartButton = findViewById(R.id.addToCartButton);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Item Milk: " + itemMilk);
                System.out.println("Item Sugar: " + itemSugar);

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .addToCart(userID, itemID, itemTitle, itemPrice, itemQuantity, itemMilk,
                                itemSugar, itemVanilla, itemCaramel, itemChocolate,
                                itemWhippedCream, itemFrappe, itemHeated);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.code() == 200) {
                            Toast.makeText(AddToCartActivity.this, "Item added to cart", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddToCartActivity.this, BrowseMenu.class);
                            startActivity(intent);

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

    private void initViews(int milkStatus, int sugarStatus, int extrasStatus) {

        if (milkStatus == 1) {

            View milkView = getLayoutInflater().inflate(R.layout.milk_selection, null, false);
            linearLayout.addView(milkView);
        }

        if (sugarStatus == 1) {
            View sugarView = getLayoutInflater().inflate(R.layout.sugar_selection, null, false);
            linearLayout.addView(sugarView);
        }

        if (extrasStatus == 1) {
            View extrasView = getLayoutInflater().inflate(R.layout.extras_selection, null, false);
            linearLayout.addView(extrasView);
        }
    }

    private void setListeners(int milkStatus, int sugarStatus, int extrasStatus) {

        if (milkStatus == 1) {
            radioGroupMilk = findViewById(R.id.milkRadioGroup);
            radioGroupMilk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.fullCream) {
                        itemMilk = "full cream milk";
                        System.out.println(itemMilk);
                    }

                    if (checkedId == R.id.skim) {
                        itemMilk = "skim milk";
                        System.out.println(itemMilk);
                    }

                    if (checkedId == R.id.almond) {
                        itemMilk = "almond milk";
                        System.out.println(itemMilk);
                    }

                    if (checkedId == R.id.soy) {
                        itemMilk = "soy milk";
                        System.out.println(itemMilk);
                    }
                }
            });
        }

        if (sugarStatus == 1) {
            radioGroupSugar = findViewById(R.id.sugarRadioGroup);
            radioGroupSugar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.sugar) {
                        itemSugar = "sugar";
                        System.out.println(itemSugar);
                    }

                    if (checkedId == R.id.sweetener) {
                        itemSugar = "sweetener";
                        System.out.println(itemSugar);
                    }

                    if (checkedId == R.id.noSugar) {
                        itemSugar = "no sugar/sweetener";
                        System.out.println(itemSugar);
                    }
                }
            });
        }

        if (extrasStatus == 1) {
            vanilla = findViewById(R.id.vanilla);
            vanilla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vanilla.isChecked()) {
                        itemVanilla = "vanilla";
                        System.out.println(itemVanilla);
                    }
                }
            });

            caramel = findViewById(R.id.caramel);
            caramel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (caramel.isChecked()) {
                        itemCaramel = "caramel";
                        System.out.println(itemCaramel);
                    }
                }
            });

            chocolate = findViewById(R.id.chocolate);
            chocolate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chocolate.isChecked()) {
                        itemChocolate = "chocolate";
                        System.out.println(itemChocolate);
                    }
                }
            });

            whippedCream = findViewById(R.id.whippedCream);
            whippedCream.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (whippedCream.isChecked()) {
                        itemWhippedCream = "whipped cream";
                        System.out.println(itemWhippedCream);
                    }
                }
            });
        }
    }
}



