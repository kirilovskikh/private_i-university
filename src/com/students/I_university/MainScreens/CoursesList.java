package com.students.I_university.MainScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.students.I_university.CourseItenActivity;
import com.students.I_university.R;

public class CoursesList extends SherlockFragment {

    String[] strings = new String[] {"Эконометрика", "Информационная безопасность", "Основы языка C#", "Менеджмент" , "Экономика"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.courses, null);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getSherlockActivity(), R.layout.course_listview_item, R.id.textView, strings);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getSherlockActivity(), CourseItenActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
