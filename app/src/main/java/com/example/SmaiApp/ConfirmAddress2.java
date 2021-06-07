package com.example.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ConfirmAddress2 extends AppCompatActivity {

    private String myDataAddress;
    Button btn_xacnhan;
    public static final int CANCEL_REQUEST_CODE = 1;
    TextInputLayout layoutCity, layoutDistrict, layoutWard, layoutDetailLocation;
    TextInputEditText city, district, ward, detailLocation;
    String nameCity, nameDistrict, nameWard, nameDetailLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_address2);

        findAction(); // get id each of components
        getTextAction(); // get value each of components

        city.addTextChangedListener(new ConfirmCityTextWatcher(city));
        district.addTextChangedListener(new ConfirmDistrictTextWatcher(district));
        ward.addTextChangedListener(new ConfirmWardTextWatcher(ward));
        detailLocation.addTextChangedListener(new ConfirmDetailLocationTextWatcher(detailLocation));

        Intent intent1 = getIntent();
        String token = intent1.getStringExtra("Token");
        String TypeAuthor = intent1.getStringExtra("TypeAuthor");
        Log.d("Token address", token);


        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(nameCity, layoutCity, city) == true && validate(nameDistrict, layoutDistrict, district) == true
                        && validate(nameWard, layoutWard, ward) && validate(nameDetailLocation, layoutDetailLocation, detailLocation) == true) {
                    Intent intent = new Intent(getApplicationContext(), CategoryActivity2.class);
                    getTextAction();
                    Bundle bundle = new Bundle();
                    bundle.putString("address", nameDetailLocation + ", " + nameWard + ", " + nameDistrict + ", " + nameCity);
                    bundle.putString("token", token);
                    bundle.putString("TypeAuthor",TypeAuthor);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }
    private void findAction() {

        btn_xacnhan = findViewById(R.id.btnXacNhan);
        city = findViewById(R.id.textview_city);
        district = findViewById(R.id.textview_quanhuyen);
        ward = findViewById(R.id.textview_phuongxa);
        detailLocation = findViewById(R.id.textview_vitricuthe);
        layoutCity = findViewById(R.id.inputlayout_city);
        layoutDistrict = findViewById(R.id.inputlayout_district);
        layoutWard = findViewById(R.id.inputlayout_ward);
        layoutDetailLocation = findViewById(R.id.inputlayout_detail);

    }

    private void getTextAction() {
        nameCity = city.getText().toString();
        nameDistrict = district.getText().toString();
        nameWard = ward.getText().toString();
        nameDetailLocation = detailLocation.getText().toString();
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

    private class ConfirmCityTextWatcher implements TextWatcher {

        private View view;

        private ConfirmCityTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            getTextAction();
            validate(nameCity, layoutCity, city);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layoutCity.setErrorEnabled(false);
        }
    }

    private class ConfirmDistrictTextWatcher implements TextWatcher {

        private View view;

        private ConfirmDistrictTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            getTextAction();
            validate(nameDistrict, layoutDistrict, district);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layoutDistrict.setErrorEnabled(false);
        }
    }

    private class ConfirmWardTextWatcher implements TextWatcher {

        private View view;

        private ConfirmWardTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            getTextAction();
            validate(nameWard, layoutWard, ward);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layoutWard.setErrorEnabled(false);
        }
    }

    private class ConfirmDetailLocationTextWatcher implements TextWatcher {

        private View view;

        private ConfirmDetailLocationTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            getTextAction();
            validate(nameDetailLocation, layoutDetailLocation, detailLocation);
        }

        @Override
        public void afterTextChanged(Editable s) {
            layoutDetailLocation.setErrorEnabled(false);
        }
    }

    public String getMyDataAddress() {
        return myDataAddress;
    }
}