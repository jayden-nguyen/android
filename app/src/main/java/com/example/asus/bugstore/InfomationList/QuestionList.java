package com.example.asus.bugstore.InfomationList;

/**
 * Created by Asus on 1/6/2018.
 */

public class QuestionList {
    String ques_id,ques_title,ques_content,producer_name,created_time;
    public QuestionList(String ques_id,String ques_title,String ques_content,String created_time,String producer_name){
        this.ques_id = ques_id;
        this.ques_title = ques_title;
        this.ques_content = ques_content;
        this.producer_name = producer_name;
        this.created_time = created_time;
    }
    public String getQues_id(){
        return ques_id;
    }
    public String getQues_title(){
        return ques_title;
    }
    public String getQues_content(){
        return ques_content;
    }
    public String getProducer_name(){
        return producer_name;
    }
    public String getCreated_time(){
        return created_time;
    }

}
