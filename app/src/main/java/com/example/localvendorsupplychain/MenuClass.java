package com.example.localvendorsupplychain;

public class MenuClass {
    private String name;
    private String desc;
    private float price;
    private String veg;

    public MenuClass(String name, String desc, float price, String veg) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.veg = veg;
    }
    MenuClass()
    {}

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public float getPrice() {
        return price;
    }

    public String isVeg() {
        return veg;
    }
}
