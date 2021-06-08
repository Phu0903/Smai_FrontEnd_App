package com.example.SmaiApp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.SmaiApp.Adapter.PhotoAdapter;
import com.example.SmaiApp.Danhmuc.NameProduct;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class Detail2 extends AppCompatActivity {

    ImageButton mChooseBtn;
    //    Declare variable to upload image
    ImageView imgView1, imgView2, imgView3, imgView4, imgView5;
    ImageView[] listImage = new ImageView[5];
    int count;//image counter
    Button btnNext;
    EditText edtLoiNhan, edtMoTa;
    List<Bitmap> bitmaps;
    List<Uri> uris = new ArrayList<>();
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1000;

    ImageButton btnUpLoadPhoto;
    PhotoAdapter photoAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

//        Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dt);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        edtLoiNhan = findViewById(R.id.edt_loinhan);
        edtMoTa = findViewById(R.id.edt_mota);

        mChooseBtn = findViewById(R.id.btn_uploadImage);
        imgView1 = findViewById(R.id.dt_imaage_view1);
        imgView2 = findViewById(R.id.dt_imaage_view2);
        imgView3 = findViewById(R.id.dt_imaage_view3);
        imgView4 = findViewById(R.id.dt_imaage_view4);
        imgView5 = findViewById(R.id.dt_imaage_view5);
        listImage[0] = imgView1;
        listImage[1] = imgView2;
        listImage[2] = imgView3;
        listImage[3] = imgView4;
        listImage[4] = imgView5;

        count = 0;

//Nhận data from Catogory: address, list nameproduct
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String token = intent.getStringExtra("token");
        Log.d("Token Detail", token);
        String TypeAuthor = intent.getStringExtra("TypeAuthor");
        Log.d("TypeAuthor detail", TypeAuthor);
        ArrayList<String> listName = intent.getStringArrayListExtra("ListName");
        ArrayList<String> listCatogory = intent.getStringArrayListExtra("ListCatogary");
//********************************************************************
        btnNext = (Button) findViewById(R.id.dt_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loinhann = edtLoiNhan.getText().toString();

                if (loinhann.isEmpty() ||  edtMoTa.getText().toString().isEmpty() || uris == null) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {

                    //                Gửi data sang activity ConfirmInfor
                    Intent intent = new Intent(Detail2.this, ConfirmInforPost2.class);
                    String loinhan = edtLoiNhan.getText().toString();
                    String mota = edtMoTa.getText().toString();
                    intent.putExtra("loinhan", loinhan);
                    intent.putExtra("mota", mota);
                    intent.putExtra("address", address);
                    intent.putExtra("token", token);
                    intent.putExtra("TypeAuthor", TypeAuthor);
                    intent.putStringArrayListExtra("ListName", listName);
                    intent.putStringArrayListExtra("ListCatogary", listCatogory);
                    intent.putParcelableArrayListExtra("Uri", (ArrayList<Uri>) uris);
                    if (uris != null) {
                        for (int i=0;i<uris.size();i++) {
                            Log.d("Uri list", String.valueOf(uris.get(i)));
                        }
                    }
                    else {
                        Log.e("Errer: ", "errer Urii");
                    }
//*******************************************************************************************
                    startActivity(intent);
                }
            }
        });

        btnUpLoadPhoto = findViewById(R.id.btn_uploadImage);
        btnUpLoadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });

        recyclerView = findViewById(R.id.rcv_photo);
        photoAdapter = new PhotoAdapter(Detail2.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(photoAdapter);
    }

    private void requestPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                selectImageFromGallery();

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void selectImageFromGallery() {
        TedBottomPicker.with(Detail2.this)
                .setPeekHeight(1000)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setTitleBackgroundResId(android.R.color.black)
                .setEmptySelectionText("No Select")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        // here is selected image uri list
                        uris = uriList;
                        photoAdapter.setData(uriList);

                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}