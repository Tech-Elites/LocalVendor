package com.example.localvendorsupplychain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addNewPostVendor extends AppCompatActivity {

    TextView t1,t2,t4;
    EditText t3;

    String name, address, post, mob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post_vendor);
        setTitle("New Post");

        t1=findViewById(R.id.NewPostShopName);
        t2=findViewById(R.id.NewPostShopAdd);
        t4=findViewById(R.id.NewPostMob);

        Bundle b = getIntent().getExtras();
        name=b.getString("name");
        address=b.getString("address");
        mob=b.getString("mob");
        t1.setText(b.getString("name"));
        t2.setText(b.getString("address"));
        t4.setText(mob);
        t3=findViewById(R.id.NewPostDesc);

    }

    public void submitNewPostVendor(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        post = t3.getText().toString();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("vendoradv").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                }
                else {
                    try {
                        custadvcont p = new custadvcont(address, name, post, mob);
                        long count=task.getResult().getChildrenCount();
                        count++;
                        FirebaseDatabase.getInstance().getReference().child("vendoradv").child(user.getUid()).child("ad"+count).setValue(p.getInfo());
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