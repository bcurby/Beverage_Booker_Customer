package com.beveragebooker.customer_app.api;

import com.beveragebooker.customer_app.models.LoginResponse;
import com.beveragebooker.customer_app.models.MenuItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.GET;

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

}
