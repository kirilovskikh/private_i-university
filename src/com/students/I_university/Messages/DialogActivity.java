package com.students.I_university.Messages;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
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
    ArrayList<Message> messages;
    AlertDialog.Builder alert;
    SharedPreferences prefs;
    MoodleRequestMessageChain moodleRequestMessageChain;
    MoodleRequestSendMessage moodleRequestSendMessage;
    CustomAdapterMessageChain adapterMessageChain;
    PullToRefreshListView contactList;
    String userName;
    android.support.v4.util.LruCache<String, Bitmap> cache;
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
        this.alert = new AlertDialog.Builder(this);
        this.prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        this.contactList = (PullToRefreshListView)findViewById(R.id.pullToRefresh);
        this.userID = getIntent().getExtras().getInt("userId");
        this.userName = getIntent().getExtras().getString("userName");
        this.cache = new LruCache<String, Bitmap>(3);

        getSupportActionBar().setTitle(" " + this.userName);
        sendMessageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendMessage();
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
    protected void onStart() {
        super.onStart();
        getMessages();
        contactList.demo();
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

        com.actionbarsherlock.view.MenuItem menuItem = menu.add("Информация");
        menuItem.setIcon(android.R.drawable.ic_dialog_info).setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getBaseContext(), ContactActivity.class);
                intent.putExtra("userId", userID);
                intent.putExtra("fullname", userName);
                startActivity(intent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String text) {
        AlertDialog dialog;
        alert.setTitle("Attention!");
        if(text.length() == 0) alert.setMessage(text);
        dialog = alert.create();
        dialog.show();
    }

    public void getMessages() {
        moodleRequestMessageChain = new MoodleRequestMessageChain(this, cache, prefs.getString("iutoken", ""), String.valueOf(userID));
        moodleRequestMessageChain.setMoodleCallback( new MoodleCallback() {
            @Override
            public void callBackRun() {
                if(moodleRequestMessageChain.isSuccess())
                {
                    messages = moodleRequestMessageChain.getMessageChain();
                    if(messages != null)
                    {
                        adapterMessageChain = new CustomAdapterMessageChain(
                                context,
                                R.layout.message_chain_left,
                                R.layout.message_chain_right,
                                messages,
                                cache
                        );
                        contactList.setAdapter(adapterMessageChain);
                        if(cache.size() == 0) getBitmaps();
                    }
                    else showMessage(moodleRequestMessageChain.getErrorMessage());
                }
                else showMessage("Нет данных о переписке с указанным пользователем.");
                contactList.onRefreshComplete();
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

    public void sendMessage() {
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

    public void getBitmaps() {
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
