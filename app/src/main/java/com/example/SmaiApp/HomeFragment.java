package com.example.SmaiApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class HomeFragment extends Fragment {


    ListView lvNews;


//    button search
    Button btnSearch;

//    button thông báo
    ImageButton btnAnouncement;

//    button khám phá tặng cộng đồng
    ImageButton btnViewDonation;

//    button  top homepage
    Button btn_tangcongdong, btn_tangnguoingheo, btn_tangquytuthien, btn_quyengopcongich;
    NewsAdapter adapter;
    public static List<PostNewsModel> posts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn_tangcongdong = view.findViewById(R.id.button_tangcongdong);
        btn_tangnguoingheo = view.findViewById(R.id.button_tangnguoingheo);
        btn_tangquytuthien = view.findViewById(R.id.button_tangquytuthien);
        btn_quyengopcongich = view.findViewById(R.id.button_quyengopcongich);

        btn_tangcongdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfirmAddress.class);
                startActivity(intent);
            }
        });

        btn_tangnguoingheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfirmAddress2.class);
                startActivity(intent);
            }
        });

        btn_tangquytuthien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfirmAddress2.class);
                startActivity(intent);
            }
        });

        btn_quyengopcongich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfirmAddress2.class);
                startActivity(intent);
            }
        });


        //      listview tin đăng mới
        lvNews = view.findViewById(R.id.listViewNews);
//        ******************************************************************************************
// get data
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

        Call<List<PostNewsModel>> call = jsonPlaceHolderApi.getPost();

        call.enqueue(new Callback<List<PostNewsModel>>() {
            @Override
            public void onResponse(Call<List<PostNewsModel>> call, Response<List<PostNewsModel>> response) {
                if ( !response.isSuccessful()) {
                    Toast.makeText(getContext(), "Fail", Toast.LENGTH_LONG).show();
                    return;
                }
                posts = response.body();

                adapter = new NewsAdapter(
                        getContext(),
                        R.layout.row_news_listview,
                        posts);
                lvNews.setAdapter(adapter);
                lvNews.setEnabled(false);
                setListViewHeightBasedOnChildren(lvNews);
            }
            @Override
            public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Failllllllllllllllll", Toast.LENGTH_LONG).show();
            }
        });








// open search screen
        btnSearch = view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Search.class);
                startActivity(intent);
            }
        });

//        open anouncement screen
        btnAnouncement = view.findViewById(R.id.btn_anouncement);

        btnAnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Announce.class);
                startActivity(intent);
            }
        });


//        open post donations
        btnViewDonation = view.findViewById(R.id.view_donations);

        btnViewDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PostDonation.class);
                startActivity(intent);
            }
        });







        return view;
    }


}
