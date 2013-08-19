package com.students.I_university;


import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ImageButton;
import android.content.SharedPreferences;
import android.view.View;
import android.content.Context;

import com.actionbarsherlock.app.SherlockActivity;
import com.students.I_university.CustomAdapter.CustomAdapterMessageChain;
import com.students.I_university.Entity.Message;
import com.students.I_university.MoodleRequest.MoodleCallback;
import com.students.I_university.MoodleRequest.MoodleRequestMessageChain;
import com.students.I_university.MoodleRequest.MoodleRequestSendMessage;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sveta
 * Date: 7/23/13
 * Time: 2:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class dialogActivity extends SherlockActivity {
    dialogActivity context;
    TextView messageTextInput;
    ImageButton sendMessageButton;
    ArrayList<Message> messages;
    AlertDialog.Builder alert;
    SharedPreferences prefs;
    MoodleRequestMessageChain moodleRequestMessageChain;
    MoodleRequestSendMessage moodleRequestSendMessage;
    ListView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.dialog_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.context = this;
        this.messageTextInput = (TextView)findViewById(R.id.messageTextInput);
        this.sendMessageButton = (ImageButton)findViewById(R.id.sendMessageButton);
        this.alert = new AlertDialog.Builder(this);
        this.prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        this.contactList = (ListView)findViewById(R.id.messageList);

        sendMessageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendMessage();
                    }
                }
        );
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getMessages();
    }

    public void showMessage(String text)
    {
        AlertDialog dialog;
        alert.setTitle("Attention!");
        alert.setMessage(text);
        dialog = alert.create();
        dialog.show();
    }

    public void getMessages()
    {
        moodleRequestMessageChain = new MoodleRequestMessageChain(this, prefs.getString("iutoken", ""), "3");
        moodleRequestMessageChain.setMoodleCallback( new MoodleCallback() {
            @Override
            public void callBackRun() {
                if(moodleRequestMessageChain.isSuccess())
                {
                    messages = moodleRequestMessageChain.getMessageChain();
                    if(messages != null)
                    {
                        CustomAdapterMessageChain adapterMessageChain = new CustomAdapterMessageChain(
                                context,
                                R.layout.message_chain_left,
                                messages
                        );
                        contactList.setAdapter(adapterMessageChain);
                        contactList.setSelection(adapterMessageChain.getCount() - 1);
                    }
                    else showMessage(moodleRequestMessageChain.getErrorMessage());

                }
                else showMessage(moodleRequestMessageChain.getErrorMessage());
            }
        });
        try
        {
            moodleRequestMessageChain.execute();
        }
        catch (Exception e)
        {
            showMessage(moodleRequestMessageChain.getErrorMessage());
        }
    }
    public void sendMessage()
    {
        moodleRequestSendMessage = new MoodleRequestSendMessage(this, prefs.getString("iutoken", ""), "3");
        moodleRequestSendMessage.setMoodleCallback( new MoodleCallback() {
            @Override
            public void callBackRun() {
                if(moodleRequestSendMessage.isSuccess())
                {
                    getMessages();
                    messageTextInput.setText("");
                }
                else showMessage(moodleRequestSendMessage.getErrorMessage());
            }
        });
        moodleRequestSendMessage.setMessageText(messageTextInput.getText().toString());
        moodleRequestSendMessage.execute();
    }
}
