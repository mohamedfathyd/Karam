package com.khalej.karam.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.karam.R;
import com.khalej.karam.activity.SubCategory;
import com.khalej.karam.model.contact_category;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_first_annonce extends RecyclerView.Adapter<RecyclerAdapter_first_annonce.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_category> contactslist;
    RecyclerView recyclerView;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;

    public RecyclerAdapter_first_annonce(Context context, List<contact_category> contactslist, RecyclerView recyclerView){
        this.contactslist=contactslist;
        this.context=context;
        this.recyclerView=recyclerView;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {

            myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");
            sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
            edt = sharedpref.edit();
            if(sharedpref.getString("language","").trim().equals("ar")){
                holder.name.setText(contactslist.get(position).getAr_title());
            }else{
                holder.name.setText(contactslist.get(position).getEn_title());}
            holder.name.setTypeface(myTypeface);
            Glide.with(context).load("http://jamalah.com/montag/KARAM/"+contactslist.get(position).getImage()).error(R.drawable.logo).into(holder.image);
        }
        catch (Exception e){}
        holder.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SubCategory.class);
                if(sharedpref.getString("language","").trim().equals("ar")){
                intent.putExtra("name",contactslist.get(position).getAr_title());
                }
                else
                    {
                        intent.putExtra("name",contactslist.get(position).getEn_title());
                    }
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

        ImageView image;
        TextView name;
        RelativeLayout a;
        public MyViewHolder(View itemView) {
            super(itemView);
            a=itemView.findViewById(R.id.a);
            image=(ImageView)itemView.findViewById(R.id.photo);
            name=itemView.findViewById(R.id.name);

        }
    }}