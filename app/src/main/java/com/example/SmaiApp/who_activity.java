package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class who_activity extends AppCompatActivity {


    Button btn_canhan, btn_grouptưthien, btn_tochuc;

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

        btn_canhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}