package com.kavireletronic.ali.kavireleclient;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.view.View;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import fragment.SefareshatFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static SharedPreferences SP;
    private android.app.FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        botton_navigation();
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
        getMenuInflater().inflate(R.menu.main, menu);
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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void botton_navigation() {
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        //
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab1, R.drawable.ic_local_shipping_black_24dp, R.color.tab1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab2, R.drawable.ic_local_grocery_store_black_24dp, R.color.tab2);
//        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab3, R.drawable.ic_people_black_24dp, R.color.tab3);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
//        bottomNavigation.addItem(item3);
        ////////////// enable /////////////////

//        if (SP.getBoolean("sefareshat",false)){
//            bottomNavigation.enableItemAtPosition(0);
//        }else {
//            bottomNavigation.disableItemAtPosition(0);
//        }
//        //
//        if (SP.getBoolean("prodect",false)){
//            bottomNavigation.enableItemAtPosition(1);
//        }else {
//            bottomNavigation.disableItemAtPosition(1);
//        }
//        //
//        if (SP.getBoolean("customer",false)){
//            bottomNavigation.enableItemAtPosition(2);
//        }else {
//            bottomNavigation.disableItemAtPosition(2);
//        }
        bottomNavigation.setItemDisableColor(Color.parseColor("#3A000000"));

        ///_______________________________________________________________

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FFFF3737"));
        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

        // Enable the translation of the FloatingActionButton
//        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);
        // Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);

        bottomNavigation.setColored(true);

        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
                if(fragment != null)
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                switch (position){
                    case 0:
                        ft = getFragmentManager().beginTransaction();
//                        ft.setCustomAnimations(R.anim.slide_out_up,R.anim.slide_in_up);
//                        ft.addToBackStack(null);

                        ft.replace(R.id.fragment, SefareshatFragment.newInstance());
                        ft.addToBackStack(null);
                        ft.commit();
                        return true;
//                    case 2:
//                        ft = getFragmentManager().beginTransaction();
////                        ft.setCustomAnimations(R.anim.slide_out_up,R.anim.slide_in_up);
////                        ft.addToBackStack(null);
//
//                        ft.replace(R.id.fragment,CustomerFragment.newInstance() );
//                        ft.addToBackStack(null);
//                        ft.commit();
//                        return true;
//                    case 1:
//                        ft = getFragmentManager().beginTransaction();
////                        ft.setCustomAnimations(R.anim.slide_out_up,R.anim.slide_in_up);
////                        ft.addToBackStack(null);
//
//                        ft.replace(R.id.fragment, ProdectFragment.newInstance() );
//                        ft.addToBackStack(null);
//                        ft.commit();
//                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
