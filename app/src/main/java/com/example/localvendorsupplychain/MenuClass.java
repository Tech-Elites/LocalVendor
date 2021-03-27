package com.example.localvendorsupplychain;

public class MenuClass {
    private String name;
    private String desc;
    private double price;
    private boolean veg;

    public MenuClass(String name, String desc, double price, boolean veg) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.veg = veg;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public double getPrice() {
        return price;
    }

    public boolean isVeg() {
        return veg;
    }
}
