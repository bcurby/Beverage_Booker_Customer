package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.models.User;
import com.beveragebooker.customer_app.notifications.NotificationOutput;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

import java.util.Objects;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView orderConfirmTextView;
    private Button returnToMainMenuButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        orderConfirmTextView = findViewById(R.id.textViewOrderConfirm);
        returnToMainMenuButton = findViewById(R.id.returnToMainMenu);

        User user = SharedPrefManager.getInstance(this).getUser();

        orderConfirmTextView.setText("Thank you for your order, " + user.getFirstName() + "."
                + "\nYour order will be ready shortly.");

        String title = "Order Ready";
        String body = user.getFirstName() + " your order is ready to enjoy";

        int userID = user.getId();

        //add the call for the completed order notification
        NotificationOutput.displayNotification(this, title, body, userID);



        returnToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPrimaryMenu();
            }
        });
    }

    private void openPrimaryMenu() {
        Intent intent = new Intent(this, PrimaryMenu.class);
        startActivity(intent);
    }
}
