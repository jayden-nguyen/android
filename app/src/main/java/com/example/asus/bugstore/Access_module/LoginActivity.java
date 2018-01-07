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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.bugstore.R;
import com.example.asus.bugstore.Singleton.MySingleton;
import com.example.asus.bugstore.StoreActivity;
import com.example.asus.bugstore.Stored_access_user_data.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText login_username,login_password;
    Button login_button;
    TextView login_to_signup;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final String login_manager_url = "http://nqhuyvn95.000webhostapp.com/login.php";

        // Match component
        login_username = (EditText)findViewById(R.id.login_username);
        login_password = (EditText)findViewById(R.id.login_password);
        login_button = (Button) findViewById(R.id.login_button);
        login_to_signup = (TextView)findViewById(R.id.login_to_signup);
        //Set Progress dialog
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Waiting from server");
        progressDialog.setMessage("Connecting");
        //Set AlertDialog
        builder = new AlertDialog.Builder(LoginActivity.this);
        // Set intent to Signup Page
        login_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        /**
         * Code for login part
         *
         * In  this part we check the information and send it to login.php to check the account
         * is in database or not
         *
         */
        login_button.setOnClickListener(new View.OnClickListener() {
            //Appear progress dialog
            @Override
            public void onClick(View v) {
                Log.d("start click","ok");

                //Check for unempty
                if(login_username.getText().toString().equals("")|| login_password.getText().toString().equals("")){
                    DisplayAlert("Please fill all the fields");
                }else{
                    //
                    Log.d("did we get there","abcdef");
                    progressDialog.show();

                    Log.d("before string request","hello");
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_manager_url, new Response.Listener<String>() {
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
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Log.d("did we come to Map", "");
                            Map<String, String> params = new HashMap<String, String>();
                            //Create infomation variables
                            final String user_name = login_username.getText().toString();
                            final String password = login_password.getText().toString();
                            params.put("user_name", user_name);
                            params.put("password", password);
                            return params;
                        }
                    };

                    MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);

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
                if(message.equals("Login success")){
                    SharedPrefManager sharedPrefManager = new SharedPrefManager(LoginActivity.this);
                    sharedPrefManager.createSessionName(login_username.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, StoreActivity.class);
                    startActivity(intent);
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
