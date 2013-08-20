package com.students.I_university;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Button;
import android.content.SharedPreferences;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;
import com.students.I_university.Contacts.ContactActivity;
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
    Button userProfile;
    ArrayList<Message> messages;
    AlertDialog.Builder alert;
    SharedPreferences prefs;
    MoodleRequestMessageChain moodleRequestMessageChain;
    MoodleRequestSendMessage moodleRequestSendMessage;
    ListView contactList;
    String fullName;
    int ownID;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.dialog_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.context = this;
        this.messageTextInput = (TextView)findViewById(R.id.messageTextInput);
        this.sendMessageButton = (ImageButton)findViewById(R.id.sendMessageButton);
        this.userProfile = (Button)findViewById(R.id.userProfile);
        this.alert = new AlertDialog.Builder(this);
        this.prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        this.contactList = (ListView)findViewById(R.id.messageList);
        this.ownID = getIntent().getExtras().getInt("userId");

        sendMessageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendMessage();
                    }
                }
        );

        userProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getBaseContext(), ContactActivity.class);
                        intent.putExtra("userId", ownID);
                        intent.putExtra("fullname", fullName);
                        startActivity(intent);
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
        moodleRequestMessageChain = new MoodleRequestMessageChain(this, prefs.getString("iutoken", ""), String.valueOf(ownID));
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
//                        if(fullName == null) fullName = messages.get(0).username;
//                        if(ownID == null)
//                        {
//                            for(int i = 0; messages.get(i).own; i++);
//                            ownID = messages.get(i).
//                        }
//                        getSupportActionBar().setTitle(fullName);
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
        moodleRequestSendMessage = new MoodleRequestSendMessage(this, prefs.getString("iutoken", ""), String.valueOf(ownID));
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
