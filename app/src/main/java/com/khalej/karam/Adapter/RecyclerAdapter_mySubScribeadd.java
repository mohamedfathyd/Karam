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
import com.khalej.karam.model.apiinterface_home;
import com.khalej.karam.model.contact_MakeSubScribe;
import com.khalej.karam.model.contact_SubScribe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter_mySubScribeadd extends RecyclerView.Adapter<RecyclerAdapter_mySubScribeadd.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_MakeSubScribe> contactslist;

    private apiinterface_home apiinterface;
    TextView toolbar_title;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    public RecyclerAdapter_mySubScribeadd(Context context, List<contact_MakeSubScribe> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.makesubscribelist,parent,false);

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
            holder.Name.setText(contactslist.get(position).getAr_title());

        }
        holder.description.setText(contactslist.get(position).getDescription());
        holder.details.setText(contactslist.get(position).getWeight());
        Glide.with(context).load("http://jamalah.com/montag/KARAM/"+contactslist.get(position).getMain_image()).error(R.drawable.logo).into(holder.image);
if(contactslist.get(position).getType()==2){
    holder.idd.setText(R.string.SubScribe);
}
else{
    holder.idd.setText(R.string.UnSubScribe);
}
        holder.idd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelOrder( contactslist.get(position).getId());
                holder.idd.setText(R.string.UnSubScribe);
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
        progressDialog = ProgressDialog.show(context, "جاري الأشتراك", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_RemoveSubScribe(id,sharedpref.getInt("id",0));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("تم الأشتراك");
                dlgAlert.setTitle("Karam");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}