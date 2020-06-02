package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.beveragebooker.customer_app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PaymentActivity extends AppCompatActivity {

    public static final String CREDIT_CARD_NUMBER = "com.beveragebooker.customer_app.CREDIT_CARD_NUMBER";
    public static final String CREDIT_CARD_CVV = "com.beveragebooker.customer_app.CREDIT_CARD_CVV";
    public static final String CREDIT_CARD_EXPIRY_MONTH = "com.beveragebooker.customer_app.CREDIT_CARD_EXPIRY_MONTH";
    public static final String CREDIT_CARD_EXPIRY_YEAR = "com.beveragebooker.customer_app.CREDIT_CARD_EXPIRY_YEAR";

    private EditText editTextCreditCardNumber, editTextCVV, editTextExpiryMonth, editTextExpiryYear;
    private Button openPlaceOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        editTextCreditCardNumber = findViewById(R.id.editTextCreditCardNumber);
        editTextCVV = findViewById(R.id.editTextCVV);
        editTextExpiryMonth = findViewById(R.id.editTextExpiryMonth);
        editTextExpiryYear = findViewById(R.id.editTextExpiryYear);

        //Continue button to open the Place Order (order confirmation) screen
        openPlaceOrderButton = findViewById(R.id.openPlaceOrderButton);
        openPlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlaceOrder();
            }
        });
    }

    private void openPlaceOrder() {

        int creditCardNumber = Integer.parseInt(editTextCreditCardNumber.getText().toString().trim());
        int creditCardCCV = Integer.parseInt(editTextCVV.getText().toString().trim());
        int expiryMonth = Integer.parseInt(editTextExpiryMonth.getText().toString().trim());
        int expiryYear = Integer.parseInt(editTextExpiryYear.getText().toString().trim());

        Intent intent = new Intent(this, PlaceOrderActivity.class );
        intent.putExtra(CREDIT_CARD_NUMBER, creditCardNumber);
        intent.putExtra(CREDIT_CARD_CVV, creditCardCCV);
        intent.putExtra(CREDIT_CARD_EXPIRY_MONTH, expiryMonth);
        intent.putExtra(CREDIT_CARD_EXPIRY_YEAR, expiryYear);
        startActivity(intent);
    }
}
