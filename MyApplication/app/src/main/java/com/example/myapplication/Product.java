package com.example.myapplication;

public class Product {

    private int picItem;
    private int idItem;

    public Product(int picItem, int idItem) {
        this.picItem = picItem;
        this.idItem = idItem;
    }

    public int getidItem() {
        return idItem;
    }

    public void setidItem(int idItem) {
        this.idItem = idItem;
    }

    public int getpicItem() {
        return picItem;
    }

    public void setpicItem(int picItem) {
        this.picItem = picItem;
    }

}
