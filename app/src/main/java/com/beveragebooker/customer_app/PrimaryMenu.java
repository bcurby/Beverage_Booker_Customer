package com.beveragebooker.customer_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PrimaryMenu extends AppCompatActivity {

    private Button menuLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_menu);

        menuLink = (Button) findViewById(R.id.menuLink);
        menuLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseMenu();
            }
        });

    }
    //open to the next activity on button click
    public void openBrowseMenu(){
        Intent intent = new Intent(this, BrowseMenu.class );
        startActivity(intent);
    }

    //TODO add the book table & book event buttons

}

