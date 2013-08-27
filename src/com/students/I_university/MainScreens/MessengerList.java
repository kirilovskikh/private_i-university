package com.students.I_university.MainScreens;

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
import com.students.I_university.CustomAdapter.CustomAdapterDialogs;
import com.students.I_university.Entity.ListMessage;
import com.students.I_university.MoodleRequest.Image;
import com.students.I_university.MoodleRequest.MoodleRequestListMessage;
import com.students.I_university.R;
import com.students.I_university.dialogActivity;

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

@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.listview_layout, null);
    String wsfunction = "local_iuniversity_recent_messages";
    SharedPreferences preferences = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

    MoodleRequestListMessage listMassager = new MoodleRequestListMessage(getActivity());



    listMassager.addParam("wstoken", preferences.getString("iutoken",""));
    listMassager.addParam("wsfunction", wsfunction);
    ListView kontaktList = (ListView) view.findViewById(R.id.listView);

       try {

           listMassager.execute();
           listMassager.get();


           if(listMassager.isSuccess())
           {
               str = listMassager.getMessage();
               if(str != null) {
                   for(int i = 0; i < str.size(); i++)
                       str.get(i).bitmap = image.userImage;

                   kontaktList.setAdapter(new CustomAdapterDialogs(getActivity().getBaseContext(), R.layout.sms, str));
               }
               else Toast.makeText(
                       getActivity().getApplicationContext(),
                       listMassager.getErrorMessage(),
                       Toast.LENGTH_LONG
               ).show();

           }
           else Toast.makeText(
                   getActivity().getApplicationContext(),
                   listMassager.getErrorMessage(),
                   Toast.LENGTH_LONG
           ).show();

           kontaktList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int userID = Integer.valueOf(str.get(position).id);
                   Intent intent = new Intent(getSherlockActivity(), dialogActivity.class);

                   intent.putExtra("userId", userID );
                   startActivity(intent);
               }
           });
           return view;


       }
       catch (Exception e) {
          String s = "exp";
           Log.e("MyApp", e.getMessage());

       }


      return null;
  }

}
