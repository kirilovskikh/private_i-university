package com.students.I_university.Courses;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.students.I_university.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 19.07.13
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
public class TopicActivity extends SherlockActivity {

    ListView listView;
    //Тема, которую мы получили из предыдущего окна
    private TopicClass Topic = CourseActivity.TransTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(" " + Topic.Name);

        ListView listView = (ListView)findViewById(R.id.list);
        ListAdapter listAdapter = new ListAdapter(this, R.layout.main, Topic.ELEMENTS);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (Topic.ELEMENTS.get(position).Type.equals("resource"))
                {
                    String URL = Topic.ELEMENTS.get(position).URL;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                    startActivity(browserIntent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }


    private class ListAdapter extends ArrayAdapter<ElementClass>
    {
        private List<ElementClass> items;

        public ListAdapter(Context context, int resource, List<ElementClass> items)
        {
            super(context, resource, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = convertView;
            ElementClass element = items.get(position);

            //if (view == null)
            //{
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            if (element.Type.equals("resource"))
            {
                view = layoutInflater.inflate(R.layout.topic_listview_item_resourse, null);
            }
            else
            {
                view = layoutInflater.inflate(R.layout.topic_listview_item_label, null);
            }
            //}

            if (element != null)
            {
                if (element.Type.equals("resource"))
                {
                    ImageView image = (ImageView)view.findViewById(R.id.img);
                    TextView text = (TextView)view.findViewById(R.id.text);
                    TextView size = (TextView)view.findViewById(R.id.filesize);

                    int img;
                    if (element.Extension.equals(".docx") || element.Extension.equals(".doc"))
                    {
                        img = R.drawable.docx;
                    }
                    else if (element.Extension.equals(".pptx") || element.Extension.equals(".ppt"))
                    {
                        img = R.drawable.pptx;
                    }
                    else if (element.Extension.equals(".xls") || element.Extension.equals(".xlsx"))
                    {
                        img = R.drawable.xlsx;
                    }
                    else if (element.Extension.equals(".pdf"))
                    {
                        img = R.drawable.pdf;
                    }
                    else img = R.drawable.mp4;



                    image.setImageResource(img);
                    text.setText(element.Text);
                    size.setText(element.Size);
                }
                else
                {
                    TextView text = (TextView)view.findViewById(R.id.text);

                    text.setText(element.Text);
                }
            }

            return view;
        }
    }
}