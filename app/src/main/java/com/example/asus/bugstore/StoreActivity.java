package com.example.asus.bugstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.bugstore.Adapter.ProductAdapter;
import com.example.asus.bugstore.InfomationList.Product;
import com.example.asus.bugstore.Singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {
    Button store_create_button;
    TextView data,profile,forum,store;
    String return_product_list_url = "http://nqhuyvn95.000webhostapp.com/return_product_list.php";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        //
        progressDialog = new ProgressDialog(StoreActivity.this);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("UPDATING");
        progressDialog.show();
        //Match
        store_create_button = (Button)findViewById(R.id.store_create_button);
        data = (TextView)findViewById(R.id.data);
        profile = (TextView)findViewById(R.id.profile);
        forum = (TextView) findViewById(R.id.forum);
        store = (TextView) findViewById(R.id.store);
        //Move to other activities
        store_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this,CreateProductActivity.class);
                startActivity(intent);
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this,ForumActivity.class);
                startActivity(intent);
            }
        });
        //Load Product List
        StringRequest stringRequest = new StringRequest(Request.Method.GET, return_product_list_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("product list",response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    ArrayList<Product> products = new ArrayList<>();
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject product = jsonArray.getJSONObject(jsonArray.length()-1-i);
                        String product_name = product.getString("product_name");
                        String producer_name = product.getString("producer_name");
                        String price = product.getString("price");
                        String image_url = product.getString("image_url");
                        String product_type = product.getString("product_type");
                        String created_time = product.getString("created_time");
                        products.add(new Product(product_name,producer_name,price,product_type,image_url,created_time));
                    }
                    ListView store_list_product = (ListView)findViewById(R.id.store_list_product);
                    ProductAdapter productAdapter = new ProductAdapter(StoreActivity.this,products);
                    store_list_product.setAdapter(productAdapter);

                    final ProductAdapter adapter = new ProductAdapter(StoreActivity.this,products);
                    store_list_product.setAdapter(adapter);
                    store_list_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Product product = adapter.getItem(position);

                            Intent product_infomation = new Intent(StoreActivity.this,ProductBuyActivity.class);
                            product_infomation.putExtra("product_name",product.getProduct_name());
                            product_infomation.putExtra("product_type",product.getProduct_type());
                            product_infomation.putExtra("product_producer",product.getProducer_name());
                            product_infomation.putExtra("product_price",product.getPrice());
                            product_infomation.putExtra("product_image_url",product.getImage_url());
                            startActivity(product_infomation);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(StoreActivity.this).addToRequestQueue(stringRequest);
    }
}
