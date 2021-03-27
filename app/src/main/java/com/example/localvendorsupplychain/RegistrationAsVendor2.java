package com.example.localvendorsupplychain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.Buffer;
import java.util.HashMap;

public class RegistrationAsVendor2 extends AppCompatActivity {

    String firstname, lastname, email, shopname;
    String password, mobilenumber, homeaddress;
    String shopaddress="TEMP";
    private  FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_as_vendor2);
        auth= FirebaseAuth.getInstance();

        Bundle b = getIntent().getExtras();
        firstname = b.getString("firstname");
        lastname = b.getString("lastname");
        email = b.getString("email");
        shopname = b.getString("shopname");
        password = b.getString("password");

    }

    public void VendorRegisterSubmit(View view) {
        EditText t1 = (EditText) findViewById(R.id.VendorMobile);
        mobilenumber = t1.getText().toString();
        EditText t2 = (EditText) findViewById(R.id.VendorHomeAddress);
        homeaddress = t2.getText().toString();

        if(TextUtils.isEmpty(mobilenumber) || TextUtils.isEmpty(homeaddress)){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Error")
                    .setMessage("Please fill all the details. ")
                    .setPositiveButton("Ok", null)
                    .show();
        }
        else{
            registerVendor();
        }

    }

    public void registerVendor(){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationAsVendor2.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null)
                    {
                        Toast.makeText(RegistrationAsVendor2.this, "User here", Toast.LENGTH_SHORT).show();
                        Vendor u=new Vendor(firstname,lastname,mobilenumber,email,shopname,homeaddress,shopaddress);
                        HashMap<String, Object> newUserCreds = u.AddDataToUserDataBase();
                        FirebaseDatabase.getInstance().getReference().child("userinfo").child("vendors").child(user.getUid()).setValue(newUserCreds);
                        new AlertDialog.Builder(RegistrationAsVendor2.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Success")
                                .setMessage("User Registered Successfully!")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    }
                                })
                                .show();
                    }
                }
                else
                {
                    new AlertDialog.Builder(RegistrationAsVendor2.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Error")
                            .setMessage("Registration failed "+task.getResult().toString())
                            .setPositiveButton("Ok", null)
                            .show();
                }
            }
        });
    }
}