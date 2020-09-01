package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.MenuItem;
import com.beveragebooker.customer_app.models.User;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.beveragebooker.customer_app.storage.SharedPrefManager.getInstance;

public class AddToCartActivity extends AppCompatActivity {

    public static final String ITEM_TYPE_RETURN = "com.beveragebooker.customer_app.ITEM_TYPE_RETURN";

    private ArrayList<MenuItem> mSingleMenuItem;

    LinearLayout linearLayout;

    RadioGroup radioGroupSize;
    RadioGroup radioGroupMilk;
    RadioGroup radioGroupSugar;
    RadioGroup radioGroupFrappe;

    CheckBox decaf;

    CheckBox vanilla;
    CheckBox caramel;
    CheckBox chocolate;
    CheckBox whippedCream;

    CheckBox heated;

    EditText comment;

    ElegantNumberButton qtyButton;
    TextView stockLevel;

    int userID;
    int itemID;
    String itemTitle;
    double itemPrice;
    int itemQuantity;
    String itemMilk;
    String itemSugar;
    String itemDecaf;
    String itemVanilla;
    String itemCaramel;
    String itemChocolate;
    String itemWhippedCream;
    String itemFrappe;
    String itemHeated;
    String itemType;
    String itemComment;
    String itemSize;

    int milkStatus;
    int sugarStatus;
    int decafStatus;
    int extrasStatus;
    int frappeStatus;
    int heatedStatus;

    Button addToCartButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        linearLayout = findViewById(R.id.linearLayout);
        stockLevel = findViewById(R.id.stockLevelAddToCart);

        mSingleMenuItem = new ArrayList<>();

        Intent intent = getIntent();

        itemID = intent.getIntExtra(BrowseMenu.ITEM_ID, 0);
        itemTitle = intent.getStringExtra(BrowseMenu.ITEM_NAME);
        itemPrice = intent.getDoubleExtra(BrowseMenu.ITEM_PRICE, 0);

        milkStatus = intent.getIntExtra(BrowseMenu.ITEM_MILK, 0);
        sugarStatus = intent.getIntExtra(BrowseMenu.ITEM_SUGAR, 0);
        decafStatus = intent.getIntExtra(BrowseMenu.ITEM_DECAF, 0);
        extrasStatus = intent.getIntExtra(BrowseMenu.ITEM_EXTRAS, 0);
        frappeStatus = intent.getIntExtra(BrowseMenu.ITEM_FRAPPE, 0);
        heatedStatus = intent.getIntExtra(BrowseMenu.ITEM_HEATED, 0);
        itemType = intent.getStringExtra(BrowseMenu.ITEM_TYPE);

        final User loggedUser = getInstance(AddToCartActivity.this).getUser();
        userID = loggedUser.getId();

        itemQuantity = 1;

        System.out.println("UserID: " + userID);
        System.out.println("ItemID: " + itemID);
        System.out.println("Item Name: " + itemTitle);
        System.out.println("Price: " + itemPrice);
        System.out.println("Milk: " + milkStatus);
        System.out.println("Sugar: " + sugarStatus);
        System.out.println("Extras: " + extrasStatus);
        System.out.println("Frappe: " + frappeStatus);
        System.out.println("Heated: " + heatedStatus);
        System.out.println("itemType: " + itemType);

        itemSize = "small";
        itemMilk = "full cream milk";
        itemSugar = "sugar";
        itemDecaf = "-";
        itemVanilla = "-";
        itemCaramel = "-";
        itemChocolate = "-";
        itemWhippedCream = "-";
        itemFrappe = "chocolate";
        itemHeated = "-";
        itemComment = "-";

        comment = findViewById(R.id.editTextComment);
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemComment = comment.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        System.out.println("Item Milk: " + itemMilk);
        System.out.println("Item Sugar: " + itemSugar);
        System.out.println("Item Frappe: " + itemFrappe);
        System.out.println("Item Heated: " + itemHeated);

        initViews(itemType, milkStatus, sugarStatus, decafStatus, extrasStatus, frappeStatus, heatedStatus);
        setListeners(milkStatus, sugarStatus, decafStatus, extrasStatus, frappeStatus, heatedStatus);

        getStockUpdate();

        addToCartButton = findViewById(R.id.addToCartButton);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemSize.equals("medium")) {
                    itemPrice = itemPrice + 0.50;
                    System.out.print("itemPrice: " + itemPrice);
                }

                if (itemSize.equals("large")) {
                    itemPrice = itemPrice + 1.00;
                    System.out.print("itemPrice: " + itemPrice);
                }


                System.out.println("Item Milk: " + itemMilk);
                System.out.println("Item Sugar: " + itemSugar);

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .addToCart(userID, itemID, itemTitle, itemPrice, itemQuantity, itemSize, itemMilk,
                                itemSugar, itemDecaf, itemVanilla, itemCaramel, itemChocolate,
                                itemWhippedCream, itemFrappe, itemHeated, itemComment, itemType);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.code() == 200) {
                            Toast.makeText(AddToCartActivity.this, "Item added to cart", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddToCartActivity.this, BrowseMenu.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra(ITEM_TYPE_RETURN, itemType);
                            startActivity(intent);

                        } else if (response.code() == 403) {
                            Toast.makeText(AddToCartActivity.this, "There was a problem adding the item to cart",
                                    Toast.LENGTH_LONG).show();

                        } else if (response.code() == 402) {
                            Toast.makeText(AddToCartActivity.this, "There is not that quantity of the item in stock. " +
                                            "Please check updated stock level on screen.",
                                    Toast.LENGTH_LONG).show();
                            getStockUpdate();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(AddToCartActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

        qtyButton = findViewById(R.id.qtyButton);
        qtyButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringItemQuantity = qtyButton.getNumber();
                itemQuantity = Integer.parseInt(stringItemQuantity);
                System.out.println("Qty: " + itemQuantity);
            }
        });
    }

    private void getStockUpdate() {

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getMenuItem(itemID);

        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mSingleMenuItem.add(response.body().get(i));
                    }

                    MenuItem singleItem = mSingleMenuItem.get(0);
                    int itemStock = singleItem.getItemStock();
                    System.out.println("ItemStock: " + itemStock);
                    int displayedStock = itemStock - 5;
                    System.out.println("DisplayedStock: " + displayedStock);

                    if (itemType.equals("food") && itemStock > 5) {
                        stockLevel.setVisibility(TextView.VISIBLE);
                        stockLevel.setText(displayedStock + " in stock");
                        qtyButton.setRange(1, displayedStock);
                    }

                    if (itemType.equals("food") && itemStock <= 5) {
                        stockLevel.setVisibility(TextView.VISIBLE);
                        stockLevel.setText("SOLD OUT");
                        stockLevel.setTextColor(Color.RED);
                        qtyButton.setVisibility(Button.INVISIBLE);
                        addToCartButton.setEnabled(false);
                        addToCartButton.setText("SOLD OUT");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {

                Toast.makeText(AddToCartActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initViews(String itemType, int milkStatus, int sugarStatus, int decafStatus, int extrasStatus,
                           int frappeStatus, int heatedStatus) {

        if (itemType.equals("drink")) {
            View sizeView = getLayoutInflater().inflate(R.layout.size_selection, null, false);
            linearLayout.addView(sizeView);
        }

        if (milkStatus == 1) {
            View milkView = getLayoutInflater().inflate(R.layout.milk_selection, null, false);
            linearLayout.addView(milkView);
        }

        if (sugarStatus == 1) {
            View sugarView = getLayoutInflater().inflate(R.layout.sugar_selection, null, false);
            linearLayout.addView(sugarView);
        }

        if (decafStatus == 1) {
            View decafView = getLayoutInflater().inflate(R.layout.decaf_selection, null, false);
            linearLayout.addView(decafView);
        }

        if (extrasStatus == 1) {
            View extrasView = getLayoutInflater().inflate(R.layout.extras_selection, null, false);
            linearLayout.addView(extrasView);
        }

        if (frappeStatus == 1) {
            View frappeView = getLayoutInflater().inflate(R.layout.frappe_selection, null, false);
            linearLayout.addView(frappeView);
        }

        if (heatedStatus == 1) {
            View heatedView = getLayoutInflater().inflate(R.layout.heated_selection, null, false);
            linearLayout.addView(heatedView);
        }
    }

    private void setListeners(int milkStatus, int sugarStatus, int decafStatus,
                              int extrasStatus, int frappeStatus, int heatedStatus) {

        if (itemType.equals("drink")) {
            radioGroupSize = findViewById(R.id.sizeRadioGroup);
            radioGroupSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.smallSize) {
                        itemSize = "small";
                        System.out.println(itemSize);
                    }

                    if (checkedId == R.id.mediumSize) {
                        itemSize = "medium";
                        System.out.println(itemSize);
                    }

                    if (checkedId == R.id.largeSize) {
                        itemSize = "large";
                        System.out.println(itemSize);
                    }
                }
            });
        } else {
            itemSize = "-";
        }

        if (milkStatus == 1) {
            radioGroupMilk = findViewById(R.id.milkRadioGroup);
            radioGroupMilk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.fullCream) {
                        itemMilk = "full cream milk";
                        System.out.println(itemMilk);
                    }

                    if (checkedId == R.id.skim) {
                        itemMilk = "skim milk";
                        System.out.println(itemMilk);
                    }

                    if (checkedId == R.id.almond) {
                        itemMilk = "almond milk";
                        System.out.println(itemMilk);
                    }

                    if (checkedId == R.id.soy) {
                        itemMilk = "soy milk";
                        System.out.println(itemMilk);
                    }
                }
            });
        } else {
            itemMilk = "-";
        }

        if (sugarStatus == 1) {
            radioGroupSugar = findViewById(R.id.sugarRadioGroup);
            radioGroupSugar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.sugar) {
                        itemSugar = "sugar";
                        System.out.println(itemSugar);
                    }

                    if (checkedId == R.id.sweetener) {
                        itemSugar = "sweetener";
                        System.out.println(itemSugar);
                    }

                    if (checkedId == R.id.noSugar) {
                        itemSugar = "no sugar";
                        System.out.println(itemSugar);
                    }
                }
            });
        } else {
            itemSugar = "-";
        }

        if (decafStatus == 1) {
            decaf = findViewById(R.id.decafCheck);
            decaf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (decaf.isChecked()) {
                        itemDecaf = "decaf";
                        System.out.println(itemDecaf);
                    }
                }
            });
        }

        if (extrasStatus == 1) {
            vanilla = findViewById(R.id.vanilla);
            vanilla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vanilla.isChecked()) {
                        itemVanilla = "vanilla";
                        System.out.println(itemVanilla);
                    }
                }
            });

            caramel = findViewById(R.id.caramel);
            caramel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (caramel.isChecked()) {
                        itemCaramel = "caramel";
                        System.out.println(itemCaramel);
                    }
                }
            });

            chocolate = findViewById(R.id.chocolate);
            chocolate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chocolate.isChecked()) {
                        itemChocolate = "chocolate";
                        System.out.println(itemChocolate);
                    }
                }
            });

            whippedCream = findViewById(R.id.whippedCream);
            whippedCream.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (whippedCream.isChecked()) {
                        itemWhippedCream = "whipped cream";
                        System.out.println(itemWhippedCream);
                    }
                }
            });
        }

        if (frappeStatus == 1) {
            radioGroupFrappe = findViewById(R.id.frappeRadioGroup);
            radioGroupFrappe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.chocolateFrappe) {
                        itemFrappe = "chocolate";
                        System.out.println(itemFrappe);
                    }

                    if (checkedId == R.id.caramelFrappe) {
                        itemFrappe = "caramel";
                        System.out.println(itemFrappe);
                    }

                    if (checkedId == R.id.coffeeFrappe) {
                        itemFrappe = "coffee";
                        System.out.println(itemFrappe);
                    }

                    if (checkedId == R.id.mochaFrappe) {
                        itemFrappe = "mocha";
                        System.out.println(itemFrappe);
                    }
                }
            });
        } else {
            itemFrappe = "-";
        }

        if (heatedStatus == 1) {
            heated = findViewById(R.id.heated);
            heated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (heated.isChecked()) {
                        itemHeated = "heated";
                        System.out.println(itemHeated);
                    }
                }
            });
        }
    }
}



