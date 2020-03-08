package com.khalej.karam.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khalej.karam.R;
import com.khalej.karam.RoundRectCornerImageView;
import com.khalej.karam.model.Apiclient_home;
import com.khalej.karam.model.Order;
import com.khalej.karam.model.apiinterface_home;
import com.khalej.karam.model.contact_SubScribe;
import com.khalej.karam.model.notificationData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter_mySubScribe extends RecyclerView.Adapter<RecyclerAdapter_mySubScribe.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_SubScribe> contactslist;

    private apiinterface_home apiinterface;
    TextView toolbar_title;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    public RecyclerAdapter_mySubScribe(Context context, List<contact_SubScribe> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribelist,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if(sharedpref.getString("language","").trim().equals("ar")){
            holder.Name.setText(contactslist.get(position).getAr_title());

        }else{
            holder.Name.setText(contactslist.get(position).getEn_title());

        }
        holder.description.setText(contactslist.get(position).getDescription());
        holder.details.setText(contactslist.get(position).getWeight());
        Glide.with(context).load("http://jamalah.com/montag/KARAM/"+contactslist.get(position).getMain_image()).error(R.drawable.logo).into(holder.image);

        holder.idd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelOrder( contactslist.get(position).getId());
            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,details,description ,idd;
        RoundRectCornerImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.txt_fish_title);
            description=(TextView)itemView.findViewById(R.id.txt_title);
            details=itemView.findViewById(R.id.txt_);
            image=itemView.findViewById(R.id.image);
            idd=itemView.findViewById(R.id.idd);
        }
    }
    public void CancelOrder(int id){
        progressDialog = ProgressDialog.show(context, "جاري ألغاء الأشتراك", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_RemoveSubScribe(id,sharedpref.getInt("id",0));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("تم ألغاء الأشتراك");
                dlgAlert.setTitle("Karam");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                ((Activity)context).finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("تم ألغاء الأشتراك");
                dlgAlert.setTitle("Karam");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
              //  Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}