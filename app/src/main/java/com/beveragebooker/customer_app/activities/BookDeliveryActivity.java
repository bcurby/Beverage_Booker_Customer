package com.beveragebooker.customer_app.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.models.User;

import static com.beveragebooker.customer_app.storage.SharedPrefManager.getInstance;

public class BookDeliveryActivity extends AppCompatActivity {

    public static final String STREET_UNIT = "com.beveragebooker.customer_app" +
            ".STREET_NUMBER";
    public static final String STREET_NAME = "com.beveragebooker.customer_app.STREET_NAME";
    public static final String DELIVERY_STATUS = "com.beveragebooker.customer_app.DELIVERY_STATUS";

    public static String CART_TOTAL_BOOK_DELIVERY = "com.beveragebooker.customer_app.CART_TOTAL_BOOK_DELIVERY";

    private EditText editTextUnitNumber, editTextStreetName;

    private String orderTotal;

    private Button ProceedToPaymentButton;

    TextView nedHelpBookDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_delivery);

        Intent intent = getIntent();
        orderTotal = intent.getStringExtra(SelectOrderTypeActivity.CART_TOTAL_ORDER_TYPE);

        editTextUnitNumber = findViewById(R.id.editTextUnit);
        editTextStreetName = findViewById(R.id.editTextStreetName);

        ProceedToPaymentButton = findViewById(R.id.ProceedToPaymentButton);
        ProceedToPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewDelivery();
            }
        });

        nedHelpBookDelivery = findViewById(R.id.textViewNeedHelpBookDelivery);
        nedHelpBookDelivery.setOnClickListener(v -> {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/DI7c75-eGwQ?t=202"));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://youtu.be/DI7c75-eGwQ?t=202"));
            try {
                BookDeliveryActivity.this.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                BookDeliveryActivity.this.startActivity(webIntent);
            }
        });
    }

    private void createNewDelivery() {
        boolean checker = false;

        User loggedUser = getInstance(BookDeliveryActivity.this).getUser();
        int userID = loggedUser.getId();
        String streetName = editTextStreetName.getText().toString().trim();

        while (!checker) {

            if (streetName.isEmpty()) {
                editTextStreetName.setError("Street name field must contain a street name");
                return;
            }
            if(!streetName.matches("^[a-zA-Z0-9 ]+")){
                editTextStreetName.setError("Street name field cant contain any symbols");

                return;
            } else {
                checker = true;
            }
        }
        goToPayment();
    }

    private void goToPayment() {
        String streetUnit = editTextUnitNumber.getText().toString().trim();
        String streetName = editTextStreetName.getText().toString().trim();

        Intent intent = new Intent(this, PlaceOrderActivity.class);
        intent.putExtra(STREET_UNIT, streetUnit);
        intent.putExtra(STREET_NAME, streetName);
        //delivery status true
        intent.putExtra(DELIVERY_STATUS, true);

        intent.putExtra(CART_TOTAL_BOOK_DELIVERY, orderTotal);
        startActivity(intent);
    }
}