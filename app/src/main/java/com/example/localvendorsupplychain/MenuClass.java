package com.example.localvendorsupplychain;

import java.util.HashMap;

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

    public String getVeg() {
        return veg;
    }

    public HashMap<String, Object> AddMenuToVendor()
    {
        HashMap<String,Object> newMenu=new HashMap<>();
        newMenu.put("name",name);
        newMenu.put("desc",desc);
        newMenu.put("price",price);
        newMenu.put("veg",veg);
        return  newMenu;
    }
}
