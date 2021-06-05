package com.example.SmaiApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SmaiApp.Helper.UriUtils;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.Model.ProductModel;
import com.example.SmaiApp.NetWorKing.ApiServices;
import com.example.SmaiApp.NetWorKing.RetrofitClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfirmInforPost extends AppCompatActivity {


    List<ProductModel> productModelList;

    List<Uri> uris = new ArrayList<Uri>();

    String mainToken="";

    Button btn_confirm;

    TextView danhmuc, tieude, ghichu, city, district, ward, detailloction;
    ImageView imgView1, imgView2, imgView3, imgView4, imgView5;
    ImageView[] listImage = new ImageView[5];
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_infor_post);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_confirm);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        Add back button at toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgView1 = findViewById(R.id.dt_imaage_view1);
        imgView2 = findViewById(R.id.dt_imaage_view2);
        imgView3 = findViewById(R.id.dt_imaage_view3);
        imgView4 = findViewById(R.id.dt_imaage_view4);
        imgView5 = findViewById(R.id.dt_imaage_view5);
        listImage[0] = imgView1;
        listImage[1] = imgView2;
        listImage[2] = imgView3;
        listImage[3] = imgView4;
        listImage[4] = imgView5;
        count=0;


//        Nhận data từ Detail Activity ***************************
        Intent intent = getIntent();
//        lấy uri của hình ảnh
        uris = intent.getParcelableArrayListExtra("Uri");
        List<Bitmap> bitmaps = new ArrayList<>();

// Chuyển uri sang bitmap
        for (Uri uri: uris) {
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            bitmaps.add(bitmap);
        }
        for (final Bitmap b:bitmaps) {
            listImage[count].setImageBitmap(b);
            count = count + 1;
        }

        String address = intent.getStringExtra("address");
        ArrayList<String> listName = intent.getStringArrayListExtra("ListName");
        ArrayList<String> listCatogory = intent.getStringArrayListExtra("ListCatogary");
        String loinhan = intent.getStringExtra("loinhan");
        String mota = intent.getStringExtra("mota");
        String token = intent.getStringExtra("token");
        Log.d("Token Confirm", token);
        String TypeAuthor = intent.getStringExtra("TypeAuthor");
//************************************************************************************************************

//ánh xạ các textview

        danhmuc = findViewById(R.id.textView12);
        tieude = findViewById(R.id.textView15);
        ghichu = findViewById(R.id.textView19);
        city = findViewById(R.id.textView24);
        district = findViewById(R.id.textView26);
        ward = findViewById(R.id.textView28);
        detailloction = findViewById(R.id.textView31);

        danhmuc.setText(listCatogory.get(0));
        tieude.setText(loinhan);
        ghichu.setText(mota);
        String[] getAddress = address.split(",");
        city.setText(getAddress[getAddress.length-1]);
        district.setText(getAddress[getAddress.length-2]);
        ward.setText(getAddress[1]);
        detailloction.setText(getAddress[0]);

//gán giá trị cho postNewModel
        PostNewsModel postNewsModel;
        postNewsModel = new PostNewsModel();


        Date currentTime = Calendar.getInstance().getTime();

        productModelList = new ArrayList<>();
//        chuyển list nameproduct sang list<product
        for (int i=0;i< listCatogory.size(); i++) {
            ProductModel productModel = new ProductModel();
            productModel.setCategory(listCatogory.get(i));
            productModel.setNameProduct(listName.get(i));
            productModelList.add(productModel);
        }
        postNewsModel.setNameProduct(productModelList);
        postNewsModel.setCreatedAt(currentTime);
        postNewsModel.setUpdatedAt(currentTime);
        postNewsModel.setAuthorID(token);
        postNewsModel.setAddress(address);
        postNewsModel.setTypeAuthor(TypeAuthor);
        postNewsModel.setTitle(loinhan);
        postNewsModel.setNote(mota);

//        PostNewsModel p1 = new PostNewsModel(mota,);

        if (postNewsModel != null) {
            Log.d("Value postnewmodel", String.valueOf(postNewsModel));
        }
        else {
            Log.e("Value postnewmodel", "model nulll");
        }


        mainToken = token;

        //Click btn_confirm
        btn_confirm = findViewById(R.id.btn_confirm);


        Uri uri = uris.get(0);
        Uri uri1 = uris.get(1);
        Uri uri2 = uris.get(2);
        Uri uri3 = uris.get(3);
        Uri uri4 = uris.get(4);

        String fullFilePath = UriUtils.getPathFromUri(this, uri2);
        String filePath = UriUtils.getPathFromUri(this, uri);
        String filePath1 = UriUtils.getPathFromUri(this, uri1);
        String filePath2 = UriUtils.getPathFromUri(this, uri2);
        String filePath3 = UriUtils.getPathFromUri(this, uri3);
        String filePath4 = UriUtils.getPathFromUri(this, uri4);

        if (fullFilePath != null) {
            Log.d("Fullfilepath", fullFilePath);
        }
        File file = new File(filePath);
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);
        File file3 = new File(filePath3);
        File file4 = new File(filePath4);

        List<File> fileList = new ArrayList<>();
        fileList.add(file);
        fileList.add(file1);
        fileList.add(file2);
        fileList.add(file3);
        fileList.add(file4);
        postNewsModel.setProductImage(fileList);

        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("productImage", file.getName(), requestFile);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse(getContentResolver().getType(uri1)), file1);
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("productImage", file1.getName(), requestFile1);
        RequestBody requestFile2 = RequestBody.create(MediaType.parse(getContentResolver().getType(uri2)), file2);
        MultipartBody.Part body2 = MultipartBody.Part.createFormData("productImage", file2.getName(), requestFile2);
        RequestBody requestFile3 = RequestBody.create(MediaType.parse(getContentResolver().getType(uri3)), file3);
        MultipartBody.Part body3 = MultipartBody.Part.createFormData("productImage", file3.getName(), requestFile3);
        RequestBody requestFile4 = RequestBody.create(MediaType.parse(getContentResolver().getType(uri4)), file4);
        MultipartBody.Part body4 = MultipartBody.Part.createFormData("productImage", file4.getName(), requestFile4);

        List<MultipartBody.Part> list = new ArrayList<>();

        list.add(body);
        list.add(body1);
        list.add(body2);
        list.add(body3);
        list.add(body4);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

//                File file = new File(fullFilePath);
//                RequestBody requestFile =
//                        RequestBody.create(MediaType.parse(getContentResolver().getType(uri2)), file);
//                MultipartBody.Part body =
//                        MultipartBody.Part.createFormData("productImage", file.getName(), requestFile);
//
//                String descriptionString = "hello, this is description speaking";
//                RequestBody description =
//                        RequestBody.create(
//                                okhttp3.MultipartBody.FORM, descriptionString);
//                Call<ResponseBody> call = jsonPlaceHolderApi.postFile("Bearer " + mainToken, body);
//
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            Log.d("Upload", "success messs");
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("Upload error:", t.getMessage());
//                    }
//                });


                Call<PostNewsModel> call = jsonPlaceHolderApi.postNews("Bearer " + token, list,postNewsModel);

                call.enqueue(new Callback<PostNewsModel>() {
                    @Override
                    public void onResponse(Call<PostNewsModel> call, Response<PostNewsModel> response) {
                        Intent intent1 = new Intent(getApplicationContext(), CompleteActivity.class);
                        startActivity(intent1);
                    }

                    @Override
                    public void onFailure(Call<PostNewsModel> call, Throwable t) {
                        Log.e("Error", t.getMessage());
                    }
                });


            }
        });
    }

}