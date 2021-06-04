package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ConfirmInforPost extends AppCompatActivity {

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
        List<Uri> uris = new ArrayList<Uri>();
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














        //Click btn_confirm
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmInforPost.this, CompleteActivity.class);
                startActivity(intent);
            }
        });
    }
}