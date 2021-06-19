package com.example.SmaiApp.Danhmuc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


        List<String> quanao = new ArrayList<String>();
        quanao.add("Đồ tổng hợp");
        quanao.add("Đồ trẻ sơ sinh");
        quanao.add("Đồ trẻ em nam");
        quanao.add("Đồ trẻ em nữ");
        quanao.add("Đồ nữ giới");
        quanao.add("Đồ nam giới");
        quanao.add("Đồ mẹ bầu");
        quanao.add("Đồ cho người cao tuổi nam");
        quanao.add("Đồ cho người cao tuổi nữ");

        List<String> hoctap = new ArrayList<String>();
        hoctap.add("Dụng cụ hỗ trợ học tập");
        hoctap.add("Sách vở tổng hợp");
        hoctap.add("Sách vở lớp 1");
        hoctap.add("Sách vở lớp 2");
        hoctap.add("Sách vở lớp 3");
        hoctap.add("Sách vở lớp 4");
        hoctap.add("Sách vở lớp 5");
        hoctap.add("Sách vở lớp 6");
        hoctap.add("Sách vở lớp 7");
        hoctap.add("Sách vở lớp 8");
        hoctap.add("Sách vở lớp 9");
        hoctap.add("Sách vở lớp 10");
        hoctap.add("Sách vở lớp 11");
        hoctap.add("Sách vở lớp 12");
        hoctap.add("Sách vở ôn thi đại học");
        hoctap.add("Giáo trình cao đẳng");
        hoctap.add("Giáo trình đại học");
        hoctap.add("Giáo trình, sách vở,... khác");

        List<String> luongthucthucpham = new ArrayList<String>();
        luongthucthucpham.add("Lương thực “gạo, mì tôm,…”");
        luongthucthucpham.add("Da vị “mắm, muối, mì chính,…”");         //rồi
        luongthucthucpham.add("Bánh kẹo, nước, đồ ăn nhanh,…");
        luongthucthucpham.add("Đồ khác");

        List<String> noithat = new ArrayList<String>();
        noithat.add("Chăn, màn, đệm…");                 //rồi
        noithat.add("Giường, bàn ghế, tủ kệ,…");
        noithat.add("Đồ khác");

        List<String> noitro = new ArrayList<String>();
        noitro.add("Chén bát, nồi, xong, chảo,…");
        noitro.add("Thau, chậu, thùng phi,..");                             // rồi
        noitro.add("Nồi điện, bếp điện,..");
        noitro.add("Máy lạnh, tủ lạnh, quạt điện…");
        noitro.add("Đồ khác");


        List<String> dodientu = new ArrayList<String>();
        dodientu.add("Điện thoại, lap top, máy tính…");                                 // rồi
        dodientu.add("Tivi, loa, đài");
        dodientu.add("Đồ khác");

        List<String> xeco = new ArrayList<String>();
        xeco.add("Xe đạp, ô tô, ...cho trẻ em");
        xeco.add("Xe đẩy, xe năn,.. người cao tuổi");   //rồi
        xeco.add("Xe đạp, xe điện, xe máy");
        xeco.add("Xe khác");

        List<String> dokhac = new ArrayList<String>();
        dokhac.add("Đồ tổng hợp cho trẻ em");
        dokhac.add("Đồ tổng hợp cho người lớn");              //rồi
        dokhac.add("Đồ tổng hợp cho người cao tuổi");
        dokhac.add("Đồ tổng hợp khác");



        expandableListDetail.put("Đồ may mặc", quanao);
        expandableListDetail.put("Đồ học tập", hoctap);
        expandableListDetail.put("Lương thực, thực phẩm", luongthucthucpham);
        expandableListDetail.put("Đồ nội thất", noithat);
        expandableListDetail.put("Đồ nội trợ, điện dân dụng", noitro);
        expandableListDetail.put("Đồ điện tử", dodientu);
        expandableListDetail.put("Xe cộ", xeco);
        expandableListDetail.put("Đồ khác", dokhac);

        return expandableListDetail;
    }
}
