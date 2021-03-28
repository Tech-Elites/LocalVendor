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

import java.util.HashMap;

public class RegistrationAsUser extends AppCompatActivity {

    private FirebaseAuth auth;
    String firstNameS,lastNameS,mobileNoS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_as_user);
        auth=FirebaseAuth.getInstance();
    }

    public void UserRegisterSubmit(View view) {
        //Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
        EditText emailAddressNew=findViewById(R.id.UserRegisterEmail);
        EditText passwordNew=findViewById(R.id.UserRegisterPassword);
        EditText confirmPassword=findViewById(R.id.UserRegisterConfirmPassword);
        EditText firstName=findViewById(R.id.UserRegisterFname);
        EditText lastName=findViewById(R.id.UserRegisterLname);
        EditText mobileNo=findViewById(R.id.UserRegisterMobile);
        firstNameS=firstName.getText().toString();
        lastNameS=lastName.getText().toString();
        mobileNoS=mobileNo.getText().toString();
        String newEmail=emailAddressNew.getText().toString();
        String newPassword=passwordNew.getText().toString();
        String confirmPasswordString=confirmPassword.getText().toString();
        if(TextUtils.isEmpty(firstNameS)||TextUtils.isEmpty(lastNameS)||TextUtils.isEmpty(newEmail)||TextUtils.isEmpty(mobileNoS)||
                TextUtils.isEmpty(newPassword)||TextUtils.isEmpty(confirmPasswordString))
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Error")
                    .setMessage("Please fill all the details. ")
                    .setPositiveButton("Ok", null)
                    .show();
        }
        else {

            if (confirmPasswordString.compareTo(newPassword) != 0) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Password Invalid")
                        .setMessage("The passwords do not match. Please try again.")
                        .setPositiveButton("Ok", null)
                        .show();

            } else {
                if (confirmPassword.length() < 7) {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Password too short")
                            .setMessage("Password should be atleast 7 characters.")
                            .setPositiveButton("Ok", null)
                            .show();

                } else {
                    registerUser(newEmail, newPassword);

                }
            }
        }
    }

    private void registerUser(String newEmail, String newPassword)
    {

        auth.createUserWithEmailAndPassword(newEmail,newPassword).addOnCompleteListener(RegistrationAsUser.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null)
                    {
                        //Toast.makeText(RegistrationAsUser.this, "User here", Toast.LENGTH_SHORT).show();
                        User u=new User(firstNameS,lastNameS,mobileNoS,newEmail);
                        HashMap<String, Object> newUserCreds = u.AddDataToUserDataBase();
                        FirebaseDatabase.getInstance().getReference().child("userinfo").child("customers").child(user.getUid()).setValue(newUserCreds);
                        new AlertDialog.Builder(RegistrationAsUser.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Success")
                                .setMessage("User Registered Successfully!")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        Intent i = new Intent(getApplicationContext(),UserLandingPage.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    }
                                })
                                .show();
                    }
                }
                else
                {
                    new AlertDialog.Builder(RegistrationAsUser.this)
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