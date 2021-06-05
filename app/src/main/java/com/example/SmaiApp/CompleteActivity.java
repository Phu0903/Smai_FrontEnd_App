package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.SmaiApp.Adapter.NewsAdapter;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.Model.ProductModel;
import com.example.SmaiApp.NetWorKing.ApiServices;
import com.example.SmaiApp.NetWorKing.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.SmaiApp.Helper.Helper.setListViewHeightBasedOnChildren;

public class CompleteActivity extends AppCompatActivity {
    Button btn_complete;

//    PostNewsModel postNewsModel;
//    List<ProductModel> productModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

//        postNewsModel = new PostNewsModel();
//
//        Intent intent = getIntent();
//        String address = intent.getStringExtra("address");
//        ArrayList<String> listName = intent.getStringArrayListExtra("ListName");
//        ArrayList<String> listCatogory = intent.getStringArrayListExtra("ListCatogary");
//        String loinhan = intent.getStringExtra("loinhan");
//        String mota = intent.getStringExtra("mota");
//        String token = intent.getStringExtra("token");
//        if (token != null) {
//            Log.d("Token complete", token);
//        }
//        String TypeAuthor = intent.getStringExtra("TypeAuthor");
//        List<Uri> uris = new ArrayList<Uri>();
////        lấy uri của hình ảnh
//        uris = intent.getParcelableArrayListExtra("Uri");
//        List<String> listUri = new ArrayList<>();
//        for (int i=0;i<uris.size();i++) {
//            listUri.add(uris.get(i).toString());
//
//            Log.d("Uri", String.valueOf(uris.get(i)));
//            Log.d("UriString", String.valueOf(listUri.get(i)));
//        }
//
//
//
//        postNewsModel.setAddress(address);
//        postNewsModel.setTypeAuthor(TypeAuthor);
//        postNewsModel.setTitle(loinhan);
//        postNewsModel.setNote(mota);
//        postNewsModel.setUrlImage(listUri);
//
//        Log.d("List UrlImage", String.valueOf(listUri.size()));
//
//        for (int i=0;i< listCatogory.size(); i++) {
//            ProductModel productModel = new ProductModel();
//            productModel.setCategory(listCatogory.get(i));
//            productModel.setNameProduct(listName.get(i));
//            productModelList.add(productModel);
//        }



        btn_complete = findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}