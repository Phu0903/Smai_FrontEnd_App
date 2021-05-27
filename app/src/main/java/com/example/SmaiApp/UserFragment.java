package com.example.SmaiApp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UserFragment extends Fragment {


    Button btnHistory;

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
                        Intent intent = new Intent(getContext(), Account.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

        return view;
    }



}