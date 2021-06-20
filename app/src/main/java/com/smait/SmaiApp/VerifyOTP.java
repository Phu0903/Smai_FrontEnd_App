package com.smait.SmaiApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.smait.SmaiApp.Model.AccountModel;
import com.smait.SmaiApp.NetWorKing.ApiServices;
import com.smait.SmaiApp.NetWorKing.RetrofitClient;
import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VerifyOTP extends AppCompatActivity {

    Pinview pinview;
    Button btnOTP;
    FirebaseAuth mAuth;
    String verificationId, fullName, phoneNumber, passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        mAuth = FirebaseAuth.getInstance();
        pinview = findViewById(R.id.pin_view);
        btnOTP = findViewById(R.id.btn_OTP);

        Intent intent = getIntent();

        fullName  = intent.getStringExtra("FullName");
        phoneNumber = intent.getStringExtra("PhoneNumber");
        passWord = intent.getStringExtra("Password");

        String phone = phoneNumber.substring(1, 10);
        sendVerificationCode(phone);

        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOTP.setEnabled(false);
                verifyPhoneNumberWithCode(pinview.getValue());
            }
        });
        
    }

    private void verifyPhoneNumberWithCode(String code) {
        // [START verify_with_code]
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential);
        }
        catch (Exception e){
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
        // [END verify_with_code]
    }

    private void sendVerificationCode(String phone) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+ phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            registration();
                        } else {
                            // Sign in failed, display a message and update the UI;
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyOTP.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        //Hàm này được gọi khi mã code đã được gửi thành công.
        public void onCodeSent(@NonNull String mverificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            verificationId = mverificationId;
        }
        @Override
        // Hàm này được gọi khi bạn xác thực thành công. Tức là mã code nhập vào đúng với mã đã gửi.
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            signInWithPhoneAuthCredential(credential);
        }

        @Override
        //Hàm này được gọi khi không gửi được mã code. Chúng ta cần thông báo cho người dùng biết. Ở ví dụ này mình đơn giản là hiển thị một Toast thông báo.
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Lỗi",e.getMessage());

        }

    };

    private void registration() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);
        AccountModel accountModel = new AccountModel();
        accountModel.setPassword(passWord);
        accountModel.setFullName(fullName);
        accountModel.setPhoneNumber(phoneNumber);
        Call<AccountModel> call = jsonPlaceHolderApi.signup(accountModel);

        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                if (response.isSuccessful()) {

                    AccountModel accountModel1 = response.body();
                    String message = accountModel1.getMessage();
                    String tk = accountModel1.getAccessToken();
                    Intent intent = new Intent(VerifyOTP.this, MainActivity.class);
                    intent.putExtra("message", message);
                    intent.putExtra("Token", tk);
                    intent.putExtra("ISLOGINED", "NO");
                    startActivity(intent);
                    finish();


                } else {
                    Log.e("Message error", response.message());
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Toast.makeText(VerifyOTP.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


/*
Retrofit retrofit = RetrofitClient.getRetrofitInstance();
                    ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);
                    AccountModel accountModel = new AccountModel();
                    accountModel.setPhoneNumber(phonenumber);
                    Log.d("PhoneNumber", accountModel.getPhoneNumber());
                    Call<String> call = jsonPlaceHolderApi.checkPhoneNumber(accountModel);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                String s = response.body();
                                Log.d("Boddy", s);
                                if (s.equals("Oke")) {
                                    phoneNumber.setError("Số điện thoại chưa đăng ký");
                                } else {
                                    btn_SendOTP.setEnabled(true);
                                    String phone = phonenumber.substring(1, 10);
                                    sendVerificationCode(phone);
                                }

                            } else {
                                Log.d("Message error", response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(ForgotPassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("sadfsd", String.valueOf(call));
                            btn_GetOTP.setEnabled(true);
                        }
                    });
 */



















