package com.example.SmaiApp.Personal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.SmaiApp.MainActivity;
import com.example.SmaiApp.Model.AccountModel;
import com.example.SmaiApp.NetWorKing.ApiServices;
import com.example.SmaiApp.NetWorKing.RetrofitClient;
import com.example.SmaiApp.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SigninFragment extends Fragment {



    public SigninFragment() {
        // Required empty public constructor
    }

    Button btnSignin;
    TextInputEditText edtHoTen, edtTenTaiKhoan, edtSDT, edtMatKhau, edtDiaChi;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    private void getComponent(View v) {

        btnSignin = v.findViewById(R.id.btn_signin);
        edtHoTen = v.findViewById(R.id.edthoten);
        edtTenTaiKhoan = v.findViewById(R.id.edttaikhoan);
        edtSDT = v.findViewById(R.id.edtsdt);
        edtMatKhau = v.findViewById(R.id.edtmatkhau);
        edtDiaChi = v.findViewById(R.id.edtdiachi);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        getComponent(view);

        String userName = edtTenTaiKhoan.getText().toString();
        String passWord = edtMatKhau.getText().toString();
        String phoneNumber = edtSDT.getText().toString();
        String fullName = edtHoTen.getText().toString();
        String address = edtDiaChi.getText().toString();
        AccountModel accountModel = new AccountModel();
        accountModel.setUserName(userName);
        accountModel.setPassword(passWord);
        accountModel.setFulllName(fullName);
        accountModel.setAddress(address);
        accountModel.setPhoneNumber(phoneNumber);


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                    ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);
                String userName = edtTenTaiKhoan.getText().toString();
                String passWord = edtMatKhau.getText().toString();
                String phoneNumber = edtSDT.getText().toString();
                String fullName = edtHoTen.getText().toString();
                String address = edtDiaChi.getText().toString();
                AccountModel accountModel = new AccountModel();
                accountModel.setUserName(userName);
                accountModel.setPassword(passWord);
                accountModel.setFulllName(fullName);
                accountModel.setAddress(address);
                accountModel.setPhoneNumber(phoneNumber);
                    Log.d("Address", accountModel.getAddress());
                    Log.d("FullName", accountModel.getFulllName());
                    Log.d("Password", accountModel.getPassword());
                    Log.d("PhoneNumber", accountModel.getPhoneNumber());
                    Log.d("Username", accountModel.getUserName());
                    Call<AccountModel> call = jsonPlaceHolderApi.signup(accountModel);

                    call.enqueue(new Callback<AccountModel>() {
                        @Override
                        public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                            if (response.isSuccessful()) {

                                AccountModel accountModel1 = response.body();
                                String message = accountModel1.getMessage();
                                String tk = accountModel1.getAccessToken();

                                Log.d("Token Login", tk);

                                Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                                intent.putExtra("message", message);
                                intent.putExtra("Token", tk);
                                getActivity().startActivity(intent);

                            }
                            else {
                                Log.e("Message", response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountModel> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

        });


        return view;
    }


}