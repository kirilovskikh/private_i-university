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

public class CustomAdapterDialogs extends ArrayAdapter<String> {

    Context mContext;
    String[] array1;
    String[] array2;
    int layout;
    int layoutItem1;
    int layoutItem2;

    public CustomAdapterDialogs(Context c, int layout, int item1, int item2, String[] array1, String[] array2){
        super(c, layout, array1);
        this.mContext = c;
        this.layout = layout;
        this.layoutItem1 = item1;
        this.layoutItem2 = item2;
        this.array1 = array1;
        this.array2 = array2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
         View v;
         LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         v = vi.inflate(layout, parent, false);

         TextView item1 = (TextView)v.findViewById(layoutItem1);
         TextView item2 = (TextView)v.findViewById(layoutItem2);

         item1.setText(array1[position]);
         item2.setText(array2[position]);

        return v;
    }
}