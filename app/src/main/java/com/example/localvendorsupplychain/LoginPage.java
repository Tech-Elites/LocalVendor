package com.example.localvendorsupplychain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

public class LoginPage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        firebaseAuth = FirebaseAuth.getInstance();



    }

    public void LoginPageLoginFunction(View view) {
        EditText email=findViewById(R.id.loginPageEmailId);
        EditText password=findViewById(R.id.loginPagePassword);
        String emailS=email.getText().toString();
        String passwordS=password.getText().toString();
        if(TextUtils.isEmpty(emailS) || TextUtils.isEmpty(passwordS))
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Invalid Details")
                    .setMessage("Fill all the details to proceed.")
                    .setPositiveButton("Ok",null)
                    .show();
        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(emailS,passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(LoginPage.this, "Logged in", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else
                    {
                        new AlertDialog.Builder(LoginPage.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Error logging in.")
                                .setMessage("Invalid login credentials. Please check your credentials and try again.")
                                .setPositiveButton("Yes",null)
                                .show();
                    }
                }
            });
        }
    }

    public void LoginPageRegisterFunction(View view) {
        Intent i = new Intent(this, RegistrationChooseMode.class);
        startActivity(i);
    }
}