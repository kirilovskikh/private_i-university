package com.students.I_university.MainScreen.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.students.I_university.Contacts.AsyncTaskGetContacts;
import com.students.I_university.Contacts.CallReturnDownload;

import com.students.I_university.Contacts.ContactActivity;
import com.students.I_university.Tools.CustomAdapter.CustomAdapterContactsListView;

import com.students.I_university.Contacts.ContactInfo;
import com.students.I_university.R;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Maksim
 * Date: 14.07.13
 * Time: 1:03
 * To change this template use File | Settings | File Templates.
 */

public class ContactsList extends SherlockFragment implements CallReturnDownload {

    private ListView listView;
    private HashMap<Integer, ContactInfo> map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout, null);

        listView = (ListView) view.findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (map == null)
                    return;

                int userId = map.get(i).getId();
                String fullname = map.get(i).getFullName();

                Intent intent = new Intent(getSherlockActivity(), ContactActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("fullname", fullname);
                startActivity(intent);
            }
        });

        AsyncTaskGetContacts asyncTaskGetContacts = new AsyncTaskGetContacts(getSherlockActivity());
        asyncTaskGetContacts.returnDownload = this;
        asyncTaskGetContacts.execute();

        return view;
    }

    @Override
    public void returnResult(HashMap<Integer, ContactInfo> map) {
        this.map = map;
        String[] name = getNameFromMap(map);

        CustomAdapterContactsListView arrayAdapter = new CustomAdapterContactsListView(getSherlockActivity(), name, map);
        listView.setAdapter(arrayAdapter);
    }

    private String[] getNameFromMap(HashMap<Integer, ContactInfo> map) {
        String[] name = new String[map.size()];

        for(int i = 0; i < map.size(); ++i)
            name[i] = map.get(i).getFullName();

        return name;  //To change body of created methods use File | Settings | File Templates.
    }

}
