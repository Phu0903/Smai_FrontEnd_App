package com.smait.quyengop.Adapter;

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
import com.smait.quyengop.Model.PostNewsModel;
import com.smait.quyengop.Model.ProductModel;
import com.smait.quyengop.NetWorKing.ApiServices;
import com.smait.quyengop.NetWorKing.RetrofitClient;
import com.smait.quyengop.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpLoadNewsAdapter extends BaseAdapter {

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
        convertView = inflater.inflate(myLayout, null);
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



        TextView txtName = convertView.findViewById(R.id.tv_tittle);
        txtName.setText(arrayNews.get(position).getTitle());


        TextView txtAddress = convertView.findViewById(R.id.tv_address);
        txtAddress.setText(mainAddress[0] + ", " + mainAddress[1]);

        TextView txtTypesNews = convertView.findViewById(R.id.tv_typenews);
        TextView note = convertView.findViewById(R.id.tv_note);
        TextView status = convertView.findViewById(R.id.status);
        TextView typePost = convertView.findViewById(R.id.typePost);
        if (arrayNews.get(position).getTypeAuthor().equals("Cá nhân") || arrayNews.get(position).getTypeAuthor().equals("Quỹ/Nhóm từ thiện") ||
                arrayNews.get(position).getTypeAuthor().equals("Tổ chức công ích")) {


            if (arrayNews.get(position).isConfirm() == false) {
                status.setText("Chờ xác thực");
                Log.d("Waiting confirm: ", arrayNews.get(position).isConfirm() + "");
                status.setTextColor(Color.RED);
            } else {
                status.setText("Đang hiển thị");
                status.setTextColor(myContext.getResources().getColor(R.color.teal_700));
                Log.d("Is confirm: ", arrayNews.get(position).isConfirm() + "");
            }
            typePost.setText("Cần xin đồ");
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

        ImageButton btnSetting = convertView.findViewById(R.id.btnSettitngNews);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v, position);
            }
        });


        return convertView;
    }

    public void showMenu (View view, int position)
    {
        PopupMenu menu = new PopupMenu (myContext, view);
        menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
        {
            @Override
            public boolean onMenuItemClick (MenuItem item)
            {
                int id = item.getItemId();
                switch (id)
                {
                    case R.id.deleteNews:
                        ConfirmCancel(view, position);
                        break;
                }
                return true;
            }
        });
        menu.inflate (R.menu.popup_menu_action_news);
        menu.show();
    }

    private void ConfirmCancel(View v, int position) {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(myContext);
        alerDialog.setTitle("Thông báo!");
        alerDialog.setMessage("Bạn có chắc muốn xóa không?");

        alerDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteNews(position);
                arrayNews.remove(position);
                notifyDataSetChanged();
            }
        });
        alerDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = alerDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(myContext.getResources().getColor(R.color.teal_700));
    }

    public void deleteNews(int position) {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

        String id = arrayNews.get(position).get_id();

        Call<String> call = jsonPlaceHolderApi.deleteNews(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {

                } else {
                    Log.d("Tra ve gì đấy", response.message());
                }

                notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.d("Tra cai dech gì đấy", t.getMessage());

            }
        });

    }
}


