package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.SmaiApp.Adapter.NewsAdapter;

import java.util.ArrayList;

import static com.example.SmaiApp.Helper.Helper.setListViewHeightBasedOnChildren;


public class PostDonation extends AppCompatActivity {

    ListView lvNews_New;
    ArrayList<News> arrayNews_New;

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


        //      listview tin đăng mới
        lvNews_New = findViewById(R.id.listViewNews_New);
        arrayNews_New = new ArrayList<News>();

        arrayNews_New.add(new News(1, "0971037601", "Học tập", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews_New.add(new News(1, "0971037601", "Học tập", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews_New.add(new News(1, "0971037601", "Học tập", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews_New.add(new News(1, "0971037601", "Học tập", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews_New.add(new News(1, "0971037601", "Học tập", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));

        NewsAdapter adapter = new NewsAdapter(PostDonation.this, R.layout.row_news_listview, arrayNews_New);
        lvNews_New.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lvNews_New);

    }
}


