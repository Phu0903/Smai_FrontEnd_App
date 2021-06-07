package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.SmaiApp.Adapter.GiveforAdapter;
import com.example.SmaiApp.Adapter.PostDonationAdapter;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.NetWorKing.ApiServices;
import com.example.SmaiApp.NetWorKing.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GiveFor extends AppCompatActivity {

    ListView lvNews_New;
    Spinner spinnerLocation;
    Spinner spinnerType;
    SearchView searchView;
    GiveforAdapter adaptergivfor;
    String address = "";
    String tokenMain;
    String TypeAuthor;
    public static List<PostNewsModel> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_for);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dt);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvNews_New = findViewById(R.id.listView_givefor);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            address = bundle.getString("address");
//            Log.d("Address", address);
        }
        if (bundle.getString("TypeAuthor") != null) {
            TypeAuthor = bundle.getString("TypeAuthor");

            Log.d("typeAuthor cato", TypeAuthor);
        }
        String token = bundle.getString("token");
        tokenMain = token;
        Log.d("Token catogo", token);




        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

        Call<List<PostNewsModel>> call = jsonPlaceHolderApi.getPostGiveCommunity(TypeAuthor);


        call.enqueue(new Callback<List<PostNewsModel>>() {
            @Override
            public void onResponse(Call<List<PostNewsModel>> call, Response<List<PostNewsModel>> response) {
                if ( !response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                    return;
                }
                posts = response.body();

                if (posts.size() != 0) {
                    Log.d("post size", String.valueOf(posts.size()));
                }
                else {
                    Log.d("post size", "Loioiiiiiii");
                }
                initSearchWidgets();
                adaptergivfor = new GiveforAdapter(GiveFor.this, R.layout.row_givefor, posts);
                lvNews_New.setAdapter(adaptergivfor);

            }

            @Override
            public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_LONG).show();
                Log.d("Error", t.getMessage());

            }
        });

    }

    private void initSearchWidgets() {
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<PostNewsModel> postNewsModelArrayList = new ArrayList<>();

                for (PostNewsModel post: posts) {
                    if( post.getNameAuthor().toLowerCase().contains(newText.toLowerCase())) {
                        postNewsModelArrayList.add(post);
                    }
                }

                adaptergivfor = new GiveforAdapter(getApplicationContext(), R.layout.row_givefor, postNewsModelArrayList);
                lvNews_New.setAdapter(adaptergivfor);

                return false;
            }
        });
    }
}