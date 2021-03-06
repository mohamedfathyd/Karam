package com.khalej.karam.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khalej.karam.R;
import com.khalej.karam.model.Order;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_morder_previous extends RecyclerView.Adapter<RecyclerAdapter_morder_previous.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<Order> contactslist;


    public RecyclerAdapter_morder_previous(Context context, List<Order> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recervation_car,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");

        holder.Name.setText(contactslist.get(position).getName());
        holder.Name.setTypeface(myTypeface);
        holder.price.setText( "الكمية :" +contactslist.get(position).getAmount());
        holder.price.setTypeface(myTypeface);
        holder.date.setText("التاريخ :"+contactslist.get(position).getDate());
        holder.date.setTypeface(myTypeface);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,details,date,price;


        public MyViewHolder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.txt_fish_title);
            date=itemView.findViewById(R.id.txt_title);
            price=itemView.findViewById(R.id.txt_);
        }
    }}