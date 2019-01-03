package com.example.think.materail.Student;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class ReportCard extends Fragment {

    String AcademiYear[] = {" Select-One", "2018-2019", "2019-2020"};
    String Term[] = {"All", "Term-I", "Term-II"};
    String item;
    String item1;
    ProgressDialog pd;
    ConnectionDetector reportcd;
    String reportUrl = "http://navayuga.icampus360.in/Assessment/R_ReportCard_N_V.aspx?";
    WebView web;
    Spinner selectacademi, selectterm;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.reportcard, container, false);
        web = (WebView) view.findViewById(R.id.webview);


        Button show = (Button) view.findViewById(R.id.btnShow);
        selectacademi = (Spinner) view.findViewById(R.id.academiyear);
        selectterm = (Spinner) view.findViewById(R.id.term);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear);
           pd = new ProgressDialog(getActivity());
        final ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, AcademiYear);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (aa.getCount() > 1) {
            selectacademi.setAdapter(aa);
        }

        selectacademi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item = parent.getItemAtPosition(position).toString();
                if (item.equals("--Select--")) {
                    item = "0";
                } else if (item.equals("2018-2019")) {
                    item = "2";
                } else if (item.equals("2019-2020")) {
                    item = "3";
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter aaa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, Term);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (aaa.getCount() > 1)

        {
            selectterm.setAdapter(aaa);

        }

        selectterm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item1 = parent.getItemAtPosition(position).toString();
                if (item1.equals("All")) {
                    item1 = "All";
                } else if (item1.equals("Term-I")) {
                    item1 = "Term-I";
                } else if (item1.equals("Term-II")) {
                    item1 = "Term-II";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        pd.dismiss();



       show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              pd.show();
                webView1();


            }
        });

        return view;
    }




    public void webView1()
    {



        String urlt = reportUrl + "StdntId=" + Utils.getSavedPreferences(getContext(), MainActivity.stuid,"") + "&AcdYr=" +
                item + "&CollegeID=" + Utils.getSavedPreferences(getContext(), MainActivity.clgid ,"") +
                "&CenterID=" + Utils.getSavedPreferences(getContext(), MainActivity.cenid,"") + "&sptype=" + item1;
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.setWebViewClient(new WebViewClient());
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.setBackgroundColor(Color.WHITE);
        web.loadUrl(urlt);
    }
}