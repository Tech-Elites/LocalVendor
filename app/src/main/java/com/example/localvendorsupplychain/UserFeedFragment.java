package com.example.localvendorsupplychain;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFeedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFeedFragment newInstance(String param1, String param2) {
        UserFeedFragment fragment = new UserFeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    LocationManager locationManager;
    LocationListener locationListener;
    Button searchWithName, searchWithLocation, seeOnMapButton;
    EditText shopNameEditText;
    static LatLng myLocation;
    static ArrayList<LatLng> allTheLocations=new ArrayList<LatLng>();
    ListView listOfVendors;
    static ArrayList<String> vendorNames=new ArrayList<String>();
    ArrayList<String> mobileNoList=new ArrayList<>();
    ArrayList<String> vendorIds=new ArrayList<String>();
    boolean whether_name;
    ArrayAdapter<String> arrayAdapter;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    void searchFromCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Toast.makeText(getActivity(), "Here", Toast.LENGTH_SHORT).show();
        whether_name=false;
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
    void searchFromName()
    {
        if(!TextUtils.isEmpty(shopNameEditText.getText().toString()))
        {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            whether_name=true;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        }
        else
        {
            Toast.makeText(getActivity(), "Enter something", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listOfVendors=getView().findViewById(R.id.listOfVendors);
        arrayAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,vendorNames);
        listOfVendors.setAdapter(arrayAdapter);
        shopNameEditText=getView().findViewById(R.id.searchWithName);
        allTheLocations.clear();
        arrayAdapter.notifyDataSetChanged();
        searchWithLocation = getView().findViewById(R.id.searchWithLocationButton);
        searchWithName = getView().findViewById(R.id.searchWithNameButton);
        seeOnMapButton = getView().findViewById(R.id.seeOnMapButton);
        seeOnMapButton.setVisibility(View.INVISIBLE);
        searchWithName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFromName();
            }
        });
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

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        listOfVendors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vendingPageRedirect(position);
            }
        });
    }
    void vendingPageRedirect(int i)
    {
        Intent intent=new Intent(getActivity(),CustToVendorLanding.class);
        intent.putExtra("vendorid",vendorIds.get(i));
        intent.putExtra("lat",allTheLocations.get(i).latitude);
        intent.putExtra("lng",allTheLocations.get(i).longitude);
        intent.putExtra("mobileno",mobileNoList.get(i));
        intent.putExtra("name",vendorNames.get(i));
        startActivity(intent);
    }

    void fillTheListName()
    {

    }

    void fillTheList()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("userinfo").child("vendors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allTheLocations.clear();
                vendorNames.clear();
                vendorIds.clear();
                //int i=0;
                int flag=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    Vendor v=dataSnapshot.getValue(Vendor.class);
                    HashMap<String,Object> hashMap=v.AddDataToUserDataBase();
                    LatLng temp=new LatLng(Double.parseDouble(hashMap.get("lat").toString()),Double.parseDouble(hashMap.get("lng").toString()));
                    float dis[]=new float[3];
                    android.location.Location.distanceBetween(temp.latitude,temp.longitude,myLocation.latitude,myLocation.longitude,dis);
                    if(whether_name)
                    {
                        String tsn=shopNameEditText.getText().toString();
                        String[] name=hashMap.get("shopname").toString().split(" ");
                        for(String s:name)
                        {
                            if(s.compareToIgnoreCase(tsn)==0)
                            {
                                allTheLocations.add(temp);
                                vendorNames.add(hashMap.get("shopname").toString());
                                vendorIds.add(dataSnapshot.getKey());
                                mobileNoList.add(hashMap.get("mobileno").toString());
                                flag=1;
                                break;
                            }
                        }
                    }
                    else
                    {
                        if(dis[0]<3000)
                        {
                            allTheLocations.add(temp);
                            vendorNames.add(hashMap.get("shopname").toString());
                            vendorIds.add(dataSnapshot.getKey());
                            mobileNoList.add(hashMap.get("mobileno").toString());
                            flag=1;
                        }

                    }

                    //i++;
                }

                if(flag==0)
                {
                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Sorryy!!!")
                            .setMessage("Cant find any restaurants!! Maybe try something else??")
                            .setPositiveButton("Ok",null)
                            .show();

                }
                arrayAdapter.notifyDataSetChanged();
                seeOnMapButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void openMap()
    {
        startActivity(new Intent(getActivity(),locationActivity.class));
    }
    void LocationServices() {


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                Toast.makeText(getActivity(), "Here", Toast.LENGTH_SHORT).show();
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