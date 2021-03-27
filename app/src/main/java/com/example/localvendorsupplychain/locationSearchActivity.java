package com.example.localvendorsupplychain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class locationSearchActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    Button searchWithName, searchWithLocation, seeOnMapButton;
    static LatLng myLocation;
    static ArrayList<LatLng> allTheLocations=new ArrayList<LatLng>();
    ListView listOfVendors;
    static ArrayList<String> vendorNames=new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
        listOfVendors=findViewById(R.id.listOfVendors);
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,vendorNames);
        listOfVendors.setAdapter(arrayAdapter);
        searchWithLocation = findViewById(R.id.searchWithLocationButton);
        searchWithName = findViewById(R.id.searchWithNameButton);
        seeOnMapButton = findViewById(R.id.seeOnMapButton);
        seeOnMapButton.setVisibility(View.INVISIBLE);
        seeOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        searchWithLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFromCurrentLocation();
            }
        });
        LocationServices();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
    void fillTheList()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("userinfo").child("vendors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allTheLocations.clear();
                vendorNames.clear();
                //int i=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    Vendor v=dataSnapshot.getValue(Vendor.class);
                    HashMap<String,Object> hashMap=v.AddDataToUserDataBase();
                    Toast.makeText(locationSearchActivity.this, ""+hashMap.get("lat").toString(), Toast.LENGTH_SHORT).show();
                    LatLng temp=new LatLng(Double.parseDouble(hashMap.get("lat").toString()),Double.parseDouble(hashMap.get("lng").toString()));
                    float dis[]=new float[3];
                    android.location.Location.distanceBetween(temp.latitude,temp.longitude,myLocation.latitude,myLocation.longitude,dis);
                    Toast.makeText(locationSearchActivity.this, ""+dis[0], Toast.LENGTH_SHORT).show();
                    if(dis[0]<3000)
                    {
                        allTheLocations.add(temp);
                        vendorNames.add(hashMap.get("shopname").toString());

                    }

                    //i++;
                }
                arrayAdapter.notifyDataSetChanged();
                seeOnMapButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*Intent intent=new Intent(this,locationActivity.class);
        intent.putExtra("Lat",myLocation.latitude);
        intent.putExtra("Long",myLocation.longitude);
        startActivity(intent);*/
    }
    void openMap()
    {
        startActivity(new Intent(this,locationActivity.class));
    }
    void LocationServices() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                Toast.makeText(locationSearchActivity.this, ""+myLocation, Toast.LENGTH_SHORT).show();
                locationManager.removeUpdates(locationListener);
                fillTheList();
                //seeOnMapButton.setVisibility(View.VISIBLE);
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

}