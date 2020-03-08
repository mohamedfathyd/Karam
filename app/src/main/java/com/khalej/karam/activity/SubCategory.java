package com.khalej.karam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.khalej.karam.Adapter.RecyclerAdapter_first;
import com.khalej.karam.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.karam.R;
import com.khalej.karam.model.Apiclient_home;
import com.khalej.karam.model.apiinterface_home;
import com.khalej.karam.model.contact_category;
import com.khalej.karam.model.contact_home;

import java.util.ArrayList;
import java.util.List;

public class SubCategory extends AppCompatActivity {
    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    private RecyclerAdapter_first recyclerAdapter;
    private RecyclerAdapter_first_annonce recyclerAdapter_annonce;
    private List<contact_home> contactList =new ArrayList<>();
    private List<contact_category> contactList_annonce= new ArrayList<>();
    private apiinterface_home apiinterface;
    TextView toolbar_title;
    ProgressBar progressBar;
    Intent intent;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        this.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        intent=getIntent();
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText(intent.getStringExtra("name"));
        id=intent.getIntExtra("id",0);
        recyclerView2=(RecyclerView)findViewById(R.id.recyclerview2);
        layoutManager = new GridLayoutManager(this, 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        2, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(staggeredGridLayoutManager);
        recyclerView2.setHasFixedSize(true);    
        progressBar=(ProgressBar)findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        fetchInfo();
    }

    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_home>> call = apiinterface.getSubCategory(id);
        call.enqueue(new Callback<List<contact_home>>() {
            @Override
            public void onResponse(Call<List<contact_home>> call, Response<List<contact_home>> response) {
                contactList = response.body();
                try {


                    if (contactList.size() != 0) {
                        progressBar.setVisibility(View.GONE);
                        recyclerAdapter = new RecyclerAdapter_first(SubCategory.this, contactList);
                        recyclerView2.setAdapter(recyclerAdapter);
                    }

                }
                catch (Exception e){
                    progressBar.setVisibility(View.GONE);
                }
           }

            @Override
            public void onFailure(Call<List<contact_home>> call, Throwable t) {

            }
        });
    }
}
