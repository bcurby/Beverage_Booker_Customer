package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beveragebooker.customer_app.R;

public class SelectOrderTypeActivity extends AppCompatActivity {

    public static final String DELIVERY_STATUS = "com.beveragebooker.customer_app.DELIVERY_STATUS";

    private Button PickUpButton;
    private Button DeliveryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order_type);

        //PickUp Button
        PickUpButton = findViewById(R.id.PickUpButton);
        PickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPayment();
            }
        });

        //Delivery Button
        DeliveryButton = findViewById(R.id.DeliveryButton);
        DeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBookDelivery();
            }
        });

    }

    private void goToPayment() {
        Intent intent = new Intent(this, PaymentActivity.class );
        startActivity(intent);
    }

    private void goToBookDelivery() {
        Intent intent = new Intent(this, BookDeliveryActivity.class );
        startActivity(intent);
    }

}
