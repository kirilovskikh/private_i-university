package com.students.I_university.Marks;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.students.I_university.Tools.CustomAdapter.CustomAdapterMarks;
import com.students.I_university.R;
import com.students.I_university.Tools.Utils;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kotvaska
 * Date: 10.07.13
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
public class MarksActivity extends SherlockActivity implements CallBackMarks {

    ListView lvMain;
    CustomAdapterMarks customAdapterMarks;
    private HashMap<Integer, MarkDetails> map;
    MarksActivity c;
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.marks);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" "+getIntent().getExtras().getString("name"));

        lvMain = (ListView) findViewById(R.id.listView);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "The Force is strong with you, young Padawan...", Toast.LENGTH_LONG).show();
                //в будущем можно сделать по нажатию
                //переход в соответствующую тему
//              Intent intent = new Intent(getApplicationContext(), TopicActivity.class);
//              intent.putExtra("id", map.get(position).getAssign());
//              startActivity(intent);
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        id = getIntent().getExtras().getInt("courseID");
        c = this;
        GetCourses curCourse = new GetCourses(this, true, Integer.toString(id));
        curCourse.returnMarks = this;
        curCourse.execute();
    }

    @Override
    public void returnMarks(HashMap<Integer, MarkDetails> map) {
        if (map==null){
            Toast.makeText(this, "Не удалось загрузить информацию", Toast.LENGTH_SHORT).show();
            return;
        }
        this.map = map;
        TextView result = (TextView) findViewById(R.id.res);
        if (map.size()==0) {
            result.setText("0.0");
            String[] subjs = new String[] {"Нет оценок"};
            String[] marks = new String[] {"---"};
            customAdapterMarks = new CustomAdapterMarks(R.layout.main_marks_listview_item, R.id.subject, R.id.mark,
                    subjs, marks, this);

        }
        else{
            String[] subjs = getFromMap(map, false);
            String[] marks = getFromMap(map, true);

            float res=0;
            for (int i=0;i<marks.length;++i){
                if (!marks[i].equals("---"))
                   res += Float.parseFloat(marks[i]);
            }
            res = res/(marks.length);
            if (res!=0)
                result.setText(Float.toString(res).substring(0,5));
            else
                result.setText(Float.toString(res));
            customAdapterMarks = new CustomAdapterMarks(R.layout.main_marks_listview_item, R.id.subject, R.id.mark,
                    subjs, marks, this);
        }

        lvMain.setAdapter(customAdapterMarks);
    }

    private String[] getFromMap(HashMap<Integer, MarkDetails> map, boolean marks) {
        String[] name = new String[map.size()];

        for(int i = 0; i < map.size(); ++i) {
            if (marks)
                name[i] = map.get(i).getMark();
            else
                name[i] = map.get(i).getCourseName();
        }
        return name;  //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

}
