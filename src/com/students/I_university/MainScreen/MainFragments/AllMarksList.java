package com.students.I_university.MainScreen.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.students.I_university.MainScreen.SlidingMenu.MainActivity;
import com.students.I_university.Tools.CustomAdapter.CustomAdapterMarks;
import com.students.I_university.Marks.MarkDetails;
import com.students.I_university.Marks.CallBackMarks;
import com.students.I_university.Marks.GetCourses;
import com.students.I_university.Marks.MarksActivity;
import com.students.I_university.R;
import com.students.I_university.Tools.TypeFragment;
import com.students.I_university.Tools.Utils;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 21.07.13
 * Time: 0:52
 * To change this template use File | Settings | File Templates.
 */
public class AllMarksList extends SherlockFragment implements CallBackMarks {

    ListView lvMain;
    CustomAdapterMarks customAdapterMarks;
    private HashMap<Integer, MarkDetails> map;
    private AllMarksList c;
    private MainActivity activity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getSherlockActivity().getActionBar().setTitle(" Оценки");
        c = this;
        GetCourses getCourses = new GetCourses(getSherlockActivity(), false, null);
        getCourses.returnMarks = this;
        getCourses.execute();

        if (!getCourses.error || !Utils.isOnline(getSherlockActivity())) {

            View view = inflater.inflate(R.layout.empty_listview, null);
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.refreshButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetCourses getCourses = new GetCourses(getSherlockActivity(), true, null);
                    getCourses.returnMarks = c;
                    getCourses.execute();
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
            return view;

        }
        else{
            View view = inflater.inflate(R.layout.main_marks, null);

            lvMain = (ListView) view.findViewById(R.id.lvMain);
            lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getSherlockActivity(), MarksActivity.class);
                    intent.putExtra("name", map.get(position).getCourseName());
                    intent.putExtra("courseID", map.get(position).getId());
                    startActivity(intent);
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });

            return view;
        }
    }

    @Override
    public void returnMarks(HashMap<Integer, MarkDetails> map) {
        if (map == null) {
            ErrorFragment fragment = new ErrorFragment();
            fragment.setTypeFragment(TypeFragment.AllMarksFragment);

            Utils.changeFragment(activity, this, fragment);
            return;
        }

        this.map = map;
        if (map.size()==0) {
            String[] subjs = new String[] {"Нет оценок"};
            String[] marks = new String[] {" "};
            customAdapterMarks = new CustomAdapterMarks(R.layout.main_marks_listview_item, R.id.subject, R.id.mark,
                    subjs, marks, getSherlockActivity());
        }
        else{
            String[] subjs = getFromMap(map, false);
            String[] marks = getFromMap(map, true);
            customAdapterMarks = new CustomAdapterMarks(R.layout.main_marks_listview_item, R.id.subject, R.id.mark,
                    subjs, marks, getSherlockActivity());
        }

        lvMain.setAdapter(customAdapterMarks);
    }

    private String[] getFromMap(HashMap<Integer, MarkDetails> map, boolean marks) {
        String[] name = new String[map.size()];

        for(int i = 0; i < map.size(); ++i) {
            if (marks) name[i] = map.get(i).getMark();
            else name[i] = map.get(i).getCourseName();
        }
        return name;  //To change body of created methods use File | Settings | File Templates.
    }
}
