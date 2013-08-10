package com.students.I_university.MainScreens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.students.I_university.Contacts.AsyncTaskGetContacts;
import com.students.I_university.Contacts.CallReturnDownload;
import com.students.I_university.Contacts.ContactActivity;
import com.students.I_university.Contacts.ContactInfo;
import com.students.I_university.CustomAdapter.ContactsListView;
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

    String[] strings = new String[] {"Иванов Иван Иванович", "Сидоров Петр Сергеевич", "Петров Алексей Федорович", "Сидорова Дарья Ивановна"};
    Context context;

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout, null);


        listView = (ListView) view.findViewById(R.id.listView);
//        ContactsListView arrayAdapter = new ContactsListView(getSherlockActivity(), strings);
//        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getSherlockActivity(), ContactActivity.class);
                startActivity(intent);
            }
        });

        AsyncTaskGetContacts asyncTaskGetContacts = new AsyncTaskGetContacts(getSherlockActivity());
        asyncTaskGetContacts.returnDownload = this;
        asyncTaskGetContacts.execute();

//        Toast.makeText(getSherlockActivity(), "create", Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void returnResult(HashMap<Integer, ContactInfo> map) {
        String[] name = getNameFromMap(map);

        ContactsListView arrayAdapter = new ContactsListView(getSherlockActivity(), name);
        listView.setAdapter(arrayAdapter);
    }

    private String[] getNameFromMap(HashMap<Integer, ContactInfo> map) {
        String[] name = new String[map.size()];

        for(int i = 0; i < map.size(); ++i)
            name[i] = map.get(i).getFullName();

        return name;  //To change body of created methods use File | Settings | File Templates.
    }


}
