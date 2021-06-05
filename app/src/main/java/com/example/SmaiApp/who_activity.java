package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class who_activity extends AppCompatActivity {


    Button btn_canhan, btn_grouptưthien, btn_tochuc;
    String address = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.who_layout);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_who);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        btn_canhan = findViewById(R.id.button7);
        btn_grouptưthien = findViewById(R.id.button3);
        btn_tochuc = findViewById(R.id.button8);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String token = bundle.getString("token");
        Log.d("Token who", token);
        if (bundle != null) {
            address = bundle.getString("address", "");
            Log.d("Address", address);
        }


        btn_canhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TypeAuthor = "Cá nhân";
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("address", address);
                bundle.putString("token", token);
                bundle.putString("TypeAuthor", TypeAuthor);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_grouptưthien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TypeAuthor = "Quỹ/nhóm từ thiện";
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("address", address);
                bundle.putString("token", token);
                bundle.putString("TypeAuthor", TypeAuthor);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_canhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TypeAuthor = "Tổ chức công ích";
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("address", address);
                bundle.putString("token", token);
                bundle.putString("TypeAuthor", TypeAuthor);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}