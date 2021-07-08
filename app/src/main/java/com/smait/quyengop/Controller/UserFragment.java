package com.smait.quyengop.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.smait.quyengop.Controller.Helper.UriUtils;
import com.smait.quyengop.Model.PostNewsModel;
import com.smait.quyengop.Model.UserModel;
import com.smait.quyengop.Controller.NetWorKing.ApiServices;
import com.smait.quyengop.Controller.NetWorKing.RetrofitClient;
import com.smait.quyengop.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends Fragment {

    private  static final String IS_LOGIN_USER = "OK";

    Button btnHistory;
    Button btn_call;
    LinearLayout layoutInfor, layoutRequired;
    TextView fullName;
    Button btnLilo;
    ImageView imgAvatar;
    String message="", token="";
    SharedPreferences sharedPreferences;
    String urlAvatar;
    ImagePopup imagePopup;
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        imgAvatar = view.findViewById(R.id.imageView3);
        fullName = view.findViewById(R.id.textView5);
//        view History
        btnHistory = (Button) view.findViewById(R.id.view_history);
        sharedPreferences = getActivity().getSharedPreferences("datalogin", MODE_PRIVATE);

        imagePopup = new ImagePopup(getContext());

        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);


        if (sharedPreferences != null) {
            String fullname = sharedPreferences.getString("fullName", "");
            fullName.setText(fullname);
            String url = sharedPreferences.getString("urlAvatar", "");
            Glide.with(getContext()).load(url)
                    .placeholder(R.drawable.user)
                    .fitCenter()
                    .apply(new RequestOptions().override(80,80))
                    .into(imgAvatar);

        }

        imagePopup.initiatePopup(imgAvatar.getDrawable());



        setHasOptionsMenu(true);
        Toolbar actionBarToolBar = (Toolbar) view.findViewById(R.id.toolbar_user);

        //        nhận từ mainactivity
        MainActivity activity = (MainActivity) getActivity();
        String codeLogin  = activity.getMyData();

        String[] code = codeLogin.split(",");


        if (code.length != 0) {
            message = code[0];
            token = code[1];
        }
        actionBarToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.log_out:
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("message", "");
                        intent.putExtra("Token", "");
                        intent.putExtra("ISLOGINED", "");
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    case R.id.editUser:
                        requestPermissions();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        btn_call = view.findViewById(R.id.call_user);
//        button call
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0938516899"));
                startActivity(intent);
            }
        });



        layoutInfor = view.findViewById(R.id.linearlayout_infor);
        layoutRequired = view.findViewById(R.id.linearlayout_requireUser);
        btnLilo = view.findViewById(R.id.btn_LILO);

        btnLilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Account.class);
                startActivity(intent);
            }
        });


            // get data
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

        Call<UserModel> call = jsonPlaceHolderApi.getInforUser("Bearer " + token);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    UserModel userModel = response.body();
                    fullName.setText(userModel.getFullName());


                    if (userModel.getUrlIamge() != null && getActivity() != null) {
                        urlAvatar = userModel.getUrlIamge();
                        Glide.with(getActivity().getApplicationContext()).load(userModel.getUrlIamge())
                                .placeholder(R.drawable.user)
                                .fitCenter()
                                .apply(new RequestOptions().override(80,80))
                                .into(imgAvatar);
                        imagePopup.initiatePopupWithGlide(urlAvatar);
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("fullName", fullName.getText().toString());
                    editor.putString("urlAvatar", urlAvatar);
                    editor.commit();

                }
                else {
                    Log.e("Message", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.equals("OK")) {
                    Intent intent = new Intent(getContext(), ViewHistory.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (message.equals("OK")) {
            layoutInfor.setVisibility(View.VISIBLE);
            layoutRequired.setVisibility(View.GONE);
            actionBarToolBar.inflateMenu(R.menu.menu_toolbar_account);

        }

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup.viewPopup();

            }
        });


        return view;
    }

    private void requestPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                selectImageFromGallery();

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void selectImageFromGallery() {
        TedBottomPicker.with(getActivity())
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected image uri
                        Uri uri1 = uri;
                        imagePopup.initiatePopupWithPicasso(uri);
                        if (uri == null) {
                            return;
                        }
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri1);
                            if (bitmap != null) {
                                imgAvatar.setImageBitmap(bitmap);
                                imgAvatar.setMaxHeight(200);
                                imgAvatar.setMaxWidth(200);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String filePath = UriUtils.getPathFromUri(getContext(), uri);
                        File file = new File(filePath);
                        Log.d("file", file + ", File name" + file.getName());
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("imageUser", file.getName(), requestFile);
                        List<MultipartBody.Part> list = new ArrayList<>();
                        list.add(body);
                        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);
                        Call<String> call = jsonPlaceHolderApi.postAvatar("Bearer " + token, body);

                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getContext(), "Đổi ảnh thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("Fail", response.message() + "");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("Fail rồi", t.getMessage() + "");
                            }
                        });
                    }
                });
    }

}