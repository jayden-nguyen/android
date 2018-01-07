package com.example.asus.bugstore.Adapter;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.asus.bugstore.InfomationList.Exchange_list;
import com.example.asus.bugstore.R;

import java.util.ArrayList;

/**
 * Created by Asus on 1/2/2018.
 */

public class Exchange_buyer_Adapter extends ArrayAdapter<Exchange_list> {
    public Exchange_buyer_Adapter(Activity ctx, ArrayList<Exchange_list> exchange_lists){
        super(ctx,0,exchange_lists);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.product_item,parent,false);
        }
        Exchange_list exchange_list = getItem(position);

        TextView product_name = (TextView)listItem.findViewById(R.id.list_product_name);
        product_name.setText(exchange_list.getProduct_name());
        TextView person_name = (TextView)listItem.findViewById(R.id.list_product_producer);
        person_name.setText(exchange_list.getPerson_name());
        TextView product_price = (TextView)listItem.findViewById(R.id.list_product_price);
        product_price.setText(exchange_list.getPrice());
        TextView product_type_time = (TextView)listItem.findViewById(R.id.list_type_time);
        product_type_time.setText("Deal Time");
        TextView product_time = (TextView)listItem.findViewById(R.id.list_time);
        product_time.setText(exchange_list.getDeal_time());
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) product_price.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(Double.parseDouble(exchange_list.getPrice()));

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);



        return listItem;

    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        if(magnitudeFloor > 100){
            magnitudeColorResourceId = R.color.magnitude9;
        }else{
            if(magnitudeFloor>50){
                magnitudeColorResourceId = R.color.magnitude6;
            }else{
                if(magnitudeFloor>10){
                    magnitudeColorResourceId = R.color.magnitude4;
                }else{
                    magnitudeColorResourceId = R.color.magnitude1;
                }
            }
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
