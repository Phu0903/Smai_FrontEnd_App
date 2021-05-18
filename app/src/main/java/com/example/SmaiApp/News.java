package com.example.SmaiApp;

public class News {

    public int IDNews;
    public String PhoneNumber;
    public String TypeNews;
    public String Address;
    public String Catalog;
    public String Tittle;
    public String Note;
    public String txtCatalog;
    public int UrlImage;

    public News(int IDNews, String phoneNumber, String typeNews, String address, String catalog, String tittle, String note, int urlImage) {
        IDNews = IDNews;
        PhoneNumber = phoneNumber;
        TypeNews = typeNews;
        Address = address;
        Catalog = catalog;
        Tittle = tittle;
        Note = note;
        UrlImage = urlImage;
    }
}
