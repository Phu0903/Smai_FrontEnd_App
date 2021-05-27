package com.example.SmaiApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.SmaiApp.Adapter.NewsAdapter;

import java.util.ArrayList;

public class UpLoadNewsFragment extends Fragment {

    private Spinner spinner;
    //list view tin đang
    ListView lvNews;
    ArrayList<News> arrayNews;
    Button btnUpload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_uploadnews, container, false);


        spinner = view.findViewById(R.id.spinner_dangtin);
        String[] paths = {"Tất cả tin đăng", "Tin tặng", "Tin bán"};
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, paths);
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        btnUpload = view.findViewById(R.id.create_post);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewpostType.class);
                startActivity(intent);
            }
        });

//      listview tin đăng mới
        lvNews = view.findViewById(R.id.listViewNews);
        arrayNews = new ArrayList<News>();

        arrayNews.add(new News(1, "0971037601", "Tin tặng", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews.add(new News(1, "0971037601", "Tin tặng", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews.add(new News(1, "0971037601", "Tin tặng", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews.add(new News(1, "0971037601", "Tin tặng", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));
        arrayNews.add(new News(1, "0971037601", "Tin tặng", "Dĩ An, Bình Dương", "Sách lớp 1",
                "Có trà xanh cần bán", "Miễn phí", "23/3/5/2021", R.drawable.pc1));

        NewsAdapter adapter = new NewsAdapter(
                getContext(),
                R.layout.row_news_listview,
                arrayNews
        );

        lvNews.setAdapter(adapter);


        return view;


    }
}
