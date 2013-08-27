package com.students.I_university.Courses;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.students.I_university.MainScreens.CoursesList;
import com.students.I_university.Marks.MarksActivity;
import com.students.I_university.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 19.07.13
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class CourseActivity extends SherlockActivity implements IReturnResult<TopicClass>
{
    private List<TopicClass> TOPICS = new ArrayList<TopicClass>();
    //Для передачи окну Тема
    static public TopicClass TransTopic;

    public static int courseID;
    private static String courseName;

    private Context mContext;
    private AsyncTaskGetTopics myThread;

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState)          //вход в программу
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);            //говорим что запускать форму main.xml

        courseID = getIntent().getExtras().getInt("courseID", -1);
        courseName = getIntent().getExtras().getString("courseName", "---");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(courseName);

        mContext = this;
        myThread = new AsyncTaskGetTopics(mContext);
        myThread.status = this;
        myThread.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.grades:
                Intent intent = new Intent(getApplicationContext(), MarksActivity.class);
                intent.putExtra("courseID", courseID);
                intent.putExtra("name", courseName);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public void returnResult(List<TopicClass> topics) {
        TOPICS = topics;

        ListView listView = (ListView)findViewById(R.id.listView);
        ListAdapter listAdapter = new ListAdapter(this, R.layout.listview_layout, TOPICS);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TransTopic = TOPICS.get(position);
                Intent intent = new Intent(getApplicationContext(), TopicActivity.class);
                startActivity(intent);
            }
        });
    }

    //Собрать все содержимое Темы и сделать в виде многострочной строки
    private String GetTopicFullContent(TopicClass topic) {
        String out = "";
        for (int i = 0; i < topic.ELEMENTS.size(); i++) {
            out += topic.ELEMENTS.get(i).Text;
            if (i < topic.ELEMENTS.size() - 1) out += "\n";
        }
        return out;
    }

    private class ListAdapter extends ArrayAdapter<TopicClass>
    {
        private List<TopicClass> items;

        public ListAdapter(Context context, int resource, List<TopicClass> items)
        {
            super(context, resource, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v = convertView;
            TopicClass p = items.get(position);

            if (v == null)
            {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.course_listview_item, null);
            }

            if (p != null)
            {
                TextView topicName = (TextView)v.findViewById(R.id.topicname);
                TextView topicContent = (TextView)v.findViewById(R.id.topiccontent);

                topicName.setText(p.Name);
                String Content = GetTopicFullContent(p);
                if (Content.isEmpty())
                    topicContent.setVisibility(View.GONE);
                else
                    topicContent.setText(Content);
            }

            return v;
        }
    }
}