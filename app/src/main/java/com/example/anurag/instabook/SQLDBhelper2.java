package com.example.anurag.instabook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLDBhelper2 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TEMP.db";
    public static final String CONTACTS_TABLE_NAME = "temp";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_AGE = "age";
    public static final String CONTACTS_COLUMN_SEX = "sex";
    public static final String CONTACTS_COLUMN_BERTH = "berth";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;

    public SQLDBhelper2(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        context.deleteDatabase(DATABASE_NAME);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS temp");
        db.execSQL(
                "create table temp " +
                        "(id integer primary key autoincrement, name text,phone text,age text, sex text , berth text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS temp");
        onCreate(db);
    }

    public boolean insertContact  (String name, String phone, String age, String sex,String berth)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("age", age);
        contentValues.put("sex", sex);
        contentValues.put("berth", berth);
        db.insert("temp", null, contentValues);
        return true;
    }
    public int c=0;
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from temp where id=" + id + "", null);
         c = res.getCount();
       // System.out.print(c);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        int numRows =(int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM "+CONTACTS_TABLE_NAME, null);
        return numRows;
    }

    public String dBtoString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query="Select * from temp where 1";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("name"))!=null){
                dbString+=c.getString(c.getColumnIndex("name"))+","+c.getString(c.getColumnIndex("age"))+","+c.getString(c.getColumnIndex("sex"))+","+c.getString(c.getColumnIndex("phone"));
                dbString+="\n \n \n";

            }
            c.moveToNext();

        }
        db.close();
        return dbString;
    }
    public List<Passanger> dBtoPassanger(){
        List<Passanger> p2=new ArrayList<Passanger>();
        String query="Select * from temp where 1";
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
                p.setPhone(c.getString(c.getColumnIndex("phone")));
                p.setSex(c.getString(c.getColumnIndex("sex")));

            }
            c.moveToNext();
            i++;
            p2.add(p);
        }
        db.close();
        return p2;
    }

    public boolean updateContact (Integer id, String name, String phone, String age, String sex,String berth)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("age", age);
        contentValues.put("sex", sex);
        contentValues.put("berth", berth);
        db.update("temp", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("temp",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from temp", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}