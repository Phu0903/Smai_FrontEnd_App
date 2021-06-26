package com.smait.quyengop.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.smait.quyengop.DetailPost;
import com.smait.quyengop.Model.PostNewsModel;
import com.smait.quyengop.Model.ProductModel;
import com.smait.quyengop.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

            // using TimeUnit class from java.util.concurrent package
            long getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
            long getHoursDiff = TimeUnit.MILLISECONDS.toHours(getDiff);
            if (getHoursDiff < 24) {
                holder.txtDatePost.setText(getHoursDiff + " h");
            } else {
                holder.txtDatePost.setText(getDaysDiff + " ngày");
            }

        } catch (Exception e) {
            e.printStackTrace();
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

//                lấy id của bài đăng
                        String id = arrayNews.get(position).get_id();

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
                        intent.putExtra("idpost", id);
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
