package com.example.SmaiApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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

public class UpLoadNewsFragment extends Fragment {

    private Spinner spinner;
    //list view tin đang
    ListView lvNews;
    ArrayList<PostNewsModel> arrayNews;
    Button btnUpload;
    NewsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_uploadnews, container, false);


        spinner = view.findViewById(R.id.spinner_dangtin);
        String[] paths = {"Tất cả tin đăng", "Tin tặng", "Tin bán"};
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, paths);
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        btnUpload = view.findViewById(R.id.create_post);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewpostType.class);
                startActivity(intent);
            }
        });

//      listview tin đăng mới
        lvNews = view.findViewById(R.id.listViewNews);
        arrayNews = new ArrayList<PostNewsModel>();


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

                List<PostNewsModel> posts = response.body();

                adapter = new NewsAdapter(
                        getContext(),
                        R.layout.row_news_listview,
                        posts
                );

            }

            @Override
            public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Failllllllllllllllll", Toast.LENGTH_LONG).show();
            }
        });





        lvNews.setAdapter(adapter);


        return view;


    }
}
