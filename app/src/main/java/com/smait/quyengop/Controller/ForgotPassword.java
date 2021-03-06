package com.smait.quyengop.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smait.quyengop.Model.AccountModel;
import com.smait.quyengop.Controller.NetWorKing.ApiServices;
import com.smait.quyengop.Controller.NetWorKing.RetrofitClient;
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
import com.smait.quyengop.R;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgotPassword extends AppCompatActivity {

    EditText phoneNumber;
    Button btn_GetOTP, btn_SendOTP;
    Pinview pinview;
    String phonenumber, number;
    FirebaseAuth mAuth;
    String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();
        phoneNumber = findViewById(R.id.edt_phonenumber);
        btn_GetOTP = findViewById(R.id.btn_getOTP);
        btn_SendOTP = findViewById(R.id.btn_OTP_changePassword);
        pinview =  findViewById(R.id.pin_view);



        btn_GetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_GetOTP.setEnabled(false);
                phonenumber = phoneNumber.getText().toString();
                number = phonenumber;
                if (phonenumber.length() != 10) {
                    phoneNumber.setError("S??? ??i???n tho???i kh??ng h???p l???");
                    btn_GetOTP.setEnabled(true);
                } else {
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
                                    phoneNumber.setError("S??? ??i???n tho???i ch??a ????ng k??");
                                    btn_GetOTP.setEnabled(true);
                                } else {
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

                }
            }
        });

        btn_SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_SendOTP.setEnabled(false);
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

                            Intent intent  = new Intent(ForgotPassword.this, GetNewPassword.class);
                            intent.putExtra("Number", number);
                            startActivity(intent);
                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI;
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        //H??m n??y ???????c g???i khi m?? code ???? ???????c g???i th??nh c??ng.
        public void onCodeSent(@NonNull String mverificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            verificationId = mverificationId;
        }
        @Override
        // H??m n??y ???????c g???i khi b???n x??c th???c th??nh c??ng. T???c l?? m?? code nh???p v??o ????ng v???i m?? ???? g???i.
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            signInWithPhoneAuthCredential(credential);
        }

        @Override
        //H??m n??y ???????c g???i khi kh??ng g???i ???????c m?? code. Ch??ng ta c???n th??ng b??o cho ng?????i d??ng bi???t. ??? v?? d??? n??y m??nh ????n gi???n l?? hi???n th??? m???t Toast th??ng b??o.
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ForgotPassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("L???i",e.getMessage());

        }

    };



}