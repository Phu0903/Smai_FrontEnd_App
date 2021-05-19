package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmInforPost extends AppCompatActivity {

    Button btn_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_infor_post);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_anouncement2);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        Add back button at toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Click btn_confirm
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmInforPost.this, CompleteActivity.class);
                startActivity(intent);
            }
        });
    }
}