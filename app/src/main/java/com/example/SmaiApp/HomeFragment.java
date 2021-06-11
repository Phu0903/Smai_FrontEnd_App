package com.example.SmaiApp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.SmaiApp.Adapter.NewsAdapter;
import com.example.SmaiApp.Adapter.NewsRecyclerviewAdapter;
import com.example.SmaiApp.Helper.LVHelper;

import com.example.SmaiApp.Helper.SpaceDividerItemDecoration;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.Model.ProductModel;
import com.example.SmaiApp.NetWorKing.ApiServices;
import com.example.SmaiApp.NetWorKing.RetrofitClient;

import java.io.Serializable;
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
    RecyclerView recyclerView;
    public static List<PostNewsModel> posts;
    String message = "";
    String token ="";
    NewsRecyclerviewAdapter recyclerviewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn_tangcongdong = view.findViewById(R.id.button_tangcongdong);
        btn_tangnguoingheo = view.findViewById(R.id.button_tangnguoingheo);
        btn_tangquytuthien = view.findViewById(R.id.button_tangquytuthien);
        btn_quyengopcongich = view.findViewById(R.id.button_quyengopcongich);


        MainActivity activity = (MainActivity) getActivity();
        String codeLogin  = activity.getMyData();
        String[] code = codeLogin.split(",");

        if (code.length != 0) {
            message = code[0];
            token = code[1];
        }




        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        DividerItemDecoration  mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        SpaceDividerItemDecoration a = new SpaceDividerItemDecoration(10, false);
        recyclerviewAdapter = new NewsRecyclerviewAdapter(recyclerView.getContext(), posts);
        recyclerView.setAdapter(recyclerviewAdapter);
        recyclerviewAdapter.notifyDataSetChanged();
        recyclerView.addItemDecoration(a);
        recyclerView.addItemDecoration(mDividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        btn_tangcongdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.equals("OK")) {
                    Intent intent = new Intent(getContext(), ConfirmAddress.class);
                    intent.putExtra("Token", token);
                    intent.putExtra("Message", message);
                    intent.putExtra("TypeAuthor", "tangcongdong");
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Đăng nhập để thực hiện chức năng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_tangnguoingheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.equals("OK")) {
                    Intent intent = new Intent(getContext(), ConfirmAddress2.class);
                    intent.putExtra("Token", token);
                    intent.putExtra("Message", message);
                    intent.putExtra("TypeAuthor", "canhan");
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Đăng nhập để thực hiện chức năng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_tangquytuthien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.equals("OK")) {
                    Intent intent = new Intent(getContext(), ConfirmAddress2.class);
                    intent.putExtra("Token", token);
                    intent.putExtra("Message", message);
                    intent.putExtra("TypeAuthor", "quy");
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Đăng nhập để thực hiện chức năng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_quyengopcongich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.equals("OK")) {
                    Intent intent = new Intent(getContext(), ConfirmAddress2.class);
                    intent.putExtra("Token", token);
                    intent.putExtra("Message", message);
                    intent.putExtra("TypeAuthor", "tochuc");
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Đăng nhập để thực hiện chức năng", Toast.LENGTH_SHORT).show();
                }
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

                recyclerviewAdapter = new NewsRecyclerviewAdapter(recyclerView.getContext(), posts);
                recyclerView.setAdapter(recyclerviewAdapter);
                recyclerviewAdapter.notifyDataSetChanged();

            }
            @Override
            public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failllllllllllllllll", Toast.LENGTH_LONG).show();
            }
        });








// open search screen
        btnSearch = view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Search.class);
                intent.putExtra("Token", token);
                intent.putExtra("Message", message);
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
