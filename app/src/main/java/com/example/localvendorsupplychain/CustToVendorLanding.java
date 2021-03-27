package com.example.localvendorsupplychain;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class CustToVendorLanding extends AppCompatActivity {

    LatLng shopAddress;
    String vendorId;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_to_vendor_landing);

        Geocoder geocoder;
        List<Address> addresses=null;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(shopAddress.latitude, shopAddress.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        address = addresses.get(0).getAddressLine(0);

    }

}