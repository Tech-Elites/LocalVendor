package com.example.localvendorsupplychain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class UserLandingPage extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,LoginPage.class));
                return  true;
            default:
                return false;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing_page);
        setTitle("View Nearby Vendors");

        BottomNavigationView bnavview=findViewById(R.id.bottomNavigationView);
        NavController navController= Navigation.findNavController(this,R.id.fragment2);
        NavigationUI.setupWithNavController(bnavview,navController);
    }
}