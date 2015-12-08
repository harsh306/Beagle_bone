package com.example.anurag.instabook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DisplayData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    AutoCompleteTextView stationsFrom,stationsTo;
    String[] s_array,s_arrayTo;
    SQLDBhelper  handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        //Date Picking
        pDisplayDate = (TextView) findViewById(R.id.dateText);
        pPickDate = (Button) findViewById(R.id.journeydate);

        /** Listener for click event of the button */
        pPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        /** Display the current date in the TextView */
        updateDisplay();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FloatingForm.class);
                startActivity(intent);
            }
        });
//        Date Picker
        TextView date=(TextView)findViewById(R.id.dateText);
        String today= DateFormat.getDateInstance().format(new Date());
        date.setText(today);
//        Auto Complete From
        stationsFrom=(AutoCompleteTextView) findViewById(R.id.fromAutoComplete);
        s_array=getResources().getStringArray(R.array.Stations);
        ArrayAdapter<String> stationAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,s_array);
        stationsFrom.setAdapter(stationAdapter);
        stationsFrom.setThreshold(3);

        // Auto Complete To
        stationsTo=(AutoCompleteTextView) findViewById(R.id.toAutoComplete);
        s_arrayTo=getResources().getStringArray(R.array.Stations);
        ArrayAdapter<String> stationAdapterTo=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,s_arrayTo);
        stationsTo.setAdapter(stationAdapterTo);
        stationsTo.setThreshold(3);

        // get the listview

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("E-Ticket");
        categories.add("i-Ticket");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);

                    // You now have everything that you would as if this was an OnChildClickListener()
                    // Add your logic here.

                    // Return true as we are handling the event.
                    return true;
                }
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        handler=new SQLDBhelper(this);
        String array=handler.dBtoString();
        String [] s = array.split(",");

        List<Passanger> l =handler.dBtoPassanger();
        Passanger [] p= l.toArray(new Passanger[l.size()]);
        Integer integer = new Integer(l.size());
        for (int i=0;i<integer;i++){
            listDataHeader.add(p[i].getName());
            List<String> top250 = new ArrayList<String>();
            top250.add("Age :"+p[i].getAge());
            top250.add("Sex :"+p[i].getSex());
            top250.add("Phone :"+p[i].getPhone());
            top250.add("Berth Preference :"+p[i].getBerth());
            listDataChild.put(listDataHeader.get(i), top250);
        }

    }
    private TextView pDisplayDate;
    private Button pPickDate;
    private int pYear;
    private int pMonth;
    private int pDay;
    /** This integer will uniquely define the dialog to be used for displaying date picker.*/
    static final int DATE_DIALOG_ID = 0;

    /** Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();

                }
            };

    /** Updates the date in the TextView */
    private void updateDisplay() {
        pDisplayDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pMonth + 1).append("/")
                        .append(pDay).append("/")
                        .append(pYear).append(" "));
    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }



    public void date(View v){
        Intent i = new Intent(getApplicationContext(),FloatingDatePicker.class);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    String msg = "Android : ";
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }



    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Intent i = new Intent(getIntent());
        startActivity(i);
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }

}








