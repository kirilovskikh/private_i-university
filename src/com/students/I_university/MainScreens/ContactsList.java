package com.students.I_university.MainScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.students.I_university.ContactActivity;
import com.students.I_university.CustomAdapter.ContactsListView;
import com.students.I_university.R;

/**
 * Created with IntelliJ IDEA.
 * User: Maksim
 * Date: 14.07.13
 * Time: 1:03
 * To change this template use File | Settings | File Templates.
 */
public class ContactsList extends SherlockFragment{

    String[] strings = new String[] {"Иванов Иван Иванович", "Сидоров Петр Сергеевич", "Петров Алексей Федорович", "Сидорова Дарья Ивановна"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout, null);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        ContactsListView arrayAdapter = new ContactsListView(getSherlockActivity(), strings);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getSherlockActivity(), ContactActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
