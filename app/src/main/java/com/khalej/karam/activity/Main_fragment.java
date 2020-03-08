package com.khalej.karam.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;


import com.khalej.karam.Adapter.RecyclerAdapter_first;
import com.khalej.karam.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.karam.R;
import com.khalej.karam.model.Apiclient_home;
import com.khalej.karam.model.apiinterface_home;
import com.khalej.karam.model.contact_category;
import com.khalej.karam.model.contact_home;

import java.util.ArrayList;
import java.util.List;

public class Main_fragment extends Fragment {

    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    private RecyclerAdapter_first recyclerAdapter;
    private RecyclerAdapter_first_annonce recyclerAdapter_annonce;
    private List<contact_home> contactList =new ArrayList<>();
    private List<contact_category> contactList_annonce= new ArrayList<>();
    private apiinterface_home apiinterface;
    int x=0;
    int y=0;
    Switch swtch;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_main_fragment, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        layoutManager = new GridLayoutManager(getContext(), 3);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        2, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView2=(RecyclerView)view.findViewById(R.id.recyclerview2);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);
        fetchInfo();
        fetchInfo_annonce();


        return  view;
    }
        public void fetchInfo(){
            progressBar.setVisibility(View.GONE);
            apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
            Call<List<contact_home>> call = apiinterface.getcontacts_first();
            call.enqueue(new Callback<List<contact_home>>() {
                @Override
                public void onResponse(Call<List<contact_home>> call, Response<List<contact_home>> response) {
                    contactList = response.body();
                    progressBar.setVisibility(View.GONE);
                    recyclerAdapter=new RecyclerAdapter_first(getActivity(),contactList);
                    recyclerView.setAdapter(recyclerAdapter);

                }

                @Override
                public void onFailure(Call<List<contact_home>> call, Throwable t) {
                    Toast.makeText(getContext(),t+"",Toast.LENGTH_LONG).show();

                }
            });
        }
    public void fetchInfo_annonce(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_category>> call = apiinterface.getcontacts_annonce();
        call.enqueue(new Callback<List<contact_category>>() {
            @Override
            public void onResponse(Call<List<contact_category>> call, Response<List<contact_category>> response) {
                contactList_annonce = response.body();
                progressBar.setVisibility(View.GONE);
                recyclerAdapter_annonce=new RecyclerAdapter_first_annonce(getActivity(),contactList_annonce,recyclerView2);
                recyclerView2.setAdapter(recyclerAdapter_annonce);

            }

            @Override
            public void onFailure(Call<List<contact_category>> call, Throwable t) {

            }
        });
    }
}
