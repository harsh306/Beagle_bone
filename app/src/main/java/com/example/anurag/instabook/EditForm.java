package com.example.anurag.instabook;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
//Script Importer
//sqlite3 /data/data/com.example.anurag.instabook/databases/MYDB.db

public class EditForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SQLDBhelper handler;
    SQLDBhelper mydb;
    String timestamp;
    String berthPref,sex,message;
    ExpandableListView expandableListView;

    Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);
        handler = new SQLDBhelper(this);
        Intent i = getIntent();
        String idText = i.getStringExtra("userid");
        id=new Integer(idText);
        expandableListView = (ExpandableListView)findViewById(R.id.lvExp);
        Passanger p =handler.getPassenger(id);
        EditText firstName,fullName,age,aadharNo,phoneno;
        fullName=(EditText) findViewById(R.id.fullnametext);
        age=(EditText)findViewById(R.id.ageText);
        aadharNo=(EditText)findViewById(R.id.aadharno);
        RadioGroup genderGroup;
        RadioButton genderButton;
        genderGroup=(RadioGroup)findViewById(R.id.radioGroup);
        int selectedId=genderGroup.getCheckedRadioButtonId();
        fullName.setText(p.getName());
        age.setText(p.getAge());
        aadharNo.setText(p.getUID());
        if(p.getSex().equals("Male"))
            genderGroup.check(R.id.radioButton2);
        else
            genderGroup.check(R.id.radioButton);
        String [] berthArray= {"No Preference","Upper Berth","Lower Berth","Middle Berth","Side Lower Berth","Side Upper Berth"};

        Spinner spinner = (Spinner) findViewById(R.id.berthpref);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add(p.getBerth());
        for (int k=0;k<berthArray.length;k++){
            if(p.getBerth().equals(berthArray[k])){
                continue;
            }
            else {
                categories.add(berthArray[k]);
            }
        }

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
    public void Update(View view){
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
            handler.updateContact(id, name, AGE, sex, berthPref, UID);
//            tempStore.insertContact(first+" "+last,"",AGE,sex,berthPref);
            Toast.makeText(getApplicationContext(), "Update Successfully", Toast.LENGTH_LONG).show();
            finish();
        }


    }

}
