package com.students.I_university.MainScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.students.I_university.CustomAdapter.CustomAdapterMarks;
import com.students.I_university.MarksActivity;
import com.students.I_university.MessageDetailsActivity;
import com.students.I_university.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 21.07.13
 * Time: 0:52
 * To change this template use File | Settings | File Templates.
 */
public class AllMarksList extends SherlockFragment{

    String[] subjs = new String[] {"Хранилища данных", "Информационная безопасность", "Android: Screen Densities", "SCORM из двух частей",  "Курс 1. Основы языка C#", "Курс 2. Введение в технологию LINQ"};
    String[] marks = new String[] {"5,0", "2,9", "1,0", "4,9", "3,1", "4,1"};

    ListView lvMain;
    ArrayList<MessageDetailsActivity> details;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_marks, null);

        lvMain = (ListView) view.findViewById(R.id.lvMain);
        details = new ArrayList<MessageDetailsActivity>();

        for (int i=0;i<6;++i){
            MessageDetailsActivity Detail;
            Detail = new MessageDetailsActivity();
            Detail.setSub(subjs[i]);
            Detail.setMark(marks[i]);
            details.add(Detail);
        }

        lvMain.setAdapter(new CustomAdapterMarks(details , getSherlockActivity()));

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getSherlockActivity(), MarksActivity.class);
                intent.putExtra("name", subjs[position]);
                startActivity(intent);
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        return view;
    }
}
