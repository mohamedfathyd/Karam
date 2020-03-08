package com.khalej.karam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.khalej.karam.R;
import com.khalej.karam.model.Apiclient_home;
import com.khalej.karam.model.Citys;
import com.khalej.karam.model.apiinterface_home;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Regester extends AppCompatActivity {
ImageView upload;
    private  static final int IMAGEUser = 100;
    Bitmap bitmapUser;
    String mediaPath;
    TextInputEditText name,phone,email,password,confirmPassword;
    Spinner cities;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String codee =null;
    List<Citys> citys =new ArrayList<>();
    AppCompatButton regeister;
    private apiinterface_home apiinterface;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);
        upload =findViewById(R.id.upload);
        name=findViewById(R.id.textInputEditTextname);
        phone=findViewById(R.id.textInputEditTextphone);
        email=findViewById(R.id.textInputEditTextemail);
        password=findViewById(R.id.textInputEditTextpassword);
        confirmPassword=findViewById(R.id.textInputEditTextConfirmpassword);
        regeister=findViewById(R.id.appCompatButtonRegisterservcies);
        cities=findViewById(R.id.textInputEditTextcity);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);

        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        regeister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fetchInfo();
            }
        });
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(Regester.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Regester.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageUser();
            }
        });

        fetchInfoCity();

    }
    private void selectImageUser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGEUser);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGEUser && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            // Toast.makeText(Registration.this,mediaPath,Toast.LENGTH_LONG).show();
            upload.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();




        }

    }

    public void fetchInfo() {
        String image="";
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("id_image", file.getName(), requestBody);


        progressDialog = ProgressDialog.show(Regester.this, "جاري انشاء الحساب", "Please wait...", false, false);
        progressDialog.show();
        String phoneee=phone.getText().toString();
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestBody namee = RequestBody.create(MediaType.parse("text/plain"), name.getText().toString());
        RequestBody pass=RequestBody.create(MediaType.parse("text/plain"),password.getText().toString());
        RequestBody address=RequestBody.create(MediaType.parse("text/plain"),email.getText().toString());
        RequestBody phonee=RequestBody.create(MediaType.parse("text/plain"),phoneee);
        RequestBody city=RequestBody.create(MediaType.parse("text/plain"),String.valueOf(cities.getSelectedItemId()+1));
        RequestBody phoneCode=RequestBody.create(MediaType.parse("text/plain"),String.valueOf(962));
        RequestBody type= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(1));
        RequestBody is_agree= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(1));
        RequestBody notion_id= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(3456787));


        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_newaccount(fileToUpload,namee,
                pass, address
                ,phonee,type,city,phoneCode,is_agree,notion_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                  //  Toast.makeText(Regester.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                      Toast.makeText(Regester.this,"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    Log.d("tag", jObjError.toString());
                    progressDialog.dismiss();
                    return;
                }
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Regester.this);
                dlgAlert.setMessage("تم أنشاء حساب جديد بنجاح");
                dlgAlert.setTitle("Karam");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                //  login_.fetchInfo(Registration.this,phone,textInputEditTextpassword.getText().toString());
                // finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Regester.this, "من فضلك قم بأختيار صورة اقل فى الحجم ", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfoCity(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<Citys>> call = apiinterface.Citys();
        call.enqueue(new Callback<List<Citys>>() {
            @Override
            public void onResponse(Call<List<Citys>> call, Response<List<Citys>> response) {
                try {


                    citys = response.body();
                    if(citys.size()!=0){
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int i = 0; i < citys.size(); i++) {

                                arrayList.add(citys.get(i).getAr_title());


                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                Regester.this,
                                android.R.layout.simple_spinner_item,
                                arrayList
                        );
                        cities.setAdapter(adapter);}
                }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<List<Citys>> call, Throwable t) {

            }
        });
    }
}
