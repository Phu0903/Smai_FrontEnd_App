package com.example.SmaiApp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.example.SmaiApp.Danhmuc.NameProduct;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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



        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                }
            }
        });
        btnNext = (Button) findViewById(R.id.dt_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loinhann = edtLoiNhan.getText().toString();
                if (loinhann.isEmpty() &&  edtMoTa.getText().toString().isEmpty() && uris.isEmpty()) {
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
//*******************************************************************************************
                    startActivity(intent);
                }
            }
        });

    }

    private void pickImageFromGallery() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setType("image/*");
//        startActivityForResult(intent, IMAGE_PICK_CODE);

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//            listImage[count].setImageURI(data.getData());
//            count = count + 1;
            bitmaps = new ArrayList<>();
            ClipData clipData = data.getClipData();

            if (clipData != null) {
                //multiple images selecetd
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    uris.add(imageUri);
                    Log.d("URI", imageUri.toString());
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        bitmaps.add(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //single image selected
                Uri imageUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    count=0;
                    for (final Bitmap b : bitmaps) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listImage[count].setImageBitmap(b);
                                count=count+1;
                            }
                        });

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}