package com.khalej.karam.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khalej.karam.R;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity {
    private ActionBar toolbar;
    ImageView card;
    TextView toolbar_title;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String lang;
    ImageView chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        lang=sharedpref.getString("language","").trim();
        if(lang.equals(null)){
            edt.putString("language","ar");
            lang="en";
            edt.apply();
        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
   card=findViewById(R.id.card);
        card.setVisibility(View.VISIBLE);
   card.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           startActivity(new Intent(MainActivity.this,CardActivity.class));
       }
   });
        this.setTitle("");


        loadFragment(new Main_fragment());
        }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_home:
                    fragment = new Main_fragment();
                    loadFragment(fragment);
                    card.setVisibility(View.VISIBLE);
                    return true;
                case R.id.action_offer:
                    fragment = new SubScribe_fragment();
                    loadFragment(fragment);
                    card.setVisibility(View.GONE);
                    return true;
                case R.id.action_acount:
                    fragment = new Profile_fragment();
                    loadFragment(fragment);
                    card.setVisibility(View.GONE);
                    return true;
                case R.id.action_search:
                     fragment = new Offers_Fragment();
                    loadFragment(fragment);
                    card.setVisibility(View.GONE);
                    return true;
                case R.id.action_more:
                    fragment = new More_fragment();
                    loadFragment(fragment);
                    card.setVisibility(View.GONE);
                    return true;
            }

            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(MainActivity.this, Login.class);
        startActivity(setIntent);
        finish();
    }
}
