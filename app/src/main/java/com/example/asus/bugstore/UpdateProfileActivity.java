package com.example.asus.bugstore;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.bugstore.Singleton.MySingleton;
import com.example.asus.bugstore.Stored_access_user_data.SharedPrefManager;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {
    EditText update_profile_user_name,update_profile_full_name,update_profile_age,update_profile_gender,update_profile_email,update_profile_password,update_profile_birth;
    Button save_update_btn;
    String update_profile_url = "http://nqhuyvn95.000webhostapp.com/update_user_profile.php";
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    TextView data,profile,forum,store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        //Match components
        update_profile_user_name = (EditText) findViewById(R.id.update_profile_user_name);
        update_profile_full_name = (EditText) findViewById(R.id.update_profile_full_name);
        update_profile_age = (EditText) findViewById(R.id.update_profile_age);
        update_profile_birth = (EditText) findViewById(R.id.update_profile_birth);
        update_profile_email = (EditText) findViewById(R.id.update_profile_email);
        update_profile_password = (EditText) findViewById(R.id.update_profile_password);
        update_profile_gender = (EditText) findViewById(R.id.update_profile_gender);
        save_update_btn = (Button) findViewById(R.id.save_update_profile_btn);
        data = (TextView)findViewById(R.id.data);
        profile = (TextView)findViewById(R.id.profile);
        forum = (TextView) findViewById(R.id.forum);
        store = (TextView) findViewById(R.id.store);
        //
        update_profile_user_name.setText(getIntent().getStringExtra("user_name"));
        update_profile_full_name.setText(getIntent().getStringExtra("full_name"));
        update_profile_age.setText(getIntent().getStringExtra("age"));
        update_profile_birth.setText(getIntent().getStringExtra("birth"));
        update_profile_email.setText(getIntent().getStringExtra("email"));
        update_profile_gender.setText(getIntent().getStringExtra("gender"));
        //
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileActivity.this,DataActivity.class);
                startActivity(intent);
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileActivity.this,StoreActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileActivity.this,ForumActivity.class);
                startActivity(intent);
            }
        });
        //
        progressDialog = new ProgressDialog(UpdateProfileActivity.this);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("UPDATING");
        builder = new AlertDialog.Builder(UpdateProfileActivity.this);
        //
       save_update_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressDialog.show();
               StringRequest stringRequest = new StringRequest(Request.Method.POST, update_profile_url, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       Log.d("update response",response);
                       SharedPrefManager sharedPrefManager1 = new SharedPrefManager(UpdateProfileActivity.this);
                       sharedPrefManager1.createSessionName(update_profile_user_name.getText().toString());
                       builder.setMessage(response);
                       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                               Intent intent = new Intent(UpdateProfileActivity.this,ProfileActivity.class);
                               startActivity(intent);
                           }
                       });
                       AlertDialog alertDialog = builder.create();
                       alertDialog.show();


                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               }){
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {
                       Map<String,String> params = new HashMap<>();
                       SharedPrefManager sharedPrefManager = new SharedPrefManager(UpdateProfileActivity.this);
                       params.put("old_user_name",sharedPrefManager.getUserName());
                       params.put("user_name",update_profile_user_name.getText().toString());
                       params.put("full_name",update_profile_full_name.getText().toString());
                       params.put("age",update_profile_age.getText().toString());
                       params.put("birth",update_profile_birth.getText().toString());
                       params.put("gender",update_profile_gender.getText().toString());
                       params.put("email",update_profile_email.getText().toString());
                       params.put("password",update_profile_password.getText().toString());
                       return params;
                   }
               };
               MySingleton.getInstance(UpdateProfileActivity.this).addToRequestQueue(stringRequest);
           }
       });
    }
}
