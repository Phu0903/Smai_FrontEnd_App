package com.example.SmaiApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class who_activity extends AppCompatActivity {


    Button btn_canhan, btn_grouptưthien, btn_tochuc;
    String address = "";
    String tokenMain;

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
        if (bundle != null) {
            address = bundle.getString("address", "");
        }

        tokenMain = token;

        btn_canhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TypeAuthor = btn_canhan.getText().toString();
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("address", address);
                bundle.putString("token", token);
                bundle.putString("TypeAuthor", "Cá nhân");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_grouptưthien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TypeAuthor = btn_grouptưthien.getText().toString();
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("address", address);
                bundle.putString("token", token);
                bundle.putString("TypeAuthor", "Quỹ/Nhóm từ thiện");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_tochuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TypeAuthor = btn_tochuc.getText().toString();
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("address", address);
                bundle.putString("token", token);
                bundle.putString("TypeAuthor", "Tổ chức công ích");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar_danhmuc, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.button_cancel) {
            ConfirmCancel();
        }
        else {
            finish();
        }
        return true;
    }

    private void ConfirmCancel() {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(this);
        alerDialog.setTitle("Thông báo!");
        alerDialog.setMessage("Bạn có chắc muốn hủy không?");

        alerDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("Token", tokenMain);
                i.putExtra("message", "OK");
                startActivity(i);
            }
        });
        alerDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerDialog.show();
    }
}