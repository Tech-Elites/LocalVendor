package com.example.localvendorsupplychain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        new CountDownTimer(4000,1000)
        {
            public void onTick(long milliseconds)
            {

            }
            public void onFinish()
            {
                finish();
                startActivity(new Intent(getApplicationContext(),LoginPage.class));
            }

        }.start();

    }






}