package com.beveragebooker.customer_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.activities.BrowseMenu;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

import es.dmoral.toasty.Toasty;

public class PrimaryMenu extends AppCompatActivity {

    public static final String ITEM_TYPE_MENU = "com.beveragebooker.customer_app.ITEM_TYPE_MENU";

    private Button foodMenuButton;
    private Button drinkMenuButton;

    private String itemType;

    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_menu);

        foodMenuButton = findViewById(R.id.foodMenuButton);
        foodMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseFoodMenu();
            }
        });

        drinkMenuButton = findViewById(R.id.drinkMenuButton);
        drinkMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseDrinkMenu();
            }
        });
    }

    private void openBrowseFoodMenu() {
        Intent intent = new Intent(this, BrowseMenu.class);
        itemType = "food";
        intent.putExtra(ITEM_TYPE_MENU, itemType);
        startActivity(intent);
    }

    //open to the next activity on button click
    public void openBrowseDrinkMenu() {
        Intent intent = new Intent(this, BrowseMenu.class);
        itemType = "drink";
        intent.putExtra(ITEM_TYPE_MENU, itemType);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toasty.Config.getInstance()
                .setTextSize(20)
                .apply();
        Toast toast = Toasty.info(PrimaryMenu.this, "Please press BACK again to exit", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 750);
        toast.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 3500);
    }
}

