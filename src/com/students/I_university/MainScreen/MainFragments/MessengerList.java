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
import com.students.I_university.MainScreen.SlidingMenu.MainActivity;
import com.students.I_university.Messages.MoodleCallback;
import com.students.I_university.Tools.CustomAdapter.CustomAdapterDialogs;
import com.students.I_university.Messages.DialogActivity;
import com.students.I_university.Messages.ListMessage;
import com.students.I_university.Messages.Image;
import com.students.I_university.Messages.MoodleRequestListMessage;
import com.students.I_university.R;
import com.students.I_university.Tools.TypeFragment;
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
    private ListView kontaktList;
    private MoodleRequestListMessage listMassager;
    private Context context;
    SherlockFragment activity;

@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.listview_layout, null);
    String wsfunction = "local_iuniversity_recent_messages";

    this.listMassager = new MoodleRequestListMessage(getActivity());
    this.context = getSherlockActivity().getBaseContext();
    this.getSherlockActivity().getSupportActionBar().setTitle(" Сообщения");
    this.activity = this;

    listMassager.addParam("wstoken", Utils.getToken(context));
    listMassager.addParam("wsfunction", wsfunction);
    kontaktList = (ListView) view.findViewById(R.id.listView);

       try {
           listMassager.execute();
           listMassager.setMoodleCallback(new MoodleCallback() {
               @Override
               public void callBackRun() {
                   if(listMassager.isSuccess())
                   {
                       str = listMassager.getMessage();
                       if(str != null) {
                           kontaktList.setAdapter(new CustomAdapterDialogs(context, R.layout.sms, str));
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
                       else {
                           ErrorFragment fragment = new ErrorFragment();
                           fragment.setTypeFragment(TypeFragment.AllMarksFragment);
                           fragment.setTextError(listMassager.getErrorMessage());

                           Utils.changeFragment((MainActivity)getActivity(), activity, fragment);
                           return;
                       }
                   }
                   else {
                       ErrorFragment fragment = new ErrorFragment();
                       fragment.setTypeFragment(TypeFragment.AllMarksFragment);
                       fragment.setTextError("Не удалось загрузить информацию. Повторите.");

                       Utils.changeFragment((MainActivity)getActivity(), activity, fragment);
                       return;
                   }
               }
           });
       }
       catch (Exception e) {
          String s = "exp";
           Log.e("MyApp", e.getMessage());

       }
    return view;
  }
}
