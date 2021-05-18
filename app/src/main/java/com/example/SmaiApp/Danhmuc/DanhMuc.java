package com.example.SmaiApp.Danhmuc;

public class DanhMuc {

    public DanhMuc(String tittle, String content) {
        this.tittle = tittle;
        this.content = content;
    }

    public String getTittle() {
        return tittle;
    }

    public String getContent() {
        return content;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String tittle;
    private String content;
}
