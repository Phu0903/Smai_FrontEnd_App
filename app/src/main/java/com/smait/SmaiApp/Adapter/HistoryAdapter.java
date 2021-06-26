package com.smait.SmaiApp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smait.SmaiApp.Model.PostNewsModel;
import com.smait.SmaiApp.Model.ProductModel;
import com.smait.SmaiApp.NetWorKing.ApiServices;
import com.smait.SmaiApp.NetWorKing.RetrofitClient;
import com.smait.SmaiApp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HistoryAdapter extends BaseAdapter {

    public HistoryAdapter(Context context, int layout, List<PostNewsModel> newsList) {
        myContext = context;
        myLayout = layout;
        arrayNews = newsList;
    }

    Context myContext;
    int myLayout;
    List<PostNewsModel> arrayNews;

    @Override
    public int getCount() {
        if (arrayNews != null) {
            return arrayNews.size();
        }
        return 0;
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
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm, dd/MM/yyyy "); //format ngày giờ thành dd/mm/yyyy hh:mm
        String sTime = localDateFormat.format(date1);


        String address = arrayNews.get(position).getAddress();
        String[] mainAddress = address.split(",");

        convertView = inflater.inflate(myLayout, null);

        TextView txtDate = convertView.findViewById(R.id.typePost);
        txtDate.setText(sTime);


        TextView txtName = convertView.findViewById(R.id.tv_tittle);
        txtName.setText(arrayNews.get(position).getTitle());

        TextView txtAddress = convertView.findViewById(R.id.tv_address);
        txtAddress.setText(mainAddress[0] + ", " + mainAddress[1]);

        TextView txtStatus = convertView.findViewById(R.id.status);
        ProductModel productModel = arrayNews.get(position).getNameProduct().get(0);
        txtStatus.setText(productModel.getCategory());

        TextView txtTypeNews = convertView.findViewById(R.id.tv_typenews);
        txtTypeNews.setText(productModel.getNameProduct());

        List<String> listUrl = arrayNews.get(position).getUrlImage();

        if (listUrl.size() != 0) {
            String url = listUrl.get(0);
            ImageView imgHinh = convertView.findViewById(R.id.img_hinh);

            Glide.with(convertView).load(url)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .fitCenter()
                    .apply(new RequestOptions().override(240,240))
                    .into(imgHinh);
        }

        return convertView;
    }
}