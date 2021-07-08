package com.smait.quyengop.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.smait.quyengop.Controller.Adapter.PostDonationAdapter;
import com.smait.quyengop.Model.PostNewsModel;
import com.smait.quyengop.Model.ProductModel;
import com.smait.quyengop.Controller.NetWorKing.ApiServices;
import com.smait.quyengop.Controller.NetWorKing.RetrofitClient;
import com.smait.quyengop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Search extends AppCompatActivity {

    EditText searchView;
    ListView lvNews_New;
    ImageButton btnMic;
    PostDonationAdapter adapter;
    public static List<PostNewsModel> posts;
    protected static final int RESULT_SPEECH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnMic = findViewById(R.id.btnMic);
        lvNews_New = findViewById(R.id.listViewNews_New);
        searchView = findViewById(R.id.search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });

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
                        lvNews_New.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent(getApplicationContext(), DetailPostSearch.class);
                                PostNewsModel post = postNewsModelArrayList.get(position);
                                String title = post.getTitle();

                                List<ProductModel> productModel = post.getNameProduct();
                                if (productModel.size() != 0) {
                                    String detailType = productModel.get(0).getNameProduct();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if  (requestCode == RESULT_SPEECH) {

            if (data != null) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Log.d("Speech cái gì đấy: ", text.get(0));
                searchView.setText(text.get(0));
            }

        }

    }

    private void requestPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                speak();

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.RECORD_AUDIO)
                .check();
    }
    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Nói gì đó");
        startActivityForResult(intent, RESULT_SPEECH);
//        try {
//            startActivityForResult(intent, RESULT_SPEECH);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(getApplicationContext(), "Thiết bị của bạn quá cùi", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
    }
}