package com.beveragebooker.customer_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.beveragebooker.customer_app.storage.SharedPrefManager.getInstance;

public class BookDeliveryActivity extends AppCompatActivity {

    private EditText editTextCityTown, editTextStreetName, editTextStreetNumber, editTextPostCode;

    private Button ProceedToPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_delivery);

        editTextStreetNumber = findViewById(R.id.editTextStreetNumber);
        editTextStreetName = findViewById(R.id.editTextStreetName);
        editTextPostCode = findViewById(R.id.editTextPostCode);
        editTextCityTown = findViewById(R.id.editTextCityTown);

        ProceedToPaymentButton = findViewById(R.id.ProceedToPaymentButton);
        ProceedToPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewDelivery();
                goToPayment();
            }
        });
    }

    private void createNewDelivery() {
        User loggedUser = getInstance(BookDeliveryActivity.this).getUser();
        int userID = loggedUser.getId();
        String streetNumber = editTextStreetNumber.getText().toString().trim();
        String streetName = editTextStreetName.getText().toString().trim();
        int postCode = Integer.parseInt(editTextPostCode.getText().toString().trim());
        String cityTown = editTextCityTown.getText().toString().trim();


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .bookDelivery(userID, streetNumber, streetName, postCode, cityTown);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(BookDeliveryActivity.this, "Delivery Submitted", Toast.LENGTH_LONG).show();
                }else if (response.code() == 422) {
                    Toast.makeText(BookDeliveryActivity.this, "Delivery Failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(BookDeliveryActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToPayment() {
        Intent intent = new Intent(this, PaymentActivity.class );
        startActivity(intent);
    }
}
