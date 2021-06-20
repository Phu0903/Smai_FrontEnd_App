package com.smait.SmaiApp.Helper;

import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class LVHelper {
    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
//        if (myListAdapter == null) {
//            //do nothing return null
//            return;
//        }
//        //set listAdapter in loop for getting final size
//        int totalHeight = 0;
//        if (myListAdapter.getCount() == 12) {
//
//        }
//        for (int size = 0; size < myListAdapter.getCount(); size++) {
//            View listItem = myListAdapter.getView(size, null, myListView);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        //setting listview item in adapter
//        ViewGroup.LayoutParams params = myListView.getLayoutParams();
//        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
//        myListView.setLayoutParams(params);

        int itemcount=myListAdapter.getCount();
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height =(12*88);
        myListView.setLayoutParams(params);
        myListView.requestLayout();

    }

}
