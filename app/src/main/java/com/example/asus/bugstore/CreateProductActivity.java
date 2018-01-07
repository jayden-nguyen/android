package com.example.asus.bugstore;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.bugstore.Singleton.MySingleton;
import com.example.asus.bugstore.Stored_access_user_data.SharedPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateProductActivity extends AppCompatActivity implements View.OnClickListener {
    EditText create_product_name,create_product_type,create_product_price,create_product_image_name;
    Button create_button,choose_image_button;
    TextView data,profile,forum,store;
    ImageView image;
    final String create_product_magager_url = "http://nqhuyvn95.000webhostapp.com/create_product.php";
    private Bitmap bitmap;
    int Image_Request = 1;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        // Match components
        create_product_name = (EditText) findViewById(R.id.create_product_name);
        create_product_price = (EditText) findViewById(R.id.create_product_price);
        create_product_type = (EditText) findViewById(R.id.create_product_type);
        create_product_image_name = (EditText) findViewById(R.id.create_product_image_name);
        image = (ImageView)findViewById(R.id.image);
        create_button = (Button)findViewById(R.id.create_button);
        choose_image_button = (Button)findViewById(R.id.choose_image_button);
        data = (TextView)findViewById(R.id.data);
        profile = (TextView)findViewById(R.id.profile);
        forum = (TextView) findViewById(R.id.forum);
        store = (TextView) findViewById(R.id.store);
        //set Intent to other activities
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateProductActivity.this,DataActivity.class);
                startActivity(intent);
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateProductActivity.this, StoreActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateProductActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateProductActivity.this,ForumActivity.class);
                startActivity(intent);
            }
        });
        //set event click for 2 buttons
        choose_image_button.setOnClickListener(this);
        create_button.setOnClickListener(this);
        //
        progressDialog = new ProgressDialog(CreateProductActivity.this);
        progressDialog.setTitle("Waiting from server");
        progressDialog.setMessage("Connecting");
        builder = new AlertDialog.Builder(CreateProductActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_image_button:
                selectImage();
                break;

            case R.id.create_button:
                Log.d("before upload","data");
                UploadData();
                break;
        }
    }
    private String ImageToString (Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,Image_Request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Image_Request && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
                create_product_image_name.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void UploadData(){
        progressDialog.show();
        Log.d("start upload","data");
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, create_product_magager_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                builder.setMessage(response);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(CreateProductActivity.this,StoreActivity.class);
                        startActivity(intent);
                    }

                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
               Log.d("the create response",response);
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
                params.put("product_name",create_product_name.getText().toString());
                params.put("product_type",create_product_type.getText().toString());
                params.put("product_price",create_product_price.getText().toString());
                SharedPrefManager sharedPrefManager = new SharedPrefManager(CreateProductActivity.this);
                Log.d("producer",sharedPrefManager.getUserName());
                params.put("product_producer",sharedPrefManager.getUserName());
                params.put("product_image_name",create_product_image_name.getText().toString());
                params.put("product_image",ImageToString(bitmap));
                return params;
            }
        };
        MySingleton.getInstance(CreateProductActivity.this).addToRequestQueue(stringRequest);
    }

}
