package com.students.I_university.Tools.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.students.I_university.R;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 11.08.13
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */

public class CustomAdapterContact extends ArrayAdapter<String>{

    private Context mContext;
    private int layout;
    private HashMap<String, String> stringStringHashMap;
    private String[] names;


    public CustomAdapterContact (Context context, int layout, String[] strings,  HashMap<String, String> map) {
        super(context, layout, strings);

        this.mContext = context;
        this.layout = layout;
        this.stringStringHashMap = map;

        this.names = new String[map.size()];

        int i = 0;
        for (String key : map.keySet()) {
            names[i] = key;
            ++i;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(layout, parent, false);

        TextView type = (TextView) view.findViewById(R.id.textView1);
        TextView data = (TextView) view.findViewById(R.id.textView);

        String stringType = names[position];
        String stringDate = stringStringHashMap.get(names[position]);

        type.setText(stringType + ":");
        data.setText(stringDate);

        return view;
    }
}
