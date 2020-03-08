package com.khalej.karam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.realm.Realm;
import io.realm.RealmResults;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.khalej.karam.Adapter.RecyclerAdapter_card;
import com.khalej.karam.R;
import com.khalej.karam.model.Add_order;
import com.khalej.karam.model.Apiclient_home;
import com.khalej.karam.model.CardRealm;
import com.khalej.karam.model.apiinterface_home;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConfirmOrder extends AppCompatActivity implements OnMapReadyCallback {
    TextView name,phonee,address;
    float latfrom,lngfrom;
    Intent intent;
    private GoogleMap mMap;
    Handler mHandler;
    Realm realm;
    private apiinterface_home apiinterface;
    TextView toolbar_title;
    ProgressDialog progressDialog;
    Add_order add_order=new Add_order();
    private List<CardRealm> contactList =new ArrayList<>();
    Button cunti;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        realm = Realm.getDefaultInstance();
        cunti=findViewById(R.id.cunti);
        cunti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedpref.getString("name","").equals("")||sharedpref.getString("name","")==null){
                    Toast.makeText(ConfirmOrder.this,"قم بتسجيل الدخول أولاً", Toast.LENGTH_LONG).show();
                }
                else{
                    AddOrder();}
            }
        });

        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
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
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phonee=findViewById(R.id.phone);
        intent=getIntent();
        name.setText(intent.getStringExtra("name"));
        address.setText(intent.getStringExtra("address"));
        phonee.setText(intent.getStringExtra("phone"));
        latfrom=intent.getFloatExtra("latfrom",0);
        lngfrom=intent.getFloatExtra("lngfrom",0);
        fetchInfo();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(latfrom, lngfrom);
        CameraUpdate location= CameraUpdateFactory.newLatLngZoom(sydney,18);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Karam").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
                mylocation(latLng);
            }
        });
        mMap.animateCamera(location);
        // For zooming automatically to the location of the marker


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void mylocation(final LatLng latLng){

        new AlertDialog.Builder(ConfirmOrder.this)
                .setTitle("Karam")
                .setMessage("هل هذا هو موقع الذي تريد توصيل طلبك أليه ؟")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Geocoder geocoder;
                        List<Address> addresses = null;
                        geocoder = new Geocoder(ConfirmOrder.this, Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                latfrom= (float) latLng.latitude;
                                lngfrom=(float)latLng.longitude;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }})
                .setNegativeButton(android.R.string.no,  new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mMap.clear();
                    }}).show();

    }

    public void AddOrder(){
        progressDialog = ProgressDialog.show(ConfirmOrder.this, "جاري تسجيل الطلب", "Please wait...", false, false);
        progressDialog.show();


      add_order.setName(sharedpref.getString("name",""));
      add_order.setAddress(sharedpref.getString("address",""));
      add_order.setLat(latfrom);
      add_order.setLog(lngfrom);
      add_order.setId(sharedpref.getInt("id",0));
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_makeOrder(add_order);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ConfirmOrder.this);
            //    Toast.makeText(ConfirmOrder.this,response.code()+"",Toast.LENGTH_LONG).show();
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                      Toast.makeText(ConfirmOrder.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                  //  Toast.makeText(ConfirmOrder.this,"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    Log.d("tag", jObjError.toString());
                    progressDialog.dismiss();
                    return;
                }
                dlgAlert.setMessage("تم تسجيل الطلب بنجاح ");
                dlgAlert.setTitle("Karam");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                deleteall();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ConfirmOrder.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfo(){
        RealmResults<CardRealm> content_realms = realm.where(CardRealm.class).findAll();
        if (content_realms.isEmpty() || content_realms.equals(null)) {

        } else {    // realm.beginTransaction();
            List<CardRealm> result = new ArrayList<>();
            result.addAll(realm.copyFromRealm(content_realms));

            add_order.setOrderDetalis(result);

        }
    }
    public void deleteall() {
        realm.beginTransaction();
        RealmResults<CardRealm> content_realms = realm.where(CardRealm.class).findAll();
        content_realms.deleteAllFromRealm();
        realm.commitTransaction();

    }
}


