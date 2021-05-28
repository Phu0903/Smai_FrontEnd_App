package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import com.example.SmaiApp.Adapter.NewsAdapter;

import java.util.ArrayList;

import static com.example.SmaiApp.Helper.Helper.setListViewHeightBasedOnChildren;

public class ViewHistory extends AppCompatActivity {

    ListView lvHistory;
    ArrayList<News> arrayNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

//        Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_history);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //      listview tin đăng mới
        lvHistory = findViewById(R.id.listViewHistory);
        arrayNews = new ArrayList<News>();

        arrayNews.add(new News(1, "0971037601", "Học tập", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews.add(new News(1, "0971037601", "Học tập", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews.add(new News(1, "0971037601", "Học tập", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews.add(new News(1, "0971037601", "Học tập", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews.add(new News(1, "0971037601", "Học tập", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));

        NewsAdapter adapter = new NewsAdapter(
                this,
                R.layout.row_news_listview,
                arrayNews
        );
        lvHistory.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lvHistory);
    }
}