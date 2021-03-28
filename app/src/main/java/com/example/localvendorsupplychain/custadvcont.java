package com.example.localvendorsupplychain;

import java.util.HashMap;

public class custadvcont {
    private String addresss,shopnamee,desc,mobileno;

    public custadvcont(String addresss, String shopnamee, String desc,String mobileno) {
        this.addresss = addresss;
        this.shopnamee = shopnamee;
        this.desc = desc;
        this.mobileno=mobileno;
    }
    custadvcont()
    {}

    public String getAddresss() {
        return addresss;
    }

    public String getShopnamee() {
        return shopnamee;
    }

    public String getDesc() {
        return desc;
    }

    public String getMobileno() {
        return mobileno;
    }

    public HashMap<String,Object> getInfo()
    {
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("addresss",addresss);
        hashMap.put("shopnamee",shopnamee);
        hashMap.put("desc",desc);
        hashMap.put("mobileno",mobileno);
        return  hashMap;
    }

}
