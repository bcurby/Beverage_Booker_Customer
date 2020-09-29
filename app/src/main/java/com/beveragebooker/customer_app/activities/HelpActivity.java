package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.beveragebooker.customer_app.R;

public class HelpActivity extends AppCompatActivity {
    private CardView cardViewHelpCreateUser;
    private CardView cardViewHelpLogin;
    private CardView cardViewHelpFillCart;
    private CardView cardViewHelpEmptyCart;
    private CardView cardViewHelpPlaceOrder;
    private CardView cardViewHelpBookDelivery;
    private CardView cardViewHelpUpdateUser;
    private CardView cardViewHelpDeleteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        cardViewHelpCreateUser = findViewById(R.id.cardViewHelp);
        cardViewHelpCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateUserLink();
            }
        });

        cardViewHelpLogin = findViewById(R.id.cardViewHelp2);
        cardViewHelpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginLink();
            }
        });

        cardViewHelpFillCart = findViewById(R.id.cardViewHelp3);
        cardViewHelpFillCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFillCartLink();
            }
        });

        cardViewHelpEmptyCart = findViewById(R.id.cardViewHelp4);
        cardViewHelpEmptyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmptyCartLink();
            }
        });

        cardViewHelpPlaceOrder = findViewById(R.id.cardViewHelp5);
        cardViewHelpPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlaceOrderLink();
            }
        });

        cardViewHelpBookDelivery = findViewById(R.id.cardViewHelp6);
        cardViewHelpBookDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBookDeliveryLink();
            }
        });

        cardViewHelpUpdateUser = findViewById(R.id.cardViewHelp7);
        cardViewHelpUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateUserLink();
            }
        });

        cardViewHelpDeleteUser = findViewById(R.id.cardViewHelp8);
        cardViewHelpDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteUserLink();
            }
        });
    }

    private void openCreateUserLink(){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=DI7c75-eGwQ&feature=youtu.be"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=DI7c75-eGwQ&feature=youtu.be"));
        try {
            HelpActivity.this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            HelpActivity.this.startActivity(webIntent);
        }
    }

    private void openLoginLink(){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/DI7c75-eGwQ?t=63"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://youtu.be/DI7c75-eGwQ?t=63"));
        try {
            HelpActivity.this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            HelpActivity.this.startActivity(webIntent);
        }
    }

    private void openFillCartLink(){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/DI7c75-eGwQ?t=95"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://youtu.be/DI7c75-eGwQ?t=95"));
        try {
            HelpActivity.this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            HelpActivity.this.startActivity(webIntent);
        }
    }

    private void openEmptyCartLink(){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/DI7c75-eGwQ?t=126"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://youtu.be/DI7c75-eGwQ?t=126"));
        try {
            HelpActivity.this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            HelpActivity.this.startActivity(webIntent);
        }
    }

    private void openPlaceOrderLink(){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/DI7c75-eGwQ?t=154"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://youtu.be/DI7c75-eGwQ?t=154"));
        try {
            HelpActivity.this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            HelpActivity.this.startActivity(webIntent);
        }
    }

    private void openBookDeliveryLink(){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/DI7c75-eGwQ?t=202"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://youtu.be/DI7c75-eGwQ?t=202"));
        try {
            HelpActivity.this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            HelpActivity.this.startActivity(webIntent);
        }
    }

    private void openUpdateUserLink(){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/DI7c75-eGwQ?t=260"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://youtu.be/DI7c75-eGwQ?t=260"));
        try {
            HelpActivity.this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            HelpActivity.this.startActivity(webIntent);
        }
    }

    private void openDeleteUserLink(){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/DI7c75-eGwQ?t=326"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://youtu.be/DI7c75-eGwQ?t=326"));
        try {
            HelpActivity.this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            HelpActivity.this.startActivity(webIntent);
        }
    }
}