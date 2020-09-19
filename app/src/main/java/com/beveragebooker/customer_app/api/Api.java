package com.beveragebooker.customer_app.api;

import android.widget.EditText;

import com.beveragebooker.customer_app.models.Cart;
import com.beveragebooker.customer_app.models.LoginResponse;
import com.beveragebooker.customer_app.models.MenuItem;
import com.beveragebooker.customer_app.models.Order;

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
    Call<List<MenuItem>> getItems(
            @Query("itemType") String itemType
    );



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
            @Field("itemQuantity") int itemQuantity,
            @Field("itemSize") String itemSize,
            @Field("itemMilk") String itemMilk,
            @Field("itemSugar") String itemSugar,
            @Field("itemDecaf") String itemDecaf,
            @Field("itemVanilla") String itemVanilla,
            @Field("itemCaramel") String itemCaramel,
            @Field("itemChocolate") String itemChocolate,
            @Field("itemWhippedCream") String itemWhippedCream,
            @Field("itemFrappe") String itemFrappe,
            @Field("itemHeated") String itemHeated,
            @Field("itemComment") String itemComment,
            @Field("itemType") String itemType
    );

    //Place an Order
    @FormUrlEncoded
    @POST("placeorder")
    Call<ResponseBody> placeOrder(
            @Field("userID") int userID,
            @Field("deliveryStatus") int deliveryStatus,
            @Field("orderTotal") double orderTotal
    );

    //Empty Cart
    @FormUrlEncoded
    @POST("emptycart")
    Call<ResponseBody> emptyCart(
            @Field("userID") int userID
    );

    //Book Delivery
    @FormUrlEncoded
    @POST("bookdelivery")
    Call<ResponseBody> bookDelivery(
            @Field("userID") int userID,
            @Field("firstName") String firstName,
            @Field("phone") String phone,
            @Field("streetUnit") String streetUnit,
            @Field("streetName") String streetName
    );

    //Returns values for a single menu item
    @GET("getmenuitem")
    Call<List<MenuItem>> getMenuItem(
            @Query("itemID") int itemID
    );

    //Delete item from database
    @FormUrlEncoded
    @POST("deletecartitem")
    Call<ResponseBody> deleteCartItem(
            @Field("id") int id,
            @Field("itemTitle") String itemTitle,
            @Field("itemPrice") double itemPrice,
            @Field("itemSize") String itemSize,
            @Field("itemMilk") String itemMilk,
            @Field("itemSugar") String itemSugar,
            @Field("itemDecaf") String itemDecaf,
            @Field("itemVanilla") String itemVanilla,
            @Field("itemCaramel") String itemCaramel,
            @Field("itemChocolate") String itemChocolate,
            @Field("itemWhippedCream") String itemWhippedCream,
            @Field("itemFrappe") String itemFrappe,
            @Field("itemHeated") String itemHeated,
            @Field("itemComment") String itemComment,
            @Field("itemType") String itemType,
            @Field("userID") int userID,
            @Field("itemQuantity") int itemQuantity


    );

    //Get status of the order from db
    @GET("getorderstatus")
    Call<Order> getOrderStatus(
            @Query("userID") int userID,
            @Query("cartID") int cartID
    );

    @FormUrlEncoded
    @POST("notificationSent")
    Call<ResponseBody> setStatus(
            @Field("orderID") int orderID
    );

    //Get status of the order from db
    @GET("getcartdetails")
    Call<Cart> getCartDetails(
            @Query("userID") int userID
    );

    //Get status of the order from db
    @GET("getcartidfromusers")
    Call<Order> getCartIDFromUsers(
            @Query("userID") int userID
    );

    //Save profile after edit
    @FormUrlEncoded
    @POST("saveprofile")
    Call<ResponseBody> saveProfile(
            @Field("userID") int userID,
            @Field("firstName") String mFirstName,
            @Field("lastName") String mLastName,
            @Field("email") String mEmail,
            @Field("phoneNum") String mPhoneNum
    );

    //delete the user
    @FormUrlEncoded
    @POST("deleteuser")
    Call<ResponseBody> deleteUser(
            @Field("userID") int userID
    );

    //Save new password
    @FormUrlEncoded
    @POST("savepassword")
    Call<ResponseBody> savePassword(
            @Field("userID")  int userID,
            @Field("password")String mPassword1
    );
}