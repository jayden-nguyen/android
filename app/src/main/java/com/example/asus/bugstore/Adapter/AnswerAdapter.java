package com.example.asus.bugstore.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.asus.bugstore.InfomationList.AnswerList;
import com.example.asus.bugstore.R;

import java.util.ArrayList;

/**
 * Created by Asus on 1/6/2018.
 */

public class AnswerAdapter extends ArrayAdapter<AnswerList> {
    public AnswerAdapter(Activity context, ArrayList<AnswerList> answerLists){
        super(context,0,answerLists);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.product_item,parent,false);
        }
        AnswerList answerList = getItem(position);

        TextView answer_content = (TextView)listItem.findViewById(R.id.list_product_name);
        answer_content.setText(answerList.getAnswer_content());
        TextView answer_producer = (TextView)listItem.findViewById(R.id.list_product_producer);
        answer_producer.setText(answerList.getAnswer_producer());
        return listItem;
    }
}
