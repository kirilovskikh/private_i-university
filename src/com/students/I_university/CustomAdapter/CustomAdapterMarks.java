package com.students.I_university.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.students.I_university.MessageDetailsActivity;
import com.students.I_university.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kotvaska
 * Date: 10.07.13
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */

public class CustomAdapterMarks extends BaseAdapter {

    private ArrayList<MessageDetailsActivity> data;
    Context mContext;

    public CustomAdapterMarks(ArrayList<MessageDetailsActivity> data, Context c){
        this.data = data;
        this.mContext = c;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
         View v;
         LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         v = vi.inflate(R.layout.main_marks_listview_item, parent, false);

         TextView markView = (TextView)v.findViewById(R.id.mark);
         TextView subView = (TextView)v.findViewById(R.id.subject);

         MessageDetailsActivity msg = data.get(position);
         markView.setText(msg.mark);
         subView.setText(msg.sub);

        return v;
    }
}