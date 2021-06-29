package com.smait.quyengop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.smait.quyengop.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ConfirmAddress3 extends AppCompatActivity {
    private String myDataAddress;
    Button btn_xacnhan;
    String nameCity, nameDistrict, nameWard, nameDetailLocation;
    TextInputEditText edt_DetailLocation;
    private final static String TAG = "countryspinnerexample";
    private JSONArray jsonCityArray, jsonDistrictArray, jsonCommuneArray;


    SharedPreferences sharedPreferences;
    SharedPreferences myPrefs;

    TextInputLayout layoutCity, layoutDistrict, layoutCommune, layoutLocation;
    AutoCompleteTextView cityAuto, districtAuto, communeAuto;

    ArrayAdapter<String> arrayAdapterCity;

    ArrayList<String> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_address3);


        btn_xacnhan = findViewById(R.id.btnXacNhan);
        Intent intent1 = getIntent();
        String token = intent1.getStringExtra("Token");

        try {
            jsonCityArray = new JSONObject(loadJSONFromAsset()).optJSONArray("province");
            jsonDistrictArray = new JSONObject(loadJSONFromAsset()).optJSONArray("district");
            jsonCommuneArray = new JSONObject(loadJSONFromAsset()).optJSONArray("commune");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "error=" + e.getMessage());
        }

        layoutCity = findViewById(R.id.layoutCity);
        layoutDistrict = findViewById(R.id.layoutDistrict);
        layoutCommune = findViewById(R.id.layoutCommune);
        layoutLocation = findViewById(R.id.layoutLocation);

        cityList = new ArrayList<>();
        cityAuto  = findViewById(R.id.autoCity);
        districtAuto  = findViewById(R.id.autoDistrict);
        communeAuto  = findViewById(R.id.autoCommune);
        edt_DetailLocation = findViewById(R.id.detailLocation);
        sharedPreferences = getSharedPreferences("dataAddress", MODE_PRIVATE);

//        lấy giá trị địa chỉ đã lưu

        if (sharedPreferences != null) {
            cityAuto.setText(sharedPreferences.getString("city", ""));
            districtAuto.setText(sharedPreferences.getString("district", ""));
            communeAuto.setText(sharedPreferences.getString("commune", ""));
            edt_DetailLocation.setText(sharedPreferences.getString("location", ""));
        }

        for (int i = 0; i < jsonCityArray.length(); i++) {
            cityList.add(jsonCityArray.optJSONObject(i).optString("name").trim());
        }

        arrayAdapterCity = new ArrayAdapter<>(ConfirmAddress3.this, R.layout.support_simple_spinner_dropdown_item, cityList);
        cityAuto.setAdapter(arrayAdapterCity);

        loadDistrict();
        loadCommune();

        listenTextChange(layoutCity, cityAuto);
        listenTextChange(layoutDistrict, districtAuto);
        listenTextChange(layoutCommune, communeAuto);
        listenEditText(layoutLocation, edt_DetailLocation);



        btn_xacnhan = findViewById(R.id.btnXacNhan);
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameCity = cityAuto.getText().toString();
                nameDistrict = districtAuto.getText().toString();
                nameWard = communeAuto.getText().toString();
                nameDetailLocation = edt_DetailLocation.getText().toString();

                if (validateCity(nameCity, layoutCity, cityAuto) == true && validateCity(nameDistrict, layoutDistrict, districtAuto) &&
                        validateCity(nameWard, layoutCommune, communeAuto) == true && validate(nameDetailLocation) == true) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("city", nameCity);
                    editor.putString("district", nameDistrict);
                    editor.putString("commune", nameWard);
                    editor.putString("location", nameDetailLocation);
                    editor.commit();

                    btn_xacnhan.setEnabled(false);
                    Intent intent = new Intent(getApplicationContext(), who_activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("address", nameDetailLocation + ", " + nameWard + ", " + nameDistrict + ", " + nameCity);
                    bundle.putString("token", token);
                    bundle.putString("TypeAuthor", "tangcongdong");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }


    public String getMyDataAddress() {
        return myDataAddress;
    }

    private boolean validate(String s) {
        if (s.trim().equals("")) {
            layoutLocation.setError("Vui lòng điền thông tin");
            return false;
        }
        return true;
    }

    private boolean validateCity(String s, TextInputLayout layout, AutoCompleteTextView autoCompleteTextView) {
        if (s.equals("")) {
            layout.setError("Vui lòng điền đầy đủ thông tin");
            return false;
        }
        return true;

    }

    private void listenTextChange(TextInputLayout layout, AutoCompleteTextView autoCompleteTextView) {
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                layout.setErrorEnabled(false);
            }
        });
    }

    private void listenEditText(TextInputLayout layout, TextInputEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                layout.setErrorEnabled(false);
            }
        });
    }


    public void loadDistrict() {

        cityAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String city = cityAuto.getText().toString();
                String idCity = null;
                ArrayList<String> districList = new ArrayList<>();

                for (int i = 0; i < jsonCityArray.length(); i++) {
                    if (jsonCityArray.optJSONObject(i).optString("name").equals(city)) {
                        idCity = jsonCityArray.optJSONObject(i).optString("idProvince");
                    }
                }
                districtAuto.setText("");
                Log.d("City selected", cityAuto.getText() + "");
                for (int i = 0; i < jsonDistrictArray.length(); i++) {
                    if (jsonDistrictArray.optJSONObject(i).optString("idProvince").equals(idCity)) {
                        districList.add(jsonDistrictArray.optJSONObject(i).optString("name"));
                    }
                }
                ArrayAdapter<String> arrayAdapterDistrict = new ArrayAdapter<>(ConfirmAddress3.this, R.layout.support_simple_spinner_dropdown_item, districList);
                districtAuto.setAdapter(arrayAdapterDistrict);
            }
        });
    }

    public void loadCommune() {
        districtAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String district = districtAuto.getText().toString();
                String idDistrict = null;
                ArrayList<String> communeList = new ArrayList<>();

                for (int i = 0; i < jsonDistrictArray.length(); i++) {
                    if (jsonDistrictArray.optJSONObject(i).optString("name").equals(district)) {
                        idDistrict = jsonDistrictArray.optJSONObject(i).optString("idDistrict");
                    }
                }
                communeAuto.setText("");
                Log.d("City selected", cityAuto.getText() + "");
                for (int i = 0; i < jsonCommuneArray.length(); i++) {
                    if (jsonCommuneArray.optJSONObject(i).optString("idDistrict").equals(idDistrict)) {
                        communeList.add(jsonCommuneArray.optJSONObject(i).optString("name"));
                    }
                }
                ArrayAdapter<String> arrayAdapterCommune = new ArrayAdapter<>(ConfirmAddress3.this, R.layout.support_simple_spinner_dropdown_item, communeList);
                communeAuto.setAdapter(arrayAdapterCommune);
            }
        });
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("db.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}