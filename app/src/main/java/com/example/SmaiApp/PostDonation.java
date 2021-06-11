package com.example.SmaiApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.SmaiApp.Adapter.NewsAdapter;
import com.example.SmaiApp.Adapter.PostDonationAdapter;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.Model.ProductModel;
import com.example.SmaiApp.NetWorKing.ApiServices;
import com.example.SmaiApp.NetWorKing.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    ArrayAdapter arrayAdapter1, arrayAdapter2;
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
        ArrayList<String> arrayLocation2 = new ArrayList<String>();
        arrayLocation.add("Tất cả");
        spinnerType = findViewById(R.id.spinner_type);
        ArrayList<String> arrayType = new ArrayList<String>();
        arrayType.add("Tất cả");
        arrayType.add("Quần áo");
        arrayType.add("Học tập");
        arrayType.add("Xe cộ");
        arrayType.add("Nội thất");
        arrayType.add("Nội trợ");

        arrayAdapter2 = new ArrayAdapter(PostDonation.this, R.layout.support_simple_spinner_dropdown_item, arrayType);
        arrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
                for (int i=0;i<posts.size();i++) {
                    String[] address = posts.get(0).getAddress().split(", ");
                    arrayLocation.add(address[address.length-1]);
                }
                Set<String> set = new HashSet<>(arrayLocation);
                arrayLocation.clear();
                arrayLocation.addAll(set);
                Log.d("Tỉnh/thành phố", String.valueOf(arrayLocation.size()));
                adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, posts);
                lvNews_New.setAdapter(adapter);
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
                        }
                        else {
                            Log.e("Full name", "no fullname");
                        }
                        String inforDetail = post.getNote();
                        String typeAuthor = post.getTypeAuthor();
                        List<String> listUrl = post.getUrlImage();
                        String url = listUrl.get(0);
                        String AuthorID = post.getAuthorID();

                        ArrayList<String> arrayListurl = new ArrayList<>();
                        for (String s: listUrl) {
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
                if (posts != null) {
                    spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ArrayList<PostNewsModel> postNewsModelArrayList = new ArrayList<>();

                            if (arrayType.get(position) == "Quần áo") {
                                for (int i = 0; i < posts.size(); i++) {
                                    List<ProductModel> postNewsModels = posts.get(i).getNameProduct();
                                    if (postNewsModels.get(0).getCategory().equals("Quần áo")) {
                                        Log.d("Name Catogory", postNewsModels.get(0).getCategory());
                                        postNewsModelArrayList.add(posts.get(i));
                                    }
                                }
                                adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, postNewsModelArrayList);
                                lvNews_New.setAdapter(adapter);
                            }
                            else {
                                if (arrayType.get(position).equals("Học tập")) {
                                    for (int i = 0; i < posts.size(); i++) {
                                        if (posts.get(i).getNameProduct().get(0).getCategory().equals("Học tập")) {
                                            postNewsModelArrayList.add(posts.get(i));
                                        }
                                    }
                                    adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, postNewsModelArrayList);
                                    lvNews_New.setAdapter(adapter);
                                }
                                else {
                                    if (arrayType.get(position).equals("Xe cộ") || arrayType.get(position).equals("Nội thất") ||
                                            arrayType.get(position).equals("Nội trợ")) {
                                        adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, postNewsModelArrayList);
                                        lvNews_New.setAdapter(adapter);
                                    }
                                    else {
                                        adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, posts);
                                        lvNews_New.setAdapter(adapter);
                                    }
                                }
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, posts);
                            lvNews_New.setAdapter(adapter);
                        }
                    });
                }
                spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ArrayList<PostNewsModel> postNewsModelArrayList = new ArrayList<>();
                        if (arrayLocation.get(position).equals("Tất cả")) {
                            adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, posts);
                            lvNews_New.setAdapter(adapter);
                        } else {
                            for (int i = 0; i < posts.size(); i++) {
                                String[] address = posts.get(i).getAddress().split(", ");
                                String city = address[address.length - 1];
                                if (arrayLocation.get(position).equals(city)) {
                                    postNewsModelArrayList.add(posts.get(i));
                                }
                            }
                            adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, postNewsModelArrayList);
                            lvNews_New.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, posts);
                        lvNews_New.setAdapter(adapter);
                    }
                });



            }

            @Override
            public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_LONG).show();
                Log.d("Error", t.getMessage());

            }
        });



        arrayAdapter1 = new ArrayAdapter(PostDonation.this, R.layout.support_simple_spinner_dropdown_item, arrayLocation);
        arrayAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(arrayAdapter1);



//        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ArrayList<PostNewsModel> postNewsModelArrayList = new ArrayList<>();
//
//                for (PostNewsModel post: posts) {
//                    if(post.getAddress().trim().equals(arrayLocation.get(position))) {
//                        postNewsModelArrayList.add(post);
//                    }
//                }
//                adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, postNewsModelArrayList);
//                lvNews_New.setAdapter(adapter);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, posts);
//                lvNews_New.setAdapter(adapter);
//            }
//        });

        if (posts != null) {
            spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ArrayList<PostNewsModel> postNewsModelArrayList = new ArrayList<>();

                    for (int i = 0; i < posts.size(); i++) {
                        if (posts.get(i).getNameProduct().get(0).getCategory() == arrayType.get(position)) {
                            postNewsModelArrayList.add(posts.get(i));
                        }
                    }
                    adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, postNewsModelArrayList);
                    lvNews_New.setAdapter(adapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    adapter = new PostDonationAdapter(PostDonation.this, R.layout.row_news_listview, posts);
                    lvNews_New.setAdapter(adapter);
                }
            });
        }

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}


