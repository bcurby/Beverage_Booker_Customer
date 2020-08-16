package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.beveragebooker.customer_app.R;

public class AddToCartActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    RadioGroup radioGroupMilk;
    RadioGroup radioGroupSugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        linearLayout = findViewById(R.id.linearLayout);

        int milk = 1;
        int sugar = 1;

        initViews(milk, sugar);
        setListeners(milk, sugar);

    }

    private void initViews(int milk, int sugar) {

        if (milk == 1) {

            View milkView = getLayoutInflater().inflate(R.layout.milk_selection, null, false);
            linearLayout.addView(milkView);
        }
        if (sugar == 1) {
            View sugarView = getLayoutInflater().inflate(R.layout.sugar_selection, null, false);
            linearLayout.addView(sugarView);
        }
    }

    private void setListeners(int milk, int sugar) {

        if (milk == 1) {
            radioGroupMilk = findViewById(R.id.milkRadioGroup);
            radioGroupMilk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.fullCream) {
                        String milkSelection = "full cream milk";
                        System.out.println(milkSelection);
                    }

                    if (checkedId == R.id.skim) {
                        String milkSelection = "skim milk";
                        System.out.println(milkSelection);
                    }

                    if (checkedId == R.id.almond) {
                        String milkSelection = "almond milk";
                        System.out.println(milkSelection);
                    }

                    if (checkedId == R.id.soy) {
                        String milkSelection = "soy milk";
                        System.out.println(milkSelection);
                    }
                }
            });

        }
        if (sugar == 1) {
            radioGroupSugar = findViewById(R.id.sugarRadioGroup);
            radioGroupSugar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.sugar) {
                        String sugarSelection = "sugar";
                        System.out.println(sugarSelection);
                    }

                    if (checkedId == R.id.sweetener) {
                        String sugarSelection = "sweetener";
                        System.out.println(sugarSelection);
                    }

                    if (checkedId == R.id.noSugar) {
                        String sugarSelection = "no sugar/sweetener";
                        System.out.println(sugarSelection);
                    }
                }
            });
        }
    }
}



