package com.example.SmaiApp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.SmaiApp.DetailPost;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.Model.ProductModel;
import com.example.SmaiApp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsRecyclerviewAdapter extends RecyclerView.Adapter<NewsRecyclerviewAdapter.ViewHolder> {


    public NewsRecyclerviewAdapter(Context myContext, List<PostNewsModel> arrayNews) {
        this.myContext = myContext;
        this.arrayNews = arrayNews;
    }

    Context myContext;
    List<PostNewsModel> arrayNews;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View heroView = inflater.inflate(R.layout.row_news_listview, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Date date1 = arrayNews.get(position).getCreatedAt();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String sTime = localDateFormat.format(date1);
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

        holder.txtName.setText(arrayNews.get(position).getTitle());
        holder.txtAddress.setText(mainAddress[0] + ", " + mainAddress[1]);

        List<ProductModel> productModels = arrayNews.get(position).getNameProduct();

        if (productModels.size() != 0) {
            ProductModel model1 = new ProductModel();
            String nameCatogory = "";
            if (model1 != null) {
                model1 = productModels.get(0);
                nameCatogory = model1.getCategory();
            }
            holder.txtTypesNews.setText(nameCatogory);
        }

        holder.txtDatePost.setText(getHour);

        List<String> listUrl = arrayNews.get(position).getUrlImage();

        if (listUrl.size() != 0) {
            String url = listUrl.get(0);

            Glide.with(myContext).load(url)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .fitCenter()
                    .apply(new RequestOptions().override(240,240))
                    .into(holder.imgHinh);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        Intent intent = new Intent(myContext, DetailPost.class);
                        PostNewsModel post = arrayNews.get(position);
                        String title = post.getTitle();

                        List<ProductModel> productModel = post.getNameProduct();
                        if (productModel.size() != 0) {
                            String detailType = productModel.get(0).getCategory();
                            intent.putExtra("detailType", detailType);
                        }
                        String address = post.getAddress();
                        String fullName = post.getNameAuthor();
                        if (fullName != null) {
                            Log.d("fullName", fullName);
                        }
                        else {
                            Log.e("Full name", "no fullname");
                        }
                        String inforDetail = post.getNote();
                        String typeAuthor = post.getTypeAuthor();
                        List<String> listUrl = post.getUrlImage();
                        ArrayList<String> arrayListurl = new ArrayList<>();
                        for (String s: listUrl) {
                            arrayListurl.add(s);
                        }
                        String AuthorID = post.getAuthorID();

                        intent.putExtra("title", title);
                        intent.putExtra("address", address);
                        intent.putExtra("fullName", fullName);
                        intent.putExtra("inforDetail", inforDetail);
                        intent.putExtra("typeAuthor", typeAuthor);
                        intent.putExtra("AuthorID", AuthorID);
                        intent.putStringArrayListExtra("url", arrayListurl);
                        myContext.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayNews != null) {
            return arrayNews.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtName;
        TextView txtAddress, txtDatePost, txtTypesNews ;
        ImageView imgHinh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_recyclerview);
            txtName = itemView.findViewById(R.id.tv_tittle);
            txtAddress = itemView.findViewById(R.id.tv_address);
            txtTypesNews = itemView.findViewById(R.id.tv_typenews);
            txtDatePost = itemView.findViewById(R.id.tv_datepost);
            imgHinh = itemView.findViewById(R.id.img_hinh);
        }
    }
}
