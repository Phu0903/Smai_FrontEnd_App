package com.smait.SmaiApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ConfirmAddress3 extends AppCompatActivity {
    private String myDataAddress;
    Button btn_xacnhan;
    String nameCity, nameDistrict, nameWard, nameDetailLocation;
    EditText edt_DetailLocation;
    private final static String TAG = "countryspinnerexample";
    private JSONArray jsonCityArray, jsonDistrictArray, jsonCommuneArray;
    private Spinner citySpinner;
    private Spinner districtSpinner;
    private Spinner communeSpinner;

    TextView tvCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_address3);

        citySpinner = findViewById(R.id.spinner_country);
        districtSpinner = findViewById(R.id.spinner_state);
        communeSpinner = findViewById(R.id.spinner_city);
        edt_DetailLocation = findViewById(R.id.textview_detailLocation);
        btn_xacnhan = findViewById(R.id.btnXacNhan);
        tvCity = findViewById(R.id.tv_city);

        Intent intent1 = getIntent();
        String token = intent1.getStringExtra("Token");
        Log.d("Token address", token);
        populateSpinner();




        btn_xacnhan = findViewById(R.id.btnXacNhan);
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameCity = citySpinner.getSelectedItem().toString();
                nameDistrict = districtSpinner.getSelectedItem().toString();
                nameWard = communeSpinner.getSelectedItem().toString();
                nameDetailLocation = edt_DetailLocation.getText().toString();
                if (validate(edt_DetailLocation.getText().toString()) == true && nameCity != "Tỉnh/thành phố") {

                    Intent intent = new Intent(getApplicationContext(), who_activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("address", nameDetailLocation + ", " + nameWard + ", " + nameDistrict + ", " + nameCity);
                    bundle.putString("token", token);
                    bundle.putString("TypeAuthor", "tangcongdong");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                else {
                    tvCity.setError("Vui lòng chọn Tỉnh/thành phố");
                }
            }
        });

    }


    public String getMyDataAddress() {
        return myDataAddress;
    }

    private boolean validate(String s) {
        if (s.trim().equals("")) {
            edt_DetailLocation.setError("Vui lòng điền thông tin");
            return false;
        }
        return true;
    }

    private void populateSpinner() {
        try {
            jsonCityArray = new JSONObject(loadJSONFromAsset()).optJSONArray("province");
            jsonDistrictArray = new JSONObject(loadJSONFromAsset()).optJSONArray("district");
            jsonCommuneArray = new JSONObject(loadJSONFromAsset()).optJSONArray("commune");

            ArrayList<String> cityList = new ArrayList<>();
            ArrayList<String> cityIdList = new ArrayList<>();
            cityList.add(0, "Tỉnh/thành phố");


            for (int i = 0; i < jsonCityArray.length(); i++) {
                cityList.add(jsonCityArray.optJSONObject(i).optString("name").trim());
                cityIdList.add(jsonCityArray.optJSONObject(i).optString("idProvince"));
            }
            ArrayAdapter<String> cityListAdapter = new ArrayAdapter<>(ConfirmAddress3.this,
                    R.layout.row_spinner, cityList);

            citySpinner.setAdapter(cityListAdapter);

            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    tvCity.setError(null);
                    final ArrayList<String> districtList = new ArrayList<>();
                    if (position != 0 ) {

                        String idProvince = "";

                        final ArrayList<String> districtIdList = new ArrayList<>();
                        for (int i = 0; i < jsonDistrictArray.length(); i++) {
                            if (cityIdList.get(position-1)
                                    .equals(jsonDistrictArray.optJSONObject(i).optString("idProvince"))) {
                                districtList.add(jsonDistrictArray.optJSONObject(i).optString("name").trim());
                                districtIdList.add(jsonDistrictArray.optJSONObject(i).optString("idDistrict"));
                            }
                        }

                        ArrayAdapter<String> districtListAdapter = new ArrayAdapter<>(ConfirmAddress3.this,
                                R.layout.row_spinner, districtList);

                        districtSpinner.setAdapter(districtListAdapter);

                        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                final ArrayList<String> communeList = new ArrayList<>();
                                Log.d("District", (String) parent.getItemAtPosition(position) + ", " + districtIdList.get(position));

                                for (int i = 0; i < jsonCommuneArray.length(); i++) {

                                    if (districtIdList.get(position)
                                            .equals(jsonCommuneArray.optJSONObject(i).optString("idDistrict"))) {

                                        communeList.add(jsonCommuneArray.optJSONObject(i).optString("name").trim());
                                    }
                                }

                                ArrayAdapter<String> communeListAdapter = new ArrayAdapter<>(ConfirmAddress3.this,
                                        R.layout.row_spinner, communeList);

                                communeSpinner.setAdapter(communeListAdapter);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {

                        final ArrayList<String> districtList2 = new ArrayList<>();
                        districtList2.add("Quận/huyên");
                        ArrayAdapter<String> districtListAdapter2 = new ArrayAdapter<>(ConfirmAddress3.this,
                                R.layout.row_spinner_default, districtList2);
                        districtSpinner.setAdapter(districtListAdapter2);
                        final ArrayList<String> communeList2 = new ArrayList<>();
                        communeList2.add("Phường/Thị trấn/xã");
                        ArrayAdapter<String> communeListAdapter2 = new ArrayAdapter<>(ConfirmAddress3.this,
                                R.layout.row_spinner_default, communeList2);
                        communeSpinner.setAdapter(communeListAdapter2);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "error=" + e.getMessage());
        }
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