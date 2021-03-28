package com.example.localvendorsupplychain;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_account, container, false);
    }
    public class custTomAdv extends ArrayAdapter<custadvcont> {

        // invoke the suitable constructor of the ArrayAdapter class
        public custTomAdv(@NonNull Context context, ArrayList<custadvcont> arrayList) {

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
            custadvcont adv = getItem(position);

            TextView textView1 = currentItemView.findViewById(R.id.shopNameCustomListCust);
            textView1.setText(adv.getShopnamee());
            TextView textView2 = currentItemView.findViewById(R.id.advDescCustomListCust);
            textView2.setText(adv.getDesc());
            TextView textView3 = currentItemView.findViewById(R.id.shopAddCustToVendor);
            textView3.setText(String.valueOf("Address "+adv.getAddresss()));
            TextView textView4 = currentItemView.findViewById(R.id.mobileNodescCustomListCust);
            textView4.setText(String.valueOf("Mobile no- "+adv.getMobileno()));

            return currentItemView;
        }
    }
}