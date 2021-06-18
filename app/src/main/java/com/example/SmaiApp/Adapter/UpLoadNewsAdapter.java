package com.example.SmaiApp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.Model.ProductModel;
import com.example.SmaiApp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpLoadNewsAdapter extends BaseAdapter {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT_24 = "HH:mm";
    public UpLoadNewsAdapter(Context context, int layout, List<PostNewsModel> newsList) {
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
        SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm"); //format ngày giờ thành dd/mm/yyyy hh:mm
        String sTime = localDateFormat.format(date1);
        String[] s1 = sTime.split(" ");

//        Log.d("Date up news", sTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);


        long currentTime = Calendar.getInstance().getTimeInMillis();
        long timeUp = calendar.getTimeInMillis();
        long hourRest = currentTime - timeUp;
        int hours   = (int) ((hourRest / (1000*60*60)) % 24);

        Date dat1 = Calendar.getInstance().getTime();

        String getHour = "";
        int day=0;
        if (hours < 23) {
            getHour = hours + " h";
        } else if (hours == 23) {
            hours = hours + 24;

            if (hours >= 24 && hours < 48) {
                getHour = "1 ngày";
            } else if (hours >= 48 && hours < 72) {
                getHour = "2 ngày";
            } else if (hours >= 72 && hours < 96) {
                getHour = "3 ngày";
            } else {
                getHour = "Hơn 3 ngày";
            }

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


        TextView typePost = convertView.findViewById(R.id.typePost);
        TextView status = convertView.findViewById(R.id.status);
        TextView txtTypesNews = convertView.findViewById(R.id.tv_typenews);
        TextView note = convertView.findViewById(R.id.tv_note);
        if (arrayNews.get(position).getTypeAuthor().equals("Cá nhân")) {
            typePost.setText("Cần xin đồ");
            status.setText("Chờ xác thực");
            status.setTextColor(Color.RED);
            txtTypesNews.setText("Danh mục nhân tặng");
            String no = String.valueOf(arrayNews.get(position).getNameProduct().size());
            note.setText(no);
            note.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            note.setTextColor(Color.BLACK);
        } else {
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
        }

        TextView txtDatePost = convertView.findViewById(R.id.tv_datepost);
        txtDatePost.setText(getHour);



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
