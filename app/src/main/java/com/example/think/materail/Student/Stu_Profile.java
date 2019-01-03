package com.example.think.materail.Student;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.think.materail.MainActivity;
import com.example.think.materail.R;
import com.example.think.materail.Utils;

public class Stu_Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView Navname, Nname;
    ImageView imageView;
    Toolbar toolbar;

    String urll = "http://navayugaapi.thincomputers.org/api/Index/Login/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu__profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("STUDENT");
        setSupportActionBar(toolbar);



        Navname = (TextView) findViewById(R.id.name);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        Nname = (TextView) view.findViewById(R.id.name);
        imageView = (ImageView) view.findViewById(R.id.image);
        Nname.setText(Utils.getSavedPreferences(this, MainActivity.stuname,""));


        navigationView.setNavigationItemSelectedListener(this);
    }
    boolean twice;

    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
       if(twice==true)
       {
           Intent intent=new Intent(Intent.ACTION_MAIN);
           intent.addCategory(Intent.CATEGORY_HOME);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);
           finish();
           System.exit(0);

       }
       twice = true;
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               twice=false;

           }
       },3000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.stu__profile, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logout) {

            Intent intent = new Intent(Stu_Profile.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.changepassword) {
            toolbar.setTitle("Change Password");
            Change_Password changePassword = new Change_Password();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, changePassword).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.profile) {
            toolbar.setTitle("profile");
            Personal_info personal_info = new Personal_info();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, personal_info).commit();
        } else if (id == R.id.attendance) {
            toolbar.setTitle("Attendance");
            Attendance attendance = new Attendance();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, attendance).commit();

        }
        else  if (id == R.id.circular) {
            toolbar.setTitle("Circular");
            Circular circular = new Circular();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, circular).commit();
        }

        else  if (id == R.id.timetable) {
            toolbar.setTitle("Time Table");
             TimeTable timeTable=new TimeTable();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, timeTable).commit();
        }
        else  if (id == R.id.reportcard) {
            toolbar.setTitle("Report Card");
            ReportCard reportCard=new ReportCard();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, reportCard).commit();
        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


    }
