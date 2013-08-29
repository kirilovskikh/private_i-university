package com.students.I_university.Tools.CustomAdapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.students.I_university.Messages.ListMessage;
import com.students.I_university.R;
import com.students.I_university.Tools.AsyncTaskDownloadImage;

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
    ArrayList<ListMessage> messages;

    public CustomAdapterDialogs(Context c, int layout,  ArrayList<ListMessage> messages2){
        super(c, layout, messages2);
        this.mContext = c;
        this.layout = layout;
        this.messages = messages2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
         View v;
         AsyncTaskDownloadImage downloadImage;
         LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         v = vi.inflate(layout, parent, false);

         TextView name = (TextView)v.findViewById(R.id.name);
         TextView sms  = (TextView)v.findViewById(R.id.sms);
         TextView date = (TextView)v.findViewById(R.id.date);
         ListMessage message = messages.get(position);
        ImageView avatar = (ImageView)v.findViewById(R.id.imageView);
        //Drawable d = null;
        name.setText(message.username);
        sms.setText(message.sms);


/*
        try {
            URL url = new URL(messages.imageURL);
            InputStream content = (InputStream)url.getContent();
            d = Drawable.createFromStream(content, "src");
            avatar.setImageDrawable(d);

        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
*/

        date.setText(message.GetCreateTime());
        downloadImage = new AsyncTaskDownloadImage(avatar);
        downloadImage.execute(message.imageURL);
   //     avatar.setImageDrawable(d);
        return v;

    }
}