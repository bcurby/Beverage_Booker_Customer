package com.beveragebooker.customer_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.activities.BrowseMenu;

public class PrimaryMenu extends AppCompatActivity {

    private Button menuLink;
    private Button foodMenuButton;


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

    }

    private void openBrowseFoodMenu() {
        Intent intent = new Intent(this, BrowseFoodMenu.class );
        startActivity(intent);
    }

    //open to the next activity on button click
    public void openBrowseMenu(){
        Intent intent = new Intent(this, BrowseMenu.class );
        startActivity(intent);
    }



}

