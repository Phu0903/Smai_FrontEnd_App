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
import java.util.concurrent.TimeUnit;

public class NewsAdapter extends BaseAdapter {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT_24 = "HH:mm";
    public NewsAdapter(Context context, int layout, List<PostNewsModel> newsList) {
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
        SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String sTime = localDateFormat.format(date1);

        Date datePostNews = null;
        Date dateNow = null;
        Date currentDate = new Date();

        try {
            // calculating the difference b/w startDate and endDate
            String startDate = sTime;
            String endDate = localDateFormat.format(currentDate);

            datePostNews = localDateFormat.parse(startDate);
            dateNow = localDateFormat.parse(endDate);

            long getDiff = dateNow.getTime() - datePostNews.getTime();
            TextView txtDatePost = convertView.findViewById(R.id.tv_datepost);
            // using TimeUnit class from java.util.concurrent package
            long getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
            long getHoursDiff = TimeUnit.MILLISECONDS.toHours(getDiff);
            if (getHoursDiff < 24) {
                txtDatePost.setText(getHoursDiff + " h");
            } else {
                txtDatePost.setText(getDaysDiff + " ngày");
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        List<ProductModel> productModels = arrayNews.get(position).getNameProduct();

        if (productModels.size() != 0) {
            ProductModel model1 = new ProductModel();
            String nameCatogory = "";
            if (model1 != null) {
                model1 = productModels.get(0);
                nameCatogory = model1.getCategory();
            }
            txtTypesNews.setText(nameCatogory);
        }



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

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.HOURS);
    }

}
