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
        ViewHolder holder;
        View v;

        if (convertView == null){
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(mContext).inflate(layout, null);
            holder = new ViewHolder((TextView)viewGroup.findViewById(layoutMarks), (TextView)viewGroup.findViewById(layoutSubj));
            viewGroup.setTag(holder);
            v = viewGroup;
        }
        else{
            v = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        holder.course.setText(course[position]);
        holder.mark.setText(mark[position]);

        return v;
    }

    private class ViewHolder {
        TextView mark;
        TextView course;

        public ViewHolder (TextView mark, TextView course){
            if (this.mark == null)
                this.mark = mark;
            if (this.course == null)
                this.course = course;
        }
    }
}