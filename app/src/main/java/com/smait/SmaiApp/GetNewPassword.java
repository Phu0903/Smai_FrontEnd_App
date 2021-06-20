package com.smait.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smait.SmaiApp.Model.AccountModel;
import com.smait.SmaiApp.NetWorKing.ApiServices;
import com.smait.SmaiApp.NetWorKing.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetNewPassword extends AppCompatActivity {

    EditText edtPassword, edtPassword2;
    String phoneNumber, passwordSend;
    Button btnChangePassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_new_password);

        edtPassword = findViewById(R.id.edt_password);
        edtPassword2 = findViewById(R.id.edt_password2);
        btnChangePassword = findViewById(R.id.btn_changePassword);



        Intent  intent = getIntent();
        phoneNumber = intent.getStringExtra("Number");

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edtPassword.getText().toString();
                String password2 = edtPassword2.getText().toString();
                if (password.equals(password2) && password != null) {
                    passwordSend = password;
                    resetPassword();


                } else {
                    if (password == null) {
                        edtPassword.setError("Vui lòng nhập mật khẩu");
                    } else {
                        edtPassword2.setError("Mật khẩu không đúng");
                    }

                }
            }
        });


    }

    private void resetPassword() {

        AccountModel accountModel = new AccountModel();
        accountModel.setPhoneNumber(phoneNumber);
        accountModel.setPassword(passwordSend);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

        Log.d("Phone", accountModel.getPhoneNumber() + ", " + accountModel.getPassword());
        Call<String> call = jsonPlaceHolderApi.getNewPassword(accountModel);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(GetNewPassword.this, Account.class);
                    Toast.makeText(GetNewPassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(GetNewPassword.this, response.message(), Toast.LENGTH_SHORT).show();
                    Log.d("lỗi chỗ nào đấy: ", response.message());

                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(GetNewPassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("lỗi chỗ nào đấyyyy: ", t.getMessage());
            }
        });
    }
}