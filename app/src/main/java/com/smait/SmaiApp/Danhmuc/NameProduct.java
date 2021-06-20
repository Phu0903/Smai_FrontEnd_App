package com.smait.SmaiApp.Danhmuc;

public class NameProduct {
    public NameProduct(String category, String nameProduct) {
        Category = category;
        NameProduct = nameProduct;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    private String Category;
    private String NameProduct;
}
