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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.khalej.karam.Adapter.RecyclerAdapter_mySubScribe;
import com.khalej.karam.Adapter.RecyclerAdapter_notification;
import com.khalej.karam.R;
import com.khalej.karam.model.Apiclient_home;
import com.khalej.karam.model.apiinterface_home;
import com.khalej.karam.model.contact_SubScribe;
import com.khalej.karam.model.contact_SubScribe;

import java.util.ArrayList;
import java.util.List;

public class MySubScribes extends AppCompatActivity {
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    EditText message;
    ImageView send;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter_mySubScribe recyclerAdapter;
    private List<contact_SubScribe> contactList = new ArrayList<>();
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sub_scribes);
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

        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager = new GridLayoutManager(this, 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);

        fetchInfo();
    }
    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_SubScribe>> call = apiinterface.getSubScribe(sharedpref.getInt("id",0));
        call.enqueue(new Callback<List<contact_SubScribe>>() {
            @Override
            public void onResponse(Call<List<contact_SubScribe>> call, Response<List<contact_SubScribe>> response) {

                try {


                    contactList = response.body();
                    if (response.code() == 404) {
                        contactList=new ArrayList<>();
                        return;
                    }
                    if(contactList.isEmpty()){
                        contactList=new ArrayList<>();
                    }
                    else {
                        //  Toast.makeText(ChatActivity.this, "22", Toast.LENGTH_LONG).show();
                        recyclerAdapter = new RecyclerAdapter_mySubScribe(MySubScribes.this, contactList);
                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerView.scrollToPosition(contactList.size() - 1);
                    }
                }
                catch (Exception e){
                    //  Toast.makeText(ChatActivity.this,e+"",Toast.LENGTH_LONG).show();
                    contactList=new ArrayList<>();
                }

            }

            @Override
            public void onFailure(Call<List<contact_SubScribe>> call, Throwable t) {
                contactList=new ArrayList<>();
            }
        });
    }
}
