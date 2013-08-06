package com.students.I_university;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 19.07.13
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
public class ThemeItemActivity extends SherlockActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_item);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Основные конструкции C#");
//        getActionBar().setIcon(R.drawable.book);

        BuildList();
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

    private void BuildList()
    {
        final String ATTRIBUTE_NAME_TEXT1 = "text1";
        final String ATTRIBUTE_NAME_TEXT2 = "text2";
        final String ATTRIBUTE_NAME_IMAGE = "image";
        //final String ATTRIBUTE_NAME_VISIBILITY = "visibility";

        String[]texts1 = {"Массивы.docx", "Циклы.pptx",""};
        String[]texts2 = {"23KB", "144KB",""};
        int[]images = {R.drawable.word, R.drawable.powerpoint, 0};
        //int[]visibilities = {View.GONE, View.GONE};

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                texts1.length);
        Map<String, Object> m;
        for (int i = 0; i < texts1.length; i++)
        {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT1, texts1[i]);
            m.put(ATTRIBUTE_NAME_TEXT2, texts2[i]);
            m.put(ATTRIBUTE_NAME_IMAGE, images[i]);
            //m.put(ATTRIBUTE_NAME_VISIBILITY, visibilities[i]);
            data.add(m);
        }

        String[] from = {ATTRIBUTE_NAME_TEXT1,
                ATTRIBUTE_NAME_TEXT2,
                ATTRIBUTE_NAME_IMAGE,
                //ATTRIBUTE_NAME_VISIBILITY
        };
        int[] to = {R.id.text1, R.id.text2, R.id.img//, R.id.progressBar
        };

        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.row_in_theme_listview_item,
                from, to);

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(sAdapter);
    }
}


