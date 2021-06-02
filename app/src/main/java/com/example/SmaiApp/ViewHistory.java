package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.SmaiApp.Adapter.NewsAdapter;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.NetWorKing.ApiServices;
import com.example.SmaiApp.NetWorKing.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.SmaiApp.Helper.Helper.setListViewHeightBasedOnChildren;

public class ViewHistory extends AppCompatActivity {

    ListView lvHistory;
    ArrayList<PostNewsModel> arrayNews;
    NewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

//        Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_history);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //      listview tin đăng mới
        lvHistory = findViewById(R.id.listViewHistory);
        arrayNews = new ArrayList<PostNewsModel>();

        // get data
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

        Call<List<PostNewsModel>> call = jsonPlaceHolderApi.getPost();

        call.enqueue(new Callback<List<PostNewsModel>>() {
            @Override
            public void onResponse(Call<List<PostNewsModel>> call, Response<List<PostNewsModel>> response) {
                if ( !response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                    return;
                }

                List<PostNewsModel> posts = response.body();

                adapter = new NewsAdapter(
                        getApplicationContext(),
                        R.layout.row_news_listview,
                        posts
                );

            }

            @Override
            public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failllllllllllllllll", Toast.LENGTH_LONG).show();
            }
        });



        lvHistory.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lvHistory);
    }
}