package com.example.SmaiApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SmaiApp.Adapter.NewsAdapter;
import com.example.SmaiApp.Adapter.PostDonationAdapter;
import com.example.SmaiApp.Danhmuc.NameProduct;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.utkala.searchablespinner.SearchableSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.SmaiApp.Helper.Helper.setListViewHeightBasedOnChildren;


public class PostDonation extends AppCompatActivity {

    String tokenMain;
    ListView lvNews_New;
    Spinner spinnerLocation;
    PostDonationAdapter adapter;
    ArrayAdapter arrayAdapter1, arrayAdapter2;
    Button imgBtnFilter;
    TextView txtFilter;
    public static List<PostNewsModel> posts;
    JSONArray jsonCityArray;
    SearchableSpinner searchableSpinner;
    ArrayList<PostNewsModel> listName = new ArrayList<>();
    ArrayList<String> listCatogory = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_donation);
//      listview tin đăng mới
        lvNews_New = findViewById(R.id.listViewNews_New);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dt);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imgBtnFilter = findViewById(R.id.btn_filter_postdonation);
        searchableSpinner = findViewById(R.id.searchableSpinner);
        Intent intent = getIntent();
        String token = intent.getStringExtra("Token");
        tokenMain =token;
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

                            PostDonationAdapter adapter1 = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, postsFilter);
                            lvNews_New.setAdapter(adapter1);
                        } else {
                            adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, posts);
                            lvNews_New.setAdapter(adapter);
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

        ArrayList<String> arrayLocation = new ArrayList<String>();
        arrayLocation.add("Tỉnh/thành phố");

        imgBtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDonation.this, FilterPostDonation.class);
                startActivity(intent);
            }
        });



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
                if (intent != null) {
                    String loc =intent.getStringExtra("loc");
                    listCatogory = intent.getStringArrayListExtra("ListName");
                    if (listCatogory != null) {
                        for (String s : listCatogory) {
                            Log.d("Name catogory", s);
                            for (int i=0;i<posts.size();i++) {
                                List<ProductModel> list = posts.get(i).getNameProduct();
                                for (int j=0;j<list.size();j++) {
                                    String nameCategory = list.get(j).getNameProduct();
                                    Log.d("Đồ khác trẻ em", nameCategory);
                                    if (nameCategory.equals(s)) {
                                        listName.add(posts.get(i));
                                    }
                                }
                            }
                        }

                        Log.d("Size list name", String.valueOf(listName.size()));
                        adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, listName);
                        lvNews_New.setAdapter(adapter);
                    }
                }
                lvNews_New.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(getApplicationContext(), DetailPostTangCongDong.class);
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
                        } else {
                            Log.e("Full name", "no fullname");
                        }
                        String inforDetail = post.getNote();
                        String typeAuthor = post.getTypeAuthor();
                        List<String> listUrl = post.getUrlImage();

                        String AuthorID = post.getAuthorID();

                        ArrayList<String> arrayListurl = new ArrayList<>();
                        for (String s : listUrl) {
                            arrayListurl.add(s);
                        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar_danhmuc, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.button_cancel) {
            ConfirmCancel();
        }
        else {
            finish();
        }
        return true;
    }

    private void ConfirmCancel() {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(this);
        alerDialog.setTitle("Thông báo!");
        alerDialog.setMessage("Bạn có chắc muốn hủy không?");

        alerDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("Token", tokenMain);
                i.putExtra("message", "OK");
                startActivity(i);
            }
        });
        alerDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerDialog.show();
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
}


