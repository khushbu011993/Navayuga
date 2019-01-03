package com.example.think.materail.Student;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.think.materail.ConnectionDetector;
import com.example.think.materail.MainActivity;
import com.example.think.materail.R;
import com.example.think.materail.Utils;
import org.json.JSONException;
import org.json.JSONObject;


public class Change_Password extends Fragment {


    EditText Oldpass,Newpass,Conpass;
    Button btn1;

    public static String changepass = "changepass";
    ConnectionDetector cd1;

    String changeurl="http://navayugaapi.thincomputers.org/api/Index/ChangePassword/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.change_password, container, false);

        cd1=new ConnectionDetector(getContext());
        Newpass =(EditText) view.findViewById(R.id.edtnewpass);
        Oldpass =(EditText) view.findViewById(R.id.edtoldpass);
        Conpass =(EditText) view.findViewById(R.id.edtconpass);
        btn1=(Button)view.findViewById(R.id.reset);




        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cd1.isConnectingToInternet()){
                    getJson1ArrayRequest();
                    UpdatePassword();

                }
                else {
                    Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }
        });




        return view;
    }

    void getJson1ArrayRequest() {


        String urlt = changeurl +Utils.getSavedPreferences(getContext(),MainActivity.loginid,"")+  "/" +
                Oldpass.getText().toString().trim() + "/" + Conpass.getText().toString();

        // System.out.println("url==" +Loginid.getText().toString().trim()+"/"+Oldpass.getText().toString().trim());
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Log.d("Manish","Kumar"+urlt);
        // Initialize a new JsonObjectRequest instance
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlt,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("khusi", "paha" + response);

                        try {


                            if (response.getString("Flag").equalsIgnoreCase("1")) {

                                String Message = response.getString("Message");
                                Utils.savePreferences(getActivity(), changepass, Message);


                            } else {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );


        requestQueue.add(jsonObjectRequest);
    }

    public void UpdatePassword() {
        String value1 = Oldpass.getText().toString().trim();
        String value2 = Newpass.getText().toString().trim();
        String value3 = Conpass.getText().toString().trim();


        if(value1.isEmpty())
        {
            Toast.makeText(getContext(), "Fill Old Password", Toast.LENGTH_SHORT).show();
        }
        else {

            if(value2.isEmpty())
            {
                Toast.makeText(getContext(), "Please Enter New Password", Toast.LENGTH_SHORT).show();
            }

            else {
                if(value3.isEmpty())
                {
                    Toast.makeText(getContext(), "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }

                else {
                    if(value2.equals(value3))
                    {
                        Toast.makeText(getContext(), "Reset Password Successfully", Toast.LENGTH_SHORT).show();
                        Oldpass.setText("");
                        Newpass.setText("");
                        Conpass.setText("");

                        Intent intent=new Intent(getContext(),MainActivity.class);
                        startActivity(intent);
                    }


                    else
                    {
                        Toast.makeText(getContext(), "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }


    }
}
