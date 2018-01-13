package com.example.asus.bugstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.bugstore.Adapter.Exchange_buyer_Adapter;
import com.example.asus.bugstore.InfomationList.Exchange_list;
import com.example.asus.bugstore.Singleton.MySingleton;
import com.example.asus.bugstore.Stored_access_user_data.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataActivity extends AppCompatActivity {
    TextView account_value,buyer,saler;
    ListView exchange_list;
    String load_account_value_url = "http://nqhuyvn95.000webhostapp.com/return_account_value.php";
    String load_buyer_list_url = "http://nqhuyvn95.000webhostapp.com/return_buyer_list.php";
    String load_saler_list_url = "http://nqhuyvn95.000webhostapp.com/return_saler_list.php";
    ProgressDialog progressDialog;
    TextView data,profile,forum,store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        //Match components
        account_value = (TextView)findViewById(R.id.account_value);
        exchange_list = (ListView)findViewById(R.id.exchange_list);
        buyer = (TextView) findViewById(R.id.buyer_list);
        saler = (TextView) findViewById(R.id.saler_list);
        data = (TextView)findViewById(R.id.data);
        profile = (TextView)findViewById(R.id.profile);
        forum = (TextView) findViewById(R.id.forum);
        store = (TextView) findViewById(R.id.store);
        //Dialog configuration
        progressDialog = new ProgressDialog(DataActivity.this);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("connecting");
        //Move to other activities
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataActivity.this,ForumActivity.class);
                startActivity(intent);
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataActivity.this,StoreActivity.class);
                startActivity(intent);
            }
        });
        progressDialog.show();
        //get user name
        SharedPrefManager sharedPrefManager = new SharedPrefManager(DataActivity.this);
        final String user_name = sharedPrefManager.getUserName();

        // download the current account value from user table database;
        StringRequest current_account_value = new StringRequest(Request.Method.POST, load_account_value_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("the response data",response);
                    JSONObject jsonObject = new JSONObject(response);
                    String credit_value = jsonObject.getString("server_response");
                    account_value.setText(credit_value + "$");
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
                params.put("user_name",user_name);
                return params;
            }
        };
        MySingleton.getInstance(DataActivity.this).addToRequestQueue(current_account_value);
        //TODO: download the list of exchange that user was buyer
        final StringRequest buyer_list = new StringRequest(Request.Method.POST, load_buyer_list_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    ArrayList<Exchange_list>  exchange_lists = new ArrayList<>();
                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject exchange = jsonArray.getJSONObject(i);
                        String product_name = exchange.getString("product_name");
                        String producer_name = exchange.getString("producer_name");
                        String price = exchange.getString("price");
                        String deal_time = exchange.getString("deal_time");

                        exchange_lists.add(new Exchange_list(product_name,producer_name,price,deal_time));
                    }
                    Exchange_buyer_Adapter exchange_buyer_adapter = new Exchange_buyer_Adapter(DataActivity.this,exchange_lists);
                    exchange_list.setAdapter(exchange_buyer_adapter);


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
                params.put("user_name",user_name);
                return params;
            }
        };
        MySingleton.getInstance(DataActivity.this).addToRequestQueue(buyer_list);
        //TODO: download the list of exchange that user was saler
        saler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saler.setBackgroundResource(R.color.colorTest);
                saler.setTextColor(Color.parseColor("#000000"));
                (DataActivity.this).buyer.setBackgroundResource(R.color.colorYellow);
                (DataActivity.this).buyer.setTextColor(Color.parseColor("#ffffff"));
                (DataActivity.this).exchange_list.setAdapter(null);

                final StringRequest saler_list = new StringRequest(Request.Method.POST, load_saler_list_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                            ArrayList<Exchange_list>  exchange_lists = new ArrayList<>();
                            for(int i = 0; i<jsonArray.length();i++){
                                JSONObject exchange = jsonArray.getJSONObject(i);
                                String product_name = exchange.getString("product_name");
                                String user_name = exchange.getString("user_name");
                                String price = exchange.getString("price");
                                String deal_time = exchange.getString("deal_time");

                                exchange_lists.add(new Exchange_list(product_name,user_name,price,deal_time));
                            }
                            Exchange_buyer_Adapter exchange_buyer_adapter = new Exchange_buyer_Adapter(DataActivity.this,exchange_lists);
                            exchange_list.setAdapter(exchange_buyer_adapter);


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
                        SharedPrefManager sharedPrefManager = new SharedPrefManager(DataActivity.this);
                        String user_name = sharedPrefManager.getUserName();
                        params.put("user_name",user_name);
                        return params;
                    }
                };
                MySingleton.getInstance(DataActivity.this).addToRequestQueue(saler_list);
            }
        });
        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                buyer.setBackgroundResource(R.color.colorTest);
                buyer.setTextColor(Color.parseColor("#000000"));
                saler.setBackgroundResource(R.color.colorYellow);
                saler.setTextColor(Color.parseColor("#ffffff"));
                //
                final StringRequest buyer_list = new StringRequest(Request.Method.POST, load_buyer_list_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                            ArrayList<Exchange_list>  exchange_lists = new ArrayList<>();
                            for(int i = 0; i<jsonArray.length();i++){
                                JSONObject exchange = jsonArray.getJSONObject(i);
                                String product_name = exchange.getString("product_name");
                                String producer_name = exchange.getString("producer_name");
                                String price = exchange.getString("price");
                                String deal_time = exchange.getString("deal_time");

                                exchange_lists.add(new Exchange_list(product_name,producer_name,price,deal_time));
                            }
                            Exchange_buyer_Adapter exchange_buyer_adapter = new Exchange_buyer_Adapter(DataActivity.this,exchange_lists);
                            exchange_list.setAdapter(exchange_buyer_adapter);


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
                        params.put("user_name",user_name);
                        return params;
                    }
                };
                MySingleton.getInstance(DataActivity.this).addToRequestQueue(buyer_list);
            }
        });

    }

}
