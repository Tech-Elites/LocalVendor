package com.example.localvendorsupplychain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistrationChooseMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_choose_mode);
        getSupportActionBar().hide();
    }

    public void RegisterAsUser(View view) {
        Intent i = new Intent(this, RegistrationAsUser.class);
        startActivity(i);
    }

    public void RegisterAsVendor(View view) {
        Intent i = new Intent(this, RegistrationAsVendor.class);
        startActivity(i);
    }
}