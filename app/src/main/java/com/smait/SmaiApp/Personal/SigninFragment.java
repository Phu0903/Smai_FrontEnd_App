package com.smait.SmaiApp.Personal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.smait.SmaiApp.Model.AccountModel;
import com.smait.SmaiApp.NetWorKing.ApiServices;
import com.smait.SmaiApp.NetWorKing.RetrofitClient;
import com.smait.SmaiApp.R;
import com.smait.SmaiApp.VerifyOTP;
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
        edtSDT.addTextChangedListener(new ConfirmPhoneNumber());
        edtMatKhau.addTextChangedListener(new ConfirmPassWord());
        edtMatKhau2.addTextChangedListener(new ConfirmPassWord2());
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Đăng ký", "Bug gì đấy");
                    Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                    ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);
                    String passWord = edtMatKhau.getText().toString();
                    String passWord2 = edtMatKhau2.getText().toString();
                    String phoneNumber = edtSDT.getText().toString();
                    String fullName = edtHoTen.getText().toString();
                    if (validate(fullName, layouthoten, edtHoTen) == true && validate(passWord, layoutmk, edtMatKhau) == true &&
                    validate(phoneNumber, layoutsdt, edtSDT) == true == true && validate(passWord2, layoutmk2, edtMatKhau2) == true) {

                        if (phoneNumber.length() != 10) {
                            layoutsdt.setError("Số điện thoại không đúng");
                            return;
                        }
                        Log.d("Đăng ký2222", "Bug gì đấy");
                        if (passWord.equals(passWord2)) {
                            Log.d("Đăng k 33333ý", "Bug gì đấy");
                            btnSignin.setEnabled(false);
                            AccountModel accountModel = new AccountModel();
                            accountModel.setPassword(passWord);
                            accountModel.setFullName(fullName);
                            accountModel.setPhoneNumber(phoneNumber);
                            Call<String> call = jsonPlaceHolderApi.checkPhoneNumber(accountModel);

                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.d("Đăng ký444444", "Bug gì đấy");
                                    if (response.isSuccessful()) {

                                        if (response.body().equals("Oke")) {
                                            Intent intent = new Intent(getActivity().getBaseContext(), VerifyOTP.class);
                                            intent.putExtra("FullName", fullName);
                                            intent.putExtra("PhoneNumber", phoneNumber);
                                            Log.d("phone number", phoneNumber);
                                            intent.putExtra("Password", passWord);
                                            getActivity().startActivity(intent);
                                            getActivity().finish();
                                        } else {
                                            layoutsdt.setError("Số điện thoại đã đăng ký");
                                            btnSignin.setEnabled(true);
                                            Log.d("Ôi noo", "lỗi chỗ này");
                                            Log.d("Ca sĩ", response.body());
                                        }


                                    } else {
                                        layoutsdt.setError("Số điện thoại đã đăng ký");
                                        btnSignin.setEnabled(true);
                                        Log.e("Message error", response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            layoutmk2.setError("Sai mật khẩu");
                            btnSignin.setEnabled(true);
                        }
                    }

            }

        });


        return view;
    }
    private void getComponent(View v) {

        btnSignin = v.findViewById(R.id.btn_signin);
        edtHoTen = v.findViewById(R.id.edthoten);
        edtSDT = v.findViewById(R.id.edtsdt);
        edtMatKhau = v.findViewById(R.id.edtmatkhau);
        edtMatKhau2 = v.findViewById(R.id.edtmatkhau2);

        layouthoten = v.findViewById(R.id.layouthoten);
        layoutsdt = v.findViewById(R.id.layoutsdt);
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