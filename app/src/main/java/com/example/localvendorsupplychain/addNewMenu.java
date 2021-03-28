package com.example.localvendorsupplychain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addNewMenu extends AppCompatActivity {
    String name,desc,veg;
    int price;
    private DatabaseReference mDatabase;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("New Menu Item");
        setContentView(R.layout.activity_add_new_menu);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addNewMenuToDB(View view) {
        EditText t1 = findViewById(R.id.NewMenuName);
        EditText t2 = findViewById(R.id.NewMenuDesc);
        EditText t3 = findViewById(R.id.NewMenuPrice);
        name = t1.getText().toString();
        desc = t2.getText().toString();
        price = Integer.parseInt(t3.getText().toString());

        Switch t4 = findViewById(R.id.NewMenuSwitch);
        if(t4.isChecked()){
            veg="Vegetarian";
        }
        else{
            veg="Non-Vegetarian";
        }

        mDatabase.child("vendormenu").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                }
                else {
                    try {
                        MenuClass m=new MenuClass(name,desc,price,veg);
                        long count=task.getResult().getChildrenCount();
                        count++;
                        FirebaseDatabase.getInstance().getReference().child("vendormenu").child(user.getUid()).child("d"+count).setValue(m.AddMenuToVendor());
                    }
                    catch (Exception e){
                        System.out.println("SEAW"+e);
                    }
                }
            }
        });

        finish();
    }
}