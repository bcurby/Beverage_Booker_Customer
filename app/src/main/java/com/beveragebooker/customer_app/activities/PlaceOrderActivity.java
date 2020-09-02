package com.beveragebooker.customer_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.adapters.CartAdapter;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.MenuItem;
import com.beveragebooker.customer_app.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.SourceTypeModel;
import com.stripe.android.model.StripeIntent;
import com.stripe.android.view.CardInputWidget;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.beveragebooker.customer_app.storage.SharedPrefManager.*;


public class PlaceOrderActivity extends AppCompatActivity {

    private String streetNumber, streetName;
    private int deliveryStatusInt;
    private boolean deliveryStatus;

    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;

    private List<MenuItem> cartItemList;

    private TextView orderTotal;
    private String orderTotalCreditCard;
    private double doubleOrderTotal;

    //Stripe
    private Stripe stripe;

    //Payment hosting URL on Heroku
    private static final String BACKEND_URL = "https://stripe-payment-beverage.herokuapp.com/";

    //Local payment server
    //private static final String BACKEND_URL = "http://10.0.2.2:5001/";

    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    Button placeOrderButton;

    DecimalFormat currency = new DecimalFormat("###0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Intent intent = getIntent();

        streetNumber = intent.getStringExtra(BookDeliveryActivity.STREET_NUMBER);
        streetName = intent.getStringExtra(BookDeliveryActivity.STREET_NAME);

        //Assigns delivery status int and order total variable
        deliveryStatus = intent.getBooleanExtra(BookDeliveryActivity.DELIVERY_STATUS, false);
        System.out.println(deliveryStatus);
        if(deliveryStatus == true) {
            deliveryStatusInt = 1;
            orderTotalCreditCard = intent.getStringExtra(BookDeliveryActivity.CART_TOTAL_BOOK_DELIVERY);
            doubleOrderTotal = Double.parseDouble(orderTotalCreditCard);
            System.out.println("Credit Card Double Delivery: " + doubleOrderTotal);
        } else {
            deliveryStatusInt = 0;
            orderTotalCreditCard = intent.getStringExtra(SelectOrderTypeActivity.CART_TOTAL_ORDER_TYPE);
            doubleOrderTotal = Double.parseDouble(orderTotalCreditCard);
            System.out.println("Credit Card Double Pick Up: " + doubleOrderTotal);
        }

        placeOrderButton = findViewById(R.id.placeOrderButton);

        //Recyclerview
        cartItemList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.placeOrderRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCartAdapter = new CartAdapter(cartItemList);

        mRecyclerView.setAdapter(mCartAdapter);

        orderTotal = findViewById(R.id.orderTotal);

        User loggedUser = getInstance(PlaceOrderActivity.this).getUser();
        int userID = loggedUser.getId();

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCartItems(userID);

        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {

                //Cart items are retrieved from the database
                if (response.code() == 201) {
                    for (int i = 0; i < response.body().size(); i++) {
                        cartItemList.add(response.body().get(i));
                    }
                    mCartAdapter.notifyDataSetChanged();
                }
                //Display the total of the items in the order
                orderTotal.setText("Order Total: $" + currency.format(getOrderTotal()));

            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {

            }
        });

        //Stripe
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51HBYC3D60uGVgHNvX5hU4dzssEi3eaqSWgxCHvqVBzYFBc8eXshDn7AdKFjWTJz2d73NCQMOrhLlSrNR7yEIdFqc00fc9JtpVH") //Your publishable key
        );
        //call check out
        startCheckout();
    }

    private void startCheckout() {

        //Total is multiplied by 100 as currency is passed to payment gateway in cents
        int total = (int) Math.rint(doubleOrderTotal * 100);
        System.out.println(total);
        Map<String, Object> payMap = new HashMap<>();
        Map<String, Object> itemMap = new HashMap<>();
        List<Map<String,Object>> itemList =new ArrayList<>();
        payMap.put("currency","aud");
        itemMap.put("id","cafe_order");
        itemMap.put("amount", total);
        itemList.add(itemMap);
        payMap.put("items", itemList);
        String json = new Gson().toJson(payMap);
        Log.i("TAG", "startCheckout: "+json);

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "create-payment-intent")
                .post(body)
                .build();

        httpClient.newCall(request)
                .enqueue(new PayCallBack(this));


        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                if (params != null) {
                    ConfirmPaymentIntentParams confirmPaymentIntentParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                    stripe.confirmPayment(PlaceOrderActivity.this, confirmPaymentIntentParams);
                }
            }
        });
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);

        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }

    //Once payment is started, It will call onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallBack(this));
    }

    private void onPaymentSuccess(@NonNull final okhttp3.Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );

        paymentIntentClientSecret = responseMap.get("clientSecret");
    }

    //Ok http call back
    private static final class PayCallBack implements okhttp3.Callback {
        @NonNull private final WeakReference<PlaceOrderActivity> activityWeakReference;

        PayCallBack(@NonNull PlaceOrderActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
            final PlaceOrderActivity activity = activityWeakReference.get();
            Log.e("TAG", "onFailure: "+e.getMessage());
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
                    ).show()
            );
        }

        @Override
        public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
            final PlaceOrderActivity activity = activityWeakReference.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }

    private final class PaymentResultCallBack implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<PlaceOrderActivity> activityWeakReference;

        PaymentResultCallBack(@NonNull PlaceOrderActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onError(@NotNull Exception e) {
            final PlaceOrderActivity activity = activityWeakReference.get();
            if (activity == null) {
                return;
            }

            //Payment request failed
            activity.displayAlert("Error", e.toString());
        }

        @Override
        public void onSuccess(PaymentIntentResult paymentIntentResult) {
            final PlaceOrderActivity activity = activityWeakReference.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = paymentIntentResult.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {

                System.out.println(deliveryStatus);
                //Payment completed successfully
                //Create Delivery in Database and Place Order

                if (deliveryStatus == true) {
                    System.out.println(deliveryStatus);
                    createNewDelivery();
                    placeOrder();
                } else {
                    placeOrder();
                }

                //Displays payment details confirmation alert for testing purposes
                //Gson gson = new GsonBuilder().setPrettyPrinting().create();

                //If payment is successful there will be details in log and UI
                //Log.i("TAG", "onSuccess:Payment " + gson.toJson(paymentIntent));
                //activity.displayAlert(
                //"Payment completed",
                // gson.toJson(paymentIntent)
                //);

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                //Payment failed
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }
    }

    //Places the order in the beverage database after payment is confirmed
    private void placeOrder() {

        final User loggedUser = getInstance(PlaceOrderActivity.this).getUser();
        int userID = loggedUser.getId();

        double orderTotal = getOrderTotal();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .placeOrder(userID, deliveryStatusInt, orderTotal);

        System.out.println(userID);
        System.out.println(deliveryStatus);
        System.out.println(orderTotal);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 201) {
                    int orderID = response.message().indexOf(2);
                    Log.d("GetOrder", String.valueOf(orderID));
                    Intent intent = new Intent(PlaceOrderActivity.this, OrderConfirmationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                } else if (response.code() == 422) {
                    Toast.makeText(PlaceOrderActivity.this, "There was a problem placing your order",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PlaceOrderActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //Creates a new delivery entry in the beverage database after payment is confirmed
    private void createNewDelivery() {
        User loggedUser = getInstance(PlaceOrderActivity.this).getUser();
        int userID = loggedUser.getId();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .bookDelivery(userID, streetNumber, streetName);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(PlaceOrderActivity.this, "Delivery Submitted", Toast.LENGTH_LONG).show();
                }else if (response.code() == 422) {
                    Toast.makeText(PlaceOrderActivity.this, "Delivery Failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PlaceOrderActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //getter method for returning the order total
    private double getOrderTotal() {

        double orderTotal = 0;

        for (int i = 0; i < cartItemList.size(); i++) {

            int quantity = cartItemList.get(i).getQuantity();
            System.out.println("Quantity: " + quantity);

            double price = cartItemList.get(i).getPrice();
            System.out.println("Price: " + price);

            double itemTotal = quantity * price;
            System.out.println("Item Total: " + itemTotal);

            orderTotal += itemTotal;
            System.out.println("Cart Total: " + orderTotal);

        }
        return orderTotal;
    }
}