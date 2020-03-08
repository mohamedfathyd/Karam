package com.khalej.karam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import io.realm.Realm;
import io.realm.RealmResults;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.karam.Adapter.RecyclerAdapter_card;
import com.khalej.karam.Adapter.RecyclerAdapter_first;
import com.khalej.karam.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.karam.R;
import com.khalej.karam.model.Apiclient_home;
import com.khalej.karam.model.CardRealm;
import com.khalej.karam.model.apiinterface_home;
import com.khalej.karam.model.contact_category;
import com.khalej.karam.model.CardRealm;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {
    private RecyclerView recyclerView,recyclerView2;
    Button cunti;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    private RecyclerAdapter_card recyclerAdapter;
    private RecyclerAdapter_first_annonce recyclerAdapter_annonce;
    private List<CardRealm> contactList =new ArrayList<>();
    private List<contact_category> contactList_annonce= new ArrayList<>();
    private apiinterface_home apiinterface;
    TextView toolbar_title;
    ProgressBar progressBar;
    Realm realm;
    Intent intent;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
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
        cunti=findViewById(R.id.cunti);
        cunti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(contactList.size()==0){
    Toast.makeText(CardActivity.this,"قم بتحديد المنتاجات أولا" , Toast.LENGTH_LONG).show();
}
else {
    Intent intent=new Intent(new Intent(CardActivity.this,ConfirmOrder.class));
    intent.putExtra("name",sharedpref.getString("name",""));
    intent.putExtra("phone",sharedpref.getString("phone",""));
    intent.putExtra("address",sharedpref.getString("address",""));
    intent.putExtra("details",sharedpref.getString("details",""));
    intent.putExtra("latfrom",sharedpref.getFloat("lat",0));
    intent.putExtra("lngfrom",sharedpref.getFloat("lng",0));

    startActivity(intent);
}
            }
        });
        id=intent.getIntExtra("id",0);
        recyclerView2=(RecyclerView)findViewById(R.id.recyclerview2);
        layoutManager = new GridLayoutManager(this, 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(staggeredGridLayoutManager);
        recyclerView2.setHasFixedSize(true);

        fetchInfo();
    }

    public void fetchInfo(){
        RealmResults<CardRealm> content_realms = realm.where(CardRealm.class).findAll();
        if (content_realms.isEmpty() || content_realms.equals(null)) {

        } else {    // realm.beginTransaction();
            List<CardRealm> result = content_realms;
            contactList=result;

            recyclerAdapter=new RecyclerAdapter_card(CardActivity.this,result);
            recyclerView2.setAdapter(recyclerAdapter);
        }
    }
}
