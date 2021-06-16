package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

    TextView textComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        textComplete = findViewById(R.id.textView8);

        Intent intent1 = getIntent();
        String token = intent1.getStringExtra("Token");
        String message = intent1.getStringExtra("message");
        String require = intent1.getStringExtra("Require");

        if (require != null && require.equals("tang")) {
            textComplete.setText("Đã được yêu cầu thành công!");
        }

        btn_complete = findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Token", token);
                intent.putExtra("message", message);
                startActivity(intent);
            }
        });
    }
}