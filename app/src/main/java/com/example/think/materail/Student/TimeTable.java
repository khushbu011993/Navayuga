package com.example.think.materail.Student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.think.materail.R;

public class TimeTable extends Fragment
{
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.timetable, container, false);

        Toast.makeText(getContext(),"Time table is not available right now ??? ",Toast.LENGTH_SHORT).show();
        return view;
    }
}
