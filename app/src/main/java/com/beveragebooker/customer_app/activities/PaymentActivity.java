package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.beveragebooker.customer_app.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PaymentActivity extends AppCompatActivity {

    public static final String CREDIT_CARD_NUMBER = "com.beveragebooker.customer_app.CREDIT_CARD_NUMBER";
    public static final String CREDIT_CARD_CVV = "com.beveragebooker.customer_app.CREDIT_CARD_CVV";
    public static final String CREDIT_CARD_EXPIRY_MONTH = "com.beveragebooker.customer_app.CREDIT_CARD_EXPIRY_MONTH";
    public static final String CREDIT_CARD_EXPIRY_YEAR = "com.beveragebooker.customer_app.CREDIT_CARD_EXPIRY_YEAR";

    private EditText editTextCreditCardNumber1, editTextCreditCardNumber2, editTextCreditCardNumber3,
            editTextCreditCardNumber4, editTextCVV, editTextExpiryMonth, editTextExpiryYear;

    private Button openPlaceOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        editTextCreditCardNumber1 = findViewById(R.id.editTextCreditCardNumber1);
        editTextCreditCardNumber2 = findViewById(R.id.editTextCreditCardNumber2);
        editTextCreditCardNumber3 = findViewById(R.id.editTextCreditCardNumber3);
        editTextCreditCardNumber4 = findViewById(R.id.editTextCreditCardNumber4);

        editTextCVV = findViewById(R.id.editTextCVV);
        editTextExpiryMonth = findViewById(R.id.editTextExpiryMonth);
        editTextExpiryYear = findViewById(R.id.editTextExpiryYear);

        openPlaceOrderButton = findViewById(R.id.openPlaceOrderButton);

        //TextWatcher
        editTextCreditCardNumber1.addTextChangedListener(paymentTextWatcher);
        editTextCreditCardNumber2.addTextChangedListener(paymentTextWatcher);
        editTextCreditCardNumber3.addTextChangedListener(paymentTextWatcher);
        editTextCreditCardNumber4.addTextChangedListener(paymentTextWatcher);

        editTextCVV.addTextChangedListener(paymentTextWatcher);
        editTextExpiryMonth.addTextChangedListener(paymentTextWatcher);
        editTextExpiryYear.addTextChangedListener(paymentTextWatcher);

        //Continue button to open the Place Order (order confirmation) screen
        openPlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCreditCardInput();
            }
        });
    }

    //Continue button is disabled by default in XML file
    //TextWatcher checks that all fields have some input before the button is enabled
    private TextWatcher paymentTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String creditCardNumberInput1 = editTextCreditCardNumber1.getText().toString().trim();
            String creditCardNumberInput2 = editTextCreditCardNumber2.getText().toString().trim();
            String creditCardNumberInput3 = editTextCreditCardNumber2.getText().toString().trim();
            String creditCardNumberInput4 = editTextCreditCardNumber2.getText().toString().trim();

            String creditCardCVVInput = editTextCVV.getText().toString().trim();
            String expiryMonthInput = editTextExpiryMonth.getText().toString().trim();
            String expiryYearInput = editTextExpiryYear.getText().toString().trim();

            openPlaceOrderButton.setEnabled(!creditCardNumberInput1.isEmpty() &&
                    !creditCardNumberInput2.isEmpty() && !creditCardNumberInput3.isEmpty() &&
                    !creditCardNumberInput4.isEmpty() && !creditCardCVVInput.isEmpty() &&
                    !expiryMonthInput.isEmpty() && !expiryYearInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void checkCreditCardInput() {

        String creditCardNumberString1 =  editTextCreditCardNumber1.getText().toString().trim();
        String creditCardNumberString2 = editTextCreditCardNumber2.getText().toString().trim();
        String creditCardNumberString3 = editTextCreditCardNumber3.getText().toString().trim();
        String creditCardNumberString4 = editTextCreditCardNumber4.getText().toString().trim();

        String creditCardLong = creditCardNumberString1 + creditCardNumberString2 + creditCardNumberString3
                + creditCardNumberString4;
        System.out.println(creditCardLong);

        String creditCardCVVString = (editTextCVV.getText().toString().trim());
        String ExpiryMonthString = (editTextExpiryMonth.getText().toString().trim());
        String ExpiryYearString = (editTextExpiryMonth.getText().toString().trim());

        int expiryMonthInt = Integer.parseInt(editTextExpiryMonth.getText().toString().trim());
        int expiryYearInt = Integer.parseInt(editTextExpiryYear.getText().toString().trim());

        if (creditCardLong.length() != 16) {
            editTextCreditCardNumber1.setError("Credit card number must be 16 digits");
            editTextCreditCardNumber1.requestFocus();
            System.out.println(creditCardLong);
            return;
        }

        if (creditCardCVVString.length() != 3) {
            editTextCVV.setError("CVV number must be 3 digits");
            editTextCVV.requestFocus();
            return;
        }

        if (ExpiryMonthString.length() != 2) {
            editTextExpiryMonth.setError("Expiry month must be 2 digits");
            editTextExpiryMonth.requestFocus();
            return;
        }

        if (expiryMonthInt > 12 || expiryMonthInt == 00) {
            editTextExpiryMonth.setError("Expiry month must be a number between 01 and 12");
            editTextExpiryMonth.requestFocus();
            return;
        }

        if (ExpiryYearString.length() != 2) {
            editTextExpiryYear.setError("Expiry year must be 2 digits");
            editTextExpiryYear.requestFocus();
            return;
        }

        if (expiryYearInt < 20) {
            editTextExpiryYear.setError("Expiry year must be a number equal to current year or greater");
            editTextExpiryYear.requestFocus();
            return;
        }

        if (expiryMonthInt < 6 && expiryYearInt == 20) {
            editTextExpiryMonth.setError("Your credit card is expired");
            editTextExpiryMonth.requestFocus();
            return;
        }

        openPlaceOrder(creditCardLong);

    }

    private void openPlaceOrder(String creditCardLong) {

        long creditCardNumber = Long.parseLong(creditCardLong.trim());
        System.out.println(creditCardLong);

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
