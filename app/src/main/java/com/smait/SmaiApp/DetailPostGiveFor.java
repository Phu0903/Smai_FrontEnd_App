package com.smait.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.smait.SmaiApp.Model.AccountModel;
import com.smait.SmaiApp.NetWorKing.ApiServices;
import com.smait.SmaiApp.NetWorKing.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailPostGiveFor extends AppCompatActivity {
    Button btnCall;

    TextView tittle, detailType, detailPrice, address, fullName, typeAuthor, inforDetail;
    ImageView productImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post_give_for);

        //        Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dt);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getIdComponent();

//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_gray)));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();


        String title = intent.getStringExtra("title");
        String detailtype = intent.getStringExtra("detailType");
        String addresss = intent.getStringExtra("address");
        String fullname = intent.getStringExtra("fullName");
        if (fullname != null) {
            Log.e("Fullname", fullname);
        }
        else {
            Log.e("FullName", "no full name");
        }
        String infordetail = intent.getStringExtra("inforDetail");
        String typeauthor = intent.getStringExtra("typeAuthor");
        ArrayList<String> listUrl = intent.getStringArrayListExtra("url");
        String AuthorID = intent.getStringExtra("AuthorID");

        tittle.setText(title);

        detailType.setText(detailtype);
        address.setText(addresss);
        fullName.setText(fullname);
        inforDetail.setText(infordetail);
        typeAuthor.setText(typeauthor);

        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        for (String uri:listUrl)  {
            slideModels.add(new SlideModel(uri));
        }
        imageSlider.setImageList(slideModels, true);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

                Call<AccountModel> call = jsonPlaceHolderApi.getPhoneNumberPost(AuthorID);

                call.enqueue(new Callback<AccountModel>() {
                    @Override
                    public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                        if (response.isSuccessful()) {
                            AccountModel accountModel = response.body();

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0"+accountModel.getPhoneNumber()));
                            startActivity(intent);
                        }
                        else {
                            Log.e("Message", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountModel> call, Throwable t) {
                        Log.e("Message failll", t.getMessage());
                    }
                });

            }
        });
    }

    private void getIdComponent() {

        tittle = findViewById(R.id.detail_title);
        detailType = findViewById(R.id.detail_type);
        detailPrice = findViewById(R.id.detail_price);
        address = findViewById(R.id.address);
        fullName = findViewById(R.id.user_name);
        typeAuthor = findViewById(R.id.user_type);
        inforDetail = findViewById(R.id.infor_detail);
        btnCall = (Button)findViewById(R.id.call);
    }


}