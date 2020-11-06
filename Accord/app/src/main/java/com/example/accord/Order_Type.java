package com.example.accord;

public class Order_Type {
    private String string;
    private int temp;

    public String GetOrder() {
        return string;
    }
    public void SetOrder(String Order) {
        string = Order;
    }
    public int GetImg() {
        return temp;
    }
    public void setImg(int img) {
        this.temp = img;
    }
}
