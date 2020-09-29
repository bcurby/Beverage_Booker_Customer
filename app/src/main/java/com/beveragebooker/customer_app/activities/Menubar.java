package com.beveragebooker.customer_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.storage.SharedPrefManager;


public class Menubar extends Fragment {

    private LinearLayout homeButton;
    private LinearLayout accountButton;
    private LinearLayout cartButton;
    private LinearLayout orderButton;
    private LinearLayout helpButton;
    private LinearLayout signOutButton;


    public Menubar(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menubar, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

         homeButton = view.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(Menubar.super.getActivity(), PrimaryMenu.class);
                startActivity(intent);
            }
        });

        accountButton = view.findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(Menubar.super.getActivity(), AccountActivity.class);
                startActivity(intent);
            }
        });

        cartButton = view.findViewById(R.id.cartButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(Menubar.super.getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        orderButton = view.findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(Menubar.super.getActivity(), OrderConfirmationActivity.class);
                startActivity(intent);
            }
        });

        helpButton = view.findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(Menubar.super.getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        signOutButton = view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                SharedPrefManager.getInstance(Menubar.super.getActivity()).clear();
                startActivity(new Intent(Menubar.super.getActivity(), MainActivity.class));
            }
        });
    }

}