package com.example.SmaiApp.NetWorKing;

import com.example.SmaiApp.Model.AccountModel;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.Model.UserModel;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiServices {

//    https://smai-back-end.herokuapp.com/post/getNewPost?fbclid=IwAR3ziFlpeKc4_ksCcr5EkAPA5yGy4Km3N8QUP_AyC09YNYX9IKAUMIM-1dA
//lấy tin đăng mới
    @GET("post/getNewpost")
    Call<List<PostNewsModel>> getPost();

//lấy tin đăng tặng cộng đồng
    //https://smai-back-end.herokuapp.com/post/getPostByTypeAuthor?typeauthor=%7BLoaij
    @GET("post/getPostByTypeAuthor")
    Call<List<PostNewsModel>> getPostGiveCommunity(@Query("typeauthor") String author);

    @GET("post/GetPostByAccountID")
    Call<List<PostNewsModel>> getUserPost(@Header("Authorization") String authHeader);

// Thông tin user
    @GET("user/getInForUserByTokenId")
    Call<UserModel> getInforUser(@Header("Authorization") String authHeader);

//    https://smai-back-end.herokuapp.com/account/login
    @POST("account/login")
    Call<AccountModel> login(@Body AccountModel accountModel);

    @POST("account/register")
    Call<AccountModel> signup(@Body AccountModel accountModel);


    @POST("post/CreatePost")
    Call<PostNewsModel> postNews(@Header("Authorization") String authHeader, @Body PostNewsModel model);

    @Multipart
    @POST("post/UpdatePost")
    Call<PostNewsModel> updateImagePost(@Header("idpost") String idpost, @Part List<MultipartBody.Part> productImage);


    @POST("post/TestToken")
    Call<String> testToken(@Header("Authorization") String authHeader);

    @POST("post/TestIamge")
    Call<ResponseBody> postFile( @Header("Authorization") String authHeader, @Part MultipartBody.Part productImage);

}
