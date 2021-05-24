package com.example.SmaiApp.Model;

import android.net.Uri;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

public class PostNewsModel {
    private String AuthorID;
    private String TypeAuthor;
    private String NameAuthor;
    private String address;
    private List<ProductModel> NameProdcut;
    private String title;
    private String note;
    private List<String> urlImage;
    private Date createdAt;
    private Date updatedAt;

    public String getAuthorID() {
        return AuthorID;
    }

    public void setAuthorID(String authorID) {
        AuthorID = authorID;
    }

    public String getTypeAuthor() {
        return TypeAuthor;
    }

    public void setTypeAuthor(String typeAuthor) {
        TypeAuthor = typeAuthor;
    }

    public String getNameAuthor() {
        return NameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        NameAuthor = nameAuthor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductModel> getNameProdcut() {
        return NameProdcut;
    }

    public void setNameProdcut(List<ProductModel> nameProdcut) {
        NameProdcut = nameProdcut;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<String> getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(List<String> urlImage) {
        this.urlImage = urlImage;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
