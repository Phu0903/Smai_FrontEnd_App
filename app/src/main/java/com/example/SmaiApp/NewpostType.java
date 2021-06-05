package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewpostType extends AppCompatActivity {

    Button btnCanxindo, btnTangcongdong, btnTangnguoingheo, btnTangtuthien, btnQuyengop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost_type);

        btnCanxindo = findViewById(R.id.btn_canxindo);
        btnTangcongdong  = findViewById(R.id.btn_tangcongdong);
        btnTangnguoingheo = findViewById(R.id.btn_tangnguoingheo);
        btnTangtuthien = findViewById(R.id.btn_tangtuthien);
        btnQuyengop = findViewById(R.id.btn_quyengop);

        Intent intent = getIntent();
        String token = intent.getStringExtra("Token");

        btnCanxindo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfirmAddress3.class);
                intent.putExtra("Token", token);
                startActivity(intent);
            }
        });
        btnTangcongdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfirmAddress.class);
                startActivity(intent);
            }
        });
        btnTangnguoingheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfirmAddress2.class);
                startActivity(intent);
            }
        });
        btnTangtuthien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfirmAddress2.class);
                startActivity(intent);
            }
        });
        btnQuyengop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfirmAddress2.class);
                startActivity(intent);
            }
        });
    }
}