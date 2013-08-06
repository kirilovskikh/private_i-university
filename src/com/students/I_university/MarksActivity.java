package com.students.I_university;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.students.I_university.CustomAdapter.CustomAdapterMarks;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kotvaska
 * Date: 10.07.13
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
public class MarksActivity extends SherlockActivity {
    private String[] subjs = new String[] {"Новостной форум", "Android: Screen Densities", "Нравится ли идея создания курса?", "Icons for different densities", "Supporting Multiple Screens", "Screen Densities"};
    private String[] marks = new String[] {"5,0", "2,9", "1,0", "4,9", "3,1", "4,1"};

    ListView lvMain;
    ArrayList<MessageDetailsActivity> details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marks);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(getIntent().getExtras().getString("name"));


        lvMain = (ListView) findViewById(R.id.listView);
        details = new ArrayList<MessageDetailsActivity>();

        for (int i=0;i<6;++i){
            MessageDetailsActivity Detail;
            Detail = new MessageDetailsActivity();
            Detail.setSub(subjs[i]);
            Detail.setMark(marks[i]);
            details.add(Detail);
        }

        lvMain.setAdapter(new CustomAdapterMarks(details , this));

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "It is your mark, dude...", Toast.LENGTH_LONG).show();
                //To change body of implemented methods use File | Settings | File Templates.
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
}
