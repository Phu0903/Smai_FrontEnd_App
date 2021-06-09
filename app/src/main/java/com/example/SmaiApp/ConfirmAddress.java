package com.example.SmaiApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ConfirmAddress extends AppCompatActivity {
    private String myDataAddress;
    Button btn_xacnhan;
    public static final int CANCEL_REQUEST_CODE = 1;
    TextInputLayout layoutCity, layoutDistrict, layoutWard, layoutDetailLocation;
    TextInputEditText city, district, ward, detailLocation;
    String nameCity, nameDistrict, nameWard, nameDetailLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double getLongtitude, getLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_confirm_address);

        findAction(); // get id each of components
        getTextAction(); // get value each of components

        city.addTextChangedListener(new ConfirmCityTextWatcher(city));
        district.addTextChangedListener(new ConfirmDistrictTextWatcher(district));
        ward.addTextChangedListener(new ConfirmWardTextWatcher(ward));
        detailLocation.addTextChangedListener(new ConfirmDetailLocationTextWatcher(detailLocation));

        Intent intent1 = getIntent();
        String token = intent1.getStringExtra("Token");
        Log.d("Token address", token);


        btn_xacnhan = findViewById(R.id.btnXacNhan);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();

        } else {
            ActivityCompat.requestPermissions(ConfirmAddress.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(nameCity, layoutCity, city) == true && validate(nameDistrict, layoutDistrict, district) == true
                        && validate(nameWard, layoutWard, ward) && validate(nameDetailLocation, layoutDetailLocation, detailLocation) == true) {
                    Intent intent = new Intent(getApplicationContext(), CategoryActivity3.class);
                    getTextAction();
                    Bundle bundle = new Bundle();
                    bundle.putString("address", nameDetailLocation + ", " + nameWard + ", " + nameDistrict + ", " + nameCity);
                    bundle.putString("token", token);
                    bundle.putString("TypeAuthor", "Cá nhân");
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
        } else {
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

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

//                initialize location
                Location location = task.getResult();
                getLatitude = location.getLatitude();
                getLongtitude = location.getLongitude();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(ConfirmAddress.this, Locale.getDefault());

//                    initialaze address list
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        String vitri = addresses.get(0).getAddressLine(0);
                        String quocgia = "", thanhpho = "", tendiachi = "", quan = "";
                        quocgia = addresses.get(0).getCountryName();
                        thanhpho = addresses.get(0).getAdminArea();
                        quan = addresses.get(0).getSubAdminArea();
                        tendiachi = addresses.get(0).getFeatureName();
                        String[] diachi = vitri.split(",");
                        city.setText(diachi[diachi.length - 2]);
                        Log.d("Thanh pho", vitri);
                        district.setText(diachi[diachi.length - 3]);
                        ward.setText(diachi[diachi.length - 4]);
                        detailLocation.setText(tendiachi);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}