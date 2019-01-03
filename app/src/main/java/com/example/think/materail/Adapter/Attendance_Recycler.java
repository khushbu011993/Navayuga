package com.example.think.materail.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.think.materail.Model.Attendance_View;
import com.example.think.materail.R;

import java.util.ArrayList;

public class Attendance_Recycler  extends RecyclerView.Adapter<Attendance_Recycler.ViewHolder>
{
    ArrayList<Attendance_View>list;
    Context context;

    public Attendance_Recycler(ArrayList<Attendance_View> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.attendance_view,null,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

       Attendance_View list1=list.get(i);

        viewHolder.txtday.setText(list1.getDay());
        viewHolder.txtdate.setText(list1.getDate());
        viewHolder.txtstatus.setText(list1.getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtday,txtdate,txtstatus;
        public ViewHolder(View itemView) {
            super(itemView);
            txtday=(TextView)itemView.findViewById(R.id.txtday);
            txtdate=(TextView)itemView.findViewById(R.id.txtdate);
            txtstatus=(TextView)itemView.findViewById(R.id.txtstatus);
        }

    }
}

