package com.example.anurag.instabook;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by harsh on 12/12/15.
 */
public class NewFormListner implements NewForm.OnBackPressedListener,ActionMode.Callback {
    private final FragmentActivity activity;

    public NewFormListner(FragmentActivity activity) {
        this.activity = activity;
    }
    ActionMode mode=null;
    @Override
    public  void doBack() {
        TextView date =(TextView)activity.findViewById(R.id.dateText);
        Spinner ticket_t=(Spinner)activity.findViewById(R.id.spinner);
        EditText trainno =(EditText)activity.findViewById(R.id.trainno);
        Spinner quota=(Spinner)activity.findViewById(R.id.quota);
        AutoCompleteTextView from_s=(AutoCompleteTextView)activity.findViewById(R.id.fromAutoComplete);
        AutoCompleteTextView to_s=(AutoCompleteTextView)activity.findViewById(R.id.toAutoComplete);
        Spinner class_t=(Spinner)activity.findViewById(R.id.class_t);
        EditText phone=(EditText)activity.findViewById(R.id.phone);
        EditText form_name =(EditText)activity.findViewById(R.id.formname);

        Toast.makeText(activity,"Go Back",Toast.LENGTH_LONG).show();
        SQLDBhelper ha = new SQLDBhelper(activity.getApplicationContext());

       // ha.insertForm(form_name.getText().toString(),from_s.getText().toString(),to_s.getText().toString(),date.getText().toString(),class_t.getSelectedItem().toString(),quota.getSelectedItem().toString(),phone.getText().toString(),ticket_t.getSelectedItem().toString(),trainno.getText().toString());
        String a=new Integer(ha.numberOfRowsForm()).toString();
        Toast.makeText(activity.getApplicationContext(),"Form"+form_name.getText().toString()+"is saved"+a+" ",Toast.LENGTH_LONG).show();
        activity.finish();
        //(activity).startActionMode(new NewForm());
        //activity.onDetachedFromWindow();
        //MenuItem i= activity.onMenuItemSelected();
        //MenuItem i =activity.
        //onActionItemClicked((activity).startActionMode(new NewForm()),item);
     //   onDestroyActionMode(mode);
        //activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        TextView date =(TextView)activity.findViewById(R.id.dateText);
        Spinner ticket_t=(Spinner)activity.findViewById(R.id.spinner);
        EditText trainno =(EditText)activity.findViewById(R.id.trainno);
        Spinner quota=(Spinner)activity.findViewById(R.id.quota);
        AutoCompleteTextView from_s=(AutoCompleteTextView)activity.findViewById(R.id.fromAutoComplete);
        AutoCompleteTextView to_s=(AutoCompleteTextView)activity.findViewById(R.id.toAutoComplete);
        Spinner class_t=(Spinner)activity.findViewById(R.id.class_t);
        EditText phone=(EditText)activity.findViewById(R.id.phone);
        EditText form_name =(EditText)activity.findViewById(R.id.formname);
        int id = item.getItemId();

        switch (id){
            case R.id.menu_edit: {
                //(String form_name, String from_s, String to_s,String date,String class_t,String quota,String phone,String ticket_t)

                SQLDBhelper ha = new SQLDBhelper(activity.getApplicationContext());
                ha.insertForm(form_name.getText().toString(), from_s.getText().toString(), to_s.getText().toString(), date.getText().toString(), class_t.getSelectedItem().toString(), quota.getSelectedItem().toString(), phone.getText().toString(), ticket_t.getSelectedItem().toString(), trainno.getText().toString());
                int a=ha.numberOfRowsForm();
                Integer b =new Integer(a);
                String al=b.toString();

                Toast.makeText(activity.getApplicationContext(),"Form"+form_name.getText().toString()+"is saved"+al+" ",Toast.LENGTH_LONG).show();
            }
            case R.id.menu_delete:
            {
                Toast.makeText(activity.getApplicationContext(),"form would be deleted",Toast.LENGTH_LONG).show();
            }

        }

        return true;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {

        DrawerLayout drawerLayout = (DrawerLayout)activity.findViewById(R.id.drawer_layout);

        drawerLayout.openDrawer(GravityCompat.START);

    }
}
