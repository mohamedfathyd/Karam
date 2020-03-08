package com.khalej.karam.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.khalej.karam.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Offers_Fragment extends Fragment {

    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_offers_, container, false);

        return view;
    }

}
