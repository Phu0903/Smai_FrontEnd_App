package com.smait.SmaiApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.smait.SmaiApp.Adapter.HistoryAdapter;
import com.smait.SmaiApp.Adapter.NewsAdapter;
import com.smait.SmaiApp.Adapter.NewsRecyclerviewAdapter;
import com.smait.SmaiApp.Model.PostNewsModel;
import com.smait.SmaiApp.NetWorKing.ApiServices;
import com.smait.SmaiApp.NetWorKing.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.smait.SmaiApp.Helper.Helper.setListViewHeightBasedOnChildren;

public class ViewHistory extends AppCompatActivity {

    ListView lvHistory;
    ArrayList<PostNewsModel> arrayNews;
    HistoryAdapter adapter;
    String token;
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

        token = getIntent().getStringExtra("token");
        Log.d("Token history", token);


        // get data
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);
//
//        Call<List<PostNewsModel>> call = jsonPlaceHolderApi.getHistory("Bearer " + token, listHistory);
//        call.enqueue(new Callback<List<PostNewsModel>>() {
//            @Override
//            public void onResponse(Call<List<PostNewsModel>> call, Response<List<PostNewsModel>> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(getApplicationContext(), "Fail, " + response.message(), Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Failllllllllllllllll", Toast.LENGTH_LONG).show();
//            }
//        });


        Call<List<PostNewsModel>> call1 = jsonPlaceHolderApi.getHistoryPost("Bearer " + token);
        call1.enqueue(new Callback<List<PostNewsModel>>() {
            @Override
            public void onResponse(Call<List<PostNewsModel>> call, Response<List<PostNewsModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Fail, " + response.message(), Toast.LENGTH_LONG).show();

                }

                List<PostNewsModel> posts = response.body();

                adapter = new HistoryAdapter(
                        getApplicationContext(),
                        R.layout.row_news_history,
                        posts
                );
                lvHistory.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failllllllllllllllll", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            finish();

        return true;
    }
}