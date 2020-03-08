package com.khalej.karam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.khalej.karam.R;
import com.khalej.karam.model.Apiclient_home;
import com.khalej.karam.model.Reset;
import com.khalej.karam.model.apiinterface_home;
import com.khalej.karam.model.contact_userinfo;

public class ForgetPassword extends AppCompatActivity {
TextInputEditText textInputEditTextphone;
AppCompatButton appCompatButtonRegisterservcies;
    ProgressDialog progressDialog;
    private apiinterface_home apiinterface;
   Reset reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        textInputEditTextphone=findViewById(R.id.textInputEditTextphone);
        appCompatButtonRegisterservcies=findViewById(R.id.appCompatButtonRegisterservcies);
        appCompatButtonRegisterservcies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchInfo();
            }
        });

    }

    public void fetchInfo(){
        progressDialog = ProgressDialog.show(ForgetPassword.this,"جاري الإرسال","Please wait...",false,false);
        progressDialog.show();

        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<Reset> call= apiinterface.getcontacts_ResetPassword(
                textInputEditTextphone.getText().toString());
        call.enqueue(new Callback<Reset>() {
            @Override
            public void onResponse(Call<Reset> call, Response<Reset> response) {
                reset=response.body();
                progressDialog.dismiss();
                if(reset.getCan()==1){
                    Toast.makeText(ForgetPassword.this,"تم الطلب",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(ForgetPassword.this,"هذه البيانات غير مسجلة",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Reset> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
