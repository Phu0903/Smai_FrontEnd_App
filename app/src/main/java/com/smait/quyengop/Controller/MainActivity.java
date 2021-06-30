package com.smait.quyengop.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smait.quyengop.R;

public class MainActivity extends AppCompatActivity {

    private static String realData;

    private String myData;
    SharedPreferences sharedPreferences;
    String message, token;
    String isLoged;
    String Loged = "DONE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        token = intent.getStringExtra("Token");
        isLoged = intent.getStringExtra("ISLOGINED");
        myData = message + "," + token;
        realData = token;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (message != null) {

            if (isLoged!= null && isLoged.equals("DONE")) {

                editor.putString("message", message);
                editor.putString("Token", token);
                editor.putString("loged", "DONE");
                editor.commit();
            } else {
                editor.clear();
                editor.commit();
            }

            myData = message + "," + token;
            realData = token;

        } else {
            if (sharedPreferences != null) {
                message = sharedPreferences.getString("message", "");
                token = sharedPreferences.getString("Token", "");
                isLoged = sharedPreferences.getString("loged", "");
                myData = message + "," + token;
                realData = token;

            }
            else {
                myData="";
                realData="";
            }
        }

    }

    private  BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch ( item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_upLoadNews:
                    selectedFragment = new UpLoadNewsFragment();
                    break;
                case R.id.nav_catogories:
                    selectedFragment = new UserFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;

        }
    };

    public String getMyData() {
        return myData;
    }
    public static String sendMyData() {
        return realData;
    }

}