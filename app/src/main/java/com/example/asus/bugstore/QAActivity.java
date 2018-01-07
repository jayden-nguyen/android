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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.bugstore.Adapter.AnswerAdapter;
import com.example.asus.bugstore.InfomationList.AnswerList;
import com.example.asus.bugstore.Singleton.MySingleton;
import com.example.asus.bugstore.Stored_access_user_data.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QAActivity extends AppCompatActivity {
    TextView show_producer_question,show_ques_title,show_ques_content;
    EditText create_answer;
    ListView show_answer_list;
    Button create_answer_btn;
    TextView data,profile,forum,store;
    ProgressDialog progressDialog;
    ArrayList<AnswerList> answerLists = new ArrayList<>();
    AlertDialog.Builder builder;
    String return_answer_list_url = "http://nqhuyvn95.000webhostapp.com/return_answer_list.php";
    String create_answer_url = "http://nqhuyvn95.000webhostapp.com/create_answer.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
        //Dialog start
        progressDialog = new ProgressDialog(QAActivity.this);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("CONNECTING");
        builder = new AlertDialog.Builder(QAActivity.this);
        //Match components
        show_producer_question = (TextView)findViewById(R.id.show_produder_question);
        show_ques_title = (TextView)findViewById(R.id.show_ques_title);
        show_ques_content = (TextView)findViewById(R.id.show_ques_content);
        create_answer = (EditText)findViewById(R.id.create_answer);
        create_answer_btn = (Button) findViewById(R.id.create_answer_btn);
        show_answer_list = (ListView)findViewById(R.id.show_answer_list);
        data = (TextView)findViewById(R.id.data);
        profile = (TextView)findViewById(R.id.profile);
        forum = (TextView) findViewById(R.id.forum);
        store = (TextView) findViewById(R.id.store);
        //
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QAActivity.this,DataActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QAActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QAActivity.this,ForumActivity.class);
                startActivity(intent);
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QAActivity.this,StoreActivity.class);
                startActivity(intent);
            }
        });
        //
        final String producer_ques = getIntent().getStringExtra("producer_name");
        String ques_title = getIntent().getStringExtra("ques_title");
        final String ques_content = getIntent().getStringExtra("ques_content");
        final String ques_id = getIntent().getStringExtra("ques_id");
        Log.d("producer",producer_ques);

        show_producer_question.setText(producer_ques);
        show_ques_title.setText(ques_title);
        show_ques_content.setText(ques_content);
        //TODO: Load answer list
        StringRequest stringRequest = new StringRequest(Request.Method.POST, return_answer_list_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("answer list res",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                    for(int i = 0;i<jsonArray.length();i++){
                        JSONObject answer = jsonArray.getJSONObject(i);
                        String answer_content = answer.getString("answer_content");
                        String answer_producer = answer.getString("answer_producer");

                        answerLists.add(new AnswerList(answer_content,answer_producer));
                    }
                    AnswerAdapter answerAdapter = new AnswerAdapter(QAActivity.this,answerLists);
                    show_answer_list.setAdapter(answerAdapter);
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
                params.put("ques_id",ques_id);
                return params;
            }
        };
        MySingleton.getInstance(QAActivity.this).addToRequestQueue(stringRequest);
        //TODO: Create answer, upload to server
        create_answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final SharedPrefManager sharedPrefManager = new SharedPrefManager(QAActivity.this);
                answerLists.add(new AnswerList(create_answer.getText().toString(),sharedPrefManager.getUserName()));
                AnswerAdapter answerAdapter = new AnswerAdapter(QAActivity.this,answerLists);
                answerAdapter.notifyDataSetChanged();
                show_answer_list.setAdapter(answerAdapter);

                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, create_answer_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("create answer res",response);
                        progressDialog.dismiss();
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

                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("answer_content",create_answer.getText().toString());
                        params.put("ques_id",ques_id);

                        params.put("answer_producer",sharedPrefManager.getUserName());
                        return params;
                    }
                };
                MySingleton.getInstance(QAActivity.this).addToRequestQueue(stringRequest1);
            }
        });

    }
}
