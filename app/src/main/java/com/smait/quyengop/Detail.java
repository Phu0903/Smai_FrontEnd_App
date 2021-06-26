package com.smait.quyengop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.smait.quyengop.Adapter.PhotoAdapter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.smait.quyengop.R;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class Detail extends AppCompatActivity {

    String tokenMain;
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
    PhotoAdapter photoAdapter, photoAdapter2;
    RecyclerView recyclerView, recyclerView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        tokenMain = token;
//********************************************************************
        btnNext = (Button) findViewById(R.id.dt_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loinhann = edtLoiNhan.getText().toString();
                Log.d("Size uriiiiii", String.valueOf(uris.size()));
                Log.d("Loi nhan", loinhann);
                if (loinhann.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {

                    //                Gửi data sang activity ConfirmInfor
                    Intent intent = new Intent(Detail.this, ConfirmInforPost.class);
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

        recyclerView2 = findViewById(R.id.rcv_photo2);
        recyclerView = findViewById(R.id.rcv_photo);
        photoAdapter = new PhotoAdapter(Detail.this);
        photoAdapter2 = new PhotoAdapter(Detail.this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 3);
        recyclerView2.setLayoutManager(gridLayoutManager2);
        recyclerView2.setAdapter(photoAdapter2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(photoAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Detail.this, DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView2.addItemDecoration(dividerItemDecoration);
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
        TedBottomPicker.with(Detail.this)
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
                        List<Uri> urisList1 = new ArrayList<>();
                        List<Uri> urisList2 = new ArrayList<>();
                        if (uriList.size() > 3) {

                            urisList1.add(uriList.get(0));
                            urisList1.add(uriList.get(1));
                            urisList2.add(uriList.get(2));
                            urisList2.add(uriList.get(3));

                            if (uriList.size() == 5) {
                                urisList2.add(uriList.get(4));
                            }
                            photoAdapter.setData(urisList1);
                            photoAdapter2.setData(urisList2);


                        }
                        else {
                            if (uriList.size() == 3) {
                                urisList1.add(uriList.get(0));
                                urisList1.add(uriList.get(1));
                                urisList2.add(uriList.get(2));
                                photoAdapter.setData(uriList);
                                photoAdapter2.setData(urisList2);
                            }
                            else {
                                photoAdapter.setData(uriList);
                                photoAdapter2.setData(urisList2);
                            }

                        }

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
        alerDialog.show();
    }
}