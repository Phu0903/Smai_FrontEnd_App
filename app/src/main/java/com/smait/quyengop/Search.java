package com.smait.quyengop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.smait.quyengop.Adapter.PostDonationAdapter;
import com.smait.quyengop.Model.PostNewsModel;
import com.smait.quyengop.Model.ProductModel;
import com.smait.quyengop.NetWorKing.ApiServices;
import com.smait.quyengop.NetWorKing.RetrofitClient;
import com.smait.quyengop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Search extends AppCompatActivity {

    EditText searchView;
    ListView lvNews_New;
    PostDonationAdapter adapter;
    public static List<PostNewsModel> posts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lvNews_New = findViewById(R.id.listViewNews_New);
        searchView = findViewById(R.id.search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getAllPost();



    }



    private void getAllPost() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

        Call<List<PostNewsModel>> call = jsonPlaceHolderApi.getFullPost();

        call.enqueue(new Callback<List<PostNewsModel>>() {
            @Override
            public void onResponse(Call<List<PostNewsModel>> call, Response<List<PostNewsModel>> response) {
                if ( !response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                    return;
                }
                posts = response.body();

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
                            if(post.getNameAuthor().toLowerCase().contains(s.toString().toLowerCase()) ||
                                    post.getAddress().toLowerCase().contains(s.toString().toLowerCase()) ||
                                    post.getTitle().toLowerCase().contains(s.toString().toLowerCase()) ||
                                    post.getTypeAuthor().toLowerCase().contains(s.toString().toLowerCase()) ||
                                    post.getNameProduct().get(0).getCategory().toLowerCase().contains(s.toString().toLowerCase())) {
                                postNewsModelArrayList.add(post);
                            }
                        }

//                adaptergivfor = new GiveforAdapter(getApplicationContext(), R.layout.row_givefor, postNewsModelArrayList);
//                lvNews_New.setAdapter(adaptergivfor);
                        adapter = new PostDonationAdapter(Search.this, R.layout.row_news_listview, postNewsModelArrayList);
                        lvNews_New.setAdapter(adapter);

                    }
                });


                lvNews_New.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(getApplicationContext(), DetailPostSearch.class);
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

                        String AuthorID = post.getAuthorID();

                        ArrayList<String> arrayListurl = new ArrayList<>();
                        for (String s : listUrl) {
                            arrayListurl.add(s);
                        }
                        String idpost = posts.get(position).get_id();
                        intent.putExtra("idpost", idpost);
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



    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}