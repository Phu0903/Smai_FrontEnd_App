package com.example.SmaiApp.Danhmuc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


        List<String> quanao = new ArrayList<String>();
        quanao.add("Quần áo trẻ nam");
        quanao.add("Quần áo trẻ nữ");
        quanao.add("Quần áo mẹ bầu");
        quanao.add("Quần áo chị em");
        quanao.add("Quần áo nam");



        List<String> hoctap = new ArrayList<String>();
        hoctap.add("Giáo trình đại học");
        hoctap.add("Sách lớp 12");
        hoctap.add("Sách lớp 11");
        hoctap.add("Sách lớp 10");

        List<String> noitro = new ArrayList<String>();

        List<String> xeco = new ArrayList<String>();


        List<String> noithat = new ArrayList<String>();


        expandableListDetail.put("Quần áo", quanao);
        expandableListDetail.put("Học tập", hoctap);
        expandableListDetail.put("Nội trợ", noitro);
        expandableListDetail.put("Xe cộ", xeco);
        expandableListDetail.put("Nội thất", noithat);


        return expandableListDetail;
    }
}
