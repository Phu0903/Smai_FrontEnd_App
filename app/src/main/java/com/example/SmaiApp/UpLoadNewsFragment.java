package com.example.SmaiApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class UpLoadNewsFragment extends Fragment {

    private Spinner spinner;
    //list view tin đang
    ListView lvNews;
    Button btnUpload;
    NewsAdapter adapter;
    TextView tvNotLogin;
    public static List<PostNewsModel> posts;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_uploadnews, container, false);


        spinner = view.findViewById(R.id.spinner_dangtin);
        String[] paths = {"Tất cả tin đăng", "Tin tặng", "Tin bán"};
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, paths);
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        MainActivity activity = (MainActivity) getActivity();
        String codeLogin  = activity.getMyData();

        String[] code = codeLogin.split(",");

        String message = code[0];
        String token = code[1];
        Log.d("Token Upload", token);


        btnUpload = view.findViewById(R.id.create_post);


//      listview tin đăng mới
        lvNews = view.findViewById(R.id.listViewNews);
        tvNotLogin = view.findViewById(R.id.notlogin);

        if (message.equals("OK")) {

            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(getActivity().getBaseContext(), NewpostType.class);
                    intent.putExtra("Token", token);
                    getActivity().startActivity(intent);
                }
            });
            // get data
            tvNotLogin.setVisibility(View.GONE);
            lvNews.setVisibility(View.VISIBLE);
            Retrofit retrofit = RetrofitClient.getRetrofitInstance();
            ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);
            Call<List<PostNewsModel>> call = jsonPlaceHolderApi.getUserPost("Bearer " + token);

            call.enqueue(new Callback<List<PostNewsModel>>() {
                @Override
                public void onResponse(Call<List<PostNewsModel>> call, Response<List<PostNewsModel>> response) {
                    if ( response.isSuccessful()) {


                    posts = response.body();

                    adapter = new NewsAdapter(
                            getContext(),
                            R.layout.row_news_listview,
                            posts);
                    lvNews.setAdapter(adapter);
                    }
                }
                @Override
                public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
                    Toast.makeText(getContext(), "Failllllllllllllllll", Toast.LENGTH_LONG).show();
                }
            });
        }







        return view;


    }
}
