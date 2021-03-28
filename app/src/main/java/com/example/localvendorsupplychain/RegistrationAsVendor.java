package com.example.localvendorsupplychain;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class RegistrationAsVendor extends AppCompatActivity {

    String firstName, lastName, email, shopName;
    String password, confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_as_vendor);
        getSupportActionBar().hide();
    }


    public void VendorRegisterNext(View view) {
        EditText t1 = (EditText) findViewById(R.id.VendorFirstName);
        firstName = t1.getText().toString();
        EditText t2 = (EditText) findViewById(R.id.VendorLastName);
        lastName = t2.getText().toString();
        EditText t3 = (EditText) findViewById(R.id.VendorEmail);
        email = t3.getText().toString();
        EditText t4 = (EditText) findViewById(R.id.VendorShopName);
        shopName = t4.getText().toString();
        EditText t5 = (EditText) findViewById(R.id.VendorPassword);
        password = t5.getText().toString();
        EditText t6 = (EditText) findViewById(R.id.VendorConfirmPassword);
        confirmPassword = t6.getText().toString();

        if(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(shopName) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Error")
                    .setMessage("Please fill all the details. ")
                    .setPositiveButton("Ok", null)
                    .show();
        }
        else if (password.compareTo(confirmPassword) != 0){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Password Invalid")
                    .setMessage("The passwords do not match. Please try again.")
                    .setPositiveButton("Ok", null)
                    .show();
        }
        else if(password.length()<7){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Password Too Short")
                    .setMessage("Password should be atleast 7 characters.")
                    .setPositiveButton("Ok", null)
                    .show();
        }
        else{
            Intent i = new Intent(this, RegistrationAsVendorTwo.class);
            i.putExtra("firstname",firstName);
            i.putExtra("lastname",lastName);
            i.putExtra("email",email);
            i.putExtra("shopname",shopName);
            i.putExtra("password",password);
            startActivity(i);
        }
    }
}