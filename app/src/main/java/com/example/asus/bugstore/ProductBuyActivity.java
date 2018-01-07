package com.example.asus.bugstore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.bugstore.Singleton.MySingleton;
import com.example.asus.bugstore.Stored_access_user_data.SharedPrefManager;

import java.util.HashMap;
import java.util.Map;

public class ProductBuyActivity extends AppCompatActivity {
    TextView show_product_name,show_product_type,show_product_price,show_product_producer;
    ImageView background_image;
    Button buy_button;
    TextView data,profile,forum,store;
    String Process_exchange_url = "http://nqhuyvn95.000webhostapp.com/process_exchange.php";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_buy);

        final String product_name = getIntent().getStringExtra("product_name");
        String product_type = getIntent().getStringExtra("product_type");
        final String product_producer = getIntent().getStringExtra("product_producer");
        final String product_price = getIntent().getStringExtra("product_price");
        String product_image_url = getIntent().getStringExtra("product_image_url");
        //Match
        show_product_name = (TextView)findViewById(R.id.show_product_name);
        show_product_type = (TextView)findViewById(R.id.show_product_type);
        show_product_price = (TextView)findViewById(R.id.show_product_price);
        show_product_producer = (TextView)findViewById(R.id.show_product_producer);
        background_image = (ImageView)findViewById(R.id.background_image);
        buy_button = (Button)findViewById(R.id.buy_button);
        data = (TextView)findViewById(R.id.data);
        profile = (TextView)findViewById(R.id.profile);
        forum = (TextView) findViewById(R.id.forum);
        store = (TextView) findViewById(R.id.store);
        //
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductBuyActivity.this,DataActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductBuyActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductBuyActivity.this,ForumActivity.class);
                startActivity(intent);
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductBuyActivity.this,StoreActivity.class);
                startActivity(intent);
            }
        });
        //
        ImageRequest imageRequest = new ImageRequest(product_image_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                background_image.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(ProductBuyActivity.this).addToRequestQueue(imageRequest);
        //
        show_product_name.setText(product_name);
        show_product_type.setText(product_type);
        show_product_price.setText(product_price);
        show_product_producer.setText(product_producer);

        //Buy button click
        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Process_exchange_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response from process",response);
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
                        params.put("product_name",product_name);
                        params.put("product_price",product_price);
                        params.put("producer_name",product_producer);
                        SharedPrefManager sharedPrefManager = new SharedPrefManager(ProductBuyActivity.this);
                        params.put("user_name",sharedPrefManager.getUserName());
                        return params;
                    }
                };
                MySingleton.getInstance(ProductBuyActivity.this).addToRequestQueue(stringRequest);
            }
        });

    }
}
