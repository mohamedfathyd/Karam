package com.khalej.karam.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.karam.R;
import com.khalej.karam.RoundRectCornerImageView;
import com.khalej.karam.activity.CardActivity;
import com.khalej.karam.activity.ShowDetails;
import com.khalej.karam.model.CardRealm;
import com.khalej.karam.model.contact_home;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;


public class RecyclerAdapter_card extends RecyclerView.Adapter<RecyclerAdapter_card.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    List<CardRealm> contactslist;
  Realm realm;
 int numberofsets=0;
    public RecyclerAdapter_card(Context context, List<CardRealm> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_circle_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        realm=Realm.getDefaultInstance();
        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();

            holder.Name.setText(contactslist.get(position).getName());

        holder.num.setText(contactslist.get(position).getNum()+"");
        holder.Name.setTypeface(myTypeface);

        Glide.with(context).load(contactslist.get(position).getImage()).error(R.drawable.logo).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShowDetails.class);

            }

        });
        holder.cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                deletedata(contactslist.get(position).getName());}
                catch (Exception e){
                    ((Activity)context).finish();
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numberofsets==0){
                    numberofsets=contactslist.get(position).getNum()-1;
                    if(numberofsets==0){
                        numberofsets=1;
                    }
                    holder.num.setText(numberofsets+"");
                }
                else{
                    numberofsets=numberofsets-1;
                    if(numberofsets==0){
                        numberofsets=1;
                    }
                    holder.num.setText(numberofsets+"");
                }

                addData(contactslist.get(position).getName(),numberofsets);

            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numberofsets==0){
                    numberofsets=contactslist.get(position).getNum()+1;
                    holder.num.setText(numberofsets+"");
                }
                else{
                    numberofsets=numberofsets+1;
                    holder.num.setText(numberofsets+"");
                }
                addData(contactslist.get(position).getName(),numberofsets);
            }
        });
    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,price,num;
        RoundRectCornerImageView image;
      RelativeLayout relativelayout2;
      ImageView cancel_button,plus,minus;
        public MyViewHolder(View itemView) {
            super(itemView);
            relativelayout2=itemView.findViewById(R.id.relativelayout2);
            Name=(TextView)itemView.findViewById(R.id.name);
            num=itemView.findViewById(R.id.numOfSets);
            image=itemView.findViewById(R.id.photo);
            price=itemView.findViewById(R.id.price);
            cancel_button=itemView.findViewById(R.id.cancel_button);
            plus=itemView.findViewById(R.id.plus);
            minus=itemView.findViewById(R.id.minus);
        }
    }
    public void deletedata(String name) {
        realm.beginTransaction();
        CardRealm content_realms = realm.where(CardRealm.class).equalTo("name", name).findFirst();
        content_realms.deleteFromRealm();
        realm.commitTransaction();
        fetchInfo();
    }
    public void addData(String name,int numberofsets){
        realm.beginTransaction();
        CardRealm content_realms = realm.where(CardRealm.class).equalTo("name", name).findFirst();
        content_realms.setNum(numberofsets);
        realm.commitTransaction();

    }
    public void fetchInfo(){
        RealmResults<CardRealm> content_realms = realm.where(CardRealm.class).findAll();
        if (content_realms.isEmpty() || content_realms.equals(null)) {

        } else {    // realm.beginTransaction();
            List<CardRealm> result = content_realms;
            contactslist=result;
            notifyDataSetChanged();
        }
    }
    public void deleteall() {
        realm.beginTransaction();
        RealmResults<CardRealm> content_realms = realm.where(CardRealm.class).findAll();
        content_realms.deleteAllFromRealm();
        realm.commitTransaction();
        fetchInfo();
    }
}