package com.example.localvendorsupplychain;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAccountFragment newInstance(String param1, String param2) {
        UserAccountFragment fragment = new UserAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    ListView lv;
    custTomAdvAdaptor customAdaptor;
    ArrayList<custadvcont> arrayList=new ArrayList<>();
    ArrayList<String> vendorIds=new ArrayList<>();
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng myLocation;
    Button refreshField;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv=getView().findViewById(R.id.feedListView);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Feed");
        refreshField=getView().findViewById(R.id.refreshFeedButton);
        refreshField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        getLocation();

    }
    void getLocation()
    {
        LocationServices();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_account, container, false);
    }
    public class custTomAdvAdaptor extends ArrayAdapter<custadvcont> {

        // invoke the suitable constructor of the ArrayAdapter class
        public custTomAdvAdaptor(@NonNull Context context, ArrayList<custadvcont> arrayList) {

            // pass the context and arrayList for the super
            // constructor of the ArrayAdapter class
            super(context, 0, arrayList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            // convertView which is recyclable view
            View currentItemView = convertView;

            // of the recyclable view is null then inflate the custom layout for the same
            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.adv_of_vendor_feed_on_cust_listview, parent, false);
            }
            // get the position of the view from the ArrayAdapter
            try {
                custadvcont adv = getItem(position);

                TextView textView1 = currentItemView.findViewById(R.id.shopNameCustomListCust);
                textView1.setText(adv.getShopnamee());
                TextView textView2 = currentItemView.findViewById(R.id.advDescCustomListCust);
                textView2.setText(adv.getDesc());
                TextView textView3 = currentItemView.findViewById(R.id.addressShopCustomListCust);
                textView3.setText(("Address: "+adv.getAddresss()));
                TextView textView4 = currentItemView.findViewById(R.id.mobileNodescCustomListCust);
                textView4.setText(("Contact: "+adv.getMobileno()));

            }
            catch (Exception e)
            {
                //Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return currentItemView;
        }
    }
    void fillTheList()
    {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("userinfo").child("vendors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Toast.makeText(getActivity(), "Here", Toast.LENGTH_SHORT).show();
                vendorIds.clear();
                arrayList.clear();
                //int i=0;
                int flag=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    Vendor v=dataSnapshot.getValue(Vendor.class);
                    HashMap<String,Object> hashMap=v.AddDataToUserDataBase();
                    LatLng temp=new LatLng(Double.parseDouble(hashMap.get("lat").toString()),Double.parseDouble(hashMap.get("lng").toString()));
                    float dis[]=new float[3];
                    android.location.Location.distanceBetween(temp.latitude,temp.longitude,myLocation.latitude,myLocation.longitude,dis);

                    if(dis[0]<3000)
                    {
                        vendorIds.add(dataSnapshot.getKey());

                        flag=1;
                    }



                    //i++;
                }

                if(flag==0)
                {
                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Sorryy!!!")
                            .setMessage("Cant find any take outs near u!! Maybe try something else??")
                            .setPositiveButton("Ok",null)
                            .show();

                }
                else
                {

                    gettingTheAdv(vendorIds,0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void gettingTheAdv(ArrayList<String> vendorid,int index)
    {

            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("vendoradv").child(vendorid.get(index));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot snapshot1:snapshot.getChildren()) {
                        try {
                            custadvcont c=snapshot1.getValue(custadvcont.class);

                            arrayList.add(c);
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    if(index==(vendorIds.size()-1))
                    {


                        try {
                            customAdaptor=new custTomAdvAdaptor(getActivity(),arrayList);
                            lv.setAdapter(customAdaptor);

                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {

                        gettingTheAdv(vendorid,index+1);
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    void LocationServices() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                myLocation = new LatLng(location.getLatitude(), location.getLongitude());

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