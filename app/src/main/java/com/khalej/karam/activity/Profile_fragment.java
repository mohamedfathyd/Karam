package com.khalej.karam.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.khalej.karam.R;
import com.khalej.karam.model.Order;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile_fragment extends Fragment {
    CircleImageView image;
    TextView name ,address,phone ;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    LinearLayout orders,notification,myinfo;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        image=view.findViewById(R.id.image);
        name=view.findViewById(R.id.username);
        address=view.findViewById(R.id.address);
        phone=view.findViewById(R.id.phone);
        Glide.with(getContext()).load(sharedpref.getString("image","")).error(R.drawable.profile).into(image);
        name.setText(sharedpref.getString("name",""));
        address.setText(sharedpref.getString("address",""));
        phone.setText(sharedpref.getString("phone",""));
        orders= view.findViewById(R.id.orders);
        notification=view.findViewById(R.id.notification);
        myinfo=view.findViewById(R.id.myinfo);

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MyOrders.class));
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Notification.class));
            }
        });

        return view;
    }

}
