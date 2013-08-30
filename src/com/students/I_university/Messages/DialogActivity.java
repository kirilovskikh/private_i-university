package com.students.I_university.Messages;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.LruCache;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.students.I_university.Contacts.ContactActivity;
import com.students.I_university.Tools.AsyncTaskDownloadBitmap;
import com.students.I_university.Tools.CustomAdapter.CustomAdapterMessageChain;
import com.students.I_university.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sveta
 * Date: 7/23/13
 * Time: 2:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class DialogActivity extends SherlockActivity {
    DialogActivity context;
    TextView messageTextInput;
    ImageButton sendMessageButton;
    ImageButton refreshButton;
    Button userProfile;
    ArrayList<Message> messages;
    AlertDialog.Builder alert;
    SharedPreferences prefs;
    MoodleRequestMessageChain moodleRequestMessageChain;
    MoodleRequestSendMessage moodleRequestSendMessage;
    CustomAdapterMessageChain adapterMessageChain;
    PullToRefreshListView contactList;
    String userName;
    LruCache<String, Bitmap> cache;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.dialog_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        this.context = this;
        this.messageTextInput = (TextView)findViewById(R.id.messageTextInput);
        this.sendMessageButton = (ImageButton)findViewById(R.id.sendMessageButton);
        this.userProfile = (Button)findViewById(R.id.userProfile);
        this.alert = new AlertDialog.Builder(this);
        this.prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        this.contactList = (PullToRefreshListView)findViewById(R.id.pullToRefresh);
        this.userID = getIntent().getExtras().getInt("userId");
        this.userName = getIntent().getExtras().getString("userName");
        this.cache = new LruCache<String, Bitmap>(3);

        getSupportActionBar().setTitle(this.userName);
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
                        intent.putExtra("userId", userID);
                        intent.putExtra("fullname", userName);
                        startActivity(intent);
                    }
                }
        );
        contactList.setOnRefreshListener( new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getMessages();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getMessages();
        contactList.demo();
    }

    public void showMessage(String text)
    {
        AlertDialog dialog;
        alert.setTitle("Attention!");
        if(!text.isEmpty()) alert.setMessage(text);
        dialog = alert.create();
        dialog.show();
    }

    public void getMessages()
    {
        moodleRequestMessageChain = new MoodleRequestMessageChain(this, cache, prefs.getString("iutoken", ""), String.valueOf(userID));
        moodleRequestMessageChain.setMoodleCallback( new MoodleCallback() {
            @Override
            public void callBackRun() {
                contactList.onRefreshComplete();
                if(moodleRequestMessageChain.isSuccess())
                {
                    messages = moodleRequestMessageChain.getMessageChain();
                    if(messages != null)
                    {
                        if(adapterMessageChain == null)
                        {
                            adapterMessageChain = new CustomAdapterMessageChain(
                                    context,
                                    R.layout.message_chain_left,
                                    R.layout.message_chain_right,
                                    messages,
                                    cache
                            );

                            contactList.getRefreshableView().setAdapter(adapterMessageChain);
                            //contactList.getRefreshableView().setSelection(adapterMessageChain.getCount() - 1);

                            getBitmaps();

                        }
                        else adapterMessageChain.notifyDataSetChanged();
                    }
                    //else showMessage("Нет переписки с указанным пользователем.");
                    else showMessage(moodleRequestMessageChain.getErrorMessage());
                }
                else showMessage("Нет данных о переписке с указанным пользователем.");
                //else showMessage(moodleRequestMessageChain.getErrorMessage());
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
        if(messageTextInput.getText().length() == 0)
        {
            Toast.makeText(context, "Нельзя отправлять пустую строку. ;)", Toast.LENGTH_SHORT).show();
            return;
        }
        moodleRequestSendMessage = new MoodleRequestSendMessage(this, prefs.getString("iutoken", ""), String.valueOf(userID));
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
    public void getBitmaps()
    {
        AsyncTaskDownloadBitmap downloadBitmap;
        AsyncTaskDownloadBitmap downloadBitmap2;
        String ownImage = "";
        String userImage = "";

        for(int i = 0; i < messages.size(); i++)
        {
            if(messages.get(i).own && ownImage.length() == 0) ownImage = messages.get(i).imageURL;
        }

        downloadBitmap = new AsyncTaskDownloadBitmap(cache);
        downloadBitmap.setCallBack( new MoodleCallback() {
            @Override
            public void callBackRun() {
                adapterMessageChain.notifyDataSetChanged();
            }
        });
        downloadBitmap.execute(ownImage);

        for(int i = 0; i < messages.size(); i++)
        {
            if(!messages.get(i).own && userImage.length() == 0) userImage = messages.get(i).imageURL;
        }

        downloadBitmap2 = new AsyncTaskDownloadBitmap(cache);
        downloadBitmap2.setCallBack( new MoodleCallback() {
            @Override
            public void callBackRun() {
                adapterMessageChain.notifyDataSetChanged();
            }
        });
        downloadBitmap2.execute(userImage);
    }
}
