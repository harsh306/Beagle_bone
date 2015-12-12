package com.example.anurag.instabook;

public class Passanger {
    private int id;
    private String name,sex,age,uid,berth,userid;
    public Passanger(){uid="";}
    public void setName(String name){this.name=name;}
    public void setSex(String sex){this.sex=sex;}
    public void setBerth(String b){this.berth=b;}
    public void setAge(String age){this.age=age;}
    public void setUID(String Uid){this.uid=Uid;}
    public void setUserID(String userID){this.userid=userID;}
    public String getSex(){return this.sex;}
    public String getBerth(){return this.berth;}
    public String getName(){return this.name;}
    public String getAge(){return this.age;}
    public String getUID(){return  this.uid;}
    public String getuserID(){return this.userid;}


}
