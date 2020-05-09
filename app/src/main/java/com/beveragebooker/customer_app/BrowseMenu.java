package com.beveragebooker.customer_app;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseMenu extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private ArrayList<MenuItem> mMenuItems;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_menu);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMenuItems = new ArrayList<MenuItem>();

        final RecyclerAdapter adapter = new RecyclerAdapter(mMenuItems);

        mRecyclerView.setAdapter(adapter);

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getItems();

        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                            mMenuItems.add(response.body().get(i));
                    }

                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Toast.makeText(BrowseMenu.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }


}
