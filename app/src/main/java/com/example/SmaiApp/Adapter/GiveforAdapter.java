package com.example.SmaiApp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.R;

import java.util.List;

public class GiveforAdapter extends BaseAdapter {

    public GiveforAdapter(Context context, int layout, List<PostNewsModel> newsList) {
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

        convertView = inflater.inflate(myLayout, null);
        TextView fullName = convertView.findViewById(R.id.fullname_givefor);
        TextView Address = convertView.findViewById(R.id.address_givefor);
        TextView mota = convertView.findViewById(R.id.mota_givefor);


        if (arrayNews.get(position).getNameAuthor() != null) {
            String nameAuthor = arrayNews.get(position).getNameAuthor();
            fullName.setText(nameAuthor);
        }

//        if (arrayNews.get(position).getAddress() != null) {
            String address = arrayNews.get(position).getAddress();
            Address.setText(address);
//        }

//        if (arrayNews.get(position).getTitle() != null) {
            String Mota = arrayNews.get(position).getTitle();
            mota.setText(Mota);
//        }
        if (arrayNews.get(position).getUrlImage().size() != 0) {
            List<String> listUrl = arrayNews.get(position).getUrlImage();
            ImageView imageViewgivfor = convertView.findViewById(R.id.img_givefor);
            String url = listUrl.get(0);
            Log.e("Hinh", url);
            Glide.with(convertView).load(url).placeholder(R.drawable.book1).into(imageViewgivfor);
        } else {
            Log.e("Hinh", "no hinh givefor");
        }



        return convertView;
    }
}
