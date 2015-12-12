package com.example.anurag.instabook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SQLDBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MYDB.db";
    public static final String CONTACTS_TABLE_NAME = "passes";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_AGE = "age";
    public static final String CONTACTS_COLUMN_SEX = "sex";
    public static final String CONTACTS_COLUMN_BERTH = "berth";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;

    public SQLDBhelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table passes " +
                        "(id integer primary key autoincrement, name text,age text, gender text , berth text,uid text,timestamp text,userid text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS passes");
        onCreate(db);
    }

    public boolean insertContact  (String name, String age, String sex,String berth,String uid,String userid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("uid",uid);
        contentValues.put("age", age);
        contentValues.put("gender", sex);
        contentValues.put("berth", berth);
        contentValues.put("userid",userid);
        db.insert("passes", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from passes where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

//    public String dBtoString(){
//        String dbString = "";
//        SQLiteDatabase db = getWritableDatabase();
//        String query="Select * from passes";
//        Cursor c = db.rawQuery(query,null);
//        c.moveToFirst();
//        while(!c.isAfterLast()){
//            if(c.getString(c.getColumnIndex("name"))!=null){
//                dbString+=c.getString(c.getColumnIndex("name"))+","+c.getString(c.getColumnIndex("age"))+","+c.getString(c.getColumnIndex("sex"))+","+c.getString(c.getColumnIndex("uid"));
//                dbString+="\n \n \n";
//
//            }
//            c.moveToNext();
//
//        }
//        db.close();
//        return dbString;
//    }
    public String getLast(){
        String query="Select userid from passes";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        c.moveToLast();
        String userid=c.getString(c.getColumnIndex("userid"));
        return userid;
    }
    public Passanger getPassenger(Integer id){
        String query="Select * from passes where userid="+id.toString();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        Passanger p= new Passanger();
        while(!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("name")) != null) {
                p.setBerth(c.getString(c.getColumnIndex("berth")));
                p.setName(c.getString(c.getColumnIndex("name")));
                p.setAge(c.getString(c.getColumnIndex("age")));
                p.setUID(c.getString(c.getColumnIndex("uid")));
                p.setSex(c.getString(c.getColumnIndex("gender")));

            }
            c.moveToNext();
        }
        return p;
    }
    public List<Passanger> dBtoPassanger(){
        List<Passanger> p2=new ArrayList<Passanger>();
        String query="Select * from passes where 1";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        int i=0;
        while(!c.isAfterLast()) {
            Passanger p = new Passanger();
            if(c.getString(c.getColumnIndex("name"))!=null) {
                p.setBerth(c.getString(c.getColumnIndex("berth")));
                p.setName(c.getString(c.getColumnIndex("name")));
                p.setAge(c.getString(c.getColumnIndex("age")));
                p.setUID(c.getString(c.getColumnIndex("uid")));
                p.setSex(c.getString(c.getColumnIndex("gender")));
                p.setUserID(c.getString(c.getColumnIndex("userid")));
            }
            c.moveToNext();
            i++;
            p2.add(p);
        }
        db.close();
        return p2;
    }

    public boolean updateContact (Integer id, String name,  String age, String sex,String berth,String uid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("uid", uid);
        contentValues.put("age", age);
        contentValues.put("gender", sex);
        contentValues.put("berth", berth);
        db.update("passes", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("passes",
                "userid = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from passes", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}