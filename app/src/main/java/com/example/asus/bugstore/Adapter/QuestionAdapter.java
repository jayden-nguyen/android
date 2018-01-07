package com.example.asus.bugstore.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.asus.bugstore.InfomationList.QuestionList;
import com.example.asus.bugstore.R;

import java.util.ArrayList;

/**
 * Created by Asus on 1/6/2018.
 */

public class QuestionAdapter extends ArrayAdapter<QuestionList> {
    public QuestionAdapter(Activity context, ArrayList<QuestionList> questionLists){
        super(context,0,questionLists);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.product_item,parent,false);
        }
        QuestionList questionList = getItem(position);

        TextView ques_title = (TextView)listItem.findViewById(R.id.list_product_name);
        ques_title.setText(questionList.getQues_title());
        TextView producer_name = (TextView)listItem.findViewById(R.id.list_product_producer);
        producer_name.setText(questionList.getProducer_name());
        TextView created_time = (TextView)listItem.findViewById(R.id.list_time);
        created_time.setText(questionList.getCreated_time());
        TextView type_time = (TextView)listItem.findViewById(R.id.list_type_time);
        type_time.setText("Created time");
        TextView id = (TextView)listItem.findViewById(R.id.list_product_price);
        id.setText(questionList.getQues_id());

        return listItem;
    }
}
