package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.SmaiApp.Adapter.NewsAdapter;
import com.example.SmaiApp.Adapter.PostDonationAdapter;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.NetWorKing.ApiServices;
import com.example.SmaiApp.NetWorKing.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.SmaiApp.Helper.Helper.setListViewHeightBasedOnChildren;


public class PostDonation extends AppCompatActivity {

    ListView lvNews_New;
    Spinner spinnerLocation;
    Spinner spinnerType;
    PostDonationAdapter adapter;
    public static List<PostNewsModel> posts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_donation);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dt);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinnerLocation = findViewById(R.id.spinner);
        ArrayList<String> arrayLocation = new ArrayList<String>();
        arrayLocation.add("Tp.HCM");
        arrayLocation.add("Đồng Nai");
        arrayLocation.add("Thủ Đức");
        spinnerType = findViewById(R.id.spinner_type);
        ArrayList<String> arrayType = new ArrayList<String>();
        arrayType.add("Tất cả");
        arrayType.add("Đồ dùng");
        arrayType.add("Giấy vụn");
        arrayType.add("Đồ gia dụng");

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayLocation);
        spinnerLocation.setAdapter(arrayAdapter1);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayType);
        spinnerType.setAdapter(arrayAdapter2);


        //      listview tin đăng mới
        lvNews_New = findViewById(R.id.listViewNews_New);

        // get data
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

        Call<List<PostNewsModel>> call = jsonPlaceHolderApi.getPostGiveCommunity("tangcongdong");


        call.enqueue(new Callback<List<PostNewsModel>>() {
            @Override
            public void onResponse(Call<List<PostNewsModel>> call, Response<List<PostNewsModel>> response) {
                if ( !response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                    return;
                }
                posts = response.body();

                adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, posts);
                lvNews_New.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_LONG).show();
                Log.d("Error", t.getMessage());

            }
        });

    }
}


