package com.example.SmaiApp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.Model.ProductModel;
import com.example.SmaiApp.News;
import com.example.SmaiApp.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.internal.Util;

public class PostDonationAdapter extends BaseAdapter {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT_24 = "HH:mm";
    public PostDonationAdapter(Context context, int layout, List<PostNewsModel> newsList) {
        myContext = context;
        myLayout = layout;
        arrayNews = newsList;
    }

    Context myContext;
    int myLayout;
    List<PostNewsModel> arrayNews;

    @Override
    public int getCount() {
        return arrayNews.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//ngày giờ đăng tin
        Date date1 = arrayNews.get(position).getCreatedAt();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long timeUp = calendar.getTimeInMillis();
        long hourRest = currentTime - timeUp;
        int hours   = (int) ((hourRest / (1000*60*60)) % 24);
        String getHour = "";
        if (hours < 24) {
            getHour = hours + " h";
        }
        else if (hours >= 24 && hours < 48) {
            getHour = "1 ngày";
        }
        else if (hours >=48 && hours < 72) {
            getHour = "2 ngày";
        }
        else if (hours >=72 && hours < 96) {
            getHour = "3 ngày";
        }
        else {
            getHour = "Hơn 3 ngày";
        }


        String address = arrayNews.get(position).getAddress();
        String[] mainAddress = address.split(",");

        convertView = inflater.inflate(myLayout, null);

        TextView txtName = convertView.findViewById(R.id.tv_tittle);
        txtName.setText(arrayNews.get(position).getTitle());


//        TextView txtNote = convertView.findViewById(R.id.tv_note);
//        txtNote.setText(arrayNews.get(position).get);

        TextView txtAddress = convertView.findViewById(R.id.tv_address);
        txtAddress.setText(mainAddress[0] + ", " + mainAddress[1]);

        TextView txtTypesNews = convertView.findViewById(R.id.tv_typenews);
        List<ProductModel> productModels = arrayNews.get(0).getNameProduct();


        ProductModel model1 = productModels.get(0);
        String nameCatogory = model1.getCategory();

        txtTypesNews.setText(nameCatogory);


        TextView txtDatePost = convertView.findViewById(R.id.tv_datepost);
        txtDatePost.setText(getHour);

        List<String> listUrl = arrayNews.get(position).getUrlImage();
        if (listUrl.size() != 0) {
            String url = listUrl.get(0);
            ImageView imgHinh = convertView.findViewById(R.id.img_hinh);

            Glide.with(convertView).load(url).into(imgHinh);
        }
        return convertView;
    }
}
