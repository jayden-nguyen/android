package com.example.asus.bugstore.InfomationList;

/**
 * Created by Asus on 1/2/2018.
 */

public class Exchange_list {
    String product_name;
    String person_name;
    String price;
    String image_url;
    String product_type;
    String deal_time;
    public Exchange_list(String product_name,String person_name,String price,String deal_time){
        this.product_name = product_name;
        this.person_name = person_name;
        this.price = price;
        this.deal_time = deal_time;
    }
    public String getProduct_name(){
        return product_name;
    }
    public String getPerson_name(){
        return person_name;
    }
    public String getPrice(){
        return price;
    }
    public String getProduct_type(){
        return  product_type;
    }
    public String getImage_url(){
        return image_url;
    }
    public String getDeal_time(){
        return deal_time;
    }
}
