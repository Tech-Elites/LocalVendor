package com.example.localvendorsupplychain;

import java.util.HashMap;

public class User {
    public String firstname,lastname;
    public String mobileno,emailid;


    User()
    {

    }
    User(String fn,String ln,String mn,String ei)
    {
        firstname=fn;
        lastname=ln;
        mobileno=mn;
        emailid=ei;

    }
    public HashMap<String, Object> AddDataToUserDataBase()
    {
        HashMap<String,Object> newUser=new HashMap<>();
        newUser.put("firstname",firstname);
        newUser.put("lastname",lastname);
        newUser.put("mobileno",mobileno);
        newUser.put("emailid",emailid);
        return  newUser;
    }
}
