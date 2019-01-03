package com.example.think.materail;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Main2Activity extends Activity {


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =10048;
    private static int SPLASH_TIME_OUT = 4000;


    ConnectionDetector connectionDetector = new ConnectionDetector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        ConnectionDetector connectionDetector = new ConnectionDetector(Main2Activity.this);


        if (connectionDetector.isConnectingToInternet()) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent send = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(send);
                    finish();
                }
            }, SPLASH_TIME_OUT);


        } else {

            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Main2Activity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)

                    .setTitle("Cannot Connect To Internet !!!")

                    .setMessage("Please Check Your Phone's Data Connection or Wi-Fi Settings and Try Again ???")


                    .setPositiveButton(" Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){

                            Toast.makeText(getApplicationContext(), "No Internet Connection !!!!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    })
                    .show();

          /*  WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.width = 700;
            lp.height = 500;
            //lp.x= 500;
            //lp.y= 500;
            alertDialog.getWindow().setAttributes(lp);*/

        }

        }

    @Override

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {


        if (ContextCompat.checkSelfPermission(Main2Activity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,
                    Manifest.permission.READ_CONTACTS)) {

            } else {

                ActivityCompat.requestPermissions(Main2Activity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);


            }
        } else {

        }
    }
}





