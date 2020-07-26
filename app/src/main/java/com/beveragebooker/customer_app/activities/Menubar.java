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
import com.beveragebooker.customer_app.R;



public class Menubar extends Fragment {


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
        Button homeButton = (Button) view.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(Menubar.super.getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        Button accountButton = (Button) view.findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(Menubar.super.getActivity(), AccountActivity.class);
                startActivity(intent);
            }
        });
        Button cartButton = (Button) view.findViewById(R.id.cartButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(Menubar.super.getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

}