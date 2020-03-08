package com.khalej.karam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khalej.karam.R;

public class Edit_Profile extends AppCompatActivity {
    TextView name,address,phone,id;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Typeface myTypeface;
    CircleImageView image;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
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
        initializer();
        myTypeface = Typeface.createFromAsset(getAssets(), "Droid.ttf");
        sharedpref= getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        image=findViewById(R.id.image);
        Glide.with(this).load(sharedpref.getString("image","").trim()).error(R.drawable.profile).into(image);

        name.setTypeface(myTypeface);
        address.setTypeface(myTypeface);
        phone.setTypeface(myTypeface);
        name.setText(sharedpref.getString("name","").trim());
        address.setText(sharedpref.getString("address","").trim());
        phone.setText(sharedpref.getString("phone","").trim());


    }
    private void initializer(){
        name=(TextView)findViewById(R.id.username);
        address=(TextView)findViewById(R.id.address);
        phone=findViewById(R.id.phone);

    }
}
