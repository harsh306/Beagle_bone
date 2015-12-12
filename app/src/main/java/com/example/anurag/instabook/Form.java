package com.example.anurag.instabook;

import java.lang.ref.SoftReference;

/**
 * Created by harsh on 12/12/15.
 */
public class Form {
    private int id,count;
    private String form_name,from_s,to_s,class_t,quota,ticket_t,date,phone;
    public void setForm_name(String form_name){this.form_name=form_name;}
    public void setFrom_s(String from_s){this.from_s=from_s;}
    public void setTo_s(String to_s){this.to_s=to_s;}
    public void setClass_t(String class_t){this.class_t=class_t;}
    public void setQuota(String quota){this.quota=quota;}
    public void setTicket_t(String ticket_t){this.ticket_t=ticket_t;}
    public  void setDate(String date){this.date=date;}
    public void setPhone(String phone){this.phone=phone;}
    public void setCount(Integer count){this.count=count;}
    public String getForm_name(){return this.form_name;}
    public String getFrom_s(){return  this.from_s;}
    public String getTo_s(){return  this.to_s;}
    public String getClass_t(){return this.class_t;}
    public String getQuota(){return this.quota;}
    public String getTicket_t(){return this.ticket_t;}
    public String getDate(){return this.date;}
    public String getPhone(){return this.phone;}
    public Integer getCount(){return this.count;}
}
