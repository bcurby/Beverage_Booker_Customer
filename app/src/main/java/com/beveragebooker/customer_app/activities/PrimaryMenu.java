package com.beveragebooker.customer_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.activities.BrowseMenu;

public class PrimaryMenu extends AppCompatActivity {

    public static final String ITEM_TYPE_MENU = "com.beveragebooker.customer_app.ITEM_TYPE_MENU";

    private Button menuLink;
    private Button foodMenuButton;
    private Button viewOrderButton;
    private Button viewHelpButton;

    private String itemType;


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

        menuLink = findViewById(R.id.menuLink);
        menuLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseMenu();
            }
        });

        viewOrderButton = findViewById(R.id.viewOrderButton);
        viewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewOrder();
            }
        });

        viewHelpButton = findViewById(R.id.viewHelpButton);
        viewHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelp();
            }
        });

    }

    private void openHelp() {
        Intent intent = new Intent(this, HelpActivity.class );
        startActivity(intent);
    }

    private void openBrowseFoodMenu() {
        Intent intent = new Intent(this, BrowseMenu.class );
        itemType = "food";
        intent.putExtra(ITEM_TYPE_MENU, itemType);
        startActivity(intent);
    }

    //open to the next activity on button click
    public void openBrowseMenu(){
        Intent intent = new Intent(this, BrowseMenu.class );
        itemType = "drink";
        intent.putExtra(ITEM_TYPE_MENU, itemType);
        startActivity(intent);
    }

    private void openViewOrder() {
        Intent intent = new Intent(this, OrderConfirmationActivity.class );
        startActivity(intent);
    }



}

