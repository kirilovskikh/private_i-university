package com.students.I_university.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.students.I_university.Contacts.ContactInfo;
import com.students.I_university.R;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 19.07.13
 * Time: 2:50
 * To change this template use File | Settings | File Templates.
 */
public class ContactsListView extends ArrayAdapter<String> {

    private Context mContext;
    private String[] strings;
    private HashMap<Integer, ContactInfo> map;

    public ContactsListView(Context context, String[] s, HashMap<Integer, ContactInfo> map) {
        super(context, R.layout.list, s);
        this.mContext = context;
        this.strings = s;
        this.map = map;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        if (map.get(position).getPhoneNumber() != null) {
            rowView = inflater.inflate(R.layout.contact_with_phone, parent, false);

            ImageButton imageButton1 = (ImageButton) rowView.findViewById(R.id.imageButton);
            imageButton1.setFocusable(false);
            imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:123456789"));
                    mContext.startActivity(callIntent);
                }
            });

        }
        else
            rowView = inflater.inflate(R.layout.contact_no_phone, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.textView);
        textView.setText(strings[position]);

        return rowView;
    }
}
