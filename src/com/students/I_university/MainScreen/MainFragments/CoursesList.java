package com.students.I_university.MainScreen.MainFragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.students.I_university.Courses.AsyncTaskGetCourses;
import com.students.I_university.Courses.CourseActivity;
import com.students.I_university.Courses.CourseClass;
import com.students.I_university.Courses.IReturnResult;
import com.students.I_university.MainScreen.SlidingMenu.MainActivity;
import com.students.I_university.R;
import com.students.I_university.Tools.TypeFragment;
import com.students.I_university.Tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class CoursesList extends SherlockFragment implements IReturnResult<CourseClass> {
    View view;
    //private Context mContext;

    private List<CourseClass> COURSES = new ArrayList<CourseClass>();

    public static SharedPreferences prefs;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Заголовок окна
        getSherlockActivity().getSupportActionBar().setTitle("Список курсов");
        //Запускаем второй поток на загрузку Списка Курсов
        AsyncTaskGetCourses myThread;
        myThread = new AsyncTaskGetCourses(getActivity());
        myThread.status = this;
        myThread.execute();
        //Устанавливаем связь с разметкой
        view = inflater.inflate(R.layout.courses, null);
        return view;
    }

    public void returnResult(List<CourseClass> courses)
    {
        if (courses == null) {
            ErrorFragment fragment = new ErrorFragment();
            fragment.setTypeFragment(TypeFragment.CourseFragment);

            Utils.changeFragment((MainActivity)getActivity(), this, fragment);
            return;
        }

        //Сохраняем переданные данные у себя, чтобы иметь к ним доступ в функции onItemClick
        COURSES = courses;
        //В массив строк записываем названия курсов
        String[] strings = new String[COURSES.size()];
        for (int i = 0; i < COURSES.size(); i++) {
            strings[i] = COURSES.get(i).Name;
        }
        //Передаем список названий курсов адаптеру и оправляем на прорисовку
        ListView listView = (ListView) view.findViewById(R.id.coursesListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getSherlockActivity(), R.layout.courses_listview_item, R.id.coursesTextView, strings);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                int courseID = COURSES.get(i).ID;
                String courseName = COURSES.get(i).Name;

                Intent intent = new Intent(getSherlockActivity(), CourseActivity.class);
                intent.putExtra("courseID", courseID);
                intent.putExtra("courseName", courseName);
                startActivity(intent);
            }
        });
    }
}
