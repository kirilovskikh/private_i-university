package com.students.I_university.MainScreens;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.students.I_university.CustomAdapter.CustomAdapterDialogs;
import com.students.I_university.Entity.ListMessage;
import com.students.I_university.MoodleRequest.MoodleRequest;
import com.students.I_university.MoodleRequest.MoodleRequestListMessages;
import com.students.I_university.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 23.07.13
 * Time: 3:40
 * To change this template use File | Settings | File Templates.
 */
public class MessengerList extends SherlockFragment {

@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.listview_layout, null);
    final ArrayList<ListMessage> str;
    SharedPreferences preferences = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
    MoodleRequestListMessages listMassager = new MoodleRequestListMessages(preferences.getString("iutoken",""));
    ListView kontaktList = (ListView) view.findViewById(R.id.listView);

       try {

         MoodleRequest request = new MoodleRequest();
         request.execute();
         request.get();

           listMassager.execute();
           listMassager.get();
           if(listMassager.isSuccess())
           {
               str = listMassager.getMessage();
               if(str != null) kontaktList.setAdapter(
                       new CustomAdapterDialogs(
                               getActivity().getBaseContext(),
                               R.layout.sms,
                               str
                       )
               );
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
           return view;

       }
       catch (Exception e) {
          String s = "exp";
           Log.e("MyApp", e.getMessage());

       }
      return null;
  }



}
