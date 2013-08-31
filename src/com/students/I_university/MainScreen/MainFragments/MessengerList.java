package com.students.I_university.MainScreen.MainFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.students.I_university.Messages.MoodleCallback;
import com.students.I_university.Tools.CustomAdapter.CustomAdapterDialogs;
import com.students.I_university.Messages.DialogActivity;
import com.students.I_university.Messages.ListMessage;
import com.students.I_university.Messages.Image;
import com.students.I_university.Messages.MoodleRequestListMessage;
import com.students.I_university.R;
import com.students.I_university.Tools.Utils;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 23.07.13
 * Time: 3:40
 * To change this template use File | Settings | File Templates.
 */
public class MessengerList extends SherlockFragment {
    public Image image = new Image();
    private ArrayList<ListMessage> str;
    private Context context;
    private ListView kontaktList;
    private MoodleRequestListMessage listMassager;

@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.listview_layout, null);
    String wsfunction = "local_iuniversity_recent_messages";
    this.kontaktList = (ListView) view.findViewById(R.id.listView);
    this.context = this.getSherlockActivity();
    this.listMassager = new MoodleRequestListMessage(context);

    this.getSherlockActivity().getSupportActionBar().setTitle("Сообщения");


    listMassager.addParam("wstoken", Utils.getToken(context));
    listMassager.addParam("wsfunction", wsfunction);
    listMassager.setMoodleCallback(new MoodleCallback() {
        @Override
        public void callBackRun() {
                //listMassager.get();
                if(listMassager.isSuccess())
                {
                    str = listMassager.getMessage();
                    if(str != null) {
//                   for(int i = 0; i < str.size(); i++)
//                       str.get(i).bitmap = image.userImage;

                        kontaktList.setAdapter(new CustomAdapterDialogs(getActivity().getBaseContext(), R.layout.sms, str));
                        kontaktList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int userID = Integer.valueOf(str.get(position).id);
                                String fullname = str.get(position).username;
                                Intent intent = new Intent(getSherlockActivity(), DialogActivity.class);
                                intent.putExtra("userName", fullname );
                                intent.putExtra("userId", userID );
                                startActivity(intent);
                            }
                        });
                    }
                    else Toast.makeText(
                            getActivity().getApplicationContext(),
                            listMassager.getErrorMessage(),
                            Toast.LENGTH_LONG
                    ).show();

                }
                else Toast.makeText(
                        getActivity().getApplicationContext(),
                   /*listMassager.getErrorMessage(),*/
                        "Данные о контактах не получены",
                        Toast.LENGTH_LONG
                ).show();
        }
    });

    try {
        listMassager.execute();
    }
    catch (Exception e) {
            String s = "exp";
            Log.e("MyApp", e.getMessage());

    }
    return view;
  }

}
