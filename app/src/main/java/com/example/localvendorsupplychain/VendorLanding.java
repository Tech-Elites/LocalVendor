package com.example.localvendorsupplychain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class VendorLanding extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String selectedStartTime, selectedEndTime;
    FirebaseUser user;
    ListView listView;
    ArrayList<MenuClass>  arrayList = new ArrayList<>();
    CustomAdapterMenu customAdapterMenu;
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
        setContentView(R.layout.activity_vendor_landing);
        listView = findViewById(R.id.listViewMenu);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TextView name = findViewById(R.id.VendorLandName);
        TextView shopname = findViewById(R.id.VendorLandShopName);
        TextView shopadd = findViewById(R.id.VendorLandShopAdd);
        TextView contact = findViewById(R.id.VendorLandContact);

        mDatabase.child("userinfo").child("vendors").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                }
                else {
                    Vendor v = task.getResult().getValue(Vendor.class);

                    try {
                            name.setText(v.firstname+" "+v.lastname);
                        shopname.setText("Shop name:    "+v.shopname);
                         shopadd.setText("Shop Address: "+v.shopaddress);
                         contact.setText("Contact:      "+v.mobileno);
                    }
                    catch (Exception e){
                        System.out.println("SEAW"+e);
                    }

                }
            }
        });

        makeListView();
    }

    public void makeListView(){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("vendormenu").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    MenuClass m=snapshot1.getValue(MenuClass.class);

                    arrayList.add(m);
                }
                customAdapterMenu=new CustomAdapterMenu(getApplicationContext(),arrayList);
                listView.setAdapter(customAdapterMenu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addMenu(View view){
        Intent i = new Intent(this, addNewMenu.class);
        startActivity(i);
    }

    public void updateLocationVendor(View view) {

    }

    public void updateWorkingHours(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if(selectedHour<10 && selectedMinute<10) {
                    selectedEndTime = ("0" + selectedHour + ":0" + selectedMinute);
                }else if(selectedHour<10){
                    selectedEndTime=( "0"+selectedHour + ":" + selectedMinute);
                }else if(selectedMinute<10){
                    selectedEndTime=( selectedHour + ":0" + selectedMinute);
                }else {
                    selectedEndTime = (selectedHour + ":" + selectedMinute);
                }
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                updateWorkingHours(selectedStartTime, selectedEndTime);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(VendorLanding.this);
                builder.setMessage("Your new working hours are:\nStart at "+selectedStartTime+" and end at "+selectedEndTime).setPositiveButton(
                        "Update", dialogClickListener).setNegativeButton("Cancel", dialogClickListener).show();
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select End Time");
        mTimePicker.show();

        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if(selectedHour<10 && selectedMinute<10) {
                    selectedStartTime = ("0" + selectedHour + ":0" + selectedMinute);
                }else if(selectedHour<10){
                    selectedStartTime=( "0"+selectedHour + ":" + selectedMinute);
                }else if(selectedMinute<10){
                    selectedStartTime=( selectedHour + ":0" + selectedMinute);
                }else {
                    selectedStartTime = (selectedHour + ":" + selectedMinute);
                }


            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Start Time");
        mTimePicker.show();

    }

    public void updateWorkingHours(String selectedStartTime, String selectedEndTime){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText((VendorLanding.this), "Start: "+selectedStartTime+"\nEnd: "+selectedEndTime, Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference().child("userinfo").child("vendors").child(user.getUid()).child("StartTime").setValue(selectedStartTime);
        FirebaseDatabase.getInstance().getReference().child("userinfo").child("vendors").child(user.getUid()).child("EndTime").setValue(selectedEndTime);
    }



}