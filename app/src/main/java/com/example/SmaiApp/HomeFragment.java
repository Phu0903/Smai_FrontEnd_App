package com.example.SmaiApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.SmaiApp.Adapter.NewsAdapter;

import java.util.ArrayList;

import static com.example.SmaiApp.Helper.Helper.setListViewHeightBasedOnChildren;

public class HomeFragment extends Fragment {


    ListView lvNews;
    ArrayList<News> arrayNews;


//    button search
    Button btnSearch;

//    button thông báo
    ImageButton btnAnouncement;

//    button khám phá tặng cộng đồng
    ImageButton btnViewDonation;

//    button  top homepage
    Button btn_tangcongdong, btn_tangnguoingheo, btn_tangquytuthien, btn_quyengopcongich;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn_tangcongdong = view.findViewById(R.id.button_tangcongdong);
        btn_tangnguoingheo = view.findViewById(R.id.button_tangnguoingheo);
        btn_tangquytuthien = view.findViewById(R.id.button_tangquytuthien);
        btn_quyengopcongich = view.findViewById(R.id.button_quyengopcongich);

        btn_tangcongdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfirmAddress.class);
                startActivity(intent);
            }
        });

        btn_tangnguoingheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfirmAddress.class);
                startActivity(intent);
            }
        });

        btn_tangquytuthien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfirmAddress.class);
                startActivity(intent);
            }
        });

        btn_quyengopcongich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfirmAddress.class);
                startActivity(intent);
            }
        });


//      listview tin đăng mới
        lvNews = view.findViewById(R.id.listViewNews);
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
                getContext(),
                R.layout.row_news_listview,
                arrayNews
        );
        lvNews.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lvNews);

// open search screen
        btnSearch = view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Search.class);
                startActivity(intent);
            }
        });

//        open anouncement screen
        btnAnouncement = view.findViewById(R.id.btn_anouncement);

        btnAnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Announce.class);
                startActivity(intent);
            }
        });


//        open post donations
        btnViewDonation = view.findViewById(R.id.view_donations);

        btnViewDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PostDonation.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
