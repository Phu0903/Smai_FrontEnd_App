package com.example.SmaiApp;

import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.SmaiApp.Adapter.NewsAdapter;
import com.example.SmaiApp.Helper.LVHelper;
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
    public static List<PostNewsModel> posts;

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

        String message = code[0];
        String token = code[1];





        btn_tangcongdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.equals("OK")) {
                    Intent intent = new Intent(getContext(), ConfirmAddress.class);
                    intent.putExtra("Token", token);
                    intent.putExtra("Message", message);
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

                adapter = new NewsAdapter(
                        getContext(),
                        R.layout.row_news_listview,
                        posts);
                lvNews.setAdapter(adapter);
//                setListViewHeightBasedOnChildren(lvNews);


                lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(getActivity().getBaseContext(), DetailPost.class);
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
                        getActivity().startActivity(intent);


                    }
                });
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
