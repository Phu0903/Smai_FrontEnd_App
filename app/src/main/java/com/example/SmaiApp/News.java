package com.example.SmaiApp;

public class News {

    public News(int IDNews, String phoneNumber, String typeNews, String address, String catalog, String tittle, String note, String datePost, int urlImage) {
        this.IDNews = IDNews;
        PhoneNumber = phoneNumber;
        TypeNews = typeNews;
        Address = address;
        Catalog = catalog;
        Tittle = tittle;
        Note = note;
        DatePost = datePost;
        UrlImage = urlImage;
    }

    public int IDNews;
    public String PhoneNumber;
    public String TypeNews;
    public String Address;
    public String Catalog;
    public String Tittle;
    public String Note;
    public String DatePost;
    public int UrlImage;


}
