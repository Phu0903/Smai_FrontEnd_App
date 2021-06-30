package com.smait.quyengop.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.smait.quyengop.Controller.Adapter.GiveforAdapter;
import com.smait.quyengop.Model.PostNewsModel;
import com.smait.quyengop.Model.ProductModel;
import com.smait.quyengop.Controller.NetWorKing.ApiServices;
import com.smait.quyengop.Controller.NetWorKing.RetrofitClient;
import com.smait.quyengop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import pl.utkala.searchablespinner.SearchableSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GiveFor extends AppCompatActivity {

    ListView lvNews_New;
    JSONArray jsonCityArray;
    SearchableSpinner searchableSpinner;
    EditText searchView;
    GiveforAdapter adaptergivfor;
    String address = "";
    String tokenMain;
    String TypeAuthor;
    public static List<PostNewsModel> posts;
    LinearLayout linearLayout;
    ArrayList<PostNewsModel> listName = new ArrayList<>();
    String namePro;

    SharedPreferences preferences;
    String realData;
    String realToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_for);

        if (savedInstanceState != null) {
            TypeAuthor = savedInstanceState.getString("TypeAuthor");
            address = savedInstanceState.getString("address");
            tokenMain = savedInstanceState.getString("token");
            namePro = savedInstanceState.getString("namepro");

        }
        else {
            Log.d("savedinstantcestate", "is nulllllll");
        }

        realData = HomeFragment.sendMyDataHome();
        realToken = realData.split(",")[1];

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dt);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        linearLayout = findViewById(R.id.linearlayoutGiveFor);
        linearLayout.requestFocus();
        lvNews_New = findViewById(R.id.listView_givefor);
        Intent intent = getIntent();
        namePro = intent.getStringExtra("ListName");



        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            address = bundle.getString("address");
            TypeAuthor = bundle.getString("TypeAuthor");
            tokenMain = bundle.getString("token");

        }

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiServices jsonPlaceHolderApi = retrofit.create(ApiServices.class);

        Call<List<PostNewsModel>> call = jsonPlaceHolderApi.getPostGiveCommunity(TypeAuthor);


        call.enqueue(new Callback<List<PostNewsModel>>() {
            @Override
            public void onResponse(Call<List<PostNewsModel>> call, Response<List<PostNewsModel>> response) {
                if ( !response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                    return;
                }
                posts = response.body();




//                adaptergivfor = new GiveforAdapter(GiveFor.this, R.layout.row_givefor, posts);
//                lvNews_New.setAdapter(adaptergivfor);
//                initSearchWidgets(posts);
//                initFilter(posts);
                if (namePro != null) {
                    for (int i=0;i<posts.size();i++) {
                        List<ProductModel> list = posts.get(i).getNameProduct();
                        for (int j=0;j<list.size();j++) {
                            String nameCategory = list.get(j).getNameProduct();
                            if (nameCategory.equals(namePro)) {
                                listName.add(posts.get(i));
                            }
                        }
                    }


                    adaptergivfor = new GiveforAdapter(GiveFor.this, R.layout.row_givefor, listName);
                    lvNews_New.setAdapter(adaptergivfor);
                    initSearchWidgets(listName);
                    initFilter(listName);
                }



                lvNews_New.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(getApplicationContext(), DetailPostGiveFor.class);
                        PostNewsModel post = posts.get(position);
                        String title = post.getTitle();

                        List<ProductModel> productModel = post.getNameProduct();
                        ArrayList<String> listNameCategory = new ArrayList<>();
                        ArrayList<String> listNamee = new ArrayList<>();
                        if (productModel.size() != 0) {
                            String detailType = productModel.get(0).getCategory();
                            for (int i=0;i<productModel.size();i++) {
                                listNameCategory.add(productModel.get(i).getCategory());
                                listNamee.add(productModel.get(i).getNameProduct());
                            }
                            intent.putExtra("detailType", detailType);
                            intent.putStringArrayListExtra("ListName", listNamee);
                        }
                        String address = post.getAddress();
                        String fullName = post.getNameAuthor();
                        String inforDetail = post.getNote();
                        String typeAuthor = post.getTypeAuthor();
                        List<String> listUrl = post.getUrlImage();
                        ArrayList<String> arrayListurl = new ArrayList<>();
                        for (String s: listUrl) {
                            arrayListurl.add(s);
                        }
                        String AuthorID = post.getAuthorID();

                        String idpost = posts.get(position).get_id();
                        intent.putExtra("idpost", idpost);
                        intent.putExtra("title", title);
                        intent.putExtra("address", address);
                        intent.putExtra("fullName", fullName);
                        intent.putExtra("inforDetail", inforDetail);
                        intent.putExtra("typeAuthor", typeAuthor);
                        intent.putExtra("AuthorID", AuthorID);
                        intent.putStringArrayListExtra("url", arrayListurl);
                        startActivity(intent);


                    }
                });

            }

            @Override
            public void onFailure(Call<List<PostNewsModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_LONG).show();
                Log.d("Error", t.getMessage());

            }
        });

    }


    private void initSearchWidgets(List<PostNewsModel> posts) {
        searchView = findViewById(R.id.search);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<PostNewsModel> postNewsModelArrayList = new ArrayList<>();

                for (PostNewsModel post: posts) {
                    if( post.getNameAuthor().toLowerCase().contains(s.toString().toLowerCase())) {
                        postNewsModelArrayList.add(post);
                    }
                }

                adaptergivfor = new GiveforAdapter(getApplicationContext(), R.layout.row_givefor, postNewsModelArrayList);
                lvNews_New.setAdapter(adaptergivfor);

            }
        });
    }

    private void initFilter(List<PostNewsModel> posts) {
        searchableSpinner = findViewById(R.id.searchableSpinner);

        try {
            jsonCityArray = new JSONObject(loadJSONFromAsset()).optJSONArray("province");

            ArrayList<String> cityList = new ArrayList<String>();
            cityList.add(0, "Tỉnh/thành phố");


            for (int i = 0; i < jsonCityArray.length(); i++) {
                cityList.add(jsonCityArray.optJSONObject(i).optString("name").trim());
            }
            searchableSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, cityList));

            searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    List<PostNewsModel> postsFilter = new ArrayList<>();

                    if (posts != null && posts.size() != 0) {

                        if (!cityList.get(position).equals("Tỉnh/thành phố")) {
                            for (int i = 0; i < posts.size(); i++) {
                                String[] address = posts.get(i).getAddress().split(", ");
                                if (cityList.get(position).contains(address[address.length - 1])) {
                                    postsFilter.add(posts.get(i));
                                }
                            }

                            GiveforAdapter adapter1 = new GiveforAdapter(GiveFor.this,  R.layout.row_givefor, postsFilter);
                            lvNews_New.setAdapter(adapter1);
                        } else  {
                            adaptergivfor = new GiveforAdapter(GiveFor.this, R.layout.row_givefor, posts);
                            lvNews_New.setAdapter(adaptergivfor);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TypeAuthor", TypeAuthor);
        outState.putString("token", tokenMain);
        outState.putString("address", address);
        outState.putString("namepro", namePro);

    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        TypeAuthor = savedInstanceState.getString("TypeAuthor");
//        address = savedInstanceState.getString("address");
//        tokenMain = savedInstanceState.getString("token");
//
//    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar_danhmuc, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.button_cancel) {
            ConfirmCancel();
        }
        else {
            finish();
        }
        return true;
    }

    private void ConfirmCancel() {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(this);
        alerDialog.setTitle("Thông báo!");
        alerDialog.setMessage("Bạn có chắc muốn hủy không?");

        alerDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("Token", tokenMain);
                i.putExtra("message", "OK");
                startActivity(i);
                finish();
            }
        });
        alerDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = alerDialog.create();
        dialog.show();
        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(this.getResources().getColor(R.color.teal_700));
    }
}