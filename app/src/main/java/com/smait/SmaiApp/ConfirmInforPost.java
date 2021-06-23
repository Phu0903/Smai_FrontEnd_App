package com.smait.SmaiApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smait.SmaiApp.Helper.UriUtils;
import com.smait.SmaiApp.Model.PostNewsModel;
import com.smait.SmaiApp.Model.ProductModel;
import com.smait.SmaiApp.NetWorKing.ApiServices;
import com.smait.SmaiApp.NetWorKing.RetrofitClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfirmInforPost extends AppCompatActivity {
    public AlertDialog waitingDialog;

    List<ProductModel> productModelList;

    List<Uri> uris = new ArrayList<Uri>();

    String mainToken="";

    String tokenMain;
    Button btn_confirm, btnPopup;

    TextView danhmuc, tieude, ghichu, city, district, ward, detailloction, banlaai, who;
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
        tokenMain = token;
//************************************************************************************************************

//ánh xạ các textview
        btnPopup = findViewById(R.id.popupDanhmuc);
        danhmuc = findViewById(R.id.textView12);
        tieude = findViewById(R.id.textView15);
        ghichu = findViewById(R.id.textView19);
        city = findViewById(R.id.textView24);
        district = findViewById(R.id.textView26);
        ward = findViewById(R.id.textView28);
        detailloction = findViewById(R.id.textView31);
        banlaai = findViewById(R.id.textView9);
        banlaai.setText("Bạn là ai*");

        who = findViewById(R.id.textView10);
        if (TypeAuthor.equals("Cá nhân")) {
            who.setText("Người nghèo/hoàn cảnh khó khăn");
        } else {
            who.setText(TypeAuthor);
        }
        danhmuc.setText("Danh mục nhận tặng     " + listCatogory.size());
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
        Log.d("Size list anh: ", String.valueOf(list.size()));

        waitingDialog = new SpotsDialog.Builder()
                .setContext(ConfirmInforPost.this)
                .setMessage("Đang đăng bài")
                .build();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waitingDialog.show();
                btn_confirm.setEnabled(false);
                Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);
                Call<PostNewsModel> call = jsonPlaceHolderApi.postNews("Bearer " + token, postNewsModel);
                final String[] idPost = new String[1];
                call.enqueue(new Callback<PostNewsModel>() {
                    @Override
                    public void onResponse(Call<PostNewsModel> call, Response<PostNewsModel> response) {
                        if (response.body() != null) {

                            if (fileList.size() != 0) {

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
                                        Log.e("errer", t.getMessage());
                                    }
                                });
                            } else {
                                Intent intent1 = new Intent(getApplicationContext(), CompleteActivity.class);
                                intent1.putExtra("Token", token);
                                intent1.putExtra("message", "OK");
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



        btnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ConfirmInforPost.this, PopupCategory.class);
                intent1.putExtra("listname", listName);
                startActivity(intent1);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar_danhmuc, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.button_cancel) {
            ConfirmCancel();
        }
        else {
            finish();
        }
        return true;
    }

    private void ConfirmCancel() {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(this);
        alerDialog.setTitle("Thông báo!");
        alerDialog.setMessage("Bạn có chắc muốn hủy không?");

        alerDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("Token", tokenMain);
                i.putExtra("message", "OK");
                startActivity(i);
            }
        });
        alerDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerDialog.show();
    }
}