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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser u= firebaseAuth.getCurrentUser();
        if(u!=null)
        {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("userinfo").child("customers");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int flag=1;
                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        if(u.getUid().compareTo(dataSnapshot.getKey())==0)
                        {
                            flag=0;
                            startActivity(new Intent(getApplicationContext(),locationSearchActivity.class));
                            break;
                        }
                    }
                    if(flag==1)
                    {
                        Toast.makeText(LoginPage.this, "A vendor is here", Toast.LENGTH_SHORT).show();
                        //vendor code here
                        //for now signing out
                        FirebaseAuth.getInstance().signOut();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

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
                        FirebaseUser u= firebaseAuth.getCurrentUser();
                        if(u!=null)
                        {
                            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("userinfo").child("customers");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int flag=1;
                                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                                    {
                                        if(u.getUid().compareTo(dataSnapshot.getKey())==0)
                                        {
                                            flag=0;
                                            startActivity(new Intent(getApplicationContext(),locationSearchActivity.class));
                                            break;
                                        }
                                    }
                                    if(flag==1)
                                    {
                                        Toast.makeText(LoginPage.this, "A vendor is here", Toast.LENGTH_SHORT).show();
                                        //vendor code here
                                        //for now signing out
                                        FirebaseAuth.getInstance().signOut();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
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