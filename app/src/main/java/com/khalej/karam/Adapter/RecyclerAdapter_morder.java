package com.khalej.karam.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.karam.R;
import com.khalej.karam.model.Apiclient_home;
import com.khalej.karam.model.Order;
import com.khalej.karam.model.apiinterface_home;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter_morder extends RecyclerView.Adapter<RecyclerAdapter_morder.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<Order> contactslist;
    private apiinterface_home apiinterface;
    TextView toolbar_title;
    ProgressDialog progressDialog;
  int user_id;
    public RecyclerAdapter_morder(Context context, List<Order> contactslist, int user_id){
        this.contactslist=contactslist;
        this.context=context;
        this.user_id=  user_id;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recervation_car_cancel,parent,false);

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

        holder.cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            CancelOrder(contactslist.get(position).getId());
            }
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,details,date,price;
ImageView cancel_button;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.txt_fish_title);
            date=itemView.findViewById(R.id.txt_title);
            price=itemView.findViewById(R.id.txt_);
            cancel_button=itemView.findViewById(R.id.cancel_button);
        }
    }
    public void CancelOrder(int id){
        progressDialog = ProgressDialog.show(context, "جاري مسح الطلب", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_CancelOrder(id,user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("تم حذف الطلب بنجاح ");
                dlgAlert.setTitle("Karam");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                ((Activity)context).finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}