package com.example.anurag.instabook;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

public class InstantBook extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ActionMode.Callback,NewForm.OnFragmentInteractionListener,HelloFragment.OnFragmentInteractionListener, ExpandableListViewFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLDBhelper sql =new SQLDBhelper(getApplicationContext());
                String a=new Integer(sql.numberOfRows()).toString();

                Snackbar.make(view, a, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                if(sql.numberOfRows()<6) {
                    Intent intent = new Intent(getApplicationContext(), FloatingForm.class);
                    startActivity(intent);
                }
                else
                {
                    Snackbar.make(view, "Limit is to Add only 6", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.instant_book, menu);
        MenuItem save=menu.findItem(R.id.save);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Class fragmentClass=null;

        int id = item.getItemId();
        NewForm firstFragment = new NewForm();
        HelloFragment secondFragment= new HelloFragment();
        Bundle args = new Bundle();
        firstFragment.setArguments(getIntent().getExtras());
        firstFragment.setArguments(args);
        secondFragment.setArguments(getIntent().getExtras());
        secondFragment.setArguments(args);
        ExpandableListViewFragment listViewFragment = new ExpandableListViewFragment();
        listViewFragment.setArguments(getIntent().getExtras());
        listViewFragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();


        if (id == R.id.nav_camera) {
            android.support.v4.app.FragmentManager fragmentManagera = getSupportFragmentManager();
            fragmentManagera.beginTransaction().replace(R.id.flContent,firstFragment).commit();
            fragmentTransaction.addToBackStack(null);

        } else if (id == R.id.nav_gallery) {
            android.support.v4.app.FragmentManager fragmentManagera = getSupportFragmentManager();
            fragmentManagera.beginTransaction().replace(R.id.flContent,secondFragment).commit();

            fragmentTransaction.addToBackStack(null);


        } else if (id == R.id.nav_slideshow) {
            android.support.v4.app.FragmentManager fragmentManagera = getSupportFragmentManager();
            fragmentManagera.beginTransaction().replace(R.id.flContent,listViewFragment).commit();

            fragmentTransaction.addToBackStack(null);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Download InstaBook");
            sendIntent.setType("text/plain");
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            dialog.show();
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));

        } else if (id == R.id.nav_send) {

        }
        fragmentTransaction.commit();
        // Highlight the selected item, update the title, and close the drawer
        item.setChecked(true);
        setTitle(item.getTitle());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    public void onCreateContextMenu(ContextMenu menu, View v,
//                                    ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.context_menu, menu);
//    }
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch (item.getItemId()) {
//            case R.id.edit:
//
//                return true;
//            case R.id.save:
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
//    }
    private ActionMode mActionMode;
//    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
//
//        // Called when the action mode is created; startActionMode() was called
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            // Inflate a menu resource providing context menu items
//            MenuInflater inflater = mode.getMenuInflater();
//            inflater.inflate(R.menu.context_menu, menu);
//            return true;
//        }
//
//        // Called each time the action mode is shown. Always called after onCreateActionMode, but
//        // may be called multiple times if the mode is invalidated.
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return false; // Return false if nothing is done
//        }
//
//        // Called when the user selects a contextual menu item
//        @Override
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.save:
//                    mode.finish(); // Action picked, so close the CAB
//                    return true;
//                default:
//                    return false;
//            }
//        }
//
//        // Called when the user exits the action mode
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//            mActionMode = null;
//        }
//    };
    public void  newForm(String string){

    }
    public void onFragmentInteraction(Uri uri) {

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
        switch (item.getItemId()) {
            case R.id.save:
                Toast.makeText(getApplicationContext(),"Did it ",Toast.LENGTH_LONG).show();
                mode.finish(); // Action picked, so close the CAB
                return true;
            default:
                return false;

    }


}

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
