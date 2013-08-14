package com.students.I_university;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.actionbarsherlock.app.SherlockActivity;
import com.students.I_university.CustomAdapter.CustomAdapterMessageChain;
import com.students.I_university.Entity.Message;
import com.students.I_university.MoodleRequest.MoodleRequestMessageChain;

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

        final ArrayList<Message> messages;
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        MoodleRequestMessageChain moodleRequest = new MoodleRequestMessageChain(prefs.getString("iutoken", ""), "3");

        setContentView(R.layout.listview_layout);
        TextView errorText = (TextView)findViewById(R.id.errorText);
        errorText.setText("I love you!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView contactList = (ListView)findViewById(R.id.listView);
        try
        {
            moodleRequest.execute();
            moodleRequest.get();
            //errorText.setText(moodleRequest.getResponse());
            if(moodleRequest.isSuccess())
            {
                messages = moodleRequest.getMessageChain();
                if(messages != null) contactList.setAdapter(
                            new CustomAdapterMessageChain(
                                        getBaseContext(),
                                        R.layout.message_chain,
                                        messages
                            )
                );
                else Toast.makeText(
                            getApplicationContext(),
                            moodleRequest.getErrorMessage(),
                            Toast.LENGTH_LONG
                ).show();

            }
            else Toast.makeText(
                    getApplicationContext(),
                    moodleRequest.getErrorMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
