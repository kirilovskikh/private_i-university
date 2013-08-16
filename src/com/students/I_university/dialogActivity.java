package com.students.I_university;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ImageButton;
import android.content.SharedPreferences;

import com.actionbarsherlock.app.SherlockActivity;
import com.students.I_university.CustomAdapter.CustomAdapterMessageChain;
import com.students.I_university.Entity.Message;
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
        this.moodleRequestMessageChain = new MoodleRequestMessageChain(this, prefs.getString("iutoken", ""), "3");

        this.contactList = (ListView)findViewById(R.id.messageList);

/*        sendMessageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MoodleRequestSendMessage moodleRequestSendMessage = new MoodleRequestSendMessage(
                                prefs.getString("iutoken", ""),
                                "3",
                                "LOVE!"//messageTextInput.getText().toString()
                        );
                        moodleRequestSendMessage.execute();
                        AlertDialog dialog;
                        alert.setTitle("You have sent a message!");
                        if(moodleRequestSendMessage.isSuccess())
                            alert.setMessage(moodleRequestSendMessage.foo);
                        else
                            alert.setMessage(moodleRequestSendMessage.getErrorMessage());
                        dialog = alert.create();
                        dialog.show();
                    }
                }
        );*/
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        try
        {
            moodleRequestMessageChain.execute();
            moodleRequestMessageChain.get();
            //errorText.setText(moodleRequest.getResponse());
            if(moodleRequestMessageChain.isSuccess())
            {
                messages = moodleRequestMessageChain.getMessageChain();
                if(messages != null) contactList.setAdapter(
                        new CustomAdapterMessageChain(
                                getBaseContext(),
                                R.layout.message_chain_left,
                                messages
                        )
                );
                else Toast.makeText(
                        getApplicationContext(),
                        moodleRequestMessageChain.getErrorMessage(),
                        Toast.LENGTH_LONG
                ).show();

            }
            else Toast.makeText(
                    getApplicationContext(),
                    moodleRequestMessageChain.getErrorMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
