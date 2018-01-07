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

public class AddQuestionActivity extends AppCompatActivity {
    EditText question_title,question_content;
    Button create_question_btn;
    String create_question_url = "http://nqhuyvn95.000webhostapp.com/create_question.php";
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    TextView data,profile,forum,store;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        //Match the components;
        question_content = (EditText) findViewById(R.id.question_content);
        question_title = (EditText) findViewById(R.id.question_title);
        create_question_btn = (Button) findViewById(R.id.create_question_btn);
        data = (TextView)findViewById(R.id.data);
        profile = (TextView)findViewById(R.id.profile);
        forum = (TextView) findViewById(R.id.forum);
        store = (TextView) findViewById(R.id.store);
        //
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQuestionActivity.this,DataActivity.class);
                startActivity(intent);
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQuestionActivity.this,StoreActivity.class);
                startActivity(intent);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQuestionActivity.this,ForumActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQuestionActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(AddQuestionActivity.this);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("Creating");
        builder = new AlertDialog.Builder(AddQuestionActivity.this);
        create_question_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, create_question_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Add ques response",response);
                        builder.setMessage(response);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
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
                        SharedPrefManager sharedPrefManager = new SharedPrefManager(AddQuestionActivity.this);
                        params.put("producer_name",sharedPrefManager.getUserName());
                        params.put("ques_title",question_title.getText().toString());
                        params.put("ques_content",question_content.getText().toString());
                        return params;
                    }
                };
                MySingleton.getInstance(AddQuestionActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }
}
