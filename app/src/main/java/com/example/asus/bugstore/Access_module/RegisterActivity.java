package com.example.asus.bugstore.Access_module;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.bugstore.R;
import com.example.asus.bugstore.Singleton.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText register_username,register_password,register_confirm_password,register_email;
    Button register_button;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    String register_manager_url = "http://nqhuyvn95.000webhostapp.com/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Match component
        register_username = (EditText)findViewById(R.id.register_username);
        register_password = (EditText)findViewById(R.id.register_password);
        register_confirm_password = (EditText)findViewById(R.id.register_confirm_password);
        register_email = (EditText)findViewById(R.id.register_email);
        register_button = (Button)findViewById(R.id.register_button);
        //Set Progress dialog
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Waiting from server");
        progressDialog.setMessage("Connecting");
        //Set AlertDialog
        builder = new AlertDialog.Builder(RegisterActivity.this);
        /**
         * Code for register part
         *
         * This code send data to register.php
         */
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checkfor empty
                if(register_username.getText().toString().equals("")||register_email.getText().toString().equals("")||register_password.getText().toString().equals("")||register_confirm_password.getText().toString().equals("")){
                    DisplayAlert("Please fill all ther fields");
                }else{
                    //Check for valid confirm password
                    if(!register_password.getText().toString().equals(register_confirm_password.getText().toString())){
                        DisplayAlert("Wrong confirm password");
                    }else{
                        //Send data to server
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, register_manager_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response is",response);
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String message = jsonObject.getString("server_response");
                                    DisplayAlert(message);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<>();
                                String user_name = register_username.getText().toString();
                                String email = register_email.getText().toString();
                                String password = register_password.getText().toString();
                                params.put("user_name",user_name);
                                params.put("email",email);
                                params.put("password",password);
                                return params;
                            }
                        };
                        MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(stringRequest);
                    }
                }
            }
        });

    }
    public void DisplayAlert(final String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(message.equals("Create account success")){
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
