package com.example.SmaiApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.SmaiApp.Adapter.GiveforAdapter;
import com.example.SmaiApp.Adapter.PostDonationAdapter;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.Model.ProductModel;
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
    EditText searchView;
    GiveforAdapter adaptergivfor;
    String address = "";
    String tokenMain;
    String TypeAuthor;
    public static List<PostNewsModel> posts;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_for);

        if (savedInstanceState != null) {
            TypeAuthor = savedInstanceState.getString("TypeAuthor");
            Log.d("TypeAuthoronSaveonget", TypeAuthor);
            address = savedInstanceState.getString("address");
            tokenMain = savedInstanceState.getString("token");
        }
        else {
            Log.d("savedinstantcestate", "is nulllllll");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dt);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearLayout = findViewById(R.id.linearlayoutGiveFor);
        linearLayout.requestFocus();
        lvNews_New = findViewById(R.id.listView_givefor);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            address = bundle.getString("address");
//            Log.d("Address", address);
            TypeAuthor = bundle.getString("TypeAuthor");
            tokenMain = bundle.getString("token");
            Log.d("typeAuthor cato", TypeAuthor);
        }
//        String token = bundle.getString("token");
//        tokenMain = token;
//        Log.d("Token catogo", tokenMain);




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
                lvNews_New.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(getApplicationContext(), DetailPostGiveFor.class);
                        PostNewsModel post = posts.get(position);
                        String title = post.getTitle();

                        List<ProductModel> productModel = post.getNameProduct();
                        if (productModel.size() != 0) {
                            String detailType = productModel.get(0).getCategory();
                            intent.putExtra("detailType", detailType);
                        }
                        String address = post.getAddress();
                        String fullName = post.getNameAuthor();
                        if (fullName != null) {
                            Log.d("fullName", fullName);
                        }
                        else {
                            Log.e("Full name", "no fullname");
                        }
                        String inforDetail = post.getNote();
                        String typeAuthor = post.getTypeAuthor();
                        List<String> listUrl = post.getUrlImage();
                        ArrayList<String> arrayListurl = new ArrayList<>();
                        for (String s: listUrl) {
                            arrayListurl.add(s);
                        }
                        String AuthorID = post.getAuthorID();

                        intent.putExtra("title", title);
                        intent.putExtra("address", address);
                        intent.putExtra("fullName", fullName);
                        intent.putExtra("inforDetail", inforDetail);
                        intent.putExtra("typeAuthor", typeAuthor);
                        intent.putExtra("AuthorID", AuthorID);
                        intent.putStringArrayListExtra("url", arrayListurl);
                        startActivity(intent);


                    }
                });

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
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<PostNewsModel> postNewsModelArrayList = new ArrayList<>();

                for (PostNewsModel post: posts) {
                    if( post.getNameAuthor().toLowerCase().contains(s.toString().toLowerCase())) {
                        postNewsModelArrayList.add(post);
                    }
                }

                adaptergivfor = new GiveforAdapter(getApplicationContext(), R.layout.row_givefor, postNewsModelArrayList);
                lvNews_New.setAdapter(adaptergivfor);

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("TypeAuthor on Save", TypeAuthor);
        outState.putString("TypeAuthor", TypeAuthor);
        outState.putString("token", tokenMain);
        outState.putString("address", address);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TypeAuthor = savedInstanceState.getString("TypeAuthor");
        Log.d("TypeAuthor on Restore", TypeAuthor);
        address = savedInstanceState.getString("address");
        tokenMain = savedInstanceState.getString("token");
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}