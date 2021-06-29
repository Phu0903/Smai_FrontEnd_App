package com.smait.quyengop;

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
import android.widget.CheckedTextView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.smait.quyengop.Danhmuc.CustomExpandableNoCheckBoxListAdapter;
import com.smait.quyengop.Danhmuc.ExpandableListDataPump;
import com.smait.quyengop.Danhmuc.NameProduct;
import com.smait.quyengop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryActivity2 extends AppCompatActivity {
    String tokenMain;
    String address = "";

    String TypeAuthor;
    CheckedTextView checkedTextView;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    String namePro, nameCatogery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);
        //        toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_anouncement);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            address = bundle.getString("address");
        }
        if (bundle.getString("TypeAuthor") != null) {
            TypeAuthor = bundle.getString("TypeAuthor");

        }

        String token = bundle.getString("token");
        tokenMain = token;



        expandableListView = (ExpandableListView) findViewById(R.id.expandable_listivew_danhmuc);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableNoCheckBoxListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                checkedTextView = v.findViewById(R.id.checkList);

                checkedTextView.setEnabled(false);

                NameProduct nameProduct = new NameProduct(expandableListTitle.get(groupPosition), expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));

                namePro = nameProduct.getNameProduct();
                nameCatogery = nameProduct.getCategory();

                Intent intent1 = new Intent(getApplicationContext(), GiveFor.class);
                intent1.putExtra("ListName", namePro);
                intent1.putExtra("ListCatogary", nameCatogery);
                intent1.putExtra("TypeAuthor", TypeAuthor);
                intent1.putExtra("address", address);
                intent1.putExtra("token", token);
                startActivity(intent1);



                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar_danhmuc, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        switch (item.getItemId()) {
//            case R.id.button_cancel:
//                ConfirmCancel();
//                break;
//            default:
//                break;
//        }
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
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("Token", tokenMain);
                i.putExtra("message", "OK");
                startActivity(i);
                finish();
            }
        });
        alerDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = alerDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(this.getResources().getColor(R.color.teal_700));
    }


}