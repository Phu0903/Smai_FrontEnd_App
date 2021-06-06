package com.example.SmaiApp.Personal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SigninFragment extends Fragment {



    public SigninFragment() {
        // Required empty public constructor
    }

    Button btnSignin;
    TextInputEditText edtHoTen, edtTenTaiKhoan, edtSDT, edtMatKhau, edtDiaChi, edtMatKhau2;
    TextInputLayout layouthoten, layouttaikhoan, layoutsdt, layoutdiachi, layoutmk, layoutmk2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        getComponent(view);


        edtHoTen.addTextChangedListener(new ConfirmHoTen());
        edtTenTaiKhoan.addTextChangedListener(new ConfirmTenTaiKhoan());
        edtSDT.addTextChangedListener(new ConfirmPhoneNumber());
        edtDiaChi.addTextChangedListener(new ConfirmAddress());
        edtMatKhau.addTextChangedListener(new ConfirmPassWord());
        edtMatKhau2.addTextChangedListener(new ConfirmPassWord2());






        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                    ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);
                    String userName = edtTenTaiKhoan.getText().toString();
                    String passWord = edtMatKhau.getText().toString();
                    String passWord2 = edtMatKhau2.getText().toString();
                    String phoneNumber = edtSDT.getText().toString();
                    String fullName = edtHoTen.getText().toString();
                    String address = edtDiaChi.getText().toString();


                    if (validate(fullName, layouthoten, edtHoTen) == true && validate(userName, layouttaikhoan,edtTenTaiKhoan)==true && validate(passWord, layoutmk, edtMatKhau) == true &&
                    validate(phoneNumber, layoutsdt, edtSDT) == true && validate(address, layoutdiachi, edtDiaChi) == true && validate(passWord2, layoutmk2, edtMatKhau2) == true) {

                        AccountModel accountModel = new AccountModel();
                        accountModel.setUserName(userName);
                        accountModel.setPassword(passWord);
                        accountModel.setFullName(fullName);
                        accountModel.setAddress(address);
                        accountModel.setPhoneNumber(phoneNumber);
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

                                } else {
                                    Log.e("Message", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<AccountModel> call, Throwable t) {
                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

            }

        });


        return view;
    }
    private void getComponent(View v) {

        btnSignin = v.findViewById(R.id.btn_signin);
        edtHoTen = v.findViewById(R.id.edthoten);
        edtTenTaiKhoan = v.findViewById(R.id.edttaikhoan);
        edtSDT = v.findViewById(R.id.edtsdt);
        edtMatKhau = v.findViewById(R.id.edtmatkhau);
        edtDiaChi = v.findViewById(R.id.edtdiachi);
        edtMatKhau2 = v.findViewById(R.id.edtmatkhau2);

        layouthoten = v.findViewById(R.id.layouthoten);
        layouttaikhoan = v.findViewById(R.id.layouttaikhoan);
        layoutsdt = v.findViewById(R.id.layoutsdt);
        layoutdiachi = v.findViewById(R.id.layoutdiachi);
        layoutmk = v.findViewById(R.id.layoutmatkhau);
        layoutmk2 = v.findViewById(R.id.layoutmatkhau2);

    }
    private boolean validate(String nameEditText, TextInputLayout textInputLayout, TextInputEditText editText) {

        if (nameEditText.trim().isEmpty()) {
            textInputLayout.setError("Vui lòng điển " + editText.getHint());
            editText.requestFocus();
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    private class ConfirmHoTen implements TextWatcher {

        private View view;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            this.view = view;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validate(edtHoTen.getText().toString(), layouthoten, edtHoTen);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layouthoten.setErrorEnabled(false);
        }
    }

    private class ConfirmTenTaiKhoan implements TextWatcher {
        private View view;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            this.view = view;;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validate(edtTenTaiKhoan.getText().toString(), layouttaikhoan, edtTenTaiKhoan);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layouttaikhoan.setErrorEnabled(false);
        }
    }

    private class ConfirmPhoneNumber implements TextWatcher {
        private View view;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            this.view = view;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validate(edtSDT.getText().toString(), layoutsdt, edtSDT);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layoutsdt.setErrorEnabled(false);
        }
    }

    private class ConfirmAddress implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validate(edtDiaChi.getText().toString(), layoutdiachi, edtDiaChi);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layoutdiachi.setErrorEnabled(false);
        }
    }

    private class ConfirmPassWord implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validate(edtMatKhau.getText().toString(), layoutmk, edtMatKhau);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layoutmk.setErrorEnabled(false);
        }
    }

    private class ConfirmPassWord2 implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validate(edtMatKhau2.getText().toString(), layoutmk2, edtMatKhau2);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layoutmk2.setErrorEnabled(false);
        }
    }

}