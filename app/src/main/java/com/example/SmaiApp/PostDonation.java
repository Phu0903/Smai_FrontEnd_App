package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


public class PostDonation extends AppCompatActivity {
    int[] imageIDs = {R.drawable.pc1, R.drawable.pc2, R.drawable.pc3};
    int image1 = R.drawable.pc1;
    int image2 = R.drawable.pc2;
    int image3 = R.drawable.pc3;

    post temp1 = new post(1, image1, "Messi");
    post temp2 = new post(2, image2, "Messi");
    post temp3 = new post(3, image3, "Messi");

    post[] listPost = {temp1, temp2, temp3};

    Spinner spinnerLocation;
    Spinner spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_donation);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dt);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinnerLocation = findViewById(R.id.spinner);
        ArrayList<String> arrayLocation = new ArrayList<String>();
        arrayLocation.add("Tp.HCM");
        arrayLocation.add("Đồng Nai");
        arrayLocation.add("Thủ Đức");
        spinnerType = findViewById(R.id.spinner_type);
        ArrayList<String> arrayType = new ArrayList<String>();
        arrayType.add("Tất cả");
        arrayType.add("Đồ dùng");
        arrayType.add("Giấy vụn");
        arrayType.add("Đồ gia dụng");

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayLocation);
        spinnerLocation.setAdapter(arrayAdapter1);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayType);
        spinnerType.setAdapter(arrayAdapter2);
//        FrameLayout frame = findViewById(R.id.frame_layout);
//        LinearLayout linearLayout = findViewById(R.id.linearlayout);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//        for(post p : listPost){
//            LinearLayout temp = new LinearLayout(this);
//
//            ImageView iv = new ImageView(this);
//            TextView tv = new TextView(this);
//            tv.setText(p.name);
//            iv.setImageResource(p.image);
//            FrameLayout.LayoutParams param1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
//            , ViewGroup.LayoutParams.MATCH_PARENT);
//            param1.width = 200;
//            param1.height = 250;
//            iv.setLayoutParams(param1);
//
//            FrameLayout.LayoutParams param2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
//                    , ViewGroup.LayoutParams.WRAP_CONTENT);
//            tv.setLayoutParams(param2);
//            tv.setTextSize(15);
//            temp.addView(iv);
//            temp.addView(tv);
//            linearLayout.addView(temp);
//        }
    }
}

//instance post
class post {
    int id;
    int image;
    String name;

    public post(int id, int image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }
}