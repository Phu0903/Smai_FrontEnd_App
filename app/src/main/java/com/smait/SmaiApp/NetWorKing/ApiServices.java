package com.smait.SmaiApp.NetWorKing;

import com.smait.SmaiApp.Model.AccountModel;
import com.smait.SmaiApp.Model.PostNewsModel;
import com.smait.SmaiApp.Model.UserModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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

//    lấy số điện thoại tin đăng
    @GET("user/getPhonNumber")
    Call<AccountModel> getPhoneNumberPost(@Query("AuthorID") String authorId);


//    https://smai-back-end.herokuapp.com/account/login
    @POST("account/login")
    Call<AccountModel> login(@Body AccountModel accountModel);

    @POST("account/register")
    Call<AccountModel> signup(@Body AccountModel accountModel);

    @POST("account/getPhone")
    Call<String> checkPhoneNumber(@Body AccountModel accountModel);

//create news
    @POST("post/CreatePost")
    Call<PostNewsModel> postNews(@Header("Authorization") String authHeader, @Body PostNewsModel model);

//    up news
    @Multipart
    @POST("post/UpdatePost")
    Call<PostNewsModel> updateImagePost(@Header("idpost") String idpost, @Part List<MultipartBody.Part> productImage);


//    delete post
    @DELETE("post/deletePostbyUser")
    Call<String> deleteNews(@Query("_id") String id);

//    reset password  account/Forgot

    @POST("account/Forgot")
    Call<String> getNewPassword(@Body AccountModel accountModel);

    @POST("post/TestToken")
    Call<String> testToken(@Header("Authorization") String authHeader);

    @POST("post/TestIamge")
    Call<ResponseBody> postFile( @Header("Authorization") String authHeader, @Part MultipartBody.Part productImage);

}
