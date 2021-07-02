package com.smait.quyengop.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smait.quyengop.Controller.Adapter.UpLoadNewsAdapter;
import com.smait.quyengop.Model.PostNewsModel;
import com.smait.quyengop.Model.ProductModel;
import com.smait.quyengop.Controller.NetWorKing.ApiServices;
import com.smait.quyengop.Controller.NetWorKing.RetrofitClient;
import com.smait.quyengop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpLoadNewsFragment extends Fragment {

    Spinner spinner;
    //list view tin đang
    ListView lvNews;
    Button btnUpload;
    UpLoadNewsAdapter adapter;
    TextView tvNotLogin;
    String message="", token="";

    public static List<PostNewsModel> posts, postsTangCongDong, postsCanXinDo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_uploadnews, container, false);


        spinner = view.findViewById(R.id.spinner_dangtin);
        String[] paths = {"Tất cả tin đăng", "Tin tặng cộng đồng", "Tin cần xin đồ"};
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, paths);
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        MainActivity activity = (MainActivity) getActivity();
        String codeLogin  = activity.getMyData();

        String[] code = codeLogin.split(",");

        if (code.length != 0) {
            message = code[0];
            token = code[1];
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
            List<PostNewsModel> postsTinTangCongDong = new ArrayList<>();
            List<PostNewsModel> postsTinCanXinDo = new ArrayList<>();
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
                        for (int i=0;i<posts.size();i++) {
                            if (posts.get(i).getTypeAuthor().equals("Cá nhân") || posts.get(i).getTypeAuthor().equals("Quỹ/Nhóm từ thiện") ||
                                    posts.get(i).getTypeAuthor().equals("Tổ chức công ích")) {
                                postsTinCanXinDo.add(posts.get(i));
                            } else {
                                postsTinTangCongDong.add(posts.get(i));
                            }
                        }


                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(paths[position] == "Tin tặng cộng đồng") {
                                    adapter = new UpLoadNewsAdapter(getContext(), R.layout.row_uploadnews, postsTinTangCongDong);
                                    lvNews.setAdapter(adapter);
                                }
                                else if (paths[position] == "Tin cần xin đồ") {
                                    adapter = new UpLoadNewsAdapter(getContext(), R.layout.row_uploadnews, postsTinCanXinDo);
                                    lvNews.setAdapter(adapter);
                                } else {
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
                                Intent intent = new Intent(getActivity().getBaseContext(), DetailPostTinDang.class);
                                PostNewsModel post = posts.get(position);
                                String title = post.getTitle();

                                List<ProductModel> productModel = post.getNameProduct();
                                ArrayList<String> listNameCategory = new ArrayList<>();
                                ArrayList<String> listName = new ArrayList<>();
                                if (productModel.size() != 0) {
                                    String detailType = productModel.get(0).getCategory();
                                    for (int i=0;i<productModel.size();i++) {
                                        listNameCategory.add(productModel.get(i).getCategory());
                                        listName.add(productModel.get(i).getNameProduct());
                                    }
                                    intent.putExtra("detailType", detailType);
                                    intent.putStringArrayListExtra("ListName", listName);
                                    intent.putStringArrayListExtra("ListCatogory", listNameCategory);
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
        } else {
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(), Account.class);
                    startActivity(intent);

                }
            });
        }







        return view;


    }
}
