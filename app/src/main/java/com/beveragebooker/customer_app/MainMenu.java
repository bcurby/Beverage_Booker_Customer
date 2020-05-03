package com.beveragebooker.customer_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity implements View.OnClickListener  {

    private Button menuLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        menuLink = (Button) findViewById(R.id.menuLink);

        menuLink. setOnClickListener(this);
//        setContentView(R.layout.activity_profile);
//        if (!SharePrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//
//        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
//        textViewUserEmail = (TextView) findViewById(R.id.textViewUseremail);
//
//        textViewUserEmail.setText(SharePrefManager.getInstance(this).getUserEmail());
//        textViewUsername.setText(SharePrefManager.getInstance(this).getUserName());

    }
    @Override
    public void onClick(View view) {

        if (view == menuLink){

            startActivity(new Intent(getApplicationContext(),
                    BrowseMenu.class));


        }
    }

}
