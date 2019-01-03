package com.example.think.materail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.think.materail.Student.Stu_Profile;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    private ProgressDialog dialog;

    Button btn;
    EditText pass, edtstuid;
    CheckBox show_pwd;
    TextView forgot_password;
    ProgressDialog progressDialog;
    ConnectionDetector connectionDetector;
    String url = "http://navayugaapi.thincomputers.org/api/Index/Login/";
    public static String stuid = "stuid";
    public static String stuname = "stuname";
    public static String stuimage = "stuimage";
    public static String loginid = "loginid";
    public static String  clgid="clgid";
    public static String cenid="centerid";
    public static String classid="classid";
    public static  String sectionid="sectionid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn = (Button) findViewById(R.id.btn);
        show_pwd = (CheckBox) findViewById(R.id.showpassword);
        pass = (EditText) findViewById(R.id.password);
        edtstuid = (EditText) findViewById(R.id.edtstuid);
        progressDialog = new ProgressDialog(this);
        connectionDetector = new ConnectionDetector(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftKeyboard(MainActivity.this);

                if (connectionDetector.isConnectingToInternet()) {
                    getJsonArrayRequest();
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("No Internet ")
                            .setMessage("Please Check your Connection ???")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();

                }
                if (pass.length() <= 0) {
                    pass.setError("Enter Valid Password ");
                    pass.setText("");

                }

            }
        });

        show_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    void getJsonArrayRequest() {

        String urlto = url + edtstuid.getText().toString().trim() + "/" + pass.getText().toString().trim();
        //  System.out.println("url==" +edtstuid.getText().toString().trim()+"/"+pass.getText().toString().trim());
        progressDialog.show();
        //alternameMobileNo
        final String id = edtstuid.getText().toString().trim();
        final String pas = pass.getText().toString().trim();

        if (edtstuid.length() <= 0 ||  pass.length()<=0 || id.isEmpty()  || pas.isEmpty()) {

            if (id.isEmpty()) {
                edtstuid.setError("Enter Valid User Name");
            }
            if (pas.isEmpty()) {
                pass.setError("Enter Valid Password ");
                //pass.setEnabled(false);
            }
            progressDialog.dismiss();

        }

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // Initialize a new JsonObjectRequest instance
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlto,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("khushi", "pahal" + response);
                        Utils.write("ssss=" + response);
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try {

                            //JSONObject object= new JSONObject("response");


                            if (response.getString("Flag").equalsIgnoreCase("true")) {


                                //   progressDialog.show();
                                String StudentId = response.getString("StudentId");
                                String UserName = response.getString("UserName");
                                String UserImgPath=response.getString("UserImgPath");
                                Utils.savePreferences(MainActivity.this,stuimage,UserImgPath);
                                String LoginID =response.getString("LoginID");
                                Utils.savePreferences(MainActivity.this,loginid,LoginID);
                                Utils.savePreferences(MainActivity.this, stuid, StudentId);
                                Utils.savePreferences(MainActivity.this, stuname, UserName);
                                String collegeid=response.getString("collegeid");
                                Utils.savePreferences(MainActivity.this,clgid,collegeid);
                                String centerid=response.getString("centerid");
                                Utils.savePreferences(MainActivity.this,cenid,centerid);
                                String Classid=response.getString("Classid");
                                Utils.savePreferences(MainActivity.this,classid,Classid);
                                String SectionId=response.getString("SectionId");
                                Utils.savePreferences(MainActivity.this,sectionid,SectionId);
                                Intent intent = new Intent(MainActivity.this, Stu_Profile.class);
                                startActivity(intent);
                                finish();


                            } else {

                                Toast.makeText(MainActivity.this,"Username or password is incorrect",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();

                            //  Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred

                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }
}
