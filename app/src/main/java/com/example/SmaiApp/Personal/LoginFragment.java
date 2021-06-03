package com.example.SmaiApp.Personal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.SmaiApp.Account;
import com.example.SmaiApp.MainActivity;
import com.example.SmaiApp.Model.AccountModel;
import com.example.SmaiApp.Model.PostNewsModel;
import com.example.SmaiApp.NetWorKing.ApiServices;
import com.example.SmaiApp.NetWorKing.RetrofitClient;
import com.example.SmaiApp.R;
import com.example.SmaiApp.UserFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {

    TextClicked mCallback;

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
    CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btn_login = view.findViewById(R.id.btn_login);
        edtUserName = view.findViewById(R.id.textview_username);
        edtPassWord = view.findViewById(R.id.textView_password);
        checkBox = view.findViewById(R.id.checkbox);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = edtUserName.getText().toString();
                String passWord = edtPassWord.getText().toString();
                AccountModel accountModel = new AccountModel();
                accountModel.setUserName(userName);
                accountModel.setPassword(passWord);

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
                    Toast.makeText(getContext(), "Username or password required", Toast.LENGTH_SHORT).show();
                }
                else {

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

                                Log.d("Token", tk);

                                Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                                intent.putExtra("message", message);
                                intent.putExtra("Token", tk);
                                getActivity().startActivity(intent);

                            }
                            else {
                                Toast.makeText(getContext(), "Login is not correct", Toast.LENGTH_SHORT).show();
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


}