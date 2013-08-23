package com.students.I_university.CustomAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.students.I_university.Entity.ListMessage;
import com.students.I_university.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kotvaska
 * Date: 10.07.13
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */

public class CustomAdapterDialogs extends ArrayAdapter<ListMessage> {

    Context mContext;
    int layout;
    ArrayList<ListMessage> message;

    public CustomAdapterDialogs(Context c, int layout,  ArrayList<ListMessage> message){
        super(c, layout, message);
        this.mContext = c;
        this.layout = layout;
        this.message = message;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
         View v;
         LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         v = vi.inflate(layout, parent, false);

         TextView name = (TextView)v.findViewById(R.id.name);
         TextView sms  = (TextView)v.findViewById(R.id.sms);
         TextView date = (TextView)v.findViewById(R.id.date);
         ListMessage messages = message.get(position);

        name.setText(messages.username);
        sms.setText(messages.sms);
        date.setText(messages.GetCreateTime());
        return v;

    }
}