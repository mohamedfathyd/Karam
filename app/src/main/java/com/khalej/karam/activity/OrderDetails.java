package com.khalej.karam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.khalej.karam.R;
import com.khalej.karam.directionhelpers.FetchURL;
import com.khalej.karam.directionhelpers.TaskLoadedCallback;

public class OrderDetails extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    CircleImageView image;
    TextView name ,details,phone ;
    double latfrom,lngfrom;
    Intent intent;
    private GoogleMap mMap;

    private MarkerOptions place1, place2;

    private Polyline currentPolyline;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
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
        image=findViewById(R.id.image);
        name=findViewById(R.id.username);
        phone=findViewById(R.id.phone);
        details=findViewById(R.id.details);
        intent=getIntent();
        intent=getIntent();
        name.setText(intent.getStringExtra("name"));
        details.setText(intent.getStringExtra("details"));
        phone.setText(intent.getStringExtra("phone"));
        Glide.with(this).load(sharedpref.getString("image","")).error(R.drawable.profile).into(image);

        latfrom=intent.getDoubleExtra("latFrom",0);
        lngfrom=intent.getDoubleExtra("lngFrom",0);
        place1 = new MarkerOptions().position(new LatLng(latfrom, lngfrom)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(sharedpref.getFloat("lat",0), sharedpref.getFloat("lng",0))).title("Location 2");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        new FetchURL(OrderDetails.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
