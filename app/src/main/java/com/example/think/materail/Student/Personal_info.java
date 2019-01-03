package com.example.think.materail.Student;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.think.materail.ConnectionDetector;
import com.example.think.materail.MainActivity;
import com.example.think.materail.R;
import com.example.think.materail.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Personal_info extends Fragment {



    TextView Name, Fname,Fcontact,Mcontact,Reg,Stuid,Clgid,Cenid,Clsid,Gen,Scontact,Curryear,Adclass,Adstatus,Mname;
    ImageView profileimage;
    final String url1="http://navayugaapi.thincomputers.org/api/Student/StudentProfile/";
    ProgressDialog progressDialog;
    ConnectionDetector cd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.personal_info, container, false);
        progressDialog=new ProgressDialog(getActivity());
        cd=new ConnectionDetector(getActivity());
        Name =(TextView)view.findViewById(R.id.name);
        Fname=(TextView)view.findViewById(R.id.fathername);
        Fcontact=(TextView)view.findViewById(R.id.fathercontact);
        Mcontact=(TextView)view.findViewById(R.id.mothercontact);
        Reg=(TextView)view.findViewById(R.id.registration);
        Stuid=(TextView)view.findViewById(R.id.studentid);
        Clgid=(TextView)view.findViewById(R.id.collegeid);
        Cenid=(TextView)view.findViewById(R.id.centerid);
        Clsid=(TextView)view.findViewById(R.id.classid);
        Gen=(TextView)view.findViewById(R.id.gender);
        Scontact=(TextView)view.findViewById(R.id.studentcontact);
        Curryear=(TextView)view.findViewById(R.id.currentyear);
        Adclass=(TextView)view.findViewById(R.id.admissionclass);
        Adstatus=(TextView)view.findViewById(R.id.admissionstatus);
        Mname=(TextView)view.findViewById(R.id.mothername);
        profileimage=(ImageView)view.findViewById(R.id.profile_image);



        if(cd.isConnectingToInternet()){
            getJsonArrayRequest();
        }
        else {
            Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }


        return view;
    }
    void getJsonArrayRequest() {


        String urlto = url1+ Utils.getSavedPreferences(getActivity(), MainActivity.stuid,"");


        progressDialog.show();//alternameMobileNo
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlto,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try {
                            String name=response.getString("StudentName");
                            Name.setText(name);

                            String fathername=response.getString("FatherName");
                            Fname.setText(fathername);

                            String registration=response.getString("RegistrationNo");
                            Reg.setText(registration);

                            String fathercontact=response.getString("Fathercontact");
                            Fcontact.setText(fathercontact);

                            String mothercontact=response.getString("MotherContact");
                            Mcontact.setText(mothercontact);

                            String studentid=response.getString("StdntId");
                            Stuid.setText(studentid);

                            String collegeid=response.getString("CollegeId");
                            Clgid.setText(collegeid);

                            String centerid=response.getString("CenterId");
                            Cenid.setText(centerid);

                            String classid=response.getString("Classid");
                            Clsid.setText(classid);

                            String gender=response.getString("Gender");
                            Gen.setText(gender);

                            String studentcontact=response.getString("StdntContactNo");
                            Scontact.setText(studentcontact);

                            String currentyear=response.getString("CurrentYear");
                            Curryear.setText(currentyear);
                            String admissionclass=response.getString("CurrentYear");
                            Adclass.setText(admissionclass);
                            String admissionstatus=response.getString("AdmissnStatus");
                            Adstatus.setText(admissionstatus);

                            String mothername=response.getString("MotherName");
                            Mname.setText(mothername);


                            progressDialog.dismiss();




                        }
                        catch (JSONException e) {
                            e.printStackTrace();
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
