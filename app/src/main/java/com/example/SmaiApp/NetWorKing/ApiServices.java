package com.example.SmaiApp.NetWorKing;

import com.example.SmaiApp.Model.PostNewsModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiServices {

//    https://smai-back-end.herokuapp.com/post/getNewPost?fbclid=IwAR3ziFlpeKc4_ksCcr5EkAPA5yGy4Km3N8QUP_AyC09YNYX9IKAUMIM-1dA
    //https://smai-back-end.herokuapp.com/post/getPostByTypeAuthor?typeauthor=%7BLoaij
    @GET("post/getNewpost")
    Call<List<PostNewsModel>> getPost();

    @GET("post/getPostByTypeAuthor")
    Call<List<PostNewsModel>> getPostGiveCommunity(@Query("typeauthor") String author);
}
