package com.beveragebooker.customer_app.api;

import com.beveragebooker.customer_app.models.LoginResponse;
import com.beveragebooker.customer_app.models.MenuItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    //Create User entry in database
    @FormUrlEncoded
    @POST("createuser")
    Call<ResponseBody> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("phone") String phone
    );

    //Login existing user
    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    //Get Menu items from database
    @GET("getitems")
    Call<List<MenuItem>> getItems();


    //Get Cart items from database
    @GET("getcartitems")
    Call<List<MenuItem>> getCartItems(
            @Query("userID") int userID
    );


    //Add item to cart
    @FormUrlEncoded
    @POST("addtocart")
    Call<ResponseBody> addToCart(
            @Field("userID") int userID,
            @Field("itemID") int itemID,
            @Field("itemTitle") String itemTitle,
            @Field("itemPrice") double itemPrice,
            @Field("itemQuantity") int itemQuantity
    );

    //Place an Order
    @FormUrlEncoded
    @POST("placeorder")
    Call<ResponseBody> placeOrder(
            @Field("userID") int userID,
            @Field("creditCardNumber") long creditCardNumber,
            @Field("creditCardCVV") int creditCardCVV,
            @Field("expiryMonth") int expiryMonth,
            @Field("expiryYear") int expiryYear,
            @Field("orderTotal") double orderTotal
    );

    //Empty Cart
    @FormUrlEncoded
    @POST("emptycart")
    Call<ResponseBody> emptyCart(
            @Field("cartID") int cartID
    );
}
