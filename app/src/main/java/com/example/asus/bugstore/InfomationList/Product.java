package com.example.asus.bugstore.InfomationList;

/**
 * Created by Asus on 12/27/2017.
 */

public class Product {
    String product_name;
    String producer_name;
    String price;
    String image_url;
    String product_type;
    String created_time;
    public Product(String product_name,String producer_name,String price,String product_type,String image_url,String created_time){
        this.product_name = product_name;
        this.producer_name = producer_name;
        this.price = price;
        this.image_url = image_url;
        this.product_type = product_type;
        this.created_time = created_time;
    }
    public String getProduct_name(){
        return product_name;
    }
    public String getProducer_name(){
        return producer_name;
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
    public String getCreated_time(){
        return created_time;
    }
}
