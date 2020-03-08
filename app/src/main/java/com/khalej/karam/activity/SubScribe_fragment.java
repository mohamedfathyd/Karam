package com.khalej.karam.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.khalej.karam.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;


public class SubScribe_fragment extends Fragment {
    CircleImageView image;
    TextView name ,address,phone ;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_subscribe, container, false);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();

        return view;
    }

}
