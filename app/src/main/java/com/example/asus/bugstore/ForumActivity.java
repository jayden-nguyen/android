package com.example.asus.bugstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.bugstore.Adapter.QuestionAdapter;
import com.example.asus.bugstore.InfomationList.QuestionList;
import com.example.asus.bugstore.Singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {
    Button forum_create_question_btn;
    ListView forum_question_list;
    TextView data,profile,forum,store;
    ArrayList<QuestionList> questionLists = new ArrayList<>();

    String return_question_list_url = "http://nqhuyvn95.000webhostapp.com/return_question_list.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        //
        forum_create_question_btn = (Button)findViewById(R.id.forum_create_ques_btn);
        forum_question_list = (ListView)findViewById(R.id.forum_question_list);
        data = (TextView)findViewById(R.id.data);
        profile = (TextView)findViewById(R.id.profile);
        forum = (TextView) findViewById(R.id.forum);
        store = (TextView) findViewById(R.id.store);
        //
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumActivity.this,DataActivity.class);
                startActivity(intent);
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumActivity.this,StoreActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        forum_create_question_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumActivity.this,AddQuestionActivity.class);
                startActivity(intent);
            }
        });
        //
        StringRequest stringRequest = new StringRequest(Request.Method.GET, return_question_list_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject question = jsonArray.getJSONObject(i);
                        String ques_id = question.getString("ques_id");
                        String ques_title = question.getString("ques_title");
                        String ques_content = question.getString("ques_content");
                        String producer_name = question.getString("producer_name");
                        String created_time = question.getString("created_time");

                        questionLists.add(new QuestionList(ques_id,ques_title,ques_content,created_time,producer_name));
                    }
                    final QuestionAdapter questionAdapter = new QuestionAdapter(ForumActivity.this,questionLists);
                    forum_question_list.setAdapter(questionAdapter);
                    forum_question_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            QuestionList questionList = questionAdapter.getItem(position);
                            Intent question_infomation = new Intent(ForumActivity.this,QAActivity.class);
                            question_infomation.putExtra("producer_name",questionList.getProducer_name());
                            question_infomation.putExtra("ques_title",questionList.getQues_title());
                            question_infomation.putExtra("ques_content",questionList.getQues_content());
                            question_infomation.putExtra("ques_id",questionList.getQues_id());
                            startActivity(question_infomation);
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
        MySingleton.getInstance(ForumActivity.this).addToRequestQueue(stringRequest);
    }

}
