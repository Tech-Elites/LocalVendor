package com.example.localvendorsupplychain;

import java.util.HashMap;

public class Vendor {
    public String firstname, lastname, shopname;
    public String mobileno,emailid,homeaddress,shopaddress;

    Vendor()
    {

    }
    Vendor(String fn,String ln,String mn,String ei, String shop, String had, String sad)
    {
        firstname=fn;
        lastname=ln;
        mobileno=mn;
        emailid=ei;
        shopname=shop;
        homeaddress=had;
        shopaddress=sad;
    }
    public HashMap<String, Object> AddDataToUserDataBase()
    {
        HashMap<String,Object> newUser=new HashMap<>();
        newUser.put("firstname",firstname);
        newUser.put("lastname",lastname);
        newUser.put("mobileno",mobileno);
        newUser.put("emailid",emailid);
        newUser.put("shopname",shopname);
        newUser.put("homeaddress",homeaddress);
        newUser.put("shopaddress",shopaddress);
        return  newUser;
    }
}