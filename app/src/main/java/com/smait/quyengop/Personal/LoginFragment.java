package com.smait.quyengop.Personal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.smait.quyengop.ForgotPassword;
import com.smait.quyengop.MainActivity;
import com.smait.quyengop.Model.AccountModel;
import com.smait.quyengop.NetWorKing.ApiServices;
import com.smait.quyengop.NetWorKing.RetrofitClient;
import com.smait.quyengop.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {

    TextClicked mCallback;

    SharedPreferences sp;

    Button forgotPassword;

    public interface TextClicked{
        void sendText(String text);
    }


    public void someMethod(){
        mCallback.sendText("YOUR TEXT");
    }

    @Override
    public void onDetach() {
        mCallback = null; // => avoid leaking, thanks @Deepscorn
        super.onDetach();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    Button btn_login;
    TextInputEditText edtUserName, edtPassWord;
    TextInputLayout layoutTaiKhoan, layoutMatKhau;
    CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btn_login = view.findViewById(R.id.btn_login);
        forgotPassword = view.findViewById(R.id.btn_forgotpassword);
        edtUserName = view.findViewById(R.id.textview_username);
        edtPassWord = view.findViewById(R.id.textView_password);
        checkBox = view.findViewById(R.id.checkbox);
        layoutTaiKhoan = view.findViewById(R.id.layouttentaikhoan);
        layoutMatKhau = view.findViewById(R.id.layoutpassword);


        edtUserName.addTextChangedListener(new ConfirmUserName());
        edtPassWord.addTextChangedListener(new ConfirmPassword());

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), ForgotPassword.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = edtUserName.getText().toString();
                String passWord = edtPassWord.getText().toString();
                AccountModel accountModel = new AccountModel();
                accountModel.setPhoneNumber(userName);
                accountModel.setPassword(passWord);

                if (validate(userName, layoutTaiKhoan, edtUserName) == true && validate(passWord, layoutMatKhau, edtPassWord) == true) {
                    btn_login.setEnabled(false);

                    Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                    ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

                    Call<AccountModel> call = jsonPlaceHolderApi.login(accountModel);

                    call.enqueue(new Callback<AccountModel>() {
                        @Override
                        public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                            if (response.isSuccessful()) {
                                AccountModel accountModel1 = response.body();
                                String message = accountModel1.getMessage();
                                String tk = accountModel1.getAccessToken();
                                String islogined;
                                Log.d("Token Login", tk);


                                Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                                intent.putExtra("message", message);
                                intent.putExtra("Token", tk);
                                if (checkBox.isChecked()) {
                                    islogined = "DONE";
                                    intent.putExtra("ISLOGINED", islogined);
                                }
                                else {
                                    intent.putExtra("ISLOGINED", "NO");
                                }

                                getActivity().startActivity(intent);
                                getActivity().finish();
                            }
                            else {
                                Toast.makeText(getContext(), "Sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                                btn_login.setEnabled(true);

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

    private boolean validate(String nameEditText, TextInputLayout textInputLayout, TextInputEditText editText) {
        if (nameEditText.trim().isEmpty()) {
            textInputLayout.setError("Vui lòng điển đủ thông tin!");
            editText.requestFocus();
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private class ConfirmUserName implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validate(edtUserName.getText().toString(), layoutTaiKhoan, edtUserName);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layoutTaiKhoan.setErrorEnabled(false);
        }
    }

    private class ConfirmPassword implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validate(edtPassWord.getText().toString(), layoutMatKhau, edtPassWord);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layoutMatKhau.setErrorEnabled(false);
        }
    }


}