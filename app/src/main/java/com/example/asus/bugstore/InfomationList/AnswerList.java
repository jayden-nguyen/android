package com.example.asus.bugstore.InfomationList;

/**
 * Created by Asus on 1/6/2018.
 */

public class AnswerList {
    String answer_content;
    String answer_producer;
    public AnswerList(String answer_content,String answer_producer){
        this.answer_content = answer_content;
        this.answer_producer = answer_producer;
    }
    public String getAnswer_content(){
        return answer_content;
    }
    public String getAnswer_producer(){
        return answer_producer;
    }
}
