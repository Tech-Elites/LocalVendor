package com.example.localvendorsupplychain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapterMenu extends ArrayAdapter<MenuClass>{

    // invoke the suitable constructor of the ArrayAdapter class
    public CustomAdapterMenu(@NonNull Context context, ArrayList<MenuClass> arrayList) {

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
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view_menu, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        MenuClass menuClass = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.MenuItemName);
        textView1.setText(menuClass.getName());
        TextView textView2 = currentItemView.findViewById(R.id.MenuItemDesc);
        textView2.setText(menuClass.getDesc());
        TextView textView3 = currentItemView.findViewById(R.id.MenuItemPrice);
        textView3.setText(String.valueOf(menuClass.getPrice()));
        TextView textView4 = currentItemView.findViewById(R.id.MenuItemVeg);
        if(menuClass.getVeg().compareToIgnoreCase("yes")==0)
        {
            textView4.setText("Veg");
        }
        else
        {
            textView4.setText("Non veg");
        }

        return currentItemView;
    }
}