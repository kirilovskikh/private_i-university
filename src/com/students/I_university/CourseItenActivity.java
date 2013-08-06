package com.students.I_university;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 19.07.13
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class CourseItenActivity extends SherlockActivity {

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState)          //вход в программу
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);            //говорим что запускать форму main.xml

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Основы языка C#");

        BuildList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ThemeItemActivity.class);
                startActivity(intent);
            }
        });
    }

    private void BuildList() {
        final String ATTRIBUTE_NAME_TEXT1 = "text1";
        final String ATTRIBUTE_NAME_TEXT2 = "text2";

        String[]texts1 = {"Экзамен", "Зачет","Основные конструкции C#"};
        String[]texts2 = {"Критерии оценок:\n60% правильных ответов - оценка 3\n...",
                "Требования к зачету:\n1)  Сдать все лабораторные работы\n...",
                "Массивы.docx\nЦиклы.pptx\n..."};

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(texts1.length);
        Map<String, Object> m;
        for (int i = 0; i < texts1.length; i++)
        {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT1, texts1[i]);
            m.put(ATTRIBUTE_NAME_TEXT2, texts2[i]);
            data.add(m);
        }

        String[] from = {ATTRIBUTE_NAME_TEXT1, ATTRIBUTE_NAME_TEXT2};
        int[] to = {R.id.text1, R.id.text2};

        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.theme_in_course_listview_item,from, to);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(sAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_refresh:
                Intent intent = new Intent(getApplicationContext(), MarksActivity.class);
                intent.putExtra("name", "Курс 1. Основы языка C#");
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

     @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

}