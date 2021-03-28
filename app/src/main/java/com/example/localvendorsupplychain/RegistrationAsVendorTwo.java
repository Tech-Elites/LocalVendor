package com.example.localvendorsupplychain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.Buffer;
import java.util.HashMap;

public class RegistrationAsVendorTwo extends AppCompatActivity {
    String firstname, lastname, email, shopname;
    String password, mobilenumber, homeaddress;
    String shopaddress="TEMP";
    LocationManager locationManager;
    LocationListener locationListener;
    Button registerButton;
    private FirebaseAuth auth;
    LatLng myLocation;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    searchFromCurrentLocation();
                }
            }
        }

    }

    void searchFromCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Toast.makeText(this, "Here", Toast.LENGTH_SHORT).show();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    void LocationServices() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                Toast.makeText(RegistrationAsVendorTwo.this, ""+myLocation, Toast.LENGTH_SHORT).show();
                locationManager.removeUpdates(locationListener);
                registerButton.setVisibility(View.VISIBLE);
                //call the function that can get the
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_as_vendor_two);
        LocationServices();
        registerButton=findViewById(R.id.VendorRegisterSubmit);
        registerButton.setVisibility(View.INVISIBLE);
        auth= FirebaseAuth.getInstance();

        Bundle b = getIntent().getExtras();
        firstname = b.getString("firstname");
        lastname = b.getString("lastname");
        email = b.getString("email");
        shopname = b.getString("shopname");
        password = b.getString("password");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            searchFromCurrentLocation();
        }
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
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationAsVendorTwo.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null)
                    {
                        Toast.makeText(RegistrationAsVendorTwo.this, "User here", Toast.LENGTH_SHORT).show();
                        Vendor u=new Vendor(Double.toString(myLocation.latitude),Double.toString(myLocation.longitude),firstname,lastname,mobilenumber,email,shopname,homeaddress,shopaddress);
                        HashMap<String, Object> newUserCreds = u.AddDataToUserDataBase();
                        FirebaseDatabase.getInstance().getReference().child("userinfo").child("vendors").child(user.getUid()).setValue(newUserCreds);
                        new AlertDialog.Builder(RegistrationAsVendorTwo.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Success")
                                .setMessage("User Registered Successfully!")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        Intent i = new Intent(getApplicationContext(),VendorLanding.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    }
                                })
                                .show();
                    }
                }
                else
                {
                    new AlertDialog.Builder(RegistrationAsVendorTwo.this)
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