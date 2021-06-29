package com.smait.quyengop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smait.quyengop.Model.PostNewsModel;
import com.smait.quyengop.Model.ProductModel;
import com.smait.quyengop.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    public HistoryAdapter(Context context, int layout, List<PostNewsModel> newsList) {
        myContext = context;
        myLayout = layout;
        arrayNewsHistory = newsList;
    }

    Context myContext;
    int myLayout;
    List<PostNewsModel> arrayNewsHistory;

    @Override
    public int getCount() {
        if (arrayNewsHistory != null) {
            return arrayNewsHistory.size();
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
        convertView = inflater.inflate(myLayout, null);
//ngày giờ đăng tin
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm, dd/MM/yyyy "); //format ngày giờ thành dd/mm/yyyy hh:mm
        Date dateHistory1;
        TextView txtDate = convertView.findViewById(R.id.typePost);
        String sTime;

        TextView txtName = convertView.findViewById(R.id.tv_tittle);
        txtName.setText(arrayNewsHistory.get(position).getTitle());


        if (arrayNewsHistory != null && arrayNewsHistory.size() != 0) {
            dateHistory1 = arrayNewsHistory.get(position).getCreatedAt();

            sTime = localDateFormat.format(dateHistory1);
            txtDate.setText(sTime);
        }



        String address = arrayNewsHistory.get(position).getAddress();
        String[] mainAddress = address.split(",");



        TextView txtAddress = convertView.findViewById(R.id.tv_address);
        txtAddress.setText(mainAddress[0] + ", " + mainAddress[1]);

        TextView txtStatus = convertView.findViewById(R.id.status);
        ProductModel productModel = arrayNewsHistory.get(position).getNameProduct().get(0);
        txtStatus.setText(productModel.getCategory());

        TextView txtTypeNews = convertView.findViewById(R.id.tv_typenews);
        txtTypeNews.setText(productModel.getNameProduct());

        List<String> listUrl = arrayNewsHistory.get(position).getUrlImage();

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