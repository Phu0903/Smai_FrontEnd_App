package com.smait.quyengop.Controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.smait.quyengop.R;

import java.util.List;

public class PopupCategoryAdapter extends BaseAdapter {

    public PopupCategoryAdapter(Context myContext, int myLayout, List<String> arrayCategory) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrayCategory = arrayCategory;
    }

    Context myContext;
    int myLayout;
    List<String> arrayCategory;


    @Override
    public int getCount() {
        if (arrayCategory != null) {
            return arrayCategory.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);




        convertView = inflater.inflate(myLayout, null);

        CheckedTextView checkedTextView = convertView.findViewById(R.id.checkList);
        checkedTextView.setText(arrayCategory.get(position));
        checkedTextView.setChecked(true);

        return convertView;
    }
}
