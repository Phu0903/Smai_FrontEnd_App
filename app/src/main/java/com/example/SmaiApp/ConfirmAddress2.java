package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmAddress2 extends AppCompatActivity {


    Button btn_xacnhan;
    public static final int CANCEL_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_address2);

        btn_xacnhan = findViewById(R.id.btnXacNhan);

        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity2.class);

                startActivity(intent);
            }
        });
    }
}