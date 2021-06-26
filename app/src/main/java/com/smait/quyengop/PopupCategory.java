package com.smait.quyengop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;

import com.smait.quyengop.Adapter.PopupCategoryAdapter;
import com.smait.quyengop.R;

import java.util.ArrayList;

public class PopupCategory extends AppCompatActivity {


    ListView lvCategory;
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_category);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7), (int) (height*.3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -30;
        getWindow().setAttributes(params);

        btn = findViewById(R.id.closePopup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvCategory = findViewById(R.id.listpopupdanhmuc);
        Intent intent = getIntent();
        ArrayList<String> listName = intent.getStringArrayListExtra("listname");

        PopupCategoryAdapter adapter = new PopupCategoryAdapter(PopupCategory.this, R.layout.list_item_danhmuc, listName);
        lvCategory.setAdapter(adapter);

    }
}