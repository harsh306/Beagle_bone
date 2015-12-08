package com.example.anurag.instabook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FloatingForm extends Activity implements AdapterView.OnItemSelectedListener{

    SQLDBhelper mydb;

    String berthPref,sex,message;
    Integer count;
    List<Passanger> p2=new ArrayList<Passanger>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_floating_form);
        mydb = new SQLDBhelper(this);
//        Button add=(Button)findViewById(R.id.button3);
        Intent intent = getIntent();

//        add.setText("Add New Passanger ");
        count=new Integer("1");
        EditText first,last;
        Spinner spinner = (Spinner) findViewById(R.id.berthpref);
        spinner.setOnItemSelectedListener( this);
        List<String> categories = new ArrayList<String>();
        categories.add("Upper Berth");
        categories.add("Lower Berth");
        categories.add("Middle Berth");
        categories.add("Window ");
        categories.add("Side Lower Berth");
        categories.add("Side Upper Berth");
        categories.add("None");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        berthPref=item;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void addNew(View view){

        EditText firstName,fullName,age,aadharNo,phoneno;
        fullName=(EditText) findViewById(R.id.fullnametext);
        age=(EditText)findViewById(R.id.ageText);
        aadharNo=(EditText)findViewById(R.id.aadharno);


        if(count==null)
            count=1;
        if(count <6 ){
            count++;
            fullName.setText("");
            age.setText("");
            aadharNo.setText("");

        }
        else{
            Toast.makeText(getApplicationContext(), "Maximum Limit Reached", Toast.LENGTH_LONG).show();
        }

    }
    public void Submit(View view){
        EditText firstName,fullName,age,aadharNo,phoneno;
        fullName=(EditText) findViewById(R.id.fullnametext);
        age=(EditText)findViewById(R.id.ageText);
        aadharNo=(EditText)findViewById(R.id.aadharno);
        RadioGroup genderGroup;
        RadioButton genderButton;
        genderGroup=(RadioGroup)findViewById(R.id.radioGroup);
        int selectedId=genderGroup.getCheckedRadioButtonId();






        String name=fullName.getText().toString()+"";
        String AGE=age.getText().toString()+"";
        String UID=aadharNo.getText().toString()+"";
        TextView forgotName= (TextView)findViewById(R.id.forgotName);
        EditText forgotName2 = (EditText) findViewById(R.id.fullnametext);
        TextView noage= (TextView)findViewById(R.id.noage);
        EditText noage2 = (EditText) findViewById(R.id.ageText);
        forgotName.setTextColor(Color.parseColor("#ffffff"));
        forgotName2.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
        forgotName.setText("");
        if(AGE.equals("") && name.equals("")){

            forgotName.setTextColor(Color.parseColor("#f44336"));
            forgotName.setText("Enter name");

            forgotName2.getBackground().setColorFilter(Color.parseColor("#f44336"), PorterDuff.Mode.SRC_ATOP);


            noage.setTextColor(Color.parseColor("#f44336"));
            noage.setText("Enter age");

            noage2.getBackground().setColorFilter(Color.parseColor("#f44336"), PorterDuff.Mode.SRC_ATOP);

        }
        else  if(selectedId==-1)
            Toast.makeText(getApplicationContext(), "Enter Gender, Male/Female ", Toast.LENGTH_LONG).show();
       else if(name.equals("")){

            forgotName.setTextColor(Color.parseColor("#f44336"));
            forgotName.setText("Enter name");

            forgotName2.getBackground().setColorFilter(Color.parseColor("#f44336"), PorterDuff.Mode.SRC_ATOP);
        }
       else if (AGE.equals("")) {


            noage.setTextColor(Color.parseColor("#f44336"));
            noage.setText("Enter age");
            noage2.getBackground().setColorFilter(Color.parseColor("#f44336"), PorterDuff.Mode.SRC_ATOP);
        }

//            Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG).show();
        // SQL Entires will be saved from here



        else {
//            SQLDBhelper2 tempStore;
//            tempStore=new SQLDBhelper2(this);
            genderButton = (RadioButton) findViewById(selectedId);
            sex = genderButton.getText().toString();
            Toast.makeText(getApplicationContext(), sex, Toast.LENGTH_LONG).show();
            mydb.insertContact(name, "", AGE, sex, berthPref);
//            tempStore.insertContact(first+" "+last,"",AGE,sex,berthPref);
            Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
            finish();
        }


    }

    public void close(View view){
        finish();
    }

}

