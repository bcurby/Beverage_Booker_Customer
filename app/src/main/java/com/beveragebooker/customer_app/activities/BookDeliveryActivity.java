package com.beveragebooker.customer_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.models.User;

import static com.beveragebooker.customer_app.storage.SharedPrefManager.getInstance;

public class BookDeliveryActivity extends AppCompatActivity {

    public static final String STREET_NUMBER = "com.beveragebooker.customer_app.STREET_NUMBER";
    public static final String STREET_NAME = "com.beveragebooker.customer_app.STREET_NAME";
    public static final String DELIVERY_STATUS = "com.beveragebooker.customer_app.DELIVERY_STATUS";

    public static String CART_TOTAL_BOOK_DELIVERY = "com.beveragebooker.customer_app.CART_TOTAL_BOOK_DELIVERY";

    private EditText editTextStreetNumber, editTextStreetName;

    private String orderTotal;

    private Button ProceedToPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_delivery);

        Intent intent = getIntent();
        orderTotal = intent.getStringExtra(SelectOrderTypeActivity.CART_TOTAL_ORDER_TYPE);

        editTextStreetNumber = findViewById(R.id.editTextStreetNumber);
        editTextStreetName = findViewById(R.id.editTextStreetName);

        ProceedToPaymentButton = findViewById(R.id.ProceedToPaymentButton);
        ProceedToPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewDelivery();
            }
        });
    }

    private void createNewDelivery() {
        boolean checker = false;

        User loggedUser = getInstance(BookDeliveryActivity.this).getUser();
        int userID = loggedUser.getId();
        String streetNumber = editTextStreetNumber.getText().toString().trim();
        String streetName = editTextStreetName.getText().toString().trim();

        while (checker == false) {


            if (streetNumber.isEmpty()) {
                editTextStreetNumber.setError("Street number field must contain a street number");
                return;
            } else if (streetName.isEmpty()) {
                editTextStreetName.setError("Street name field must contain a street name");
                return;
            } else {
                checker = true;
            }
        }
        goToPayment();
    }

    private void goToPayment() {
        String streetNumber = editTextStreetNumber.getText().toString().trim();
        String streetName = editTextStreetName.getText().toString().trim();
        boolean deliveryStatus = true;


        Intent intent = new Intent(this, PlaceOrderActivity.class);
        intent.putExtra(STREET_NUMBER, streetNumber);
        intent.putExtra(STREET_NAME, streetName);
        intent.putExtra(DELIVERY_STATUS, deliveryStatus);

        intent.putExtra(CART_TOTAL_BOOK_DELIVERY, orderTotal);
        startActivity(intent);
    }
}
