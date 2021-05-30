package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GiveFor extends AppCompatActivity {

    CardView receiver1, receiver2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_for);

        receiver1 = findViewById(R.id.receiver1);

        receiver1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailPost.class);
                startActivity(intent);
            }
        });

        receiver2 = findViewById(R.id.receiver2);

        receiver2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailPost.class);
                startActivity(intent);
            }
        });
    }
}