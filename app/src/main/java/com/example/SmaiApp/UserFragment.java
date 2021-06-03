package com.example.SmaiApp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserFragment extends Fragment {

    private  static final String IS_LOGIN_USER = "OK";

    Button btnHistory;
    Button btn_call;
    LinearLayout layoutInfor, layoutRequired;
    Button btnLilo;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user, container, false);


//        view History
        btnHistory = (Button) view.findViewById(R.id.view_history);

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewHistory.class);
                startActivity(intent);
            }
        });

        setHasOptionsMenu(true);
        Toolbar actionBarToolBar = (Toolbar) view.findViewById(R.id.toolbar_user);
        actionBarToolBar.inflateMenu(R.menu.menu_toolbar_account);
        actionBarToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.log_out:
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        btn_call = view.findViewById(R.id.call_user);
//        button call
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0938516899"));
                startActivity(intent);
            }
        });



        layoutInfor = view.findViewById(R.id.linearlayout_infor);
        layoutRequired = view.findViewById(R.id.linearlayout_requireUser);
        btnLilo = view.findViewById(R.id.btn_LILO);

        btnLilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Account.class);
                startActivity(intent);
            }
        });

        MainActivity activity = (MainActivity) getActivity();
        String codeLogin  = activity.getMyData();

        String[] code = codeLogin.split(",");

        String message = code[0];
        String token = code[1];

        if (message.equals("OK")) {
            layoutInfor.setVisibility(View.VISIBLE);
            layoutRequired.setVisibility(View.GONE);
        }

        Log.d("Message and token", message + ",    " + token);

        return view;
    }



}