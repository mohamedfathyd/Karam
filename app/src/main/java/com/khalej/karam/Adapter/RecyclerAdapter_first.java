package com.khalej.karam.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.karam.R;
import com.khalej.karam.RoundRectCornerImageView;
import com.khalej.karam.activity.ShowDetails;
import com.khalej.karam.model.contact_home;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_first extends RecyclerView.Adapter<RecyclerAdapter_first.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    List<contact_home> contactslist;


    public RecyclerAdapter_first(Context context, List<contact_home> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if(sharedpref.getString("language","").trim().equals("ar")){
            holder.Name.setText(contactslist.get(position).getAr_title());
            holder.price.setText(contactslist.get(position).getPrice()+"ريال");
        }else{
            holder.Name.setText(contactslist.get(position).getEn_title());
            holder.price.setText(contactslist.get(position).getPrice()+"Rial");
        }
        holder.Name.setTypeface(myTypeface);

        holder.price.setTypeface(myTypeface);
        Glide.with(context).load("http://jamalah.com/montag/KARAM/"+contactslist.get(position).getImage()).error(R.drawable.logo).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShowDetails.class);
                if(sharedpref.getString("language","").trim().equals("ar")){
                intent.putExtra("name",contactslist.get(position).getAr_title());}
                else{
                    intent.putExtra("name",contactslist.get(position).getEn_title());
                }
                intent.putExtra("subDetails",contactslist.get(position).getDetails());
                intent.putExtra("image","http://jamalah.com/montag/KARAM/"+contactslist.get(position).getImage());
                intent.putExtra("id",contactslist.get(position).getId());
                context.startActivity(intent);
            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,price;
        RoundRectCornerImageView image;
      RelativeLayout relativelayout2;
        public MyViewHolder(View itemView) {
            super(itemView);
            relativelayout2=itemView.findViewById(R.id.relativelayout2);
            Name=(TextView)itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.photo);
            price=itemView.findViewById(R.id.price);
        }
    }}