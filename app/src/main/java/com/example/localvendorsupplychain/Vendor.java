package com.example.localvendorsupplychain;

import java.util.HashMap;

public class Vendor {
    public String firstName, lastName, shopName;
    public String mobileNo,EmailId;

    Vendor()
    {

    }
    Vendor(String fn,String ln,String mn,String ei, String shop)
    {
        firstName=fn;
        lastName=ln;
        mobileNo=mn;
        EmailId=ei;
        shopName=shop;
    }
    public HashMap<String, Object> AddDataToUserDataBase()
    {
        HashMap<String,Object> newUser=new HashMap<>();
        newUser.put("firstName",firstName);
        newUser.put("lastName",lastName);
        newUser.put("mobileNo",mobileNo);
        newUser.put("EmailId",EmailId);
        newUser.put("ShopName",shopName);
        return  newUser;
    }
}