package com.khalej.karam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.realm.Realm;
import io.realm.RealmResults;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khalej.karam.R;
import com.khalej.karam.model.CardRealm;
import com.khalej.karam.model.apiinterface_home;

import java.util.List;

public class ShowDetails extends AppCompatActivity {
    Intent intent;
    String addressTo,addressFrom,details,name,subDetails;
    double latFrom,lngFrom,latTo,lngTo;
    int id ,numberofSets=1;
    TextView nameofcategory,numOfSets,locationFromAddress,locationToAddress,subName;
    EditText detailsEdit;
    Realm realm;
    Button cunti;
    ProgressDialog progressDialog;
    ImageView imageView;
    LinearLayout locationFrom,locationTo;
    private apiinterface_home apiinterface;
    ImageView plus,minus,relativelayout1;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        initializer();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();

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
        intent=getIntent();
        Glide.with(this).load(intent.getStringExtra("image")).error(R.drawable.logo).into(imageView);
        nameofcategory.setText(intent.getStringExtra("name"));
        details=intent.getStringExtra("subDetails");
        id=intent.getIntExtra("id",0);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberofSets++;
                numOfSets.setText(numberofSets+"");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberofSets==1){
                    numberofSets=1;
                }
                else {
                    numberofSets--;
                    numOfSets.setText(numberofSets+"");
                }
            }
        });
        cunti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdata();
            }
        });
subName.setText(details);

    }
    private void initializer(){
        nameofcategory=findViewById(R.id.name);
        numOfSets=findViewById(R.id.numOfSets);
        subName=findViewById(R.id.subName);
        cunti=findViewById(R.id.cunti);
        plus=findViewById(R.id.plus);
        imageView=findViewById(R.id.relativelayout1);
        minus=findViewById(R.id.minus);
        relativelayout1=findViewById(R.id.relativelayout1);
    }

    public void WriteTodatabase(){

                realm.beginTransaction();
        CardRealm card = realm.createObject(CardRealm.class);

        card.setImage(intent.getStringExtra("image"));
        card.setAllid(7793);
        card.setId(intent.getIntExtra("id",0));
        card.setName(intent.getStringExtra("name"));
        card.setNum(numberofSets);
        realm.commitTransaction();
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ShowDetails.this);
        dlgAlert.setMessage("تم اضافة طلبك الى السلة بنجاح");
        dlgAlert.setTitle("Karam");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

    }

    public void showdata(){

        RealmResults<CardRealm> content_realms = realm.where(CardRealm.class).findAll();
        if (content_realms.isEmpty() || content_realms.equals(null)) {
            WriteTodatabase();
        } else {    // realm.beginTransaction();
            List<CardRealm> result = content_realms;
            if(result.size()!=0){
            for(int i =0; i<result.size();i++){
                if(result.get(i).getName().equals(intent.getStringExtra("name"))){
                    Toast.makeText(ShowDetails.this,"هذا المنتج تم أضافته الى السلة من قبل " , Toast.LENGTH_LONG).show();
                    return;
                }
            }
                WriteTodatabase();
            }
            else{
                WriteTodatabase();
            }
        }
    }
}
