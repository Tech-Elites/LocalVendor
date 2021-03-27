package com.example.localvendorsupplychain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if (u != null) {
            //logged in
        } else {
            // go to login
        }
        //Main - login/sign in
        //RegisterActivity - signup
        //customer - fname lname mobile
        //vendor - vendorName shopName shopAddress mobile (INSIDE-operatingHours, servicesProvided)

        //i have changed here

        //hello harshva

    }

    public void goToLogin(View view) {
        Intent i = new Intent(this, LoginPage.class);
        startActivity(i);
    }

    public void goToMap(View view) {
        Intent i = new Intent(this, locationSearchActivity.class);
        startActivity(i);
    }
}