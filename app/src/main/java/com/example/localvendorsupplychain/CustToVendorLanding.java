package com.example.localvendorsupplychain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustToVendorLanding extends AppCompatActivity {

    LatLng shopLatLng;
    String vendorId;
    String address;
    String mobileNo,shopname;
    TextView shopNameTextView,addressTextView,mobileNoTextView;
    CustomAdapterMenu customAdapterMenu;
    ListView listOfMenu;
    ArrayList<MenuClass> arrayList=new ArrayList<MenuClass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_to_vendor_landing);
        shopNameTextView=findViewById(R.id.shopNameCustToVendor);
        listOfMenu=findViewById(R.id.listOfMenuItemss);
        addressTextView=findViewById(R.id.shopAddCustToVendor);
        mobileNoTextView=findViewById(R.id.shopMobileNoCustToVendor);
        Intent intent =getIntent();
        double lat=intent.getDoubleExtra("lat",0);
        double lng=intent.getDoubleExtra("lng",0);
        shopLatLng=new LatLng(lat,lng);
        vendorId=intent.getStringExtra("vendorid");
        mobileNo=intent.getStringExtra("mobileno");
        shopname=intent.getStringExtra("name");
        Geocoder geocoder;
        List<Address> addresses=null;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(shopLatLng.latitude, shopLatLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        address = addresses.get(0).getAddressLine(0);
        //Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
        shopNameTextView.setText(shopname);
        addressTextView.setText(address);
        mobileNoTextView.setText(mobileNo);
        getMenu();
    }
    void getMenu()
    {
        try {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("vendormenu").child(vendorId);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        MenuClass m=snapshot1.getValue(MenuClass.class);
                        //Toast.makeText(CustToVendorLanding.this, ""+m.getDesc(), Toast.LENGTH_SHORT).show();
                        arrayList.add(m);
                    }
                    customAdapterMenu=new CustomAdapterMenu(getApplicationContext(),arrayList);
                    listOfMenu.setAdapter(customAdapterMenu);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
            catch (Exception e){
            System.out.println(e);
        }
    }

}