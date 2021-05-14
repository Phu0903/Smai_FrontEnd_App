package com.example.SmaiApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_uploadnews, container, false);



        spinner = view.findViewById(R.id.spinner_dangtin);
        String[] paths = {"Tất cả", "item 2", "item 3"};
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, paths);
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

//      listview tin đăng mới
        lvNews = view.findViewById(R.id.listViewNews);
        arrayNews = new ArrayList<News>();

        arrayNews.add(new News(1, "0906729189", "Đồ dùng",
                "Dĩ An, Bình Dương", "Bạn cho","Có kệ cũ cần cho","Miễn phí",R.drawable.kesach));
        arrayNews.add(new News(1, "0906729189", "Sách vở",
                "Dĩ An, Bình Dương", "Bạn bán","Sách dư cần bán","Liên hệ giá",R.drawable.sacdoisong));
        arrayNews.add(new News(1, "0906729189", "Sách vở",
                "Dĩ An, Bình Dương", "Bạn cho","Thanh lý một số sách","400.000đ",R.drawable.sachthanly));
        arrayNews.add(new News(1, "0906729189", "Sách vở",
                "Dĩ An, Bình Dương", "Bạn cho","Có kệ cũ cần cho","Miễn phí",R.drawable.kesach));
        arrayNews.add(new News(1, "0906729189", "Sách vở",
                "Dĩ An, Bình Dương", "Bạn cho","Có kệ cũ cần cho","Miễn phí",R.drawable.kesach));
        arrayNews.add(new News(1, "0906729189", "Sách vở",
                "Dĩ An, Bình Dương", "Bạn cho","Có kệ cũ cần cho","Miễn phí",R.drawable.kesach));

        NewsAdapter adapter = new NewsAdapter(
                getContext(),
                R.layout.row_news_listview,
                arrayNews
        );

        lvNews.setAdapter(adapter);


        return view;


    }
}
