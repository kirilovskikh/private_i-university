package com.students.I_university.Tools.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.students.I_university.Messages.Message;
import com.students.I_university.R;

import java.util.ArrayList;

/**
 * Created by Akella on 13.08.13.
 */
public class CustomAdapterMessageChain extends ArrayAdapter<Message> {

    Context mContext;
    ArrayList<Message> messages;
    int layout_left;
    int layout_right;

    public CustomAdapterMessageChain(Context c, int layout_left, int layout_right, ArrayList<Message> messages){
        super(c, layout_left, messages);
        this.mContext = c;
        this.layout_left = layout_left;
        this.layout_right = layout_right;
        this.messages = messages;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(position);

        if(message.own) v = vi.inflate(layout_left, parent, false);
        else v = vi.inflate(layout_right, parent, false);

        TextView username = (TextView)v.findViewById(R.id.name);
        TextView messageText = (TextView)v.findViewById(R.id.text);
        TextView date = (TextView)v.findViewById(R.id.date);
        ImageView avatar = (ImageView)v.findViewById(R.id.imageView);


        username.setText(message.username);
        messageText.setText(message.messageText);
        date.setText(message.GetCreateTime());
        if(message.bitmap != null) avatar.setImageBitmap(message.bitmap);

        return v;
    }
}
