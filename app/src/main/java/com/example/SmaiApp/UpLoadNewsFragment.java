package com.example.SmaiApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.SmaiApp.Adapter.UpLoadNewsAdapter;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.Model.ProductModel;
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

    Spinner spinner;
    //list view tin đang
    ListView lvNews;
    Button btnUpload;
    UpLoadNewsAdapter adapter;
    TextView tvNotLogin;
    String message="", token="";

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

        if (code.length != 0) {
            message = code[0];
            token = code[1];
            Log.d("Token Upload", token);
        }



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
            List<PostNewsModel> postsTinBan = new ArrayList<>();
            Retrofit retrofit = RetrofitClient.getRetrofitInstance();
            ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);
            Call<List<PostNewsModel>> call = jsonPlaceHolderApi.getUserPost("Bearer " + token);

            call.enqueue(new Callback<List<PostNewsModel>>() {
                @Override
                public void onResponse(Call<List<PostNewsModel>> call, Response<List<PostNewsModel>> response) {
                    if ( response.isSuccessful()) {


                        posts = response.body();
                        adapter = new UpLoadNewsAdapter(getContext(), R.layout.row_uploadnews, posts);
                        lvNews.setAdapter(adapter);

                        Log.d("onResponse: ", "hhhh");

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(paths[position] == "Tin bán") {
                                    adapter = new UpLoadNewsAdapter(getContext(), R.layout.row_uploadnews, postsTinBan);
                                    lvNews.setAdapter(adapter);
                                }
                                else if (paths[position] == "Tất cả tin đăng" || paths[position] == "Tin tặng") {
                                    adapter = new UpLoadNewsAdapter(getContext(), R.layout.row_uploadnews, posts);
                                    lvNews.setAdapter(adapter);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                adapter = new UpLoadNewsAdapter(getContext(), R.layout.row_uploadnews, posts);
                                lvNews.setAdapter(adapter);
                            }
                        });

                        lvNews.setItemsCanFocus(true);

                        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.d("onResponse: ", "yeu bong beo");
                                Intent intent = new Intent(getActivity().getBaseContext(), DetailPostTinDang.class);
                                PostNewsModel post = posts.get(position);
                                String title = post.getTitle();

                                List<ProductModel> productModel = post.getNameProduct();
                                ArrayList<String> listNameCategory = new ArrayList<>();
                                if (productModel.size() != 0) {
                                    String detailType = productModel.get(0).getCategory();
                                    for (int i=0;i<productModel.size();i++) {
                                        listNameCategory.add(productModel.get(i).getCategory());
                                    }
                                    intent.putExtra("detailType", detailType);
                                    intent.putStringArrayListExtra("ListName", listNameCategory);
                                }
                                String address = post.getAddress();
                                String fullName = post.getNameAuthor();
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
                }
                @Override
                public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {

                }
            });
        }







        return view;


    }
}
/*
lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Log.d("Message", "Bông béooooo");
                                Intent intent = new Intent(getActivity().getBaseContext(), DetailPostTinDang.class);
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
 */