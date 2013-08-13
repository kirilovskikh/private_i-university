package com.students.I_university;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.students.I_university.CustomAdapter.CustomAdapterMessageChain;
import com.students.I_university.Entity.Message;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sveta
 * Date: 7/23/13
 * Time: 2:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class dialogActivity extends SherlockActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.listview_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String[] kontakt = new String[] {"Старикова Анастасия Константиновна", "Сидоров Петр Сергеевич", "Петров Алексей Федорович", "Сидорова Дарья Ивановна"};
        final String[] sms = new String[] {"Hello","Hi","Привет","Ура"};
        final ArrayList<Message> messages = new ArrayList<Message>();
        messages.add(new Message("Анастасия","Старикова","","Hello!", new Timestamp(2000000000)));
        messages.add(new Message("Анастасия","Старикова","","Hello!", new Timestamp(2000000000)));
        messages.add(new Message("Анастасия","Старикова","","Hello!", new Timestamp(2000000000)));
        messages.add(new Message("Анастасия","Старикова","","Hello!", new Timestamp(2000000000)));

        ListView kontaktList = (ListView)findViewById(R.id.listView);

        try
        {
            kontaktList.setAdapter(new CustomAdapterMessageChain(getBaseContext(), R.layout.message_chain, messages));
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
