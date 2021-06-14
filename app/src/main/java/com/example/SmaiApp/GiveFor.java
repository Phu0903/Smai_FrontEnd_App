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
import android.widget.ArrayAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import pl.utkala.searchablespinner.SearchableSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GiveFor extends AppCompatActivity {

    ListView lvNews_New;
    JSONArray jsonCityArray;
    SearchableSpinner searchableSpinner;
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
            TypeAuthor = bundle.getString("TypeAuthor");
            tokenMain = bundle.getString("token");

        }



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

                initSearchWidgets();
                initFilter();


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

    private void initFilter() {
        searchableSpinner = findViewById(R.id.searchableSpinner);

        try {
            jsonCityArray = new JSONObject(loadJSONFromAsset()).optJSONArray("province");

            ArrayList<String> cityList = new ArrayList<String>();
            cityList.add(0, "Tỉnh/thành phố");


            for (int i = 0; i < jsonCityArray.length(); i++) {
                cityList.add(jsonCityArray.optJSONObject(i).optString("name").trim());
            }
            searchableSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, cityList));

            searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    List<PostNewsModel> postsFilter = new ArrayList<>();

                    if (posts != null && posts.size() != 0) {

                        if (!cityList.get(position).equals("Tỉnh/thành phố")) {
                            for (int i = 0; i < posts.size(); i++) {
                                String[] address = posts.get(i).getAddress().split(", ");
                                if (cityList.get(position).contains(address[address.length - 1])) {
                                    postsFilter.add(posts.get(i));
                                }
                            }

                            GiveforAdapter adapter1 = new GiveforAdapter(GiveFor.this,  R.layout.row_givefor, postsFilter);
                            lvNews_New.setAdapter(adapter1);
                        } else  {
                            adaptergivfor = new GiveforAdapter(GiveFor.this, R.layout.row_givefor, posts);
                            lvNews_New.setAdapter(adaptergivfor);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
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

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("db.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}