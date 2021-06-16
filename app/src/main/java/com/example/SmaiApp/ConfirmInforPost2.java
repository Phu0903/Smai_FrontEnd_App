package com.example.SmaiApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.CursorLoader;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
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

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfirmInforPost2 extends AppCompatActivity {

    public AlertDialog waitingDialog;
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
        setContentView(R.layout.activity_confirm_infor_post2);

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
//        for (final Bitmap b:bitmaps) {
//            listImage[count].setImageBitmap(b);
//            count = count + 1;
//        }
        for (int i=0;i< bitmaps.size();i++) {
            listImage[i].setImageBitmap(bitmaps.get(i));
        }

        String address = intent.getStringExtra("address");
        ArrayList<String> listName = intent.getStringArrayListExtra("ListName");
        ArrayList<String> listCatogory = intent.getStringArrayListExtra("ListCatogary");
        String loinhan = intent.getStringExtra("loinhan");
        String mota = intent.getStringExtra("mota");
        String token = intent.getStringExtra("token");
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




        mainToken = token;

        //Click btn_confirm
        btn_confirm = findViewById(R.id.btn_confirm);
        List<String> listFilePath = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        for (int i = 0; i< uris.size();i++) {
            String filePath = UriUtils.getPathFromUri(this, uris.get(i));
            File file = new File(filePath);
            fileList.add(file);

        }


        List<MultipartBody.Part> list = new ArrayList<>();
        for (int i = 0; i< uris.size();i++) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileList.get(i));
            MultipartBody.Part body = MultipartBody.Part.createFormData("productImage", fileList.get(i).getName(), requestFile);
            list.add(body);
        }
        waitingDialog = new SpotsDialog.Builder()
                .setContext(ConfirmInforPost2.this)
                .setMessage("Đang đăng bài")
                .build();
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_confirm.setEnabled(false);
                waitingDialog.show();
                Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

                Call<PostNewsModel> call = jsonPlaceHolderApi.postNews("Bearer " + token, postNewsModel);
                final String[] idPost = new String[1];
                call.enqueue(new Callback<PostNewsModel>() {
                    @Override
                    public void onResponse(Call<PostNewsModel> call, Response<PostNewsModel> response) {
                        if (response.body() != null) {

                            if (fileList.size() != 0 ) {
                                idPost[0] = response.body().getIdpost();
                                Call<PostNewsModel> call1 = jsonPlaceHolderApi.updateImagePost(idPost[0], list);

                                call1.enqueue(new Callback<PostNewsModel>() {
                                    @Override
                                    public void onResponse(Call<PostNewsModel> call, Response<PostNewsModel> response) {
                                        Intent intent1 = new Intent(getApplicationContext(), CompleteActivity.class);
                                        intent1.putExtra("Token", token);
                                        intent1.putExtra("message", "OK");
                                        startActivity(intent1);
                                    }

                                    @Override
                                    public void onFailure(Call<PostNewsModel> call, Throwable t) {

                                    }
                                });
                            } else {
                                Intent intent1 = new Intent(getApplicationContext(), CompleteActivity.class);
                                intent1.putExtra("Token", token);
                                intent1.putExtra("message", "OK");
                                intent1.putExtra("Require", "tang");
                                startActivity(intent1);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<PostNewsModel> call, Throwable t) {
                        Log.e("Error", t.getMessage());
                    }
                });



            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}