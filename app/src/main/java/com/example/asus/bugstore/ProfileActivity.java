package com.example.asus.bugstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.bugstore.Singleton.MySingleton;
import com.example.asus.bugstore.Stored_access_user_data.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    TextView profile_user_name,profile_full_name,profile_age,profile_gender,profile_email,profile_birth,profile_password;
    Button profile_to_update_btn;
    TextView data,profile,forum,store;
    String return_profile_url = "http://nqhuyvn95.000webhostapp.com/return_profile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Match components
        profile_user_name = (TextView) findViewById(R.id.profile_user_name);
        profile_full_name = (TextView) findViewById(R.id.profile_full_name);
        profile_age = (TextView) findViewById(R.id.profile_age);
        profile_gender = (TextView) findViewById(R.id.profile_gender);
        profile_birth = (TextView) findViewById(R.id.profile_birth);
        profile_email = (TextView) findViewById(R.id.profile_email);
        profile_password = (TextView) findViewById(R.id.profile_password);
        profile_to_update_btn = (Button) findViewById(R.id.profile_to_update_btn);
        data = (TextView)findViewById(R.id.data);
        profile = (TextView)findViewById(R.id.profile);
        forum = (TextView) findViewById(R.id.forum);
        store = (TextView) findViewById(R.id.store);
        //
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,StoreActivity.class);
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,DataActivity.class);
                startActivity(intent);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,ForumActivity.class);
                startActivity(intent);
            }
        });
        profile_to_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                intent.putExtra("user_name",profile_user_name.getText().toString());
                intent.putExtra("full_name",profile_full_name.getText().toString());
                intent.putExtra("age",profile_age.getText().toString());
                intent.putExtra("birth",profile_birth.getText().toString());
                intent.putExtra("email",profile_email.getText().toString());
                intent.putExtra("gender",profile_gender.getText().toString());
                startActivity(intent);
            }
        });
        //
        StringRequest stringRequest = new StringRequest(Request.Method.POST, return_profile_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("profile response is",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    JSONObject profile = jsonArray.getJSONObject(0);
                    //
                    profile_user_name.setText(profile.getString("user_name"));
                    profile_full_name.setText(profile.getString("full_name"));
                    profile_age.setText(profile.getString("age"));
                    profile_birth.setText(profile.getString("birth"));
                    profile_email.setText(profile.getString("email"));
                    profile_password.setText(profile.getString("password"));
                    profile_gender.setText(profile.getString("gender"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                SharedPrefManager sharedPrefManager = new SharedPrefManager(ProfileActivity.this);
                params.put("user_name",sharedPrefManager.getUserName());
                return params;
            }
        };
        MySingleton.getInstance(ProfileActivity.this).addToRequestQueue(stringRequest);

    }
}
