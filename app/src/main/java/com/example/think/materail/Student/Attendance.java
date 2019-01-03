package com.example.think.materail.Student;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.think.materail.Adapter.Attendance_Recycler;
import com.example.think.materail.ConnectionDetector;
import com.example.think.materail.MainActivity;
import com.example.think.materail.Model.Attendance_View;
import com.example.think.materail.R;
import com.example.think.materail.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.P;

public class Attendance extends Fragment {

    ProgressDialog dialog;
    ConnectionDetector Attcd;
    RecyclerView recyclerView;
    Button show;
    int item;
    String item1;
    TextView totalday,totalpresent,totalabsent;
    int total;
    String Status="Present";

    String ABPRE;
    LinearLayout linearLayout;


    final String urlAtt = "http://navayugaapi.thincomputers.org/api/Student/Attendance/";

    String Year[] = {" Select year", "2020", "2019", "2018", "2017"};
    String Month[] = {"Select month", "January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};

    Spinner selectyear, selectmonth;
    ArrayList<Attendance_View> list;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.attendance, container, false);
        dialog = new ProgressDialog(getActivity());
        Attcd = new ConnectionDetector(getActivity());

        totalday=(TextView)view.findViewById(R.id.totalday);
        totalpresent=(TextView)view.findViewById(R.id.totalpresent);
        totalabsent=(TextView)view.findViewById(R.id.totalabsent);
        selectmonth = (Spinner) view.findViewById(R.id.month);
        selectyear = (Spinner) view.findViewById(R.id.year);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        show = (Button) view.findViewById(R.id.btnshow);
        linearLayout=(LinearLayout)view.findViewById(R.id.l3);
        list = new ArrayList<Attendance_View>();




        final ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, Year);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (aa.getCount() > 1) {
            selectyear.setAdapter(aa);
        }

        selectyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item1 = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter aaa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, Month);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (aaa.getCount() > 1)

        {
            selectmonth.setAdapter(aaa);

        }

        selectmonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getSelectedItemPosition();



            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Attcd.isConnectingToInternet()) {
                    getJsonArrayRequest0();
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // totalday.setText(total);

        return view;
    }

    void getJsonArrayRequest0() {


        String urlto = urlAtt + Utils.getSavedPreferences(getActivity(), MainActivity.stuid, "") + "/" + item1 + "/" +item ;
        Log.d("ggg","ggg"+urlto);
        linearLayout.setVisibility(View.VISIBLE);
        dialog.show();
        list.clear();
        StringRequest request = new StringRequest(Request.Method.GET,urlto,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            int l=array.length();
                            totalday.setText("" + l);
                            int A=0;
                            int P=0;
                            if(array.length()<1)
                            {
                                Toast.makeText(getContext(), "Data is not found", Toast.LENGTH_SHORT).show();
                            }
                            totalpresent.setText(""+P);
                            totalabsent.setText(""+A);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Attendance_View item2 = new Attendance_View(

                                        object.getString("Day"),
                                        object.getString("CreatedDate"),
                                        ABPRE= object.getString("Status")
                                );



                                if(Status.equals(ABPRE))
                                {
                                    P++;
                                    P=P;

                                    totalpresent.setText("" + P);


                                }

                                else {
                                    A++;
                                    A=A;

                                    totalabsent.setText("" + A);

                                }

                                list.add(item2);

                            }


                            Log.d("abc","abc"+A+"  "+P   );


                            Attendance_Recycler attendance_recycler=new Attendance_Recycler(list,getContext());
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
                            recyclerView.setAdapter(attendance_recycler);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getContext(), "please select year and month", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }
}




