package com.beveragebooker.customer_app.activities;

import android.content.Intent;
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

    public static final String STREET_NUMBER_PAYMENT = "com.beveragebooker.customer_app.STREET_NUMBER_PAYMENT";
    public static final String STREET_NAME_PAYMENT = "com.beveragebooker.customer_app.STREET_NAME_PAYMENT";
    public static final String DELIVERY_STATUS_PAYMENT = "com.beveragebooker.customer_app.DELIVERY_STATUS_PAYMENT";

    private EditText editTextStreetNumber, editTextStreetName;
    //

    private Button ProceedToPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_delivery);

        editTextStreetNumber = findViewById(R.id.editTextStreetNumber);
        editTextStreetName = findViewById(R.id.editTextStreetName);
        //textViewPostCode = findViewById(R.id.textViewPostCode);
        //textViewCityTown = findViewById(R.id.textViewCityTown);

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


        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(STREET_NUMBER_PAYMENT, streetNumber);
        intent.putExtra(STREET_NAME_PAYMENT, streetName);
        //intent.putExtra(POST_CODE_PAYMENT, postCode);
        //intent.putExtra(CITY_TOWN_PAYMENT, cityTown);
        intent.putExtra(DELIVERY_STATUS_PAYMENT, deliveryStatus);
        startActivity(intent);
    }
}