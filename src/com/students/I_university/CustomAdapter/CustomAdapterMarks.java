package com.students.I_university.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: kotvaska
 * Date: 10.07.13
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */

public class CustomAdapterMarks extends ArrayAdapter<String> {

    String[] course;
    String[] mark;
    Context mContext;
    int layout;
    int layoutSubj;
    int layoutMarks;

    public CustomAdapterMarks(int layout, int Lsubj, int Lmark, String[] course, String[] mark, Context c){
        super(c, layout, course);
        this.layout = layout;
        this.layoutSubj = Lsubj;
        this.layoutMarks = Lmark;
        this.course = course;
        this.mark = mark;
        this.mContext = c;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v;
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(layout, parent, false);

        TextView markView = (TextView)v.findViewById(layoutMarks);
        TextView subView = (TextView)v.findViewById(layoutSubj);

        markView.setText(mark[position]);
        subView.setText(course[position]);

        return v;
    }
}